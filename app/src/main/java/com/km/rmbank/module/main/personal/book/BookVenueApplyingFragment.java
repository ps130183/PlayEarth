package com.km.rmbank.module.main.personal.book;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.daimajia.swipe.SwipeLayout;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseFragment;
import com.km.rmbank.entity.BookVenueApplyDto;
import com.km.rmbank.mvp.model.BookVenueApplyModel;
import com.km.rmbank.mvp.presenter.BookVenueApplyPresenter;
import com.km.rmbank.mvp.view.BookVenueApplyView;
import com.km.rmbank.utils.DateUtils;
import com.km.rmbank.utils.dialog.DialogUtils;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.ps.mrcyclerview.BViewHolder;
import com.ps.mrcyclerview.ItemViewConvert;
import com.ps.mrcyclerview.MRecyclerView;
import com.ps.mrcyclerview.click.OnClickItemListener;
import com.ps.mrcyclerview.utils.RefreshUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookVenueApplyingFragment extends BaseFragment<BookVenueApplyView,BookVenueApplyPresenter> implements BookVenueApplyView {


    public static BookVenueApplyingFragment newInstance(Bundle bundle) {
        BookVenueApplyingFragment fragment = new BookVenueApplyingFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_book_venue_applying;
    }

    @Override
    protected BookVenueApplyPresenter createPresenter() {
        return new BookVenueApplyPresenter(new BookVenueApplyModel());
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {
        initRecycler();
    }

    private void initRecycler(){
        MRecyclerView<BookVenueApplyDto> mRecyclerView = mViewManager.findView(R.id.recyclerView);
        mRecyclerView.addContentLayout(R.layout.item_book_venue_applying_swipe, new ItemViewConvert<BookVenueApplyDto>() {
            @Override
            public void convert(@NonNull BViewHolder holder, final BookVenueApplyDto mData, int position, @NonNull List<Object> payloads) {

                SwipeLayout swipeLayout = holder.findView(R.id.swipe_layout);
                if (swipeLayout != null) {
                    //set show mode.
                    swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
                    swipeLayout.setSwipeEnabled(true);
                    swipeLayout.setClickToClose(true); //点击其他区域关闭侧滑
                }

                holder.findView(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogUtils.showDefaultAlertDialog("是否取消该场地的预约申请?", new DialogUtils.ClickListener() {
                            @Override
                            public void clickConfirm() {
                                getPresenter().cancelBookVenueApply(mData);
                            }
                        });

                    }
                });

                holder.findView(R.id.content).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("bookVenueApplyInfo",mData);
                        startActivity(BookVenueDetailsActivity.class,bundle);
                    }
                });

                GlideImageView logo = holder.findView(R.id.iv_venue_logo);
                GlideUtils.loadImageOnPregress(logo,mData.getPlaceUrl(),null);
                holder.setText(R.id.tv_venue_name,mData.getPlaceTitle());
                String status = "";
                if ("1".equals(mData.getPlaceType())){
                    status = "下午茶";
                } else if ("2".equals(mData.getPlaceType())){
                    status = "结缘晚宴";
                } else if ("3".equals(mData.getPlaceType())){
                    status = "路演大会";
                } else {
                    holder.findView(R.id.tv_venue_type).setVisibility(View.GONE);
                }
                holder.setText(R.id.tv_venue_type,status);

                holder.setText(R.id.tv_venue_time,"申请时间：" + DateUtils.getInstance().dateToString(new Date(mData.getStartDate()),DateUtils.YMDHM)
                                                                + " - "
                                                                + DateUtils.getInstance().dateToString(new Date(mData.getEndDate()),DateUtils.YMDHM));

            }
        }).create();

        mRecyclerView.addRefreshListener(new RefreshUtils.OnRefreshListener() {
            @Override
            public void refresh() {
                getPresenter().getBookVenueApplyList("1");
            }
        });

        mRecyclerView.startRefresh();
    }

    @Override
    public void showBookVenueApplyList(List<BookVenueApplyDto> bookVenueApplyDtos) {
        MRecyclerView<BookVenueApplyDto> mRecyclerView = mViewManager.findView(R.id.recyclerView);
        mRecyclerView.stopRefresh();
        mRecyclerView.clear();
        for (BookVenueApplyDto bookVenueApplyDto : bookVenueApplyDtos){
            bookVenueApplyDto.setItemViewRes(R.layout.item_book_venue_applying_swipe);
        }
        mRecyclerView.loadDataOfNextPage(bookVenueApplyDtos);
    }

    @Override
    public void cancelSuccess(BookVenueApplyDto bookVenueApplyDto) {
        MRecyclerView<BookVenueApplyDto> mRecyclerView = mViewManager.findView(R.id.recyclerView);
        mRecyclerView.delete(bookVenueApplyDto);
    }
}
