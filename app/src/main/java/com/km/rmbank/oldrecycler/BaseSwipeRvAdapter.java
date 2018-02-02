package com.km.rmbank.oldrecycler;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.LogUtils;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.km.rmbank.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by kamangkeji on 17/3/26.
 */

public abstract class BaseSwipeRvAdapter<T> extends RecyclerSwipeAdapter<BaseSwipeRvAdapter.BaseSwipeViewHolder> {

    protected static final int viewtype_item = 0;
    protected static final int viewtype_header = Integer.MAX_VALUE - 3;
    protected static final int viewtype_load_more = Integer.MAX_VALUE - 2;
    protected static final int viewtype_item_empty = Integer.MAX_VALUE - 1;

    protected Context mContext;
    private List<T> mDatas;
    private int itemLayoutRes;

    private BaseSwipeRvAdapter.IAdapter iAdapter;

    private BaseAdapter.MoreDataListener moreDataListener;
    private boolean loadMoreFinish;

    private BaseAdapter.ItemClickListener<T> itemClickListener;
    private int curPage = 0;

//    public BaseSwipeRvAdapter(Context mContext, List<T> mDatas) {
//        this.mContext = mContext;
//        this.mDatas = mDatas;
//
//        if (mDatas.size() > 0){
//            curPage = 1;
//        } else {
//            curPage = 0;
//        }
//    }

    public BaseSwipeRvAdapter(Context mContext, List<T> listContents, int itemLayoutRes) {
        this.mContext = mContext;
        this.mDatas = listContents;
        this.itemLayoutRes = itemLayoutRes;
        if (listContents.size() > 0) {
            curPage = 1;
        } else {
            curPage = 0;
        }
    }

    public BaseSwipeRvAdapter(Context mContext, int itemLayoutRes) {
        this(mContext, new ArrayList<T>(), itemLayoutRes);
    }

