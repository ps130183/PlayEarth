package com.km.rmbank.oldrecycler;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.km.rmbank.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * Created by kamangkeji on 17/1/19.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseAdapter.BaseViewHolder> {

    protected static final int viewtype_item = 0;
    protected static final int viewtype_header = 1000;
    protected static final int viewtype_footer = 2000;
    protected static final int viewtype_load_more = 1;
    protected static final int viewtype_item_empty = 2;

    private int pageNumber = 20;
    private int mHeaderLayoutRes = -1;//头部
    private int mFooterLayoutRes = -1;//底部
    private boolean mExistHeader = false;
    private boolean mExistFooter = false;//底部

    private boolean mExistLoadMore = false;//是否有加载更多
    private boolean loadMoreFinish = false;//是否已加载完 所有数据
    private LoadMoreViewHolder loadMoreHolder;

    private boolean mExistEmptyView = true;//数据为空时提示  默认为空时 显示 提示

    protected Context mContext;
    private List<T> mListDatas;
    private int mItemLayoutRes;

    private IAdapter iAdapter;
    private IHeaderAdapter iHeaderAdapter;
    private IFooterAdapter iFooterAdapter;

    protected ItemClickListener<T> itemClickListener;
    protected int curPage = 0;

    private MoreDataListener moreDataListener;

    public BaseAdapter(Context mContext, List<T> listContents, @LayoutRes int itemLayoutRes) {
        this.mContext = mContext;
        this.mListDatas = listContents;
        this.mItemLayoutRes = itemLayoutRes;
        if (listContents.size() > 0) {
            curPage = 1;
        } else {
            curPage = 0;
        }
    }

    public BaseAdapter(Context mContext, @LayoutRes int itemLayoutRes) {
        this(mContext, new ArrayList<T>(), itemLayoutRes);
    }

    public BaseAdapter(Context mContext) {
        this(mContext, R.layout.item_rv_default);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (iAdapter == null) {
            throw new IllegalArgumentException(this.toString() + "  iAdapter is not null,请实现iadapter接口");
        } else if (viewType == viewtype_item_empty) {//没有数据
            return getEmptyViewHolder(inflater, parent);
        } else if (viewType == viewtype_header) { //头部 Header
            if (iHeaderAdapter == null) {
                throw new IllegalArgumentException("iHeaderAdapter is not null,请实现IHeaderAdapter接口");
            }
            return iHeaderAdapter.createHeaderViewHolder(inflater.inflate(mHeaderLayoutRes, parent, false), viewType);
        } else if (viewType == viewtype_footer) {//footer 底部
            if (iFooterAdapter == null) {
                throw new IllegalArgumentException("iFooterAdapter is not null,请实现IFooterAdapter接口");
            }
            return iFooterAdapter.createFooterViewHolder(inflater.inflate(mFooterLayoutRes, parent, false), viewType);
        } else if (viewType == viewtype_load_more) {//加载更多
            if (loadMoreHolder == null) {
                loadMoreHolder = new LoadMoreViewHolder(ViewUtils.getView(inflater, parent, R.layout.rc_footer_load_more));
            }
            if (mExistEmptyView || mListDatas.size() < pageNumber) loadMoreHolder.hideLoadMore();
            return loadMoreHolder;
        } else {
            View view = inflater.inflate(mItemLayoutRes, parent, false);
            return iAdapter.createViewHolder(view, viewType);
        }
    }


    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (iAdapter == null) {
            throw new IllegalArgumentException("iAdapter is not null,请实现iadapter接口");
        } else if (mExistHeader && position == 0) {
            if (iHeaderAdapter == null) {
                throw new IllegalArgumentException("iHeaderAdapter is not null,请实现IHeaderAdapter接口");
            } else {
                iHeaderAdapter.createHeaderView((BaseHeaderViewHolder) holder, position);
            }
        } else if (mExistEmptyView && (mExistHeader ? position == 1 : position == 0)) {

        } else if (mExistFooter && position == getItemCount() - 1) {
            if (iFooterAdapter == null) {
                throw new IllegalArgumentException("iFooterAdapter is not null,请实现IFooterAdapter接口");
            } else {
                iFooterAdapter.createFooterView((BaseFooterViewHolder) holder, position);
            }
        } else if (mExistLoadMore && (mExistFooter ? position == getItemCount() - 2 : position == getItemCount() - 1)) {//加载更多

        } else {
            iAdapter.createView(holder, mExistHeader ? position - 1 : position);
            setItemClick(holder.itemView, position);
        }
    }

    @Override
    public int getItemCount() {
        int itemCount = mListDatas.size();
        int headerCount = mHeaderLayoutRes > 0 ? 1 : 0;
        int footerCount = mFooterLayoutRes > 0 ? 1 : 0;
        int loadMoreCount = moreDataListener == null ? 0 : 1;
        mExistEmptyView = mExistEmptyView ? itemCount == 0 : false;
        mExistHeader = headerCount > 0;
        mExistFooter = footerCount > 0;
        mExistLoadMore = mExistEmptyView ? true : loadMoreCount > 0;

        int emptyCount = itemCount > 0 ? itemCount : mExistEmptyView ? 1 : 0;

        return itemCount > 0 ? itemCount + headerCount + footerCount + loadMoreCount : emptyCount + headerCount + footerCount + loadMoreCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (mExistHeader && position == 0) {
            return viewtype_header;
        }
        if (mExistEmptyView && (mExistHeader ? position == 1 : position == 0)) {
            return viewtype_item_empty;
        }

        if (mExistFooter && position == getItemCount() - 1) {
            return viewtype_footer;
        }

        if (mExistLoadMore && (mExistFooter ? position == getItemCount() - 2 : position == getItemCount() - 1)) {
            return viewtype_load_more;
        }

        return viewtype_item;
    }

    /**
     * 获取指定位置的数据
     *
     * @param position
     * @return
     */
    public T getItemData(int position) {
        if (position >= 0 && mListDatas.size() > position) {
            return mListDatas.get(position);
        } else {
            throw new IllegalArgumentException("position is container mListDatas.size,点击的位置不在列表的范围之内");
        }
    }

    /**
     * 获取所有数据
     *
     * @return
     */
    public List<T> getAllData() {
        return mListDatas;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    /**
     * 获取下一页
     *
     * @return
     */
    public int getNextPage() {
        return curPage + 1;
    }

    /**
     * 向类表里填充数据
     *
     * @param datas
     * @param nextPage 下一页
     */
    public void addData(List<T> datas, int nextPage) {
        if (mListDatas != null && (datas != null && datas.size() > 0)) {
            if (nextPage > 1) {
                curPage++;
            } else {
                curPage = 1;
                mListDatas.clear();
            }
            mListDatas.addAll(datas);
            if (mListDatas.size() < pageNumber){ //数据不足一页
                if (loadMoreHolder != null){
                    loadMoreHolder.hideLoadMore();
                }
            } else if (datas.size() < pageNumber) {//20为每页的数量
                setLoadMoreFinish(true);
            } else {
                setLoadMoreFinish(false);
            }
        } else {
            if (nextPage == 1){
                clearAllData();
                setmExistEmptyView(true);
            } else if (mListDatas.size() > 0) {
                setLoadMoreFinish(true);
            } else {
                if (loadMoreHolder != null){
                    loadMoreHolder.hideLoadMore();
                }
            }
        }

        notifyDataSetChanged();
        isLoadMore = false;
    }

    private void setLoadMoreFinish(boolean isFinish){
        loadMoreFinish = isFinish;
        if (loadMoreHolder == null){
            return;
        }
        if (isFinish){
            loadMoreHolder.setLoadMoreFinish();
        } else {
            loadMoreHolder.setLoadMore();
        }
    }

    /**
     * 向类表里填充数据
     *
     * @param datas
     */
    public void addData(List<T> datas) {
        addData(datas, 1);
    }

    /**
     * 向类表里填充数据
     *
     * @param datas
     */
    public int addData(T datas) {
        int position = 0;

        if (mListDatas != null) {
            mListDatas.add(datas);
            position = mListDatas.indexOf(datas);
            notifyItemInserted(position);
        }
        return position;
    }

    public void addDataOnFirst(T datas) {
        if (mListDatas != null) {
            mListDatas.add(0, datas);
//            notifyItemChanged(mListDatas.size() - 1);
            notifyDataChanged();
        }
    }

    public void addDataOnPosition(T data,int position){
        if (mListDatas != null) {
            mListDatas.add(position, data);
            notifyItemChanged(position);
//            notifyDataChanged();
        }
    }

    /**
     * 移除某条数据
     *
     * @param data
     */
    public void removeData(T data) {
        if (mListDatas != null) {
            mListDatas.remove(data);
            notifyDataSetChanged();
        }
    }

    /**
     * 清空所有数据
     */
    public void clearAllData() {
        if (mListDatas != null) {
            mListDatas.clear();
            curPage = 0;
            notifyDataSetChanged();
        }
    }

    /**
     * 设置单击事件
     *
     * @param itemView
     * @param position
     */
    private void setItemClick(View itemView, final int position) {
        if (itemClickListener != null) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int itemPosition = mExistHeader ? position - 1 : position;
                    itemClickListener.onItemClick(getItemData(itemPosition), itemPosition);
                }
            });
        }
    }

    //    public abstract BaseViewHolder createViewHolder(View view, int viewType);
