package com.doubleos.night.packet;

import com.doubleos.night.variable.MainGame;
import com.doubleos.night.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketShowVoting implements IMessage, IMessageHandler<CPacketShowVoting, CPacketVotingOpen>
{


    boolean active = false;

    public CPacketShowVoting(boolean active)
    {
        this.active = active;
    }
    public CPacketShowVoting(){}
    @Override
    public void fromBytes(ByteBuf buf) {
        active = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(active);
    }

    @Override
    public CPacketVotingOpen onMessage(CPacketShowVoting message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            Variable.getMainGame().setShowVotingResult(message.active);
        });

        return null;
    }
}
