package com.km.rmbank.module.main.personal.account.bank;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.dto.UserBalanceDto;
import com.km.rmbank.dto.WithDrawAccountDto;
import com.km.rmbank.module.main.personal.account.withdraw.AddBankCardActivity;
import com.km.rmbank.mvp.model.WithDrawModel;
import com.km.rmbank.mvp.presenter.WithDrawPresenter;
import com.km.rmbank.mvp.view.IWithDrawView;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.ps.mrcyclerview.BViewHolder;
import com.ps.mrcyclerview.ItemViewConvert;
import com.ps.mrcyclerview.MRecyclerView;
import com.ps.mrcyclerview.click.OnClickItemListener;

import java.util.ArrayList;
import java.util.List;

public class MyBankCardListActivity extends BaseActivity<IWithDrawView,WithDrawPresenter> implements IWithDrawView {

    @Override
    public int getContentViewRes() {
        return R.layout.activity_my_bank_card_list;
    }

    @Override
    public String getTitleContent() {
        return "我的银行卡";
    }

    @Override
    protected WithDrawPresenter createPresenter() {
        return new WithDrawPresenter(new WithDrawModel());
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().getWithDrawList();
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initRecycler();
    }

    private void initRecycler(){
        MRecyclerView<WithDrawAccountDto> bankCardRecycler = mViewManager.findView(R.id.bankCardRecycler);
        bankCardRecycler.addContentLayout(R.layout.item_bank_card, new ItemViewConvert<WithDrawAccountDto>() {
            @Override
            public void convert(@NonNull BViewHolder holder, WithDrawAccountDto mData, int position, @NonNull List<Object> payloads) {
                holder.setText(R.id.bankName,mData.getBankName());
                holder.setText(R.id.bankCardNo,"**** **** **** " + mData.getWithdrawNumber().substring(mData.getWithdrawNumber().length()-4,mData.getWithdrawNumber().length()));
                GlideImageView bankIcon = holder.findView(R.id.bankIcon);
                GlideUtils.loadImageOnPregress(bankIcon,mData.getBankLogo(),null);
            }

        }).create();

        bankCardRecycler.addClickItemListener(new OnClickItemListener() {
            @Override
            public void clickItem(Object mData, int position) {
                WithDrawAccountDto accountDto = (WithDrawAccountDto) mData;
                Bundle bundle = new Bundle();
                bundle.putParcelable("accountDto",accountDto);
                startActivity(MyBankCardDetailsActivity.class,bundle);
            }
        });
        getPresenter().getWithDrawList();
    }

    @Override
    public void showBalance(UserBalanceDto userBalanceDto) {

    }

    @Override
    public void withdrawSuccess(String money) {

    }

    @Override
    public void creatOrUpdateSuccess() {

    }

    @Override
    public void showWithDrawList(List<WithDrawAccountDto> withDrawAccountDtos) {
        for (WithDrawAccountDto accountDto : withDrawAccountDtos){
            accountDto.setLayoutRes(R.layout.item_bank_card);
        }
        MRecyclerView<WithDrawAccountDto> bankCardRecycler = mViewManager.findView(R.id.bankCardRecycler);
        bankCardRecycler.clear();
        bankCardRecycler.loadDataOfNextPage(withDrawAccountDtos);
    }

    @Override
    public void deleteSuccess(WithDrawAccountDto withDrawAccountDto) {

    }

    /**
     * 添加新的银行卡
     * @param view
     */
    public void addNewBankCard(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("fromPage",0);
        startActivity(AddBankCardActivity.class,bundle);
    }
}
