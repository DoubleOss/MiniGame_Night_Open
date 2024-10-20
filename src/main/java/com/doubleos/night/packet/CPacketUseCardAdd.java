package com.doubleos.night.packet;

import com.doubleos.night.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketUseCardAdd implements IMessage, IMessageHandler<CPacketUseCardAdd, CPacketUseCardAdd>
{

    public CPacketUseCardAdd(){}



    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    @Override
    public CPacketUseCardAdd onMessage(CPacketUseCardAdd message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            Variable.getMainGame().getUseCardIdList().add(1);
        });
        return null;
    }
}
