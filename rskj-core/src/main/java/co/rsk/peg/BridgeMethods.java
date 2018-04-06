/*
 * This file is part of RskJ
 * Copyright (C) 2017 RSK Labs Ltd.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package co.rsk.peg;

import org.ethereum.core.CallTransaction;

import java.util.Optional;

public enum BridgeMethods {
    UPDATE_COLLECTIONS(
            CallTransaction.Function.fromSignature(
                    "updateCollections",
                    new String[]{},
                    new String[]{}
            ),
            48000L,
            Bridge::updateCollections
    ),
    RECEIVE_HEADERS(
            CallTransaction.Function.fromSignature(
                    "receiveHeaders",
                    new String[]{"bytes[]"},
                    new String[]{}
            ),
            22000L,
            Bridge::receiveHeaders
    ),
    REGISTER_BTC_TRANSACTION(
            CallTransaction.Function.fromSignature(
                    "registerBtcTransaction",
                    new String[]{"bytes", "int", "bytes"},
                    new String[]{}
            ),
            22000L,
            Bridge::registerBtcTransaction
    ),
    RELEASE_BTC(
            CallTransaction.Function.fromSignature(
                    "releaseBtc",
                    new String[]{},
                    new String[]{}
            ),
            23000L,
            Bridge::releaseBtc
    ),
    ADD_SIGNATURE(
            CallTransaction.Function.fromSignature(
                    "addSignature",
                    new String[]{"bytes", "bytes[]", "bytes"},
                    new String[]{}
            ),
            70000L,
            Bridge::addSignature
    ),
    GET_STATE_FOR_BTC_RELEASE_CLIENT(
            CallTransaction.Function.fromSignature(
                    "getStateForBtcReleaseClient",
                    new String[]{},
                    new String[]{"bytes"}
            ),
            4000L,
            Bridge::getStateForBtcReleaseClient
    ),
    GET_STATE_FOR_DEBUGGING(
            CallTransaction.Function.fromSignature(
                    "getStateForDebugging",
                    new String[]{},
                    new String[]{"bytes"}
            ),
            3_000_000L,
            Bridge::getStateForDebugging
    ),
    GET_BTC_BLOCKCHAIN_BEST_CHAIN_HEIGHT(
            CallTransaction.Function.fromSignature(
                    "getBtcBlockchainBestChainHeight",
                    new String[]{},
                    new String[]{"int"}
            ),
            19000L,
            Bridge::getBtcBlockchainBestChainHeight
    ),
    GET_BTC_BLOCKCHAIN_BLOCK_LOCATOR(
            CallTransaction.Function.fromSignature(
                    "getBtcBlockchainBlockLocator",
                    new String[]{},
                    new String[]{"string[]"}
            ),
            76000L,
            Bridge::getBtcBlockchainBlockLocator
    ),
    GET_MINIMUM_LOCK_TX_VALUE(
            CallTransaction.Function.fromSignature(
                    "getMinimumLockTxValue",
                    new String[]{},
                    new String[]{"int"}
            ),
            2000L,
            Bridge::getMinimumLockTxValue
    ),
    IS_BTC_TX_HASH_ALREADY_PROCESSED(
            CallTransaction.Function.fromSignature(
                    "isBtcTxHashAlreadyProcessed",
                    new String[]{"string"},
                    new String[]{"bool"}
            ),
            23000L,
            Bridge::isBtcTxHashAlreadyProcessed
    ),
    GET_BTC_TX_HASH_PROCESSED_HEIGHT(
            CallTransaction.Function.fromSignature(
                    "getBtcTxHashProcessedHeight",
                    new String[]{"string"},
                    new String[]{"int64"}
            ),
            22000L,
            Bridge::getBtcTxHashProcessedHeight
    ),
    GET_FEDERATION_ADDRESS(
            CallTransaction.Function.fromSignature(
                    "getFederationAddress",
                    new String[]{},
                    new String[]{"string"}
            ),
            11000L,
            Bridge::getFederationAddress
    ),
    GET_FEDERATION_SIZE(
            CallTransaction.Function.fromSignature(
                    "getFederationSize",
                    new String[]{},
                    new String[]{"int256"}
            ),
            10000L,
            Bridge::getFederationSize
    ),
    GET_FEDERATION_THRESHOLD(
            CallTransaction.Function.fromSignature(
                    "getFederationThreshold",
                    new String[]{},
                    new String[]{"int256"}
            ),
            11000L,
            Bridge::getFederationThreshold
    ),
    GET_FEDERATOR_PUBLIC_KEY(
            CallTransaction.Function.fromSignature(
                    "getFederatorPublicKey",
                    new String[]{"int256"},
                    new String[]{"bytes"}
            ),
            10000L,
            Bridge::getFederatorPublicKey
    ),
    GET_FEDERATION_CREATION_TIME(
            CallTransaction.Function.fromSignature(
                    "getFederationCreationTime",
                    new String[]{},
                    new String[]{"int256"}
            ),
            10000L,
            Bridge::getFederationCreationTime
    ),
    GET_FEDERATION_CREATION_BLOCK_NUMBER(
            CallTransaction.Function.fromSignature(
                    "getFederationCreationBlockNumber",
                    new String[]{},
                    new String[]{"int256"}
            ),
            10000L,
            Bridge::getFederationCreationBlockNumber
    ),
    GET_RETIRING_FEDERATION_ADDRESS(
            CallTransaction.Function.fromSignature(
                    "getRetiringFederationAddress",
                    new String[]{},
                    new String[]{"string"}
            ),
            3000L,
            Bridge::getRetiringFederationAddress
    ),
    GET_RETIRING_FEDERATION_SIZE(
            CallTransaction.Function.fromSignature(
                    "getRetiringFederationSize",
                    new String[]{},
                    new String[]{"int256"}
            ),
            3000L,
            Bridge::getRetiringFederationSize
    ),
    GET_RETIRING_FEDERATION_THRESHOLD(
            CallTransaction.Function.fromSignature(
                    "getRetiringFederationThreshold",
                    new String[]{},
                    new String[]{"int256"}
            ),
            3000L,
            Bridge::getRetiringFederationThreshold
    ),
    GET_RETIRING_FEDERATOR_PUBLIC_KEY(
            CallTransaction.Function.fromSignature(
                    "getRetiringFederatorPublicKey",
                    new String[]{"int256"},
                    new String[]{"bytes"}
            ),
            3000L,
            Bridge::getRetiringFederatorPublicKey
    ),
    GET_RETIRING_FEDERATION_CREATION_TIME(
            CallTransaction.Function.fromSignature(
                    "getRetiringFederationCreationTime",
                    new String[]{},
                    new String[]{"int256"}
            ),
            3000L,
            Bridge::getRetiringFederationCreationTime
    ),
    GET_RETIRING_FEDERATION_CREATION_BLOCK_NUMBER(
            CallTransaction.Function.fromSignature(
                    "getRetiringFederationCreationBlockNumber",
                    new String[]{},
                    new String[]{"int256"}
            ),
            3000L,
            Bridge::getRetiringFederationCreationBlockNumber
    ),
    CREATE_FEDERATION(
            CallTransaction.Function.fromSignature(
                    "createFederation",
                    new String[]{},
                    new String[]{"int256"}
            ),
            11000L,
            Bridge::createFederation
    ),
    ADD_FEDERATOR_PUBLIC_KEY(
            CallTransaction.Function.fromSignature(
                    "addFederatorPublicKey",
                    new String[]{"bytes"},
                    new String[]{"int256"}
            ),
            13000L,
            Bridge::addFederatorPublicKey
    ),
    COMMIT_FEDERATION(
            CallTransaction.Function.fromSignature(
                    "commitFederation",
                    new String[]{"bytes"},
                    new String[]{"int256"}
            ),
            38000L,
            Bridge::commitFederation
    ),
    ROLLBACK_FEDERATION(
            CallTransaction.Function.fromSignature(
                    "rollbackFederation",
                    new String[]{},
                    new String[]{"int256"}
            ),
            12000L,
            Bridge::rollbackFederation
    ),
    GET_PENDING_FEDERATION_HASH(
            CallTransaction.Function.fromSignature(
                    "getPendingFederationHash",
                    new String[]{},
                    new String[]{"bytes"}
            ),
            3000L,
            Bridge::getPendingFederationHash
    ),
    GET_PENDING_FEDERATION_SIZE(
            CallTransaction.Function.fromSignature(
                    "getPendingFederationSize",
                    new String[]{},
                    new String[]{"int256"}
            ),
            3000L,
            Bridge::getPendingFederationSize
    ),
    GET_PENDING_FEDERATOR_PUBLIC_KEY(
            CallTransaction.Function.fromSignature(
                    "getPendingFederatorPublicKey",
                    new String[]{"int256"},
                    new String[]{"bytes"}
            ),
            3000L,
            Bridge::getPendingFederatorPublicKey
    ),
    GET_LOCK_WHITELIST_SIZE(
            CallTransaction.Function.fromSignature(
                    "getLockWhitelistSize",
                    new String[]{},
                    new String[]{"int256"}
            ),
            16000L,
            Bridge::getLockWhitelistSize
    ),
    GET_LOCK_WHITELIST_ADDRESS(
            CallTransaction.Function.fromSignature(
                    "getLockWhitelistAddress",
                    new String[]{"int256"},
                    new String[]{"string"}
            ),
            16000L,
            Bridge::getLockWhitelistAddress
    ),
    ADD_LOCK_WHITELIST_ADDRESS(
            CallTransaction.Function.fromSignature(
                    "addLockWhitelistAddress",
                    new String[]{"string", "int256"},
                    new String[]{"int256"}
            ),
            25000L,
            Bridge::addLockWhitelistAddress
    ),
    REMOVE_LOCK_WHITELIST_ADDRESS(
            CallTransaction.Function.fromSignature(
                    "removeLockWhitelistAddress",
                    new String[]{"string"},
                    new String[]{"int256"}
            ),
            24000L,
            Bridge::removeLockWhitelistAddress
    ),
    SET_LOCK_WHITELIST_DISABLE_BLOCK_DELAY(
            CallTransaction.Function.fromSignature(
                    "setLockWhitelistDisableBlockDelay",
                    new String[]{"int256"},
                    new String[]{"int256"}
            ),
            24000L,
            Bridge::setLockWhitelistDisableBlockDelay
    ),
    GET_FEE_PER_KB(
            CallTransaction.Function.fromSignature(
                    "getFeePerKb",
                    new String[]{},
                    new String[]{"int256"}
            ),
            2000L,
            Bridge::getFeePerKb
    ),
    VOTE_FEE_PER_KB(
            CallTransaction.Function.fromSignature(
                    "voteFeePerKbChange",
                    new String[]{"int256"},
                    new String[]{"int256"}
            ),
            10000L,
            Bridge::voteFeePerKbChange
    );

    private final CallTransaction.Function function;
    private final long cost;
    private final BridgeMethodExecutor executor;

    BridgeMethods(CallTransaction.Function function, long cost, BridgeMethodExecutor executor) {
        this.function = function;
        this.cost = cost;
        this.executor = executor;
    }

    public CallTransaction.Function getFunction() {
        return function;
    }

    public long getCost() {
        return cost;
    }

    public BridgeMethodExecutor getExecutor() {
        return executor;
    }

    public interface BridgeMethodExecutor {
        Optional<?> execute(Bridge self, Object[] args) throws Exception;
    }
}
