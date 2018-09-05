package com.km.rmbank.module.main.appoint;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.AppointDto;
import com.km.rmbank.entity.BookVenueSitEntity;
import com.km.rmbank.mvp.model.AppointModel;
import com.km.rmbank.mvp.presenter.AppointPresenter;
import com.km.rmbank.mvp.view.AppointView;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.DateUtils;
import com.km.rmbank.utils.StringUtils;
import com.km.rmbank.utils.ViewUtils;
import com.ps.commonadapter.adapter.wrapper.LoadMoreWrapper;
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

public class AppointActionListActivity extends BaseActivity<AppointView, AppointPresenter> implements AppointView {

    private SimpleTitleBar simpleTitleBar;
    private String placeId;
    private int newType;
    @Override
    public int getContentViewRes() {
        return R.layout.activity_appoint_action_list;
    }

    @Override
    protected AppointPresenter createPresenter() {
        return new AppointPresenter(new AppointModel());
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        simpleTitleBar = (SimpleTitleBar) titleBar;
    }

    @Override
    public String getTitleContent() {
        String title = getIntent().getStringExtra("title");
        return title;
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        placeId = getIntent().getStringExtra("placeId");
        newType = getIntent().getIntExtra("newType",0);
        initRecycler();
    }

    private void initRecycler(){
        MRecyclerView<AppointDto> mRecyclerView = mViewManager.findView(R.id.recyclerView);
        mRecyclerView.addContentLayout(R.layout.item_appoint, new ItemViewConvert<AppointDto>() {
            @Override
            public void convert(@NonNull BViewHolder holder, AppointDto mData, int position, @NonNull List<Object> payloads) {
                holder.addRippleEffectOnClick();
                LinearLayout actionContent = holder.findView(R.id.actionContent);
                if (position == 0){
                    ViewUtils.setMargins(actionContent,0, ConvertUtils.dp2px(4),0,0);
                } else {
                    ViewUtils.setMargins(actionContent,0, 0,0,0);
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

        mRecyclerView.addClickItemListener(new OnClickItemListener() {
            @Override
            public void clickItem(Object mData, int position) {
                AppointDto data = (AppointDto) mData;
                Bundle bundle = getIntent().getExtras();
                if (data.getIsDynamic().equals("0")){
                    if ("3".equals(data.getNewType())){//平台基地活动
                        bundle.putString("scenicId",data.getClubId());
                        bundle.putString("activityId",data.getId());
//                        startActivity(ScenicActivity.class,bundle);
                        startActivity(ActionOutdoorActivity.class,bundle);
                    } else if ("1".equals(data.getNewType()) || "2".equals(data.getNewType())){//下午茶 或 晚宴
                        bundle.putString("actionId", data.getId());
                        startActivity(AppointAfternoonTeaActivity.class, bundle);
                    } else {
                        bundle.putString("actionId", data.getId());
                        startActivity(ActionRecentInfoActivity.class,bundle);
                    }
                } else {
                    bundle.putString("activityId",data.getId());
                    startActivity(ActionPastDetailActivity.class,bundle);
                }
            }
        });

        mRecyclerView.addRefreshListener(new RefreshUtils.OnRefreshListener() {
            @Override
            public void refresh() {
                if (newType == 4){
                    getPresenter().getActionListByPlace(1,"",placeId);
                } else {
                    getPresenter().getActionListByPlace(1,placeId,"");
                }
            }
        });
        mRecyclerView.addLoadMoreListener(new LoadMoreListener() {
            @Override
            public void loadMore(int nextPage) {
                if (newType == 4){
                    getPresenter().getActionListByPlace(nextPage,"",placeId);
                } else {
                    getPresenter().getActionListByPlace(nextPage,placeId,"");
                }

            }
        });
        mRecyclerView.startRefresh();
    }

    @Override
    public void showAppointList(int pageNo, List<AppointDto> appointDtos) {
        MRecyclerView<AppointDto> mRecyclerView = mViewManager.findView(R.id.recyclerView);
        if (pageNo == 1){
            mRecyclerView.clear();
        }
        mRecyclerView.stopRefresh();
        mRecyclerView.loadDataOfNextPage(appointDtos);
    }

    @Override
    public void applyActionSuccess(String actionId) {

    }

    @Override
    public void showVenueSitList(List<BookVenueSitEntity> bookVenueSitEntities) {

    }
}
