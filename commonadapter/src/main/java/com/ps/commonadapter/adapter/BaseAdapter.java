package com.ps.commonadapter.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ps.commonadapter.R;

import java.util.List;

/**
 * Created by PengSong on 17/12/8.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<CommonViewHolder> {

    private Context mContext;
    private List<T> mDatas;
    private @LayoutRes
    int mLayoutId;

    private OnItemClickListener onItemClickListener;

    public BaseAdapter(Context mContext, List<T> mDatas, @LayoutRes int mLayoutId) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.mLayoutId = mLayoutId;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return CommonViewHolder.getHolder(mContext, parent, mLayoutId);
    }

    @Override
    public void onBindViewHolder(final CommonViewHolder holder, final int position) {
        convert(holder, mDatas.get(position));

        setClickListener(holder,position);

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public abstract void convert(CommonViewHolder holder, T mData);

    public interface OnItemClickListener {
        void itemClick(CommonViewHolder holder, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    /**
     * 设置点击事件
     * @param holder
     * @param position
     */
    private void setClickListener(final CommonViewHolder holder, final int position){
        holder.getConvertView().setBackgroundResource(R.drawable.recycler_bg);
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.itemClick(holder, position);
                }
            }
        });
    }
}
