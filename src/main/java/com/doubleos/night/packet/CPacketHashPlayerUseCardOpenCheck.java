package com.doubleos.night.packet;

import com.doubleos.night.variable.MainGame;
import com.doubleos.night.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketHashPlayerUseCardOpenCheck implements IMessage, IMessageHandler<CPacketHashPlayerUseCardOpenCheck, CPacketHashPlayerUseCardOpenCheck>
{

    String playerName = "";

    int cardId = 0;


    public CPacketHashPlayerUseCardOpenCheck(){}

    public CPacketHashPlayerUseCardOpenCheck(String playerName, int cardId)
    {
        this.playerName = playerName;
        this.cardId = cardId;
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        playerName = ByteBufUtils.readUTF8String(buf);
        cardId = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, playerName);
        buf.writeInt(cardId);
    }

    @Override
    public CPacketHashPlayerUseCardOpenCheck onMessage(CPacketHashPlayerUseCardOpenCheck message, MessageContext ctx)
    {
        MainGame game = Variable.getMainGame();
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            game.getHashPlayerUseCardOpenCheck().put(message.playerName, message.cardId);
        });
        return null;
    }
}
