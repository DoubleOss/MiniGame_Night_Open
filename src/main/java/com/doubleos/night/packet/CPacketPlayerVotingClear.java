package com.doubleos.night.packet;

import com.doubleos.night.variable.MainGame;
import com.doubleos.night.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketPlayerVotingClear implements IMessage, IMessageHandler<CPacketPlayerVotingClear, CPacketPlayerVotingClear>
{



    public CPacketPlayerVotingClear(){

    }


    @Override
    public void fromBytes(ByteBuf buf) {


    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    @Override
    public CPacketPlayerVotingClear onMessage(CPacketPlayerVotingClear message, MessageContext ctx)
    {

        Minecraft.getMinecraft().addScheduledTask(()->{
            Variable.getMainGame().getHashPlayerVotingCount().clear();
            Variable.getMainGame().getHashPlayerVotingCheck().clear();

        });
        return null;
    }
}
