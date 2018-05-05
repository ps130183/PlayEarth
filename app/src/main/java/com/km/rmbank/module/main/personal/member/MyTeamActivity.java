package com.km.rmbank.module.main.personal.member;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.MyTeamParentAdapter;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.MyTeamDto;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.mvp.model.MyTeamModel;
import com.km.rmbank.mvp.presenter.MyTeamPresenter;
import com.km.rmbank.mvp.view.IMyTeamView;
import com.km.rmbank.oldrecycler.RVUtils;
import com.km.rmbank.titleBar.SimpleTitleBar;

import java.util.List;

import butterknife.BindView;

public class MyTeamActivity extends BaseActivity<IMyTeamView,MyTeamPresenter> implements IMyTeamView,MyTeamParentAdapter.onClickUserListener {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_team_number)
    TextView tvTeamNumber;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_my_team;
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setTitleContent("我的人脉");
    }

    @Override
    protected MyTeamPresenter createPresenter() {
        return new MyTeamPresenter(new MyTeamModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initRecycler();
    }

    public void initRecycler() {
        RVUtils.setLinearLayoutManage(mRecyclerView, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(mRecyclerView);
        MyTeamParentAdapter adapter = new MyTeamParentAdapter(this);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnClickUserListener(this);
        getPresenter().getMyTeamData();
    }

    @Override
    public void showMyTeam(List<MyTeamDto> teamEntities) {
        MyTeamParentAdapter adapter = (MyTeamParentAdapter) mRecyclerView.getAdapter();
        adapter.addData(teamEntities);
        tvTeamNumber.setText(adapter.getMemberCount()+"");
    }

    @Override
    public void showUserCard(UserInfoDto cardDto) {
        Bundle bundle = new Bundle();
//        bundle.putParcelable("memberDto",itemData);
        bundle.putParcelable("userCard",cardDto);
//        startActivity(UserCardActivity.class,bundle);
    }


    @Override
    public void clickUser(MyTeamDto.MemberDtoListBean itemData, int position) {
        getPresenter().getUserCardById(itemData.getId());
//        showToast(itemData.toString());

    }


}
