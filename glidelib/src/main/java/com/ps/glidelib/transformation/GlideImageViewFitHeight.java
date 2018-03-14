package com.ps.glidelib.transformation;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.nio.ByteBuffer;
import java.security.MessageDigest;

/**
 * Created by PengSong on 18/2/2.
 */

public class GlideImageViewFitHeight extends BitmapTransformation{

    private ImageView imageView;

    public GlideImageViewFitHeight(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        int width = imageView.getWidth();
        int bitmapWidth = toTransform.getWidth();
        float scale = (float)width / (float)bitmapWidth;
        int height = (int) (toTransform.getHeight() * scale);
        imageView.getLayoutParams().height = height;
        LogUtils.d("imageview width = " + width + "   scale = " + scale + "   height = " + height);
        return toTransform;
    }


    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
    }
}
