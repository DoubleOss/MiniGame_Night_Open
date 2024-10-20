package com.doubleos.night.packet;

import com.doubleos.night.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketFacingSync implements IMessage, IMessageHandler<CPacketFacingSync, CPacketFacingSync>
{

    String name = "";
    String facing = "";
    public CPacketFacingSync()
    {

    }
    public CPacketFacingSync(String name, String facing)
    {
        this.name = name;
        this.facing = facing;

    }


    @Override
    public void fromBytes(ByteBuf buf) {
        name = ByteBufUtils.readUTF8String(buf);
        facing = ByteBufUtils.readUTF8String(buf);


    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, name);
        ByteBufUtils.writeUTF8String(buf, facing);

    }

    @Override
    public CPacketFacingSync onMessage(CPacketFacingSync message, MessageContext ctx) {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            Variable.Instance().turnFacing = message.facing;
            Variable.Instance().startName = message.name;
        });
        return null;
    }
}
