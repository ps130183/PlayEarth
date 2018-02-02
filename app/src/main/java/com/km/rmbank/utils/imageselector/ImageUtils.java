package com.km.rmbank.utils.imageselector;

import android.app.Activity;
import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by kamangkeji on 17/3/28.
 */

public class ImageUtils {

    private static final String imagePath = "/RMBank/Pictures";

    private static int defalutBgColor = 0xffffffff;
    private static int defalutBgColorRed = 0xffd50000;
    private static int defalutTextColor = 0xff000000;

    public static int REQUEST_CODE_SINGLE = 0;

    private static GalleryConfig mGallertConfig;
    private static List<String> emptyPath;

    static {
        emptyPath = new ArrayList<>();
        mGallertConfig = new GalleryConfig.Builder()
                .imageLoader(new GlideLoader())    // ImageLoader 加载框架（必填）
//                .provider(mContext.getPackageName() + ".fileprovider")   // provider(必填)
//                .pathList(path)                         // 记录已选的图片
//                .multiSelect(false)                      // 是否多选   默认：false
//                .multiSelect(false, 9)                   // 配置是否多选的同时 配置多选数量   默认：false ， 9
//                .maxSize(9)                             // 配置多选时 的多选数量。    默认：9
                .crop(true)                             // 快捷开启裁剪功能，仅当单选 或直接开启相机时有效
                .crop(true, 1, 1, 500, 500)             // 配置裁剪功能的参数，   默认裁剪比例 1:1
                .isShowCamera(true)                     // 是否现实相机按钮  默认：false
                .pathList(emptyPath)
                .filePath(imagePath)
                .build();
    }



    /**
     * 选择单张图片  头像
     *
     * @param mContext
     * @param listener
     */
    private static void getUserProtrait(Context mContext, final SelectImageListener listener) {
        mGallertConfig.getBuilder().provider(mContext.getPackageName() + ".fileprovider")
                .crop(true)
                .isOpenCamera(false)
                .iHandlerCallBack(getCallBack(listener))
                .multiSelect(false)
                .build();
        GalleryPick.getInstance().setGalleryConfig(mGallertConfig).open((Activity) mContext);
    }

    /**
     * 选择单张图片  可剪切
     *
     * @param mContext
     * @param listener
     */
    private static void getSingleImgByCrop(Context mContext, int cropWidth, int cropHeight, final SelectImageListener listener) {
        mGallertConfig.getBuilder().provider(mContext.getPackageName() + ".fileprovider")
                .crop(true)
                .crop(true, cropWidth, cropHeight, cropWidth, cropHeight)
                .isOpenCamera(false)
                .iHandlerCallBack(getCallBack(listener))
                .multiSelect(false)
                .build();
        GalleryPick.getInstance().setGalleryConfig(mGallertConfig).open((Activity) mContext);
    }


    /**
     * 直接打开相机
     *
     * @param mContext
     * @param listener
     */
    public static void getImageFromCamera(Context mContext, boolean isCrop, final SelectImageListener listener) {
        mGallertConfig.getBuilder().provider(mContext.getPackageName() + ".fileprovider")
                .crop(isCrop)
                .isOpenCamera(true)
                .iHandlerCallBack(getCallBack(listener))
                .build();
        GalleryPick.getInstance().setGalleryConfig(mGallertConfig).open((Activity) mContext);
    }

    /**
     * 直接打开相机 设计剪切尺寸
     *
     * @param mContext
     * @param listener
     */
    public static void getSingleImageByCrop(Context mContext, boolean isOpenCamera, int cropWidth, int cropHeight, final SelectImageListener listener) {
        mGallertConfig.getBuilder().provider(mContext.getPackageName() + ".fileprovider")
                .crop(true)
                .crop(true, cropWidth, cropHeight, cropWidth*5, cropHeight*5)
                .isOpenCamera(isOpenCamera)
                .iHandlerCallBack(getCallBack(listener))
                .build();
        GalleryPick.getInstance().setGalleryConfig(mGallertConfig).open((Activity) mContext);
    }


    /**
     * 从相册获取照片
     */
    private static void getSingleImageFromPhotoAlbum(Context mContext, SelectImageListener listener) {
        mGallertConfig.getBuilder().provider(mContext.getPackageName() + ".fileprovider")
                .crop(false)
                .isOpenCamera(false)
                .iHandlerCallBack(getCallBack(listener))
                .multiSelect(false)
                .build();
        GalleryPick.getInstance().setGalleryConfig(mGallertConfig).open((Activity) mContext);
    }

    private static void getMultiImagesFromPhotoAlbum(Context mContext, List<String> pathList, SelectImageListener listener) {

        GalleryConfig config = new GalleryConfig.Builder().provider(mContext.getPackageName() + ".fileprovider")
                .imageLoader(new GlideLoader())    // ImageLoader 加载框架（必填）
                .crop(false)
                .isOpenCamera(false)
                .multiSelect(true, 9)
                .iHandlerCallBack(getCallBack(listener))
                .isShowCamera(true)                     // 是否现实相机按钮  默认：false
                .filePath(imagePath)
                .build();
        if (pathList != null) {
            config.getBuilder().pathList(pathList).build();
        }
        GalleryPick.getInstance().setGalleryConfig(config).open((Activity) mContext);
    }

    /**
     * 从相册获取图片
     *
     * @param mContext
     * @param type
     * @param number
     * @param pathList
     * @param listener
     */
    public static void getImageFromPhotoAlbum(Context mContext, ImageType type, ImageNumber number, List<String> pathList, SelectImageListener listener) {
        if (type == ImageType.PROTRAIT) {
            getUserProtrait(mContext, listener);
        } else {
            if (number == ImageNumber.SINGLE) {
                getSingleImageFromPhotoAlbum(mContext, listener);
            } else {
                getMultiImagesFromPhotoAlbum(mContext, pathList, listener);
            }
        }
    }

    private static IHandlerCallBack getCallBack(final SelectImageListener listener) {
        return new IHandlerCallBack() {

            @Override
            public void onStart() {
                 LogUtils.i("onStart: 开启");
            }

            @Override
            public void onSuccess(List<String> photoList) {
                listener.onSuccess(photoList);

            }

            @Override
            public void onCancel() {
                 LogUtils.i("onCancel: 取消");
            }

            @Override
            public void onFinish() {
                 LogUtils.i("onFinish: 结束");
            }

            @Override
            public void onError() {
                 LogUtils.i("onError: 出错");
            }
        };
    }

    /**
     * 选择图片成功
     */
    public interface SelectImageListener {
        void onSuccess(List<String> photoList);
    }

    interface listener extends IHandlerCallBack {
        @Override
        void onSuccess(List<String> photoList);
    }

    /**
     * 照片的类型  头像 或 商品图片
     */
    public enum ImageType {
        PROTRAIT,
        PRODUCT;
    }

    /**
     * 选择照片的类型  单张  或 多张
     */
    public enum ImageNumber {
        SINGLE,
        MULTIPLE;
    }
}
