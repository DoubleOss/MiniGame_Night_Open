package com.doubleos.night.command;


import com.doubleos.night.block.tile.BlockTileCardView;
import com.doubleos.night.handler.HudConfig;
import com.doubleos.night.handler.SoundHandler;
import com.doubleos.night.packet.*;
import com.doubleos.night.variable.GameCardInfo;
import com.doubleos.night.variable.GameQuestionCard;
import com.doubleos.night.variable.MainGame;
import com.doubleos.night.variable.Variable;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.server.CommandSetBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import net.minecraftforge.fml.server.FMLServerHandler;

import javax.annotation.Nullable;
import java.util.*;

public class GuiCommand extends CommandBase
{

    @Override
    public String getName() {
        return "gn";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        World serverWorld = ((EntityPlayerMP) sender).world;
        World clientWorld = sender.getCommandSenderEntity().getEntityWorld();
        if(args.length >= 1)
        {
            Variable variable =Variable.Instance();
            EntityPlayer entPlayer = (EntityPlayer) sender;

            if(args[0].equalsIgnoreCase("인원등록"))
            {

            }
            if(args[0].equalsIgnoreCase("게임시작"))
            {
                //gn 게임시작 [인원수] 1 2 3 4 5 6
                if(!args[1].isEmpty())
                {
                    int size = Integer.parseInt(args[1]);

                    for(EntityPlayerMP player : server.getPlayerList().getPlayers())
                    {
                        player.sendMessage(new TextComponentString("잠시 클라이언트가 멈출 수 있습니다. 기다려주세요..."));
                    }
                    for(BlockPos pos : variable.prisonLocList)
                    {
                        server.getEntityWorld().setBlockState(pos.add(1, 0, 0), Blocks.AIR.getDefaultState());
                        server.getEntityWorld().setBlockState(pos.add(-1, 0, 0), Blocks.AIR.getDefaultState());
                        server.getEntityWorld().setBlockState(pos.add(0, 0, 1), Blocks.AIR.getDefaultState());
                        server.getEntityWorld().setBlockState(pos.add(0, 0, -1), Blocks.AIR.getDefaultState());

                    }

                    for(EntityPlayerMP player : server.getPlayerList().getPlayers())
                    {
                        Packet.networkWrapper.sendTo(new CPacketReset(), player);
                    }
                    for(EntityPlayerMP player : server.getPlayerList().getPlayers())
                    {
                        Packet.networkWrapper.sendTo(new CPacketImageListClear(), player);
                        Packet.networkWrapper.sendTo(new CPacketPlayerCount(size), player);
                    }
                    Variable.getMainGame().setGameStart(true);
                    Variable.getMainGame().gamePhase = MainGame.eGamePhase.PLAYER_HEAD_DOWNLOADING;
                    Packet.networkWrapper.sendToAll(new CPacketGamePhaseSync(Variable.getMainGame().gamePhase.name()));
                    for(int i = 0; i<size; i++)
                    {
                        Variable.getMainGame().getGamePlayerName().add(args[i+2]);
                    }
                    Collections.shuffle(Variable.getMainGame().getGamePlayerName());
                    for(int i = 0; i<size; i++)
                    {
                        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) server.getEntityWorld().getPlayerEntityByName(args[i+2]);
                        entityPlayerMP.setGameType(GameType.SURVIVAL);
                        for(ItemStack stack : entityPlayerMP.inventory.mainInventory)
                            entityPlayerMP.inventory.deleteStack(stack);
                        Packet.networkWrapper.sendToAll(new CGangsterPlayerListSet(i ,args[i+2]));
                    }

                    for(int i = 0; i<Variable.getMainGame().getGamePlayerName().size(); i++)
                    {
                        Variable.getMainGame().getSaveGamePlayer().add(Variable.getMainGame().getGamePlayerName().get(i));
                    }

                    Packet.networkWrapper.sendToAll(new CPacketPlaySound("curtain"));
//                    Variable.getMainGame().setSaveGamePlayer(Variable.getMainGame().getGamePlayerName());

                }
            }
            else if(args[0].equals("설정확인"))
            {
                entPlayer.sendMessage(new TextComponentString(" ---------작탁 의자 설정----------"));
                for (int i = 1; i < 7; i++)
                {
                    float x,y,z;
                    x = HudConfig.getFloat("night", "question_time_player_chair_"+i+"_pos_x");
                    y = HudConfig.getFloat("night", "question_time_player_chair_"+i+"_pos_y");
                    z = HudConfig.getFloat("night", "question_time_player_chair_"+i+"_pos_z");

                    entPlayer.sendMessage(new TextComponentString(" "+i+" 번 작탁의자 좌표: x "+ x + " y: " + y + " z: " + z));

                }
                entPlayer.sendMessage(new TextComponentString(" "));

                entPlayer.sendMessage(new TextComponentString(" ---------감옥 설정----------"));
                for (int i = 1; i < 7; i++)
                {
                    float x,y,z;
                    x = HudConfig.getFloat("night", "prison_loc_list_"+i+"_pos_x");
                    y = HudConfig.getFloat("night", "prison_loc_list_"+i+"_pos_y");
                    z = HudConfig.getFloat("night", "prison_loc_list_"+i+"_pos_z");

                    entPlayer.sendMessage(new TextComponentString(" "+i+" 번 감옥 좌표: x "+ x + " y: " + y + " z: " + z));

                }
                entPlayer.sendMessage(new TextComponentString(" "));
                float x,y,z;
                x = HudConfig.getFloat("night", "police_loc_x");
                y = HudConfig.getFloat("night", "police_loc_y");
                z = HudConfig.getFloat("night", "police_loc_z");
                entPlayer.sendMessage(new TextComponentString(" ---------교도관 감옥 설정----------"));
                entPlayer.sendMessage(new TextComponentString(" 교도관 감옥 좌표: x "+ x + " y: " + y + " z: " + z));
                entPlayer.sendMessage(new TextComponentString(" "));
                entPlayer.sendMessage(new TextComponentString(" ------카드 엔티티 설정--------"));
                for (int i = 1; i < 7; i++)
                {

                    x = HudConfig.getFloat("night", "prison_card_entity_loc_list_"+i+"_pos_x");
                    y = HudConfig.getFloat("night", "prison_card_entity_loc_list_"+i+"_pos_y");
                    z = HudConfig.getFloat("night", "prison_card_entity_loc_list_"+i+"_pos_z");

                    entPlayer.sendMessage(new TextComponentString(" "+i+" 번 카드 엔티티 좌표: x "+ x + " y: " + y + " z: " + z));

                }
                entPlayer.sendMessage(new TextComponentString(" "));
            }
            else if(args[0].equals("작탁의자설정"))
            {
                //gn 작탁의자설정 번호[1~6] 해당위치로 좌표 설정
                int number = Integer.parseInt(args[1]);
                BlockPos playerPos = ((EntityPlayerMP) sender).getPosition();
                HudConfig.writeConfig("night", "question_time_player_chair_"+number+"_pos_x", playerPos.getX());
                HudConfig.writeConfig("night", "question_time_player_chair_"+number+"_pos_y", playerPos.getY());
                HudConfig.writeConfig("night", "question_time_player_chair_"+number+"_pos_z", playerPos.getZ());
                entPlayer.sendMessage(new TextComponentString(" 작탁 "+number+" 번 위치가 설정되었습니다."));
                variable.questionTimePlayerChairLocList.set(number-1, playerPos);

            }
            else if(args[0].equals("감옥설정"))
            {
                //gn 감옥설정 번호[1~6] 해당위치로 좌표 설정
                int number = Integer.parseInt(args[1]);
                BlockPos playerPos = ((EntityPlayerMP) sender).getPosition();
                HudConfig.writeConfig("night", "prison_loc_list_"+number+"_pos_x", playerPos.getX());
                HudConfig.writeConfig("night", "prison_loc_list_"+number+"_pos_y", playerPos.getY());
                HudConfig.writeConfig("night", "prison_loc_list_"+number+"_pos_z", playerPos.getZ());
                entPlayer.sendMessage(new TextComponentString(" 감옥 "+number+" 번 위치가 설정되었습니다."));
                variable.prisonLocList.set(number-1, playerPos);


            }
            else if(args[0].equals("교도관감옥설정"))
            {
                //gn 교도관감옥설정 해당위치로 좌표 설정
                BlockPos playerPos = ((EntityPlayerMP) sender).getPosition();
                HudConfig.writeConfig("night", "police_loc_x", playerPos.getX());
                HudConfig.writeConfig("night", "police_loc_y", playerPos.getY());
                HudConfig.writeConfig("night", "police_loc_z", playerPos.getZ());
                entPlayer.sendMessage(new TextComponentString(" 교도관 감옥이 설정되었습니다."));
                variable.guard = playerPos;

            }
            else if(args[0].equals("엔티티블럭설정"))
            {
                //gn 엔티티블럭설정 해당위치로 좌표 설정
                int number = Integer.parseInt(args[1]);
                BlockPos playerPos = ((EntityPlayerMP) sender).getPosition();
                HudConfig.writeConfig("night", "prison_card_entity_loc_list_"+number+"_pos_x", playerPos.getX());
                HudConfig.writeConfig("night", "prison_card_entity_loc_list_"+number+"_pos_y", playerPos.getY());
                HudConfig.writeConfig("night", "prison_card_entity_loc_list_"+number+"_pos_z", playerPos.getZ());
                entPlayer.sendMessage(new TextComponentString(" 엔티티 블럭 위치가 설정되었습니다."));
                variable.cardEntityList.set(number-1, playerPos);

            }
            else if(args[0].equals("결과방설정"))
            {
                //gn 결과방설정
                BlockPos playerPos = ((EntityPlayerMP) sender).getPosition();
                variable.resultPos = playerPos;
                HudConfig.writeConfig("night", "result_loc_x", playerPos.getX());
                HudConfig.writeConfig("night", "result_loc_y", playerPos.getY());
                HudConfig.writeConfig("night", "result_loc_z", playerPos.getZ());
                entPlayer.sendMessage(new TextComponentString(" 결과방 위치가 설정되었습니다."));
            }
            else if(args[0].equals("페이즈변경"))
            {
                MainGame.eGamePhase gamePhase = MainGame.eGamePhase.valueOf(args[1]);
                MainGame maingame = Variable.getMainGame();
                maingame.gamePhase = gamePhase;
                Packet.networkWrapper.sendToAll(new CPacketGamePhaseSync(maingame.gamePhase.name()));
                entPlayer.sendMessage(new TextComponentString(" 게임 페이즈가  " + gamePhase.name() + " 으로 변경 되었습니다."));
            }
            else if(args[0].equals("리셋"))
            {
                Packet.networkWrapper.sendToAll(new CPacketReset());
                variable.reset();
                for(BlockPos pos : variable.prisonLocList)
                {
                    server.getEntityWorld().setBlockState(pos.add(1, 0, 0), Blocks.AIR.getDefaultState());
                    server.getEntityWorld().setBlockState(pos.add(-1, 0, 0), Blocks.AIR.getDefaultState());
                    server.getEntityWorld().setBlockState(pos.add(0, 0, 1), Blocks.AIR.getDefaultState());
                    server.getEntityWorld().setBlockState(pos.add(0, 0, -1), Blocks.AIR.getDefaultState());

                }

                entPlayer.sendMessage(new TextComponentString("모든 플레이어 설정이 리셋되었습니다."));
            }
            else if(args[0].equals("연출공지"))
            {
                //Packet.networkWrapper.sendToAll(new CPacketBroadCastAnimation("", "역할카드뽑기연출"));
                //Packet.networkWrapper.sendToAll(new CPacketBroadCastAnimation("", "질문카드뽑기연출"));
                //Packet.networkWrapper.sendToAll(new CPacketBroadCastAnimation("", "역할카드공개연출"));

                Packet.networkWrapper.sendToAll(new CPacketBroadCastAnimation("", args[1]));
                entPlayer.sendMessage(new TextComponentString("연출 공지를 설정했습니다."));
            }
            else if(args[0].equals("카드설정"))
            {
                int cardId = Integer.parseInt(args[1]);
                Packet.networkWrapper.sendTo(new CPacketPlayerCardSync(entPlayer.getName(), cardId), (EntityPlayerMP) sender);
                entPlayer.sendMessage(new TextComponentString("카드값을 설정했습니다."));
            }
            else if(args[0].equals("질문카드강제추가"))
            {
                //gn 질문카드강제추가 카드번호
                int cardId = Integer.parseInt(args[1]);

                GameQuestionCard card = variable.getHashIdToQuestonCard().get(cardId);
                Variable.getMainGame().getViewQuestionCardList().add(card);

                Packet.networkWrapper.sendToAll(new CPacketQuestionCardId(card.getCardId(), true));
                Packet.networkWrapper.sendToAll(new CPacketBroadCastAnimation("", "질문카드뽑기연출"));
                Packet.networkWrapper.sendToAll(new CPacketHashPlayerClear());
            }
            else if (args[0].equalsIgnoreCase("효과음"))
            {
                //escape 효과음 재생명 플레이어
                if(args.length == 2)
                    Packet.networkWrapper.sendTo(new CPacketPlaySound(args[1]), (EntityPlayerMP) entPlayer);
                else
                {
                    EntityPlayerMP playerMP = (EntityPlayerMP) server.getEntityWorld().getPlayerEntityByName(args[2]);
                    Packet.networkWrapper.sendTo(new CPacketPlaySound(args[1]), (EntityPlayerMP) playerMP);
                }

            }

            else if (args[0].equalsIgnoreCase("전체효과음"))
            {
                //escape 전체효과음 재생명
                for(EntityPlayerMP players : server.getPlayerList().getPlayers())
                {
                    Packet.networkWrapper.sendTo(new CPacketPlaySound(args[1]), (EntityPlayerMP) players);
                }

            }
            else if(args[0].equals("투표창"))
            {
                Packet.networkWrapper.sendTo(new CPacketVotingOpen(), (EntityPlayerMP) entPlayer);
            }
            else if(args[0].equals("결과연출"))
            {
                Variable.getMainGame().reasonDirectYesNo(server);
                entPlayer.sendMessage(new TextComponentString("결과연출 명령어를 사용했습니다."));
//                MainGame maingame = Variable.getMainGame();
//                ArrayList<Integer> list = new ArrayList<>(maingame.getUseCardIdList());
//                for(int i = 0; i<list.size(); i++)
//                {
//                    if(list.get(i) == 99)
//                    {
//                        list.remove(i);
//                    }
//                }
//                for(int i = 0; i< Variable.Instance().prisonLocList.size(); i++)
//                {
//                    BlockPos pos = Variable.Instance().prisonLocList.get(i);
//                    TileEntity tile = server.getEntityWorld().getTileEntity(pos.add(0, 2, 0));
//                    if(tile instanceof BlockTileCardView)
//                    {
//                        ((BlockTileCardView) tile).cardId = list.get(i);
//                    }
//                }
//                TileEntity tiles = server.getEntityWorld().getTileEntity(variable.guard.add(0, 2, 0));
//                if(tiles != null)
//                {
//
//                    BlockTileCardView view = (BlockTileCardView) tiles;
//                    view.cardId = 99;
//                }

            }
            else if(args[0].equals("교도관승리"))
            {
                int teleportCount = 0;
                for(int i = 0; i<Variable.getMainGame().getGamePlayerName().size(); i++)
                {
                    String searchPlayer = Variable.getMainGame().getSaveGamePlayer().get(i);
                    if(Variable.getMainGame().getHashPlayerToCardInfo().get(searchPlayer).getCardName().equals("교도관"))
                    {
                        EntityPlayerMP policePlayer = server.getPlayerList().getPlayerByUsername(searchPlayer);
                        policePlayer.addItemStackToInventory(MainGame.POLICE_STICK.copy());
                        policePlayer.sendMessage(new TextComponentString("사랑의 곤봉이 지급되었습니다."));
                    }
                    else
                    {
                        EntityPlayerMP player = server.getPlayerList().getPlayerByUsername(searchPlayer);
                        BlockPos pos = variable.prisonLocList.get(teleportCount++);
                        player.setPositionAndUpdate(pos.getX(), pos.getY(), pos.getZ());
                    }
                }
            }
            else if (args[0].equalsIgnoreCase("페이드연출"))
            {
                //gn 페이드연출 1 20
                if(!args[1].isEmpty() && !args[2].isEmpty() && !args[3].isEmpty() && !args[4].isEmpty())
                {
                    int aniTime = Integer.parseInt(args[1]);
                    int aniDelay = Integer.parseInt(args[2]);

                    Packet.networkWrapper.sendTo(new CPacketFadeEffectAdd(aniTime, aniDelay, args[3], args[4]), (EntityPlayerMP) sender);
                }

            }
        }

    }

    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        Variable variable = Variable.Instance();

        if(args.length == 1)
            return getListOfStringsMatchingLastWord(args, "게임시작");
        else if(args[0].equalsIgnoreCase("게임시작") && args.length >= 2)
        {
            return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
        }
        else if(args[0].equals("페이즈변경"))
        {
            return getListOfStringsMatchingLastWord(args, Arrays.asList(MainGame.eGamePhase.values()));
        }
        else if(args[0].equalsIgnoreCase("효과음") && args.length == 2 || args[0].equalsIgnoreCase("전체효과음") && args.length == 2 )
        {
            String[] soundList = SoundHandler.m_soundListString.toArray(new String[0]);
            return getListOfStringsMatchingLastWord(args, soundList);
        }
        else if(args[0].equalsIgnoreCase("효과음") && args.length >= 3)
        {
            return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
        }
        return Collections.emptyList();
    }
}
