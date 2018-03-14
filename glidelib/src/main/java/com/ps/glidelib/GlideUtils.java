package com.ps.glidelib;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.OnCompositionLoadedListener;
import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.ps.glidelib.progress.CircleProgressView;
import com.ps.glidelib.progress.GlideApp;
import com.ps.glidelib.progress.OnGlideImageViewListener;
import com.ps.glidelib.transformation.GlideImageViewFitHeight;

import java.security.MessageDigest;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by PengSong on 17/12/26.
 */

public class GlideUtils {

    private static final String TAG = "GlideUtils";
    private static final int radius = 150;

    private static LottieDrawable getLottieDrawable(Context context, final ImageView imageView){
            final LottieDrawable drawable = new LottieDrawable();
            LottieComposition.Factory.fromAssetFileName(context, "loading.json", new OnCompositionLoadedListener() {
                @Override
                public void onCompositionLoaded(@Nullable LottieComposition composition) {
//                    int ivLeft = imageView.getLeft();
//                    int ivTop = imageView.getTop();
//                    int ivRight = imageView.getRight();
//                    int ivBottom = imageView.getBottom();
//                    int x = imageView.getWidth() / 2;
//                    int y = imageView.getHeight() / 2 ;
//                    Rect rect = composition.getBounds();
//                    rect.left = ivLeft + x - radius;
//                    rect.top = ivTop + y - radius;
//                    rect.right = ivRight - x + radius;
//                    rect.bottom = ivBottom - y + radius;
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
                .placeholder(getLottieDrawable(context,imageView))
                .error(R.drawable.load_image_fail)
                .centerCrop()
                .into(imageView);
    }

    public static void loadImageFitHeight(GlideImageView imageView,String imagePath, final CircleProgressView progressView){
        imageView.loadImageFitHeight(imagePath,R.color.placeholder_color).listener(new OnGlideImageViewListener() {
            @Override
            public void onProgress(int percent, boolean isDone, GlideException exception) {
                if (progressView != null){
                    if (exception != null && !TextUtils.isEmpty(exception.getMessage())) {
//                        Toast.makeText(tagImageView.getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    progressView.setProgress(percent);
                    progressView.setVisibility(isDone ? View.GONE : View.VISIBLE);
                }
            }
        });
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

    /**
     * 加载图片带进度条
     * @param tagImageView
     * @param imageUrl
     * @param progressView
     */
    public static void loadImageOnPregress(final GlideImageView tagImageView, String imageUrl, final CircleProgressView progressView){
        tagImageView.loadImage(imageUrl,R.color.placeholder_color).listener(new OnGlideImageViewListener() {
            @Override
            public void onProgress(int percent, boolean isDone, GlideException exception) {
                if (progressView != null){
                    if (exception != null && !TextUtils.isEmpty(exception.getMessage())) {
                        LogUtils.e(exception.getMessage());
                    }
                    progressView.setProgress(percent);
                    progressView.setVisibility(isDone ? View.GONE : View.VISIBLE);
                }
            }
        });
    }

    /**
     * 加载圆形图片 来自网络
     * @param imageView
     * @param imageUrl
     */
    public static void loadCircleImageByUrl(GlideImageView imageView,String imageUrl){
        imageView.loadCircleImage(imageUrl,R.color.placeholder_color);
    }

    /**
     * 加载圆形图片 来自资源文件
     * @param imageView
     * @param imageRes
     */
    public static void loadCircleImageByRes(GlideImageView imageView,int imageRes){
        imageView.loadLocalCircleImage(imageRes,R.color.placeholder_color);
    }
}
