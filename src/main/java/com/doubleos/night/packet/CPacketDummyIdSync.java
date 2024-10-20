package com.doubleos.night.packet;

import com.doubleos.night.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketDummyIdSync implements IMessage, IMessageHandler<CPacketDummyIdSync, CPacketDummyIdSync>
{

    int dummyId = 0;
    public CPacketDummyIdSync(int dummyId)
    {
        this.dummyId = dummyId;
    }

    public CPacketDummyIdSync()
    {

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        dummyId = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeInt(dummyId);

    }

    @Override
    public CPacketDummyIdSync onMessage(CPacketDummyIdSync message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            Variable.getMainGame().setDummyCardId(message.dummyId);
        });
        return null;
    }
}
