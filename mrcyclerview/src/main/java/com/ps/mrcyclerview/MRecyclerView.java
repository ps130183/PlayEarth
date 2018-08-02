package com.ps.mrcyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.andview.refreshview.XRefreshView;
import com.ps.mrcyclerview.click.OnClickItemListener;
import com.ps.mrcyclerview.click.OnLoadMoreErrorListener;
import com.ps.mrcyclerview.click.OnLongClickItemListener;
import com.ps.mrcyclerview.delegate.MoreFinishDelegate;
import com.ps.mrcyclerview.divider.GridDividerItemDecotation;
import com.ps.mrcyclerview.divider.LinearDividerItemDecoration;
import com.ps.mrcyclerview.divider.StaggeredGridDividerItemDecoration;
import com.ps.mrcyclerview.refresh.CustomGifHeader;
import com.ps.mrcyclerview.utils.RefreshUtils;

import java.util.List;

/**
 * Created by PengSong on 18/6/1.
 */

public class MRecyclerView<D> extends FrameLayout {

    private static final String TAG = "MRecyclerView";
    private static final int LM_LINEAR = 0;
    private static final int LM_GRID = 1;
    private static final int LM_STAGGERED_GRID = 2;
    private static final int ORIENTATION_VERTICAL = 0;
    private static final int ORIENTATION_HORIZONTAL = 1;

    private static final int ADAPTER_DEFAULT = 0;
    private static final int ADAPTER_SWIPE = 1;

    private RecyclerView mRecyclerView;
    private IAdapter<D> mAdapter;

    private @LayoutRes int emptyRes;
    private View mEmptyView;

    //加载更多布局
    private @LayoutRes int loadMoreRes;
    //已加载完所有的更多 布局
    private @LayoutRes int loadMoreFinishRes;
    //加载更多失败
    private @LayoutRes int loadMoreErrorRes;
    private LoadMoreListener mLoadMoreListener;

    //布局管理类型linear线性管理 / grid网格管理 / staggeredGrid瀑布流
    private int lmType;
    //显示方向 vertical 垂直 / horizontal水平
    private int orientation;
    //同一方向 显示的个数  适用于 grid  和 staggeredGrid  默认：2
    private int spanCount;

    private int dividerWidth;
    private @ColorInt int dividerColor;

    private int adapterType;

    //下拉刷新
    private XRefreshView mXRefreshView = null;
    private boolean isRefresh;
    private RefreshUtils.OnRefreshListener refreshListener;


    public MRecyclerView(Context context) {
        this(context,null);
    }

