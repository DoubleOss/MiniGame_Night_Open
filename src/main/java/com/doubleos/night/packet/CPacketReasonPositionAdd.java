package com.doubleos.night.packet;

import com.doubleos.night.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketReasonPositionAdd implements IMessage, IMessageHandler<CPacketReasonPositionAdd, CPacketReasonPositionAdd>
{

    String playerName = "";
    String position = "";

    public CPacketReasonPositionAdd()
    {

    }

    public CPacketReasonPositionAdd(String playerName, String position)
    {
        this.playerName = playerName;
        this.position = position;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        playerName = ByteBufUtils.readUTF8String(buf);
        position = ByteBufUtils.readUTF8String(buf);

    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeUTF8String(buf, playerName);
        ByteBufUtils.writeUTF8String(buf, position);

    }

    @Override
    public CPacketReasonPositionAdd onMessage(CPacketReasonPositionAdd message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            Variable.getMainGame().getHashReasonPosition().put(message.playerName, message.position);
        });
        return null;
    }
}
