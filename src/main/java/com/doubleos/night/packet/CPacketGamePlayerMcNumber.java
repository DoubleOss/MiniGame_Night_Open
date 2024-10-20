package com.doubleos.night.packet;

import com.doubleos.night.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketGamePlayerMcNumber implements IMessage, IMessageHandler<CPacketGamePlayerMcNumber, CPacketGamePlayerMcNumber>
{

    int number = 0;
    public CPacketGamePlayerMcNumber(){}

    public CPacketGamePlayerMcNumber(int number)
    {
        this.number = number;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        number = buf.readInt();

    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(number);
    }

    @Override
    public CPacketGamePlayerMcNumber onMessage(CPacketGamePlayerMcNumber message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            Variable.getMainGame().setGamePlayerMcNumber(message.number);
        });
        return null;
    }
}
