package com.km.rmbank.module.main.personal.integral;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.UserIntegralAdapter;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.IntegralDetailsDto;
import com.km.rmbank.dto.IntegralDto;
import com.km.rmbank.module.webview.AgreementActivity;
import com.km.rmbank.mvp.model.IntegralModel;
import com.km.rmbank.mvp.presenter.IntegralPresenter;
import com.km.rmbank.mvp.view.IIntegralView;
import com.km.rmbank.oldrecycler.BaseAdapter;
import com.km.rmbank.oldrecycler.RVUtils;
import com.km.rmbank.titleBar.SimpleTitleBar;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyIntegralActivity extends BaseActivity<IIntegralView,IntegralPresenter> implements IIntegralView {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @BindView(R.id.tv_balance)
    TextView tvBalance;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_my_integral;
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setTitleContent("我的积分");
    }

    @Override
    protected IntegralPresenter createPresenter() {
        return new IntegralPresenter(new IntegralModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initRecyclerview();
        getPresenter().getUserIntegralInfo();
    }

    private void initRecyclerview(){
        RVUtils.setLinearLayoutManage(mRecyclerView, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(mRecyclerView, RVUtils.DIVIDER_COLOR_ACCOUNT_DETAILS,2);
//        List<ICell> iCells = new ArrayList<>();
//        for (int i = 0; i < 10; i++){
//            iCells.add(new MyIntegralCell(null));
//        }
//        TemplateAdapter adapter = new TemplateAdapter();
        final UserIntegralAdapter adapter = new UserIntegralAdapter(this);
        mRecyclerView.setAdapter(adapter);
        adapter.addLoadMore(mRecyclerView, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                getPresenter().getIntegralDetails(adapter.getNextPage());
            }
        });
        getPresenter().getIntegralDetails(adapter.getNextPage());
    }

    /**
     * 积分规则
     * @param view
     */
    @OnClick(R.id.tv_integral)
    public void withDraw(View view){
        Bundle bundle = new Bundle();
        bundle.putString("titleName","积分规则");
        bundle.putString("agreementUrl","/member/total/view");
        startActivity(AgreementActivity.class,bundle);
    }

    @Override
    public void showUserIntegralInfo(IntegralDto integralDto) {
        tvBalance.setText(integralDto.getTotal()+"");
    }

    @Override
    public void showIntegralDetails(List<IntegralDetailsDto> integralDetailsDtos, int pageNo) {
        UserIntegralAdapter adapter = (UserIntegralAdapter) mRecyclerView.getAdapter();
        adapter.addData(integralDetailsDtos,pageNo);
    }


}
