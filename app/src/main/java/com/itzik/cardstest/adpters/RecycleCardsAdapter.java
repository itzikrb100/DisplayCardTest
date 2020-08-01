package com.itzik.cardstest.adpters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.itzik.cardstest.CardApp;
import com.itzik.cardstest.R;
import com.itzik.cardstest.events.ClickCardEvent;
import com.itzik.common.datamodels.CardModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


public class RecycleCardsAdapter extends RecyclerView.Adapter< RecycleCardsAdapter.ViewHolder> {

    private static final String TAG = RecycleCardsAdapter.class.getSimpleName();

    private List<CardModel> mCards;
    private LayoutInflater mInflater;

    public RecycleCardsAdapter(){
        mCards = new ArrayList<>();
        mInflater = LayoutInflater.from(CardApp.getInstance().getApplicationContext());
    }


    /**
     *  add new sub list of cards and notify
     * @param cardModel
     */
    public void addAndNotifySubListCard(CardModel cardModel){
        Log.d(TAG,"addAndNotifySubListCard: Card = " + cardModel.getFullName());
        mCards.add(cardModel);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG,"onCreateViewHolder");
        View view = mInflater.inflate(R.layout.raw_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
             CardModel cardModel =  mCards.get(position);
             holder.bind(cardModel);
             Log.d(TAG,"onBindViewHolder");
    }

    @Override
    public int getItemCount() {
        return mCards.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(final CardModel cardModel){
            Log.d(TAG," bind Item = " + cardModel.getFullName());
            ((TextView)itemView.findViewById(R.id.textViewName)).setText(cardModel.getFullName());
            Glide.with(itemView.getContext()).load(cardModel.getUrlSmallIcon()).into((ImageView) itemView.findViewById(R.id.icon));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new ClickCardEvent(cardModel));
                }
            });
        }
    }
}
