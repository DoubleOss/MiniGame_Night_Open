package com.doubleos.night.packet;

import com.doubleos.night.variable.Variable;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.nio.charset.Charset;

@SideOnly(Side.CLIENT)
public class ServerToClient
{

    public void onPacket(FMLNetworkEvent.ClientCustomPacketEvent event)
    {

        Variable variable = Variable.Instance();
        FMLProxyPacket packet = event.getPacket();
        Minecraft minecraft = Minecraft.getMinecraft();
        if (event.getPacket().channel().equals("KimJinWoo"))
        {
            String packetData = new String(ByteBufUtil.getBytes(packet.payload()), Charset.forName("UTF-8"));
            String[] list = packetData.split("-");;

            System.out.println(packetData.toString());
            //앱 열기
            //modconnet night viewActive true/false
            if (list[0].equalsIgnoreCase("night"))
            {
                if(list[1].equalsIgnoreCase("viewActive"))
                {
                    Boolean active = Boolean.parseBoolean(list[2]);
                }
            }
            //modconnet2 OpenGui Trade bool
            else if (list[0].equalsIgnoreCase(""))
            {
                if (list[1].equalsIgnoreCase("Trade"))
                {
                    if (list[2].equalsIgnoreCase("true"))
                    {
                      //  Minecraft.getMinecraft().addScheduledTask(() -> Minecraft.getMinecraft().displayGuiScreen(new Trade(true)));
                    }
                    else
                    {
                     //   Minecraft.getMinecraft().addScheduledTask(() -> Minecraft.getMinecraft().displayGuiScreen(new Trade(false)));
                    }


                }
            }
            //modconnet2 Variable [Money, Bottle, Jewlry, TradeCount1,2,3] value
            else if (list[0].equalsIgnoreCase("Variable"))
            {

                String ting = list[1];
                int value = Integer.parseInt(list[2]);
                if (ting.equalsIgnoreCase("Money"))
                {

                }
                else if (ting.equalsIgnoreCase("Bottle"))
                {

                }
                else if (ting.equalsIgnoreCase("Jewlry"))
                {

                }
                else if (ting.contains("TradeCount"))
                {

                }
                else if (ting.equalsIgnoreCase("Score"))
                {

                }
            }
            //modconnet2 Render [Active, Add] bool/playername itemId
            else if (list[0].equalsIgnoreCase("Render"))
            {
                if(list[1].equalsIgnoreCase("Active"))
                {

                }
                else if (list[1].equalsIgnoreCase("Add"))
                {

                }
            }
            else if (list[0].equalsIgnoreCase("Arrest"))
            {


            }
            else if(list[0].equalsIgnoreCase("View"))
            {

            }


        }
    }
}
