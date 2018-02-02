package com.km.rmbank.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.blankj.utilcode.util.LogUtils;


/**
 * Created by kamangkeji on 17/8/15.
 */

public class FirstRecyclerView extends RecyclerView {

    private LinearLayoutManager mLayoutManager;
    private float y;
    private float x;
    private float mLastY;
    private float mLastX;

    private SubRvFirstVisibleListener subRvFirstVisibleListener;

    public FirstRecyclerView(Context context) {
        this(context,null);
    }

    public FirstRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FirstRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        y = e.getY();
        x = e.getX();

        if (e.getAction() == MotionEvent.ACTION_DOWN){
            mLastY = y;
            mLastX = x;
        } else if (e.getAction() == MotionEvent.ACTION_MOVE){

            float deltaX = x - mLastX;
            float deltaY = y - mLastY;
            if (subRvFirstVisibleListener == null){
                throw new IllegalArgumentException("请实现  SubRvFirstVisibleListener  接口");
            }

            mLayoutManager = (LinearLayoutManager) getLayoutManager();
            int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();
            if (firstVisibleItemPosition == 0){
                if (Math.abs(deltaX) >= Math.abs(deltaY)){//左右滑动 不拦截
//                    Logger.d("左右 滑动 ---- delaX = " + deltaX + "  delaY = " + deltaY + "   delaX - delaY = " + (deltaX - deltaY));
                    return false;
                } else if (deltaY > 0 && !subRvFirstVisibleListener.getSubRvFirstVisible(e)){//上下滑动，子rv的firstItem不在顶部，不拦截
                    LogUtils.d("上下滑动，不拦截滑动，子rv的firstItem不在顶部");
                    return false;
                } else {
                    LogUtils.d("上下滑动,父级recyclerView 的移动");
                    return true;
                }
            } else {
                //子recyclerview滑动到顶部，父级重新拦截
                if (deltaY > 0){
                    LogUtils.d("subRvFirstVisible ==== " + subRvFirstVisibleListener.getSubRvFirstVisible(e));
                    return subRvFirstVisibleListener.getSubRvFirstVisible(e);
                }
                return false;
            }
        } else if (e.getAction() == MotionEvent.ACTION_UP){

        }
        return super.onInterceptTouchEvent(e);
    }

    public interface SubRvFirstVisibleListener{
        boolean getSubRvFirstVisible(MotionEvent event);
    }

    public void setSubRvFirstVisibleListener(SubRvFirstVisibleListener subRvFirstVisibleListener) {
        this.subRvFirstVisibleListener = subRvFirstVisibleListener;
    }
}
