package com.ps.commonadapter.adapter.wrapper;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.utils.WrapperUtils;

/**
 * Created by PengSong on 17/12/12.
 */

public class HeaderAndFooterWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_TYPE_HEADER = 10000;
    private static final int ITEM_TYPE_FOOTER = 20000;

    private RecyclerView.Adapter mInnerAdapter;

    private SparseArray<Integer> mHeaderLayoutRes = new SparseArray<>();
    private SparseArray<Integer> mFooterLayoutRes = new SparseArray<>();

    private LoadHeaderAndFooterData loadHeaderAndFooterData;

    public HeaderAndFooterWrapper(RecyclerView.Adapter mInnerAdapter) {
        this.mInnerAdapter = mInnerAdapter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderLayoutRes.get(viewType) != null) {
            CommonViewHolder holder = CommonViewHolder.getHolder(parent.getContext(), parent, mHeaderLayoutRes.get(viewType));
            return holder;
        } else if (mFooterLayoutRes.get(viewType) != null) {
            CommonViewHolder holder = CommonViewHolder.getHolder(parent.getContext(), parent, mFooterLayoutRes.get(viewType));
            return holder;
        } else {
            return mInnerAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isHeaderView(position)) {
            if (loadHeaderAndFooterData != null) {
                loadHeaderAndFooterData.loadHeaderData((CommonViewHolder) holder, position);
            }
            return;
        } else if (isFooterView(position)) {
            if (loadHeaderAndFooterData != null) {
                loadHeaderAndFooterData.loadFooterData((CommonViewHolder) holder, position - getHeaderCount() - getRealItemCount());
            }
            return;
        } else {
            mInnerAdapter.onBindViewHolder(holder, position - getHeaderCount());
        }
    }

    @Override
    public int getItemCount() {
        return getHeaderCount() + getFooterCount() + getRealItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderView(position)) {
            return mHeaderLayoutRes.keyAt(position);
        } else if (isFooterView(position)) {
            return mFooterLayoutRes.keyAt(position - getHeaderCount() - getRealItemCount());
        } else {
            return mInnerAdapter.getItemViewType(position - getHeaderCount());
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback() {
            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                int viewType = getItemViewType(position);
                if (mHeaderLayoutRes.get(viewType) != null) {
                    return layoutManager.getSpanCount();
                } else if (mFooterLayoutRes.get(viewType) != null) {
                    return layoutManager.getSpanCount();
                }
                if (oldLookup != null)
                    return oldLookup.getSpanSize(position);
                return 1;
            }
        });
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        if (isHeaderView(position) || isFooterView(position)) {
            WrapperUtils.setFullSpan(holder);
        }
    }

    /**
     * 获取实际的数据项 条数
     *
     * @return
     */
    public int getRealItemCount() {
        return mInnerAdapter.getItemCount();
    }

    /**
     * 增加头部
     *
     * @param headerLayoutRes
     */
    public void addHeader(@LayoutRes int headerLayoutRes) {
        mHeaderLayoutRes.put(mHeaderLayoutRes.size() + ITEM_TYPE_HEADER, headerLayoutRes);
    }

    /**
     * 增加底部
     *
     * @param footerLayoutRes
     */
    public void addFooter(@LayoutRes int footerLayoutRes) {
        mFooterLayoutRes.put(mFooterLayoutRes.size() + ITEM_TYPE_FOOTER, footerLayoutRes);
    }

    /**
     * 获取顶部 数据量
     *
     * @return
     */
    public int getHeaderCount() {
        return mHeaderLayoutRes.size();
    }

    /**
     * 获取底部数据量
     *
     * @return
     */
    public int getFooterCount() {
        return mFooterLayoutRes.size();
    }

    private boolean isHeaderView(int position) {
        return position < getHeaderCount();
    }

    private boolean isFooterView(int position) {
        return position >= getHeaderCount() + getRealItemCount();
    }


    public interface LoadHeaderAndFooterData {
        public void loadHeaderData(CommonViewHolder holder, int position);

        public void loadFooterData(CommonViewHolder holder, int position);
    }

    public void addLoadHeaderAndFooterData(LoadHeaderAndFooterData loadHeaderAndFooterData) {
        this.loadHeaderAndFooterData = loadHeaderAndFooterData;
    }
}
