package com.itzik.cardstest;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class CardApp extends Application {

    private static final String TAG = CardApp.class.getSimpleName();
    private static CardApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Log.d(TAG,"onCreate");
    }

    public static CardApp getInstance(){
        return mInstance;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
