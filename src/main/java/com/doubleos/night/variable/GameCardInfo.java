package com.doubleos.night.variable;

public class GameCardInfo
{


    int cardId = 0;
    String cardName = "";
    boolean cardOpen = false;

    public GameCardInfo(int cardNumber, String cardName, boolean cardOpen) {
        this.cardId = cardNumber;
        this.cardName = cardName;
        this.cardOpen = cardOpen;
    }



    public int getcardId() {
        return cardId;
    }

    public void setCardId(int cardNumber) {
        this.cardId = cardNumber;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public boolean isCardOpen() {
        return cardOpen;
    }

    public void setCardOpen(boolean cardOpen) {
        this.cardOpen = cardOpen;
    }

}
