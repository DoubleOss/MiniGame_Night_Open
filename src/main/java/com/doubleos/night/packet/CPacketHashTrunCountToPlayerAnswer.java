package com.doubleos.night.packet;

import com.doubleos.night.variable.Variable;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.HashMap;

public class CPacketHashTrunCountToPlayerAnswer implements IMessage, IMessageHandler<CPacketHashTrunCountToPlayerAnswer, CPacketHashTrunCountToPlayerAnswer>
{



    int turnCount = 0;
    String playerName = "";
    String answer = "";

    public CPacketHashTrunCountToPlayerAnswer()
    {

    }

    public CPacketHashTrunCountToPlayerAnswer(int turnCount, String playerName, String answer) {
        this.turnCount = turnCount;
        this.playerName = playerName;
        this.answer = answer;
    }


    @Override
    public void fromBytes(ByteBuf buf)
    {
        turnCount = buf.readInt();
        playerName = ByteBufUtils.readUTF8String(buf);
        answer = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(turnCount);
        ByteBufUtils.writeUTF8String(buf, playerName);
        ByteBufUtils.writeUTF8String(buf, answer);

    }

    @Override
    public CPacketHashTrunCountToPlayerAnswer onMessage(CPacketHashTrunCountToPlayerAnswer message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            Variable.getMainGame().setGameTurn(message.turnCount);
            Variable.getMainGame().getHashPlayerToPlayerAnswer().put(message.playerName, message.answer);
            HashMap<String, String> hash = new HashMap<>(Variable.getMainGame().getHashPlayerToPlayerAnswer());
            Variable.getMainGame().getHashTurnCountToPlayerAnswer().put(message.turnCount, hash);
            Variable.Instance().viewDisplay = true;

        });
        return null;
    }
}
