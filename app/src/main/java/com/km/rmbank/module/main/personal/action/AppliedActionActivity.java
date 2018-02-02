package com.km.rmbank.module.main.personal.action;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.module.main.club.ActionRecentInfoActivity;
import com.km.rmbank.mvp.model.AppointModel;
import com.km.rmbank.mvp.presenter.AppointPresenter;
import com.km.rmbank.mvp.view.AppointView;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.DateUtils;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.MultiItemTypeAdapter;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;
import com.ps.commonadapter.adapter.wrapper.LoadMoreWrapper;
import com.ps.glidelib.GlideUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class AppliedActionActivity extends BaseActivity<AppointView, AppointPresenter> implements AppointView {

    @BindView(R.id.actionRecycler)
    RecyclerView actionRecycler;

    private List<ActionDto> appointList;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_applied_action;
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setTitleContent("已报名活动");
    }

    @Override
    protected AppointPresenter createPresenter() {
        return new AppointPresenter(new AppointModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initRecycler();
    }

    private void initRecycler() {
        appointList = new ArrayList<>();

        RecyclerAdapterHelper<ActionDto> mHelper = new RecyclerAdapterHelper<>(actionRecycler);
        mHelper.addLinearLayoutManager()
                .addDividerItemDecoration(LinearLayoutManager.VERTICAL)
                .addCommonAdapter(R.layout.item_appoint, appointList, new RecyclerAdapterHelper.CommonConvert<ActionDto>() {
                    @Override
                    public void convert(CommonViewHolder holder, ActionDto mData, int position) {
                        holder.addRippleEffectOnClick();
                        GlideUtils.loadImage(mInstance, mData.getActivityPictureUrl(), holder.getImageView(R.id.actionImae));
                        holder.setText(R.id.actionTitle, mData.getTitle());
                        holder.setText(R.id.actionTime, DateUtils.getInstance().dateToString(new Date(mData.getStartDate()), DateUtils.YMDHM));

                        holder.getTextView(R.id.free).setVisibility(View.GONE);
                        holder.setText(R.id.actionAddress, mData.getAddress());
                        if (mData.getStatus().equals("0")){
                            holder.setText(R.id.memberNum, "未参加");
                        } else {
                            holder.setText(R.id.memberNum, "已参加");
                        }

                    }
                }).addLoadMoreWrapper(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequest(LoadMoreWrapper wrapper, int nextPage) {
                getPresenter().getAppointAppliedList(nextPage, wrapper);
            }
        }).create();
        mHelper.getBasicAdapter().setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<ActionDto>() {
            @Override
            public void onItemClick(CommonViewHolder holder, ActionDto data, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("actionId",data.getId());
                startActivity(ActionRecentInfoActivity.class,bundle);
            }

            @Override
            public boolean onItemLongClick(CommonViewHolder holder, ActionDto data, int position) {
                return false;
            }

        });
        getPresenter().getAppointAppliedList(1,null);
    }

    @Override
    public void showAppointList(LoadMoreWrapper wrapper, List<ActionDto> appointDtos) {
        if (wrapper == null) {
            appointList.clear();
        } else {
            wrapper.setLoadMoreFinish(appointDtos.size());
        }
        appointList.addAll(appointDtos);
        actionRecycler.getAdapter().notifyDataSetChanged();
    }
}
