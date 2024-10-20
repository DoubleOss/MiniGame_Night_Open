package com.doubleos.night.packet;

import com.doubleos.night.variable.MainGame;
import com.doubleos.night.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketPlayerVotingPositionSync implements IMessage, IMessageHandler<CPacketPlayerVotingPositionSync, CPacketPlayerVotingPositionSync>
{

    String playerName = "";

    String position = "";

    public CPacketPlayerVotingPositionSync(){}

    public CPacketPlayerVotingPositionSync(String name, String position)
    {
        this.playerName = name;

        this.position = position;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        playerName = ByteBufUtils.readUTF8String(buf);
        position = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, playerName);
        ByteBufUtils.writeUTF8String(buf, position);
    }

    @Override
    public CPacketPlayerVotingPositionSync onMessage(CPacketPlayerVotingPositionSync message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            MainGame mainGame = Variable.getMainGame();

            mainGame.getHashPlayerVotingCheck().put(message.playerName, message.position);
        });
        return null;
    }
}
