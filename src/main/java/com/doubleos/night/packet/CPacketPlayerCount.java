package com.doubleos.night.packet;

import com.doubleos.night.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketPlayerCount implements IMessage, IMessageHandler<CPacketPlayerCount, CPacketPlayerCount>
{

    int playerCount = 0;

    public CPacketPlayerCount()
    {

    }

    public CPacketPlayerCount(int playerCount)
    {
        this.playerCount = playerCount;
    }


    @Override
    public void fromBytes(ByteBuf buf)
    {
        playerCount = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(playerCount);
    }

    @Override
    public CPacketPlayerCount onMessage(CPacketPlayerCount message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            Variable variable = Variable.Instance();
            variable.playerCount = message.playerCount;
            variable.downloadCounting = 0;
        });
        return null;
    }
}
