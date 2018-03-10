package com.km.rmbank.module.main.fragment.home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseFragment;
import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.dto.AppointDto;
import com.km.rmbank.module.main.club.ActionPastDetailActivity;
import com.km.rmbank.module.main.club.ActionRecentInfoActivity;
import com.km.rmbank.module.main.scenic.ScenicActivity;
import com.km.rmbank.mvp.model.AppointModel;
import com.km.rmbank.mvp.presenter.AppointPresenter;
import com.km.rmbank.mvp.view.AppointView;
import com.km.rmbank.utils.DateUtils;
import com.km.rmbank.utils.SwipeRefreshUtils;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.MultiItemTypeAdapter;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;
import com.ps.commonadapter.adapter.wrapper.LoadMoreWrapper;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.ps.glidelib.progress.CircleProgressView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * 首页约吗
 */
public class AppointFragment extends BaseFragment<AppointView, AppointPresenter> implements AppointView {

    private RecyclerView appointRecycler;
    private List<AppointDto> appointList;
    private RecyclerAdapterHelper<AppointDto> mHelper;

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
        appointList = new ArrayList<>();
        appointRecycler = mViewManager.getRecyclerView(R.id.appointRecycler);
        initAppointRecycler();
    }

    private void initAppointRecycler() {
        mHelper = new RecyclerAdapterHelper<>(appointRecycler);
        mHelper.addLinearLayoutManager()
                .addDividerItemDecoration(LinearLayoutManager.VERTICAL)
                .addCommonAdapter(R.layout.item_appoint, appointList, new RecyclerAdapterHelper.CommonConvert<AppointDto>() {
                    @Override
                    public void convert(CommonViewHolder holder, final AppointDto mData, int position) {
                        holder.addRippleEffectOnClick();
                        TextView free = holder.getTextView(R.id.free);
                        TextView actionTime = holder.getTextView(R.id.actionTime);
                        TextView actionAddress =  holder.getTextView(R.id.actionAddress);

                        GlideImageView imageView = holder.findView(R.id.actionImae);
                        CircleProgressView progressView = holder.findView(R.id.progressView);
                        GlideUtils.loadImageOnPregress(imageView,mData.getActivityPictureUrl(),progressView);
                        holder.setText(R.id.actionTitle,mData.getTitle());

                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (mData.getType().equals("1")){
                                    if ("3".equals(mData.getNewType())){//平台基地活动
                                        Bundle bundle = new Bundle();
                                        bundle.putString("scenicId",mData.getClubId());
                                        bundle.putString("activityId",mData.getId());
                                        startActivity(ScenicActivity.class,bundle);
                                    } else {
                                        Bundle bundle = new Bundle();
                                        bundle.putString("actionId",mData.getId());
                                        startActivity(ActionRecentInfoActivity.class,bundle);
                                    }
                                } else {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("actionPastId",mData.getId());
                                    startActivity(ActionPastDetailActivity.class,bundle);
                                }
                            }
                        });

                        if (mData.getType().equals("1")){//将要举办的活动
                            holder.setText(R.id.memberNum,mData.getApplyCount() + "人已报名");
                            actionTime.setVisibility(View.VISIBLE);
                            actionTime.setText("举办时间：" + DateUtils.getInstance().dateToString(new Date(mData.getStartDate())));

                            actionAddress.setVisibility(View.VISIBLE);
                            actionAddress.setText(mData.getAddress());

                            free.setText("免费");
                        } else {//咨询
                            holder.setText(R.id.memberNum,mData.getStatus());
                            free.setText("浏览量：" + mData.getViewCount());
                            if (mData.getStartDate() == 0){
                                actionTime.setVisibility(View.INVISIBLE);
                            } else {
                                actionTime.setVisibility(View.VISIBLE);
                                actionTime.setText("举办时间：" + DateUtils.getInstance().dateToString(new Date(mData.getStartDate())));
                            }


                            actionAddress.setVisibility(View.INVISIBLE);
                        }
                    }
                }).addRefreshView(mXRefreshView)
                .addRefreshListener(new RecyclerAdapterHelper.OnRefreshListener() {
                    @Override
                    public void refresh() {
                        appointList.clear();
                        getPresenter().getAppointList(1,null);
                    }
                }).addLoadMoreWrapper(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequest(LoadMoreWrapper wrapper, int nextPage) {
                getPresenter().getAppointList(nextPage,wrapper);
            }
        }).create();
        showLoading();

        mHelper.getBasicAdapter().setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<AppointDto>() {
            @Override
            public void onItemClick(CommonViewHolder holder, AppointDto data, int position) {
                if (data.getType().equals("1")){
                    if ("3".equals(data.getNewType())){//平台基地活动
                        Bundle bundle = new Bundle();
                        bundle.putString("scenicId",data.getClubId());
                        bundle.putString("activityId",data.getId());
                        startActivity(ScenicActivity.class,bundle);
                    } else {
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

            @Override
            public boolean onItemLongClick(CommonViewHolder holder, AppointDto data, int position) {
                return false;
            }

        });
    }


    @Override
    public void showAppointList(LoadMoreWrapper wrapper, List<AppointDto> appointDtos) {
        if (wrapper != null){
            wrapper.setLoadMoreFinish(appointDtos.size());
        }
        hideLoading();
        appointList.addAll(appointDtos);
        appointRecycler.getAdapter().notifyDataSetChanged();
    }
}
