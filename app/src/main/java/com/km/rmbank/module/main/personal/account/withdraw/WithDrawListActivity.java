package com.km.rmbank.module.main.personal.account.withdraw;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.km.rmbank.R;
import com.km.rmbank.adapter.WithDrawAccountAdapter;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.UserBalanceDto;
import com.km.rmbank.dto.WithDrawAccountDto;
import com.km.rmbank.mvp.model.WithDrawModel;
import com.km.rmbank.mvp.presenter.WithDrawPresenter;
import com.km.rmbank.mvp.view.IWithDrawView;
import com.km.rmbank.oldrecycler.BaseAdapter;
import com.km.rmbank.oldrecycler.RVUtils;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.DialogUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class WithDrawListActivity extends BaseActivity<IWithDrawView,WithDrawPresenter> implements IWithDrawView,WithDrawAccountAdapter.OnEditWithdrawListener, WithDrawAccountAdapter.OnDeleteWithdrawListener{

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().getWithDrawList();
    }

    @Override
    public int getContentViewRes() {
        return R.layout.activity_with_draw_list;
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setTitleContent("提现管理");
    }

    @Override
    protected WithDrawPresenter createPresenter() {
        return new WithDrawPresenter(new WithDrawModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initRecyclerView();
    }

    private void initRecyclerView() {
        RVUtils.setLinearLayoutManage(mRecyclerView, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(mRecyclerView);
        WithDrawAccountAdapter adapter = new WithDrawAccountAdapter(this);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnEditWithdrawListener(this);
        adapter.setOnDeleteWithdrawListener(this);
        adapter.setItemClickListener(new BaseAdapter.ItemClickListener<WithDrawAccountDto>() {
            @Override
            public void onItemClick(WithDrawAccountDto itemData, int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("withDrawAccountDto",itemData);
                startActivity(WithDrawActivity.class,bundle);
            }
        });
    }

    @OnClick(R.id.btn_add_account)
    public void createAccount(View view) {
        startActivity(CreateWithDrawAccountActivity.class);
    }

    @Override
    public void showBalance(UserBalanceDto userBalanceDto) {

    }

    @Override
    public void withdrawSuccess() {

    }

    @Override
    public void creatOrUpdateSuccess() {

    }

    @Override
    public void showWithDrawList(List<WithDrawAccountDto> withDrawAccountDtos) {
        WithDrawAccountAdapter adapter = (WithDrawAccountAdapter) mRecyclerView.getAdapter();
        adapter.addData(withDrawAccountDtos);
    }

    @Override
    public void deleteSuccess(WithDrawAccountDto withDrawAccountDto) {
        WithDrawAccountAdapter adapter = (WithDrawAccountAdapter) mRecyclerView.getAdapter();
        adapter.removeData(withDrawAccountDto);
    }

    @Override
    public void editWithdraw(WithDrawAccountDto withDrawAccountDto) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("withDrawAccountDto",withDrawAccountDto);
        startActivity(CreateWithDrawAccountActivity.class,bundle);
    }

    @Override
    public void deleteWithdraw(final WithDrawAccountDto withDrawAccountDto) {
        DialogUtils.showDefaultAlertDialog("是否要删除该提现账号？", new DialogUtils.ClickListener() {
            @Override
            public void clickConfirm() {
                getPresenter().deleteWithdrawAccount(withDrawAccountDto);
            }
        });
    }

}
