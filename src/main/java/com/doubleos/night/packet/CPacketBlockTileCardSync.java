package com.doubleos.night.packet;

import com.doubleos.night.block.tile.BlockTileCardView;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketBlockTileCardSync implements IMessage, IMessageHandler<CPacketBlockTileCardSync, CPacketBlockTileCardSync>
{

    BlockPos m_pos = BlockPos.ORIGIN;
    Long m_posLong = 0l;
    int cardId = 0;

    public CPacketBlockTileCardSync()
    {

    }
    public CPacketBlockTileCardSync(BlockPos pos, int cardId)
    {
        this.m_pos = pos;
        this.cardId = cardId;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        m_posLong = buf.readLong();
        cardId = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(m_pos.toLong());
        buf.writeInt(cardId);
    }

    @Override
    public CPacketBlockTileCardSync onMessage(CPacketBlockTileCardSync message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            BlockPos pos = BlockPos.fromLong(message.m_posLong);
            TileEntity tileEntity = Minecraft.getMinecraft().world.getTileEntity(pos);
            if(tileEntity instanceof BlockTileCardView)
            {
                BlockTileCardView fruitEntity = (BlockTileCardView) tileEntity;
                fruitEntity.cardId = message.cardId;
            }
        });
        return null;
    }
}
