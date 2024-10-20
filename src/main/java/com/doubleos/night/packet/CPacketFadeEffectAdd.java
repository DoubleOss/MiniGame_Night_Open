package com.doubleos.night.packet;


import com.doubleos.night.util.AnimationState;
import com.doubleos.night.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketFadeEffectAdd implements IMessage, IMessageHandler<CPacketFadeEffectAdd, CPacketFadeEffectAdd>
{


    float m_animationTime = 1;
    float m_animationDelay = 1;
    String m_result = "X";
    String m_playerName = "";


    public CPacketFadeEffectAdd(){}
    public CPacketFadeEffectAdd(float animationTime, float animationDelay, String result, String playerName)
    {
        m_animationTime = animationTime;
        m_animationDelay = animationDelay;
        m_result = result;
        m_playerName = playerName;
    }
    @Override
    public void fromBytes(ByteBuf buf)
    {

        m_animationTime = buf.readFloat();
        m_animationDelay = buf.readFloat();
        m_result = ByteBufUtils.readUTF8String(buf);
        m_playerName = ByteBufUtils.readUTF8String(buf);


    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeFloat(m_animationTime);
        buf.writeFloat(m_animationDelay);
        ByteBufUtils.writeUTF8String(buf, m_result);
        ByteBufUtils.writeUTF8String(buf, m_playerName);
    }

    @Override
    public CPacketFadeEffectAdd onMessage(CPacketFadeEffectAdd message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            Variable variable = Variable.Instance();
            AnimationState state = new AnimationState(message.m_animationTime, message.m_animationDelay);
            state.m_String = message.m_result;
            state.m_Player = message.m_playerName;

            if(variable.m_animationStateList.size() == 0)
                variable.m_animationStateList.add(state);
            else {
                for(int i = 0; i<variable.m_animationStateList.size(); i++)
                {
                    AnimationState checkState = variable.m_animationStateList.get(i);

                    if(i == variable.m_animationStateList.size())
                    {
                        variable.m_animationStateList.add(state);
                    }

                }
            }

        });

        return null;
    }
}
