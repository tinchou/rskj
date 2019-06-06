val transfers01 = dsltest {
    val acc1 = account(10000000)
    val acc2 = account(0)

    val tx01 = transaction {
        sender = acc1
        receiver = acc2
        value = 1000
    }

    val b01 = block {
        parent = genesis
        transactions {
            +tx01
        }
    }

    connect {
        +b01
    }

    expect(b01) toBe bestBlock
    expect(acc2) balanceToBe 1000
}

//transaction_build tx01
//sender acc1
//receiver acc2
//value 1000
//build
//
//block_build b01
//parent g00
//transactions tx01
//build
//
//block_connect b01
//
//# Assert best block
//assert_best b01
//
//assert_balance acc2 1000

