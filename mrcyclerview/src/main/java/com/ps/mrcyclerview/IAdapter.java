package com.ps.mrcyclerview;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;

import com.ps.mrcyclerview.click.OnClickItemListener;
import com.ps.mrcyclerview.click.OnLoadMoreErrorListener;
import com.ps.mrcyclerview.click.OnLongClickItemListener;
import com.ps.mrcyclerview.delegate.MoreDelegate;

import java.util.List;

/**
 * Created by PengSong on 18/7/13.
 */

public interface IAdapter<D> {

    int getItemCount();

    void setMoreLayoutRes(int moreLayoutRes);
    void setMoreFinishLayoutRes(int moreFinishLayoutRes);
    void setMoreErrorLayoutRes(int moreErrorLayoutRes);

    void addContentLayout(@LayoutRes int contentLayoutRes, ItemViewConvert<D> convert);
    void addHeaderLayout(@LayoutRes int headerLayoutRes,ItemViewConvert convert);
    void addFooterLayout(@LayoutRes int footerLayoutRes,ItemViewConvert convert);

    void setOnClickItemListener(OnClickItemListener onClickItemListener);
    void setOnLongClickItemListener(OnLongClickItemListener onLongClickItemListener);
    void setOnLoadMoreErrorListener(OnLoadMoreErrorListener onLoadMoreErrorListener);

    void loadMoreError();
    void loadDataOfNextPage(List<D> datas);
    void insert(D data);
    void update(D data,int position,Object payloads);
    void delete(int position);
    void delete(D data);
    List<D> getAllDatas();
    int getDataSize();
    void clear();

    SparseArray<Integer> getDataSizeArray();

    boolean isLoadMore();
    void addMoreDelegate();
    MoreDelegate getMoreDelegate();
    int getNextPage();

    boolean isContentView(int position);
    boolean isHeaderView(int position);
    int getHeaderSize();
}
