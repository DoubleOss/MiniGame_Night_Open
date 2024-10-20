package com.doubleos.night.packet;

import com.doubleos.night.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketReasonVotingExit implements IMessage, IMessageHandler<CPacketReasonVotingExit, CPacketReasonVotingExit>
{

    boolean active = false;

    public CPacketReasonVotingExit()
    {

    }

    public CPacketReasonVotingExit(boolean active)
    {
        this.active = active;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        active = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(active);
    }

    @Override
    public CPacketReasonVotingExit onMessage(CPacketReasonVotingExit message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            Variable.Instance().votingExitActive = message.active;
        });
        return null;
    }
}