    @Override
    public BaseSwipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (iAdapter == null) {
            throw new IllegalArgumentException(this.toString() + "  iAdapter is not null,请实现iadapter接口");
        } else if (viewType == viewtype_item_empty) {//没有数据
            return new BaseSwipeRvAdapter.NonDataViewHolder(ViewUtils.getView(inflater, parent, R.layout.rc_item_empty));
        } else if (viewType == viewtype_load_more) {//加载更多
            if (loadMoreFinish) {
                return new BaseSwipeRvAdapter.LoadMoreViewHolder(ViewUtils.getView(inflater, parent, R.layout.rc_footer_load_more_finish));
            } else {
                return new BaseSwipeRvAdapter.LoadMoreViewHolder(ViewUtils.getView(inflater, parent, R.layout.rc_footer_load_more));
            }
        } else {
            View view = inflater.inflate(itemLayoutRes, parent, false);
            return iAdapter.createViewHolder(view, viewType);
        }
    }

    @Override
    public void onBindViewHolder(BaseSwipeViewHolder viewHolder, int position) {
        if (iAdapter == null) {
            throw new IllegalArgumentException("iAdapter is not null,请实现iadapter接口");
        } else if (mDatas.size() == 0 || position == mDatas.size()) {

        } else {
            iAdapter.createView(viewHolder, position);
            setItemClick(viewHolder.mSwiperLayout.getSurfaceView(), position);
        }
//        iAdapter.createView(viewHolder,position);
    }

    @Override
    public int getItemViewType(int position) {
        if (mDatas.size() == 0) {
            return viewtype_item_empty;
        } else if (moreDataListener != null && mDatas.size() > 10) {
            return viewtype_load_more;
        }
        return viewtype_item;
    }

    @Override
    public int getItemCount() {
        int itemCount = mDatas.size();
        if (moreDataListener != null) {//有加载更多
            itemCount += 1;
        }
        return mDatas.size() > 0 ? itemCount : 1;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swiper_layout;
    }

    /**
     * 获取指定位置的数据
     *
     * @param position
     * @return
     */
    public T getItemData(int position) {
        if (position >= 0 && mDatas.size() > position) {
            return mDatas.get(position);
        } else {
            throw new IllegalArgumentException("position is container mDatas.size,点击的位置不在列表的范围之内");
        }
    }

    /**
     * 获取所有数据
     *
     * @return
     */
    public List<T> getAllData() {
        return mDatas;
    }

    public void setItemClickListener(BaseAdapter.ItemClickListener itemClickListener) {
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
        if (mDatas != null && (datas != null && datas.size() > 0)) {
            if (nextPage > 1) {
                curPage++;
            } else {
                curPage = 1;
                clearAllData();
            }
            mDatas.addAll(datas);
            loadMoreFinish = false;
        } else {
            if (mDatas.size() > 0) {
                loadMoreFinish = true;
            }
        }
        notifyDataSetChanged();
        isLoadMore = false;
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
    public void addData(T datas) {
        if (mDatas != null) {
            mDatas.add(datas);
            notifyDataSetChanged();
        }
    }

    /**
     * 移除某条数据
     *
     * @param data
     */
    public void removeData(T data) {
        if (mDatas != null) {
            mDatas.remove(data);
            notifyDataSetChanged();
        }
    }

    /**
     * 清空所有数据
     */
    public void clearAllData() {
        if (mDatas != null) {
            mDatas.clear();
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
//                    if (holder.mSwiperLayout.getOpenStatus() != SwipeLayout.Status.Open){
                        itemClickListener.onItemClick(getItemData(position), position);
//                    }
                }
            });
        }
    }


    public static class BaseSwipeViewHolder extends RecyclerView.ViewHolder {

        View itemView;

        SwipeLayout mSwiperLayout;

        public BaseSwipeViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
            initSwiperLayout();
        }

        protected void initSwiperLayout() {
//            mSwiperLayout.setLayoutMode();
            mSwiperLayout = (SwipeLayout) itemView.findViewById(R.id.swiper_layout);
            if (mSwiperLayout != null) {
                //set show mode.
                mSwiperLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
                mSwiperLayout.setSwipeEnabled(true);
                mSwiperLayout.setClickToClose(true); //点击其他区域关闭侧滑
            }

            //set drag edge.
//            mSwiperLayout.addDrag(SwipeLayout.DragEdge.Left,);
//            mSwiperLayout.setDragEdge(SwipeLayout.DragEdge.Left);
        }

        public SwipeLayout getmSwiperLayout() {
            return mSwiperLayout;
        }
    }

    class NonDataViewHolder extends BaseSwipeRvAdapter.BaseSwipeViewHolder {

        public NonDataViewHolder(View itemView) {
            super(itemView);
        }
    }

    class LoadMoreViewHolder extends BaseSwipeRvAdapter.BaseSwipeViewHolder {

        public LoadMoreViewHolder(View itemView) {
            super(itemView);
        }
    }


    //    public abstract BaseViewHolder createViewHolder(View view, int viewType);
//    public abstract void createView(BaseViewHolder holder, int position);
    public interface IAdapter<VH extends BaseSwipeViewHolder> {
        VH createViewHolder(View view, int viewType);

        void createView(VH holder, int position);
    }

    public void setiAdapter(IAdapter iAdapter) {
        this.iAdapter = iAdapter;
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
    public void addLoadMore(RecyclerView recyclerView, final BaseAdapter.MoreDataListener moreDataListener) {
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

                    if (!loadMoreFinish && !isLoadMore && totalItemCount <= (lastVisiableItemPosition + visibleThreshold)) {
                        moreDataListener.loadMoreData();
                        isLoadMore = true;
                    }
                }
            });
        }
    }

    private interface BaseOnScrollListener {
    }

    public interface MoreDataListener extends BaseSwipeRvAdapter.BaseOnScrollListener {
        void loadMoreData();
    }

    /**----------------------------加载更多------------结束--------------*/

    /**
     * ----------------------------RecyclerView 上滑监听 和 下滑监听----------------------------------------
     */

    public interface RcScrollListener extends BaseSwipeRvAdapter.BaseOnScrollListener {
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
    public void addScrollListener(RecyclerView recyclerView, final BaseAdapter.RcScrollListener scrollListener,
                                  final BaseAdapter.MoreDataListener moreDataListener) {
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

}
