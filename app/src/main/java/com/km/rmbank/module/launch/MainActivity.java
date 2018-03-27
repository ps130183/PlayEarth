package com.km.rmbank.module.launch;

import android.os.Build;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.WindowManager;

import com.blankj.utilcode.util.SPUtils;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.module.main.HomeActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    @Override
    public int getContentViewRes() {
        return R.layout.activity_main;
    }

    @Override
    public BaseTitleBar getBaseTitleBar() {
        return null;
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        //设置当前窗体为全屏显示
        int flag= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setFlags(flag, flag);
        init();

    }

    private void init(){
        Observable.timer(2, TimeUnit.SECONDS)
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
//                        CityPickData.initData(LaunchActivity.this);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        boolean isFirst = SPUtils.getInstance().getBoolean("isFirst",true);
                        if (isFirst && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                            startActivity(GuideActivity.class);
                        } else {
                            startActivity(HomeActivity.class);
                        }
                        finish();
                    }
                });
    }
}
