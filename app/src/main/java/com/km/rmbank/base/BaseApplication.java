package com.km.rmbank.base;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

/**
 * Created by PengSong on 17/12/22.
 */

public class BaseApplication extends Application {

    private static BaseApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Utils.init(this);
    }

    public static BaseApplication getInstance(){
        return mInstance;
    }
}
