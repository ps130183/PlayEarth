package com.ps.commonadapter.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;

import com.ps.commonadapter.adapter.base.ItemViewDelegate;

import java.util.List;

/**
 * Created by PengSong on 18/1/9.
 */

public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {

    public CommonAdapter(Context context, List<T> datas, @LayoutRes final int layoutId) {
        super(context, datas);
        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(CommonViewHolder holder, T t, int position) {
                CommonAdapter.this.convert(holder,t,position);
            }
        });
    }

    public abstract void convert(CommonViewHolder holder, T mData,int position);

}
