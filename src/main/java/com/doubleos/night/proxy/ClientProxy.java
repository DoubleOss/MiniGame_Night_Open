package com.doubleos.night.proxy;



import com.doubleos.night.Night;
import com.doubleos.night.Render.FruitRenderTile;
import com.doubleos.night.block.StartupClientOnly;
import com.doubleos.night.block.tile.BlockTileCardView;
import com.doubleos.night.gui.GuiVoting;
import com.doubleos.night.handler.SoundHandler;
import com.doubleos.night.util.*;
import com.doubleos.night.variable.GameCardInfo;
import com.doubleos.night.variable.GameQuestionCard;
import com.doubleos.night.variable.MainGame;
import com.doubleos.night.variable.Variable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClientProxy extends CommonProxy
{


    Minecraft minecraft = Minecraft.getMinecraft();

    ScaledResolution scale = new ScaledResolution(minecraft);
    protected static final ResourceLocation WIDGETS_TEX_PATH = new ResourceLocation("textures/gui/widgets.png");

    public static KeyBinding m_miniGameKey;
    public static KeyBinding m_animationScoreGuiKey;
    public static KeyBinding m_mapViewKey;


    public boolean m_keyInputDelay = true;

    public boolean m_SoundDelay = false;

    public int m_soundDelay = 0;


    PositionedSoundRecord positionedsoundrecord;


    public boolean m_areaViewActive = false;
    public boolean m_areaCloseActive = false;

    public boolean m_spaceClick = false;

    public String m_viewAreaName = "";

    int angle = 0;
    Variable variable = Variable.Instance();




    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event)
    {
        if(m_miniGameKey.isPressed())
        {
            if(variable.viewDisplay)
                variable.viewDisplay = false;
            else
                variable.viewDisplay = true;
        }

    }
    @SubscribeEvent
    public void onRenderHandEvent(RenderHandEvent event)
    {

    }
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event)
    {

    }

//    @Override
//    public void openGuiPuzzle() {
//        minecraft.displayGuiScreen(new GuiMiniGame());
//    }
//
//    @Override
//    public void openGuiAje() {
//        minecraft.displayGuiScreen(new GuiAJe());
//    }

    void initMiniGame()
    {


    }
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event)
    {


    }

//    public void openGuiScreen(boolean bool)
//    {
//        Minecraft.getMinecraft().addScheduledTask(() -> Minecraft.getMinecraft().displayGuiScreen(new Dialog(1, 3)));
//    }
    @Override
    public void openDialog()
    {
        //Minecraft.getMinecraft().addScheduledTask(() -> Minecraft.getMinecraft().displayGuiScreen(new Dialog()));
    }

    ISound m_sfxISound;


    @Override
    public void playSound(SoundEvent event)
    {
        Variable variable = Variable.Instance();
        if(m_sfxISound != null) {
            minecraft.getSoundHandler().stopSound(m_sfxISound);
        }

        m_sfxISound = Sound.getSound(event, SoundCategory.RECORDS, 1);
        minecraft.getSoundHandler().playSound(m_sfxISound);


    }

    @SubscribeEvent
    public void onRenderLiving(RenderLivingEvent.Specials.Pre event)
    {
//        if(minecraft.player !=  null)
//        {
//            if(!minecraft.player.isCreative())
//                event.setCanceled(true);
//        }

    }

    @Override
    public void postInit(FMLPostInitializationEvent event)
    {
        super.postInit(event);
        StartupClientOnly.postInitClientOnly();
    }

    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);
        OBJLoader.INSTANCE.addDomain(Reference.MODID);
        //RenderingRegistry.registerEntityRenderingHandler(EntityRope.class, RenderRope::new);

        StartupClientOnly.preInitClientOnly();

