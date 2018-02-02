package com.km.rmbank.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by pengsong on 2016-08-03.
 */
public class
EventBusUtils {

    /**
     * 注册eventBus
     * @param context
     */
    public static void register(Context context){
        EventBus.getDefault().register(context);
//        RxBus.getInstance().register(context);
    }
    /**
     * 注册eventBus
     * @param activity
     */
    public static void register(Activity activity){
        EventBus.getDefault().register(activity);
//        RxBus.getInstance().register(activity);
    }
    /**
     * 注册eventBus
     * @param fragment
     */
    public static void register(Fragment fragment){
        EventBus.getDefault().register(fragment);
//        RxBus.getInstance().register(fragment);
    }
    /**
     * 注销eventBus
     * @param context
     */
    public static void unregister(Context context){
        EventBus.getDefault().unregister(context);
//        RxBus.getInstance().unregister(context);
    }
    /**
     * 注销eventBus
     * @param activity
     */
    public static void unregister(Activity activity){
        EventBus.getDefault().unregister(activity);
//        RxBus.getInstance().unregister(activity);
    }
    /**
     * 注销eventBus
     * @param fragment
     */
    public static void unregister(Fragment fragment){
        EventBus.getDefault().unregister(fragment);
//        RxBus.getInstance().unregister(fragment);
    }

    /**
     * 发送请求
     * @param message
     */
    public static void post(Object message){
        EventBus.getDefault().post(message);
//        RxBus.getInstance().post(message);
    }
}
