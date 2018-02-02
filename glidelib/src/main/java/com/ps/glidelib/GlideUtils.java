package com.ps.glidelib;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.widget.ImageView;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.OnCompositionLoadedListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by PengSong on 17/12/26.
 */

public class GlideUtils {

    private static final String TAG = "GlideUtils";

    private static LottieDrawable getLottieDrawable(Context context){
            final LottieDrawable drawable = new LottieDrawable();
            LottieComposition.Factory.fromAssetFileName(context, "loading.json", new OnCompositionLoadedListener() {
                @Override
                public void onCompositionLoaded(@Nullable LottieComposition composition) {
                    drawable.setComposition(composition);
                }
            });
            drawable.loop(true);
            drawable.playAnimation();
        return drawable;
    }


    public static void loadImage(Context context, String imagePath, ImageView imageView){
        GlideApp.with(context)
                .load(imagePath)
                .placeholder(getLottieDrawable(context))
                .error(R.drawable.load_image_fail)
                .centerCrop()
                .into(imageView);
    }

    public static void loadProtrait(Context context, String imagePath, ImageView imageView){
        GlideApp.with(context)
                .load(imagePath)
                .placeholder(getLottieDrawable(context))
                .error(R.drawable.default_protrait)
                .centerCrop()
                .into(imageView);
    }

    public static void loadImage(Context context, int imageRes, ImageView imageView){
        GlideApp.with(context)
                .load(imageRes)
                .error(R.drawable.load_image_fail)
                .centerCrop()
//                .transform(new RoundedCornersTransformation(50,0, RoundedCornersTransformation.CornerType.ALL))
//                .placeholder(getLottieDrawable(context))
                .into(imageView);
    }
}
