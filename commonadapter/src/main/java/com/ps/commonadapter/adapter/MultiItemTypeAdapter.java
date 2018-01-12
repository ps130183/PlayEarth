package com.ps.commonadapter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


import com.ps.commonadapter.R;
import com.ps.commonadapter.adapter.base.ItemViewDelegate;
import com.ps.commonadapter.adapter.base.ItemViewDelegateManager;

import java.util.List;

/**
 * Created by zhy on 16/4/9.
 */
public class MultiItemTypeAdapter<T> extends RecyclerView.Adapter<CommonViewHolder> {
    protected Context mContext;
    protected List<T> mDatas;

    protected ItemViewDelegateManager mItemViewDelegateManager;
    protected OnItemClickListener mOnItemClickListener;


    public MultiItemTypeAdapter(Context context, List<T> datas) {
        mContext = context;
        mDatas = datas;
        mItemViewDelegateManager = new ItemViewDelegateManager();
    }

    @Override
    public int getItemViewType(int position) {
        if (!useItemViewDelegateManager()) return super.getItemViewType(position);
        return mItemViewDelegateManager.getItemViewType(mDatas.get(position), position);
    }


    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewDelegate itemViewDelegate = mItemViewDelegateManager.getItemViewDelegate(viewType);
        int layoutId = itemViewDelegate.getItemViewLayoutId();
        CommonViewHolder holder = CommonViewHolder.getHolder(mContext, parent, layoutId);
        onViewHolderCreated(holder,holder.getConvertView());
        return holder;
    }

    public void onViewHolderCreated(CommonViewHolder holder,View itemView){

    }

    public void convert(CommonViewHolder holder, T t) {
        mItemViewDelegateManager.convert(holder, t, holder.getAdapterPosition());
    }



    protected void setListener(final CommonViewHolder viewHolder, final int position) {
        viewHolder.getConvertView().setBackgroundResource(R.drawable.recycler_bg);
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(viewHolder,getDatas().get(position), position);
                }
            }
        });

        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    return mOnItemClickListener.onItemLongClick(viewHolder,getDatas().get(position), position);
                }
                return false;
            }
        });
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        convert(holder, mDatas.get(position));
        setListener(holder,position);
    }

    @Override
    public int getItemCount() {
        int itemCount = mDatas.size();
        return itemCount;
    }


    public List<T> getDatas() {
        return mDatas;
    }

    public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    public MultiItemTypeAdapter addItemViewDelegate(int viewType, ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(viewType, itemViewDelegate);
        return this;
    }

    protected boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    public interface OnItemClickListener<T> {
        void onItemClick(CommonViewHolder holder,T data, int position);

        boolean onItemLongClick(CommonViewHolder holder,T data, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

}