//        RenderingRegistry.registerEntityRenderingHandler(AirDrop.class, RenderAirDrop::new);

        File modFolder = new File(Minecraft.getMinecraft().mcDataDir, "resource/gamenight");
        modFolder.mkdirs();


        List<IResourcePack> defaultResourcePacks = ObfuscationReflectionHelper.getPrivateValue(FMLClientHandler.class, FMLClientHandler.instance(), "resourcePackList");
        IResourcePack pack = new ModResourcePack(modFolder);
        defaultResourcePacks.add(pack);
        ((SimpleReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).reloadResourcePack(pack);

        ClientRegistry.bindTileEntitySpecialRenderer(BlockTileCardView.class, new FruitRenderTile());

    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);
        MinecraftForge.EVENT_BUS.register(this);
        ClientRegistry.registerKeyBinding(m_miniGameKey = new KeyBinding("미니게임 키", 36, "죄수들의 밤"));
        StartupClientOnly.postInitClientOnly();

    }

    protected void renderHotbarItem(int p_184044_1_, int p_184044_2_, float p_184044_3_, EntityPlayer player, ItemStack stack)
    {
        if (!stack.isEmpty())
        {
            float f = (float)stack.getAnimationsToGo() - p_184044_3_;
            if (f > 0.0F)
            {
                GlStateManager.pushMatrix();
                float f1 = 1.0F + f / 5.0F;
                GlStateManager.translate((float)(p_184044_1_ + 8), (float)(p_184044_2_ + 12), 0.0F);
                GlStateManager.scale(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
                GlStateManager.translate((float)(-(p_184044_1_ + 8)), (float)(-(p_184044_2_ + 12)), 0.0F);
            }

            minecraft.getRenderItem().renderItemAndEffectIntoGUI(player, stack, p_184044_1_, p_184044_2_);

            if (f > 0.0F)
            {
                GlStateManager.popMatrix();
            }

            GlStateManager.pushMatrix();
            GlStateManager.translate(0, 0, 1);
            minecraft.getRenderItem().renderItemOverlays(minecraft.fontRenderer, stack, p_184044_1_, p_184044_2_);
            GlStateManager.popMatrix();
        }
    }
    @Override
    public void openVotingGui()
    {
        minecraft.displayGuiScreen(new GuiVoting());
    }

    @SubscribeEvent
    public void onBlockDraw(DrawBlockHighlightEvent event)
    {



    }
    @SubscribeEvent
    public void onRenderHud(RenderGameOverlayEvent event)
    {

        float widthPer =  minecraft.displayWidth / 1920f;
        float heightPer =  minecraft.displayHeight / 1080f;

        Variable variable = Variable.Instance();

        ScaledResolution scaled = new ScaledResolution(minecraft);

        float scaleWidth = (float)scaled.getScaledWidth_double();
        float scaleHeight = (float)scaled.getScaledHeight_double();

        int scaleFactor = scaled.getScaleFactor();

        float fpsCurrection = (120f / Minecraft.getDebugFPS()) * 1f;
        float partialTick = event.getPartialTicks();
        String playerName = minecraft.player.getName();
        MainGame mainGame = Variable.getMainGame();
        if(event.getType().equals(RenderGameOverlayEvent.ElementType.CHAT))
        {
            event.setCanceled(true);
            GlStateManager.pushMatrix();
            {
                GlStateManager.translate(2, scaleHeight-15, 3);
                GlStateManager.scale(0.5, 0.5f, 1f);
                minecraft.ingameGUI.getChatGUI().drawChat(minecraft.ingameGUI.getUpdateCounter());
            }
            GlStateManager.popMatrix();


        }

        if(event.getType().equals(RenderGameOverlayEvent.ElementType.ALL))
        {
            float broadCastWidth = 860f/3f;
            float broadCastHeight = 368f/3f;



            if(variable.m_gameAnimationDirectList.size() > 0) //공지창
            {
                Variable.Instance().viewDisplay = true;
                AnimationBroadCast animation = variable.m_gameAnimationDirectList.get(0);
                if(animation.m_type.equals("역할카드뽑기연출"))
                {
                    float boradCastYpos = 100f;
                    if(!animation.m_animationShow)
                    {
                        animation.m_animationShow = true;
                        animation.m_animationPlay = true;
                    }
                    else
                    {
                        float aniValue = 0f;
                        float animationAlpha = 100f;

                        float check = 0f;

                        if(animation.m_animationOpen)
                        {
                            animation.m_currentAnimationFrame += partialTick * 0.8f * fpsCurrection;
                            animation.m_curreentAniYPosPer += partialTick * 0.8f * fpsCurrection;
                            aniValue = easeInOutSine(((animation.m_curreentAniYPosPer)) * 0.01f);
                            animationAlpha = easeOutCubic(animation.m_currentAnimationFrame * 0.01f);

                        }
                        else if (animation.m_animationDelay)
                        {
                            animation.m_curreentAniYPosPer = 100;
                            aniValue = easeInOutSine(animation.m_curreentAniYPosPer * 0.01f);
                            animation.m_currentAnimationFrame += partialTick * 0.5f * fpsCurrection;
                        }
                        else if(animation.m_animationClose)
                        {
                            animation.m_currentAnimationFrame += partialTick * 0.8f * fpsCurrection;
                            animation.m_curreentAniYPosPer -= partialTick* 0.8f * fpsCurrection;
                            animationAlpha = easeOutCubic((100f - animation.m_currentAnimationFrame) * 0.01f);
                            aniValue = easeOutCubic(((animation.m_curreentAniYPosPer)) * 0.01f);
                        }
                        float currentFrame = animation.m_currentAnimationFrame;
                        if(animation.m_maxAnimationFrame <= animation.m_currentAnimationFrame)
                        {
                            animation.m_currentAnimationFrame = 0;
                            if(animation.m_animationOpen)
                            {
                                animation.m_animationOpen = false;
                                animation.m_animationDelay = true;

                            }
                            else if(animation.m_animationDelay)
                            {
                                animation.m_animationDelay = false;
                                animation.m_animationClose = true;

                            }
                            else if(animation.m_animationClose)
                            {

                                animation.m_animationClose = false;
                                variable.m_gameAnimationDirectList.remove(0);
                                return;
                            }

                        }
                        if(variable.m_gameAnimationDirectList.get(0) != null)
                        {

                            if(animation.m_animationDelay)
                                check = animation.m_currentAnimationFrame >= 50f ? 1f : animation.m_currentAnimationFrame * 0.01f;
                            else
                                check = 0;
                            float cardWidth = 754f/12f;
                            float cardHeight = 1080f/12f;
                            if(mainGame.getHashPlayerToCardInfo().get(playerName) != null)
                            {
                                int cardId = mainGame.getHashPlayerToCardInfo().get(playerName).getcardId();
                                if(animation.m_animationOpen || animation.m_animationDelay &&  animation.m_currentAnimationFrame < 50)
                                    minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\card\\카드뒷면.png"));
                                else if(animation.m_animationDelay && animation.m_currentAnimationFrame >= 50 || animation.m_animationClose)
                                    minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\card\\"+cardId+".png"));
                                Render.drawRotateTextureY(360 - 360 * check, scaleWidth/2 - cardWidth/2, -50 + boradCastYpos * (aniValue), cardWidth, cardHeight, 0, 0, 1, 1, 1, 1);

                            }
                            else
                            {
                                minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\card\\카드뒷면.png"));
                                Render.drawRotateTextureY(360 - 360 * check, scaleWidth/2 - cardWidth/2, -50 + boradCastYpos * (aniValue), cardWidth, cardHeight, 0, 0, 1, 1, 1, 1);

                            }


                        }
                    }
                }
                else if(animation.m_type.equals("질문카드뽑기연출")) //질문카드뽑기연출
                {
                    if(!animation.m_animationShow)
                    {
                        animation.m_animationShow = true;
                        animation.m_animationPlay = true;
                    }
                    else
                    {
                        float aniValue = 0f;
                        float animationAlpha = 100f;

                        float check = 0f;
                        float imageMulti = 1f;

                        if(animation.m_animationOpen) //초기 애니메이션 재생 상태
                        {
                            animation.m_currentAnimationFrame += partialTick * 0.85f * fpsCurrection;
                            animation.m_curreentAniYPosPer += partialTick * 0.85f * fpsCurrection;
                            aniValue = easeInOutSine(((animation.m_curreentAniYPosPer)) * 0.01f); //아래로 살짝 뽑는 느낌 로직
                            animationAlpha = easeOutCubic(animation.m_currentAnimationFrame * 0.01f);

                        }
                        else if (animation.m_animationDelay) //애니메이션 재생 후 딜레이 
                        {
                            animation.m_curreentAniYPosPer = 100;
                            aniValue = easeInOutSine(animation.m_currentAnimationFrame * 0.01f);
                            animation.m_currentAnimationFrame += partialTick * 1.2f * fpsCurrection;
                            //imageMulti = 1 + ( 1  * aniValue);
                        }
                        else if(animation.m_animationClose) //애니메이션 종료 시킬 경우
                        {
                            //imageMulti = 2f;
                            float speed = 0.8f;
                            if (animation.m_currentAnimationFrame >= 50)
                                speed = 0.2f;
                            animation.m_currentAnimationFrame += partialTick * speed * fpsCurrection;
                            animation.m_curreentAniYPosPer -= partialTick* speed * fpsCurrection;
                            animationAlpha = easeOutCubic((100f - animation.m_currentAnimationFrame) * 0.01f);
                            aniValue = easeOutCubic(((animation.m_curreentAniYPosPer)) * 0.01f);
                        }
                        float currentFrame = animation.m_currentAnimationFrame;
                        if(animation.m_maxAnimationFrame <= animation.m_currentAnimationFrame)
                        {
                            animation.m_currentAnimationFrame = 0;
                            if(animation.m_animationOpen)
                            {
                                animation.m_animationOpen = false;
                                animation.m_animationDelay = true;

                            }
                            else if(animation.m_animationDelay)
                            {
                                animation.m_animationDelay = false;
                                animation.m_animationClose = true;

                            }
                            else if(animation.m_animationClose)
                            {

                                animation.m_animationClose = false;
                                variable.m_gameAnimationDirectList.remove(0);
                                return;
                            }

                        }
                        if(variable.m_gameAnimationDirectList.get(0) != null)
                        {

                            float boradCastYpos = 10f;

                            //Rotate 애니메이션 로직
                            if(animation.m_animationClose)
                                check = animation.m_currentAnimationFrame >= 50f ? 1f : animation.m_currentAnimationFrame * 0.01f;
                            else
                                check = 0;
                            float cardWidth = 754f/12f * imageMulti;
                            float cardHeight = 1080/12f * imageMulti;

                            float placeCardPersonalWidth = 270f/4f * imageMulti;
                            float placeCardPersonalHeight = 381f/4f * imageMulti;


                            if(animation.m_animationOpen)
                            {
//                                minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\question\\questionback.png"));
//                                Render.drawTexture(scaleWidth/2 - cardWidth/2 + 165, scaleHeight/2-cardHeight/2 - 56 + boradCastYpos * (aniValue), cardWidth, cardHeight, 0, 0, 1, 1, 8, 1);

                                minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\question\\questionback.png"));
                                Render.drawTexture(scaleWidth - placeCardPersonalWidth - 6f, 9f + (boradCastYpos * (aniValue)), placeCardPersonalWidth, placeCardPersonalHeight, 0, 0, 1, 1, 10, 1);


                            }
                            else if(animation.m_animationDelay)
                            {
                                minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\question\\questionback.png"));
                                Render.drawTexture(scaleWidth - placeCardPersonalWidth - 6f, 19f, placeCardPersonalWidth, placeCardPersonalHeight, 0, 0, 1, 1, 20, 1);

                            }
                            else
                            {
                                check = animation.m_currentAnimationFrame >= 55f ? 1f : animation.m_currentAnimationFrame * 0.01f;
                                int questionCardId = mainGame.getQuestionCardId();
                                float checkValue = Render.easeInOutSine(check);
                                if(animation.m_currentAnimationFrame < 50)
                                    minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\question\\questionback.png"));
                                else
                                    minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\question\\q"+questionCardId+".png"));
                                Render.drawRotateTextureY(360 - 360 * checkValue, scaleWidth - placeCardPersonalWidth - 6f, 19, placeCardPersonalWidth, placeCardPersonalHeight, 0, 0, 1, 1, 10, 1);
                            }

                        }
                    }
                }
                else if(animation.m_type.equals("역할카드공개연출"))//역할카드공개
                {
                    if(!animation.m_animationShow)
                    {
                        animation.m_animationShow = true;
                        animation.m_animationPlay = true;
                    }
                    else
                    {
                        float aniValue = 0f;
                        float animationAlpha = 100f;

                        float check = 0f;
                        float imageMulti = 1f;

                        if(animation.m_animationOpen)
                        {
                            animation.m_currentAnimationFrame += partialTick * 0.85f * fpsCurrection;
                            animation.m_curreentAniYPosPer += partialTick * 0.85f * fpsCurrection;
                            aniValue = easeInOutSine(((animation.m_curreentAniYPosPer)) * 0.01f);
                            animationAlpha = easeOutCubic(animation.m_currentAnimationFrame * 0.01f);

                        }
                        else if (animation.m_animationDelay)
                        {
                            animation.m_curreentAniYPosPer = 100;
                            aniValue = easeInOutSine(animation.m_currentAnimationFrame * 0.01f);
                            animation.m_currentAnimationFrame += partialTick * 0.7f * fpsCurrection;
                            imageMulti = 1 + ( 1  * aniValue);
                        }
                        else if(animation.m_animationClose)
                        {
                            imageMulti = 2f;
                            float speed = 0.8f;
                            if (animation.m_currentAnimationFrame >= 50)
                                speed = 0.2f;
                            animation.m_currentAnimationFrame += partialTick * speed * fpsCurrection;
                            animation.m_curreentAniYPosPer -= partialTick* speed * fpsCurrection;
                            animationAlpha = easeOutCubic((100f - animation.m_currentAnimationFrame) * 0.01f);
                            aniValue = easeOutCubic(((animation.m_curreentAniYPosPer)) * 0.01f);
                        }
                        float currentFrame = animation.m_currentAnimationFrame;
                        if(animation.m_maxAnimationFrame <= animation.m_currentAnimationFrame)
                        {
                            animation.m_currentAnimationFrame = 0;
                            if(animation.m_animationOpen)
                            {
                                animation.m_animationOpen = false;
                                animation.m_animationDelay = true;

                            }
                            else if(animation.m_animationDelay)
                            {
                                animation.m_animationDelay = false;
                                animation.m_animationClose = true;
                            }
                            else if(animation.m_animationClose)
                            {

                                animation.m_animationClose = false;
                                variable.m_gameAnimationDirectList.remove(0);
                                return;
                            }

                        }
                        if(variable.m_gameAnimationDirectList.get(0) != null)
                        {

                            float hideCardWidth = 754f/20f;
                            float hideCardHeight = 1080f/20f;

                            if(animation.m_animationDelay)
                                check = animation.m_currentAnimationFrame >= 50f ? 1f : animation.m_currentAnimationFrame * 0.01f;
                            else
                                check = 0;

                            for(int i = 0; i<mainGame.getGamePlayerName().size(); i++)
                            {
                                if(animation.m_animationOpen || animation.m_animationDelay &&  animation.m_currentAnimationFrame < 50)
                                {
                                    minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\card\\카드뒷면.png"));
                                    Render.drawRotateTextureY(360 - 360 * check, scaleWidth/2 - hideCardWidth/2 + 80 - (38 * i), 2, hideCardWidth, hideCardHeight, 0, 0, 1, 1, 20, 1);

                                }

                                else if(animation.m_animationDelay && animation.m_currentAnimationFrame >= 50 || animation.m_animationClose)
                                {
                                    System.out.println(mainGame.getUseCardIdList().get(i));
                                    if(mainGame.getUseCardIdList().get(i) != 99)
                                    {
                                        minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\card\\"+mainGame.getUseCardIdList().get(i)+".png"));
                                        Render.drawRotateTextureY(360 - 360 * check, scaleWidth/2 - hideCardWidth/2 + 80 - (38 * i), 2, hideCardWidth, hideCardHeight, 0, 0, 1, 1, 20, 1);

                                    }
                                    else
                                    {
                                        int count = mainGame.getUseCardIdList().size()-1;
                                        minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\card\\"+mainGame.getUseCardIdList().get(count)+".png"));
                                        Render.drawRotateTextureY(360 - 360 * check, scaleWidth/2 - hideCardWidth/2 + 80 - (38 * i), 2, hideCardWidth, hideCardHeight, 0, 0, 1, 1, 20, 1);

                                    }

                                }

                            }

                        }
                    }
                }
            }
            else if(variable.m_animationStateList.size() > 0)
            {
                Variable.Instance().viewDisplay = true;
                drawLoading(scale, partialTick, fpsCurrection);
            }


        }
        if(event.getType().equals(RenderGameOverlayEvent.ElementType.HOTBAR) || event.getType().equals(RenderGameOverlayEvent.ElementType.FOOD)
                || event.getType().equals(RenderGameOverlayEvent.ElementType.HEALTH) || event.getType().equals(RenderGameOverlayEvent.ElementType.EXPERIENCE))
        {
            if(variable.m_animationStateList.size() > 0)
            {
                event.setCanceled(true);
            }
        }
        if(event.getType().equals(RenderGameOverlayEvent.ElementType.HOTBAR))
        {

            if (Variable.Instance().viewDisplay)
            {
                if(variable.m_animationStateList.size() > 0)
                    return;
                MainGame.eGamePhase gamePhase = Variable.getMainGame().gamePhase;
                if (Variable.getMainGame().gamePhase != MainGame.eGamePhase.PLAYER_HEAD_DOWNLOADING && (Variable.getMainGame().gamePhase != MainGame.eGamePhase.NONE))
                {

                    if (Variable.getMainGame().gamePhase == MainGame.eGamePhase.TRUN_REVERS_VIEW)
                    {

                    }
                    else if (mainGame.gamePhase == MainGame.eGamePhase.RANDOM_QUESTION_CARD_DRAW || mainGame.gamePhase == MainGame.eGamePhase.QUESTION_CARD_TIME
                            || mainGame.gamePhase == MainGame.eGamePhase.REASON_CIRME_VIEW || mainGame.gamePhase == MainGame.eGamePhase.REASON_VOTING_TIME ||
                            mainGame.gamePhase == MainGame.eGamePhase.REASON_VOTING_VIEW || mainGame.gamePhase ==  MainGame.eGamePhase.REASON_DIRECT_Y_N || mainGame.gamePhase == MainGame.eGamePhase.POLICE_DETECTIVE)
                    {

                        if(Variable.Instance().votingExitActive)
                        {
                            float backWidth = 643f/4f;
                            float backHeight = 912f/4f;

                            minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\gui\\backgorund.png"));
                            Render.drawTexture(scaleWidth/2f - backWidth/2f, scaleHeight/2-backHeight/2, backWidth, backHeight, 0, 0, 1, 1, 1, 1);

                            for(int j = 0; j<mainGame.getGamePlayerName().size(); j++)
                            {
                                for (int i = 0; i<variable.m_hashGetPngInfo.get("플레이어").size(); i++)
                                {
                                    ImageInfo info = variable.m_hashGetPngInfo.get("플레이어").get(i);
                                    if(info.m_Name.equals(mainGame.getGamePlayerName().get(j)))
                                    {
                                        int votingCount = mainGame.getHashPlayerVotingCount().getOrDefault(info.m_Name, 0);
                                        String exitCheck = mainGame.getHashPlayerGameExit().getOrDefault(info.m_Name, "");
                                        GlStateManager.pushMatrix();
                                        {
                                            if(exitCheck.equals(""))
                                                Render.drawSkinHead(info.m_info.getId(), scaleWidth/2 - 57, scaleHeight/2 - 79 + (32 * j), 4, 0.92f, 1, 1, 1);
                                            else
                                                Render.drawSkinHead(info.m_info.getId(), scaleWidth/2 - 57, scaleHeight/2 - 79 + (32 * j), 4, 0.92f, 0.3f, 0.3f, 0.3f);
                                            Render.drawStringScaleResizeByMiddleWidth(info.m_Name, scaleWidth/2 + 12, scaleHeight/2 - 70 + (32 * j), 5, 1, 1, false); //드롭쉐도우 뺀 버전으로 검은색 만들기
                                            Render.drawStringScaleResizeByLeftWidth(" §c" + votingCount +"§0표", scaleWidth/2 + 50, scaleHeight/2 - 70 + (32 * j), 5, 1, 1, false); //드롭쉐도우 뺀 버전으로 검은색 만들기


                                            minecraft.fontRenderer.drawString("테스트", scaleWidth/2, scaleHeight/2, -1 ,true);
                                        }
                                        GlStateManager.popMatrix();

                                    }
                                }
                            }
                        }
                        else
                        {


//                            minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\place_ex3.png"));
//                            Render.drawTexture(0f, 0, scaleWidth, scaleHeight, 0, 0, 1, 1, 1, 0.2f);

                            float backWidth = 922f/4f;
                            float backHeight = 922f/4f;

                            float questionCardWidth = 754f/24f;
                            float questionCardHeight = 1080f/24f;

                            float placeCardPersonalWidth = 270f/4f;
                            float placeCardPersonalHeight = 381f/4f;


                            minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\question\\qca_place.png"));
                            Render.drawTexture(scaleWidth - placeCardPersonalWidth - 6f, 9f, placeCardPersonalWidth, placeCardPersonalHeight, 0, 0, 1, 1, 10, 1);

                            minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\w_table.png"));
                            Render.drawTexture(scaleWidth/2 - backWidth/2, scaleHeight/2 - backHeight/2 + 10, backWidth, backHeight, 0, 0, 1, 1, 1, 1);


                            float hideCardWidth = 754f/20f;
                            float hideCardHeight = 1080f/20f;

                            minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\card\\99.png"));
                            Render.drawTexture(scaleWidth/2 - hideCardWidth/2 + 80 + 38, 2, hideCardWidth, hideCardHeight, 0, 0, 1, 1, 5, 1);
                            ArrayList<Integer> idList = new ArrayList<>();
                            for(int i = 0; i<mainGame.getGamePlayerName().size(); i++)
                            {
                                String playerName2 = mainGame.getGamePlayerName().get(i);
                                if(mainGame.getHashPlayerGameExit().getOrDefault(playerName2,"").equals("탈락"))
                                {
                                    idList.add(mainGame.getHashPlayerToCardInfo().get(mainGame.getGamePlayerName().get(i)).getcardId());
                                }

                            }
                            for(int i = 0; i<mainGame.getGamePlayerName().size(); i++)
                            {

                                if (mainGame.gamePhase != MainGame.eGamePhase.REASON_CIRME_VIEW && mainGame.gamePhase != MainGame.eGamePhase.REASON_VOTING_TIME && mainGame.gamePhase != MainGame.eGamePhase.REASON_VOTING_VIEW && mainGame.gamePhase != MainGame.eGamePhase.POLICE_DETECTIVE)
                                {

                                    minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\card\\카드뒷면.png"));
                                    Render.drawTexture(scaleWidth/2 - hideCardWidth/2 + 80 - (38 * i), 2, hideCardWidth, hideCardHeight, 0, 0, 1, 1, 5, 1);

                                }

                                else if(variable.m_gameAnimationDirectList.size() == 0)
                                {

                                    if(mainGame.getUseCardIdList().get(i) != 99)
                                    {

                                        minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\card\\"+mainGame.getUseCardIdList().get(i)+".png"));
                                        boolean check = false;
                                        boolean dummyCheck = false;
                                        for(Integer id : idList)
                                        {
                                            if(id == mainGame.getUseCardIdList().get(i))
                                                check = true;
                                        }


                                        if(mainGame.getUseCardIdList().get(i) == mainGame.getDummyCardId() && mainGame.isDummyCardSearch())
                                        {
                                            check = true;
                                            dummyCheck = true;

                                        }
                                        if(check)
                                        {
                                            Render.drawTexture(scaleWidth/2 - hideCardWidth/2 + 80 - (38 * i), 2, hideCardWidth, hideCardHeight, 0, 0, 1, 1, 5, 1, 0.4f, 0.4f, 0.4f);
//                                            ImageInfo info = variable.m_hashGetPngInfo.get("플레이어").get(i);
//                                            Render.drawSkinHead(info.m_info.getId(), scaleWidth/2 - hideCardWidth/2 + 91 - (38 * i), 46, 6, 0.5f, 1, 1, 1);
                                            String idToPlayerName = mainGame.getHashCardIdToPlayerName().getOrDefault( mainGame.getUseCardIdList().get(i), "");
                                            if (!dummyCheck)
                                            {
                                                for(ImageInfo info : variable.m_hashGetPngInfo.get("플레이어"))
                                                {
                                                    if(info.m_Name.equals(idToPlayerName))
                                                    {

                                                        //Render.drawSkinHead(info.m_info.getId(), scaleWidth/2 - hideCardWidth/2 + 80 - (38 * i), 46, 6, 0.5f, 1, 1, 1);
                                                        Render.drawSkinHead(info.m_info.getId(), scaleWidth/2 - hideCardWidth/2 + 91 - (38 * i), 46, 6, 0.5f, 1, 1, 1);
                                                    }
                                                }
                                            }



                                        }
                                        else
                                        {
                                            Render.drawTexture(scaleWidth/2 - hideCardWidth/2 + 80 - (38 * i), 2, hideCardWidth, hideCardHeight, 0, 0, 1, 1, 5, 1, 1f, 1f, 1f);
                                        }
                                        if(mainGame.getPoliceDectiveCardId() == mainGame.getUseCardIdList().get(i))
                                        {
                                            minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\card\\selectCard.png"));
                                            Render.drawTexture(scaleWidth/2 - hideCardWidth/2 + 80 - (38 * i), 2, hideCardWidth, hideCardHeight, 0, 0, 1, 1, 15, 1, 1f, 1f, 1f);
                                        }
                                    }
                                    else
                                    {
                                        int count = (mainGame.getUseCardIdList().size()-1);
                                        boolean check = false;
                                        boolean dummyCheck = false;
                                        for(Integer id : idList)
                                        {
                                            if(id == mainGame.getUseCardIdList().get(count))
                                                check = true;
                                        }
                                        //System.out.println("  " +  mainGame.getUseCardIdList().get(count) + " " +mainGame.getDummyCardId() + "  " + mainGame.isDummyCardSearch());
                                        if(mainGame.getUseCardIdList().get(count) == mainGame.getDummyCardId() && mainGame.isDummyCardSearch())
                                        {
                                            dummyCheck = true;
                                            check = true;

                                        }

                                        minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\card\\"+mainGame.getUseCardIdList().get(count)+".png"));
                                        if(check)
                                        {
                                            Render.drawTexture(scaleWidth/2 - hideCardWidth/2 + 80 - (38 * i), 2, hideCardWidth, hideCardHeight, 0, 0, 1, 1, 5, 1, 0.4f, 0.4f, 0.4f);
                                            //ImageInfo info = variable.m_hashGetPngInfo.get("플레이어").get(i);
                                            String idToPlayerName = mainGame.getHashCardIdToPlayerName().getOrDefault( mainGame.getUseCardIdList().get(count), "");
                                            if(!dummyCheck)
                                            {
                                                for(ImageInfo info : variable.m_hashGetPngInfo.get("플레이어"))
                                                {
                                                    if(info.m_Name.equals(idToPlayerName))
                                                    {
                                                        //Render.drawSkinHead(info.m_info.getId(), scaleWidth/2 - hideCardWidth/2 + 80 - (38 * i), 46, 6, 0.5f, 1, 1, 1);
                                                        Render.drawSkinHead(info.m_info.getId(), scaleWidth/2 - hideCardWidth/2 + 91 - (38 * i), 46, 6, 0.5f, 1, 1, 1);
                                                    }
                                                }

                                            }

                                        }
                                        else
                                            Render.drawTexture(scaleWidth/2 - hideCardWidth/2 + 80 - (38 * i), 2, hideCardWidth, hideCardHeight, 0, 0, 1, 1, 5, 1, 1f, 1f, 1f);
                                        if(mainGame.getPoliceDectiveCardId() == mainGame.getUseCardIdList().get(count))
                                        {
                                            minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\card\\selectCard.png"));
                                            Render.drawTexture(scaleWidth/2 - hideCardWidth/2 + 80 - (38 * i), 2, hideCardWidth, hideCardHeight, 0, 0, 1, 1, 5, 1, 1f, 1f, 1f);
                                        }
                                    }

                                }

                            }

                            float yesToken = 128f/8f;

                            //진행된 턴 만큼 반복문 진행
                            for(int j = 1; j<=mainGame.getGameTurn(); j++)
                            {
                                HashMap<String, String> map = mainGame.getHashTurnCountToPlayerAnswer().get(j);
                                if(map != null)
                                {
                                    float turnMiddlePos[] = new float[]{-40, 39, -0.5f, -40, 39};
                                    float turnMiddlePosY[] = new float[]{-50, -50, 9, 66.5f, 66.5f};

                                    int i = 0;
                                    //게임 참여중인 플레이어 수 만큼 반복 진행
                                    for(int b = 0; b<mainGame.getGamePlayerName().size(); b++)
                                    {
                                        String answerName = mainGame.getGamePlayerName().get(b);
                                        int yposCurrotion3 = i > 2 ? 1 : 0;
                                        int xPos3 = i > 3 ? (i - 3) % 3 : i %3;

                                        float xPoes3[] = new float[]{-25.5f, 24.5f, -25.5f, 24.5f, -25.5f, 24.5f};
                                        float yPoes3[] = new float[]{-15, -15 ,1, 1, 17.5f, 17.5f};

                                        if(map.get(answerName) != null)
                                        {
                                            //RGB 값에 따라 color값이 변경됨
                                            int red = 0, green = 0, blue = 0;
                                            //질문 시간이고 j[턴]이 현재턴이 아닐 경우
                                            if (j != mainGame.getGameTurn() && mainGame.gamePhase.equals(MainGame.eGamePhase.QUESTION_CARD_TIME))
                                            {
                                                red = 1;
                                                green = 1;
                                                blue = 1;
                                            }
                                            else if(! mainGame.gamePhase.equals(MainGame.eGamePhase.QUESTION_CARD_TIME))
                                            {
                                                red = 1;
                                                green = 1;
                                                blue = 1;
                                            }
                                            if(map.get(answerName).contains("YES"))
                                                minecraft.renderEngine.bindTexture(new ResourceLocation(Reference.MODID, "textures/items/yes.png"));
                                            else
                                                minecraft.renderEngine.bindTexture(new ResourceLocation(Reference.MODID, "textures/items/no.png"));

                                            Render.drawTexture(scaleWidth/2 - yesToken/2 + turnMiddlePos[j-1] + xPoes3[i], scaleHeight/2-yesToken/2 + turnMiddlePosY[j-1] + yPoes3[i], yesToken, yesToken, 0, 0, 1, 1, 5, 1, red, green, blue);
                                            i++;
                                        }

                                    }
                                }
                            }
                            float playerFrontWidth = 132f/4f;
                            float playerBacktWidth = 133f/4f;

                            for(int j = 0; j<mainGame.getGamePlayerName().size(); j++)
                            {
                                for (int i = 0; i<variable.m_hashGetPngInfo.get("플레이어").size(); i++)
                                {
                                    ImageInfo info = variable.m_hashGetPngInfo.get("플레이어").get(i);
                                    if(info.m_Name.equals(mainGame.getGamePlayerName().get(j)))
                                    {
                                        String exitCheck = mainGame.getHashPlayerGameExit().getOrDefault(info.m_Name, "");
                                        int xPoes[] = new  int[]{-130, 130, -148, 148, -130, 130};
                                        int yPoes[] = new  int[]{-57, -57, 10, 10, 76, 76};

                                        //System.out.println(j + "   pos " + xPos);
                                        GlStateManager.pushMatrix();
                                        {
                                            minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\player_back.png"));
                                            Render.drawTexture(scaleWidth/2 - playerBacktWidth/2f + xPoes[j], scaleHeight/2 - playerBacktWidth/2f +yPoes[j], playerBacktWidth, playerBacktWidth, 0, 0, 1, 1, 3, 1);

                                            if(exitCheck.equals(""))
                                                Render.drawSkinHead(info.m_info.getId(), scaleWidth/2 - 29/2f + xPoes[j], scaleHeight/2 - 29/2f +yPoes[j], 4, 1, 1, 1, 1);
                                            else
                                                Render.drawSkinHead(info.m_info.getId(), scaleWidth/2 - 29/2f + xPoes[j], scaleHeight/2 - 29/2f +yPoes[j], 4, 1, 0.3f, 0.3f, 0.3f);

                                            Render.drawStringScaleResizeByMiddleWidth(mainGame.getGamePlayerName().get(j), scaleWidth/2 - 29/2f + xPoes[j] + 25, scaleHeight/2 - 29/2f +yPoes[j] + 35, 12, 0.65f, -1);
                                            minecraft.fontRenderer.drawString("테스트", scaleWidth/2, scaleHeight/2, -1 ,true);

//                                            minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\player_front.png"));
//                                            Render.drawTexture(scaleWidth/2 - playerFrontWidth/2f + xPoes[j], scaleHeight/2 - playerFrontWidth/2f +yPoes[j], playerFrontWidth, playerFrontWidth, 0, 0, 1, 1, 3, 1);


                                        }
                                        GlStateManager.popMatrix();

                                    }
                                }
                            }


                            if (mainGame.gamePhase != MainGame.eGamePhase.RANDOM_QUESTION_CARD_DRAW && mainGame.gamePhase != MainGame.eGamePhase.REASON_CIRME_VIEW && mainGame.gamePhase != MainGame.eGamePhase.REASON_VOTING_VIEW && mainGame.gamePhase != MainGame.eGamePhase.REASON_VOTING_TIME && mainGame.gamePhase != MainGame.eGamePhase.POLICE_DETECTIVE)
                            {
                                float meetLWidth = 170f/4f;
                                float meetLHeight = 133f/4f;
                                int xPoes2[] = new  int[]{-135, 135, -153, 152, -135, 135};
                                int yPoes2[] = new  int[]{-57, -57, 10, 10, 76, 76};
                                String lr = (mainGame.getGamePlayerMcNumber()+1) % 2 == 0 ? "R" : "L";

                                minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\present_"+lr+".png"));
                                Render.drawTexture(scaleWidth/2 - meetLWidth/2f + xPoes2[mainGame.getGamePlayerMcNumber()], scaleHeight/2 - meetLHeight/2f +yPoes2[mainGame.getGamePlayerMcNumber()], meetLWidth, meetLHeight, 0, 0, 1, 1, 11, 1, 1f, 1f, 1f);

                                if( !(mainGame.getGamePlayerMcNumber()+1 >= mainGame.getGamePlayerName().size()))
                                {
                                    String lr2 = (mainGame.getGamePlayerMcNumber()+2) % 2 == 0 ? "R" : "L";
                                    minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\next_"+lr2+".png"));
                                    Render.drawTexture(scaleWidth/2 - meetLWidth/2f + xPoes2[mainGame.getGamePlayerMcNumber()+1], scaleHeight/2 - meetLHeight/2f +yPoes2[mainGame.getGamePlayerMcNumber()+1], meetLWidth, meetLHeight, 0, 0, 1, 1, 11, 1, 1f, 1f, 1f);

                                }

                            }
                            else if(mainGame.gamePhase == MainGame.eGamePhase.REASON_CIRME_VIEW)
                            {


                                float meetLWidth = 170f/4f;
                                float meetLHeight = 133f/4f;

                                for(int b = 0; b<mainGame.getGamePlayerName().size(); b++)
                                {
                                    int xPoes2[] = new  int[]{-135, 135, -153, 152, -135, 135};
                                    int yPoes2[] = new  int[]{-57, -57, 10, 10, 76, 76};
                                    String answerName = mainGame.getGamePlayerName().get(b);
                                    String lr = (b+1) % 2 == 0 ? "R" : "L";
                                    if(mainGame.getHashReasonPosition().get(answerName) != null)
                                    {
                                        if (mainGame.getHashReasonPosition().get(answerName).equals("종료"))
                                        {

                                            minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\meet_"+lr+".png"));
                                            Render.drawTexture(scaleWidth/2 - meetLWidth/2f + xPoes2[b], scaleHeight/2 - meetLHeight/2f +yPoes2[b], meetLWidth, meetLHeight, 0, 0, 1, 1, 11, 1, 1f, 1f, 1f);

                                        }
                                        else
                                        {
                                            minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\meet_"+lr+".png"));
                                            Render.drawTexture(scaleWidth/2 - meetLWidth/2f + xPoes2[b], scaleHeight/2 - meetLHeight/2f +yPoes2[b], meetLWidth, meetLHeight, 0, 0, 1, 1, 11, 1, 0.5f, 0.5f, 0.5f);
                                        }
                                    }
                                    else
                                    {
                                        minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\meet_"+lr+".png"));
                                        Render.drawTexture(scaleWidth/2 - meetLWidth/2f + xPoes2[b], scaleHeight/2 - meetLHeight/2f +yPoes2[b], meetLWidth, meetLHeight, 0, 0, 1, 1, 11, 1, 0.5f, 0.5f, 0.5f);

                                    }

                                }
                            }
                            else if(mainGame.gamePhase == MainGame.eGamePhase.REASON_VOTING_TIME)
                            {
                                float meetLWidth = 170f/4f;
                                float meetLHeight = 132f/4f;
                                for(int b = 0; b<mainGame.getGamePlayerName().size(); b++)
                                {
                                    int xPoes2[] = new  int[]{-135, 135, -153, 152, -135, 135};
                                    int yPoes2[] = new  int[]{-57, -57, 10, 10, 76, 76};
                                    String answerName = mainGame.getGamePlayerName().get(b);
                                    String lr = (b+1) % 2 == 0 ? "R" : "L";
                                    if(mainGame.getHashPlayerVotingCheck().get(answerName) != null)
                                    {

                                        if (mainGame.getHashPlayerVotingCheck().get(answerName).equals("완료"))
                                        {
                                            minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\vote_"+lr+".png"));
                                            Render.drawTexture(scaleWidth/2 - meetLWidth/2f + xPoes2[b], scaleHeight/2 - meetLHeight/2f +yPoes2[b], meetLWidth, meetLHeight, 0, 0, 1, 1, 11, 1, 1f, 1f, 1f);
                                        }
                                        else
                                        {
                                            minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\vote_"+lr+".png"));
                                            Render.drawTexture(scaleWidth/2 - meetLWidth/2f + xPoes2[b], scaleHeight/2 - meetLHeight/2f +yPoes2[b], meetLWidth, meetLHeight, 0, 0, 1, 1, 11, 1, 0.5f, 0.5f, 0.5f);
                                        }
                                    }
                                    else
                                    {
                                        minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\vote_"+lr+".png"));
                                        Render.drawTexture(scaleWidth/2 - meetLWidth/2f + xPoes2[b], scaleHeight/2 - meetLHeight/2f +yPoes2[b], meetLWidth, meetLHeight, 0, 0, 1, 1, 11, 1, 0.5f, 0.5f, 0.5f);

                                    }

                                }
                            }

                            int[] questionXPos = new int[]{-41, 39, -1, -41, 39};
                            int[] questionYPos = new int[]{49, 49, -10, -68, -68};

                            if( ! mainGame.getViewQuestionCardList().isEmpty())
                            {
                                for (int i = 0; i < mainGame.getViewQuestionCardList().size(); i++)
                                {
                                    GameQuestionCard info2 = mainGame.getViewQuestionCardList().get(i);
                                    if(variable.m_gameAnimationDirectList.size() != 0)
                                    {
                                        if(!variable.m_gameAnimationDirectList.get(0).m_type.equals("역할카드공개연출"))
                                        {
                                            if((i+1) != mainGame.getGameTurn())
                                            {
                                                minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\question\\q"+info2.getCardId()+".png"));
                                                Render.drawTexture(scaleWidth/2 - questionCardWidth/2 + questionXPos[i], scaleHeight/2-questionCardHeight/2 - questionYPos[i], questionCardWidth, questionCardHeight, 0, 0, 1, 1, 3, 1);

                                            }
                                        }
                                        else
                                        {
                                            minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\question\\q"+info2.getCardId()+".png"));
                                            Render.drawTexture(scaleWidth/2 - questionCardWidth/2 + questionXPos[i], scaleHeight/2-questionCardHeight/2 - questionYPos[i], questionCardWidth, questionCardHeight, 0, 0, 1, 1, 3, 1);

                                        }
                                    }
                                    else
                                    {
                                        minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\question\\q"+info2.getCardId()+".png"));
                                        Render.drawTexture(scaleWidth/2 - questionCardWidth/2 + questionXPos[i], scaleHeight/2-questionCardHeight/2 - questionYPos[i], questionCardWidth, questionCardHeight, 0, 0, 1, 1, 3, 1);

                                    }

                                }

                            }

                        }
                    }



                }

            }
            if (Variable.getMainGame().gamePhase == MainGame.eGamePhase.PLAYER_HEAD_DOWNLOADING)
            {
                String text = "리소스를 다운로드 중입니다 잠시만 기다려 주세요 " + variable.downloadCounting + " / " + variable.playerCount;
                Render.drawStringScaleResizeByMiddleWidth(text, scaleWidth/2, scaleHeight/2, 5, 1.2f, -1);
            }
            boolean nullCheck = false;
            int cardId = 0;
            if (mainGame.getHashPlayerToCardInfo().get(playerName) == null)
                nullCheck = true;
            else
                cardId = mainGame.getHashPlayerToCardInfo().get(playerName).getcardId();

            float cardWidth = 754f/12f;
            float cardHeight = 1080f/12f;

            float cardtecBackWidth = 270f/4.1f;
            float cardtecBackHeight = 380f/4.1f;

            minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\place_my.png"));
            Render.drawTexture(scaleWidth - cardtecBackWidth - 3.5f, scaleHeight - cardtecBackHeight - 3.5f, cardtecBackWidth, cardtecBackHeight, 0, 0, 1, 1, 10, 1);
            if(variable.m_gameAnimationDirectList.size() > 0 && variable.m_gameAnimationDirectList.get(0).m_type.equals("역할카드뽑기연출") || nullCheck)
                minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\card\\카드뒷면.png"));
            else
                minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\card\\"+cardId+".png"));
            Render.drawTexture(scaleWidth - cardWidth - 5, scaleHeight - cardHeight - 5, cardWidth, cardHeight, 0, 0, 1, 1, 10, 1);



        }


    }


    void drawLoading(ScaledResolution scale,  float partialTick, float fpsCurrection)
    {
        MainGame mainGame = Variable.getMainGame();

        float widthPer =  minecraft.displayWidth / 1920f;
        float heightPer =  minecraft.displayHeight / 1080f;

        float scaleWidth = (float) scale.getScaledWidth_double() * widthPer;
        float scaleHeight = (float) scale.getScaledHeight_double() * heightPer;


        if(variable.m_animationStateList.size() != 0)
        {
            AnimationState aniState = variable.m_animationStateList.get(0);

            if(aniState.m_String.equals("O") || aniState.m_String.equals("X"))
            {
                if(aniState.m_animationOpen)
                {
                    if(aniState.m_currentAnimationFrame <= aniState.m_maxAnimationFrame)
                    {
                        aniState.m_currentAnimationFrame += partialTick * fpsCurrection * aniState.m_AnimationFrameTime;

                    }
                    else
                    {
                        //System.out.println("페이드 인 종료 및 딜레이 시작 " + LocalTime.now());
                        aniState.m_animationOpen = false;
                        aniState.m_animationDelay = true;

                    }
                }
                else if(!aniState.m_animationOpen && aniState.m_animationDelay)
                {
                    if(aniState.m_currentAnimationDelay < aniState.m_maxAnimationDelay)
                    {
                        aniState.m_currentAnimationDelay += partialTick * fpsCurrection * aniState.m_AnimationDelayTime;
                    }
                    else
                    {
                        aniState.m_animationDelay = false;
                        aniState.m_animationClose = true;
                        //System.out.println("페이드 딜레이 종료  클로즈 시작" + LocalTime.now());

                    }
                }
                else if(!aniState.m_animationOpen && !aniState.m_animationDelay && aniState.m_animationClose)
                {
                    if(aniState.m_currentAnimationFrame > 0)
                    {
                        aniState.m_currentAnimationFrame -= partialTick * fpsCurrection  * aniState.m_AnimationFrameTime;
                    }
                    else
                    {
                        aniState.m_currentAnimationFrame = 0f;
                        aniState.m_animationClose = false;
                        variable.m_animationStateList.remove(0);
                        ///System.out.println("페이드 클로즈 종료 " + LocalTime.now());
                    }
                }
                float alpha = aniState.m_currentAnimationFrame * 0.01f;
                float cardAlpha1 = aniState.m_animationDelay && aniState.m_currentAnimationDelay <= 15f ? (aniState.m_currentAnimationDelay / 15f) : 1f;
                float cardAlpha2 =  aniState.m_animationDelay && aniState.m_currentAnimationDelay <= 25f ? (aniState.m_currentAnimationDelay / 25f) : 1f;
                float cardAlpha3 =  aniState.m_animationDelay && aniState.m_currentAnimationDelay <= 35f ? (aniState.m_currentAnimationDelay / 35f) : 1f;
                float cardAlpha4 =  aniState.m_animationDelay && aniState.m_currentAnimationDelay <= 45f ? (aniState.m_currentAnimationDelay / 45f) : 1f;
                float cardAlpha5 =  aniState.m_animationDelay && aniState.m_currentAnimationDelay <= 55f ? (aniState.m_currentAnimationDelay / 55f) : 1f;

                float questionCardWidth = 754f/14f;
                float questionCardHeight = 1080f/14f;

                if(aniState.m_currentAnimationDelay >= 70f)
                {
                    cardAlpha1 = 0f;
                    cardAlpha2 = 0f;
                    cardAlpha3 = 0f;
                    cardAlpha4 = 0f;
                    cardAlpha5 = 0f;

                }
                int testNumber = 2;
                if (aniState.m_animationDelay)
                    alpha = 1;
                String text = "";

                GlStateManager.pushMatrix();
                {

                    if(aniState.m_animationDelay )
                    {
                        ImageInfo info = null;
                        int number = 0;
                        for(int i = 0; i<variable.m_hashGetPngInfo.get("플레이어").size(); i++)
                        {
                            if(variable.m_hashGetPngInfo.get("플레이어").get(i).m_Name.equals(aniState.m_Player))
                            {
                                number = i;
                                info = variable.m_hashGetPngInfo.get("플레이어").get(i);
                            }
                        }

                        GlStateManager.pushMatrix();
                        {
                            GlStateManager.bindTexture(info.m_standInfo.id);
                            float infoWidth = info.m_standInfo.getWidth() / 3f;
                            float infoHeight = info.m_standInfo.getHeight() / 3f;

                            //System.out.println("Width  = " + scaleWidth  +  "   Height = " + scaleHeight  + "  widthPer " +  widthPer + "  Height" +  heightPer);
                            Render.drawTexture((scaleWidth/2f - infoWidth/2f), scaleHeight/2 - infoHeight/2f, infoWidth, infoHeight, 0, 0, 1, 1, 55, 1);


                        }
                        GlStateManager.popMatrix();
                        if (true)
                        {
                            minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\결과연출.png"));
                            drawTexture(0, 0, scaleWidth*2,scaleHeight*2, 0, 0, 1, 1, 50, alpha);
                            EntityPlayer selectPlayer = minecraft.world.getPlayerEntityByName(aniState.m_Player);
                            //Render.drawEntityOnScreen((int)(scaleHeight/2) + 105, (int)(scaleHeight/2) + 160, 130, 0, 0, selectPlayer);
                            if(mainGame.getGameTurn() >= 1)
                            {
                                HashMap<String, String> map = mainGame.getHashTurnCountToPlayerAnswer().get(1);
                                String image = map.get(aniState.m_Player).contains("YES") ? "O" : "X";
                                GameQuestionCard info2 = mainGame.getViewQuestionCardList().get(0);
                                minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\question\\q"+info2.getCardId()+".png"));
                                Render.drawTexture(scaleWidth/2 - questionCardWidth/2 - 100, scaleHeight/2-questionCardHeight/2 - 76, questionCardWidth, questionCardHeight, 0, 0, 1, 1, 60, cardAlpha1);
                                minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\"+image+".png"));
                                Render.drawTexture(scaleWidth/2 - questionCardWidth/2 - 118 + 46/2f, scaleHeight/2-questionCardHeight/2 - 68 + 46/6f, 46, 46, 0, 0, 1, 1, 60, cardAlpha1 * 0.5f);

                            }
                            if(mainGame.getGameTurn() >= 2)
                            {
                                HashMap<String, String> map = mainGame.getHashTurnCountToPlayerAnswer().get(2);
                                String image = map.get(aniState.m_Player).contains("YES") ? "O" : "X";
                                GameQuestionCard info2 = mainGame.getViewQuestionCardList().get(1);
                                minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\question\\q"+info2.getCardId()+".png"));
                                Render.drawTexture(scaleWidth/2 - questionCardWidth/2 + 130, scaleHeight/2-questionCardHeight/2 - 76, questionCardWidth, questionCardHeight, 0, 0, 1, 1, 60, cardAlpha2);
                                minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\"+image+".png"));
                                Render.drawTexture(scaleWidth/2 - questionCardWidth/2 + 111+ 46/2f, scaleHeight/2-questionCardHeight/2 - 68+ 46/6f, 46, 46, 0, 0, 1, 1, 60, cardAlpha2  * 0.5f);

                            }
                            if(mainGame.getGameTurn() >= 3)
                            {
                                HashMap<String, String> map = mainGame.getHashTurnCountToPlayerAnswer().get(3);
                                String image = map.get(aniState.m_Player).contains("YES") ? "O" : "X";
                                GameQuestionCard info2 = mainGame.getViewQuestionCardList().get(2);
                                minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\question\\q"+info2.getCardId()+".png"));
                                Render.drawTexture(scaleWidth/2 - questionCardWidth/2 - 150, scaleHeight/2-questionCardHeight/2 + 10, questionCardWidth, questionCardHeight, 0, 0, 1, 1, 60, cardAlpha3);
                                minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\"+image+".png"));
                                Render.drawTexture(scaleWidth/2 - questionCardWidth/2 - 169 + 46/2f, scaleHeight/2-questionCardHeight/2 + 19 + 46/6f, 46, 46, 0, 0, 1, 1, 60, cardAlpha3  * 0.5f);

                            }
                            if(mainGame.getGameTurn() >= 4)
                            {
                                HashMap<String, String> map = mainGame.getHashTurnCountToPlayerAnswer().get(4);
                                String image = map.get(aniState.m_Player).contains("YES") ? "O" : "X";
                                GameQuestionCard info2 = mainGame.getViewQuestionCardList().get(3);
                                minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\question\\q"+info2.getCardId()+".png"));
                                Render.drawTexture(scaleWidth/2 - questionCardWidth/2 + 150, scaleHeight/2-questionCardHeight/2 + 10, questionCardWidth, questionCardHeight, 0, 0, 1, 1, 60, cardAlpha4);
                                minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\"+image+".png"));
                                Render.drawTexture(scaleWidth/2 - questionCardWidth/2 + 131 + 46/2f, scaleHeight/2-questionCardHeight/2 + 19 + 46/6f, 46, 46, 0, 0, 1, 1, 60, cardAlpha4  * 0.5f);

                            }
                            if(mainGame.getGameTurn() >= 5)
                            {
                                HashMap<String, String> map = mainGame.getHashTurnCountToPlayerAnswer().get(5);
                                String image = map.get(aniState.m_Player).contains("YES") ? "O" : "X";
                                GameQuestionCard info2 = mainGame.getViewQuestionCardList().get(4);
                                minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\question\\q"+info2.getCardId()+".png"));
                                Render.drawTexture(scaleWidth/2 - questionCardWidth/2, scaleHeight/2-questionCardHeight/2 + 92, questionCardWidth, questionCardHeight, 0, 0, 1, 1, 60, cardAlpha5);
                                minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\"+image+".png"));
                                Render.drawTexture(scaleWidth/2 - questionCardWidth/2 + 46/2f -19, scaleHeight/2-questionCardHeight/2 + 92+ 46/6f, 46, 46, 0, 0, 1, 1, 60, cardAlpha5  * 0.5f);

                            }

                            GlStateManager.pushMatrix();
                            {
                                if(!rotateMul)
                                {
                                    if(rotateZ >= 6f)
                                    {
                                        rotateMul = true;
                                    }
                                    rotateZ += partialTick * 0.02f;
                                }
                                else
                                {
                                    if(rotateZ <= -6f)
                                    {
                                        rotateMul = false;
                                    }
                                    rotateZ -= partialTick * 0.02f;
                                }



                                GlStateManager.rotate(rotateZ, 0, 0, 1);
                                minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\1.png"));
                                Render.drawTexture(scaleWidth/2 - 447/1.5f/2f - 4f, -50, 447f/1.5f, 709/1.5f, 0, 0, 1, 1, 53, 1f);
                            }
                            GlStateManager.popMatrix();

                        }
                        if(aniState.m_currentAnimationDelay >= 70f)
                        {
                            text = aniState.m_Player + " 은 ";
                            if(aniState.m_currentAnimationDelay >= 80f)
                                text = aniState.m_Player + " 은 교도관이";
                            if(aniState.m_currentAnimationDelay >= 90f)
                            {
                                if(aniState.m_String.equals("O"))
                                {
                                    text = aniState.m_Player + " 은 교도관이 §b맞습니다";
                                }
                                else
                                {
                                    text = aniState.m_Player + " 은 교도관이 §4아니었습니다.";
                                }
                            }

                        }
                        int fontSize = minecraft.fontRenderer.getStringWidth(text);
                        Render.drawStringScaleResizeByMiddleWidth(text, scaleWidth/2- fontSize/2, scaleHeight/2 + 70, 80, 2f, -1, true);

                    }
                    else if(aniState.m_animationClose)
                    {

                    }

                }
                GlStateManager.popMatrix();
            }
            else //  de_O
            {
                if(aniState.m_animationOpen)
                {
                    if(aniState.m_currentAnimationFrame <= aniState.m_maxAnimationFrame)
                    {
                        aniState.m_currentAnimationFrame += partialTick * fpsCurrection * aniState.m_AnimationFrameTime * 2f;
                    }
                    else
                    {
                        //System.out.println("페이드 인 종료 및 딜레이 시작 " + LocalTime.now());
                        aniState.m_animationOpen = false;
                        aniState.m_animationDelay = true;
                    }
                }
                else if(!aniState.m_animationOpen && aniState.m_animationDelay)
                {
                    if(aniState.m_currentAnimationDelay < aniState.m_maxAnimationDelay)
                    {
                        aniState.m_currentAnimationDelay += partialTick * fpsCurrection * aniState.m_AnimationDelayTime;
                    }
                    else
                    {
                        aniState.m_animationDelay = false;
                        aniState.m_animationClose = true;
                        //System.out.println("페이드 딜레이 종료  클로즈 시작" + LocalTime.now());

                    }
                }
                else if(!aniState.m_animationOpen && !aniState.m_animationDelay && aniState.m_animationClose)
                {
                    if(aniState.m_currentAnimationFrame > 0)
                    {
                        aniState.m_currentAnimationFrame -= partialTick * fpsCurrection  * aniState.m_AnimationFrameTime;
                    }
                    else
                    {
                        aniState.m_currentAnimationFrame = 0f;
                        aniState.m_animationClose = false;
                        variable.m_animationStateList.remove(0);
                    }
                }
                String text = "";
                String job = "";
                GameCardInfo info1 = mainGame.getHashPlayerToCardInfo().get(aniState.m_Player);
                GameCardInfo searchInfo = variable.getHashIdToCard().get(Variable.getMainGame().getPoliceDectiveCardId());
                if(aniState.m_currentAnimationDelay >= 20f)
                {
                    text = aniState.m_Player + " 의 직업은 ";
                    if(aniState.m_currentAnimationDelay >= 50f)
                        text = aniState.m_Player + " 의 직업은 " + searchInfo.getCardName() + "";
                    if(aniState.m_currentAnimationDelay >= 70f)
                    {
                        if(aniState.m_String.contains("O"))
                        {
                            text = aniState.m_Player + " 의 직업은 " + searchInfo.getCardName() + "" + " §b맞습니다";
                        }
                        else
                        {
                            text = aniState.m_Player + " 의 직업은 " + searchInfo.getCardName() + " " + " §4아닙니다";
                        }
                    }

                }
                int fontSize = minecraft.fontRenderer.getStringWidth(text);
                Render.drawStringScaleResizeByMiddleWidth(text, scaleWidth/2- fontSize/2, scaleHeight/2 + 65, 80, 2f, -1, true);
            }

        }

    }

    float rotateZ = 0f;
    boolean rotateMul = false;
    public float easeInOutBack(float x)
    {
        float c1 = 1.70158f;
        float c2 = c1 * 1.525f;

        return (float) (x < 0.5 ? (Math.pow(2 * x, 2) * ((c2 + 1) * 2 * x - c2)) / 2 : (Math.pow(2 * x - 2, 2) * ((c2 + 1) * (x * 2 - 2) + c2) + 2) / 2f);
    }

    public float easeOutCubic(float x)
    {
        return (float)(1 - Math.pow(1 - x, 3));
    }
    public float easeInOutSine(float x)
    {
        return (float)-(Math.cos(Math.PI * x) - 1) / 2f;
    }

    public static int RGB_TO_DECIMAL(int r, int g, int b)
    {
        int result = (r << 16) + (g << 8) + b;

        return result;
    }




    public void registerCustomModel(Item item, int meta)
    {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), "inventory"));


    }

    public void registerItemRenderer(Item item, int meta, String id)
    {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));

    }
    public void drawStringScaleResizeByRightWidth(String text, float x, float y, float depth, float scale, int color)
    {
        ScaledResolution scaled = new ScaledResolution(minecraft);

        int stringSize = minecraft.fontRenderer.getStringWidth(text);

        GlStateManager.pushMatrix();
        {
            GlStateManager.scale(scale, scale, scale);
            GlStateManager.translate(0f, 0f, depth);
            minecraft.fontRenderer.drawString(text, (x - stringSize)/scale, (y)/scale, color, false);
        }
        GlStateManager.popMatrix();
    }
    public void drawStringScaleResizeByMiddleWidth(String text, float x, float y, float depth, float scale, int color)
    {
        ScaledResolution scaled = new ScaledResolution(minecraft);

        int stringSize = minecraft.fontRenderer.getStringWidth(text);

        GlStateManager.pushMatrix();
        {
            GlStateManager.scale(scale, scale, scale);
            GlStateManager.translate(0f, 0f, depth);
            minecraft.fontRenderer.drawString(text, (x - stringSize/2f)/scale, (y)/scale, color, false);
        }
        GlStateManager.popMatrix();
    }
    public void drawStringScaleResizeByLeftWidth(String text, float x, float y, float depth, float scale, int color)
    {

        int stringSize = minecraft.fontRenderer.getStringWidth(text);

        GlStateManager.pushMatrix();
        {
            GlStateManager.scale(scale, scale, scale);
            GlStateManager.translate(0f, 0f, depth);
            minecraft.fontRenderer.drawString(text, (x)/scale, (y)/scale, color, false);
        }
        GlStateManager.popMatrix();
    }


    public void drawCricleProgressToQuad(float x, float y, float z, float textSizeX, float textSizeY, float per)
    {

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bb = tessellator.getBuffer();

        GlStateManager.enableBlend();
        GlStateManager.disableLighting();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        //GlStateManager.enableBlend();
        bb.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION_TEX);
        GlStateManager.pushMatrix();
        {

            bb.pos(x +textSizeX/2, y+textSizeY/2, z).tex(0.5f, 0.5f).endVertex();
            //bb.pos(x+(textSizeX*0.5), y, z).tex(0.5f, 0f).endVertex();
            if(per >= 87)
            {
                float b0 = (1-(100-per) / 13);
                bb.pos(x + (textSizeX/2)*b0, y, z).tex(0.5f*b0, 0f).endVertex();
            }
            if(per >= 62)
            {
                if(per > 62 && per <= 87)
                {
                    float b1 = (87-per) / 25;
                    bb.pos(x, (textSizeY * b1) + y, z).tex(0f, 1f* b1).endVertex();
                }
                else if(per > 87)
                {
                    bb.pos(x, y, z).tex(0f, 0f).endVertex();
                }
                bb.pos(x, y+textSizeY, z).tex(0f, 1f).endVertex();

            }
            if(per >= 38)
            {
                if(per > 38 && per <= 62)
                {
                    float b2 = (62-per) / 25;
                    bb.pos((textSizeX * b2) + x, y+textSizeY, z).tex(1f * b2, 1f).endVertex();
                }
                else if(per > 62)
                {
                    bb.pos(x, y+textSizeY, z).tex(0f, 1f).endVertex();
                }
                bb.pos(x+textSizeX, y+textSizeY, z).tex(1f, 1f).endVertex();
            }
            if(per >= 13)
            {
                if(per > 13 && per <= 38)
                {
                    float b3 = (1-((38-per) / 25));
                    bb.pos(x+textSizeX, y+textSizeY*b3, z).tex(1f, 1f*b3).endVertex();
                }
                else if(per > 38)
                {
                    //bb.pos(x+textSizeX, y+textSizeY, z).tex(1f, 1f).endVertex();
                }
                bb.pos(x+textSizeX, y, z).tex(1f, 0f).endVertex();
            }
            if(per < 13)
            {

                float b0 = (1-(13-per) / 13);
                bb.pos(x+textSizeX/2 + (textSizeX/2 * b0), y, z).tex(0.5f + (b0 * 0.5f), 0f).endVertex();
            }
            bb.pos(x+textSizeX/2, y, z).tex(0.5f, 0f).endVertex();

            //GlStateManager.disableBlend();
            tessellator.draw();
            GlStateManager.disableBlend();

        }
        GlStateManager.popMatrix();

    }

    public void drawTextureAutoResize(float x, float y, double xSize, double ySize, double u, double v, double uAfter, double vAfter, float z, float alpha)
    {
        Tessellator t = Tessellator.getInstance();
        BufferBuilder bb = t.getBuffer();
        float width = minecraft.displayWidth;
        float height = minecraft.displayHeight;
        GlStateManager.pushMatrix();
        {
            GlStateManager.enableBlend();
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
            GL11.glTranslated((width - height * 16.0 / 9) / 2, 0.0, 0.0);
            GL11.glScaled(height / 1080.0, height / 1080.0, height / 1080.0);
            bb.begin(7, DefaultVertexFormats.POSITION_TEX);
            bb.pos(x, y, z).tex(u, v).endVertex();
            bb.pos(x, y + ySize, z).tex(u, vAfter).endVertex();
            bb.pos(x + xSize, y + ySize, z).tex(uAfter, vAfter).endVertex();
            bb.pos(x + xSize, y, z).tex(uAfter, v).endVertex();
            t.draw();
            GlStateManager.disableBlend();
        }
        GlStateManager.popMatrix();
    }
    public void drawTexture(float x, float y, double xSize, double ySize, double u, double v, double uAfter, double vAfter, float z, float alpha)
    {
        Tessellator t = Tessellator.getInstance();
        BufferBuilder bb = t.getBuffer();
        GlStateManager.pushMatrix();
        {
            GlStateManager.enableBlend();
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
            bb.begin(7, DefaultVertexFormats.POSITION_TEX);
            bb.pos(x, y, z).tex(u, v).endVertex();
            bb.pos(x, y + ySize, z).tex(u, vAfter).endVertex();
            bb.pos(x + xSize, y + ySize, z).tex(uAfter, vAfter).endVertex();
            bb.pos(x + xSize, y, z).tex(uAfter, v).endVertex();
            t.draw();
            GlStateManager.disableBlend();
        }
        GlStateManager.popMatrix();
    }

    public void drawRotateTexture(float angle, float x, float y, double xSize, double ySize, double u, double v, double uAfter, double vAfter, float z, float alpha)
    {
        Tessellator t = Tessellator.getInstance();
        BufferBuilder bb = t.getBuffer();
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 200);
        GlStateManager.rotate(angle, 0.0f, 1.0f, 0.0f);
        GlStateManager.scale(0.2f, 0.2f, 0.2f);

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0.0f, 0.0f); GL11.glVertex2f(-100.0f, -100.0f);
        GL11.glTexCoord2f(0.0f, 1.0f); GL11.glVertex2f(-100.0f, 100.0f);
        GL11.glTexCoord2f(1.0f, 1.0f); GL11.glVertex2f(100.0f, 100.0f);
        GL11.glTexCoord2f(1.0f, 0.0f); GL11.glVertex2f(100.0f, -100.0f);
        GL11.glEnd();
        GlStateManager.popMatrix();




    }

    public void drawTexture(float x, float y, double xSize, double ySize, double u, double v, double uAfter, double vAfter, float z, float alpha, float r, float g, float b)
    {
        Tessellator t = Tessellator.getInstance();
        BufferBuilder bb = t.getBuffer();
        GlStateManager.pushMatrix();
        {
            GlStateManager.enableBlend();
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color(r, g, b, alpha);
            bb.begin(7, DefaultVertexFormats.POSITION_TEX);
            bb.pos(x, y, z).tex(u, v).endVertex();
            bb.pos(x, y + ySize, z).tex(u, vAfter).endVertex();
            bb.pos(x + xSize, y + ySize, z).tex(uAfter, vAfter).endVertex();
            bb.pos(x + xSize, y, z).tex(uAfter, v).endVertex();
            t.draw();
            GlStateManager.disableBlend();
        }
        GlStateManager.popMatrix();
    }

    private void drawSkinHead(float x, float y, float z, double scale)
    {
        GlStateManager.pushMatrix();
        this.minecraft.renderEngine.bindTexture(this.minecraft.player.getLocationSkin());


        GlStateManager.scale(scale, scale, z);
        x = x / (float)scale;
        y = y / (float)scale;
        drawTexture(x, y, 16f, 16d, 0.125d, 0.12d, 0.25d, 0.25d, z, 1f);
        GlStateManager.popMatrix();
    }

    private void drawSkinHead(int id, float x, float y, float z, double scale)
    {
        GlStateManager.pushMatrix();

        GL11.glBindTexture(3553, id);

        GlStateManager.scale(scale, scale, 1.0D);
        x = x / (float)scale;
        y = y / (float)scale;
        drawTexture(x, y, 64, 64, 0, 0, 1, 1, 1, 1);
        //drawTexture(x, y, 16f, 16d, 0.125d, 0.12d, 0.25d, 0.25d, 5f, 1f);
        GlStateManager.popMatrix();
    }

    private void drawSkinHead(float x, float y, float z, double scale, float rotate)
    {
        int yaw = (int) (minecraft.player.getPitchYaw().y % 360);

        if(yaw >= 360)
            yaw -= 360;
        else if (yaw <= 0)
            yaw += 360;
        GlStateManager.pushMatrix();
        this.minecraft.renderEngine.bindTexture(this.minecraft.player.getLocationSkin());
        GlStateManager.color(1, 1, 1);
        GlStateManager.scale(scale, scale, 1);
        x = x / (float)scale;
        y = y / (float)scale;
        drawRotateTexture2(yaw, x, y, 8f, 8f, 0.125d, 0.12d, 0.25d, 0.25d, 100, 1f);
        GlStateManager.popMatrix();
    }
    private void drawSkinHead(ResourceLocation loc, float x, float y, float z, double scale, float rotate)
    {

        GlStateManager.pushMatrix();
        this.minecraft.renderEngine.bindTexture(loc);
        GlStateManager.color(1, 1, 1);
        GlStateManager.scale(scale, scale, 1);
        x = x / (float)scale;
        y = y / (float)scale;
        drawRotateTexture2(rotate, x, y, 8f, 8f, 0.125d, 0.12d, 0.25d, 0.25d, 100, 1f);
        GlStateManager.popMatrix();
    }

    public void drawRotateTexture2(float angle, float x, float y, double xSize, double ySize, double u, double v, double uAfter, double vAfter, float z, float alpha)
    {
        Tessellator t = Tessellator.getInstance();
        BufferBuilder bb = t.getBuffer();
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.translate(x, y, 100);
        GlStateManager.rotate(angle, 0.0f, 0.0f, 1.0f);
        GlStateManager.scale(0.04f, 0.04f, 1f);

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2d(u, v); GL11.glVertex2f(-100.0f, -100.0f);
        GL11.glTexCoord2d(u, vAfter); GL11.glVertex2f(-100.0f, 100.0f);
        GL11.glTexCoord2d(uAfter, vAfter); GL11.glVertex2f(100.0f, 100.0f);
        GL11.glTexCoord2d(uAfter, v); GL11.glVertex2f(100.0f, -100.0f);
        GL11.glEnd();

        GlStateManager.disableBlend();
        GlStateManager.popMatrix();

    }
    private void drawSkinHead(int id, float x, float y, float z, double scale, float r, float g, float b)
    {

        ScaledResolution sacle = new ScaledResolution(minecraft);

        float factor = sacle.getScaleFactor();
        float imageScale = 1;

        if(minecraft.displayHeight >= 720)
            imageScale = 0.666667f;

        float width = (float) (48 * scale * imageScale);
        float height = (float) (48 * scale * imageScale);

        GlStateManager.pushMatrix();
        {
            GL11.glBindTexture(3553, id);

            GlStateManager.scale(scale, scale, 1.0D);
            x = x / (float)scale;
            y = y / (float)scale;
            drawTexture(x, y, 32, 32, 0, 0, 1, 1, 1, 1, r, g, b);

            /*
                       Tessellator t = Tessellator.getInstance();
            BufferBuilder bb = t.getBuffer();

            GlStateManager.enableBlend();
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color(r, g, b, 1f);
            bb.begin(7, DefaultVertexFormats.POSITION_TEX);
            bb.pos(x, y, z).tex(0, 0).endVertex();
            bb.pos(x, y + height, z).tex(0, 1f).endVertex();
            bb.pos(x + width, y + height, z).tex(1, 1).endVertex();
            bb.pos(x + width, y, z).tex(1, 0).endVertex();
            t.draw();
            GlStateManager.disableBlend();
            //drawTexture(x, y, 16f, 16d, 0.125d, 0.12d, 0.25d, 0.25d, 5f, 1f);
             */

        }
        GlStateManager.popMatrix();
    }


    private void drawYLinearTexture(float x, float y, float xSize, float ySize, float endV, float alpha, float z)
    {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.pushMatrix();
        {
            GlStateManager.enableBlend();
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);

            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
            bufferbuilder.pos(x, y + (ySize*endV), z).tex(0.0d,endV).endVertex();
            bufferbuilder.pos(x, y+ySize, z).tex(0.0d,1.0d).endVertex();
            bufferbuilder.pos(x+ xSize, y+ySize, z).tex(1.0d,1.0d).endVertex();
            bufferbuilder.pos(x+ xSize, y+ (ySize*endV), z).tex(1.0d,endV).endVertex();

            //bufferbuilder.pos(x, y + ySize * endU, zLevel).tex(1.0d, endU).endVertex();
            //bufferbuilder.pos(x, y + ySize * endU, zLevel).tex(0.0d, endU).endVertex();

            tessellator.draw();
            GlStateManager.disableBlend();
        }
        GlStateManager.popMatrix();
    }

    private void drawXLinearTexture(float x, float y, float xSize, float ySize, float endU, float alpha, float z)
    {
        endU = endU * 0.01f;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.pushMatrix();
        {
            GlStateManager.enableBlend();
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);

            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
            bufferbuilder.pos(x, y, z).tex(0.0d,0.0d).endVertex();
            bufferbuilder.pos(x, y+ySize, z).tex(0.0d,1.0d).endVertex();
            bufferbuilder.pos(x+ xSize * endU, y+ySize, z).tex(endU,1.0d).endVertex();
            bufferbuilder.pos(x+ xSize * endU, y, z).tex(endU,0.0d).endVertex();

            tessellator.draw();
            GlStateManager.disableBlend();
        }
        GlStateManager.popMatrix();
    }


}
