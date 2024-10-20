package com.doubleos.night.packet;

import com.doubleos.night.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketHashTrunPlayerAnswerClear implements IMessage, IMessageHandler<CPacketHashTrunPlayerAnswerClear, CPacketHashTrunPlayerAnswerClear>
{

    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    @Override
    public CPacketHashTrunPlayerAnswerClear onMessage(CPacketHashTrunPlayerAnswerClear message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            Variable.getMainGame().getHashPlayerToPlayerAnswer().clear();
        });
        return null;
    }
}