//    public abstract void createView(BaseViewHolder holder, int position);
    public interface IAdapter<VH extends BaseViewHolder> {
        VH createViewHolder(View view, int viewType);

        void createView(VH holder, int position);
    }

    public interface IHeaderAdapter<VH extends BaseHeaderViewHolder> {
        VH createHeaderViewHolder(View view, int viewType);

        void createHeaderView(VH holder, int position);
    }

    public interface IFooterAdapter<VH extends BaseFooterViewHolder> {
        VH createFooterViewHolder(View view, int viewType);

        void createFooterView(VH holder, int position);
    }

    public abstract static class BaseViewHolder extends RecyclerView.ViewHolder {

        View itemView;

        public BaseViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }


    public void setmHeaderLayoutRes(int mHeaderLayoutRes) {
        this.mHeaderLayoutRes = mHeaderLayoutRes;
    }

    public void setmFooterLayoutRes(int mFooterLayoutRes) {
        this.mFooterLayoutRes = mFooterLayoutRes;
    }

    /**
     * 空数据
     */
    public class EmptyViewHolder extends BaseViewHolder {

        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 返回空数据
     *
     * @param inflater
     * @param parent
     * @return
     */
    protected EmptyViewHolder getEmptyViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return new EmptyViewHolder(ViewUtils.getView(inflater, parent, R.layout.rc_item_empty));
    }

    public void setmExistEmptyView(boolean mExistEmptyView) {
        this.mExistEmptyView = mExistEmptyView;
    }

    /**
     * 加载更多
     */
    class LoadMoreViewHolder extends BaseViewHolder {

        @BindView(R.id.ll_loading_more)
        LinearLayout llLoadingMore;
        @BindView(R.id.tv_loading_more_finish)
        TextView tvLoadingMoreFinish;

        public LoadMoreViewHolder(View itemView) {
            super(itemView);
        }

        public void setLoadMore() {
            llLoadingMore.setVisibility(View.VISIBLE);
            tvLoadingMoreFinish.setVisibility(View.GONE);
        }

        public void setLoadMoreFinish() {
            llLoadingMore.setVisibility(View.GONE);
            tvLoadingMoreFinish.setVisibility(View.VISIBLE);
        }

        public void hideLoadMore(){
            llLoadingMore.setVisibility(View.GONE);
            tvLoadingMoreFinish.setVisibility(View.GONE);
        }
    }


    public void setmExistFooter(boolean mExistFooterView) {
        this.mExistFooter = mExistFooterView;
    }

    /**
     * footer viewHolder
     */
    protected abstract static class BaseFooterViewHolder extends BaseViewHolder {

        public BaseFooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * header viewHolder
     */
    protected abstract static class BaseHeaderViewHolder extends BaseViewHolder {

        public BaseHeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public T getListContent(int position) {
        if (mListDatas != null && mListDatas.size() > 0 && position >= 0) {
            return mListDatas.get(position);
        } else {
            throw new IllegalArgumentException("无数据");
        }
    }

    public void setiAdapter(IAdapter iAdapter) {
        this.iAdapter = iAdapter;
    }

    public void setiHeaderAdapter(IHeaderAdapter iHeaderAdapter) {
        this.iHeaderAdapter = iHeaderAdapter;
    }

    public void setiFooterAdapter(IFooterAdapter iFooterAdapter) {
        this.iFooterAdapter = iFooterAdapter;
    }

    public interface ItemClickListener<T> {
        void onItemClick(T itemData, int position);
    }


    /**
     * ----------------------------加载更多------------开始--------------
     */
    private boolean isLoadMore;
    private int totalItemCount = 0;
    private int lastVisiableItemPosition = 0;
    //当前滚动的position下面最小的items的临界值
    private static final int visibleThreshold = 5;

    /**
     * 加载更多
     * 建议在recyclerView 设置完adapter以后再进行加载更多的设置
     *
     * @param recyclerView
     * @param moreDataListener
     */
    public void addLoadMore(RecyclerView recyclerView, final MoreDataListener moreDataListener) {
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            this.moreDataListener = moreDataListener;
            final LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    totalItemCount = llm.getItemCount();
                    lastVisiableItemPosition = llm.findLastVisibleItemPosition();
                    Flowable.just(lastVisiableItemPosition)
                            .filter(new Predicate<Integer>() {
                                @Override
                                public boolean test(@NonNull Integer integer) throws Exception {
                                    return !loadMoreFinish
                                            && !isLoadMore
                                            && totalItemCount <= (lastVisiableItemPosition + visibleThreshold)
                                            && !(curPage <= 1 && totalItemCount < pageNumber);
                                }
                            })
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnNext(new Consumer<Integer>() {
                                @Override
                                public void accept(@NonNull Integer integer) throws Exception {
//                                    showLoadMore();
                                    setLoadMoreFinish(false);
                                    moreDataListener.loadMoreData();
                                    isLoadMore = true;
                                }
                            })
                            .subscribe(new Consumer<Integer>() {
                                @Override
                                public void accept(@NonNull Integer integer) throws Exception {
//                                    hideLoadeMore();
                                    notifyDataChanged();
                                }
                            });
//                    if (!loadMoreFinish && !isLoadMore && totalItemCount <= (lastVisiableItemPosition + visibleThreshold)
//                            && !(curPage <= 1 && totalItemCount < 20)) {
//                        moreDataListener.loadMoreData();
//                        isLoadMore = true;
//                    }
                }
            });
        }
    }

    private interface BaseOnScrollListener {
    }

    public interface MoreDataListener extends BaseOnScrollListener {
        void loadMoreData();
    }

    /**----------------------------加载更多------------结束--------------*/

    /**
     * ----------------------------RecyclerView 上滑监听 和 下滑监听----------------------------------------
     */

    public interface RcScrollListener extends BaseOnScrollListener {
        void scrollUp();

        void scrollDown();
    }

    //监听recyclerView的上下滑动的监听状态
    private boolean scrollUp = false;
    private boolean scrollDown = false;

    /**
     * 为recyclerView 添加上下滑动监听
     *
     * @param recyclerView
     * @param scrollListener
     */
    public void addScrollListener(RecyclerView recyclerView, final RcScrollListener scrollListener,
                                  final MoreDataListener moreDataListener) {
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            this.moreDataListener = moreDataListener;
            final LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    LogUtils.d("onScrollStateChanged : " + newState + " scrollUP:" + scrollUp + "  scrollDown:" + scrollDown);
                    if (newState == 0) {  //滑动停止时 重置监听状态
                        scrollUp = false;
                        scrollDown = false;
                    }
//                    if (newState > 0 && !scrollDown && !scrollUp){
//                        scrollListener.scrollDown();
//                        scrollDown = true;
//                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (dy > 0 && !scrollUp) {//向上滑动
                        scrollUp = true;
                        scrollListener.scrollUp();
                    }
                    if (dy < 0 && !scrollDown) {//向下滑动
                        scrollDown = true;
                        scrollListener.scrollDown();
                    }

                    if (moreDataListener != null) {//加载更多
                        totalItemCount = llm.getItemCount();
                        lastVisiableItemPosition = llm.findLastVisibleItemPosition();

                        if (!loadMoreFinish && !isLoadMore && totalItemCount <= (lastVisiableItemPosition + visibleThreshold)) {
                            moreDataListener.loadMoreData();
                            isLoadMore = true;
                        }
                    }
                }
            });
        }
    }
    /**----------------------------RecyclerView 上滑监听 和 下滑监听----------------------------------------*/

    /**
     * 刷新列表
     */
    public void notifyDataChanged() {
        AppUtils.executeOnUiThread()
                .subscribe(new Consumer() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        notifyDataSetChanged();
                    }
                });
    }

    public void notifyItemDataChanged(final int position, final Object payload){
        AppUtils.executeOnUiThread()
                .subscribe(new Consumer() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        notifyItemChanged(position,payload);
                    }
                });
    }

    public void notifyItemDataChanged(final int position){
        AppUtils.executeOnUiThread()
                .subscribe(new Consumer() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        notifyItemChanged(position);
                    }
                });
    }

}
