package com.youth.banner.loader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.youth.banner.R;

/**
 * Created by PengSong on 18/8/24.
 */

public abstract class CustomLoader implements ImageLoaderInterface<FrameLayout> {

    @Override
    public FrameLayout createImageView(Context context) {
        FrameLayout view = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.banner_loader_custom,null,false);
        return view;
    }
}
