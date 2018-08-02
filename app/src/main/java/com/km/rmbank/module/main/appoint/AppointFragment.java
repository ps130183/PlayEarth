package com.km.rmbank.module.main.appoint;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseFragment;
import com.km.rmbank.dto.AppointDto;
import com.km.rmbank.event.ApplyActionEvent;
import com.km.rmbank.mvp.model.AppointModel;
import com.km.rmbank.mvp.presenter.AppointPresenter;
import com.km.rmbank.mvp.view.AppointView;
import com.km.rmbank.utils.DateUtils;
import com.km.rmbank.utils.EventBusUtils;
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
//    private List<AppointDto> appointList;
//    private RecyclerAdapterHelper<AppointDto> mHelper;
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
        newType = getArguments().getInt("type",0);
        switch (newType){
            case 1:
                newType = 4;//路演大会
                break;
            case 2:
                newType = 3;//户外活动
                break;
            case 3:
                newType = 1;//下午茶
                break;
            case 4:
                newType = 2;//结缘晚宴
                break;

            default:
                newType = 0;//全部
                break;
        }
        appointRecycler = mViewManager.findView(R.id.appointRecycler);
        initAppointRecycler();
    }

    private void initAppointRecycler() {

        appointRecycler.addContentLayout(R.layout.item_appoint, new ItemViewConvert<AppointDto>() {
            @Override
            public void convert(@NonNull BViewHolder holder, AppointDto mData, int position, @NonNull List<Object> payloads) {
                holder.addRippleEffectOnClick();
                LinearLayout actionContent = holder.findView(R.id.actionContent);
                if (position == 0){
                    ViewUtils.setMargins(actionContent,0, ConvertUtils.dp2px(4),0,0);
                } else {
                    ViewUtils.setMargins(actionContent,0, 0,0,0);
                }

                TextView free = holder.getTextView(R.id.free);
                TextView actionTime = holder.getTextView(R.id.actionTime);
                TextView actionAddress =  holder.getTextView(R.id.actionAddress);
                TextView hint = holder.findView(R.id.hint);

                GlideImageView imageView = holder.findView(R.id.actionImae);
                CircleProgressView progressView = holder.findView(R.id.progressView);
                GlideUtils.loadImageOnPregress(imageView,mData.getActivityPictureUrl(),progressView);
                holder.setText(R.id.actionTitle,mData.getTitle());

                if (mData.getType().equals("1")){//将要举办的活动
                    holder.setText(R.id.memberNum,mData.getApplyCount());
                    hint.setText("人已报名");
                    actionTime.setVisibility(View.VISIBLE);
                    actionTime.setText("举办时间：" + DateUtils.getInstance().dateToString(new Date(mData.getStartDate())));

                    actionAddress.setVisibility(View.VISIBLE);
                    actionAddress.setText("地址：" + mData.getAddress());


                    free.setText("免费");
                } else {//咨询
                    holder.setText(R.id.memberNum,mData.getViewCount());
                    free.setText(mData.getStatus());
                    hint.setText("人已浏览");
//                            baoming.setVisibility(View.GONE);
                    if (mData.getStartDate() == 0){
                        actionTime.setVisibility(View.INVISIBLE);
                    } else {
                        actionTime.setVisibility(View.VISIBLE);
                        actionTime.setText("举办时间：" + DateUtils.getInstance().dateToString(new Date(mData.getStartDate())));
                    }


                    actionAddress.setVisibility(View.INVISIBLE);
                }
            }

        }).create();

        appointRecycler.addRefreshListener(new RefreshUtils.OnRefreshListener() {
            @Override
            public void refresh() {
                getPresenter().getAppointList(1,newType,null);
            }
        });

        appointRecycler.addLoadMoreListener(new LoadMoreListener() {
            @Override
            public void loadMore(int nextPage) {
                getPresenter().getAppointList(nextPage,newType,null);
            }
        });

        appointRecycler.addClickItemListener(new OnClickItemListener() {
            @Override
            public void clickItem(Object mData, int position) {
                AppointDto data = (AppointDto) mData;
                if (data.getType().equals("1")){
                    if ("3".equals(data.getNewType())){//平台基地活动
                        Bundle bundle = new Bundle();
                        bundle.putString("scenicId",data.getClubId());
                        bundle.putString("activityId",data.getId());
//                        startActivity(ScenicActivity.class,bundle);
                        startActivity(ActionOutdoorActivity.class,bundle);
                    }  else {
                        Bundle bundle = new Bundle();
                        bundle.putString("actionId",data.getId());
                        startActivity(ActionRecentInfoActivity.class,bundle);
                    }
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("actionPastId",data.getId());
                    startActivity(ActionPastDetailActivity.class,bundle);
                }
            }
        });
        appointRecycler.startRefresh();
    }


    @Override
    public void showAppointList(LoadMoreWrapper wrapper, List<AppointDto> appointDtos) {
        appointRecycler.stopRefresh();
        appointRecycler.loadDataOfNextPage(appointDtos);
    }

    @Override
    public void applyActionSuccess(String actionId) {
        showToast("报名成功");
        EventBusUtils.post(new ApplyActionEvent(actionId));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void applySuccess(ApplyActionEvent event){
        List<AppointDto> appointList = appointRecycler.getAllDatas();
        for (int i = 0; i < appointList.size(); i++){
            AppointDto appointDto = appointList.get(i);
            if (event.getActionId().equals(appointDto.getId())){
                int count = Integer.parseInt(appointDto.getApplyCount());
                appointDto.setApplyCount((count + 1)+"");
                appointRecycler.update(appointDto,i,null);
                break;
            }
        }
    }
}
