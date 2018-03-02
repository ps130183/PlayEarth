package com.km.rmbank.module.main.club;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.ActionRecentJoinMemberAdapter;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.dto.ActionMemberDto;
import com.km.rmbank.mvp.model.ActionJoinMemberModel;
import com.km.rmbank.mvp.presenter.ActionJoinMemberPresenter;
import com.km.rmbank.mvp.view.IActionJoinMemberView;
import com.km.rmbank.oldrecycler.BaseAdapter;
import com.km.rmbank.oldrecycler.RVUtils;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.SwipeRefreshUtils;

import java.util.List;

import butterknife.BindView;

public class ActionJoinMemberActivity extends BaseActivity<IActionJoinMemberView, ActionJoinMemberPresenter> implements IActionJoinMemberView {


    @BindView(R.id.rv_action_join_member)
    RecyclerView rvActionJoinMember;
    private SwipeRefreshLayout mSwipeRefresh;

    private ActionDto mActionDto;
    private boolean isMyClub;

    private SimpleTitleBar simpleTitleBar;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_action_join_member;
    }

    @Override
    public String getTitleContent() {
        return "已报名";
    }

    @Override
    protected ActionJoinMemberPresenter createPresenter() {
        return new ActionJoinMemberPresenter(new ActionJoinMemberModel());
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        simpleTitleBar = (SimpleTitleBar) titleBar;
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {

        mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swiper_refresh);

        mActionDto = getIntent().getParcelableExtra("actionDto");
        isMyClub = getIntent().getBooleanExtra("isMyClub", isMyClub);
        initRecyclerview();
    }

    private void initRecyclerview() {
        SwipeRefreshUtils.initSwipeRefresh(mSwipeRefresh, new SwipeRefreshUtils.OnRereshListener() {
            @Override
            public void onRefresh() {
                getPresenter().getActionMemberList(mActionDto.getId(), 1);
                getPresenter().getActionMemberNum(mActionDto.getId());

            }
        });

        RVUtils.setLinearLayoutManage(rvActionJoinMember, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(rvActionJoinMember);
        final ActionRecentJoinMemberAdapter adapter = new ActionRecentJoinMemberAdapter(this, isMyClub);
        rvActionJoinMember.setAdapter(adapter);

        adapter.addLoadMore(rvActionJoinMember, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                getPresenter().getActionMemberList(mActionDto.getId(), adapter.getNextPage());
            }
        });
        getPresenter().getActionMemberList(mActionDto.getId(), 1);
        getPresenter().getActionMemberNum(mActionDto.getId());
    }

    @Override
    public void showActionMemberList(List<ActionMemberDto> actionMemberDtos, int pageNo) {
        ActionRecentJoinMemberAdapter adapter = (ActionRecentJoinMemberAdapter) rvActionJoinMember.getAdapter();
        adapter.addData(actionMemberDtos, pageNo);
    }

    @Override
    public void showActionMemberNum(String number) {
        simpleTitleBar.setTitleContent("已报名（" + number + ")人");
    }


}
