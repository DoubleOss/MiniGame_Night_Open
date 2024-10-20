package com.doubleos.night.packet;

import com.doubleos.night.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketQuestionCardId implements IMessage, IMessageHandler<CPacketQuestionCardId, CPacketQuestionCardId>
{


    public CPacketQuestionCardId(){

    }

    int cardId = 0;
    boolean isQuestionCard = false;
    public CPacketQuestionCardId(int cardId, boolean isQuestionCard)
    {
        this.cardId = cardId;
        this.isQuestionCard = isQuestionCard;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        cardId = buf.readInt();
        isQuestionCard = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(cardId);
        buf.writeBoolean(isQuestionCard);
    }

    @Override
    public CPacketQuestionCardId onMessage(CPacketQuestionCardId message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            Variable.getMainGame().setQuestionCardId(message.cardId);
            Variable.getMainGame().setQuestionCardDraw(message.isQuestionCard);
            Variable.getMainGame().getViewQuestionCardList().add(Variable.Instance().getHashIdToQuestonCard().get(message.cardId));

        });
        return null;
    }
}
