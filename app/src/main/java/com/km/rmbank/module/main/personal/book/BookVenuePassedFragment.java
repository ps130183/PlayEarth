package com.km.rmbank.module.main.personal.book;


import android.os.Binder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseFragment;
import com.km.rmbank.entity.BookVenueApplyDto;
import com.km.rmbank.entity.BookVenueApplyPassedEntity;
import com.km.rmbank.event.PayWanYanVenueEvent;
import com.km.rmbank.module.main.payment.PayWanYanSuccessActivity;
import com.km.rmbank.mvp.model.BookVenueApplyModel;
import com.km.rmbank.mvp.presenter.BookVenueApplyPresenter;
import com.km.rmbank.mvp.view.BookVenueApplyView;
import com.km.rmbank.utils.DateUtils;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.ps.mrcyclerview.BViewHolder;
import com.ps.mrcyclerview.ItemViewConvert;
import com.ps.mrcyclerview.MRecyclerView;
import com.ps.mrcyclerview.click.OnClickItemListener;
import com.ps.mrcyclerview.utils.RefreshUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookVenuePassedFragment extends BaseFragment<BookVenueApplyView,BookVenueApplyPresenter> implements BookVenueApplyView {

    public static BookVenuePassedFragment newInstance(Bundle bundle) {
        BookVenuePassedFragment fragment = new BookVenuePassedFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_book_venue_passed;
    }

    @Override
    protected BookVenueApplyPresenter createPresenter() {
        return new BookVenueApplyPresenter(new BookVenueApplyModel());
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {
        initRecycler();
    }

    @Override
    public void onResume() {
        super.onResume();
        MRecyclerView<BookVenueApplyDto> mRecyclerView = mViewManager.findView(R.id.recyclerView);
        mRecyclerView.startRefresh();
    }

    private void initRecycler(){
        MRecyclerView<BookVenueApplyDto> mRecyclerView = mViewManager.findView(R.id.recyclerView);
        mRecyclerView.addContentLayout(R.layout.item_book_venue_applying, new ItemViewConvert<BookVenueApplyDto>() {
            @Override
            public void convert(@NonNull BViewHolder holder, BookVenueApplyDto mData, int position, @NonNull List<Object> payloads) {

                GlideImageView applyStatus = holder.findView(R.id.status);

//                long curTime = System.currentTimeMillis();
                    GlideUtils.loadImageByRes(applyStatus,R.mipmap.icon_venue_finished);
//                if (curTime > mData.getEndDate()){
//                } else {
//                    GlideUtils.loadImageByRes(applyStatus,R.mipmap.icon_venue_release);
//                }


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

                holder.setText(R.id.tv_venue_time,"申请时间：" + DateUtils.getInstance().dateToString(new Date(mData.getStartDate()),DateUtils.YMDHM));
            }
        }).create();

        mRecyclerView.addClickItemListener(new OnClickItemListener() {
            @Override
            public void clickItem(Object mData, int position) {
                BookVenueApplyDto venueApplyDto = (BookVenueApplyDto) mData;
//                if (venueApplyDto.getStatus().equals("2")){
//                    long curTime = System.currentTimeMillis();
//                    Bundle bundle = new Bundle();
//                    bundle.putParcelable("bookVenueApplyInfo",venueApplyDto);
//                    if (curTime > venueApplyDto.getEndDate()){//已完成 去 场地申请详情
//                        bundle.putBoolean("isFinished",true);
//                    } else { //发布活动
//                        if ("2".equals(venueApplyDto.getPlaceType()) && "0".equals(venueApplyDto.getPayStatus())){//晚宴场地需支付完再发布
//                            startActivity(PayWanYanVenueActivity.class,bundle);
//                            return;
//                        }
//                        bundle.putBoolean("isFinished",false);
//                    }
//
//                    startActivity(BookVenueDetailsActivity.class,bundle);
//
//                } else {//活动详情
                    Bundle bundle = new Bundle();
                    bundle.putString("placeReservationId",venueApplyDto.getId());
                    bundle.putString("imageUrl",venueApplyDto.getPlaceUrl());
                    startActivity(ReleaseActionDetailsActivity.class,bundle);
//                }
            }
        });


        mRecyclerView.addRefreshListener(new RefreshUtils.OnRefreshListener() {
            @Override
            public void refresh() {
                getPresenter().getBookVenueApplyList("2");
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void paySuccess(PayWanYanVenueEvent event){
        Bundle bundle = new Bundle();
        bundle.putParcelable("VenueApplyDto",event.getBookVenueApplyDto());
        startActivity(PayWanYanSuccessActivity.class,bundle);
    }

    @Override
    public void showBookVenueApplyList(List<BookVenueApplyDto> bookVenueApplyDtos) {
        MRecyclerView<BookVenueApplyDto> mRecyclerView = mViewManager.findView(R.id.recyclerView);
        mRecyclerView.stopRefresh();
        mRecyclerView.clear();
        for (BookVenueApplyDto bookVenueApplyDto : bookVenueApplyDtos){
            bookVenueApplyDto.setItemViewRes(R.layout.item_book_venue_applying);
        }
        mRecyclerView.loadDataOfNextPage(bookVenueApplyDtos);
    }

    @Override
    public void cancelSuccess(BookVenueApplyDto bookVenueApplyDto) {

    }
}
