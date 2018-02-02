package com.km.rmbank.module.main.personal.account.withdraw;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.UserBalanceDto;
import com.km.rmbank.dto.WithDrawAccountDto;
import com.km.rmbank.mvp.model.WithDrawModel;
import com.km.rmbank.mvp.presenter.WithDrawPresenter;
import com.km.rmbank.mvp.view.IWithDrawView;
import com.km.rmbank.titleBar.SimpleTitleBar;

import java.util.List;

import butterknife.BindView;

public class CreateWithDrawAccountActivity extends BaseActivity<IWithDrawView,WithDrawPresenter> implements IWithDrawView {

    private WithDrawAccountDto withDrawAccountDto;

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_type_name)
    EditText etTypeName;
    @BindView(R.id.et_account)
    EditText etAccount;

    private boolean isCreate;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_create_with_draw_account;
    }

    @Override
    protected WithDrawPresenter createPresenter() {
        return new WithDrawPresenter(new WithDrawModel());
    }

    protected String getTitleName() {
        withDrawAccountDto = getIntent().getParcelableExtra("withDrawAccountDto");
        if (withDrawAccountDto == null){
            withDrawAccountDto = new WithDrawAccountDto();
            isCreate = true;
            return "新建账户";
        } else {
            isCreate = false;
            return "编辑信息";
        }
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setTitleContent(getTitleName());
        simpleTitleBar.setRightMenuContent("保存");
        simpleTitleBar.setRightMenuClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withDrawAccountDto.setName(etName.getText().toString());
                withDrawAccountDto.setWithdrawPhone(etPhone.getText().toString());
                withDrawAccountDto.setTypeName(etTypeName.getText().toString());
                withDrawAccountDto.setWithdrawNumber(etAccount.getText().toString());
                if (withDrawAccountDto.isEmpty()){
                    showToast("请将信息补充完整");
                    return;
                }
                if (isCreate){
                    getPresenter().createWithDrawAccount(withDrawAccountDto);
                } else {
                    getPresenter().updateWithDrawAccount(withDrawAccountDto);
                }
            }
        });
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        if (!isCreate){//编辑
            etName.setText(withDrawAccountDto.getName());
            etPhone.setText(withDrawAccountDto.getWithdrawPhone());
            etTypeName.setText(withDrawAccountDto.getTypeName());
            etAccount.setText(withDrawAccountDto.getWithdrawNumber());
        }
    }

    @Override
    public void showBalance(UserBalanceDto userBalanceDto) {

    }

    @Override
    public void withdrawSuccess() {

    }

    @Override
    public void creatOrUpdateSuccess() {
        showToast("保存成功");
        finish();
    }

    @Override
    public void showWithDrawList(List<WithDrawAccountDto> withDrawAccountDtos) {

    }

    @Override
    public void deleteSuccess(WithDrawAccountDto withDrawAccountDto) {

    }

}
