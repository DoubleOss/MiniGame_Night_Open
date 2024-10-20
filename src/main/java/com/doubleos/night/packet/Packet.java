package com.doubleos.night.packet;


import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class Packet
{
    public static SimpleNetworkWrapper networkWrapper;

    public static void init()
    {
        int packetId = 0;
        networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel("kimjin");


        networkWrapper.registerMessage(CGangsterPlayerListSet.class, CGangsterPlayerListSet.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketImageDownloading.class, CPacketImageDownloading.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketImageListClear.class, CPacketImageListClear.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketReset.class, CPacketReset.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketDownloadCounting.class, CPacketDownloadCounting.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketGamePhaseSync.class, CPacketGamePhaseSync.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketPlayerCardSync.class, CPacketPlayerCardSync.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketQuestionCardId.class, CPacketQuestionCardId.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketBroadCastAnimation.class, CPacketBroadCastAnimation.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketHashTrunCountToPlayerAnswer.class, CPacketHashTrunCountToPlayerAnswer.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketHashPlayerClear.class, CPacketHashPlayerClear.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketHashTrunPlayerAnswerClear.class, CPacketHashTrunPlayerAnswerClear.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketUserCardIdListClear.class, CPacketUserCardIdListClear.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketUserCardIdAdd.class, CPacketUserCardIdAdd.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketReasonPositionClear.class, CPacketReasonPositionClear.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketVotingGuiOpen.class, CPacketVotingGuiOpen.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketReasonVotingExit.class, CPacketReasonVotingExit.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketPlayerCount.class, CPacketPlayerCount.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketFacingSync.class, CPacketFacingSync.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketGamePlayerMcNumber.class, CPacketGamePlayerMcNumber.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketGamePlayerSet.class, CPacketGamePlayerSet.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketReasonPositionAdd.class, CPacketReasonPositionAdd.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketVotingOpen.class, CPacketVotingOpen.class, packetId++ ,Side.CLIENT);
        networkWrapper.registerMessage(CPacketVotingSync.class, CPacketVotingSync.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketPlayerVotingPositionSync.class, CPacketPlayerVotingPositionSync.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketPlayerVotingClear.class, CPacketPlayerVotingClear.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketBlockTileCardSync.class, CPacketBlockTileCardSync.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketFadeEffectAdd.class, CPacketFadeEffectAdd.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketHashPlayerGameExit.class, CPacketHashPlayerGameExit.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketUseCardSync.class, CPacketUseCardSync.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketUseCardAdd.class, CPacketUseCardAdd.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketGameTrunSync.class, CPacketGameTrunSync.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketHashPlayerUseCardOpenCheck.class, CPacketHashPlayerUseCardOpenCheck.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketPoliceDectiveCardId.class, CPacketPoliceDectiveCardId.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketDummyIdSync.class, CPacketDummyIdSync.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketDummyActive.class, CPacketDummyActive.class, packetId++, Side.CLIENT);
        networkWrapper.registerMessage(CPacketPlaySound.class, CPacketPlaySound.class, packetId++, Side.CLIENT);




        networkWrapper.registerMessage(SPacketDownCounting.class, SPacketDownCounting.class, packetId++, Side.SERVER);
        networkWrapper.registerMessage(SPacketVotingCount.class, SPacketVotingCount.class, packetId++, Side.SERVER);


    }



    @SubscribeEvent
    public void ReciveToPacket(FMLNetworkEvent.ClientCustomPacketEvent event)
    {

        /*
        Variable variable = Variable.Instance();
        FMLProxyPacket packet = event.getPacket();
        Minecraft minecraft = Minecraft.getMinecraft();
        if (event.getPacket().channel().equals("HalfMod"))
        {
            String packetData = new String(ByteBufUtil.getBytes(packet.payload()), Charset.forName("UTF-8"));
            String[] list = packetData.split("_");

            if(list[0].equalsIgnoreCase("Money"))
            {
                String test = list[1].replace("$", "");
                test = test.replace(",", "");
                //minecraft.player.sendChatMessage(test);
                float money = Float.parseFloat(test);
                money += 0f;
                Variable.Instance().setMoney(money);
            }
            else if(list[0].equalsIgnoreCase("View"))
            {
                String dialogName = list[1];
                int minDialogPage = Integer.parseInt(list[2]);
                int maxDialogPage = Integer.parseInt(list[3]);

                variable.setCurrentDialogPage(1);

                variable.setMaxDialogPage(maxDialogPage);
                variable.setMinDialogPage(minDialogPage);
                variable.setDialogName(dialogName);

                Minecraft.getMinecraft().addScheduledTask(() -> Minecraft.getMinecraft().displayGuiScreen(new Dialog()));
            }

            else if(list[0].equalsIgnoreCase("Btn"))
            {
                Boolean gameExitBtn = Boolean.parseBoolean(list[1]);

                //variable.setGameExitBtn(gameExitBtn.booleanValue());

            }


        }

         */
    }

}
