package com.doubleos.night.packet;

import com.doubleos.night.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CPacketDownloadCounting implements IMessage, IMessageHandler<CPacketDownloadCounting, CPacketDownloadCounting>
{

    public CPacketDownloadCounting(){}


    int count = 0;


    public CPacketDownloadCounting(int count)
    {
        this.count = count;
    }



    @Override
    public void fromBytes(ByteBuf buf)
    {
        count = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(count);
    }

    @Override
    public CPacketDownloadCounting onMessage(CPacketDownloadCounting message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(()->
        {
            Variable.Instance().downloadCounting = message.count;
        });
        return null;
    }
}
