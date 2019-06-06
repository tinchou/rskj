
import co.rsk.crypto.Keccak256
import co.rsk.test.builders.TransactionBuilder
import org.ethereum.crypto.ECKey
import org.ethereum.util.RskTestContext
import org.junit.jupiter.api.Assertions
import java.math.BigInteger

class DslTestCaseRunner(
    private val testCode: DslTestCase.() -> Unit) {
    fun runJunit() {
        // TODO(mc) configure automine
        val context = RskTestContext(arrayOf("--regtest"))
        DslTestCase(context).apply(testCode)
    }
}

class DslTestCase(private val context: RskTestContext) {
    fun expect(account: Account): DslExpectationAccount {
        return DslExpectationAccount(account)
    }

    fun expect(block: Block): DslExpectationBlock {
        return DslExpectationBlock(context, block)
    }

    fun account(balance: Int): Account {
        return Account(balance)
    }

    fun transaction(function: DslTransaction.() -> Unit): Transaction {
        val transaction = DslTransaction()
        transaction.apply(function)
        return transaction.build()
    }

    fun connect(function: DslConnect.() -> Unit) {
        DslConnect(context).apply(function)
    }

    fun block(function: DslBlock.() -> Unit): BlockWithParent {
        val block = DslBlock()
        block.apply(function)
        return block.build()
    }
}

class DslConnect(private val context: RskTestContext) {
    operator fun BlockWithParent.unaryPlus() {
//        val blockBuilder = BlockBuilder(
//            context.blockchain,
//            BlockGenerator(context.rskSystemProperties.networkConstants, context.rskSystemProperties.activationConfig)
//        )
        val parent = when(this.parent) {
            is genesis -> context.genesis
            else -> context.blockchain.bestBlock // TODO(mc) find actual parent by hash
        }
//        val block = blockBuilder
//            .parent(parent)
//            .transactions(buildTransactions())
//            .minGasPrice(BigInteger.ZERO)
//            .build()
//        context.blockExecutor.executeAndFill(block, parent.header)
//        block.seal()
        buildTransactions()
        val block = context.blockToMineBuilder.build(parent, byteArrayOf())

        // the hash is finally calculated
        this.hash = block.hash

        context.blockchain.tryToConnect(block)
    }

    private fun BlockWithParent.buildTransactions() {
        for (it in transactions) {
            val tx = TransactionBuilder()
                .sender(it.sender.rskAccount)
                .receiver(it.receiver.rskAccount)
                .value(it.value)
                .build()
            // TODO(mc) assert
            context.transactionPool.addTransaction(tx)
        }
    }
}

class Transaction(
    val sender: Account,
    val receiver: Account,
    val value: BigInteger) {

    constructor(sender: Account, receiver: Account, value: Int)
        : this(sender, receiver, value.toBigInteger())
}

sealed class Block {
    lateinit var hash: Keccak256
}

class BlockWithParent(
    val parent: Block,
    val transactions: List<Transaction>
) : Block()

object genesis : Block()

class DslTransaction {
    lateinit var sender: Account
    lateinit var receiver: Account
    var value: Int = 0

    fun build(): Transaction {
        return Transaction(
            sender,
            receiver,
            value
        )
    }
}

class DslTransactionsList {
    private val transactionsList = mutableListOf<Transaction>()

    operator fun Transaction.unaryPlus() {
        transactionsList.add(this)
    }

    fun build(): List<Transaction> {
        return transactionsList
    }
}

class DslBlock {
    lateinit var parent: Block
    private lateinit var transactionsList: List<Transaction>

    fun transactions(function: DslTransactionsList.() -> Unit) {
        require(!::transactionsList.isInitialized)
        transactionsList = DslTransactionsList().apply(function).build()
    }

    fun build(): BlockWithParent {
        return BlockWithParent(
            parent,
            transactionsList
        )
    }
}


object bestBlock

class DslExpectationBlock(
    private val context: RskTestContext,
    private val block: Block) {
    infix fun toBe(expected: bestBlock) {
        // TODO(mc) find a way to retrieve the block by hash
        val other = block.hash
        Assertions.assertEquals(context.blockchain.bestBlock.hash, other)
    }
}

class DslExpectationAccount(private val account: Account) {
    infix fun balanceToBe(expected: Int) {
        Assertions.assertEquals(expected, account.balance)
    }
}

class Account(val balance: Int) {
    val rskAccount = org.ethereum.core.Account(ECKey())

}

fun dsltest(lambda: DslTestCase.() -> Unit): DslTestCaseRunner {
    return DslTestCaseRunner(lambda)
}
