package com.ps.commonadapter.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.*;
import android.support.v7.widget.DividerItemDecoration;
import android.util.Log;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.ps.commonadapter.R;
import com.ps.commonadapter.adapter.refresh.CustomFooterView;
import com.ps.commonadapter.adapter.refresh.CustomGifHeader;
import com.ps.commonadapter.adapter.utils.WrapContentLinearLayoutManager;
import com.ps.commonadapter.recyclerviewAnimator.adapters.AlphaInAnimationAdapter;
import com.ps.commonadapter.recyclerviewAnimator.adapters.ScaleInAnimationAdapter;
import com.ps.commonadapter.recyclerviewAnimator.animators.BaseItemAnimator;
import com.ps.commonadapter.recyclerviewAnimator.animators.SlideInDownAnimator;
import com.ps.commonadapter.recyclerviewAnimator.animators.SlideInLeftToRightAnimator;
import com.ps.commonadapter.adapter.base.ItemViewDelegate;
import com.ps.commonadapter.adapter.wrapper.EmptyWrapper;
import com.ps.commonadapter.adapter.wrapper.HeaderAndFooterWrapper;
import com.ps.commonadapter.adapter.wrapper.LoadMoreWrapper;
import com.ps.commonadapter.recyclerviewAnimator.animators.SlideInLeftAnimator;
import com.ps.commonadapter.recyclerviewAnimator.animators.SlideInRightAnimator;
import com.ps.commonadapter.recyclerviewAnimator.animators.SlideInUpAnimator;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import java.util.List;


/**
 * Created by PengSong on 17/12/13.
 * RecyclerView adapter 的管理类
 * 该类通过一系列的设置会生成您想要的adapter，但设置的时候最后能遵循一定的顺序
 * 1、首先要创建新对象 传入对应的RecyclerView
 * 2、设置layoutManager(布局管理器)和divider（分割线。。可选）
 * 3、设置基础的adapter
 * 4、为adapter设置对应的动画
 * 5、为基础的adapter添加装饰类，如：empty，header,footer,loadmore等等
 * 6、最后create为RecyclerView 设置最终的adapter。
 */

public class RecyclerAdapterHelper<T> {

    private RecyclerView mRecyclerView;

    private Context mContext;

    private RecyclerView.Adapter mAdapter;

    private MultiItemTypeAdapter<T> mBasicAdapter;

    private XRefreshView xRefreshView;

    public RecyclerAdapterHelper(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
        mContext = mRecyclerView.getContext();
    }

    /**
     * 设置itemAnimator
     * @param itemAnimator
     * @param duration
     * @return
     */
    public RecyclerAdapterHelper addItemAnimator(BaseItemAnimator itemAnimator,int duration){
        mRecyclerView.setItemAnimator(itemAnimator);
        setAnimatorDuration(duration);
        return this;
    }

    /**
     * 设置itemAnimator 左进右出
     * @param duration
     * @return
     */
    public RecyclerAdapterHelper addSlidInLeftToRightAnimator(int duration){
        mRecyclerView.setItemAnimator(new SlideInLeftToRightAnimator());
        setAnimatorDuration(duration);
        return this;
    }

    /**
     * 设置itemAnimator 从左侧
     * @param duration
     * @return
     */
    public RecyclerAdapterHelper addSlidInLeftAnimator(int duration){
        mRecyclerView.setItemAnimator(new SlideInLeftAnimator());
        setAnimatorDuration(duration);
        return this;
    }

    /**
     * 设置itemAnimator 从右侧
     * @param duration
     * @return
     */
    public RecyclerAdapterHelper addSlidInRightAnimator(int duration){
        mRecyclerView.setItemAnimator(new SlideInRightAnimator());
        setAnimatorDuration(duration);
        return this;
    }

    /**
     * 设置itemAnimator 向上
     * @param duration
     * @return
     */
    public RecyclerAdapterHelper addSlidInUpAnimator(int duration){
        mRecyclerView.setItemAnimator(new SlideInUpAnimator());
        setAnimatorDuration(duration);
        return this;
    }

    /**
     * 设置itemAnimator 向下
     * @param duration
     * @return
     */
    public RecyclerAdapterHelper addSlidInDownAnimator(int duration){
        mRecyclerView.setItemAnimator(new SlideInDownAnimator());
        setAnimatorDuration(duration);
        return this;
    }

    /**
     * 设置itemAnimator 动画的执行时间
     * @param duration
     */
    private void setAnimatorDuration(int duration){
        if (duration <= 0){
            return;
        }
        mRecyclerView.getItemAnimator().setAddDuration(duration);
        mRecyclerView.getItemAnimator().setChangeDuration(duration);
        mRecyclerView.getItemAnimator().setMoveDuration(duration);
        mRecyclerView.getItemAnimator().setRemoveDuration(duration);
    }

    /**
     * 给recyclerView添加 线性布局  默认水平方向
     * @return
     */
    public RecyclerAdapterHelper addLinearLayoutManager(){
        addLinearLayoutManager(LinearLayoutManager.VERTICAL);
        return this;
    }

