package com.ps.glidelib;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.OnCompositionLoadedListener;

/**
 * Created by PengSong on 17/12/26.
 */

public class GlideUtils {

    private static LottieDrawable drawable;

    private static LottieDrawable getLottieDrawable(Context context){
        if (drawable == null){
            drawable = new LottieDrawable();
            LottieComposition.Factory.fromAssetFileName(context, "loader_4.json", new OnCompositionLoadedListener() {
                @Override
                public void onCompositionLoaded(@Nullable LottieComposition composition) {
                    drawable.setComposition(composition);
                }
            });
            drawable.loop(true);
            drawable.playAnimation();
        }
        return drawable;
    }


    public static void loadImage(Context context, String imagePath, ImageView imageView){
        GlideApp.with(context)
                .load(imagePath)
                .placeholder(getLottieDrawable(context))
                .fitCenter()
                .into(imageView);
    }

}
