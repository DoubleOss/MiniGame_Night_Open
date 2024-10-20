package com.doubleos.night.packet;

import com.doubleos.night.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketPlayerCardSync implements IMessage, IMessageHandler<CPacketPlayerCardSync, CPacketPlayerCardSync>
{

    String playerName = "";
    int cardId = 0;


    public CPacketPlayerCardSync()
    {

    }

    public CPacketPlayerCardSync(String playerName, int cardId)
    {
        this.playerName = playerName;
        this.cardId = cardId;
    }


    @Override
    public void fromBytes(ByteBuf buf)
    {
        playerName = ByteBufUtils.readUTF8String(buf);
        cardId = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeUTF8String(buf, playerName);
        buf.writeInt(cardId);
    }

    @Override
    public CPacketPlayerCardSync onMessage(CPacketPlayerCardSync message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            Variable.getMainGame().getHashPlayerToCardInfo().put(message.playerName, Variable.Instance().getHashIdToCard().get(message.cardId));
            Variable.getMainGame().getHashCardIdToPlayerName().put(message.cardId, message.playerName);
        });
        return null;
    }
}
