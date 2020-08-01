package com.itzik.cardstest.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.itzik.cardstest.R;
import com.itzik.common.datamodels.CardModel;

import static com.itzik.cardstest.Definitions.KEY_EXTRA;

public class CardDescActivity extends AppCompatActivity {


    private static final String TAG = CardDescActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setHomeButtonEnabled(true);
        setContentView(R.layout.activity_card_desc);
        updateViews();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void updateViews() {
        CardModel cardModel = getCardFromIntent();
        if (cardModel != null) {
            Log.d(TAG,"Card = " + cardModel.getFullName());
            Glide.with(this).load(cardModel.getUrlSmallIcon()).into((ImageView) findViewById(R.id.icon));
            ((TextView)findViewById(R.id.name)).setText(cardModel.getFullName());
            ((TextView)findViewById(R.id.email)).setText(cardModel.getEmail());
            ((TextView)findViewById(R.id.address)).setText(cardModel.getAddress());
            ((TextView)findViewById(R.id.phone)).setText(cardModel.getPhone());
        }else{
            Log.i(TAG,"card mode is null from get intent");
        }

    }

    private CardModel getCardFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            CardModel cardModel = intent.getParcelableExtra(KEY_EXTRA);
            return cardModel;
        }

        return null;
    }
}
