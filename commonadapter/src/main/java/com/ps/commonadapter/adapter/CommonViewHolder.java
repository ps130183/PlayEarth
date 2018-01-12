package com.ps.commonadapter.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by PengSong on 17/12/8.
 */

public class CommonViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;
    private View mConvertView;
    private Context mContext;

    private CommonViewHolder(Context context, View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
        mContext = context;
        mConvertView = itemView;
    }

    public static CommonViewHolder getHolder(Context context,View itemView){
        return new CommonViewHolder(context,itemView);
    }

    public static CommonViewHolder getHolder(Context context, ViewGroup parent, @LayoutRes int layoutId){
        View view = LayoutInflater.from(context).inflate(layoutId,parent,false);
        return new CommonViewHolder(context,view);
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 通过view Id 获取控件
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T findView(@IdRes int viewId){
        View view = mViews.get(viewId);
        if (view == null){
            view = mConvertView.findViewById(viewId);
            if (view == null){
                throw new IllegalArgumentException("在当前的viewHolder中，找不到viewId : " + viewId + " 所对应的控件！！！");
            }
            mViews.put(viewId,view);
        }
        return (T) view;
    }


    /**
     * 给textView 设置内容
     * @param viewId
     * @param content
     * @return
     */
    public CommonViewHolder setText(@IdRes int viewId,String content){
        TextView tv = findView(viewId);
        tv.setText(content);
        return this;
    }

}
