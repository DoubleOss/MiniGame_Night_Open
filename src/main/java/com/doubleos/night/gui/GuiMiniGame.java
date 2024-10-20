package com.doubleos.night.gui;

import com.doubleos.night.gui.button.BtnMiniGame;
import com.doubleos.night.handler.SoundHandler;
import com.doubleos.night.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class GuiMiniGame extends GuiScreen
{
    Minecraft minecraft = Minecraft.getMinecraft();


    float m_backYPos = 0;


    float m_backYPos2 = 0;
    float m_buttonAlpha = 0;

    boolean m_successCheck = false;

    public GuiMiniGame()
    {

    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if(button instanceof BtnMiniGame)
        {
            BtnMiniGame btn = (BtnMiniGame) button;
            if(btn.m_currentRotate >= btn.m_rotateLimit)
            {
                btn.m_currentRotate = 1;
            }
            else
            {
                btn.m_currentRotate += 1;
            }

            BtnMiniGame btn1 = (BtnMiniGame) buttonList.get(1);
            BtnMiniGame btn2 = (BtnMiniGame) buttonList.get(2);
            BtnMiniGame btn3 = (BtnMiniGame) buttonList.get(3);
            BtnMiniGame btn7 = (BtnMiniGame) buttonList.get(7);
            BtnMiniGame btn8 = (BtnMiniGame) buttonList.get(8);
            BtnMiniGame btn9 = (BtnMiniGame) buttonList.get(9);
            BtnMiniGame btn10 = (BtnMiniGame) buttonList.get(10);


            BtnMiniGame btn12 = (BtnMiniGame) buttonList.get(12);
            BtnMiniGame btn13 = (BtnMiniGame) buttonList.get(13);
            BtnMiniGame btn14 = (BtnMiniGame) buttonList.get(14);
            BtnMiniGame btn15 = (BtnMiniGame) buttonList.get(15);
            BtnMiniGame btn16 = (BtnMiniGame) buttonList.get(16);
            BtnMiniGame btn22 = (BtnMiniGame) buttonList.get(22);
            BtnMiniGame btn23 = (BtnMiniGame) buttonList.get(23);

//            System.out.println(" 버튼 1 " + btn1.m_currentRotate);
//            System.out.println(" 버튼 2 " + btn2.m_currentRotate);
//            System.out.println(" 버튼 3 " + btn3.m_currentRotate);
//            System.out.println(" 버튼 7 " + btn7.m_currentRotate);
//            System.out.println(" 버튼 8 " + btn8.m_currentRotate);
//            System.out.println(" 버튼 9 " + btn9.m_currentRotate);
//            System.out.println(" 버튼 10 " + btn10.m_currentRotate);
//            System.out.println(" 버튼 12 " + btn12.m_currentRotate);
//            System.out.println(" 버튼 13 " + btn13.m_currentRotate);
//            System.out.println(" 버튼 14 " + btn14.m_currentRotate);
//            System.out.println(" 버튼 15 " + btn15.m_currentRotate);
//            System.out.println(" 버튼 16 " + btn16.m_currentRotate);
//            System.out.println(" 버튼 22 " + btn22.m_currentRotate);
//            System.out.println(" 버튼 23 " + btn23.m_currentRotate);
//


        }
    }

    @Override
    public void initGui()
    {
        super.initGui();

        buttonList.clear();


        ScaledResolution scaled = new ScaledResolution(mc);

        float width = (float) scaled.getScaledWidth_double();

        float height = (float) scaled.getScaledHeight_double();

        float btn_Width = 96 / 3f;
        float btn_Height = 30 / 3f;

        int btn = 0;
        int btnCount = 0;

        //53 간격
        GuiButton btn0 = new BtnMiniGame(btnCount++, (int)(width/2 - btn_Width/2) - 147, (int)(height/2 - btn_Height/2) - 102, "", BtnMiniGame.BUTTON_TYPE.type_2);
        GuiButton btn1 = new BtnMiniGame(btnCount++, (int)(width/2 - btn_Width/2) - 94, (int)(height/2 - btn_Height/2) - 102, "", BtnMiniGame.BUTTON_TYPE.type_2);
        GuiButton btn2 = new BtnMiniGame(btnCount++, (int)(width/2 - btn_Width/2) - 41, (int)(height/2 - btn_Height/2) - 102, "", BtnMiniGame.BUTTON_TYPE.type_1);
        GuiButton btn3 = new BtnMiniGame(btnCount++, (int)(width/2 - btn_Width/2) + 12, (int)(height/2 - btn_Height/2) - 102, "", BtnMiniGame.BUTTON_TYPE.type_2);
        GuiButton btn4 = new BtnMiniGame(btnCount++, (int)(width/2 - btn_Width/2) + 65, (int)(height/2 - btn_Height/2) - 102, "", BtnMiniGame.BUTTON_TYPE.type_1);
        GuiButton btn5 = new BtnMiniGame(btnCount++, (int)(width/2 - btn_Width/2) + 118, (int)(height/2 - btn_Height/2) - 102, "", BtnMiniGame.BUTTON_TYPE.type_2);

        GuiButton btn6 = new BtnMiniGame(btnCount++, (int)(width/2 - btn_Width/2) - 147, (int)(height/2 - btn_Height/2) - 49, "", BtnMiniGame.BUTTON_TYPE.type_3);
        GuiButton btn7 = new BtnMiniGame(btnCount++, (int)(width/2 - btn_Width/2) - 94, (int)(height/2 - btn_Height/2) - 49, "", BtnMiniGame.BUTTON_TYPE.type_3);
        GuiButton btn8 = new BtnMiniGame(btnCount++, (int)(width/2 - btn_Width/2) - 41, (int)(height/2 - btn_Height/2) - 49, "", BtnMiniGame.BUTTON_TYPE.type_2);
        GuiButton btn9 = new BtnMiniGame(btnCount++, (int)(width/2 - btn_Width/2) + 12, (int)(height/2 - btn_Height/2) - 49, "", BtnMiniGame.BUTTON_TYPE.plus);
        GuiButton btn10 = new BtnMiniGame(btnCount++, (int)(width/2 - btn_Width/2) + 65, (int)(height/2 - btn_Height/2) - 49, "", BtnMiniGame.BUTTON_TYPE.type_2);
        GuiButton btn11= new BtnMiniGame(btnCount++, (int)(width/2 - btn_Width/2) + 118, (int)(height/2 - btn_Height/2) - 49, "", BtnMiniGame.BUTTON_TYPE.plus);


        GuiButton btn12 = new BtnMiniGame(btnCount++, (int)(width/2 - btn_Width/2) - 147, (int)(height/2 - btn_Height/2) + 4, "", BtnMiniGame.BUTTON_TYPE.type_1);
        GuiButton btn13 = new BtnMiniGame(btnCount++, (int)(width/2 - btn_Width/2) - 94, (int)(height/2 - btn_Height/2) + 4, "", BtnMiniGame.BUTTON_TYPE.type_2);
        GuiButton btn14 = new BtnMiniGame(btnCount++, (int)(width/2 - btn_Width/2) - 41, (int)(height/2 - btn_Height/2) + 4, "", BtnMiniGame.BUTTON_TYPE.type_2);
        GuiButton btn15 = new BtnMiniGame(btnCount++, (int)(width/2 - btn_Width/2) + 12, (int)(height/2 - btn_Height/2) + 4, "", BtnMiniGame.BUTTON_TYPE.plus_2);
        GuiButton btn16 = new BtnMiniGame(btnCount++, (int)(width/2 - btn_Width/2) + 65, (int)(height/2 - btn_Height/2) + 4, "", BtnMiniGame.BUTTON_TYPE.type_1);
        GuiButton btn17 = new BtnMiniGame(btnCount++, (int)(width/2 - btn_Width/2) + 118, (int)(height/2 - btn_Height/2) + 4, "", BtnMiniGame.BUTTON_TYPE.type_1);


        GuiButton btn18 = new BtnMiniGame(btnCount++, (int)(width/2 - btn_Width/2) - 147, (int)(height/2 - btn_Height/2) + 57, "", BtnMiniGame.BUTTON_TYPE.type_3);
        GuiButton btn19 = new BtnMiniGame(btnCount++, (int)(width/2 - btn_Width/2) - 94, (int)(height/2 - btn_Height/2) + 57, "", BtnMiniGame.BUTTON_TYPE.type_2);
        GuiButton btn20 = new BtnMiniGame(btnCount++, (int)(width/2 - btn_Width/2) - 41, (int)(height/2 - btn_Height/2) + 57, "", BtnMiniGame.BUTTON_TYPE.plus);
        GuiButton btn21 = new BtnMiniGame(btnCount++, (int)(width/2 - btn_Width/2) + 12, (int)(height/2 - btn_Height/2) + 57, "", BtnMiniGame.BUTTON_TYPE.type_1);
        GuiButton btn22 = new BtnMiniGame(btnCount++, (int)(width/2 - btn_Width/2) + 65, (int)(height/2 - btn_Height/2) + 57, "", BtnMiniGame.BUTTON_TYPE.type_2);
        GuiButton btn23 = new BtnMiniGame(btnCount++, (int)(width/2 - btn_Width/2) + 118, (int)(height/2 - btn_Height/2) + 57, "", BtnMiniGame.BUTTON_TYPE.type_1);


        buttonList.add(btn0);
        buttonList.add(btn1);
        buttonList.add(btn2);
        buttonList.add(btn3);
        buttonList.add(btn4);
        buttonList.add(btn5);
        buttonList.add(btn6);
        buttonList.add(btn7);
        buttonList.add(btn8);
        buttonList.add(btn9);
        buttonList.add(btn10);
        buttonList.add(btn11);
        buttonList.add(btn12);
        buttonList.add(btn13);
        buttonList.add(btn14);
        buttonList.add(btn15);
        buttonList.add(btn16);
        buttonList.add(btn17);
        buttonList.add(btn18);
        buttonList.add(btn19);
        buttonList.add(btn20);
        buttonList.add(btn21);
        buttonList.add(btn22);
        buttonList.add(btn23);

        buttonList.get(0).enabled = false;
        buttonList.get(5).enabled = false;
        buttonList.get(6).enabled = false;

        buttonList.get(11).enabled = false;
        buttonList.get(20).enabled = false;
        buttonList.get(21).enabled = false;



    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if(keyCode == 1)
        {
            minecraft.player.closeScreen();

        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        //super.drawScreen(mouseX, mouseY, partialTicks);
        drawDefaultBackground();


        ScaledResolution scale = new ScaledResolution(minecraft);


        //1920 1080 495 270
        //1280 720 640 360

        float width = (float) scale.getScaledWidth_double();

        float height = (float) scale.getScaledHeight_double();

        float fpsCurrection = (120f / Minecraft.getDebugFPS());

        float backgroundWidth = 1628f / 4f;
        float backgroundHeight = 893f / 4f;


        if(!m_successCheck)
            mc.renderEngine.bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/ele_minigame/light_off.png"));
        else
        {
            mc.renderEngine.bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/ele_minigame/light_on.png"));
            m_backYPos += fpsCurrection * partialTicks;
        }

        if(m_backYPos >= 40f)
        {
            mc.player.closeScreen();
        }

        drawTexture(width/2-backgroundWidth/2, height/2-backgroundHeight/2, backgroundWidth, backgroundHeight, 0, 0, 1, 1, 0, 1f);


        for(int i = 0 ; i<buttonList.size(); i++)
        {

            if( i == 0 || i == 5 || i == 6 || i == 11 || i == 20 || i == 21)
            {

            }
            else
            {
                buttonList.get(i).drawButton(mc, mouseX, mouseY, partialTicks);
            }

        }

    }

    public float easeOutCubic(float x)
    {
        return (float)(1 - Math.pow(1 - x, 3));
    }

    public void drawRotateTexture2(float angle, float x, float y, double xSize, double ySize, double u, double v, double uAfter, double vAfter, float z, float alpha)
    {
        Tessellator t = Tessellator.getInstance();
        BufferBuilder bb = t.getBuffer();
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.translate(x + xSize / 2, y + ySize / 2, 100); // 중심점을 텍스처의 가운데로 이동
        GlStateManager.rotate(m_backYPos, 0.0f, 0.0f, 1.0f); // 회전 적용
        GlStateManager.translate(-(x + xSize / 2), -(y + ySize / 2), 0);
        GlStateManager.scale(1f, 1f, 1f);

        bb.begin(7, DefaultVertexFormats.POSITION_TEX);

        bb.pos(x, y, z).tex(u, v).endVertex();
        bb.pos(x, y + ySize, z).tex(u, vAfter).endVertex();
        bb.pos(x + xSize, y + ySize, z).tex(uAfter, vAfter).endVertex();
        bb.pos(x + xSize, y, z).tex(uAfter, v).endVertex();
        t.draw();

        GlStateManager.disableBlend();
        GlStateManager.popMatrix();

    }
    public void drawTexture(String texture, float x, float y, double xSize, double ySize, double u, double v, double uAfter, double vAfter, float z)
    {
        ResourceLocation location = new ResourceLocation("sdf", "textures/gui/" + texture + ".png");
        Minecraft.getMinecraft().renderEngine.bindTexture(location);
        Tessellator t = Tessellator.getInstance();
        BufferBuilder bb = t.getBuffer();
        GlStateManager.pushMatrix();
        {
            GlStateManager.enableBlend();
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
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
    public void drawStringScaleResizeByRightWidth(String text, float x, float y, float depth, float scale, int color)
    {
        ScaledResolution scaled = new ScaledResolution(minecraft);

        float width = (float)scaled.getScaledWidth_double();
        float height = (float)scaled.getScaledHeight_double();

        int stringSize = minecraft.fontRenderer.getStringWidth(text);

        GlStateManager.pushMatrix();
        {
            GlStateManager.scale(scale, scale, scale);
            GlStateManager.translate(0f, 0f, depth);
            minecraft.fontRenderer.drawString(text, (width - x -stringSize)/scale, (y)/scale, color, false);
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
            minecraft.fontRenderer.drawString(text, (x +stringSize)/scale, (y)/scale, color, false);
        }
        GlStateManager.popMatrix();
    }

    private void drawSkinHead(float x, float y, float z, double scale)
    {
        GlStateManager.pushMatrix();
        this.minecraft.renderEngine.bindTexture(this.minecraft.player.getLocationSkin());

        GlStateManager.scale(scale, scale, 1.0D);
        x = x / (float)scale;
        y = y / (float)scale;
        drawTexture(x, y, 16f, 16d, 0.125d, 0.12d, 0.25d, 0.25d, 1f, 1f);
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


    public void drawStringScaleResizeByMiddleWidth(String text, float x, float y, float depth, float scale, int color)
    {
        ScaledResolution scaled = new ScaledResolution(minecraft);

        float width = (float)scaled.getScaledWidth_double();
        float height = (float)scaled.getScaledHeight_double();

        int stringSize = minecraft.fontRenderer.getStringWidth(text);

        GlStateManager.pushMatrix();
        {
            GlStateManager.scale(scale, scale, scale);
            GlStateManager.translate(0f, 0f, depth);
            minecraft.fontRenderer.drawString(text, (width - x - stringSize/2)/scale, (y)/scale, color, false);
        }
        GlStateManager.popMatrix();
    }

    private void drawYLinearTexture(float x, float y, float xSize, float ySize, float endV, float alpha, float z)
    {

        endV = 1 - endV * 0.01f;
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

        /* 거꾸로 리니어

        bufferbuilder.pos(x, y + ySize * endV, z).tex(0.0d,endV).endVertex();
        bufferbuilder.pos(x+ xSize, y + ySize * endV, z).tex(1.0d,endV).endVertex();
        bufferbuilder.pos(x+ xSize, y, z).tex(1.0d, 0.0d).endVertex();
        bufferbuilder.pos(x, y, z).tex(0.0d,0.0d).endVertex();


         */



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


    protected void renderHotbarItem(int x, int y, float partialTicks, EntityPlayer player, ItemStack stack)
    {
        Minecraft mc = Minecraft.getMinecraft();
        if (!stack.isEmpty())
        {
            float f = stack.getAnimationsToGo() - partialTicks;
            if (f > 0.0F)
            {
                GlStateManager.pushMatrix();
                {
                    float f1 = 1.0F + f / 5.0F;
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                    GlStateManager.translate((x + 8), (y + 12), 0.0F);
                    GlStateManager.scale(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
                    GlStateManager.translate(-(x + 8), -(y + 12), 0.0F);

                }

            }
            mc.getRenderItem().renderItemAndEffectIntoGUI((EntityLivingBase) player, stack, x, y);
            if (f > 0.0F)
                GlStateManager.popMatrix();
            mc.getRenderItem().renderItemOverlays(mc.fontRenderer, stack, x, y);
        }
    }

}
