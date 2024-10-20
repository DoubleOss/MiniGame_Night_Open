package com.doubleos.night.packet;

import com.doubleos.night.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketUseCardSync implements IMessage, IMessageHandler<CPacketUseCardSync, CPacketUseCardSync>
{

    int number = 0;
    int cardId = 0;

    public CPacketUseCardSync()
    {

    }
    public CPacketUseCardSync(int number, int cardId)
    {
        this.number = number;
        this.cardId = cardId;

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        number = buf.readInt();
        cardId = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(number);
        buf.writeInt(cardId);
    }

    @Override
    public CPacketUseCardSync onMessage(CPacketUseCardSync message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            Variable.getMainGame().getUseCardIdList().set(message.number, message.cardId);
        });
        return null;
    }
}
