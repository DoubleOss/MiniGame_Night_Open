package com.doubleos.night.packet;


import com.doubleos.night.util.AnimationBroadCast;
import com.doubleos.night.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketBroadCastAnimation implements IMessage, IMessageHandler<CPacketBroadCastAnimation, CPacketBroadCastAnimation>
{

    String m_string = "";
    String m_type = "";

    public CPacketBroadCastAnimation(){}

    public CPacketBroadCastAnimation(String brodcastMessage, String type)
    {
        m_string = brodcastMessage;
        m_type = type;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        m_string = ByteBufUtils.readUTF8String(buf);
        m_type = ByteBufUtils.readUTF8String(buf);

    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeUTF8String(buf, m_string);
        ByteBufUtils.writeUTF8String(buf, m_type);
    }

    @Override
    public CPacketBroadCastAnimation onMessage(CPacketBroadCastAnimation message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            Variable variable = Variable.Instance();
            variable.m_gameAnimationDirectList.add(new AnimationBroadCast(message.m_string, message.m_type));
            variable.votingCountView = false;
        });

        return null;
    }
}
