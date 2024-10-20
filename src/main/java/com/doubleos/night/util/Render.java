package com.doubleos.night.util;

import com.doubleos.night.Night;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import org.lwjgl.opengl.GL11;

public class Render
{


    public static void broadcastMessage(String message, MinecraftServer server)
    {
        for(EntityPlayerMP players : server.getPlayerList().getPlayers())
        {
            players.sendMessage(new TextComponentString(message));
        }
    }
    public static Minecraft minecraft = Minecraft.getMinecraft();

    public static float easeOutCubic(float x)
    {
        return (float)(1 - Math.pow(1 - x, 3));
    }

    public static float easeInOutBack(float x)
    {
        float c1 = 1.70158f;
        float c2 = c1 * 1.525f;

        return (float) (x < 0.5 ? (Math.pow(2 * x, 2) * ((c2 + 1) * 2 * x - c2)) / 2 : (Math.pow(2 * x - 2, 2) * ((c2 + 1) * (x * 2 - 2) + c2) + 2) / 2f);
    }
    public static float easeInOutSine(float x)
    {
        return (float)-(Math.cos(Math.PI * x) - 1) / 2f;
    }
    public static float easeOutBounce(float x)
    {
        float n1 = 7.5625f;
        float d1 = 2.75f;

        if (x < 1 / d1)
        {
            return n1 * x * x;
        }
        else if (x < 2 / d1)
        {
            return n1 * (x -= 1.5 / d1) * x + 0.75f;
        }
        else if (x < 2.5 / d1)
        {
            return n1 * (x -= 2.25 / d1) * x + 0.9375f;
        }
        else
        {
            return n1 * (x -= 2.625f / d1) * x + 0.984375f;
        }
    }

