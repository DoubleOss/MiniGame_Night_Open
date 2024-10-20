package com.doubleos.night.packet;

import com.doubleos.night.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketImageListClear implements IMessage, IMessageHandler<CPacketImageListClear, CPacketImageListClear>
{


    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    @Override
    public CPacketImageListClear onMessage(CPacketImageListClear message, MessageContext ctx)
    {

        Minecraft.getMinecraft().addScheduledTask(()->
        {

            Variable variable = Variable.Instance();
            variable.m_hashGetPngInfo.get("플레이어").clear();
            variable.m_readyList.clear();
        });


        return null;
    }
}
