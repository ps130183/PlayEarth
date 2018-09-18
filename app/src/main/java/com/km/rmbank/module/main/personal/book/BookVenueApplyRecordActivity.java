package com.km.rmbank.module.main.personal.book;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.entity.BookVenueApplyDto;
import com.km.rmbank.entity.BookVenueApplyPassedEntity;
import com.km.rmbank.entity.BookVenueApplyPassedEntity;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookVenueApplyRecordActivity extends BaseActivity<BookVenueApplyView,BookVenueApplyPresenter> implements BookVenueApplyView {

    @Override
    public int getContentViewRes() {
        return R.layout.activity_book_venue_apply_record;
    }

    @Override
    protected BookVenueApplyPresenter createPresenter() {
        return new BookVenueApplyPresenter(new BookVenueApplyModel());
    }

    @Override
    public String getTitleContent() {
        return "申请记录";
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initRecycler();
    }

    private void initRecycler(){
        MRecyclerView<BookVenueApplyDto> mRecyclerView = mViewManager.findView(R.id.recyclerView);
        mRecyclerView.addContentLayout(R.layout.item_book_venue_applying, new ItemViewConvert<BookVenueApplyDto>() {
            @Override
            public void convert(@NonNull BViewHolder holder, BookVenueApplyDto mData, int position, @NonNull List<Object> payloads) {
                String type = mData.getStatus();
                GlideImageView applyStatus = holder.findView(R.id.status);
                switch (type){
                    case "1":
                        GlideUtils.loadImageByRes(applyStatus,R.mipmap.icon_venue_applying);
                        break;
                    case "2":
                    case "5":
                        GlideUtils.loadImageByRes(applyStatus,R.mipmap.icon_venue_passed);
                        break;
                    case "3":
                        GlideUtils.loadImageByRes(applyStatus,R.mipmap.icon_venue_not_passed);
                        break;
                    case "4":
                        GlideUtils.loadImageByRes(applyStatus,R.mipmap.icon_venue_book_cancel);
                        break;
                }


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
                BookVenueApplyDto bookVenueApplyDto = (BookVenueApplyDto) mData;
//                Bundle bundle = new Bundle();
//                bundle.putParcelable("bookVenueApplyInfo",bookVenueApplyDto);
//                if (System.currentTimeMillis() - bookVenueApplyDto.getEndDate() >= 0 ){
//                    bundle.putBoolean("isFinished",true);
//                }
//
//                if ("2".equals(bookVenueApplyDto.getPlaceType()) && "0".equals(bookVenueApplyDto.getPayStatus())){//晚宴场地需支付完再发布
//                    startActivity(PayWanYanVenueActivity.class,bundle);
//                    return;
//                }
//
//                startActivity(BookVenueDetailsActivity.class,bundle);

                Bundle bundle = new Bundle();
                bundle.putString("placeReservationId",bookVenueApplyDto.getId());
                    bundle.putString("imageUrl",bookVenueApplyDto.getPlaceUrl());
                startActivity(ReleaseActionDetailsActivity.class,bundle);
            }
        });

        mRecyclerView.addRefreshListener(new RefreshUtils.OnRefreshListener() {
            @Override
            public void refresh() {
                getPresenter().getBookVenueApplyList("");
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
            bookVenueApplyDto.setItemViewRes(R.layout.item_book_venue_applying);
        }
        mRecyclerView.loadDataOfNextPage(bookVenueApplyDtos);
    }

    @Override
    public void cancelSuccess(BookVenueApplyDto bookVenueApplyDto) {

    }
}
