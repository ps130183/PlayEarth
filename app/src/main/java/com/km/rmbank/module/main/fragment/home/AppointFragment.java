package com.km.rmbank.module.main.fragment.home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseFragment;
import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.dto.AppointDto;
import com.km.rmbank.module.main.club.ActionPastDetailActivity;
import com.km.rmbank.module.main.club.ActionRecentInfoActivity;
import com.km.rmbank.mvp.model.AppointModel;
import com.km.rmbank.mvp.presenter.AppointPresenter;
import com.km.rmbank.mvp.view.AppointView;
import com.km.rmbank.utils.DateUtils;
import com.km.rmbank.utils.SwipeRefreshUtils;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.MultiItemTypeAdapter;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;
import com.ps.commonadapter.adapter.wrapper.LoadMoreWrapper;
import com.ps.glidelib.GlideUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * 首页约吗
 */
public class AppointFragment extends BaseFragment<AppointView, AppointPresenter> implements AppointView {

    private RecyclerView appointRecycler;
    private List<ActionDto> appointList;
    private RecyclerAdapterHelper<ActionDto> mHelper;

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
                .addCommonAdapter(R.layout.item_appoint, appointList, new RecyclerAdapterHelper.CommonConvert<ActionDto>() {
                    @Override
                    public void convert(CommonViewHolder holder, ActionDto mData, int position) {
                        holder.addRippleEffectOnClick();
                        GlideUtils.loadImage(getContext(),mData.getActivityPictureUrl(),holder.getImageView(R.id.actionImae));
                        holder.setText(R.id.actionTitle,mData.getTitle());
                        holder.setText(R.id.actionTime, DateUtils.getInstance().dateToString(new Date(mData.getStartDate()),DateUtils.YMDHM));

                        holder.setText(R.id.actionAddress,mData.getAddress());

                        if (mData.getIsDynamic() == 0){//未编辑
                            holder.setText(R.id.memberNum,mData.getApplyCount() + "人已报名");
                        } else {
                            holder.setText(R.id.memberNum,"已结束");
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

        mHelper.getBasicAdapter().setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<ActionDto>() {
            @Override
            public void onItemClick(CommonViewHolder holder, ActionDto data, int position) {
                if (data.getIsDynamic() == 0){
                    Bundle bundle = new Bundle();
                    bundle.putString("actionId",data.getId());
                    startActivity(ActionRecentInfoActivity.class,bundle);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("actionPastId",data.getId());
                    startActivity(ActionPastDetailActivity.class,bundle);
                }

            }

            @Override
            public boolean onItemLongClick(CommonViewHolder holder, ActionDto data, int position) {
                return false;
            }

        });
    }


    @Override
    public void showAppointList(LoadMoreWrapper wrapper, List<ActionDto> appointDtos) {
        if (wrapper != null){
            wrapper.setLoadMoreFinish(appointDtos.size());
        }
        hideLoading();
        appointList.addAll(appointDtos);
        appointRecycler.getAdapter().notifyDataSetChanged();
    }
}
