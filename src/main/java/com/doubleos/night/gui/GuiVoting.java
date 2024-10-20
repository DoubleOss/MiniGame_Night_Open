package com.doubleos.night.gui;

import com.doubleos.night.Night;
import com.doubleos.night.gui.button.BtnMiniGame;
import com.doubleos.night.packet.Packet;
import com.doubleos.night.packet.SPacketVotingCount;
import com.doubleos.night.util.ImageInfo;
import com.doubleos.night.util.Reference;
import com.doubleos.night.util.Render;
import com.doubleos.night.variable.MainGame;
import com.doubleos.night.variable.Variable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;

import java.io.IOException;
import java.util.Random;

public class GuiVoting extends GuiScreen
{

    Minecraft minecraft = Minecraft.getMinecraft();

    MainGame mainGame = Variable.getMainGame();

    Variable variable = Variable.Instance();

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        //클릭한 버튼이 투표도장 버튼일경우
        if(button instanceof BtnVolting)
        {
            BtnVolting btnVolting2 = (BtnVolting) button;
            for(GuiButton button1 : buttonList)
            {
                if(button1 instanceof  BtnVolting)
                {
                    //다른사람 투표 누를경우 버튼 초기화
                    BtnVolting btnVolting = (BtnVolting) button1;
                    btnVolting.active = false;
                }
            }
            int btnid = button.id;
            String exitCheck = mainGame.getHashPlayerGameExit().getOrDefault(mainGame.getGamePlayerName().get(btnid), "");
            if(exitCheck.equals(""))
            {
                //체크 되도록 변경
                btnVolting2.active = true;
            }


        }
        else if(button instanceof BtnConfirm)
        {
            //투표 완료 버튼 클릭시
            //서버쪽 패킷 보내야함
            int playerNumer = 0;
            boolean checkTrue = false;
            for(GuiButton button1 : buttonList)
            {
                if(button1 instanceof  BtnVolting)
                {
                    BtnVolting btnVolting = (BtnVolting) button1;
                    if(btnVolting.active)
                    {
                        playerNumer = btnVolting.id;
                        checkTrue = true;
                    }
                }

            }
            if(checkTrue)
            {
                String name = mainGame.getGamePlayerName().get(playerNumer);
                //투표 지목한 사람 값 서버쪽 전송
                Packet.networkWrapper.sendToServer(new SPacketVotingCount(name, mc.player.getName()));

            }
            else
            {
                minecraft.player.sendMessage(new TextComponentString("투표할 플레이어가 선택되지 않았습니다."));
            }

            minecraft.player.closeScreen();
        }
    }

    public GuiVoting()
    {

    }

    @Override
    public void initGui()
    {
        buttonList.clear();

        float btnWidth = 46f/3f;
        float btnHeight = 41f/3f;

        ScaledResolution scaled = new ScaledResolution(minecraft);

        float scaleWidth = (float)scaled.getScaledWidth_double();
        float scaleHeight = (float)scaled.getScaledHeight_double();

        int btnId = 0;
        for(int i = 0; i<mainGame.getGamePlayerName().size(); i++)
        {
            buttonList.add(new BtnVolting(btnId++, (int) (scaleWidth/2 - btnWidth/2 + 59), (int) (scaleHeight/2 - btnHeight/2 - 68 + (32 * i)), "", ""));
        }

        float btnConfirmWidth = 122f/3f;
        float btnConfirmHeight = 76f/3f;
        buttonList.add(new BtnConfirm(btnId++, (int)(scaleWidth/2 - btnConfirmWidth/2- 10), (int)(scaleHeight/2 - btnConfirmHeight/2 + 115), "", ""));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        drawDefaultBackground();

        ScaledResolution scaled = new ScaledResolution(minecraft);

        float scaleWidth = (float)scaled.getScaledWidth_double();
        float scaleHeight = (float)scaled.getScaledHeight_double();

        float backWidth = 643f/4f;
        float backHeight = 912f/4f;

        minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\gui\\backgorund.png"));
        Render.drawTexture(scaleWidth/2f - backWidth/2f, scaleHeight/2-backHeight/2, backWidth, backHeight, 0, 0, 1, 1, 1, 1);

        for(int j = 0; j<mainGame.getGamePlayerName().size(); j++)
        {
            for (int i = 0; i<variable.m_hashGetPngInfo.get("플레이어").size(); i++)
            {
                ImageInfo info = variable.m_hashGetPngInfo.get("플레이어").get(i);
                String exitCheck = mainGame.getHashPlayerGameExit().getOrDefault(info.m_Name, "");
                if(info.m_Name.equals(mainGame.getGamePlayerName().get(j)))
                {

                    GlStateManager.pushMatrix();
                    {
                        if(exitCheck.equals(""))
                            Render.drawSkinHead(info.m_info.getId(), scaleWidth/2 - 58, scaleHeight/2 - 79 + (32 * j), 4, 0.92f, 1, 1, 1);
                        else
                            Render.drawSkinHead(info.m_info.getId(), scaleWidth/2 - 58, scaleHeight/2 - 79 + (32 * j), 4, 0.92f, 0.3f, 0.3f, 0.3f);
                        Render.drawStringScaleResizeByMiddleWidth(info.m_Name, scaleWidth/2 + 12, scaleHeight/2 - 70 + (32 * j), 5, 1, 1, false); //드롭쉐도우 뺀 버전으로 검은색 만들기

                        minecraft.fontRenderer.drawString("테스트", scaleWidth/2, scaleHeight/2, -1 ,true);
                    }
                    GlStateManager.popMatrix();

                }
            }
        }
        for(GuiButton button : buttonList)
        {
            button.drawButton(mc, mouseX, mouseY, partialTicks);
        }
    }

    public class BtnVolting extends GuiButton
    {
        public BtnVolting(int buttonId, int x, int y, String buttonText, String test)
        {

            super(buttonId, x, y, buttonText);

            this.width = 46 / 2;
            this.height = 41 / 2;

        }
        public boolean active = false;
        float btnWidth = 46f/3f;
        float btnHeight = 41f/3f;

        public BtnVolting(int buttonId, int x, int y, String buttonText) {
            super(buttonId, x, y, buttonText);
        }

        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
        {
            float circleWidth = 39/3f;


            minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\gui\\circle_one.png"));
            Render.drawTexture(this.x, this.y+1, circleWidth, circleWidth, 0, 0, 1, 1, 6, 1f);
            if (active)
            {
                minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\gui\\check.png"));
                Render.drawTexture(this.x, this.y, btnWidth, btnHeight, 0, 0, 1, 1, 6, 1f);
            }

        }
    }
    public class BtnConfirm extends GuiButton
    {
        public BtnConfirm(int buttonId, int x, int y, String buttonText, String test)
        {

            super(buttonId, x, y, buttonText);

            this.width = 211 / 3;
            this.height = 78 / 3;

        }
        public boolean active = false;
        float btnConfirmWidth = 211f/3f;
        float btnConfirmHeight = 78f/3f;

        public BtnConfirm(int buttonId, int x, int y, String buttonText) {
            super(buttonId, x, y, buttonText);
        }

        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
        {
            String active = (mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height) ? "_active" : "";

            minecraft.renderEngine.bindTexture(new ResourceLocation(Night.RESOURCEID, "textures\\gui\\select.png"));
            if(active.equals("_active"))
                Render.drawTexture(this.x, this.y, btnConfirmWidth, btnConfirmHeight, 0, 0, 1, 1, 6, 1, 0.5f, 0.5f, 0.5f);
            else
                Render.drawTexture(this.x, this.y, btnConfirmWidth, btnConfirmHeight, 0, 0, 1, 1, 6, 1, 1f, 1f, 1f);

        }
    }

}
