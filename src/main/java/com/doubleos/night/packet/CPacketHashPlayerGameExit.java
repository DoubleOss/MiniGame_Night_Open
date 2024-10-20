package com.doubleos.night.packet;

import com.doubleos.night.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketHashPlayerGameExit implements IMessage, IMessageHandler<CPacketHashPlayerGameExit, CPacketHashPlayerGameExit>
{

    String name = "";

    String state = "";

    public CPacketHashPlayerGameExit(){}

    public CPacketHashPlayerGameExit(String name, String state)
    {
        this.name = name;
        this.state = state;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        name = ByteBufUtils.readUTF8String(buf);
        state = ByteBufUtils.readUTF8String(buf);

    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, name);
        ByteBufUtils.writeUTF8String(buf, state);

    }

    @Override
    public CPacketHashPlayerGameExit onMessage(CPacketHashPlayerGameExit message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {

            Variable.getMainGame().getHashPlayerGameExit().put(message.name, message.state);

        });

        return null;
    }
}
