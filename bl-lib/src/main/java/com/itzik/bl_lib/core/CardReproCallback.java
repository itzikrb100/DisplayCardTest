package com.itzik.bl_lib.core;


import com.itzik.bl_lib.exceptions.CardException;
import com.itzik.common.datamodels.CardModel;

public interface CardReproCallback {

    void onCardResponse(CardModel cardModel);
    void onErrorResponse(CardException e);
}
