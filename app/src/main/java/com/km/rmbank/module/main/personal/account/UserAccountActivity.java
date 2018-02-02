package com.km.rmbank.module.main.personal.account;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.UserAccountDetailAdapter;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.UserAccountDetailDto;
import com.km.rmbank.dto.UserBalanceDto;
import com.km.rmbank.module.main.personal.account.withdraw.WithDrawListActivity;
import com.km.rmbank.mvp.model.UserAccountModel;
import com.km.rmbank.mvp.presenter.UserAccountPresenter;
import com.km.rmbank.mvp.view.IUserAccountView;
import com.km.rmbank.oldrecycler.BaseAdapter;
import com.km.rmbank.oldrecycler.RVUtils;
import com.km.rmbank.titleBar.SimpleTitleBar;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class UserAccountActivity extends BaseActivity<IUserAccountView,UserAccountPresenter> implements IUserAccountView {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @BindView(R.id.tv_balance)
    TextView tvBalance;



    @Override
    public int getContentViewRes() {
        return R.layout.activity_user_account;
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setTitleContent("我的账户");
    }

    @Override
    protected UserAccountPresenter createPresenter() {
        return new UserAccountPresenter(new UserAccountModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initAccountDetail();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().loadBalance();
        getPresenter().loadAccountDetail(1);
    }

    /**
     * 提现
     *
     * @param view
     */
    @OnClick(R.id.tv_integral)
    public void withDraw(View view) {
        startActivity(WithDrawListActivity.class);
    }

    public void initAccountDetail() {
        RVUtils.setLinearLayoutManage(mRecyclerView, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(mRecyclerView, RVUtils.DIVIDER_COLOR_ACCOUNT_DETAILS, 2);
        final UserAccountDetailAdapter adapter = new UserAccountDetailAdapter(this);
        mRecyclerView.setAdapter(adapter);
        adapter.addLoadMore(mRecyclerView, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                getPresenter().loadAccountDetail(adapter.getNextPage());
            }
        });

    }

    @Override
    public void showAccountDetail(List<UserAccountDetailDto> userAccountDetailDtos, int curPage) {
        UserAccountDetailAdapter adapter = (UserAccountDetailAdapter) mRecyclerView.getAdapter();
        adapter.addData(userAccountDetailDtos,curPage);
    }

    @Override
    public void showBalance(UserBalanceDto userBalanceDto) {
        tvBalance.setText(userBalanceDto.getBalance() + "");
    }
}
