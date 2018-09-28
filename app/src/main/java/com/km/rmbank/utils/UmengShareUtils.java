package com.km.rmbank.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.km.rmbank.R;
import com.km.rmbank.dto.ShareDto;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMMin;
import com.umeng.socialize.media.UMWeb;
import com.yancy.gallerypick.utils.ScreenUtils;

/**
 * Created by kamangkeji on 17/4/13.
 */

public class UmengShareUtils {

    public static void initUmengShare(Context context) {
        UMShareAPI mShareAPI = UMShareAPI.get(context);
//        mShareAPI.isInstall((Activity) context, SHARE_MEDIA.WEIXIN);//获取客户端安装信息
        PlatformConfig.setWeixin("wx637ea06a1c6d1fff", "1b2n3v4fx5v56vj8f3hd5t7g5f6hj8fh");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }


    //分享所需权限
    public static String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_LOGS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.SET_DEBUG_APP,
            Manifest.permission.SYSTEM_ALERT_WINDOW,
            Manifest.permission.GET_ACCOUNTS,
            Manifest.permission.WRITE_APN_SETTINGS};

    /**
     * 开启分享
     * @param activity
     * @param message
     * @param listener
     */
//    public static void openShare(Activity activity, String message, UMShareListener listener){
//
//        UMWeb web = new UMWeb("http://www.baidu.com");
//        web.setTitle("分享标题");
//        web.setThumb(new UMImage(activity, R.mipmap.ic_add_block_48px));
//        web.setDescription(message);
//
//        new ShareAction(activity).withText(message)
//                .setDisplayList(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.QQ,SHARE_MEDIA.QZONE)
//
//                .withText(message)
//                .withMedia(web)
//                .setCallback(listener).open();
//    }

    /**
     * 开启分享
     * @param activity
     * @param listener
     */
//    public static void openShare(Activity activity, UMShareListener listener){
//        openShare(activity,"这是umeng的分享",listener);
//    }

    /**
     * 开启分享
     *
     * @param activity
     * @param listener
     */
    public static void openShare(Activity activity, ShareDto shareDto, UMShareListener listener) {
        if (shareDto == null) {
            ToastUtils.showShort("暂未获取到分享内容");
            return;
        }
        UMWeb web = new UMWeb(shareDto.getPageUrl());
        web.setTitle(shareDto.getTitle());
        web.setThumb(new UMImage(activity, shareDto.getSharePicUrl()));
        web.setDescription(shareDto.getContent());

        new ShareAction(activity).withText(shareDto.getContent())
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)

                .withText(shareDto.getContent())
                .withMedia(web)
                .setCallback(listener).open();
    }

    /**
     * 开启分享
     *
     * @param activity
     * @param shareDto   分享的数据
     * @param shareMedia 分享的类型  比如：微信、朋友圈等
     */
    public static void openShare(Activity activity, ShareDto shareDto, SHARE_MEDIA shareMedia) {
        if (shareDto == null || shareDto.isEmpty()) {
            ToastUtils.showShort("暂未获取到分享内容");
            return;
        }
        UMWeb web = new UMWeb(shareDto.getPageUrl());
        web.setTitle(shareDto.getTitle());
        web.setThumb(new UMImage(activity, shareDto.getSharePicUrl()));
        web.setDescription(shareDto.getContent());

        new ShareAction(activity).withText(shareDto.getContent())
                .setPlatform(shareMedia)
                .withText(shareDto.getContent())
                .withMedia(web)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {


                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        ToastUtils.showShort("分享成功");
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        ToastUtils.showShort("分享失败");
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        ToastUtils.showShort("取消分享");
                    }
                }).share();
    }

    /**
     * 分享图片
     *
     * @param activity
     * @param listener
     */
    public static void openShareForImage(Activity activity, Bitmap imageBitmap, SHARE_MEDIA shareMedia, UMShareListener listener) {
        UMImage image = new UMImage(activity, imageBitmap);

        new ShareAction(activity)
                .setPlatform(shareMedia)
                .withMedia(image)
                .setCallback(listener)
                .share();
    }

    /**
     * 分享微信小程序
     * @param activity
     * @param webUrl
     * @param imagelocal
     * @param title
     * @param description
     * @param pageUrl
     * @param shareListener
     */
    public static void shareWeChatMin(Activity activity, String webUrl,
                                      Bitmap imagelocal, String title, String description,
                                      String pageUrl, UMShareListener shareListener) {
        UMImage umImage = new UMImage(activity, imagelocal);
        //兼容低版本的网页链接
        UMMin umMin = new UMMin(webUrl);
        // 小程序消息封面图片
        umMin.setThumb(umImage);
        // 小程序消息title
        umMin.setTitle(title);
        // 小程序消息描述
        umMin.setDescription(description);
        //小程序页面路径
        umMin.setPath(pageUrl);
        // 小程序原始id,在微信平台查询
        umMin.setUserName("gh_c1c50667585f");
        new ShareAction(activity)
                .withMedia(umMin)
                .setPlatform(SHARE_MEDIA.WEIXIN)
                .setCallback(shareListener).share();
    }
}
