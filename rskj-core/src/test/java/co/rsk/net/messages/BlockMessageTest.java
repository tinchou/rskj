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

package co.rsk.net.messages;

import org.ethereum.core.Block;
import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.mock;

/**
 * Created by ajlopez on 5/10/2016.
 */
public class BlockMessageTest {
    @Test
    public void getMessageType() {
        BlockMessage message = new BlockMessage(null);
        Assert.assertEquals(MessageType.BLOCK_MESSAGE, message.getMessageType());
    }

    @Test
    public void getBlock() {
        Block block = mock(Block.class);
        BlockMessage message = new BlockMessage(block);
        Assert.assertSame(block, message.getBlock());
    }
}
