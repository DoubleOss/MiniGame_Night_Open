package com.doubleos.night.packet;

import com.doubleos.night.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketUserCardIdAdd implements IMessage, IMessageHandler<CPacketUserCardIdAdd, CPacketUserCardIdAdd>
{

    int cardId = 0;
    public CPacketUserCardIdAdd(){

    }

    public CPacketUserCardIdAdd(int cardId)
    {
        this.cardId = cardId;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        cardId = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(cardId);
    }

    @Override
    public CPacketUserCardIdAdd onMessage(CPacketUserCardIdAdd message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            Variable.getMainGame().getUseCardIdList().add(message.cardId);
        });
        return null;
    }
}
