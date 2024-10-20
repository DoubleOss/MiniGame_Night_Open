package com.doubleos.night.packet;

import com.doubleos.night.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketGameTrunSync implements IMessage, IMessageHandler<CPacketGameTrunSync,CPacketGameTrunSync>
{

    int number = 0;
    public CPacketGameTrunSync()
    {

    }

    public CPacketGameTrunSync(int number)
    {
        this.number = number;
    }
    @Override
    public void fromBytes(ByteBuf buf) {
        number =buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(number);
    }

    @Override
    public CPacketGameTrunSync onMessage(CPacketGameTrunSync message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            Variable.getMainGame().setGameTurn(message.number);
        });
        return null;
    }
}
