package com.doubleos.night.packet;

import com.doubleos.night.variable.MainGame;
import com.doubleos.night.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketGamePhaseSync implements IMessage, IMessageHandler<CPacketGamePhaseSync,CPacketGamePhaseSync>
{

    public CPacketGamePhaseSync(){

    }
    String gamePhase = "";
    public CPacketGamePhaseSync(String gamePhase)
    {
        this.gamePhase = gamePhase;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        gamePhase = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeUTF8String(buf, gamePhase);
    }

    @Override
    public CPacketGamePhaseSync onMessage(CPacketGamePhaseSync message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            Variable.getMainGame().gamePhase = MainGame.eGamePhase.valueOf(message.gamePhase);
            if(Variable.getMainGame().gamePhase == MainGame.eGamePhase.REASON_VOTING_VIEW)
            {
                Variable.Instance().votingExitActive = true;
                Variable.Instance().viewDisplay = true;
            }
        });

        return null;
    }
}
