package com.doubleos.night.packet;

import com.doubleos.night.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketDummyActive implements IMessage, IMessageHandler<CPacketDummyActive, CPacketDummyActive>
{

    boolean active = false;

    public CPacketDummyActive(boolean active)
    {
        this.active = active;
    }
    public CPacketDummyActive()
    {

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        active = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeBoolean(active);

    }

    @Override
    public CPacketDummyActive onMessage(CPacketDummyActive message, MessageContext ctx) {

        Minecraft.getMinecraft().addScheduledTask(()->
        {
            Variable.getMainGame().setDummyCardSearch(message.active);
        });
        return null;
    }
}
