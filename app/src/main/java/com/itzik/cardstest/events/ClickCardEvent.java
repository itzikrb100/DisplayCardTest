package com.itzik.cardstest.events;


import com.itzik.common.datamodels.CardModel;

public class ClickCardEvent {

    private CardModel mCardModel;

    public ClickCardEvent(CardModel cardModel){
        mCardModel = cardModel;
    }

    public CardModel getCardModel(){
        return mCardModel;
    }
}
