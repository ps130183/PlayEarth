package com.km.rmbank.module.login;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.UserLoginDto;
import com.km.rmbank.mvp.model.LoginModel;
import com.km.rmbank.mvp.presenter.LoginPresenter;
import com.km.rmbank.mvp.view.ILoginView;
import com.km.rmbank.module.main.HomeActivity;
import com.km.rmbank.titleBar.SimpleTitleBar;

public class LoginActivity extends BaseActivity<ILoginView,LoginPresenter> implements ILoginView {

    private EditText mobilePhone;
    private EditText etSmsCode;
    private TextView tvSmsCode;

    private boolean isSendCode = false;

    private int waitTime = 60;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setTitleContent("登录");
        simpleTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnHome();
            }
        });
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(new LoginModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        mobilePhone = mViewManager.findView(R.id.et_phone);
        etSmsCode = mViewManager.findView(R.id.et_code);
        tvSmsCode = mViewManager.findView(R.id.tv_send_code);
        init();
    }

    private void init(){
        mobilePhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 11){
                    isSendCode = true;
                    tvSmsCode.setTextColor(ContextCompat.getColor(LoginActivity.this,R.color.textRedColor));
                } else {
                    isSendCode = false;
                    tvSmsCode.setTextColor(ContextCompat.getColor(LoginActivity.this,R.color.textGrayColor));
                }
            }
        });

        setOnClickBackLisenter(new OnClickBackLisenter() {
            @Override
            public boolean onClickBack() {
                returnHome();
                return false;
            }
        });
    }

    /**
     * 在登录页不去登录的 情况下 返回首页
     */
    private void returnHome(){
        Bundle bundle = new Bundle();
        bundle.putInt("position",0);
        startActivity(HomeActivity.class,bundle);
    }


    /**
     * 登录
     * @param view
     */
    public void clickLogin(View view){
        String phone = mobilePhone.getText().toString();
        String smsCode = etSmsCode.getText().toString();
        if (!RegexUtils.isMobileExact(phone)){
            showToast("请填写正确的手机号");
            return;
        } else if (TextUtils.isEmpty(smsCode)){
            showToast("请填写验证码");
            return;
        }
        getPresenter().login(phone,smsCode);
    }

    /**
     * 发送验证码
     * @param view
     */
    public void sendCode(View view){
        String phone = mobilePhone.getText().toString();
        if (!RegexUtils.isMobileExact(phone)){
            showToast("请填写正确的手机号");
            return;
        } else if (isSendCode){
            waitTime = 60;
            getPresenter().getSmsCode(phone);
        }
    }

    @Override
    public void showSmsCode(String smsCode) {
        tvSmsCode.setText(waitTime+"");
        isSendCode = false;
        tvSmsCode.setTextColor(ContextCompat.getColor(LoginActivity.this,R.color.textGrayColor));
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (waitTime-- > 0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvSmsCode.setText(""+waitTime);
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvSmsCode.setText("重新发送");
                        isSendCode = true;
                        tvSmsCode.setTextColor(ContextCompat.getColor(LoginActivity.this,R.color.textBlueColor));
                    }
                });
            }
        }).start();
    }

    @Override
    public void loginSuccess(UserLoginDto userInfoDto) {
        startActivity(HomeActivity.class);
        KeyboardUtils.hideSoftInput(this);
    }

    @Override
    public void createUserInfo(String userPhone) {
        Bundle bundle = new Bundle();
        bundle.putString("phone",userPhone);
        startActivity(CreateUserInfoActivity.class,bundle);
    }

}
