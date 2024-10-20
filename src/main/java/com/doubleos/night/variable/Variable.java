package com.doubleos.night.variable;

import com.doubleos.night.util.AnimationBroadCast;
import com.doubleos.night.util.AnimationState;
import com.doubleos.night.util.ImageInfo;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.HashMap;

public class Variable
{

    public int m_gamePoliceCount = 1;

    //싱글톤
    private Variable()
    {

    }

    private static class InnerInstanceGameVariableClazz
    {
        private static final Variable uniqueGameVariable = new Variable();
    }
    
    public static Variable Instance()
    {
        return InnerInstanceGameVariableClazz.uniqueGameVariable;
    }


    ArrayList<GameCardInfo> m_cardList = new ArrayList<>();



    HashMap<Integer, GameCardInfo> m_hashIdToCard = new HashMap<>();
    HashMap<Integer, GameQuestionCard> m_hashIdToQuestonCard = new HashMap<>();


    ArrayList<GameQuestionCard> m_questionCardList = new ArrayList<>();

    public ArrayList<CheckReady> m_readyList = new ArrayList<>();

    public String turnFacing = "시계";
    public String startName = "테스트";
    public ArrayList<AnimationBroadCast> m_gameAnimationDirectList = new ArrayList<>();

    public HashMap<String, ArrayList<ImageInfo>> m_hashGetPngInfo = new HashMap<>();

    public boolean imageDownloading = false;

    public boolean votingExitActive = false;

    public boolean votingCountView = false;


    public int downloadCounting = 0;

    public int playerCount = 0;

    public boolean viewDisplay = false;

    public ArrayList<AnimationState> m_animationStateList = new ArrayList<>();

    static MainGame mainGame = new MainGame();

    public ArrayList<BlockPos> getQuestionTimePlayerChairLocList() {
        return questionTimePlayerChairLocList;
    }

    public ArrayList<BlockPos> questionTimePlayerChairLocList = new ArrayList<>();

    public ArrayList<BlockPos> prisonLocList = new ArrayList<>();

    public ArrayList<BlockPos> cardEntityList = new ArrayList<>();

    public BlockPos guard = new BlockPos(0, 0, 0);

    public BlockPos resultPos = new BlockPos(0, 0, 0);



    public void reset()
    {

        m_readyList.clear();
        imageDownloading = false;
        mainGame.reset();
        downloadCounting = 0;
        playerCount = 0;
        votingExitActive = false;
        votingCountView = false;
    }
    public HashMap<Integer, GameCardInfo> getHashIdToCard() {
        return m_hashIdToCard;
    }

    public HashMap<Integer, GameQuestionCard> getHashIdToQuestonCard() {
        return m_hashIdToQuestonCard;
    }

