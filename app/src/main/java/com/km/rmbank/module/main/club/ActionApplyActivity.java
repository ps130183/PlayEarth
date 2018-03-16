package com.km.rmbank.module.main.club;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.mvp.model.ActionRecentInfoModel;
import com.km.rmbank.mvp.presenter.ActionRecentInfoPresenter;
import com.km.rmbank.mvp.view.IActionRecentInfoView;
import com.km.rmbank.titleBar.SimpleTitleBar;

import butterknife.BindView;
import butterknife.OnClick;

public class ActionApplyActivity extends BaseActivity<IActionRecentInfoView,ActionRecentInfoPresenter> implements  IActionRecentInfoView {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    private String actionId;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_action_apply;
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setTitleContent("活动报名");
    }

    @Override
    protected ActionRecentInfoPresenter createPresenter() {
        return new ActionRecentInfoPresenter(new ActionRecentInfoModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        actionId = getIntent().getStringExtra("actionId");
    }

    @Override
    public void showActionRecentInfo(ActionDto actionDto) {

    }

    @Override
    public void applyActionSuccess(String actionId) {
        showToast("报名成功");
        finish();
    }

    @Override
    public void followClubSuccess(boolean isFollow) {

    }

    @Override
    public void addActiveValueSuccess(String result) {

    }

    @Override
    public void showClubInfo(ClubDto clubDto) {

    }

    @OnClick(R.id.btn_apply)
    public void clickApply(View view){
        String name = etName.getText().toString();
        String phone = etPhone.getText().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone)){
            showToast("请补全报名信息");
            return;
        }
        getPresenter().applyAction(actionId,name,phone);
    }


}
