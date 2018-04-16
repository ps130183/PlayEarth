package com.km.rmbank.module.login;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.module.main.HomeActivity;
import com.km.rmbank.mvp.model.UserInfoModel;
import com.km.rmbank.mvp.presenter.UserInfoPresenter;
import com.km.rmbank.mvp.view.IUserInfoView;
import com.km.rmbank.utils.Constant;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 实名认证
 */
public class IdentityVerificationActivity extends BaseActivity<IUserInfoView, UserInfoPresenter> implements IUserInfoView {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_position)
    EditText etPosition;

    @BindView(R.id.btn_submit)
    Button btnSubmit;

    private String phone;

    private boolean hasName = false;
    private boolean hasCode = false;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_identity_verification;
    }

    @Override
    public String getTitleContent() {
        return "实名认证";
    }

    @Override
    protected UserInfoPresenter createPresenter() {
        return new UserInfoPresenter(new UserInfoModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        phone = getIntent().getStringExtra("phone");
        init();
    }


    private void init() {
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hasName = TextUtils.isEmpty(s);
                btnSubmit.setEnabled(hasName && hasCode);
            }
        });

        etPosition.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hasCode = !TextUtils.isEmpty(s) && s.length() == 18;
                btnSubmit.setEnabled(hasName && hasCode);
            }
        });
    }

    @OnClick(R.id.btn_submit)
    public void submit(View view) {
        String name = etName.getText().toString();
        String position = etPosition.getText().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(position)) {
            showToast("姓名跟身份证信息不匹配！");
            return;
        }
        getPresenter().createUserCard(name, position, phone);

    }

    @Override
    public void uploadProtraitSuccess(String imageUri) {

    }

    @Override
    public void updateUserInfoResult(String result) {

    }

    @Override
    public void createUserCardSuccess(String token) {
        showToast("认证成功！");
        Constant.userLoginInfo.setToken(token);
        startActivity(HomeActivity.class);
    }
}
