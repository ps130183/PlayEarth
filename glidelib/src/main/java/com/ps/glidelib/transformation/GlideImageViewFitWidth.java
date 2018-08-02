package com.ps.glidelib.transformation;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * Created by PengSong on 18/2/2.
 */

public class GlideImageViewFitWidth extends BitmapTransformation{

    private ImageView imageView;

    public GlideImageViewFitWidth(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        int height = imageView.getHeight();
        int bitmapHeight = toTransform.getHeight();
        float scale = (float)height / (float)bitmapHeight;
        int width = (int) (toTransform.getWidth() * scale);
        imageView.getLayoutParams().width = width;
        LogUtils.d("imageview width = " + width + "   scale = " + scale + "   height = " + height);
        return toTransform;
    }


    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
    }
}
