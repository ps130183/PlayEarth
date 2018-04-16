package com.km.rmbank.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by PengSong on 18/1/18.
 */

public class RecyclerUtils {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean move = false;

    private int mIndex = 0;

    public RecyclerUtils(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
        mLinearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
    }

    /**
     * 移动到指定位置
     * @param index
     */
    public void moveToPosition(int index) {
        //获取当前recycleView屏幕可见的第一项和最后一项的Position
        int firstItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        int lastItem = mLinearLayoutManager.findLastVisibleItemPosition();
        if (index <= firstItem ){
            mRecyclerView.smoothScrollToPosition(index);
        }else if ( index <= lastItem ){
            int top = mRecyclerView.getChildAt(index - firstItem).getTop();
            mRecyclerView.smoothScrollBy(0, top);
        }else{
            mRecyclerView.smoothScrollToPosition(index);
            move = true;
        }

        mRecyclerView.addOnScrollListener(new RecyclerViewListener());
    }

    class RecyclerViewListener extends RecyclerView.OnScrollListener{
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (move && newState == RecyclerView.SCROLL_STATE_IDLE ){
                move = false;
                int n = mIndex - mLinearLayoutManager.findFirstVisibleItemPosition();
                if ( 0 <= n && n < mRecyclerView.getChildCount()){
                    int top = mRecyclerView.getChildAt(n).getTop();
                    mRecyclerView.smoothScrollBy(0, top);
                }

            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (move){
                move = false;
                int n = mIndex - mLinearLayoutManager.findFirstVisibleItemPosition();
                if ( 0 <= n && n < mRecyclerView.getChildCount()){
                    int top = mRecyclerView.getChildAt(n).getTop();
                    mRecyclerView.scrollBy(0, top);
                }
            }
        }
    }
}
