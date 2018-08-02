package com.ps.mrcyclerview.utils;

import android.os.Handler;

import com.andview.refreshview.XRefreshView;
import com.ps.mrcyclerview.refresh.CustomGifHeader;

/**
 * Created by PengSong on 18/3/12.
 */

public class RefreshUtils {

    public static XRefreshView initXRefreshView(XRefreshView xRefreshView){
        if (xRefreshView != null){
            xRefreshView.setPullLoadEnable(false);
            xRefreshView.setPullRefreshEnable(false);
            xRefreshView.setAutoLoadMore(false);
            xRefreshView.setPinnedTime(1000);
            xRefreshView.setMoveForHorizontal(true);
        }
        return xRefreshView;
    }

    public static void initXRefreshView(XRefreshView xRefreshView, final OnRefreshListener refreshListener){
        if (xRefreshView == null){
            throw new IllegalArgumentException("XRefreshView不能为null,请先设置XRefreshView");
        }
        initXRefreshView(xRefreshView);
        xRefreshView.setPullRefreshEnable(true);//设置可以下拉刷新
        xRefreshView.setCustomHeaderView(new CustomGifHeader(xRefreshView.getContext()));//样式
        xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener(){
            @Override
            public void onRefresh(boolean isPullDown) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (refreshListener != null){
                            refreshListener.refresh();
                        }
                    }
                },2000);
            }
        });
    }

    public interface OnRefreshListener{
        void refresh();
    }
}
