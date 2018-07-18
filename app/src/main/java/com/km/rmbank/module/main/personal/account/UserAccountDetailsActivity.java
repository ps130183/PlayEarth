package com.km.rmbank.module.main.personal.account;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.dto.UserAccountDetailDto;
import com.km.rmbank.dto.UserBalanceDto;
import com.km.rmbank.mvp.model.UserAccountModel;
import com.km.rmbank.mvp.presenter.UserAccountPresenter;
import com.km.rmbank.mvp.view.IUserAccountView;
import com.ps.mrcyclerview.BViewHolder;
import com.ps.mrcyclerview.ItemViewConvert;
import com.ps.mrcyclerview.LoadMoreListener;
import com.ps.mrcyclerview.MRecyclerView;
import com.ps.mrcyclerview.click.OnLoadMoreErrorListener;

import java.util.ArrayList;
import java.util.List;

public class UserAccountDetailsActivity extends BaseActivity<IUserAccountView,UserAccountPresenter> implements IUserAccountView {

    private MRecyclerView<UserAccountDetailDto> mRecyclerView;
    @Override
    public int getContentViewRes() {
        return R.layout.activity_user_account_details;
    }

    @Override
    public String getTitleContent() {
        return "账户明细";
    }

    @Override
    protected UserAccountPresenter createPresenter() {
        return new UserAccountPresenter(new UserAccountModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initView();
    }

    private void initView(){
        mRecyclerView = mViewManager.findView(R.id.recyclerView);
        mRecyclerView.addContentLayout(R.layout.item_rv_account_details, new ItemViewConvert<UserAccountDetailDto>() {
            @Override
            public void convert(@NonNull BViewHolder holder, UserAccountDetailDto mData, int position, @NonNull List<Object> payloads) {
                holder.setText(R.id.tv_explain,mData.getExplain());
                holder.setText(R.id.tv_money,mData.getAmount());
                holder.setText(R.id.tv_time,mData.getCreateDate());

                String tradeDirection = mData.getTradeDirection();
                TextView tvMoney = holder.findView(R.id.tv_money);
                //0已提现1提现中2已购买商品3卖出商品4邀请合伙人会员5邀请体验式会员6子级邀请合伙人7子级卖出商品获得金额
                if ("0".equals(tradeDirection)){
                    tradeDirection = "提现成功";
                    tvMoney.setTextColor(ContextCompat.getColor(mInstance,R.color.account_text_color_blue));
                } else if ("1".equals(tradeDirection)){
                    tradeDirection = "提现中";
                    tvMoney.setTextColor(ContextCompat.getColor(mInstance,R.color.account_text_color_red));
                } else if ("2".equals(tradeDirection) || "11".equals(tradeDirection)){
                    tradeDirection = "支出";
                    tvMoney.setTextColor(ContextCompat.getColor(mInstance,R.color.account_text_color_blue));
                } else {
                    tradeDirection = "入账";
                    tvMoney.setTextColor(ContextCompat.getColor(mInstance,R.color.account_text_color_blue));
                }
                holder.setText(R.id.tv_status,tradeDirection);
            }
        }).create();

        mRecyclerView.addLoadMoreListener(new LoadMoreListener() {
            @Override
            public void loadMore(int nextPage) {
                getPresenter().loadAccountDetail(nextPage);
            }
        });
        mRecyclerView.addLoadMoreErrorListener(new OnLoadMoreErrorListener() {
            @Override
            public void loadMoreError(int nextPage) {
                getPresenter().loadAccountDetail(nextPage);
            }
        });
        getPresenter().loadAccountDetail(1);

    }

    @Override
    public void showAccountDetail(List<UserAccountDetailDto> userAccountDetailDtos, int curPage) {
        mRecyclerView.loadDataOfNextPage(userAccountDetailDtos);
    }

    @Override
    public void showBalance(UserBalanceDto userBalanceDto) {

    }
}
