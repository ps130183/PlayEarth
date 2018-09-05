package com.km.rmbank.module.main.appoint;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseFragment;
import com.km.rmbank.dto.AppointDto;
import com.km.rmbank.entity.BookVenueSitEntity;
import com.km.rmbank.event.ApplyActionEvent;
import com.km.rmbank.mvp.model.AppointModel;
import com.km.rmbank.mvp.presenter.AppointPresenter;
import com.km.rmbank.mvp.view.AppointView;
import com.km.rmbank.utils.DateUtils;
import com.km.rmbank.utils.EventBusUtils;
import com.km.rmbank.utils.StringUtils;
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

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * 首页约吗
 */
public class AppointFragment extends BaseFragment<AppointView, AppointPresenter> implements AppointView {

    private MRecyclerView<AppointDto> appointRecycler;
    private MRecyclerView<BookVenueSitEntity> actionVenueRecycler;

    private int newType = 0;

    public static AppointFragment newInstance(Bundle bundle) {

        AppointFragment fragment = new AppointFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected AppointPresenter createPresenter() {
        return new AppointPresenter(new AppointModel());
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_appoint;
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {
        newType = getArguments().getInt("type", 0);
        switch (newType) {
            case 1:
                newType = 1;//下午茶
                break;
            case 2:
                newType = 3;//路演大会
                break;
            case 3:
                newType = 2;//结缘晚宴
                break;
            case 4:
                newType = 4;//户外活动
                break;
            default:
                newType = 0;//全部
                break;
        }
        if (newType == 0) {
            appointRecycler = mViewManager.findView(R.id.appointRecycler);
            initAppointRecycler();
        } else {
            initActionVenueRecycler();
        }

    }

    private void initAppointRecycler() {

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
                TextView actionAddress = holder.getTextView(R.id.actionAddress);

                GlideImageView imageView = holder.findView(R.id.actionImae);
                CircleProgressView progressView = holder.findView(R.id.progressView);
                GlideUtils.loadImageOnPregress(imageView, mData.getActivityPictureUrl(), progressView);
                holder.setText(R.id.actionTitle, mData.getTitle());

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

    private void initActionVenueRecycler() {
        actionVenueRecycler = mViewManager.findView(R.id.appointRecycler);

        actionVenueRecycler.setDividerColor(getResources().getColor(R.color.white))
                .setDividerWidth(0)
                .refreshRecycler();
        actionVenueRecycler.addContentLayout(R.layout.item_appoint_venue_list, new ItemViewConvert<BookVenueSitEntity>() {
            @Override
            public void convert(@NonNull BViewHolder holder, BookVenueSitEntity mData, int position, @NonNull List<Object> payloads) {
                GlideImageView imageView = holder.findView(R.id.imageView);
                GlideUtils.loadImageOnPregress(imageView, mData.getImageUrl(), null);

                holder.setText(R.id.venue_name, mData.getTitle());
            }

        }).create();

        actionVenueRecycler.addRefreshListener(new RefreshUtils.OnRefreshListener() {
            @Override
            public void refresh() {
                if (newType == 4){
                    getPresenter().getBaseList();
                } else {
                    getPresenter().getBookVenueSitList(newType);
                }
            }
        });
        actionVenueRecycler.addClickItemListener(new OnClickItemListener() {
            @Override
            public void clickItem(Object mData, int position) {
                BookVenueSitEntity data = (BookVenueSitEntity) mData;
                Bundle bundle = new Bundle();
                bundle.putString("placeId",data.getId());
                bundle.putString("title",data.getTitle());
                bundle.putInt("newType",newType);
                startActivity(AppointActionListActivity.class,bundle);
            }
        });

        actionVenueRecycler.startRefresh();
    }


    @Override
    public void showAppointList(int pageNo, List<AppointDto> appointDtos) {
        appointRecycler.stopRefresh();
        if (pageNo == 1){
            appointRecycler.clear();
        }
        appointRecycler.loadDataOfNextPage(appointDtos);
    }

    @Override
    public void applyActionSuccess(String actionId) {
        showToast("报名成功");
        EventBusUtils.post(new ApplyActionEvent(actionId));
    }

    @Override
    public void showVenueSitList(List<BookVenueSitEntity> bookVenueSitEntities) {
        actionVenueRecycler.stopRefresh();
        actionVenueRecycler.clear();
        for (BookVenueSitEntity entity : bookVenueSitEntities) {
            entity.setItemViewRes(R.layout.item_appoint_venue_list);
        }
        actionVenueRecycler.loadDataOfNextPage(bookVenueSitEntities);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void applySuccess(ApplyActionEvent event) {
        List<AppointDto> appointList = appointRecycler.getAllDatas();
        for (int i = 0; i < appointList.size(); i++) {
            AppointDto appointDto = appointList.get(i);
            if (event.getActionId().equals(appointDto.getId())) {
                int count = Integer.parseInt(appointDto.getApplyCount());
                appointDto.setApplyCount((count + 1) + "");
                appointRecycler.update(appointDto, i, null);
                break;
            }
        }
    }
}
