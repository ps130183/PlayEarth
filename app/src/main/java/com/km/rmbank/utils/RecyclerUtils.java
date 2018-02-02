package com.km.rmbank.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by PengSong on 18/1/18.
 */

public class RecyclerUtils {
    /**
     * 移动到指定位置
     * @param recyclerView
     * @param index
     */
    public static void moveToPosition(RecyclerView recyclerView, int index) {
        //获取当前recycleView屏幕可见的第一项和最后一项的Position
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int firstItem = linearLayoutManager.findFirstVisibleItemPosition();
        int lastItem = linearLayoutManager.findLastVisibleItemPosition();
        //然后区分情况
        if (index <= firstItem) {
            //当要置顶的项在当前显示的第一个项的前面时
            recyclerView.scrollToPosition(index);
        } else if (index <= lastItem) {
            //当要置顶的项已经在屏幕上显示时，计算它离屏幕原点的距离
            int top = recyclerView.getChildAt(index - firstItem).getTop();
            recyclerView.scrollBy(0, top);
        } else {
            //当要置顶的项在当前显示的最后一项的后面时
            recyclerView.scrollToPosition(index);
            //记录当前需要在RecyclerView滚动监听里面继续第二次滚动
//            move = true;
        }
    }
}
