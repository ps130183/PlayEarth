package com.km.rmbank.module.main.personal.account.bank;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.RegexUtils;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.dto.UserBalanceDto;
import com.km.rmbank.dto.WithDrawAccountDto;
import com.km.rmbank.mvp.model.WithDrawModel;
import com.km.rmbank.mvp.presenter.WithDrawPresenter;
import com.km.rmbank.mvp.view.IWithDrawView;
import com.km.rmbank.utils.StringUtils;
import com.km.rmbank.utils.dialog.DialogUtils;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;

import java.util.List;

public class MyBankCardDetailsActivity extends BaseActivity<IWithDrawView,WithDrawPresenter> implements IWithDrawView {

    private WithDrawAccountDto accountDto;
    @Override
    public int getContentViewRes() {
        return R.layout.activity_my_bank_card_details;
    }

    @Override
    protected WithDrawPresenter createPresenter() {
        return new WithDrawPresenter(new WithDrawModel());
    }

    @Override
    public String getTitleContent() {
        return "银行卡详情";
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        accountDto = getIntent().getParcelableExtra("accountDto");

        mViewManager.setText(R.id.bankName,accountDto.getBankName());
        mViewManager.setText(R.id.bankTailNumber,"尾号"+accountDto.getWithdrawNumber().substring(accountDto.getWithdrawNumber().length()-4,accountDto.getWithdrawNumber().length()));

        GlideImageView bankIcon = mViewManager.findView(R.id.bankIcon);
        GlideUtils.loadImageOnPregress(bankIcon,accountDto.getBankLogo(),null);

        String userName = accountDto.getName();
        mViewManager.setText(R.id.userName,userName.length() > 1 ? "*" + userName.substring(1,userName.length()) : "*");

        String phone = accountDto.getWithdrawPhone();
        mViewManager.setText(R.id.bankPhone, StringUtils.hidePhone(phone));
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

    }

    @Override
    public void deleteSuccess(WithDrawAccountDto withDrawAccountDto) {
        startActivity(MyBankCardListActivity.class);
    }

    /**
     * 删除银行卡
     * @param view
     */
    public void deleteBankCard(View view) {
        if (accountDto != null){
            DialogUtils.showDefaultAlertDialog("是否要删除此银行卡信息？", new DialogUtils.ClickListener() {
                @Override
                public void clickConfirm() {
                    getPresenter().deleteWithdrawAccount(accountDto);
                }
            });

        } else {
            showToast("找不到银行卡信息！");
        }
    }
}
