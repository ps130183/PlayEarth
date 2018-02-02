package com.km.rmbank.module.main.personal.leader;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.SignInDto;
import com.km.rmbank.module.main.personal.account.UserAccountActivity;
import com.km.rmbank.mvp.model.SignInListModel;
import com.km.rmbank.mvp.presenter.SignInListPresenter;
import com.km.rmbank.mvp.view.ISignInListView;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.SwipeRefreshUtils;
import com.ps.commonadapter.adapter.CommonAdapter;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SignInListActivity extends BaseActivity<ISignInListView,SignInListPresenter> implements ISignInListView {

    @BindView(R.id.rv_sign_in)
    RecyclerView rvSignIn;
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;

    private CommonAdapter<SignInDto> adapter;
    private RecyclerView.Adapter mAdapter;

    private String actionId;


    @Override
    public int getContentViewRes() {
        return R.layout.activity_sign_in_list;
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setTitleContent("签到统计");
    }

    @Override
    protected SignInListPresenter createPresenter() {
        return new SignInListPresenter(new SignInListModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        actionId = getIntent().getStringExtra("actionId");
        initRv();
    }


    private List<SignInDto> mDatas;

    private RecyclerAdapterHelper<SignInDto> mHelper;

    private void initRv() {
        mDatas = new ArrayList<>();

        adapter = new CommonAdapter<SignInDto>(this, mDatas, R.layout.item_rv_sign_in_list) {
            @Override
            public void convert(CommonViewHolder holder, SignInDto mData, int position) {
                holder.setText(R.id.tv_user_name, mData.getRegistrationName());
                holder.setText(R.id.tv_status, "1".equals(mData.getStatus()) ? "未缴费" : mData.getSumTotal());
                holder.setText(R.id.tv_back_money, mData.getBackMoney() + "");
            }
        };

        mHelper = new RecyclerAdapterHelper<>(rvSignIn);
        mHelper.addLinearLayoutManager()
                .addAdapter(adapter)
                .addEmptyWrapper(null)
                .addRefreshView(mXRefreshView)
                .addRefreshListener(new RecyclerAdapterHelper.OnRefreshListener() {
                    @Override
                    public void refresh() {
                        getPresenter().loadSignInLists(actionId);
                    }
                })
                .create();

        mAdapter = mHelper.getmAdapter();
        adapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CommonViewHolder holder, Object data, int position) {

            }

            @Override
            public boolean onItemLongClick(CommonViewHolder holder, Object data, int position) {
                return false;
            }

        });

        getPresenter().loadSignInLists(actionId);

    }

    private int i = 3;

    @OnClick(R.id.tv_submit)
    public void submit(View view) {
        startActivity(UserAccountActivity.class);
    }

    @Override
    public void showSignInLists(List<SignInDto> signInDtos) {
        mDatas.clear();
        mDatas.addAll(signInDtos);
        mAdapter.notifyDataSetChanged();

        countTotalMoney();
    }

    private void countTotalMoney() {
        int money = 0;
        for (int i = 0; i < mDatas.size(); i++) {
            money += mDatas.get(i).getBackMoney();
        }
        tvTotalMoney.setText("收入总计：¥ " + money);

    }

}
