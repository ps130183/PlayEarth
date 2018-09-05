package com.km.rmbank.module.main.fragment.home;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.dto.AppointDto;
import com.km.rmbank.entity.BookVenueSitEntity;
import com.km.rmbank.module.main.appoint.ActionOutdoorActivity;
import com.km.rmbank.module.main.appoint.ActionPastDetailActivity;
import com.km.rmbank.module.main.appoint.ActionRecentInfoActivity;
import com.km.rmbank.module.main.appoint.AppointAfternoonTeaActivity;
import com.km.rmbank.module.main.appoint.AppointFragment;
import com.km.rmbank.mvp.model.AppointModel;
import com.km.rmbank.mvp.presenter.AppointPresenter;
import com.km.rmbank.mvp.view.AppointView;
import com.km.rmbank.utils.DateUtils;
import com.km.rmbank.utils.ViewUtils;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.ps.glidelib.progress.CircleProgressView;
import com.ps.mrcyclerview.BViewHolder;
import com.ps.mrcyclerview.ItemViewConvert;
import com.ps.mrcyclerview.LoadMoreListener;
import com.ps.mrcyclerview.MRecyclerView;
import com.ps.mrcyclerview.click.OnClickItemListener;
import com.ps.mrcyclerview.utils.RefreshUtils;

import java.util.Date;
import java.util.List;

public class MoreActionActivity extends BaseActivity<AppointView, AppointPresenter> implements AppointView {

    private String type;
    private int newType= 0;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_more_action;
    }

    @Override
    public String getTitleContent() {
        type = getIntent().getStringExtra("type");
        String title = "全部活动";
        return title;
    }

    @Override
    protected AppointPresenter createPresenter() {
        return new AppointPresenter(new AppointModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initRecycler();
    }

    private void initRecycler(){
        MRecyclerView<AppointDto> appointRecycler = mViewManager.findView(R.id.recyclerView);
        appointRecycler.addContentLayout(R.layout.item_appoint, new ItemViewConvert<AppointDto>() {
            @Override
            public void convert(@NonNull BViewHolder holder, AppointDto mData, int position, @NonNull List<Object> payloads) {
                holder.addRippleEffectOnClick();
                LinearLayout actionContent = holder.findView(R.id.actionContent);
                if (position == 0) {
                    ViewUtils.setMargins(actionContent, 0, ConvertUtils.dp2px(4), 0, 0);
                } else {
                    ViewUtils.setMargins(actionContent, 0, 0, 0, 0);
                }

                TextView actionTime = holder.getTextView(R.id.actionTime);
                TextView actionAddress =  holder.getTextView(R.id.actionAddress);

                GlideImageView imageView = holder.findView(R.id.actionImae);
                CircleProgressView progressView = holder.findView(R.id.progressView);
                GlideUtils.loadImageOnPregress(imageView,mData.getActivityPictureUrl(),progressView);
                holder.setText(R.id.actionTitle,mData.getTitle());

                if (mData.getType().equals("1")) {//将要举办的活动
                    actionTime.setVisibility(View.VISIBLE);
                    actionTime.setText(Html.fromHtml("时间：<font color='#3285ff'>" + DateUtils.getInstance().dateToString(new Date(mData.getStartDate()),DateUtils.YMD_DOT) + "</font>"));

                    actionAddress.setVisibility(View.VISIBLE);
                    actionAddress.setText("地址：" + mData.getAddress());
                } else {//咨询
                    if (mData.getStartDate() == 0) {
                        actionTime.setVisibility(View.INVISIBLE);
                    } else {
                        actionTime.setVisibility(View.VISIBLE);
                        actionTime.setText("时间：" + DateUtils.getInstance().dateToString(new Date(mData.getStartDate())));
                    }

                    actionAddress.setVisibility(View.VISIBLE);
                    actionAddress.setText(Html.fromHtml("<font color='#3285ff'>" + mData.getViewCount() + "</font>人已阅读"));
                }
            }

        }).create();

        appointRecycler.addRefreshListener(new RefreshUtils.OnRefreshListener() {
            @Override
            public void refresh() {
                getPresenter().getAppointList(1, newType);
            }
        });

        appointRecycler.addLoadMoreListener(new LoadMoreListener() {
            @Override
            public void loadMore(int nextPage) {
                getPresenter().getAppointList(nextPage, newType);
            }
        });

        appointRecycler.addClickItemListener(new OnClickItemListener() {
            @Override
            public void clickItem(Object mData, int position) {
                AppointDto data = (AppointDto) mData;
                if (data.getType().equals("1")) {
                    if ("3".equals(data.getNewType())) {//平台基地活动
                        Bundle bundle = new Bundle();
                        bundle.putString("scenicId", data.getClubId());
                        bundle.putString("activityId", data.getId());
//                        startActivity(ScenicActivity.class,bundle);
                        startActivity(ActionOutdoorActivity.class, bundle);
                    } else if ("1".equals(data.getNewType()) || "2".equals(data.getNewType())){//下午茶 或 晚宴
                        Bundle bundle = new Bundle();
                        bundle.putString("actionId", data.getId());
                        startActivity(AppointAfternoonTeaActivity.class, bundle);

                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putString("actionId", data.getId());
                        startActivity(ActionRecentInfoActivity.class,bundle);
                    }
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("actionPastId", data.getId());
                    startActivity(ActionPastDetailActivity.class, bundle);
                }
            }
        });
        appointRecycler.startRefresh();
    }

    @Override
    public void showAppointList(int pageNo, List<AppointDto> appointDtos) {
        MRecyclerView<AppointDto> appointRecycler = mViewManager.findView(R.id.recyclerView);
        if (pageNo == 1){
            appointRecycler.clear();
        }
        appointRecycler.stopRefresh();
        appointRecycler.loadDataOfNextPage(appointDtos);
    }

    @Override
    public void applyActionSuccess(String actionId) {

    }

    @Override
    public void showVenueSitList(List<BookVenueSitEntity> bookVenueSitEntities) {

    }
}
