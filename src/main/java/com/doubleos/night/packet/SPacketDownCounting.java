package com.doubleos.night.packet;

import com.doubleos.night.variable.MainGame;
import com.doubleos.night.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SPacketDownCounting implements IMessage, IMessageHandler<SPacketDownCounting, SPacketDownCounting>
{

    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    @Override
    public SPacketDownCounting onMessage(SPacketDownCounting message, MessageContext ctx)
    {
        ctx.getServerHandler().player.getServer().addScheduledTask(()->
        {
            Variable variable = Variable.Instance();
            variable.downloadCounting += 1;
            for(EntityPlayerMP player :  ctx.getServerHandler().player.getServer().getPlayerList().getPlayers())
            {
                Packet.networkWrapper.sendTo(new CPacketDownloadCounting(variable.downloadCounting), player);
            }
            if(variable.downloadCounting >= Variable.getMainGame().getGamePlayerName().size() && Variable.getMainGame().gamePhase == MainGame.eGamePhase.PLAYER_HEAD_DOWNLOADING)
            {
                Variable.getMainGame().gamePhase = MainGame.eGamePhase.PLAYER_SEQUENCE;
                Variable.getMainGame().playerSequence(ctx.getServerHandler().player.getServer());
                for(EntityPlayerMP player : ctx.getServerHandler().player.getServer().getPlayerList().getPlayers())
                {
                    Packet.networkWrapper.sendTo(new CPacketGamePhaseSync(Variable.getMainGame().gamePhase.name()), player);
                }
            }

        });


        return null;
    }
}
