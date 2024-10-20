package com.doubleos.night.packet;

import com.doubleos.night.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketGamePlayerSet implements IMessage, IMessageHandler<CPacketGamePlayerSet, CPacketGamePlayerSet>
{

    int number=0;
    String playerName = "";

    public CPacketGamePlayerSet(){}

    public CPacketGamePlayerSet(int number, String playerName)
    {
        this.playerName = playerName;
        this.number = number;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        playerName = ByteBufUtils.readUTF8String(buf);
        number = buf.readInt();


    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, playerName);
        buf.writeInt(number);
    }

    @Override
    public CPacketGamePlayerSet onMessage(CPacketGamePlayerSet message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            Variable.getMainGame().getGamePlayerName().set(message.number, message.playerName);
        });
        return null;
    }
}
