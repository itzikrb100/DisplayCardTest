package com.itzik.cardstest.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;


import com.itzik.cardstest.Definitions;
import com.itzik.cardstest.R;
import com.itzik.cardstest.adpters.RecycleCardsAdapter;
import com.itzik.cardstest.events.ClickCardEvent;
import com.itzik.cardstest.viewmodel.CardViewModel;
import com.itzik.common.datamodels.CardModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;



import static com.itzik.cardstest.Definitions.MAX_COLUM;

public class CardActivity extends AppCompatActivity {


    private static final String TAG = CardActivity.class.getSimpleName();

    private CardViewModel mCardViewModel;
    private RecyclerView mRecyclerView;
    private RecycleCardsAdapter mRecycleCardsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        mCardViewModel = ViewModelProviders.of(this).get(CardViewModel.class);
        subscribeBus();
        initRecyclerView();
        subscribeUpdateCardModel();
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
        unsubscribeBus();
        super.onDestroy();
    }


    /**
     *  detected click item from adapter
     * @param event item
     */

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ClickCardEvent event) {
        // handle to ui thread
        Log.d(TAG,"onEvent - ClickCardEvent");
        launchCardContact(event.getCardModel());
    }


    private void updateAdapterCard(CardViewModel.UpdateViewCard updateViewCard){
        boolean needUpdate = updateViewCard != null && updateViewCard.isNeedUpdate;
        Log.d(TAG,"updateAdapterCard: = " + needUpdate);
        if(mRecycleCardsAdapter != null &&  needUpdate){
            mRecycleCardsAdapter.addAndNotifySubListCard(updateViewCard.cardModel);
        }else{
            Log.d(TAG,"no need update!");
        }
    }

    private void initRecyclerView(){
        mRecyclerView = findViewById(R.id.recycle_cards);
        //mRecyclerView.addItemDecoration(new MarginDecoration(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, MAX_COLUM));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecycleCardsAdapter = new RecycleCardsAdapter();
        mRecyclerView.setAdapter(mRecycleCardsAdapter);
    }

    private void subscribeBus(){
        EventBus.getDefault().register(this);
    }

    private void unsubscribeBus(){
        EventBus.getDefault().unregister(this);
    }


    private void launchCardContact(CardModel cardModel){
        Intent contactActivityContact = new Intent(this, CardDescActivity.class);
        contactActivityContact.putExtra(Definitions.KEY_EXTRA, cardModel);
        startActivity(contactActivityContact);
    }

    private void subscribeUpdateCardModel(){
        mCardViewModel.getUpdateCardModel().observe(this, new Observer<CardViewModel.UpdateViewCard>() {
            @Override
            public void onChanged(CardViewModel.UpdateViewCard updateViewCard) {
                   // receive update card from ui thread
                updateAdapterCard(updateViewCard);
            }
        });
    }
}
