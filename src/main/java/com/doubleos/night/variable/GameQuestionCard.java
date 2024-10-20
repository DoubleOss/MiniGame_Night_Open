package com.doubleos.night.variable;

public class GameQuestionCard
{


    int cardId = 0;
    String cardString = "";

    public GameQuestionCard(int cardId, String cardString) {
        this.cardId = cardId;
        this.cardString = cardString;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public String getCardString() {
        return cardString;
    }

    public void setCardString(String cardString) {
        this.cardString = cardString;
    }

}
