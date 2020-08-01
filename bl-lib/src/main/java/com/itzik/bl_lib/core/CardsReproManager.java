package com.itzik.bl_lib.core;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import android.text.TextUtils;
import android.util.Log;



import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itzik.bl_lib.defenotions.Defenitions;
import com.itzik.bl_lib.exceptions.CardException;
import com.itzik.common.datamodels.CardModel;
import com.itzik.common.datamodels.responsemodel.ResponsData;

import java.io.IOException;
import java.util.List;



import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.itzik.bl_lib.defenotions.Defenitions.DEFUALT_RETREY;
import static com.itzik.bl_lib.defenotions.Defenitions.TIMEOUT;

public class CardsReproManager implements IReproManager{

    private static final String TAG = CardsReproManager.class.getSimpleName();



    private class CardHandler extends Handler {

        CardHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg != null) {
                HANDLE_JOB job = HANDLE_JOB.values()[msg.what];
                Log.d(TAG, "handleMessage: job? " + job);
                if (job != null) {
                    getRequestCard();
                }

                try {
                    // wait timeout until send next request
                    Thread.sleep(TIMEOUT);
                } catch (InterruptedException e) {
                    Log.e(TAG, "error sleep due to: " + e.getMessage());
                }
                synchronized (lock) {
                    Log.d(TAG,"Handle message check is request = " + mIsRequest);
                    if(mIsRequest && mLimitRetrey >=0 ) {
                        sendMessageHandler(HANDLE_JOB.REQUEST_CARDS);
                        mLimitRetrey --;
                    }
                }
            }
        }
    }


    private enum HANDLE_JOB {
        REQUEST_CARDS
    }



    public CardsReproManager() {
        mHandlerThread = new HandlerThread("CardsReproManager");
        mOkHttpClient = new OkHttpClient();
    }



    private CardReproCallback mCallback;
    private int mLimitRetrey =  DEFUALT_RETREY;
    private boolean mIsRequest = false;
    private OkHttpClient mOkHttpClient;

    private HandlerThread mHandlerThread;
    private CardHandler mCardHandler;
    private Object lock = new Object();

    @Override
    public void init(CardReproCallback cardReproCallback) {
        mCallback = cardReproCallback;
        mHandlerThread.start();
        mCardHandler = new CardHandler(mHandlerThread.getLooper());
    }

    @Override
    public void requestWithRetry(int limit) {
         synchronized (lock){
             if(!mIsRequest) {
                 mLimitRetrey = limit;
                 mIsRequest = true;
                 sendMessageHandler(HANDLE_JOB.REQUEST_CARDS);
             }
         }
    }

    @Override
    public void stopRequest() {
        synchronized (lock){
            if(mIsRequest){
                mLimitRetrey = DEFUALT_RETREY;
                mIsRequest = false;
            }
        }
    }

    @Override
    public void release() {
        stopRequest();
        mCallback = null;
        mHandlerThread.quitSafely();
    }



    private void sendMessageHandler(HANDLE_JOB job) {
        Message msg = Message.obtain();
        msg.what = job.ordinal();
        mCardHandler.sendMessage(msg);
    }

    private void getRequestCard() {

        try {
            Request requestCard = new Request.Builder()
                    .url(Defenitions.URL_CARD)
                    .method(Defenitions.GET_METHOD, null)
                    .build();

            Response call = mOkHttpClient.newCall(requestCard).execute();
            String gsonBody = call.body().string();
            Log.d(TAG,"gsonBody = " + gsonBody);
            if (!TextUtils.isEmpty(gsonBody)) {
                // todo validate gson string
                Gson gson = new Gson();
                ResponsData responsData = gson.fromJson(gsonBody, new TypeToken<ResponsData>(){}.getType());
                notifyResponse(true, responsData, null);
            }else{
                notifyResponse(false, null, new CardException("unresolved response"));
            }
        } catch (IOException e) {
            Log.e(TAG, "ERROR getRequestCard due to: " + e.getMessage());
            notifyResponse(false, null, new CardException("error response card due to: " + e.getMessage()));
        }
    }


    private void notifyResponse(boolean isSuccess, ResponsData responsData, CardException cardEx) {
         if (mCallback != null) {
                if (isSuccess) {
                    mCallback.onCardResponse(CardModel.cardModelFrom(responsData.getResults().get(0)));
                } else {
                    mCallback.onErrorResponse(cardEx);
                }
            }
    }
}


