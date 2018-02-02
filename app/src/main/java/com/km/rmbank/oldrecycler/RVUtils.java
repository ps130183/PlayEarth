package com.km.rmbank.oldrecycler;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.km.rmbank.R;
import com.km.rmbank.oldrecycler.rcline.DashlineItemDivider;
import com.km.rmbank.oldrecycler.rcline.DividerItemDecoration;
import com.ps.commonadapter.adapter.utils.WrapContentLinearLayoutManager;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * Created by kamangkeji on 17/1/19.
 */

public class RVUtils {

    public static final int DIVIDER_COLOR_DEFAULT = 0xfff4f2f2;
    public static final int DIVIDER_COLOR_WHITE = 0xffffffff;
    public static final int DIVIDER_COLOR_ACCOUNT_DETAILS = 0xffcfcdcd;

    public static void setLinearLayoutManage(RecyclerView rc, int orientation){
        WrapContentLinearLayoutManager llm = new WrapContentLinearLayoutManager(rc.getContext(),orientation,false);
        llm.setSmoothScrollbarEnabled(true);
        llm.setAutoMeasureEnabled(true);
        rc.setLayoutManager(llm);
        rc.setHasFixedSize(true);
        rc.setNestedScrollingEnabled(false);
    }

    public static void setDefaultLinearLayoutManage(RecyclerView rc, int orientation){
        LinearLayoutManager llm = new LinearLayoutManager(rc.getContext(),orientation,false);
        rc.setLayoutManager(llm);
    }


    public static void setGridLayoutManage(RecyclerView rv, int spanCount){
        GridLayoutManager dlm = new GridLayoutManager(rv.getContext(),spanCount);
        rv.setLayoutManager(dlm);
    }

    public static void setLoadMore(final RecyclerView rc, final BaseAdapter adapter, final RcLoadMore rcLoadMore){
        final LinearLayoutManager llm = (LinearLayoutManager) rc.getLayoutManager();
        rc.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int curFisrtVisiblePosition = llm.findFirstVisibleItemPosition();
                int visibleItemCount = llm.findLastVisibleItemPosition() - llm.findFirstVisibleItemPosition();
                int allItemCount = adapter.getAllData().size();
//                showLogI("curFisrtVisiblePosition == " + curFisrtVisiblePosition + "   allItemCount == " + allItemCount + "  visibleItemCount == " + visibleItemCount);
                if (dy > 0) {//向上滑动 加载更多
                    if (allItemCount - curFisrtVisiblePosition > visibleItemCount * 2) {
                        return;
                    }
                    rcLoadMore.onRcLoadMore();
                }
            }
        });
    }

    public interface RcLoadMore{
        void onRcLoadMore();
    }

    /**-----------------------------我是分割线---------------------------------------*/
    /**
     * 为recyclerView 添加虚线分割
     * @param rc
     */
    public static void setDashLineForRv(RecyclerView rc){
        rc.addItemDecoration(new DashlineItemDivider(0xffd2d2d2));
//        rc.addItemDecoration(new DividerItemDecoration(rc.getContext(),LinearLayoutManager.VERTICAL,0xfff5dada,2, DividerItemDecoration.LineType.fill));
//        rc.addItemDecoration(new RecycleViewDivider(rc.getContext(),LinearLayoutManager.VERTICAL, R.drawable.shape_rc_dash_divider));
//        rc.addItemDecoration(new RecycleViewDivider(activity,LinearLayoutManager.VERTICAL, 5,0xfff5dada));
    }

    /**
     * 给列表添加分割线
     * @param rv
     * @param color
     * @param origation
     * @param lineHeight
     */
    public static void addDivideItemForRv(RecyclerView rv, int color, int origation, int lineHeight){
        rv.addItemDecoration(new DividerItemDecoration(rv.getContext(),origation,color,lineHeight, DividerItemDecoration.LineType.fill));
    }

    /**
     * 给列表添加分割线
     * @param rv
     * @param color
     */
    public static void addDivideItemForRv(RecyclerView rv, int color, int lineHeight){
        addDivideItemForRv(rv,color, LinearLayoutManager.VERTICAL,lineHeight);
    }

    /**
     * 给列表添加分割线
     * @param rv
     * @param color
     */
    public static void addDivideItemForRv(RecyclerView rv, int color){
        addDivideItemForRv(rv,color, LinearLayoutManager.VERTICAL,5);
    }

    /**
     * 给列表添加分割线
     * @param rv
     */
    public static void addDivideItemForRv(RecyclerView rv){
        addDivideItemForRv(rv,DIVIDER_COLOR_DEFAULT);
    }

    /**-----------------------------我是分割线---------------------------------------*/

    public static void addSwipRefresh(final SwipeRefreshLayout mSwipeRefresh, final OnSwipeRefresh refreshListener){
        if (mSwipeRefresh == null)
            throw new RuntimeException("swipeRefreshLayout is not null");
        mSwipeRefresh.setProgressBackgroundColorSchemeResource(android.R.color.white);
        // 设置下拉进度的主题颜色
        mSwipeRefresh.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);


        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Observable.just(refreshListener)
                        .filter(new Predicate<OnSwipeRefresh>() {
                            @Override
                            public boolean test(@NonNull OnSwipeRefresh onSwipeRefresh) throws Exception {
                                return refreshListener != null;
                            }
                        })
                        .doOnNext(new Consumer<OnSwipeRefresh>() {
                            @Override
                            public void accept(@NonNull OnSwipeRefresh onSwipeRefresh) throws Exception {
                                mSwipeRefresh.setRefreshing(true);
                            }
                        })
                        .doOnNext(new Consumer<OnSwipeRefresh>() {
                            @Override
                            public void accept(@NonNull OnSwipeRefresh onSwipeRefresh) throws Exception {
                                onSwipeRefresh.onRefresh(mSwipeRefresh);
                            }
                        }).subscribe(new Consumer<OnSwipeRefresh>() {
                    @Override
                    public void accept(@NonNull OnSwipeRefresh onSwipeRefresh) throws Exception {
                        mSwipeRefresh.setRefreshing(false);
                    }
                });
//                if (refreshListener != null){
//                    // 开始刷新，设置当前为刷新状态
//                    mSwipeRefresh.setRefreshing(true);
//                    refreshListener.onRefresh(mSwipeRefresh);
//                }
            }
        });
    }

    public interface OnSwipeRefresh{
        void onRefresh(SwipeRefreshLayout mSwipeRefresh);
    }

    /**-----------------------------我是分割线---------------------------------------*/
}
