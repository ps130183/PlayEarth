package com.km.rmbank.module.login;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.UserLoginDto;
import com.km.rmbank.module.webview.AgreementActivity;
import com.km.rmbank.mvp.model.LoginModel;
import com.km.rmbank.mvp.presenter.LoginPresenter;
import com.km.rmbank.mvp.view.ILoginView;
import com.km.rmbank.module.main.HomeActivity;
import com.km.rmbank.retrofit.ApiConstant;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.Constant;
import com.ruffian.library.RTextView;

import java.util.Set;

import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class LoginActivity extends BaseActivity<ILoginView,LoginPresenter> implements ILoginView {

    private EditText mobilePhone;
//    private EditText etSmsCode;
//    private RTextView tvSmsCode;

    private Button btnLogin;

    private boolean isSendCode = false;
    private boolean isCanLogin = false;
    private boolean isCanSend = false;

    private int waitTime = 60;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setTitleContent("欢迎使用「玩转地球」");
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
//        etSmsCode = mViewManager.findView(R.id.et_code);
//        tvSmsCode = mViewManager.findView(R.id.tv_send_code);
        btnLogin = mViewManager.findView(R.id.btn_login);
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
//                if (s.length() >= 11){
//                    isSendCode = true;
//                    isCanSend = true;
//                } else {
//                    isCanSend = false;
//                    isSendCode = false;
//                }
//                tvSmsCode.setEnabled(isSendCode);
//                LogUtils.d("登录按钮是否可点击：" + (isCanSend && isCanLogin));
                btnLogin.setEnabled(s.length() >= 11);
            }
        });

//        etSmsCode.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s.length() == 6){
//                    isCanLogin = true;
//                } else {
//                    isCanLogin = false;
//                }
//                LogUtils.d("登录按钮是否可点击：" + (isCanSend && isCanLogin));
//                btnLogin.setEnabled(isCanSend && isCanLogin);
//            }
//        });

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
//        startActivity(CreateUserInfoActivity.class);
//        String phone = mobilePhone.getText().toString();
//        String smsCode = etSmsCode.getText().toString();
//        if (!RegexUtils.isMobileExact(phone)){
//            showToast("手机号码错误，请重新输入！");
//            return;
//        } else if (TextUtils.isEmpty(smsCode)){
//            showToast("验证码错误，请重新获取！");
//            return;
//        }
//        getPresenter().login(phone,smsCode);
        sendCode();
    }

    /**
     * 发送验证码
     */
    public void sendCode(){
        String phone = mobilePhone.getText().toString();
        if (!RegexUtils.isMobileExact(phone)){
            showToast("手机号码错误，请重新输入！");
            return;
        } else {
            waitTime = 60;
            getPresenter().getSmsCode(phone);
        }
    }

    @Override
    public void showSmsCode(String smsCode) {
        showToast("验证码获取成功！");

        //去填写验证码
        Bundle bundle = new Bundle();
        bundle.putString("phone",mobilePhone.getText().toString());
        startActivity(CreateUserInfoActivity.class,bundle);
//        tvSmsCode.setText(waitTime+"");
//        isSendCode = false;
//        tvSmsCode.setEnabled(isSendCode);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (waitTime-- > 0){
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            tvSmsCode.setText(waitTime+"'");
//                        }
//                    });
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        tvSmsCode.setText("重新获取");
//                        isSendCode = true;
//                        tvSmsCode.setEnabled(isSendCode);
//                    }
//                });
//            }
//        }).start();
    }

    @Override
    public void loginSuccess(UserLoginDto userInfoDto) {
        JPushInterface.setAlias(this, Constant.userLoginInfo.getMobilePhone(), new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                LogUtils.d("极光别名设置成功 = " + s + "    i =" + i);
            }
        });
        showToast("登录成功！");
        startActivity(HomeActivity.class);
        KeyboardUtils.hideSoftInput(this);
    }

    @Override
    public void createUserInfo(String userPhone) {

    }

    @OnClick(R.id.tv_register_agreement)
    public void registerAgreement(View view){
        Bundle bundle = new Bundle();
        bundle.putString("titleName","玩转地球注册协议");
        bundle.putString("agreementUrl", ApiConstant.API_BASE_URL + ApiConstant.API_MODEL +"/member/loginAgreement/view");
        startActivity(AgreementActivity.class,bundle);
    }

}
