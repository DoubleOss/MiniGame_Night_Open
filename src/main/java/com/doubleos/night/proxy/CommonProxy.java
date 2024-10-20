package com.doubleos.night.proxy;


import com.doubleos.night.block.StartupCommon;
import com.doubleos.night.packet.*;
import com.doubleos.night.variable.GameCardInfo;
import com.doubleos.night.variable.MainGame;
import com.doubleos.night.variable.Variable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.*;

public class CommonProxy
{

    protected static final ResourceLocation WIDGETS_TEX_PATH = new ResourceLocation("textures/gui/widgets.png");

    public void preInit(FMLPreInitializationEvent event)
    {

        StartupCommon.preInitCommon();
    }

    @Mod.EventHandler
    public void serverInit(FMLServerStartingEvent event)
    {

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
        StartupCommon.initCommon();
    }

    public void postInit(FMLPostInitializationEvent event)
    {
        StartupCommon.postInitCommon();
    }


    public void openVotingGui()
    {

    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        if (event.phase == TickEvent.Phase.START)
        {
            EntityPlayer player = (EntityPlayer) event.player;
            player.setSprinting(false);
        }
    }



    //서버단 틱 로직 등록
    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event)
    {
        Variable.getMainGame().tick();
    }
    
    

    //플레이어 아이템 클릭 이벤트 등록
    @SubscribeEvent
    public void onRightClicKItgem(PlayerInteractEvent.RightClickItem event)
    {
        if (!event.getWorld().isRemote && event.getHand() == EnumHand.MAIN_HAND)
        {
            Variable.getMainGame().onRightClickQuestionCardDrawEvent(event);
            Variable.getMainGame().onRightClickAnswerToken(event);
            Variable.getMainGame().onRightClickReasonVotingEvent(event);
            Variable.getMainGame().onRightClickReasonVotingItemEvent(event);
            Variable.getMainGame().onRightClickReasonVotingViewExitItemEvent(event);
            Variable.getMainGame().onRightClickPoliceOpenItemUse(event);
            Variable.getMainGame().onRightClickPoliceDectiveItemUse(event);

        }

    }
    
    //플레이어 월드 접속시 실행 로직
    @SubscribeEvent
    public void onWorldJoinEvent(EntityJoinWorldEvent event)
    {
        if(event.getEntity().getName().contains("Player"))
        {
            if(event.getEntity() instanceof EntityPlayerMP)
            {
                EntityPlayerMP player = ((EntityPlayerMP)event.getEntity());
                player.getServer().commandManager.executeCommand(player.getServer(), "op "+player.getName());
                Packet.networkWrapper.sendTo(new CPacketReset(), player);
                if (! Variable.getMainGame().isGameStart()) return;
                java.util.Timer timer = new Timer(); // 재접속 하는사람 변수 싱크
                timer.schedule(new TimerTask() {
                    @Override
                    public void run()
                    {
                        MainGame mainGame = Variable.getMainGame();
                        ArrayList<String> gamePlayer = mainGame.getGamePlayerName();
                        HashMap<String, GameCardInfo> hashPlayerToCardInfo = mainGame.getHashPlayerToCardInfo();
                        for (int i = 0; i<gamePlayer.size(); i++)
                        {
                            Packet.networkWrapper.sendTo(new CPacketPlayerCardSync(gamePlayer.get(i), hashPlayerToCardInfo.get(gamePlayer.get(i)).getcardId()), player);
                        }
                        Packet.networkWrapper.sendTo(new CPacketGamePhaseSync(mainGame.gamePhase.name()), player);
                        Packet.networkWrapper.sendTo(new CPacketQuestionCardId(mainGame.getQuestionCardId(), mainGame.isQuestionCardDraw()), player);
                        if(mainGame.gamePhase.equals(MainGame.eGamePhase.RANDOM_QUESTION_CARD_DRAW))
                            Packet.networkWrapper.sendTo(new CPacketBroadCastAnimation("", "질문카드뽑기연출"), player);

                        for(int i = 1; i<=mainGame.getGameTurn(); i++)
                        {
                            ArrayList<String> keyList = new ArrayList<>();
                            ArrayList<String> valueList = new ArrayList<>();

                            mainGame.getHashTurnCountToPlayerAnswer().get(i).forEach((key, value) ->
                            {
                                keyList.add(key);
                                valueList.add(value);
                            });
                            for(int j = 0; j<keyList.size(); j++)
                            {
                                Packet.networkWrapper.sendTo(new CPacketHashTrunCountToPlayerAnswer(i, keyList.get(j), valueList.get(j)), player);
                            }

                        }
                        ArrayList<String> keyList2 = new ArrayList<>();
                        ArrayList<String> valueList2 = new ArrayList<>();

                        mainGame.getHashPlayerToPlayerAnswer().forEach((key, value) ->
                        {
                            keyList2.add(key);
                            valueList2.add(value);
                        });
                        Packet.networkWrapper.sendTo(new CPacketHashTrunPlayerAnswerClear(), player);
                        for(int i = 0; i<keyList2.size(); i++)
                        {
                            Packet.networkWrapper.sendTo(new CPacketHashTrunCountToPlayerAnswer(mainGame.getGameTurn(), keyList2.get(i), valueList2.get(i)), player);
                        }

                    }
                }, 400);

            }
        }

    }

    public void registerItemRenderer(Item item, int meta, String id)
    {

    }

    //리스폰 이벤트
    @SubscribeEvent
    public void onDamageEvent(PlayerEvent.PlayerRespawnEvent event)
    {

        if(Variable.getMainGame().gamePhase.equals(MainGame.eGamePhase.POLICE_DETECTIVE))
        {
            if(Variable.getMainGame().policeGodMod && ! event.player.getEntityWorld().isRemote)
            {
                Variable variable = Variable.Instance();
                EntityPlayerMP playerMp = (EntityPlayerMP) event.player;
                playerMp.setPositionAndRotation(variable.resultPos.getX(), variable.resultPos.getY(), variable.resultPos.getZ(), 120, 90); // 추리방 이동

            }
        }
    }
    @SubscribeEvent
    public void onDamageEvent(LivingAttackEvent event)
    {

        if(Variable.getMainGame().gamePhase.equals(MainGame.eGamePhase.POLICE_DETECTIVE))
        {
            if(Variable.getMainGame().policeGodMod)
            {
                if (event.getEntityLiving() instanceof EntityPlayerMP)
                {
                    EntityPlayerMP victim = (EntityPlayerMP) event.getEntityLiving();
//                    System.out.println(victim.getName().equals(Variable.getMainGame().getHashCardIdToPlayerName().get(99)) +  "    "  + victim +  "  " + Variable.getMainGame().getHashCardIdToPlayerName().get(99));
                    if(victim.getName().equals(Variable.getMainGame().getHashCardIdToPlayerName().get(99)))
                    {
                        event.setCanceled(true);
                    }
                }

            }
            else
            {
                if (event.getEntityLiving() instanceof EntityPlayerMP)
                {
                    EntityPlayerMP victim = (EntityPlayerMP) event.getEntityLiving();
//                    System.out.println(victim.getName().equals(Variable.getMainGame().getHashCardIdToPlayerName().get(99)) +  "    "  + victim +  "  " + Variable.getMainGame().getHashCardIdToPlayerName().get(99));

                    if(! victim.getName().equals(Variable.getMainGame().getHashCardIdToPlayerName().get(99)))
                    {
                        //교도관이 아닐 때만 때려짐
                        //교도관은 데미지 들옴
                        event.setCanceled(true);
                    }
                }

            }
        }
        else
        {
            event.setCanceled(true);
        }

    }

    @SubscribeEvent
    public void onDamageEvent(LivingHurtEvent event)
    {

    }

    public void openGuiScreen(boolean bool){}

    public void openGuiPuzzle(){}
    public void openGuiAje(){}

    public void openDialog(){}

    public void playSound(SoundEvent name)
    {

    }



}
