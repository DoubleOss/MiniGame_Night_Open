package com.doubleos.night.packet;

import com.doubleos.night.variable.MainGame;
import com.doubleos.night.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketPoliceDectiveCardId implements IMessage, IMessageHandler<CPacketPoliceDectiveCardId, CPacketPoliceDectiveCardId>
{


    int cardId = 0;

    public CPacketPoliceDectiveCardId(){}


    public CPacketPoliceDectiveCardId(int cardId)
    {
        this.cardId = cardId;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        cardId = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(cardId);
    }

    @Override
    public CPacketPoliceDectiveCardId onMessage(CPacketPoliceDectiveCardId message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            MainGame game = Variable.getMainGame();
            game.setPoliceDectiveCardId(message.cardId);
        });
        return null;
    }
}
