package com.doubleos.night.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SPacketReadyCommand implements IMessage, IMessageHandler<SPacketReadyCommand, SPacketReadyCommand>
{

    String m_name = "";
    String m_command = "";

    public SPacketReadyCommand(){}

    public SPacketReadyCommand(String name, String command)
    {
        m_name = name;
        m_command = command;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {

        m_name = ByteBufUtils.readUTF8String(buf);
        m_command = ByteBufUtils.readUTF8String(buf);

    }

    @Override
    public void toBytes(ByteBuf buf)
    {

        ByteBufUtils.writeUTF8String(buf, m_name);
        ByteBufUtils.writeUTF8String(buf, m_command);

    }

    @Override
    public SPacketReadyCommand onMessage(SPacketReadyCommand message, MessageContext ctx)
    {
        EntityPlayerMP player = ctx.getServerHandler().player;
        player.getServer().commandManager.executeCommand(player, message.m_command);
        return null;
    }
}
