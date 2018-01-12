package com.ps.commonadapter.adapter.wrapper;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ps.commonadapter.R;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.utils.WrapperUtils;

/**
 * Created by PengSong on 17/12/12.
 */

public class EmptyWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static int ITEM_TYPE_EMPTY = Integer.MAX_VALUE - 1;

    private RecyclerView.Adapter mInnerAdapter;
    private View mEmptyView;
    private @LayoutRes int mEmptyLayoutRes = R.layout.item_rv_empty;

    public EmptyWrapper(RecyclerView.Adapter innerAdapter) {
        this.mInnerAdapter = innerAdapter;
    }

    private boolean isEmpty(){
        if ((mEmptyView != null || mEmptyLayoutRes != 0) && mInnerAdapter.getItemCount() == 0){
            return true;
        }
        return false;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isEmpty()){
            CommonViewHolder viewHolder;
            if (mEmptyView != null){
                viewHolder = CommonViewHolder.getHolder(parent.getContext(),mEmptyView);
            } else {
                viewHolder = CommonViewHolder.getHolder(parent.getContext(),parent,mEmptyLayoutRes);
            }
            return viewHolder;
        }
        return mInnerAdapter.onCreateViewHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isEmpty()){
            return;
        }
        mInnerAdapter.onBindViewHolder(holder,position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback()
        {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position)
            {
                if (isEmpty())
                {
                    return gridLayoutManager.getSpanCount();
                }
                if (oldLookup != null)
                {
                    return oldLookup.getSpanSize(position);
                }
                return 1;
            }
        });


    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder)
    {
        mInnerAdapter.onViewAttachedToWindow(holder);
        if (isEmpty())
        {
            WrapperUtils.setFullSpan(holder);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isEmpty()){
            return ITEM_TYPE_EMPTY;
        }
        return mInnerAdapter.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        if (isEmpty()){
            return 1;
        }
        return mInnerAdapter.getItemCount();
    }


    public void setmEmptyView(View mEmptyView) {
        this.mEmptyView = mEmptyView;
    }

    public void setmEmptyLayoutRes(int mEmptyLayoutRes) {
        this.mEmptyLayoutRes = mEmptyLayoutRes;
    }
}