    public static void drawRotateTextureY(float angle, float x, float y, double xSize, double ySize, double u, double v, double uAfter, double vAfter, float z, float alpha)
    {
        Tessellator t = Tessellator.getInstance();
        BufferBuilder bb = t.getBuffer();
        GlStateManager.pushMatrix();
        {
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.translate(x + xSize / 2, y + ySize / 2, z); // 중심점을 텍스처의 가운데로 이동
            GlStateManager.rotate(angle, 0.0f, 1.0f, 0.0f); // 회전 적용
            GlStateManager.translate(-(x + xSize / 2), -(y + ySize / 2), z);
            GlStateManager.color(1, 1, 1, alpha);

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

    public static void drawTexture(String texture, float x, float y, double xSize, double ySize, double u, double v, double uAfter, double vAfter, float z)
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


    public static void drawTexture(float x, float y, double xSize, double ySize, double u, double v, double uAfter, double vAfter, float z, float alpha)
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

    public static void drawCricleNewProgressToQuad(float x, float y, float z, double textSizeX, double textSizeY, double per, double color) {

        Tessellator t = Tessellator.getInstance();
        BufferBuilder bb = t.getBuffer();
        GlStateManager.pushMatrix();
        {
            GlStateManager.enableBlend();
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color(1f, 1f, 1f, 1f);
            //GlStateManager.translate(x, y, z);
            bb.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION_TEX);
            bb.pos(x+textSizeX/2f, y+textSizeY/2, z).tex(0.5, 0.5).endVertex();
            for (float i = 0; i <= per * 100 / 100; i++) {
                float theta = (float) (2.0f * Math.PI * i / 100f); // 현재 각도 계산
                float xPos = (float) (textSizeX * Math.cos(theta)); // x 좌표 계산
                float yPos = (float) (textSizeY * Math.sin(theta)); // y 좌표 계산
                float u = (x + 1.0f) / 2.0f; // x 좌표를 [0, 1] 범위로 변환하여 텍스처 좌표 계산
                float v = (y + 1.0f) / 2.0f;
                bb.pos(xPos + (x+textSizeX/2f), yPos+(y+textSizeY/2f), z).tex(u, v).endVertex();
            }

            t.draw();
            GlStateManager.disableBlend();
        }
        GlStateManager.popMatrix();
    }
    public static void drawCricleProgressToQuad(float x, float y, float z, double textSizeX, double textSizeY, double per, double color)
    {

        Tessellator t = Tessellator.getInstance();
        BufferBuilder bb = t.getBuffer();
        GlStateManager.pushMatrix();
        {
            GlStateManager.enableBlend();
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color(1f, 1f, 1f, 1f);

            bb.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION_TEX);

            bb.pos(x +textSizeX/2, y+textSizeY/2, z).tex(0.5d, 0.5d).endVertex();
            if(per >= 87)
            {
                double b0 = (1d-(99d-per) / 13d);
                bb.pos(x + (textSizeX/2)*b0, y, z).tex(0.5d*b0, 0d).endVertex();
            }
            if(per >= 62)
            {
                if(per > 62 && per <= 74)
                {
                    double b1 = (86.5d-per) / 25d;
                    bb.pos(x, (textSizeY * b1) + y, z).tex(0d, 1d* b1).endVertex();
                }
                else if(per > 74f && per <= 80f)
                {
                    double b1 = (87.5d-per) / 25d;
                    bb.pos(x, (textSizeY * b1) + y, z).tex(0d, 1d* b1).endVertex();
                }
                else if(per > 80f && per <= 87f)
                {
                    double b1 = (87.5-per) / 25d;
                    bb.pos(x, (textSizeY * b1) + y, z).tex(0d, 1d* b1).endVertex();
                }
                else if(per > 87)
                {
                    bb.pos(x, y, z).tex(0d, 0d).endVertex();
                }
                bb.pos(x, y+textSizeY, z).tex(0d, 1d).endVertex();

            }
            if(per >= 38)
            {

                if(per >= 38 && per < 40)
                {
                    double b2 = (62-per) / 25d;
                    bb.pos((textSizeX * b2) + x, y+textSizeY, z).tex(1d * b2, 1d).endVertex();
                }
                else if(per >= 40 && per <= 47)
                {
                    double b2 = (61.2f-per) / 25d;
                    bb.pos((textSizeX * b2) + x, y+textSizeY, z).tex(1d * b2, 1d).endVertex();
                }
                else if(per > 47 && per <= 54)
                {
                    double b2 = (61.8f-per) / 25d;
                    bb.pos((textSizeX * b2) + x, y+textSizeY, z).tex(1d * b2, 1d).endVertex();
                }
                else if(per > 54 && per <= 62)
                {
                    double b2 = (63.2d-per) / 25d;
                    bb.pos((textSizeX * b2) + x, y+textSizeY, z).tex(1d * b2, 1d).endVertex();
                }
                else if(per > 62)
                {
                    bb.pos(x, y+textSizeY, z).tex(0d, 1d).endVertex();
                }
                bb.pos(x+textSizeX, y+textSizeY, z).tex(1d, 1d).endVertex();
            }
            if(per > 12)
            {
                if(per > 12 && per <= 38)
                {
                    if (per >= 14f && per < 18f)
                    {
                        double b3 = (1-((36.5f-per) / 25d));
                        bb.pos(x+textSizeX, y+textSizeY*b3, z).tex(1d, 1d*b3).endVertex();

                    }
                    else if(per >= 18 && per < 20)
                    {
                        double b3 = (1-((36-per) / 25d));
                        bb.pos(x+textSizeX, y+textSizeY*b3, z).tex(1d, 1d*b3).endVertex();
                    }
                    else if(per >= 20 && per < 24)
                    {
                        double b3 = (1-((37-per) / 25d));
                        bb.pos(x+textSizeX, y+textSizeY*b3, z).tex(1d, 1d*b3).endVertex();
                    }
                    else if (per >= 24 && per < 35)
                    {
                        double b3 = (1-((37.5f-per) / 25d));
                        bb.pos(x+textSizeX, y+textSizeY*b3, z).tex(1d, 1d*b3).endVertex();
                    }
                    else
                    {
                        double b3 = (1-((37-per) / 25d));
                        bb.pos(x+textSizeX, y+textSizeY*b3, z).tex(1d, 1d*b3).endVertex();
                    }


                }
                else if(per > 38)
                {
                    //bb.pos(x+textSizeX, y+textSizeY, z).tex(1d, 1d).endVertex();
                }
                bb.pos(x+textSizeX, y, z).tex(1d, 0d).endVertex();
            }
            if(per <= 12)
            {

                double b0 = (1-(12-per) / 12);
                bb.pos(x+textSizeX/2 + (textSizeX/2 * b0), y, z).tex(0.5d + (b0 * 0.5d), 0d).endVertex();
            }
            bb.pos(x+textSizeX/2, y, z).tex(0.5d, 0d).endVertex();


            t.draw();
            GlStateManager.disableBlend();
        }
        GlStateManager.popMatrix();

    }
    public static void drawStringScaleResizeByRightWidth(String text, float x, float y, float depth, float scale, int color)
    {
        ScaledResolution scaled = new ScaledResolution(minecraft);

        float width = (float)scaled.getScaledWidth_double();
        float height = (float)scaled.getScaledHeight_double();

        int stringSize = minecraft.fontRenderer.getStringWidth(text);

        GlStateManager.pushMatrix();
        {
            GlStateManager.scale(scale, scale, scale);
            GlStateManager.translate(0d, 0f, depth);
            minecraft.fontRenderer.drawString(text, (width - x -stringSize)/scale, (y)/scale, color, false);
        }
        GlStateManager.popMatrix();
    }
    public static void drawStringScaleResizeByLeftWidth(String text, float x, float y, float depth, float scale, int color)
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
    public static void drawStringScaleResizeByLeftWidth(String text, float x, float y, float depth, float scale, int color, boolean active)
    {

        int stringSize = minecraft.fontRenderer.getStringWidth(text);

        GlStateManager.pushMatrix();
        {
            GlStateManager.scale(scale, scale, 1f);
            GlStateManager.translate(0f, 0f, depth);
            minecraft.fontRenderer.drawString(text, (x)/scale, (y)/scale, color, active);
        }
        GlStateManager.popMatrix();
    }
    public static void drawTexture(float x, float y, double xSize, double ySize, double u, double v, double uAfter, double vAfter, float z, float alpha, float r, float g, float b)
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
    public static void drawRotateTexture2(float angle, float x, float y, double xSize, double ySize, double u, double v, double uAfter, double vAfter, float z, float alpha)
    {
        Tessellator t = Tessellator.getInstance();
        BufferBuilder bb = t.getBuffer();
        GlStateManager.pushMatrix();
        {
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.translate(x + xSize / 2, y + ySize / 2, z); // 중심점을 텍스처의 가운데로 이동
            GlStateManager.rotate(angle, 0.0f, 0.0f, 1.0f); // 회전 적용
            GlStateManager.translate(-(x + xSize / 2), -(y + ySize / 2), z);
            GlStateManager.color(1, 1, 1, alpha);

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
    public static void drawSkinHead(float x, float y, float z, double scale)
    {
        GlStateManager.pushMatrix();
        minecraft.renderEngine.bindTexture(minecraft.player.getLocationSkin());

        GlStateManager.scale(scale, scale, 1.0D);
        x = x / (float)scale;
        y = y / (float)scale;
        drawTexture(x, y, 16f, 16d, 0.125d, 0.12d, 0.25d, 0.25d, 1f, 1f);
        GlStateManager.popMatrix();
    }

    public static void drawSkinHead(int id, float x, float y, float z, double scale, float r, float g, float b)
    {

        ScaledResolution sacle = new ScaledResolution(minecraft);

        float factor = sacle.getScaleFactor();


        float width = (float) (128 * scale);
        float height = (float) (128 * scale);

        GlStateManager.pushMatrix();
        {
            GL11.glBindTexture(3553, id);

            GlStateManager.scale(scale, scale, 1.0D);
            x = x / (float)scale;
            y = y / (float)scale;
            drawTexture(x, y, 29f, 29f, 0, 0, 1, 1, z, 1, r, g, b);
        }
        GlStateManager.popMatrix();
    }


    public static void drawTexture(float x, float y, double xSize, double ySize, double u, double v, double uAfter, double vAfter, float z, float alpha, float scale)
    {
        Tessellator t = Tessellator.getInstance();
        BufferBuilder bb = t.getBuffer();
        GlStateManager.pushMatrix();
        {

        }
        GlStateManager.popMatrix();

    }


    public static void drawTextureMiddle(float x, float y, double xSize, double ySize, double u, double v, double uAfter, double vAfter, float z, float alpha, float scale)
    {
        Tessellator t = Tessellator.getInstance();
        BufferBuilder bb = t.getBuffer();

        GlStateManager.pushMatrix();
        {
            GlStateManager.enableBlend();
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
            GlStateManager.color(0.7f, 0.45f, 0.9f);
            GlStateManager.scale(scale, scale, 1);
            bb.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION_TEX);
            scale = 1f;
            bb.pos((x + (xSize/2))/scale, y/scale, z).tex(0.5f, 0f).endVertex();
            bb.pos(x/scale, y/scale, z).tex(0f, 0f).endVertex();
            bb.pos(x/scale, (y+ySize)/scale, z).tex(0f, 1f).endVertex();
            bb.pos((x+xSize)/scale, (y+ySize)/scale, z).tex(1f, 1f).endVertex();
            bb.pos((x+xSize)/scale, y/scale, z).tex(1f, 0f).endVertex();
            bb.pos((x+xSize/2)/scale, y/scale, z).tex(0.5f, 0f).endVertex();
//            bb.pos(x + (xSize/2), y, z).tex(0.5f, 0f).endVertex();
//            bb.pos(x, y, z).tex(0f, 0f).endVertex();
//            bb.pos(x, y+ySize, z).tex(0f, 1f).endVertex();
//            bb.pos(x+xSize, y+ySize, z).tex(1f, 1f).endVertex();
//            bb.pos(x+xSize, y, z).tex(1f, 0f).endVertex();
//            bb.pos(x+xSize/2, y, z).tex(0.5f, 0f).endVertex();
            t.draw();
            GlStateManager.disableBlend();
        }
        GlStateManager.popMatrix();
    }

    public static int RGB_TO_DECIMAL(int r, int g, int b)
    {
        int result = (r << 16) + (g << 8) + b;

        return result;
    }

    public static void drawStringScaleResizeByMiddleWidth(String text, float x, float y, float depth, float scale, int color)
    {
        ScaledResolution scaled = new ScaledResolution(minecraft);

        float width = (float)scaled.getScaledWidth_double();
        float height = (float)scaled.getScaledHeight_double();

        int stringSize = minecraft.fontRenderer.getStringWidth(text);

        GlStateManager.pushMatrix();
        {
            GlStateManager.scale(scale, scale, 1f);
            GlStateManager.translate(0f, 0f, depth);
            minecraft.fontRenderer.drawString(text, (x - stringSize/2f)/scale, (y)/scale, color, true);
        }
        GlStateManager.popMatrix();
    }

    public static void drawStringScaleResizeByMiddleWidth(String text, float x, float y, float depth, float scale, int color, boolean dropShadow)
    {
        ScaledResolution scaled = new ScaledResolution(minecraft);

        float width = (float)scaled.getScaledWidth_double();
        float height = (float)scaled.getScaledHeight_double();

        int stringSize = minecraft.fontRenderer.getStringWidth(text);

        GlStateManager.pushMatrix();
        {
            GlStateManager.scale(scale, scale, 1f);
            GlStateManager.translate(0f, 0f, depth);
            minecraft.fontRenderer.drawString(text, (x - stringSize/2f)/scale, (y)/scale, color, dropShadow);
        }
        GlStateManager.popMatrix();
    }
    public static void drawYLinearTexture(float x, float y, float xSize, float ySize, float endV, float alpha, float z)
    {
        endV = 1 - endV;
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

    public static void drawXLinearTexture(float x, float y, float xSize, float ySize, float endU, float alpha, float z)
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


    public static void renderHotbarItem(int x, int y, float partialTicks, EntityPlayer player, ItemStack stack)
    {
        Minecraft mc = Minecraft.getMinecraft();
        if (!stack.isEmpty())
        {
            float f = stack.getAnimationsToGo() - partialTicks;
            if (f > 0.0F)
            {
                GlStateManager.pushMatrix();
                {
                    GlStateManager.enableBlend();
                    GlStateManager.blendFunc(770, 771);
                    float f1 = 1.0F + f / 5.0F;
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                    GlStateManager.translate((x + 8), (y + 12), 1.0F);
                    GlStateManager.scale(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
                    GlStateManager.translate(-(x + 8), -(y + 12), 1.0F);

                    GlStateManager.disableBlend();
                }

            }
            mc.getRenderItem().renderItemAndEffectIntoGUI((EntityLivingBase) player, stack, x, y);
            if (f > 0.0F)
                GlStateManager.popMatrix();
            mc.getRenderItem().renderItemOverlays(mc.fontRenderer, stack, x, y);
        }
    }


    public static void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, EntityLivingBase ent)
    {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)posX, (float)posY, 55f);
        GlStateManager.scale((float)(-scale), (float)scale, (float)1f);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float f = ent.renderYawOffset;
        float f1 = ent.rotationYaw;
        float f2 = ent.rotationPitch;
        float f3 = ent.prevRotationYawHead;
        float f4 = ent.rotationYawHead;
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        ent.renderYawOffset = (float)Math.atan((double)(mouseX / 40.0F)) * 20.0F;
        ent.rotationYaw = (float)Math.atan((double)(mouseX / 40.0F)) * 40.0F;
        ent.rotationPitch = -((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F;
        ent.rotationYawHead = ent.rotationYaw;
        ent.prevRotationYawHead = ent.rotationYaw;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\hud\\더블.png"));
        rendermanager.renderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
        rendermanager.setRenderShadow(true);
        ent.renderYawOffset = f;
        ent.rotationYaw = f1;
        ent.rotationPitch = f2;
        ent.prevRotationYawHead = f3;
        ent.rotationYawHead = f4;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
}
