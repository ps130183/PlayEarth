package com.km.rmbank.utils;

import android.support.v4.widget.SwipeRefreshLayout;

import com.blankj.utilcode.util.LogUtils;
import com.km.rmbank.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kamangkeji on 17/6/2.
 */

public class SwipeRefreshUtils {

    public static void initSwipeRefresh(SwipeRefreshLayout mSwipeLayout, final OnRereshListener onRefreshListener) {
        if (mSwipeLayout == null) {
            LogUtils.d("mSwipeRefresh is null");
            return;
        }
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSwipeLayout.setDistanceToTriggerSync(400);// 设置手指在屏幕下拉多少距离会触发下拉刷新
        mSwipeLayout.setProgressBackgroundColorSchemeResource(R.color.white);
        mSwipeLayout.setSize(SwipeRefreshLayout.DEFAULT);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Observable.timer(2, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
//                                Logger.d("refresh number == %d", aLong);
                                if (onRefreshListener != null) {
                                    onRefreshListener.onRefresh();
                                }
                            }
                        });
            }
        });
    }

    public interface OnRereshListener {
        void onRefresh();
    }

}
