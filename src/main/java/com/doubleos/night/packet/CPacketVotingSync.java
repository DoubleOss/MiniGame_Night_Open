package com.doubleos.night.packet;

import com.doubleos.night.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketVotingSync implements IMessage, IMessageHandler<CPacketVotingSync,CPacketVotingSync>
{

    String name = "";
    int number = 0;

    public CPacketVotingSync()
    {

    }

    public CPacketVotingSync(String name, int number)
    {
        this.name = name;
        this.number = number;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        name = ByteBufUtils.readUTF8String(buf);
        number = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, name);
        buf.writeInt(number);
    }

    @Override
    public CPacketVotingSync onMessage(CPacketVotingSync message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            Variable.getMainGame().getHashPlayerVotingCount().put(message.name, message.number);
        });
        return null;
    }
}
