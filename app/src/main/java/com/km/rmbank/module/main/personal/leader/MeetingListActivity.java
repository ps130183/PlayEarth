package com.km.rmbank.module.main.personal.leader;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.mvp.model.MeetingListModel;
import com.km.rmbank.mvp.presenter.MeetingListPresenter;
import com.km.rmbank.mvp.view.IMeetingListView;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.DateUtils;
import com.ps.commonadapter.adapter.CommonAdapter;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class MeetingListActivity extends BaseActivity<IMeetingListView,MeetingListPresenter> implements IMeetingListView {

    @BindView(R.id.rv_meeting_list)
    RecyclerView rvMeetingList;

    private List<ActionDto> meetingDtos;
    private RecyclerAdapterHelper<ActionDto> mHelper;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_meeting_list;
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setTitleContent("助教管理");
    }

    @Override
    protected MeetingListPresenter createPresenter() {
        return new MeetingListPresenter(new MeetingListModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initRecycler();
    }

    private void initRecycler(){
        meetingDtos = new ArrayList<>();
        mHelper = new RecyclerAdapterHelper<>(rvMeetingList);

        mHelper.addLinearLayoutManager();
        mHelper.addCommonAdapter(R.layout.item_rv_meeting_list,  meetingDtos, new RecyclerAdapterHelper.CommonConvert<ActionDto>() {
            @Override
            public void convert(CommonViewHolder holder, ActionDto mData, int position) {
                holder.setText(R.id.tv_meeting_name,mData.getTitle());
                holder.setText(R.id.tv_start_time, DateUtils.getInstance().dateToString(new Date(mData.getStartDate()),DateUtils.YMDHM));
            }

        }).create();

        mHelper.getBasicAdapter().setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CommonViewHolder holder, Object data, int position) {
                ActionDto actionDto = meetingDtos.get(position);
                Bundle bundle = new Bundle();
                bundle.putParcelable("actionDto",actionDto);
                startActivity(EntranceSignInActivity.class,bundle);
            }

            @Override
            public boolean onItemLongClick(CommonViewHolder holder, Object data, int position) {
                return false;
            }

        });

        getPresenter().loadActionList();

    }

    @Override
    public void showActionList(List<ActionDto> meetingDtos) {
        this.meetingDtos.clear();
        this.meetingDtos.addAll(meetingDtos);
        mHelper.getmAdapter().notifyDataSetChanged();
    }
}
