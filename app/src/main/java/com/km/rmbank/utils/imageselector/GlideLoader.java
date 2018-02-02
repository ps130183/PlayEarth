package com.km.rmbank.utils.imageselector;

import android.app.Activity;
import android.content.Context;

import com.ps.glidelib.GlideUtils;
import com.yancy.gallerypick.inter.ImageLoader;
import com.yancy.gallerypick.widget.GalleryImageView;

/**
 * Created by kamangkeji on 17/3/28.
 */

public class GlideLoader implements ImageLoader {

    @Override
    public void displayImage(Activity activity, Context context, String path, GalleryImageView galleryImageView, int width, int height) {
        GlideUtils.loadImage(context,path,galleryImageView);
    }

    @Override
    public void clearMemoryCache() {

    }
}
