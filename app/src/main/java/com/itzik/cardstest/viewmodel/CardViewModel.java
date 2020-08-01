package com.itzik.cardstest.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.itzik.bl_lib.core.CardReproCallback;
import com.itzik.bl_lib.core.CardsReproManager;
import com.itzik.bl_lib.exceptions.CardException;
import com.itzik.common.datamodels.CardModel;

public class CardViewModel extends ViewModel {

    private static final String TAG = CardViewModel.class.getSimpleName();


    private MutableLiveData<UpdateViewCard> mCardsModels;
    private CardsReproManager mCardsReproManager;
    private ResponseCard mResponseCard;

    public CardViewModel() {
        super();
        Log.i(TAG, "CardViewModel");
        mResponseCard = new ResponseCard();
        mCardsModels = new MutableLiveData<>();
        mCardsReproManager = new CardsReproManager();
        mCardsReproManager.init(mResponseCard);
        mCardsReproManager.requestWithRetry(50);
    }


    void setUpdateCardModel(boolean isUpdate, CardModel cardModel){
        UpdateViewCard updateViewCard = new UpdateViewCard();
        updateViewCard.cardModel = cardModel;
        updateViewCard.isNeedUpdate = isUpdate;
        mCardsModels.postValue(updateViewCard);
    }

    public LiveData<UpdateViewCard> getUpdateCardModel(){
        return mCardsModels;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mResponseCard = null;
        if(mCardsReproManager != null) {
            mCardsReproManager.release();
        }
        Log.i(TAG,"onCleared");
    }



    public class UpdateViewCard{
        public CardModel cardModel;
        public boolean isNeedUpdate;
    }

    private class ResponseCard implements CardReproCallback {

        @Override
        public void onCardResponse(CardModel cardModel) {
            setUpdateCardModel(true, cardModel);
        }

        @Override
        public void onErrorResponse(CardException e) {
           Log.i(TAG,"onErrorResponse - failure due to = " + e.getMessage());
            setUpdateCardModel(false, null);
        }
    }
}