    public void init()
    {
        ArrayList<ImageInfo> m_imageListInfo1 = new ArrayList<>();//플레이어
        m_hashGetPngInfo.put("플레이어", m_imageListInfo1);

        int startCardId = 1;
        m_cardList.add(new GameCardInfo(startCardId++, "대마 재배", false));
        m_cardList.add(new GameCardInfo(startCardId++, "장기 매매", false));
        m_cardList.add(new GameCardInfo(startCardId++, "절도", false));
        m_cardList.add(new GameCardInfo(startCardId++, "주가 조작", false));
        m_cardList.add(new GameCardInfo(startCardId++, "탈영", false));

        m_cardList.add(new GameCardInfo(startCardId++, "테러", false));
        m_cardList.add(new GameCardInfo(startCardId++, "폭행", false));
        m_cardList.add(new GameCardInfo(startCardId++, "해킹", false));
        m_cardList.add(new GameCardInfo(startCardId++, "공금 횡령", false));
        m_cardList.add(new GameCardInfo(startCardId++, "공무 집행 방해", false));
        m_cardList.add(new GameCardInfo(startCardId++, "공문서 위조", false));

        m_cardList.add(new GameCardInfo(startCardId++, "노상 방뇨", false));
        m_cardList.add(new GameCardInfo(startCardId++, "마약 판매", false));
        m_cardList.add(new GameCardInfo(startCardId++, "무전 취식", false));
        m_cardList.add(new GameCardInfo(startCardId++, "방화", false));
        m_cardList.add(new GameCardInfo(startCardId++, "보이스 피싱", false));
        m_cardList.add(new GameCardInfo(startCardId++, "빈집 털이", false));
        m_cardList.add(new GameCardInfo(startCardId++, "사기", false));

        m_cardList.add(new GameCardInfo(startCardId++, "산업 스파이", false));
        m_cardList.add(new GameCardInfo(startCardId++, "살인", false));
        m_cardList.add(new GameCardInfo(startCardId++, "승부 조작", false));
        m_cardList.add(new GameCardInfo(startCardId++, "쓰래기 투기", false));
        m_cardList.add(new GameCardInfo(startCardId++, "악플", false));
        m_cardList.add(new GameCardInfo(startCardId++, "야생동물 밀수", false));
        m_cardList.add(new GameCardInfo(startCardId++, "은행 강도", false));

        m_cardList.add(new GameCardInfo(startCardId++, "음주 운전", false));
        m_cardList.add(new GameCardInfo(startCardId++, "입시 비리", false));
        m_cardList.add(new GameCardInfo(startCardId++, "탈세", false));
        m_cardList.add(new GameCardInfo(startCardId++, "짝퉁 판매", false));
        m_cardList.add(new GameCardInfo(startCardId++, "간첩", false));
        m_cardList.add(new GameCardInfo(startCardId++, "불법 도박", false));
        m_cardList.add(new GameCardInfo(startCardId++, "불법 시위", false));

        m_cardList.add(new GameCardInfo(startCardId++, "소매 치기", false));
        m_cardList.add(new GameCardInfo(startCardId++, "위조 지폐", false));
        m_cardList.add(new GameCardInfo(99, "교도관", false));


        for(GameCardInfo info : m_cardList)
        {
            m_hashIdToCard.put(info.cardId, info);
        }

        int questionCardId = 1;

        m_questionCardList.add(new GameQuestionCard(questionCardId++, "돈 때문에 저지른 범죄였다"));
        m_questionCardList.add(new GameQuestionCard(questionCardId++, "죄가 가벼운 편이였다"));
        m_questionCardList.add(new GameQuestionCard(questionCardId++, "비밀스런 범죄 였다"));
        m_questionCardList.add(new GameQuestionCard(questionCardId++, "국제적인 범죄였다"));
        m_questionCardList.add(new GameQuestionCard(questionCardId++, "수많은 경찰이 체포하러 왔다"));
        m_questionCardList.add(new GameQuestionCard(questionCardId++, "밤에 범죄를 저질렀다"));
        m_questionCardList.add(new GameQuestionCard(questionCardId++, "반복해서 저질렀다"));
        m_questionCardList.add(new GameQuestionCard(questionCardId++, "큰 돈을 만질 수 있었다"));
        m_questionCardList.add(new GameQuestionCard(questionCardId++, "뉴스에 나왔었다"));
        m_questionCardList.add(new GameQuestionCard(questionCardId++, "피해자가 많았다"));
        m_questionCardList.add(new GameQuestionCard(questionCardId++, "계획적인 범죄였다"));
        m_questionCardList.add(new GameQuestionCard(questionCardId++, "공범이 있었다"));
        m_questionCardList.add(new GameQuestionCard(questionCardId++, "조선시대에도 있었던 범죄였다"));
        m_questionCardList.add(new GameQuestionCard(questionCardId++, "초등학생도 할 수 있는 범죄였다"));
        m_questionCardList.add(new GameQuestionCard(questionCardId++, "범죄를 저지를 때 즐거웠다"));
        m_questionCardList.add(new GameQuestionCard(questionCardId++, "범죄를 저지를 때 피를 보았다"));
        m_questionCardList.add(new GameQuestionCard(questionCardId++, "컴퓨터가 필요했다"));
        m_questionCardList.add(new GameQuestionCard(questionCardId++, "가술이 필요한 범죄였다"));
        m_questionCardList.add(new GameQuestionCard(questionCardId++, "이동을 해야 했다"));
        m_questionCardList.add(new GameQuestionCard(questionCardId++, "음악을 들으며 범죄를 저질렀다"));


        for(GameQuestionCard info : m_questionCardList)
        {
            m_hashIdToQuestonCard.put(info.cardId, info);
        }



        questionTimePlayerChairLocList.add(new BlockPos(0, 0,0));
        questionTimePlayerChairLocList.add(new BlockPos(0, 0,0));
        questionTimePlayerChairLocList.add(new BlockPos(0, 0,0));
        questionTimePlayerChairLocList.add(new BlockPos(0, 0,0));
        questionTimePlayerChairLocList.add(new BlockPos(0, 0,0));
        questionTimePlayerChairLocList.add(new BlockPos(0, 0,0));

        prisonLocList.add(new BlockPos(0, 0, 0));
        prisonLocList.add(new BlockPos(0, 0, 0));
        prisonLocList.add(new BlockPos(0, 0, 0));
        prisonLocList.add(new BlockPos(0, 0, 0));
        prisonLocList.add(new BlockPos(0, 0, 0));
        prisonLocList.add(new BlockPos(0, 0, 0));

        cardEntityList.add(new BlockPos(0, 0, 0));
        cardEntityList.add(new BlockPos(0, 0, 0));
        cardEntityList.add(new BlockPos(0, 0, 0));
        cardEntityList.add(new BlockPos(0, 0, 0));
        cardEntityList.add(new BlockPos(0, 0, 0));
        cardEntityList.add(new BlockPos(0, 0, 0));



    }

    public static MainGame getMainGame() {
        return mainGame;
    }


}
