package com.km.rmbank.module.login;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.KeyboardUtils;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.module.main.HomeActivity;
import com.km.rmbank.mvp.model.UserInfoModel;
import com.km.rmbank.mvp.presenter.LoginPresenter;
import com.km.rmbank.mvp.presenter.UserInfoPresenter;
import com.km.rmbank.mvp.view.ILoginView;
import com.km.rmbank.mvp.view.IUserInfoView;
import com.km.rmbank.utils.Constant;

import butterknife.BindView;
import butterknife.OnClick;

public class CreateUserInfoActivity extends BaseActivity<IUserInfoView,UserInfoPresenter> implements IUserInfoView   {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_position)
    EditText etPosition;

    private String phone;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_create_user_info;
    }

    @Override
    public String getTitleContent() {
        return "让别人认识你";
    }

    @Override
    protected UserInfoPresenter createPresenter() {
        return new UserInfoPresenter(new UserInfoModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        phone = getIntent().getStringExtra("phone");
    }

    @OnClick(R.id.btn_submit)
    public void submit(View view){
        String name = etName.getText().toString();
        String position = etPosition.getText().toString();

        if (TextUtils.isEmpty(name)){
            showToast("请填写您的真实姓名");
            return;
        } else if (TextUtils.isEmpty(position)){
            showToast("请填写您的身份");
            return;
        }
        getPresenter().createUserCard(name,position,phone);

    }

    @Override
    public void saveUserInfoSuccess() {

    }

    @Override
    public void createUserCardSuccess(String token) {
        Constant.userLoginInfo.setToken(token);
        startActivity(HomeActivity.class);
    }
}