    public MRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.MRecyclerView);
        loadMoreRes = ta.getResourceId(R.styleable.MRecyclerView_loadMoreLayout,R.layout.mr_load_more);
        loadMoreFinishRes = ta.getResourceId(R.styleable.MRecyclerView_loadMoreFinishLayout,R.layout.mr_load_more_finish);
        loadMoreErrorRes = ta.getResourceId(R.styleable.MRecyclerView_loadMoreErrorLayout,R.layout.mr_load_more_error);
        emptyRes = ta.getResourceId(R.styleable.MRecyclerView_emptyLayout,-1);
        lmType = ta.getInt(R.styleable.MRecyclerView_lmType,LM_LINEAR);
        if (lmType == 0){
            orientation = ta.getInt(R.styleable.MRecyclerView_orientation, ORIENTATION_VERTICAL);
        }
        spanCount = ta.getInteger(R.styleable.MRecyclerView_spanCount,2);
        dividerWidth = ta.getInteger(R.styleable.MRecyclerView_dividerWidth, 1);
        dividerColor = ta.getColor(R.styleable.MRecyclerView_dividerColor,0xffefeff4);

        adapterType = ta.getInt(R.styleable.MRecyclerView_adapterType,ADAPTER_DEFAULT);
        isRefresh = ta.getBoolean(R.styleable.MRecyclerView_refresh,false);
        initRecycler();
    }


    private void initRecycler(){
        if (emptyRes > 0){
            mEmptyView = LayoutInflater.from(this.getContext()).inflate(emptyRes,this,false);
            this.addView(mEmptyView);
        }

        mRecyclerView = new RecyclerView(this.getContext());
        FrameLayout.LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        mRecyclerView.setLayoutParams(lp);
        if (isRefresh){
            mXRefreshView = new XRefreshView(this.getContext());
            LayoutParams lp1 = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
            mXRefreshView.setLayoutParams(lp1);
            this.addView(mXRefreshView);

            mXRefreshView.addView(mRecyclerView);
        } else {
            this.addView(mRecyclerView);
        }


        if (lmType == LM_LINEAR){
            addLinearLayoutManager(orientation == ORIENTATION_VERTICAL ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);
            mRecyclerView.addItemDecoration(new LinearDividerItemDecoration(mRecyclerView,dividerWidth,dividerColor));
        } else if (lmType == LM_GRID){
            addGridLayoutManager(spanCount);
            mRecyclerView.addItemDecoration(new GridDividerItemDecotation(mRecyclerView,
                    dividerWidth,dividerColor));
        } else if (lmType == LM_STAGGERED_GRID){
            addStaggeredGridLayoutManager(orientation == ORIENTATION_VERTICAL ? StaggeredGridLayoutManager.VERTICAL : StaggeredGridLayoutManager.HORIZONTAL,spanCount);
            mRecyclerView.addItemDecoration(new StaggeredGridDividerItemDecoration(mRecyclerView,dividerWidth,dividerColor));
        } else {
            throw new IllegalArgumentException("not find layoutManager,please setLayoutManager()");
        }

        if (adapterType == ADAPTER_DEFAULT){
            mAdapter = new RecyclerAdapter(this.getContext());
        } else if (adapterType == ADAPTER_SWIPE){
            mAdapter = new SRecyclerAdapter<>(this.getContext());
        } else {
            throw new IllegalArgumentException("没有找到adapterType,请选择一个adapterType");
        }

        mAdapter.setMoreLayoutRes(loadMoreRes);
        mAdapter.setMoreFinishLayoutRes(loadMoreFinishRes);
        mAdapter.setMoreErrorLayoutRes(loadMoreErrorRes);
    }

    /**
     * 给recyclerView添加 线性布局
     * @param orientation
     * @return
     */
    public MRecyclerView addLinearLayoutManager(int orientation){
        WrapContentLinearLayoutManager llm = new WrapContentLinearLayoutManager(this.getContext(),orientation,false);
        llm.setSmoothScrollbarEnabled(true);
        llm.setAutoMeasureEnabled(false);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        return this;
    }

    public void addRefreshListener(final RefreshUtils.OnRefreshListener listener) {
        this.refreshListener = listener;
        if (isRefresh && refreshListener != null) {
            initXRefreshView(mXRefreshView, listener);
        }
    }


    private XRefreshView initXRefreshView(XRefreshView xRefreshView){
        if (xRefreshView != null){
            xRefreshView.setPullLoadEnable(false);
            xRefreshView.setPullRefreshEnable(false);
            xRefreshView.setAutoLoadMore(false);
            xRefreshView.setPinnedTime(1000);
            xRefreshView.setMoveForHorizontal(true);
        }
        return xRefreshView;
    }

    public void initXRefreshView(XRefreshView xRefreshView, final RefreshUtils.OnRefreshListener refreshListener){
        if (xRefreshView == null){
            throw new IllegalArgumentException("XRefreshView不能为null,请先设置XRefreshView");
        }
        initXRefreshView(xRefreshView);
        xRefreshView.setPullRefreshEnable(true);//设置可以下拉刷新
        xRefreshView.setCustomHeaderView(new CustomGifHeader(xRefreshView.getContext()));//样式
        xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener(){
            @Override
            public void onRefresh(boolean isPullDown) {
                clear();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (refreshListener != null){
                            refreshListener.refresh();
                        }
                    }
                },2000);
            }
        });
    }

    public interface OnRefreshListener{
        void refresh();
    }



    public XRefreshView getmXRefreshView() {
        return mXRefreshView;
    }

    /**
     * 开启刷新
     */
    public void startRefresh(){
        if (isRefresh){
            getmXRefreshView().startRefresh();
        }
    }

    /**
     * 停止刷新
     */
    public void stopRefresh(){
        if (isRefresh && getmXRefreshView().mPullRefreshing){
            getmXRefreshView().stopRefresh();
        }
    }

    /**
     * 网格布局
     * @param spanCount
     * @return
     */
    public MRecyclerView addGridLayoutManager(int spanCount){
        GridLayoutManager glm = new GridLayoutManager(this.getContext(),spanCount);
        mRecyclerView.setLayoutManager(glm);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        return this;
    }

    /**
     * 瀑布流
     * @param orientation
     * @param spanCount
     * @return
     */
    public MRecyclerView addStaggeredGridLayoutManager(int orientation,int spanCount){
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(spanCount,orientation);
        mRecyclerView.setLayoutManager(sglm);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        return this;
    }
    /**
     * 添加item内容 布局
     * @param contentLayoutRes
     * @return
     */
    public MRecyclerView addContentLayout(@LayoutRes int contentLayoutRes,ItemViewConvert convert){
        mAdapter.addContentLayout(contentLayoutRes,convert);
        return this;
    }

    /**
     * 添加 headerItemView 布局
     * @param headerLayoutRes
     * @param convert
     * @return
     */
    public MRecyclerView addHeaderLayout(@LayoutRes int headerLayoutRes,ItemViewConvert convert){
        mAdapter.addHeaderLayout(headerLayoutRes,convert);
        return this;
    }

    /**
     * 添加 footerItemView 布局
     * @param footerLayoutRes
     * @param convert
     * @return
     */
    public MRecyclerView addFooterLayout(@LayoutRes int footerLayoutRes,ItemViewConvert convert){
        mAdapter.addFooterLayout(footerLayoutRes,convert);
        return this;
    }

    public MRecyclerView create(){
        mRecyclerView.setAdapter((RecyclerView.Adapter) mAdapter);
        return this;
    }

    /**
     * 添加点击事件
     * @param clickItemListener
     * @return
     */
    public MRecyclerView addClickItemListener(OnClickItemListener clickItemListener){
        mAdapter.setOnClickItemListener(clickItemListener);
        return this;
    }

    /**
     * 添加长按事件
     * @param longClickItemListener
     * @return
     */
    public MRecyclerView addLongClickItemListener(OnLongClickItemListener longClickItemListener){
        mAdapter.setOnLongClickItemListener(longClickItemListener);
        return this;
    }

    /**
     * 添加加载更多失败监听事件
     * @param loadMoreErrorListener
     * @return
     */
    public MRecyclerView addLoadMoreErrorListener(OnLoadMoreErrorListener loadMoreErrorListener){
        mAdapter.setOnLoadMoreErrorListener(loadMoreErrorListener);
        return this;
    }

    /**
     * 加载失败
     */
    public void loadMoreError(){
        mAdapter.loadMoreError();
    }

    /**
     * 更新数据
     * @param mDatas
     * @return
     */
    public void loadDataOfNextPage(List<D> mDatas){
        mAdapter.loadDataOfNextPage(mDatas);
        if (mAdapter.getDataSizeArray().size() == 0){
            mRecyclerView.setVisibility(GONE);
            if (mEmptyView != null){
                mEmptyView.setVisibility(VISIBLE);
            }
        } else if (mAdapter.getDataSizeArray().size() == 1){
            mRecyclerView.setVisibility(VISIBLE);
            if (mEmptyView != null){
                mEmptyView.setVisibility(GONE);
            }
        }
    }

    /**
     * 插入一条数据
     * @param data
     */
    public void insert(D data){
        mAdapter.insert(data);
        moveToPosition(getDatasSize() - 1);
    }

    /**
     * 更新数据
     * @param data
     * @param position
     */
    public void update(D data,int position,Object payLoads){
        mAdapter.update(data,position,payLoads);
    }

    /**
     * 删除数据
     * @param position
     */
    public void delete(int position){
        mAdapter.delete(position);
    }


    /**
     * 删除数据
     * @param data
     */
    public void delete(D data){
        mAdapter.delete(data);
    }

    /**
     * 获取当前数据
     * @return
     */
    public List<D> getAllDatas(){
        return mAdapter.getAllDatas();
    }

    /**
     * 获取总数据量 大小
     * @return
     */
    public int getDatasSize(){
        return mAdapter.getDataSize();
    }

    public void moveToPosition(int position){
        mRecyclerView.smoothScrollToPosition(position);
    }
    /**
     * 清空数据
     */
    public void clear(){
        mAdapter.clear();
        mRecyclerView.setVisibility(GONE);
        if (mEmptyView != null){
            mEmptyView.setVisibility(VISIBLE);
        }
    }

    public void addLoadMoreListener(LoadMoreListener loadMoreListener){
        mLoadMoreListener = loadMoreListener;
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();
                if (lm instanceof  LinearLayoutManager){
                    LinearLayoutManager llm = (LinearLayoutManager) lm;
                    if (LinearLayoutManager.VERTICAL == llm.getOrientation()){//上下滑动
                        final int curLastVisibleItem = llm.findLastVisibleItemPosition();
                        if (!mAdapter.isLoadMore() || mAdapter.getMoreDelegate() instanceof MoreFinishDelegate){//已加载完所有数据
                            return;
                        }
                        if (curLastVisibleItem >= mAdapter.getItemCount() - 3 && mAdapter.getMoreDelegate() == null){
                            mAdapter.addMoreDelegate();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d(TAG,"当前屏幕最下方的位置：" + curLastVisibleItem);
                                    Log.d(TAG,"nextPage: " + mAdapter.getNextPage());
                                    mLoadMoreListener.loadMore(mAdapter.getNextPage());
                                }
                            },2000);
                        }
                    }
                } else if (lm instanceof StaggeredGridLayoutManager){//瀑布流
                    StaggeredGridLayoutManager sglm = (StaggeredGridLayoutManager) lm;
                    //获取最后一个完全显示的ItemPosition
                    int[] lastVisiblePositions = sglm.findLastVisibleItemPositions(new int[sglm.getSpanCount()]);
                    int lastVisiblePos = getMaxElem(lastVisiblePositions);
                    int totalItemCount = sglm.getItemCount();
                    if (!mAdapter.isLoadMore() || mAdapter.getMoreDelegate() instanceof MoreFinishDelegate){//已加载完所有数据
                        return;
                    }
                    // 判断是否滚动到底部
                    if (lastVisiblePos == (totalItemCount - 1) && mAdapter.getMoreDelegate() == null) {
                        //加载更多功能的代码
                        mAdapter.addMoreDelegate();
                        Log.d(TAG,"当前屏幕最下方的位置：" + lastVisiblePos);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Log.d(TAG,"nextPage: " + mAdapter.getNextPage());
                                mLoadMoreListener.loadMore(mAdapter.getNextPage());
                            }
                        },2000);
                    }
                }

            }
        });
    }

    private int getMaxElem(int[] arr) {
        int size = arr.length;
        int maxVal = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            if (arr[i]>maxVal)
                maxVal = arr[i];
        }
        return maxVal;
    }

}
