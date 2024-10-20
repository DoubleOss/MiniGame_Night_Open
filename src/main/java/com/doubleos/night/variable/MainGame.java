package com.doubleos.night.variable;

import com.doubleos.night.block.tile.BlockTileCardView;
import com.doubleos.night.init.ModItems;
import com.doubleos.night.packet.*;
import com.doubleos.night.util.Render;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.server.SPacketTitle;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.server.FMLServerHandler;

import java.util.*;

public class MainGame
{


    //미니게임 사용 아이템 변수 선언
    public static final ItemStack QUESTION_CARD_RANDOM_DRAW_ITEM = new ItemStack(ModItems.QUESTIONCARD, 1, 0);
    public static final ItemStack ANSWER_YES_TOKEN_ITEM = new ItemStack(ModItems.YES_ITEM, 3, 0);
    public static final ItemStack ANSWER_NO_TOKEN_ITEM = new ItemStack(ModItems.NO_ITEM, 5, 0);

    public static final ItemStack REASON_VOTING = new ItemStack(ModItems.votrpaper, 1, 0);

    public static final ItemStack REASON_SKIP = new ItemStack(ModItems.votrpaper_wan2, 1, 0);


    public static final ItemStack REASON_VOTING_PAPER = new ItemStack(ModItems.votrpaper, 1, 0);

    public static final ItemStack REASON_VOTING_VIEW_EXIT = new ItemStack(ModItems.votrpaper_wan, 1, 0);

    public static final ItemStack POLICE_STICK = new ItemStack(ModItems.policebong, 1, 0);

    public static final ItemStack POLICE_ALL_OPEN = new ItemStack(Items.BOOK, 1, 0);

    public static final ItemStack POLICE_DECTIVE_ITEM = new ItemStack(ModItems.cuff, 1, 0);



    Variable variable = Variable.Instance();
    ArrayList<Integer> useCardIdList = new ArrayList<>();

    ArrayList<GameQuestionCard> viewQuestionCardList = new ArrayList<>();

    public ArrayList<GameQuestionCard> getHideQuestionCardList() {
        return hideQuestionCardList;
    }

    ArrayList<GameQuestionCard> hideQuestionCardList = new ArrayList<>();

    HashMap<String, GameCardInfo> hashPlayerToCardInfo = new HashMap<>();

    public HashMap<Integer, String> getHashCardIdToPlayerName() {
        return hashCardIdToPlayerName;
    }


    HashMap<Integer, String> hashCardIdToPlayerName= new HashMap<>();


    HashMap<String, Integer> hashPlayerCardYesAnswer = new HashMap<>();

    HashMap<String, String> hashReasonPosition = new HashMap<>();



    HashMap<String, Integer> hashPlayerVotingCount = new HashMap<>();


    HashMap<String, String> hashPlayerVotingCheck = new HashMap<>();


    HashMap<Integer, HashMap<String, String>> hashTurnCountToPlayerAnswer = new HashMap<>(); //해쉬 턴값 플레이어 포지션 값

    HashMap<String, String> hashPlayerToPlayerAnswer = new HashMap<>();

    public HashMap<String, String> getHashPlayerGameExit() {
        return hashPlayerGameExit;
    }

    HashMap<String, String> hashPlayerGameExit = new HashMap<>();

    public HashMap<String, Integer> getHashPlayerUseCardOpenCheck() {
        return hashPlayerUseCardOpenCheck;
    }


    HashMap<String, Integer> hashPlayerUseCardOpenCheck = new HashMap<>();
    int questionCardId = 0;


    public void setGamePlayerMcNumber(int gamePlayerMcNumber) {
        this.gamePlayerMcNumber = gamePlayerMcNumber;
    }

    int gamePlayerMcNumber = 0;
    int gameMcCount = 0;



    int gameTurn = 0;

    int delayTick = 0;

    int maxDelayTick = 80;



    int reasonCrimeTimeCardNumber = 0; //추리시간 카드 숫자



    int reasonCrimeVotingCount = 0;

    int reasonVotingExitCount = 0;

    int votingOpenPlayerCount = 0;

    public boolean isShowVotingResult() {
        return showVotingResult;
    }

    public void setShowVotingResult(boolean showVotingResult) {
        this.showVotingResult = showVotingResult;
    }

    boolean showVotingResult = false;

    ArrayList<String> gamePlayerName = new ArrayList<>();


    public ArrayList<String> getSaveGamePlayer() {
        return saveGamePlayer;
    }

    public void setSaveGamePlayer(ArrayList<String> saveGamePlayer) {
        this.saveGamePlayer = saveGamePlayer;
    }

    ArrayList<String> saveGamePlayer = new ArrayList<>();

    public eGamePhase gamePhase = eGamePhase.NONE;

    boolean nextPhase = false;


    boolean gameStart = false;

    boolean resultTeleport = false;


    public void setQuestionCardDraw(boolean questionCardDraw) {
        isQuestionCardDraw = questionCardDraw;
    }


    boolean crimeSkip = false;
    boolean isQuestionCardDraw = false;

    int dummyCardId = 80;

    boolean dummyCardSearch = false;

    String selectPlayer = "";


    public MainGame(){
        
        //사용할 아이템 체크
        QUESTION_CARD_RANDOM_DRAW_ITEM.setStackDisplayName("§f질문카드 뽑기");
        ANSWER_NO_TOKEN_ITEM.setStackDisplayName("§fNO");
        ANSWER_YES_TOKEN_ITEM.setStackDisplayName("§fYES");
        REASON_VOTING.setStackDisplayName("§f투표하기");
        REASON_SKIP.setStackDisplayName("§f회의 종료");
        REASON_VOTING_PAPER.setStackDisplayName("§f투표하기");
        REASON_VOTING_VIEW_EXIT.setStackDisplayName("§f창닫기");
        POLICE_STICK.setStackDisplayName("§d사랑의 곤봉");
        POLICE_ALL_OPEN.setStackDisplayName("§f교도관 결과 공개");
        POLICE_DECTIVE_ITEM.setStackDisplayName("§f교도관 추리 결과");


    }

    public void tick()
    {
        if(delayTick >= maxDelayTick)
        {

        }
    }

    public void changeTick()
    {
        delayTick = 0;
        nextPhase = true;

    }

