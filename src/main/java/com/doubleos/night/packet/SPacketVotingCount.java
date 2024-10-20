package com.doubleos.night.packet;

import com.doubleos.night.variable.GameCardInfo;
import com.doubleos.night.variable.MainGame;
import com.doubleos.night.variable.Variable;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SPacketVotingCount implements IMessage, IMessageHandler<SPacketVotingCount, SPacketVotingCount>
{

    String playerName = "";
    String usePlayerName = "";
    public SPacketVotingCount(){}

    public SPacketVotingCount(String playerName, String usePlayer)
    {
        this.playerName = playerName;
        this.usePlayerName = usePlayer;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        playerName = ByteBufUtils.readUTF8String(buf);
        usePlayerName = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeUTF8String(buf, playerName);
        ByteBufUtils.writeUTF8String(buf, usePlayerName);
    }

    @Override
    public SPacketVotingCount onMessage(SPacketVotingCount message, MessageContext ctx)
    {
        ctx.getServerHandler().player.getServer().addScheduledTask(()->
        {
            EntityPlayerMP player = ctx.getServerHandler().player.getServer().getPlayerList().getPlayerByUsername(message.usePlayerName);
            MainGame game = Variable.getMainGame();
            
            //투표 해시맵에 값 추가
            int count = game.getHashPlayerVotingCount().getOrDefault(message.playerName, 0);
            count += 1;
            //투표 완료했으면 완료 상태로 변경
            game.getHashPlayerVotingCount().put(message.playerName, count);
            game.getHashPlayerVotingCheck().put(message.playerName, "완료");

            for(ItemStack stack : player.inventory.mainInventory)
            {
                if(stack.getDisplayName().equals(MainGame.REASON_VOTING_PAPER.getDisplayName()))
                    player.inventory.deleteStack(stack);
            }

            player.sendMessage(new TextComponentString("교도관 투표를 " + message.playerName + " 님에게 투표했습니다."));
            game.setReasonCrimeVotingCount(game.getReasonCrimeVotingCount()+1);
            //투표 완료한 사람은 완료로 보이도록 모든 클라이언트들에게 전송
            Packet.networkWrapper.sendToAll(new CPacketPlayerVotingPositionSync(message.usePlayerName, "완료"));
            if(game.getReasonCrimeVotingCount() >= game.getGamePlayerName().size())
            {
                game.ReasonVotingSuccess(ctx.getServerHandler().player.getServer());
            }

        });
        return null;
    }
}
