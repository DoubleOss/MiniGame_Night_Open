package com.doubleos.night.packet;

import com.doubleos.night.util.ImageInfo;
import com.doubleos.night.variable.CheckReady;
import com.doubleos.night.variable.MainGame;
import com.doubleos.night.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CGangsterPlayerListSet implements IMessage, IMessageHandler<CGangsterPlayerListSet, CGangsterPlayerListSet>
{

    int m_count = 0;
    String m_PlayerName = "";

    public CGangsterPlayerListSet(){}
    public CGangsterPlayerListSet(int count, String playerName)
    {
        m_count = count;
        m_PlayerName = playerName;

    }
    @Override
    public void fromBytes(ByteBuf buf)
    {
        m_count = buf.readInt();
        m_PlayerName = ByteBufUtils.readUTF8String(buf);

    }

    @Override
    public void toBytes(ByteBuf buf)
    {

        buf.writeInt(m_count);
        ByteBufUtils.writeUTF8String(buf, m_PlayerName);
    }

    @Override
    public CGangsterPlayerListSet onMessage(CGangsterPlayerListSet message, MessageContext ctx)
    {
        //클라이언트 단
        // 참여하는 플레이어의 ID값이 들어옴
        Variable variable = Variable.Instance();

        ArrayList<ImageInfo> imageListInfo = variable.m_hashGetPngInfo.get("플레이어");
        //게임에 참여하는 플레이어 등록
        CheckReady checkReady = new CheckReady();
        checkReady.m_ready = false;
        checkReady.m_name = message.m_PlayerName;

        Minecraft.getMinecraft().addScheduledTask(()->
        {
            variable.m_readyList.add(checkReady);
            
            Variable.getMainGame().getGamePlayerName().add(message.m_PlayerName);
            Variable.getMainGame().getSaveGamePlayer().add(message.m_PlayerName);

            //현제 게임 상태 클라이언트 단에서 반영
            //다운로드 하는 과정으로 변경 gamePhase 를 통해 UI 상태가 변경됨
            Variable.getMainGame().gamePhase = MainGame.eGamePhase.PLAYER_HEAD_DOWNLOADING;
        });
        
        //외부 사이트에서 이미지 비동기 다운로드
        CompletableFuture<ImageInfo> future = CompletableFuture.supplyAsync(() ->
        {
            //return new ImageInfo(message.m_PlayerName, "https://cravatar.eu/helmavatar/"+message.m_PlayerName+"/128", "http://minotar.net/armor/body/"+message.m_PlayerName+"/400.png");
            return new ImageInfo(message.m_PlayerName, "http://minotar.net/helm/"+message.m_PlayerName+"/128.png", "http://minotar.net/armor/body/"+message.m_PlayerName+"/400.png");

        });

        ImageInfo info = null;
        try {
            info = future.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        if(info != null)
        {
            ImageInfo finalInfo2 = info;
            Minecraft.getMinecraft().addScheduledTask(()->
            {
                //웹에서 받은 이미지 BufferImage로 변경 이후 OpenGl TextureId 로 각 클라이언트 플레이어 얼굴 이미지 저장
                finalInfo2.m_info.id = (new DynamicTexture(finalInfo2.m_info.image)).getGlTextureId();
                finalInfo2.m_standInfo.id = (new DynamicTexture(finalInfo2.m_standInfo.image)).getGlTextureId();

                if(imageListInfo.size() == message.m_count)
                {
                    imageListInfo.add(finalInfo2);
                }
                else if(imageListInfo.size() > message.m_count)
                {
                    imageListInfo.set(message.m_count, finalInfo2);
                }

                if(imageListInfo.size() >= variable.playerCount)
                {
                    //모든 플레이어 얼굴 이미지 다운로드 끝나면 서버쪽으로 준비 완료 카운팅 전송
                    Packet.networkWrapper.sendToServer(new SPacketDownCounting());
                }
            });

        }



        return null;
    }
}
