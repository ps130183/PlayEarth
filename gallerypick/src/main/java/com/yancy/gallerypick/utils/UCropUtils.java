package com.yancy.gallerypick.utils;

import android.app.Activity;
import android.net.Uri;

import com.yalantis.ucrop.UCrop;
import com.yancy.gallerypick.R;

import java.io.File;

/**
 * UCropUtils
 * Created by Yancy on 2016/11/2.
 */
public class UCropUtils {

    public static void start(Activity mActivity, File sourceFile, File destinationFile, float aspectRatioX, float aspectRatioY, int maxWidth, int maxHeight) {
        UCrop uCrop = UCrop.of(Uri.fromFile(sourceFile), Uri.fromFile(destinationFile))
                .withAspectRatio(aspectRatioX, aspectRatioY)
                .withMaxResultSize(maxWidth, maxHeight);

        UCrop.Options options = new UCrop.Options();
        int bgcolor = mActivity.getResources().getColor(R.color.ucrop_gallery_blue);
        options.setToolbarColor(bgcolor);
        options.setStatusBarColor(bgcolor);
        if (bgcolor == 0xffffffff){
            options.setToolbarWidgetColor(mActivity.getResources().getColor(R.color.gallery_black));
        }
        uCrop.withOptions(options);


        uCrop.start(mActivity);
    }


}
/*
 *   ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 *     ┃　　　┃
 *     ┃　　　┃
 *     ┃　　　┗━━━┓
 *     ┃　　　　　　　┣┓
 *     ┃　　　　　　　┏┛
 *     ┗┓┓┏━┳┓┏┛
 *       ┃┫┫　┃┫┫
 *       ┗┻┛　┗┻┛
 *        神兽保佑
 *        代码无BUG!
 */