    //서버단 카드 랜덤 셔플 후 플레이어 카드 지급
    public void suffleCard(MinecraftServer server)
    {
        Variable  variable = Variable.Instance();

        ArrayList<GameCardInfo> info = new ArrayList<>(variable.m_cardList);
        GameCardInfo policeInfo = null;
        for(GameCardInfo forInfo : variable.m_cardList)
        {
            if(forInfo.cardId == 99)
            {
                policeInfo = new GameCardInfo(forInfo.cardId, forInfo.cardName, forInfo.cardOpen);
                info.remove(forInfo);
            }
        }
        Collections.shuffle(info);

        Collections.shuffle(gamePlayerName);
        for(int i = 0; i<gamePlayerName.size(); i++)
        {
            Packet.networkWrapper.sendToAll(new CPacketGamePlayerSet(i, gamePlayerName.get(i)));
        }

        for(int i = 0; i<gamePlayerName.size()+1; i++)
        {
            Random random = new Random(System.currentTimeMillis());
            int cardId = random.nextInt(info.size());
            useCardIdList.add(info.get(cardId).cardId);
            info.remove(cardId);
            Collections.shuffle(info);
        }
        Collections.shuffle(useCardIdList); //6인플 기준 0 1 2 3 4 5 6
        int policeRandom = new Random(System.currentTimeMillis()).nextInt(gamePlayerName.size());

        for (int i = 0; i<gamePlayerName.size(); i++)
        {
            if(i == policeRandom)
            {
                GameCardInfo info3 = variable.m_hashIdToCard.get(99);
                hashPlayerToCardInfo.put(gamePlayerName.get(i), info3);
                hashCardIdToPlayerName.put(info3.cardId, gamePlayerName.get(i));
                useCardIdList.set((gamePlayerName.size()-1), useCardIdList.get(i));
                useCardIdList.set(i, 99);
            }
            else
            {
                GameCardInfo info3 = variable.m_hashIdToCard.get(useCardIdList.get(i));
                hashPlayerToCardInfo.put(gamePlayerName.get(i), info3);
                hashCardIdToPlayerName.put(info3.cardId, gamePlayerName.get(i));
            }

        }

        ArrayList<Integer> dummyCheck = new ArrayList<>(useCardIdList);

        for(int i = 0; i<gamePlayerName.size(); i++)
        {
            Integer value = Integer.valueOf(hashPlayerToCardInfo.get(gamePlayerName.get(i)).cardId);
            dummyCheck.remove(value);
        }

        dummyCardId = dummyCheck.get(0);

        Packet.networkWrapper.sendToAll(new CPacketDummyIdSync(dummyCardId));


        final int[] count = {0};

        Timer timer3 = new Timer();
        timer3.schedule(new TimerTask()
        {
               @Override
               public void run()
               {
           if(count[0] <6)
           {

               Packet.networkWrapper.sendToAll(new CPacketPlaySound("CARD"));
           }
           else
           {
               timer3.cancel();
           }
           count[0] +=1;

               }
        },0l, 400l);
        
        System.out.println("뽑기연출 시작 타이밍");

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run()
            {
                Packet.networkWrapper.sendToAll(new CPacketBroadCastAnimation("", "역할카드뽑기연출"));
                broadcastMessage(" 카드 분배가 완료 되었습니다.", server);
            }
        },4000l);

        Timer timer7 = new Timer();
        timer7.schedule(new TimerTask() {
            @Override
            public void run()
            {
                for (int i = 0; i<gamePlayerName.size(); i++)
                {

                    Packet.networkWrapper.sendToAll(new CPacketPlayerCardSync(gamePlayerName.get(i), hashPlayerToCardInfo.get(gamePlayerName.get(i)).getcardId()));
                    System.out.println(gamePlayerName.get(i) + " " + hashPlayerToCardInfo.get(gamePlayerName.get(i)).getcardId());

                }
                for(int i = 0; i<useCardIdList.size(); i++)
                {
                    Packet.networkWrapper.sendToAll(new CPacketUseCardAdd());
                }
                for(int i = 0; i<useCardIdList.size(); i++)
                {
                    Packet.networkWrapper.sendToAll(new CPacketUseCardSync(i, useCardIdList.get(i)));
                }
            }
        },4500l);

        Timer timer4 = new Timer();
        timer4.schedule(new TimerTask() {
            @Override
            public void run() {
                Packet.networkWrapper.sendToAll(new CPacketPlaySound("FLIP"));
            }
        }, 6000l);



        timer.schedule(new TimerTask()
        {
            @Override
            public void run() {
                gamePhase = eGamePhase.TRUN_REVERS_VIEW;
                Packet.networkWrapper.sendToAll(new CPacketGamePhaseSync(gamePhase.name()));
                trunRevers(server);
            }
        },10000l);

        hideQuestionCardList = new ArrayList<>(variable.m_questionCardList);

    }
    public void broadcastMessage(String message, MinecraftServer server)
    {
        for(EntityPlayerMP players : server.getPlayerList().getPlayers())
        {
            players.sendMessage(new TextComponentString(message));
        }
    }
    public void playerSequence(MinecraftServer server)
    {
        broadcastMessage(" 플레이어의 순서를 랜덤으로 배치하고 있습니다...", server);

        Timer timer = new Timer();
        Timer timer2 = new Timer();
        final int[] count = {0};


        TimerTask task = new TimerTask() {

            @Override
            public void run()
            {
                if(count[0] >= gamePlayerName.size())
                {
                    timer.cancel();
                    broadcastMessage(" ", server);
                    TimerTask task2 = new TimerTask() {

                        @Override
                        public void run() {
                            broadcastMessage(" 카드 분배 단계로 이동합니다...", server);
                            gamePhase = eGamePhase.CARD_SUFFLE;

                            Packet.networkWrapper.sendToAll(new CPacketGamePhaseSync(gamePhase.name()));

                            suffleCard(server);
                            timer.cancel();

                        }
                    };
                    timer2.schedule(task2, 1000l);

                }
                else
                {
                    BlockPos pos = variable.getQuestionTimePlayerChairLocList().get(count[0]);
                    EntityPlayerMP player = server.getPlayerList().getPlayerByUsername(gamePlayerName.get(count[0]));
                    if (player != null)
                        player.setPositionAndUpdate(pos.getX()+0.5f, pos.getY()+0.5f, pos.getZ()+0.5f);
                    count[0]++; // 카운트 증가
                }
            }
        };


        // 처음 작업을 스케줄링 (즉시 실행, 이후 3초마다 반복)
        timer.schedule(task, 0, 2000);

    }

    public void trunRevers(MinecraftServer server)
    {

        gamePlayerMcNumber = 0;
        Packet.networkWrapper.sendToAll(new CPacketGamePlayerMcNumber());
        gamePhase = eGamePhase.RANDOM_QUESTION_CARD_DRAW;

        Packet.networkWrapper.sendToAll(new CPacketGamePhaseSync(gamePhase.name()));

        for(String name : gamePlayerName)
        {
            EntityPlayerMP gamePlayer = server.getPlayerList().getPlayerByUsername(name);

            gamePlayer.inventory.addItemStackToInventory(ANSWER_YES_TOKEN_ITEM.copy());
            gamePlayer.inventory.addItemStackToInventory(ANSWER_NO_TOKEN_ITEM.copy());

        }

        randomQuestionCardDraw(server);

    }

    public void randomQuestionCardDraw(MinecraftServer server)
    {
        // 6. 순서 결정 후 결정된 플레이어가 [블럭or화면] 클릭하여 질문카드 뽑기 -> 아이템 줘서 카드뽑기?
        // 7. 뽑힌 질문카드가 각 플레이어 화면에서 표기됨 -> 이후 작은 표시로 존재[단축키로 확대 가능]
        broadcastMessage("  질문카드 뽑기 아이템이 " + gamePlayerName.get(gamePlayerMcNumber) + " 님께 지급되었습니다", server);
        EntityPlayerMP mcPlayer = server.getPlayerList().getPlayerByUsername(gamePlayerName.get(gamePlayerMcNumber));
        mcPlayer.addItemStackToInventory(QUESTION_CARD_RANDOM_DRAW_ITEM.copy()); // 뽑기 아이템 지급

    }

    public ArrayList<GameQuestionCard> getViewQuestionCardList() {
        return viewQuestionCardList;
    }

    public void onRightClickQuestionCardDrawEvent(PlayerInteractEvent.RightClickItem event)
    {
        if(gamePhase.equals(eGamePhase.RANDOM_QUESTION_CARD_DRAW) && event.getItemStack().getDisplayName().equals(QUESTION_CARD_RANDOM_DRAW_ITEM.getDisplayName())
                && event.getEntityPlayer().getName().equals(gamePlayerName.get(gamePlayerMcNumber)) && !isQuestionCardDraw)
        {

            gameTurn++; //게임 턴 증가
            Packet.networkWrapper.sendToAll(new CPacketGameTrunSync(gameTurn)); //모든 클라이언트에게 전부 턴값 전송 
            EntityPlayerMP entPlayer = (EntityPlayerMP) event.getEntityPlayer();
            Random random = new Random();
            random.setSeed(System.currentTimeMillis());
            entPlayer.inventory.deleteStack(event.getItemStack());
            
            broadcastMessage(" " + gamePlayerName.get(gamePlayerMcNumber) + " 님이 질문카드를 사용하셨습니다.", entPlayer.getServer());
            
            int randomCardNumber = random.nextInt(hideQuestionCardList.size());
            
            //카드 데아터에서 랜덤으로 카드 뽑기
            questionCardId = hideQuestionCardList.get(randomCardNumber).cardId;
            //나온 카드 데이터 저장
            viewQuestionCardList.add(hideQuestionCardList.get(randomCardNumber));
            //카드 데이터에서 중복방지로 뽑아내기
            hideQuestionCardList.remove(randomCardNumber);
            
            //연속 뽑기 방지 서버측 코드
            isQuestionCardDraw = true;

            Packet.networkWrapper.sendToAll(new CPacketQuestionCardId(questionCardId, isQuestionCardDraw)); // 뽑은 카드값 전송
            Packet.networkWrapper.sendToAll(new CPacketBroadCastAnimation("", "질문카드뽑기연출")); //카드 뽑기 애니메이션 재생 패킷
            Packet.networkWrapper.sendToAll(new CPacketHashPlayerClear());
            Timer timer4 = new Timer();
            timer4.schedule(new TimerTask() {
                @Override
                public void run() {
                    Packet.networkWrapper.sendToAll(new CPacketPlaySound("FLIP"));
                }
            }, 1800l);

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run()
                {
                    gamePhase = eGamePhase.QUESTION_CARD_TIME;
                    Packet.networkWrapper.sendToAll(new CPacketGamePhaseSync(gamePhase.name()));

                    giveAnswerItem(entPlayer.getServer());

                }
            },4000l);
        }
    }
    public void giveAnswerItem(MinecraftServer server) //교도관 추리 순서
    {
        String playerName = gamePlayerName.get(gamePlayerMcNumber);

        broadcastMessage(" " + playerName + "님의 차례입니다.", server);
        broadcastMessage(" " + playerName + "질문을 보고 Yes / No 를 선택해주세요", server);

    }

    boolean turnStop = false; //yes 카드 3번 나올 경우 다음 턴으로 안감

    public void onRightClickAnswerToken(PlayerInteractEvent.RightClickItem event)
    {
        if(this.gamePhase.equals(eGamePhase.QUESTION_CARD_TIME) &&
                (event.getItemStack().getDisplayName().equals(ANSWER_NO_TOKEN_ITEM.getDisplayName()) || event.getItemStack().isItemEqual(ANSWER_YES_TOKEN_ITEM)))
        {
            if(event.getEntityPlayer().getName().equals(gamePlayerName.get(gamePlayerMcNumber)))
            {
                String playerName = gamePlayerName.get(gamePlayerMcNumber);
                String answer = event.getItemStack().getDisplayName();
                EntityPlayerMP entPlayer = (EntityPlayerMP) event.getEntityPlayer();
                //플레이어가 선택한 대답 값 HashMap 저장
                hashPlayerToPlayerAnswer.put(playerName, answer);
                //플레이어가 이번 턴에 사용한 대답 값 저장
                hashTurnCountToPlayerAnswer.put(gameTurn, hashPlayerToPlayerAnswer);

                answer = answer.replaceAll("§f", "");

                if(answer.equalsIgnoreCase("YES"))
                {
                    int count = hashPlayerCardYesAnswer.getOrDefault(playerName, 0);
                    count +=1;
                    hashPlayerCardYesAnswer.put(playerName, count);
                    //플레이어가 YES 대답을 3번 했을 경우 턴 진행 X
                    if(count >= 3)
                        turnStop = true;
                }
                //모든 플레이어에게 소리 재생
                Packet.networkWrapper.sendTo(new CPacketPlaySound("TOKEN"), entPlayer);
                broadcastMessage(" " + playerName + " 님이 토큰을 내셨습니다.", entPlayer.getServer());
                
                //게임에 참여하는 모든 플레이어에게 이번 턴에 플레이어가 무슨 대답을 했는지 각 클라이언트에 전송
                Packet.networkWrapper.sendToAll(new CPacketHashTrunCountToPlayerAnswer(gameTurn, playerName, answer));
                
                //플레이어 턴 카운트 늘리기
                gamePlayerMcNumber += 1;

                
                //인벤토리 아이템 소모 로직
                for (int i = 0; i < entPlayer.inventory.getSizeInventory(); i++)
                {
                    ItemStack slotStack = entPlayer.inventory.getStackInSlot(i);
                    if (slotStack.getDisplayName().equals(event.getItemStack().getDisplayName()))
                    {
                        int stackSize = slotStack.getCount();
                        if (stackSize >= 1) {
                            entPlayer.inventory.decrStackSize(i, 1);
                            break;
                        } else {
                            entPlayer.inventory.decrStackSize(i, 1);
                        }
                    }
                }

                if(gameMcCount >= 4 && gamePlayerMcNumber >= gamePlayerName.size() || turnStop && gamePlayerMcNumber >= gamePlayerName.size())
                {
                    broadcastMessage(" 추리 시간으로 넘어갑니다..", entPlayer.getServer());
                    turnStop = false;
                    for (ItemStack stack : entPlayer.inventory.mainInventory)
                    {
                        if (stack.getDisplayName().equals(ANSWER_NO_TOKEN_ITEM.getDisplayName()) || stack.getDisplayName().equals(ANSWER_YES_TOKEN_ITEM.getDisplayName()))
                        {
                            entPlayer.inventory.deleteStack(stack);
                        }
                    }
                    Collections.shuffle(useCardIdList);
                    Packet.networkWrapper.sendToAll(new CPacketUserCardIdListClear());
                    for(Integer cardId : useCardIdList)
                    {
                        Packet.networkWrapper.sendToAll(new CPacketUserCardIdAdd(cardId));
                    }
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run()
                        {

                            gamePhase = eGamePhase.REASON_CIRME_VIEW;
                            Packet.networkWrapper.sendToAll(new CPacketGamePhaseSync(gamePhase.name()));
                            ReasonCrimeTime(entPlayer.getServer());
                            ReasonCrimeItemGive(entPlayer.getServer());
                        }
                    },4000l);
                }
                else
                {
                    if( gamePlayerMcNumber >= gamePlayerName.size()) // 대답 다 봤을 경우
                    {
                        gamePhase = eGamePhase.RANDOM_QUESTION_CARD_DRAW;
                        gamePlayerMcNumber = 0;
                        Packet.networkWrapper.sendToAll(new CPacketGamePlayerMcNumber(gamePlayerMcNumber));
                        Packet.networkWrapper.sendToAll(new CPacketGamePhaseSync(gamePhase.name()));
                        isQuestionCardDraw = false;
                        broadcastMessage(" 모든 플레이어가 응답하여 다음 질문카드 뽑기로 넘어갑니다.", entPlayer.getServer());
                        turnStop = false;
                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run()
                            {
                                randomQuestionCardDraw(event.getEntity().getServer());
                            }
                        },4000l);
                        gameMcCount += 1;


                    }
                    else
                    {
                        //턴 표시 넘기기 위한 클라이언트에게 현재 턴 차례 전송
                        Packet.networkWrapper.sendToAll(new CPacketGamePlayerMcNumber(gamePlayerMcNumber));
                        giveAnswerItem(entPlayer.getServer());
                    }
                }

            }
        }
    }

    public void CardOpen()
    {

    }
    public void ReasonCrimeItemGive(MinecraftServer server) //교도관 추리 순서
    {

        for(String playerName : gamePlayerName)
        {
            EntityPlayerMP playerMp = server.getPlayerList().getPlayerByUsername(playerName);
            for(ItemStack stack : playerMp.inventory.mainInventory)
            {
                if(stack.getDisplayName().equals(ANSWER_NO_TOKEN_ITEM.getDisplayName()) || stack.getDisplayName().equals(ANSWER_YES_TOKEN_ITEM.getDisplayName()))
                {
                    playerMp.inventory.deleteStack(stack);
                }
            }
            playerMp.inventory.addItemStackToInventory(REASON_SKIP.copy());

        }
    }



    public void ReasonCrimeTime(MinecraftServer server) //범죄카드 1장식 추리 시간 //덮어진 카드 공개까지 이뤄줘야함
    {

        broadcastMessage(" 게임에 사용된 직업카드가 공개 됩니다.", server);
        broadcastMessage(" 각자의 역할을 이야기 하면서 추론해 주시고, 회의종료를 원할경우 아이템을 우클릭 해주세요.", server);
        Packet.networkWrapper.sendToAll(new CPacketBroadCastAnimation("", "역할카드공개연출"));
        Timer timer4 = new Timer();
        timer4.schedule(new TimerTask() {
            @Override
            public void run() {
                Packet.networkWrapper.sendToAll(new CPacketPlaySound("FLIP"));
            }
        }, 1800l);
        hashReasonPosition.clear();
        Packet.networkWrapper.sendToAll(new CPacketReasonPositionClear());
    }
    public void onRightClickReasonVotingEvent(PlayerInteractEvent.RightClickItem event)
    {
        if(gamePhase.equals(eGamePhase.REASON_CIRME_VIEW) && event.getItemStack().getDisplayName().equals(REASON_SKIP.getDisplayName()))
        {
            EntityPlayerMP entPlayer = (EntityPlayerMP) event.getEntityPlayer();
            if(event.getItemStack().getDisplayName().equals(REASON_VOTING.getDisplayName())) //우클릭한 아이템이 투표일 경우
            {

            }
            else
            {
                if(hashReasonPosition.get(event.getEntityPlayer().getName()) == null)
                {
                    hashReasonPosition.put(event.getEntityPlayer().getName(), "종료");
                    Packet.networkWrapper.sendToAll(new CPacketReasonPositionAdd(event.getEntityPlayer().getName(), "종료"));
                    event.getEntityPlayer().sendMessage(new TextComponentString(" 회의종료에 선택하셨습니다."));
                }
                else if (hashReasonPosition.get(event.getEntityPlayer().getName()).equals("종료"))
                {
                    hashReasonPosition.put(event.getEntityPlayer().getName(), "취소");
                    Packet.networkWrapper.sendToAll(new CPacketReasonPositionAdd(event.getEntityPlayer().getName(), "취소"));
                    event.getEntityPlayer().sendMessage(new TextComponentString(" 회의종료를 취소하셨습니다."));
                }
                else
                {
                    hashReasonPosition.put(event.getEntityPlayer().getName(), "종료");
                    Packet.networkWrapper.sendToAll(new CPacketReasonPositionAdd(event.getEntityPlayer().getName(), "종료"));
                    event.getEntityPlayer().sendMessage(new TextComponentString(" 회의종료에 선택하셨습니다."));
                }



            }

            if(hashReasonPosition.size() >= gamePlayerName.size())
            {
                int skipCount = 0;
                for(int i = 0; i<gamePlayerName.size(); i++)
                {
                    String votingStr = hashReasonPosition.getOrDefault(gamePlayerName.get(i), "");
                    if (votingStr.equals("종료"))
                        skipCount+=1;
                }
                if(skipCount >= gamePlayerName.size()) // 동률 or 스킵
                {
                    broadcastMessage(" 모든플레이어가 회의 종료에 동의하여 투표단계로 이동합니다.", entPlayer.getServer());
                    gamePhase = eGamePhase.REASON_VOTING_TIME;
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run()
                        {
                            Packet.networkWrapper.sendToAll(new CPacketGamePhaseSync(gamePhase.name()));
                            ReasonVotingTime(entPlayer.getServer());
                        }
                    },4000l);
                }

            }
        }
    }
    public HashMap<String, String> getHashPlayerVotingCheck() {
        return hashPlayerVotingCheck;
    }

    public void setHashPlayerVotingCheck(HashMap<String, String> hashPlayerVotingCheck) {
        this.hashPlayerVotingCheck = hashPlayerVotingCheck;
    }

    public void ReasonVotingTime(MinecraftServer server)
    {
        for(String playerName : gamePlayerName)
        {
            EntityPlayerMP entPlayer = server.getPlayerList().getPlayerByUsername(playerName);
            for (ItemStack stack : entPlayer.inventory.mainInventory)
            {
                if (stack.getDisplayName().equals(REASON_SKIP.getDisplayName()) || stack.getDisplayName().equals(REASON_VOTING.getDisplayName()))
                {
                    entPlayer.inventory.deleteStack(stack);
                }
            }
            entPlayer.inventory.addItemStackToInventory(REASON_VOTING_PAPER.copy());

        }

        broadcastMessage(" 투표용지가 지급되었습니다.", server);
        broadcastMessage(" 교도관으로 의심되는 플레이어를 투표하여 주십시요", server);
        hashPlayerVotingCount.clear();
        reasonCrimeVotingCount = 0;
        reasonVotingExitCount = 0;
        hashPlayerVotingCheck.clear();
        Packet.networkWrapper.sendToAll(new CPacketPlayerVotingClear());


    }
    public void onRightClickReasonVotingItemEvent(PlayerInteractEvent.RightClickItem event)
    {
        if(gamePhase.equals(eGamePhase.REASON_VOTING_TIME) && (event.getItemStack().getDisplayName().equals(REASON_VOTING_PAPER.getDisplayName())))
        {
            Packet.networkWrapper.sendTo(new CPacketVotingGuiOpen(), (EntityPlayerMP) event.getEntityPlayer());
        }
    }

    public void ReasonVotingSuccess(MinecraftServer server)
    {
        broadcastMessage(" 모든 플레이어가 투표를 끝냈습니다.", server);
        gamePhase = eGamePhase.REASON_VOTING_VIEW;

        //해시맵 결과 집계 모든 플레이어에게 전송
        for(String name : gamePlayerName)
        {
            int count = hashPlayerVotingCount.getOrDefault(name, 0);
            Packet.networkWrapper.sendToAll(new CPacketVotingSync(name, count));
        }


        ArrayList<Integer> list = new ArrayList<>(getUseCardIdList());
        for(int i = 0; i<list.size(); i++)
        {
            if(list.get(i) == 99)
            {
                list.remove(i);
            }
        }
        for(int i = 0; i< Variable.Instance().prisonLocList.size(); i++)
        {
            BlockPos pos = Variable.Instance().prisonLocList.get(i);
            TileEntity tile = server.getEntityWorld().getTileEntity(pos.add(0, 2, 0));
            if (i < list.size())
            {
                if(tile instanceof BlockTileCardView)
                {
                    ((BlockTileCardView) tile).cardId = list.get(i);
                }
            }

        }
        TileEntity tiles = server.getEntityWorld().getTileEntity(variable.guard.add(0, 2, 0));
        if(tiles != null)
        {
            BlockTileCardView view = (BlockTileCardView) tiles;
            view.cardId = 99;
        }

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run()
            {
                broadcastMessage(" 투표 결과를 공개합니다.", server);
                broadcastMessage(" 결과 창을 닫으시려면 지급된 아이템을 우클릭 해주세요.", server);
                broadcastMessage(" 결과 창을 닫아야 다음 스탭으로 이동됩니다.", server);

                Packet.networkWrapper.sendToAll(new CPacketGamePhaseSync(gamePhase.name()));
                Packet.networkWrapper.sendToAll(new CPacketReasonVotingExit(true));
                for(String playerName : gamePlayerName)
                {
                    EntityPlayerMP playerMp = server.getPlayerList().getPlayerByUsername(playerName);
                    for(ItemStack stack : playerMp.inventory.mainInventory)
                        if(stack.getDisplayName().equals(REASON_VOTING_PAPER.getDisplayName()))
                            playerMp.inventory.deleteStack(REASON_VOTING_PAPER);
                    playerMp.inventory.addItemStackToInventory(REASON_VOTING_VIEW_EXIT.copy());

                }
            }
        },4000l);

    }
    public static boolean hasTie(ArrayList<Integer> scores, int maxNumber)
    {
        Set<Integer> scoreSet = new HashSet<>();
        for (int score : scores)
        {
            if (!scoreSet.add(score) && maxNumber == score) {
                return true;
            }
        }
        return false;
    }


    public void onRightClickReasonVotingViewExitItemEvent(PlayerInteractEvent.RightClickItem event)
    {
        if(gamePhase.equals(eGamePhase.REASON_VOTING_VIEW) && (event.getItemStack().getDisplayName().equals(REASON_VOTING_VIEW_EXIT.getDisplayName())))
        {
            Packet.networkWrapper.sendTo(new CPacketReasonVotingExit(false), (EntityPlayerMP) event.getEntityPlayer());
            EntityPlayerMP entPlayer = (EntityPlayerMP) event.getEntityPlayer();
            //event.getEntityPlayer().sendMessage(new TextComponentString(" 결과창을 닫았습니다."));
            broadcastMessage(event.getEntityPlayer().getName() + " 님이 투표 결과 창을 닫았습니다. ", entPlayer.getServer());
            event.getEntityPlayer().inventory.deleteStack(event.getItemStack());

            reasonVotingExitCount+=1;
            if(reasonVotingExitCount >= gamePlayerName.size())
            {
                Packet.networkWrapper.sendToAll(new CPacketReasonVotingExit(false));
                boolean check = false;
                int maxCount = 1;
                int maxNumber = 99;
                //중복 로직
                for(int i = 0; i<gamePlayerName.size(); i++)
                {
                    int count = hashPlayerVotingCount.getOrDefault(gamePlayerName.get(i), 0);
                    if(maxCount < count)
                    {
                        maxCount = count;
                        maxNumber = i;
                        check = false;
                    }
                    if (maxCount == count && maxNumber != i)
                    {
                        check = true;
                    }
                }
                if(check)
                {
                    broadcastMessage(" 투표결과 동률이 발생하여 재투표를 진행합니다.", entPlayer.getServer());
                    Packet.networkWrapper.sendToAll(new CPacketReasonVotingExit(false));
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run()
                        {
                            gamePhase = eGamePhase.REASON_VOTING_TIME;
                            Packet.networkWrapper.sendToAll(new CPacketGamePhaseSync(gamePhase.name()));
                            ReasonVotingTime(entPlayer.getServer());
                        }
                    },4000l);
                }
                else
                {
                    selectPlayer = gamePlayerName.get(maxNumber);
                    Packet.networkWrapper.sendToAll(new CPacketReasonVotingExit(false));
                    //broadcastMessage(" 결과 방으로 이동됩니다.", entPlayer.getServer());

                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run()
                        {
                            if(!resultTeleport)
                            {
                                resultTeleport = true;
                                for(String playerName : gamePlayerName)
                                {
                                    EntityPlayerMP playerMp = event.getEntityPlayer().getServer().getPlayerList().getPlayerByUsername(playerName);
                                    playerMp.setPositionAndRotation(variable.resultPos.getX(), variable.resultPos.getY(), variable.resultPos.getZ(), 120, 90); // 추리방 이동

                                }
                            }
//                            hashPlayerGameExit.put(selectPlayer, "탈락");
//                            Packet.networkWrapper.sendToAll(new CPacketHashPlayerGameExit(selectPlayer, "탈락"));
//                            gamePlayerName.remove(selectPlayer); // 해당 플레이어 지우고 다시 진행
                            EntityPlayerMP givePlayer = entPlayer.getServer().getPlayerList().getPlayerByUsername(gamePlayerName.get(0));
                            givePlayer.inventory.addItemStackToInventory(POLICE_ALL_OPEN.copy());
                            broadcastMessage(" 교도관 공개 아이템이 " + gamePlayerName.get(0) + " 님에게 지급되었습니다.", entPlayer.getServer());
//                            EntityPlayerMP playerSelect = event.getEntityPlayer().getServer().getPlayerList().getPlayerByUsername(selectPlayer);
//                            int playerCardId = hashPlayerToCardInfo.get(playerSelect.getName()).getcardId();
//                            int resultNumber = 0;
//                            for(int i = 0; i<useCardIdList.size(); i++)
//                            {
//                                if(playerCardId == useCardIdList.get(i))
//                                {
//                                {
//                                    resultNumber = i;
//                                }
//                            }
//                            BlockPos teleportPos = variable.prisonLocList.get(resultNumber);
//                            playerSelect.setPositionAndUpdate(teleportPos.getX(), teleportPos.getY(), teleportPos.getZ());

                            //reasonDirectYesNo(entPlayer.getServer());
                            
                        }
                    },4000l);
                }

            }
        }
    }

    public void reasonDirectYesNo(MinecraftServer server)
    {
        //해당 함수는 명령어로 호출 예정
        gamePhase = eGamePhase.REASON_DIRECT_Y_N;
        Packet.networkWrapper.sendToAll(new CPacketGamePhaseSync(gamePhase.name()));

        //카드 그릴 엔티티 소환 
        
        //해당 플레이어는 교도관이   -> 맞/ 아닙니다 띄우고

        //이부분에서 연출 보여줘야함
        
        boolean policeCheck = false;

        hashPlayerGameExit.put(selectPlayer, "탈락");
        Packet.networkWrapper.sendToAll(new CPacketHashPlayerGameExit(selectPlayer, "탈락"));
        gamePlayerName.remove(selectPlayer); // 해당 플레이어 지우고 다시 진행

        String result = "X";
        if(hashPlayerToCardInfo.get(selectPlayer).getCardName().equals("교도관"))
        {
            policeCheck = true;
            result = "O";
        }
        else
        {
            policeCheck = false;
            result = "X";
        }

        if(policeCheck)
        {
            Packet.networkWrapper.sendToAll(new CPacketFadeEffectAdd(1, 14, result, selectPlayer));

            Timer timer4 = new Timer();
            timer4.schedule(new TimerTask() {
                @Override
                public void run() {
                    Packet.networkWrapper.sendToAll(new CPacketPlaySound("CORRECT"));
                }
            }, 1500l);

//            Timer timer4 = new Timer();
//            timer4.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    Packet.networkWrapper.sendToAll(new CPacketPlaySound("DONE"));
//                }
//            }, 10000l);

            EntityPlayerMP playerMP = server.getPlayerList().getPlayerByUsername(selectPlayer);

            //playerMP.setPositionAndUpdate(variable.guard.getX(), variable.guard.getY(), variable.guard.getZ());

            Packet.networkWrapper.sendToAll(new CPacketReasonVotingExit(false));

            ArrayList<Integer> resultCardId = new ArrayList<>(useCardIdList);

            for(String name : saveGamePlayer)
            {
                if( hashPlayerGameExit.getOrDefault(name, "").equals("탈락"))
                {
                    System.out.println(" 탈락 카드 :  " + hashPlayerToCardInfo.get(name).cardId + "   " + hashPlayerToCardInfo.get(name).cardName);
                    resultCardId.remove(Integer.valueOf(hashPlayerToCardInfo.get(name).cardId) );
                }
            }
            if(dummyCardSearch)
                resultCardId.remove(Integer.valueOf(dummyCardId));
            resultCardId.remove(Integer.valueOf(99));


            Collections.shuffle(resultCardId);
            policeDectiveCardId = resultCardId.get(0);

            Packet.networkWrapper.sendToAll(new CPacketPoliceDectiveCardId(policeDectiveCardId));

            System.out.println( " 선택된 경찰 추리 카드 : policeDectiveCardId:  " + policeDectiveCardId );

            Timer timer3 = new Timer();
            timer3.schedule(new TimerTask() {
                @Override
                public void run() {
                    broadcastMessage(" 교도관이 발견되어 교도관의 추리 시간으로 넘어갑니다.", server);
                    broadcastMessage(" 지급된 아이템 우클릭 할때 마다 화면에 표시된 직업부터 결과를 공개합니다.", server);
                    gamePhase = eGamePhase.POLICE_DETECTIVE;
                    playerMP.inventory.addItemStackToInventory(POLICE_DECTIVE_ITEM.copy());
                    Packet.networkWrapper.sendToAll(new CPacketGamePhaseSync(gamePhase.name()));

                }
            }, 12000l);
            //교도관이 잡혔을 때 추가 로직
            //투표용지 개조해서 교도관이 추리하는 상황 보여주기
        }
        else
        {
            Packet.networkWrapper.sendToAll(new CPacketFadeEffectAdd(1, 14, result, selectPlayer));

            Timer timer4 = new Timer();
            timer4.schedule(new TimerTask() {
                @Override
                public void run() {
                    Packet.networkWrapper.sendToAll(new CPacketPlaySound("CORRECT"));
                }
            }, 1500l);
//            Timer timer4 = new Timer();
//            timer4.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    Packet.networkWrapper.sendToAll(new CPacketPlaySound("FAIL"));
//                }
//            }, 6000l);
            Timer timer3 = new Timer();
            timer3.schedule(new TimerTask() {
                @Override
                public void run()
                {
                    if (gamePlayerName.size() == 2)
                    {

                        broadcastMessage(" 투표 인원이 부족하여 추리 시간으로 넘어갑니다.", server);
                        broadcastMessage(" 교도관은 지급된 아이템을 우클릭 할때 마다 화면에 표시된 직업 결과를 공개합니다.", server);

                        String policePlayer = hashCardIdToPlayerName.get(99);
                        EntityPlayerMP policePlayerMp = server.getPlayerList().getPlayerByUsername(policePlayer);
                        policePlayerMp.inventory.addItemStackToInventory(POLICE_DECTIVE_ITEM.copy());
                        gamePhase = eGamePhase.POLICE_DETECTIVE;
                        Packet.networkWrapper.sendToAll(new CPacketGamePhaseSync(gamePhase.name()));
                        Packet.networkWrapper.sendToAll(new CPacketReasonVotingExit(false));


                        ArrayList<Integer> resultCardId = new ArrayList<>(useCardIdList);

                        for(String name : saveGamePlayer)
                        {
                            if( hashPlayerGameExit.getOrDefault(name, "").equals("탈락"))
                            {
                                resultCardId.remove(Integer.valueOf(hashPlayerToCardInfo.get(name).cardId));
                            }
                        }
                        if(dummyCardSearch)
                            resultCardId.remove(Integer.valueOf(dummyCardId));
                        resultCardId.remove(Integer.valueOf( 99));


                        Collections.shuffle(resultCardId);
                        policeDectiveCardId = resultCardId.get(0);

                        Packet.networkWrapper.sendToAll(new CPacketPoliceDectiveCardId(policeDectiveCardId));
                    }
                    else
                    {
                        broadcastMessage(" 죄수가 감옥에 들어가게되어 다시 투표를 진행합니다.", server);
                        gamePhase = eGamePhase.REASON_VOTING_TIME;

                        GameCardInfo info =  hashPlayerToCardInfo.get(selectPlayer);
                        EntityPlayerMP entityPlayerMP = server.getPlayerList().getPlayerByUsername(selectPlayer);

                        ArrayList<Integer> list = new ArrayList<>(getUseCardIdList());
                        for(int i = 0; i<list.size(); i++)
                        {
                            if(list.get(i) == 99)
                            {
                                list.remove(i);
                            }
                        }
                        for(int i = 0; i< Variable.Instance().prisonLocList.size(); i++)
                        {
                            BlockPos pos = Variable.Instance().prisonLocList.get(i);

                            TileEntity tile = server.getEntityWorld().getTileEntity(pos.add(0, 2, 0));
                            if(tile instanceof BlockTileCardView)
                            {
                                int cardId = ((BlockTileCardView) tile).cardId;
                                if(cardId == info.cardId)
                                {
                                    entityPlayerMP.setPositionAndUpdate(pos.getX()+0.5f, pos.getY(), pos.getZ()+0.5f);
                                    server.getEntityWorld().setBlockState(pos.add(1, 0, 0), Blocks.BARRIER.getDefaultState());
                                    server.getEntityWorld().setBlockState(pos.add(-1, 0, 0), Blocks.BARRIER.getDefaultState());
                                    server.getEntityWorld().setBlockState(pos.add(0, 0, 1), Blocks.BARRIER.getDefaultState());
                                    server.getEntityWorld().setBlockState(pos.add(0, 0, -1), Blocks.BARRIER.getDefaultState());
                                    hashPlayerUseCardOpenCheck.put(selectPlayer, cardId);
                                    Packet.networkWrapper.sendToAll(new CPacketHashPlayerUseCardOpenCheck(selectPlayer, cardId));

                                }
                            }
                        }
                    }

                }
            },10000l);
            // 아닐 경우 다시 투표
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run()
                {
                    Packet.networkWrapper.sendToAll(new CPacketGamePhaseSync(gamePhase.name()));
                    ReasonVotingTime(server);
                }
            },14000l);
        }

    }
    public void onRightClickPoliceOpenItemUse(PlayerInteractEvent.RightClickItem event)
    {
        if (gamePhase.equals(eGamePhase.REASON_VOTING_VIEW) && event.getItemStack().getDisplayName().equals(POLICE_ALL_OPEN.getDisplayName()))
        {
            event.getEntityPlayer().inventory.deleteStack(event.getItemStack());
            reasonDirectYesNo(event.getEntityPlayer().getServer());
        }
    }

    public int getPoliceDectiveCardId() {
        return policeDectiveCardId;
    }

    public void setPoliceDectiveCardId(int policeDectiveCardId) {
        this.policeDectiveCardId = policeDectiveCardId;
    }

    public int getDummyCardId() {
        return dummyCardId;
    }

    public void setDummyCardId(int dummyCardId) {
        this.dummyCardId = dummyCardId;
    }

    public boolean isDummyCardSearch() {
        return dummyCardSearch;
    }

    public void setDummyCardSearch(boolean dummyCardSearch) {
        this.dummyCardSearch = dummyCardSearch;
    }

    public void onRightClickPoliceDectiveItemUse(PlayerInteractEvent.RightClickItem event)
    {
        if (gamePhase.equals(eGamePhase.POLICE_DETECTIVE) && event.getItemStack().getDisplayName().equals(POLICE_DECTIVE_ITEM.getDisplayName()))
        {
            System.out.println(" 작동 " + "  " + event.getItemStack().getDisplayName().equals(POLICE_DECTIVE_ITEM.getDisplayName())
                    + " " + event.getItemStack().getDisplayName() +  "  " + POLICE_DECTIVE_ITEM.getDisplayName());

            String checkPlayerName = hashCardIdToPlayerName.getOrDefault(policeDectiveCardId, "");
            MinecraftServer server = event.getEntityPlayer().getServer();

            EntityPlayerMP checkPlayerMp = server.getPlayerList().getPlayerByUsername(checkPlayerName);

            EntityPlayerMP policePlayerMp = server.getPlayerList().getPlayerByUsername(event.getEntityPlayer().getName());

            for(ItemStack stack : policePlayerMp.inventory.mainInventory)
            {
                if(stack.getDisplayName().equals(POLICE_DECTIVE_ITEM.getDisplayName()))
                {
                    policePlayerMp.inventory.deleteStack(stack);
                }
            }

            if(checkPlayerName.equals(""))
            {
                for(int i = 0; i< Variable.Instance().prisonLocList.size(); i++)
                {
                    BlockPos pos = Variable.Instance().prisonLocList.get(i);

                    TileEntity tile = server.getEntityWorld().getTileEntity(pos.add(0, 2, 0));
                    if (tile instanceof BlockTileCardView)
                    {
                        int cardId = ((BlockTileCardView) tile).cardId;
                        System.out.println(" 더미값 좌표 " + pos + "  엔티티 카드ID " + cardId + "  경찰체크카드  " + policeDectiveCardId);
                        if(cardId == policeDectiveCardId)
                        {
                            boolean check = false;
                            String notPlayer = "";
                            for(String playerName : gamePlayerName)
                            {
                                EntityPlayerMP checkPlayer = server.getPlayerList().getPlayerByUsername(playerName);
                                if(checkPlayer.getDistanceSq(pos) <= 2f)
                                {
                                    check = true;
                                    notPlayer = checkPlayer.getName();
                                }

                            }
                            if(check)
                            {

                                Packet.networkWrapper.sendToAll(new CPacketFadeEffectAdd(1, 8, "de_X", notPlayer));


                                Timer timer4 = new Timer();
                                timer4.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        Packet.networkWrapper.sendToAll(new CPacketPlaySound("FAIL"));
                                    }
                                }, 5000l);

                                String policePlayer = hashCardIdToPlayerName.get(99);


                                policePlayerMp.inventory.deleteStack(event.getItemStack());

                                Timer timer3 = new Timer();
                                timer3.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        Packet.networkWrapper.sendToAll(new CPacketPlaySound("GAME_OVER"));
                                        broadcastMessage(" 교도관이 추리를 실패해 죄수들이 풀려났습니다.", server);
                                        for(int i = 0; i<saveGamePlayer.size(); i++)
                                        {
                                            if(! saveGamePlayer.get(i).equals(policePlayer))
                                            {
                                                EntityPlayerMP playerMP = server.getPlayerList().getPlayerByUsername(saveGamePlayer.get(i));
                                                playerMP.setPositionAndUpdate(variable.resultPos.getX(), variable.resultPos.getY(), variable.resultPos.getZ());
                                                policePlayerMp.setPositionAndUpdate(variable.guard.getX(), variable.guard.getY(), variable.guard.getZ());
                                            }
                                        }
                                    }
                                }, 16000l);
                            }
                            else
                            {
                                System.out.println(" i = " + i + " 사이즈" + variable.prisonLocList.size());
                                broadcastMessage(" 맞습니다 해당 직업을 가진 사람은 없습니다.", server);

                                ArrayList<Integer> resultCardId = new ArrayList<>(useCardIdList);
                                dummyCardSearch = true;

                                Packet.networkWrapper.sendToAll(new CPacketPlaySound("DONE"));
                                Packet.networkWrapper.sendToAll(new CPacketDummyActive(dummyCardSearch));

                                selectPlayer ="";

                                for(String name : saveGamePlayer)
                                {
                                    if( hashPlayerGameExit.getOrDefault(name, "").equals("탈락"))
                                    {
                                        System.out.println(" 탈락 카드 :  " + hashPlayerToCardInfo.get(name).cardId + "   " + hashPlayerToCardInfo.get(name).cardName);
                                        resultCardId.remove(Integer.valueOf(hashPlayerToCardInfo.get(name).cardId));
                                    }

                                }
                                if(dummyCardSearch)
                                    resultCardId.remove(Integer.valueOf(dummyCardId));
                                resultCardId.remove(Integer.valueOf(99));

                                Collections.shuffle(resultCardId);


                                if(resultCardId.size() == 0)
                                {
                                    String policePlayer = hashCardIdToPlayerName.get(99);

                                    Timer timer3 = new Timer();
                                    timer3.schedule(new TimerTask() {
                                        @Override
                                        public void run() {
                                            broadcastMessage(" 모든 역할을 전부 맞춰 승리하셨습니다.", server);
                                            EntityPlayerMP policePlayerMp2 = server.getPlayerList().getPlayerByUsername(event.getEntityPlayer().getName());
                                            policePlayerMp2.inventory.addItemStackToInventory(POLICE_STICK.copy());
                                            policePlayerMp2.sendMessage(new TextComponentString("사랑의 곤봉이 지급되었습니다."));
                                            Packet.networkWrapper.sendToAll(new CPacketPlaySound("GAME_OVER"));
                                            policeGodMod = true;
                                            for(BlockPos pos : variable.prisonLocList)
                                            {
                                                server.getEntityWorld().setBlockState(pos.add(1, 0, 0), Blocks.AIR.getDefaultState());
                                                server.getEntityWorld().setBlockState(pos.add(-1, 0, 0), Blocks.AIR.getDefaultState());
                                                server.getEntityWorld().setBlockState(pos.add(0, 0, 1), Blocks.AIR.getDefaultState());
                                                server.getEntityWorld().setBlockState(pos.add(0, 0, -1), Blocks.AIR.getDefaultState());

                                            }

                                        }
                                    }, 10000l);
                                    

                                }
                                else {

                                    Timer timer3 = new Timer();
                                    timer3.schedule(new TimerTask() {
                                        @Override
                                        public void run() {

                                            policeDectiveCardId = resultCardId.get(0);
                                            System.out.println( " 선택된 경찰 추리 카드 : policeDectiveCardId:  " + policeDectiveCardId );

                                            Packet.networkWrapper.sendToAll(new CPacketPoliceDectiveCardId(policeDectiveCardId));
                                            broadcastMessage(" 교도관이 추리를 맞춰 다음 추리를 확인합니다.", server);
                                            broadcastMessage(" 교도관은 준비가 되었으면 아이템을 우클릭 해 주십시요", server);

                                            EntityPlayerMP policePlayerMp2 = server.getPlayerList().getPlayerByUsername(event.getEntityPlayer().getName());
                                            policePlayerMp2.inventory.addItemStackToInventory(POLICE_DECTIVE_ITEM.copy());

                                        }
                                    }, 8000l);
                                }

                            }
                        }

                    }
                }

            }
            else
            {
                for(int i = 0; i< Variable.Instance().prisonLocList.size(); i++)
                {
                    BlockPos pos = Variable.Instance().prisonLocList.get(i);
                    BlockPos tilePos = pos.add(0, 2, 0);
                    TileEntity tile = server.getEntityWorld().getTileEntity(tilePos);
                    if (tile instanceof BlockTileCardView)
                    {
                        int cardId = ((BlockTileCardView) tile).cardId;
                        if (cardId == policeDectiveCardId) //추리하는 카드 서치
                        {
                            double distance = checkPlayerMp.getDistanceSq(pos);
                            if(distance <= 2f) //맞을경우
                            {
                                checkPlayerMp.setPositionAndUpdate(pos.getX()+0.5f, pos.getY(), pos.getZ()+0.5f);
                                server.getEntityWorld().setBlockState(pos.add(1, 0, 0), Blocks.BARRIER.getDefaultState());
                                server.getEntityWorld().setBlockState(pos.add(-1, 0, 0), Blocks.BARRIER.getDefaultState());
                                server.getEntityWorld().setBlockState(pos.add(0, 0, 1), Blocks.BARRIER.getDefaultState());
                                server.getEntityWorld().setBlockState(pos.add(0, 0, -1), Blocks.BARRIER.getDefaultState());

                                Packet.networkWrapper.sendToAll(new CPacketFadeEffectAdd(1, 8, "de_O", checkPlayerName));

                                Timer timer4 = new Timer();
                                timer4.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        Packet.networkWrapper.sendToAll(new CPacketPlaySound("DONE"));
                                    }
                                }, 5000l);

                                hashPlayerGameExit.put(checkPlayerName,"탈락");
                                Packet.networkWrapper.sendToAll(new CPacketHashPlayerGameExit(checkPlayerName, "탈락"));

                                ArrayList<Integer> resultCardId = new ArrayList<>(useCardIdList);

                                for(String name : saveGamePlayer)
                                {
                                    System.out.println("이름 :" + name + "   " + hashPlayerGameExit.getOrDefault(name, "").equalsIgnoreCase("탈락"));
                                    if( hashPlayerGameExit.getOrDefault(name, "").equalsIgnoreCase("탈락"))
                                    {
                                        System.out.println(" 탈락 카드 :  " + hashPlayerToCardInfo.get(name).cardId + "   " + hashPlayerToCardInfo.get(name).cardName);
                                        resultCardId.remove(Integer.valueOf(hashPlayerToCardInfo.get(name).cardId));
                                    }

                                }
                                if(dummyCardSearch)
                                    resultCardId.remove(Integer.valueOf(dummyCardId));
                                resultCardId.remove(Integer.valueOf( 99));

                                Collections.shuffle(resultCardId);


                                for(int b = 0; b<resultCardId.size(); b++)
                                {
                                    System.out.println(resultCardId.get(b));
                                }

                                if(resultCardId.size() == 1 && ! dummyCardSearch)
                                {
                                    Timer timer3 = new Timer();
                                    timer3.schedule(new TimerTask() {
                                        @Override
                                        public void run() {
                                            broadcastMessage(" 모든 역할을 전부 맞춰 승리하셨습니다.", server);
                                            EntityPlayerMP policePlayerMp2 = server.getPlayerList().getPlayerByUsername(event.getEntityPlayer().getName());
                                            policePlayerMp2.inventory.addItemStackToInventory(POLICE_STICK.copy());
                                            policePlayerMp2.sendMessage(new TextComponentString("사랑의 곤봉이 지급되었습니다."));
                                            Packet.networkWrapper.sendToAll(new CPacketPlaySound("GAME_OVER"));
                                            policeGodMod = true;
                                            for(BlockPos pos : variable.prisonLocList)
                                            {
                                                server.getEntityWorld().setBlockState(pos.add(1, 0, 0), Blocks.AIR.getDefaultState());
                                                server.getEntityWorld().setBlockState(pos.add(-1, 0, 0), Blocks.AIR.getDefaultState());
                                                server.getEntityWorld().setBlockState(pos.add(0, 0, 1), Blocks.AIR.getDefaultState());
                                                server.getEntityWorld().setBlockState(pos.add(0, 0, -1), Blocks.AIR.getDefaultState());

                                            }

                                        }
                                    }, 10000l);

                                }
                                else if(resultCardId.size() == 0)
                                {
                                    String policePlayer = hashCardIdToPlayerName.get(99);

                                    Timer timer3 = new Timer();
                                    timer3.schedule(new TimerTask() {
                                        @Override
                                        public void run() {
                                            broadcastMessage(" 모든 역할을 전부 맞춰 승리하셨습니다.", server);
                                            EntityPlayerMP policePlayerMp2 = server.getPlayerList().getPlayerByUsername(event.getEntityPlayer().getName());
                                            policePlayerMp2.inventory.addItemStackToInventory(POLICE_STICK.copy());
                                            policePlayerMp2.sendMessage(new TextComponentString("사랑의 곤봉이 지급되었습니다."));
                                            Packet.networkWrapper.sendToAll(new CPacketPlaySound("GAME_OVER"));
                                            policeGodMod = true;
                                            for(BlockPos pos : variable.prisonLocList)
                                            {
                                                server.getEntityWorld().setBlockState(pos.add(1, 0, 0), Blocks.AIR.getDefaultState());
                                                server.getEntityWorld().setBlockState(pos.add(-1, 0, 0), Blocks.AIR.getDefaultState());
                                                server.getEntityWorld().setBlockState(pos.add(0, 0, 1), Blocks.AIR.getDefaultState());
                                                server.getEntityWorld().setBlockState(pos.add(0, 0, -1), Blocks.AIR.getDefaultState());

                                            }


                                        }
                                    }, 10000l);

                                }
                                else
                                {

                                    Timer timer3 = new Timer();
                                    timer3.schedule(new TimerTask() {
                                        @Override
                                        public void run()
                                        {

                                            selectPlayer = "";
                                            broadcastMessage(" 교도관이 추리를 맞춰 다음 추리를 확인합니다.", server);
                                            broadcastMessage(" 교도관은 준비가 되었으면 아이템을 우클릭 해 주십시요", server);
                                            server.addScheduledTask(()->
                                            {
                                                policeDectiveCardId = resultCardId.get(0);
                                                Packet.networkWrapper.sendToAll(new CPacketPoliceDectiveCardId(policeDectiveCardId));
                                                System.out.println( " 선택된 경찰 추리 카드 : policeDectiveCardId:  " + policeDectiveCardId );
                                            });

                                            policePlayerMp.inventory.addItemStackToInventory(POLICE_DECTIVE_ITEM.copy());

                                        }
                                    }, 8000l);

                                }

                            }
                            else // 틀릴경우
                            {

                                String notPlayer = "";
                                System.out.println("틀렸을 경우 왔다 ");
                                for(String playerName : gamePlayerName)
                                {
                                    EntityPlayerMP checkPlayer = server.getPlayerList().getPlayerByUsername(playerName);

                                    if(checkPlayer.getDistanceSq(pos) <= 2d)
                                    {
                                        System.out.println( " 판정된 플레이어 " + playerName);
                                        notPlayer = checkPlayer.getName();
                                    }

                                }

                                Packet.networkWrapper.sendToAll(new CPacketFadeEffectAdd(1, 8, "de_X", notPlayer));

                                Timer timer4 = new Timer();
                                timer4.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        Packet.networkWrapper.sendToAll(new CPacketPlaySound("FAIL"));
                                    }
                                }, 5000l);

                                String policePlayer = hashCardIdToPlayerName.get(99);

                                Timer timer3 = new Timer();
                                timer3.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        Packet.networkWrapper.sendToAll(new CPacketPlaySound("GAME_OVER"));
                                        broadcastMessage(" 교도관이 추리를 실패해 죄수들이 풀려났습니다.", server);
                                        for(int i = 0; i<saveGamePlayer.size(); i++)
                                        {
                                            if(! saveGamePlayer.get(i).equals(policePlayer))
                                            {
                                                EntityPlayerMP playerMP = server.getPlayerList().getPlayerByUsername(saveGamePlayer.get(i));
                                                playerMP.setPositionAndUpdate(variable.resultPos.getX(), variable.resultPos.getY(), variable.resultPos.getZ());
                                                policePlayerMp.setPositionAndUpdate(variable.guard.getX(), variable.guard.getY(), variable.guard.getZ());
                                            }
                                        }
                                    }
                                }, 16000l);

                            }
                        }
                    }
                }


            }


        }
    }
    public boolean isGameStart() {
        return gameStart;
    }

    public void setGameStart(boolean gameStart) {
        this.gameStart = gameStart;
    }


    public int getGameTurn() {
        return gameTurn;
    }

    public int getReasonCrimeVotingCount() {
        return reasonCrimeVotingCount;
    }

    public void setReasonCrimeVotingCount(int reasonCrimeVotingCount) {
        this.reasonCrimeVotingCount = reasonCrimeVotingCount;
    }
    public int getQuestionCardId() {
        return questionCardId;
    }
    public void setQuestionCardId(int questionCardId) {
        this.questionCardId = questionCardId;
    }

    public int getGamePlayerMcNumber() {
        return gamePlayerMcNumber;
    }

    public boolean isQuestionCardDraw() {
        return isQuestionCardDraw;
    }

    public void setGameTurn(int gameTurn) {
        this.gameTurn = gameTurn;
    }


    int policeDectiveCardId = 0;

    public boolean policeGodMod = false;
    public void reset()
    {
        gamePhase = eGamePhase.NONE;
        hashPlayerToCardInfo.clear();
        useCardIdList.clear();
        gamePlayerName.clear();
        delayTick = 0;
        nextPhase = false;
        hashTurnCountToPlayerAnswer.clear();
        hashPlayerToPlayerAnswer.clear();
        isQuestionCardDraw = false;
        questionCardId = 0;
        crimeSkip = false;
        hashPlayerVotingCheck.clear();
        hashReasonPosition.clear();
        hashPlayerVotingCount.clear();
        hashPlayerToCardInfo.clear();
        hashTurnCountToPlayerAnswer.clear();
        hashPlayerCardYesAnswer.clear();
        viewQuestionCardList.clear();
        hashPlayerGameExit.clear();
        hashCardIdToPlayerName.clear();

        gamePlayerMcNumber = 0;
        gameMcCount = 0;
        gameTurn = 0;
        delayTick = 0;
        policeDectiveCardId=0;

        reasonCrimeTimeCardNumber = 0;
        reasonCrimeVotingCount = 0;
        reasonVotingExitCount = 0;
        votingOpenPlayerCount = 0;

        showVotingResult = false;
        nextPhase = false;

        gameStart = false;
        resultTeleport = false;
        dummyCardSearch = false;



        crimeSkip = false;
        isQuestionCardDraw = false;

        selectPlayer = "";
        turnStop = false;
        hashPlayerUseCardOpenCheck.clear();
        saveGamePlayer.clear();
        policeGodMod = false;


    }


    public enum eGamePhase
    {
        PLAYER_HEAD_DOWNLOADING,
        PLAYER_SEQUENCE,
        CARD_SUFFLE,
        TRUN_REVERS_VIEW,
        RANDOM_QUESTION_CARD_DRAW,
        QUESTION_CARD_TIME, //질문카드 보고 Y/N 내는 시간

        CARD_OPEN,
        REASON_CIRME_VIEW, // 추리시간

        REASON_VOTING_TIME, // 범쥐카드 추리시간
        
        REASON_VOTING_VIEW, //투표 공개 시간

        REASON_DIRECT_Y_N, // 범죄카드 매칭 Y or N

        POLICE_DETECTIVE, // 폴리스 잡혔으면 추리 시간

        NONE
    }

    public HashMap<String, Integer> getHashPlayerVotingCount() {
        return hashPlayerVotingCount;
    }


    public HashMap<String, String> getHashReasonPosition() {
        return hashReasonPosition;
    }
    public int getReasonCrimeTimeCardNumber() {
        return reasonCrimeTimeCardNumber;
    }

    public void setReasonCrimeTimeCardNumber(int reasonCrimeTimeCardNumber) {
        this.reasonCrimeTimeCardNumber = reasonCrimeTimeCardNumber;
    }
    public HashMap<Integer, HashMap<String, String>> getHashTurnCountToPlayerAnswer() {
        return hashTurnCountToPlayerAnswer;
    }

    public HashMap<String, String> getHashPlayerToPlayerAnswer() {
        return hashPlayerToPlayerAnswer;
    }

    public ArrayList<Integer> getUseCardIdList() {
        return useCardIdList;
    }

    public HashMap<String, GameCardInfo> getHashPlayerToCardInfo() {
        return hashPlayerToCardInfo;
    }
    public ArrayList<String> getGamePlayerName() {
        return gamePlayerName;
    }

}
