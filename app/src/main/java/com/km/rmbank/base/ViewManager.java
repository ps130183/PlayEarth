package com.km.rmbank.base;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by PengSong on 17/12/21.
 *
 * 页面的View管理类,负责View的查找以及设置数据等操作
 */

public class ViewManager {

    //页面的View
    private View mContentView;
    //该页面所有的View
    private SparseArray<View> mViews;


    public ViewManager(Context context,@LayoutRes int contentViewId) {
        this.mContentView = LayoutInflater.from(context).inflate(contentViewId,null,false);
        this.mViews = new SparseArray<>();
    }

    public ViewManager(View mContentView) {
        this.mContentView = mContentView;
        this.mViews = new SparseArray<>();
    }

    public View getContentView() {
        if (mContentView == null){
            throw new CannotFoundContentViewException("找不到contentView");
        }
        return mContentView;
    }

    public <T extends View> T findView(@IdRes int viewId){
        View view = mViews.get(viewId);
        if (view == null){
            view = mContentView.findViewById(viewId);
            if (view == null){
                throw new IllegalArgumentException("找不到 viewId: " + viewId + "所代表的控件,请检查布局文件");
            }
            mViews.put(viewId,view);
        }
        return (T) view;
    }

    /**
     *
     * @param viewId
     * @param content
     */
    public void setText(@IdRes int viewId,String content){
        TextView textView = findView(viewId);
        textView.setText(content);
    }

    public ImageView getImageView(@IdRes int viewId){
        return findView(viewId);
    }

    public RecyclerView getRecyclerView(@IdRes int viewId){
        return findView(viewId);
    }
    /**
     * 设置点击事件
     * @param viewId
     * @param clickListener
     */
    public void setClickListener(@IdRes int viewId, View.OnClickListener clickListener){
        View view = findView(viewId);
        view.setOnClickListener(clickListener);
    }

    public class CannotFoundContentViewException extends RuntimeException{

        public CannotFoundContentViewException(String message) {
            super(message);
        }

    }

}
