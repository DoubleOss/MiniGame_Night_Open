package com.doubleos.night.gui.button;

import com.doubleos.night.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

import java.util.Random;

public class BtnMiniGame extends GuiButton {

    public int m_rotateLimit = 0;

    public int m_currentRotate = 1;

    public BUTTON_TYPE m_buttonType;

    public enum BUTTON_TYPE {
        plus,
        plus_2,
        type_1,
        type_2,
        type_3

    }

    public BtnMiniGame(int buttonId, int x, int y, String buttonText, BUTTON_TYPE type)
    {

        super(buttonId, x, y, buttonText);

        this.width = 214 / 4;
        this.height = 214 / 4;

        m_buttonType = type;

        if (m_buttonType.equals(BUTTON_TYPE.plus) || m_buttonType.equals(BUTTON_TYPE.plus_2) || (m_buttonType.equals(BUTTON_TYPE.type_1))) {
            m_rotateLimit = 2;
        } else if (m_buttonType.equals(BUTTON_TYPE.type_2) || (m_buttonType.equals(BUTTON_TYPE.type_3))) {
            m_rotateLimit = 4;
        }

        int ranNum = new Random().nextInt((m_rotateLimit - m_currentRotate) + 1) + m_currentRotate;
        m_currentRotate = ranNum;

    }


    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
    {

        String active = (mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height) ? "_on" : "";

        float typeWidth = 214f/4f;
        float typeHeight = 214f/4f;

        mc.renderEngine.bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/ele_minigame/"+m_buttonType.name()+".png"));
        if (m_buttonType.equals(BUTTON_TYPE.plus) || m_buttonType.equals(BUTTON_TYPE.plus_2)  )
        {
            int radius = m_currentRotate == 2 ? 3 : 1;
            drawTexture(this.x, this.y, typeWidth, typeHeight, 0, 0, 1, 1, 3, 1f, radius);
        }
        else
        {
            drawTexture(this.x, this.y, typeWidth, typeHeight, 0, 0, 1, 1, 3, 1f, m_currentRotate);
        }

//
//        if(active.equals("_on"))
//        {
//            mc.renderEngine.bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/ele_minigame/"+m_buttonType.name()+".png"));
//            drawTexture(this.x, this.y, typeWidth, typeHeight, 0, 0, 1, 1, 3, 1f, m_currentRotate);
//        }
//        else
//        {
//            mc.renderEngine.bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/ele_minigame/"+m_buttonType.name()+".png"));
//            drawTexture(this.x, this.y, typeWidth, typeHeight, 0, 0, 1, 1, 3, 1f, m_currentRotate);
//        }

    }

    @Override
    public void playPressSound(SoundHandler soundHandlerIn)
    {
        //soundHandlerIn.playSound(PositionedSoundRecord.getMasterRecord(com.doubleos.night.handler.SoundHandler.PLUG, 1.0F));
    }

    public void drawTexture(float x, float y, double xSize, double ySize, double u, double v, double uAfter, double vAfter, float z, float alpha, int rotate)
    {
        Tessellator t = Tessellator.getInstance();
        BufferBuilder bb = t.getBuffer();
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.translate(x + xSize / 2, y + ySize / 2, 100); // 중심점을 텍스처의 가운데로 이동
        GlStateManager.rotate((rotate-1)*90, 0.0f, 0.0f, 1.0f); // 회전 적용
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

    public void drawRotateTexture2(float angle, float x, float y, double xSize, double ySize, double u, double v, double uAfter, double vAfter, float z, float alpha)
    {
        Tessellator t = Tessellator.getInstance();
        BufferBuilder bb = t.getBuffer();
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.translate(x + xSize / 2, y + ySize / 2, 100); // 중심점을 텍스처의 가운데로 이동
        GlStateManager.rotate(angle, 0.0f, 0.0f, 1.0f); // 회전 적용
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



}