    /**
     * 给recyclerView添加 线性布局
     * @param orientation
     * @return
     */
    public RecyclerAdapterHelper addLinearLayoutManager(int orientation){
        WrapContentLinearLayoutManager llm = new WrapContentLinearLayoutManager(mContext,orientation,false);
        llm.setSmoothScrollbarEnabled(true);
        llm.setAutoMeasureEnabled(true);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        return this;
    }

    /**
     * 给recyclerView 添加网格布局 默认水平方向
     * @param spanCount
     * @return
     */
    public RecyclerAdapterHelper addGrigLayoutMnager(int spanCount){
        GridLayoutManager glm = new GridLayoutManager(mContext,spanCount);
        mRecyclerView.setLayoutManager(glm);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        return this;
    }

    /**
     * 给recyclerView添加网格布局
     * @param spanCount
     * @param orientation 布局方向
     */
    public RecyclerAdapterHelper addGrigLayoutMnager(int spanCount,int orientation){
        GridLayoutManager glm = new GridLayoutManager(mContext,spanCount,orientation,false);
        mRecyclerView.setLayoutManager(glm);
        return this;
    }

    /**
     * 添加分割线
     * @param orientation
     * @return
     */
    public RecyclerAdapterHelper addDividerItemDecoration(int orientation, @DrawableRes int drawableRes){
        android.support.v7.widget.DividerItemDecoration itemDecoration = new DividerItemDecoration(mContext,orientation);
        if (drawableRes > 0){
            Drawable drawable = ContextCompat.getDrawable(mContext,drawableRes);
            itemDecoration.setDrawable(drawable);
        }
        mRecyclerView.addItemDecoration(itemDecoration);
        return this;
    }
    /**
     * 添加分割线
     * @param orientation
     * @return
     */
    public RecyclerAdapterHelper addDividerItemDecoration(int orientation){
        addDividerItemDecoration(orientation, R.drawable.recycler_item_divider);
        return this;
    }

    /**
     * 添加分割线
     * @return
     */
    public RecyclerAdapterHelper addDividerItemDecorationForGrid(int orientation){
        if (orientation == DividerItemDecoration.VERTICAL){
            mRecyclerView.addItemDecoration(
                    new HorizontalDividerItemDecoration.Builder(mContext)
                            .colorResId(R.color.divide_color).build());
        } else {
            mRecyclerView.addItemDecoration(
                    new VerticalDividerItemDecoration.Builder(mContext)
                            .colorResId(R.color.divide_color).build());
        }

        return this;
    }

    /**
     * 添加commonAdapter
     * @param layoutRes
     * @param mDatas
     * @param commonConvert
     * @return
     */
    public RecyclerAdapterHelper addCommonAdapter(@LayoutRes int layoutRes, List<T> mDatas, final CommonConvert<T> commonConvert){
        mBasicAdapter = new CommonAdapter<T>(mContext,mDatas,layoutRes) {
            @Override
            public void convert(CommonViewHolder holder, T mData, int position) {
                if (commonConvert != null){
                    commonConvert.convert(holder,mData,position);
                }
            }
        };
        mAdapter = mBasicAdapter;
        return this;
    }

    public interface CommonConvert<T>{
        void convert(CommonViewHolder holder,T mData,int position);
    }

    public RecyclerAdapterHelper addMulitItemTypeAdapter(List<T> mDatas, List<ItemViewDelegate<T>> itemViewDelegates){
        mBasicAdapter = new MultiItemTypeAdapter<>(mContext,mDatas);
        for (int i = 0; i < itemViewDelegates.size(); i++){
            mBasicAdapter.addItemViewDelegate(itemViewDelegates.get(i));
        }
        mAdapter = mBasicAdapter;
        return this;
    }

    public RecyclerAdapterHelper addAdapter(MultiItemTypeAdapter adapter){
        mBasicAdapter = adapter;
        mAdapter = adapter;
        return this;
    }

    /**
     * 设置recyclerView 列表没有数据时展示的内容
     * @param emptyLayoutRes
     * @return
     */
    public RecyclerAdapterHelper addEmptyWrapper(@LayoutRes Integer emptyLayoutRes) {
        if (mAdapter == null){
            throw new IllegalArgumentException("mAdapter 不能为空，请先使用addCommonAdapter方法添加adapter");
        }
        EmptyWrapper wrapper = new EmptyWrapper(mAdapter);
        if (emptyLayoutRes != null){
            wrapper.setmEmptyLayoutRes(emptyLayoutRes);
        }
        mAdapter = wrapper;
        return this;
    }

