package com.doubleos.night.packet;

import com.doubleos.night.Night;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketVotingOpen implements IMessage, IMessageHandler<CPacketVotingOpen,CPacketVotingOpen>
{


    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    @Override
    public CPacketVotingOpen onMessage(CPacketVotingOpen message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            Night.proxy.openVotingGui();
        });
        return null;
    }
}
