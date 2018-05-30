package com.km.rmbank.oldrecycler;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.SDCardUtils;
import com.km.rmbank.utils.Constant;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by pengsong on 2016-10-20.
 */

public class AppUtils {

    public static DisplayMetrics metrics = null;
    public static Handler mHandler;

    static {
        metrics = new DisplayMetrics();
    }

    /**
     * 判断网络是否可用
     *
     * @param context Context对象
     */
    public static Boolean isNetworkReachable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = cm.getActiveNetworkInfo();
        if (current == null) {
            return false;
        }
        return (current.isAvailable());
    }

    /**
     * 判断是否是wifi连接
     * @param mContext
     * @return
     */
    public static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }


    /**
     * 判断是否是移动网络连接
     * @param context
     * @return
     */
    public boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);   //获取移动网络信息
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();    //getState()方法是查询是否连接了数据网络
            }
        }
        return false;
    }
    public static String getCacheDir(Context context) {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;

    }

    /**
     * 跳转页面
     *
     * @param context
     * @param cls
     * @param bundle
     */
    public static void skipToPage(Context context, Class cls, Bundle bundle) {
        Intent intent = new Intent(context, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    /**
     * 获取当前屏幕宽度
     *
     * @param mContext
     * @return
     */
    public static int getCurWindowWidth(Context mContext) {
//        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    /**
     * 获取当前屏幕高度
     *
     * @param mContext
     * @return
     */
    public static int getCurWindowHeight(Context mContext) {
//        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**-----------------------------------我是分割线----------------------------------------------*/
    /**
     * 去主线程运行
     *
     * @param mills
     * @param uiThread
     */
    public static void executeOnUIThread(int mills, final UIThread uiThread) {
        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        uiThread.onUIThread();
                    }
                });

    }

    public static Observable executeOnUiThread(){
        return Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread());
    };

    public interface UIThread {
        void onUIThread();
    }


    final public static int REQUEST_CODE_ASK_CALL_PHONE = 1111;

    /**
     * 动态申请权限
     * 要在申请权限的页面中 实现{link @onRequestPermissionsResult}
     *
     * @param mContext
     * @param permission
     * @param applyPermissionListener
     */
    public static void applyPermission(Context mContext, String[] permission, onApplyPermissionListener applyPermissionListener) {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) mContext,
                        permission,
                        REQUEST_CODE_ASK_CALL_PHONE);
                return;
            } else {
                applyPermissionListener.applyPermissionSuccess();
            }
        } else {
            applyPermissionListener.applyPermissionSuccess();
        }
    }

    public interface onApplyPermissionListener {
        void applyPermissionSuccess();
    }


    /**
     * 获取版本号 名称
     * @param mContext
     * @return 当前应用的版本号
     */
    public static String getAppVersionName(Context mContext) {
        try {
            PackageManager manager = mContext.getPackageManager();
            PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取版本号
     * @param mContext
     * @return 当前应用的版本号
     */
    public static int getAppVersionCode(Context mContext) {
        try {
            PackageManager manager = mContext.getPackageManager();
            PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取下载app存放的路径
     * @param appName
     * @return
     */
    public static String getDownloadAppPath(String appName){
        if (!SDCardUtils.isSDCardEnable()) return "";
        return Environment.getExternalStorageDirectory().getPath() + File.separator + "WanZhuanDiQiu" + File.separator + "app" + File.separator + appName;
    }

    /**
     * 获取压缩图片的存储路径
     * @param imageName
     * @return
     */
    public static String getImagePath(String imageName){
        if (!SDCardUtils.isSDCardEnable()) return "";
        String filePath = Environment.getExternalStorageDirectory().getPath() + File.separator + "WanZhuanDiQiu" + File.separator + "image";
        FileUtils.createOrExistsDir(filePath);
        return filePath + File.separator + imageName;
    }

    /**
     * 获取文件的存储路径
     * @param spaceName 文件夹名称
     * @param fileName 文件名
     * @return
     */
    public static String getPath(String spaceName,String fileName){
        if (!SDCardUtils.isSDCardEnable()) return "";
        String filePath = Environment.getExternalStorageDirectory().getPath() + Constant.SAVE_PATH_DEFAULT + File.separator + fileName;
        FileUtils.createOrExistsDir(filePath);
        return filePath ;
    }


}