    /**
     * 为recyclerView添加顶部 和 底部
     * @param headerLayoutIds
     * @param footerLayoutIds
     * @param loadHeaderAndFooterData
     * @return
     */
    public RecyclerAdapterHelper addHeaderAndFooterWrapper(Integer[] headerLayoutIds,
                                                           Integer[] footerLayoutIds,
                                                           HeaderAndFooterWrapper.LoadHeaderAndFooterData loadHeaderAndFooterData){
        if (mAdapter == null){
            throw new IllegalArgumentException("mAdapter 不能为空，请先使用addCommonAdapter方法添加adapter");
        }
        HeaderAndFooterWrapper wrapper = new HeaderAndFooterWrapper(mAdapter);
        if (headerLayoutIds != null){
            for (int i = 0; i < headerLayoutIds.length; i++){
                wrapper.addHeader(headerLayoutIds[i]);
            }
        }
        if (footerLayoutIds != null){
            for (int i = 0; i < footerLayoutIds.length; i++){
                wrapper.addFooter(footerLayoutIds[i]);
            }
        }
        if (loadHeaderAndFooterData != null){
            wrapper.addLoadHeaderAndFooterData(loadHeaderAndFooterData);
        }
        mAdapter = wrapper;
        return this;
    }

    /**
     * 给recyclerView 添加加载更多的功能
     * @param loadMoreListener
     * @return
     */
    public RecyclerAdapterHelper addLoadMoreWrapper(LoadMoreWrapper.OnLoadMoreListener loadMoreListener){
        if (mAdapter == null){
            throw new IllegalArgumentException("mAdapter 不能为空，请先使用addCommonAdapter方法添加adapter");
        }
        LoadMoreWrapper wrapper = new LoadMoreWrapper(mAdapter);
        if (loadMoreListener != null){
            wrapper.setOnLoadMoreListener(loadMoreListener);
        }
        mAdapter = wrapper;
        return this;
    }

    /**
     * itemView 进出屏幕的时候 缩放动画
     * @return
     */
    public RecyclerAdapterHelper addScaleInAdnimationForAdapter(){
        ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(mAdapter);
        scaleInAnimationAdapter.setFirstOnly(false);
        mAdapter = scaleInAnimationAdapter;
        return this;
    }

    /**
     * itemView 进出屏幕的时候 透明动画
     * @return
     */
    public RecyclerAdapterHelper addAlphaInAnimatorForAdapter(){
        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(mAdapter);
        alphaInAnimationAdapter.setFirstOnly(false);
        mAdapter = alphaInAnimationAdapter;
        return this;
    }

    public void create(){
        if (mRecyclerView != null && mAdapter != null){
//            ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(mAdapter);
//            scaleInAnimationAdapter.setFirstOnly(false);
//            mRecyclerView.setAdapter(scaleInAnimationAdapter);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            throw new IllegalArgumentException("mrecyclerView 或 mAdapter 不能为空，请先使用addCommonAdapter方法添加adapter");
        }
    }

    public RecyclerView.Adapter getmAdapter() {
        return mAdapter;
    }

    public MultiItemTypeAdapter getBasicAdapter(){
        return mBasicAdapter;
    }



    public RecyclerAdapterHelper addRefreshView(XRefreshView refreshView){
        xRefreshView = refreshView;
        xRefreshView.setPullLoadEnable(false);
        xRefreshView.setPullRefreshEnable(false);
        xRefreshView.setAutoLoadMore(false);
        xRefreshView.setPinnedTime(1000);
        xRefreshView.setMoveForHorizontal(true);
        return this;
    }

    /**
     * 刷新数据
     * @param refreshListener
     * @return
     */
    public RecyclerAdapterHelper addRefreshListener(final OnRefreshListener refreshListener){
        if (xRefreshView == null){
            throw new IllegalArgumentException("XRefreshView不能为null,请先设置XRefreshView");
        }
        xRefreshView.setPullRefreshEnable(true);//设置可以下拉刷新
        xRefreshView.setCustomHeaderView(new CustomGifHeader(mContext));//样式
        xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener(){
            @Override
            public void onRefresh(boolean isPullDown) {
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
        return this;
    }


//    private int curPage = 1;
//    private int nextPage = 1;
//
//    /**
//     * 加载更多
//     * @param loadMoreListener
//     * @return
//     */
//    public RecyclerAdapterHelper addLoadMoreListener(final OnLoadMoreListener loadMoreListener){
//        if (xRefreshView == null){
//            throw new IllegalArgumentException("XRefreshView不能为null,请先设置XRefreshView");
//        }
//        xRefreshView.setPullLoadEnable(true);//设置可以下拉刷新
//        xRefreshView.setCustomFooterView(new CustomFooterView(mContext));
//        xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener(){
//            @Override
//            public void onLoadMore(boolean isSilence) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (loadMoreListener != null && curPage == nextPage){
//                            nextPage = curPage + 1;
//                            loadMoreListener.loadMore(nextPage);
//                        } else {
//                            xRefreshView.stopLoadMore(false);
//                        }
//                    }
//                },2000);
//            }
//        });
//        return this;
//    }

//    /**
//     * 加载更多完成
//     * @param finish
//     */
//    public void loadMoreFinish(boolean finish){
//        if (xRefreshView == null){
//            return;
//        }
//        if (finish){
//            curPage++;
//        }
//        xRefreshView.stopLoadMore(finish);
//    }

    public interface OnRefreshListener{
        void refresh();
    }

//    public interface OnLoadMoreListener{
//        void loadMore(int nextPage);
//    }
}
