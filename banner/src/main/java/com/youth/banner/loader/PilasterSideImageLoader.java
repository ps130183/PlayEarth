package com.youth.banner.loader;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;

import com.youth.banner.R;


public abstract class PilasterSideImageLoader implements ImageLoaderInterface<CardView> {

    @Override
    public CardView createImageView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.banner_item,null,false);
        CardView cardView = view.findViewById(R.id.cardView);
        return cardView;
    }

}
