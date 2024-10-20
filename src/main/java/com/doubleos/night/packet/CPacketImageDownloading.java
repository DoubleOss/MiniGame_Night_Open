package com.doubleos.night.packet;

import com.doubleos.night.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketImageDownloading implements IMessage, IMessageHandler<CPacketImageDownloading, CPacketImageDownloading>
{

    public CPacketImageDownloading() {
    }

    public CPacketImageDownloading(boolean downloading) {
        this.downloading = downloading;
    }

    boolean downloading = false;

    @Override
    public void fromBytes(ByteBuf buf)
    {
        downloading = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeBoolean(downloading);
    }

    @Override
    public CPacketImageDownloading onMessage(CPacketImageDownloading message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            Variable.Instance().imageDownloading = message.downloading;
        });
        return null;
    }
}
