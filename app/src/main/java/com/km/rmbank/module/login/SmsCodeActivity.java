package com.km.rmbank.module.login;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.dalimao.corelibrary.VerificationCodeInput;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.dto.UserLoginDto;
import com.km.rmbank.module.main.HomeActivity;
import com.km.rmbank.mvp.model.LoginModel;
import com.km.rmbank.mvp.presenter.LoginPresenter;
import com.km.rmbank.mvp.view.ILoginView;
import com.km.rmbank.utils.Constant;

import java.util.Set;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 短信验证码
 */
public class SmsCodeActivity extends BaseActivity<ILoginView,LoginPresenter> implements ILoginView {


    private String phone;
    @BindView(R.id.smsCodeSecond)
    TextView tvSmsCode;
    private int waitTime = 60;
    private boolean isSendCode = false;

    @BindView(R.id.smsCode)
    LinearLayout smsCode;


    @Override
    public int getContentViewRes() {
        return R.layout.activity_sms_code;
    }

    @Override
    public String getTitleContent() {
        return "输入验证码";
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(new LoginModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        phone = getIntent().getStringExtra("phone");
        TextView smsCodeHint = mViewManager.findView(R.id.smsCodeHint);
        smsCodeHint.setText("验证码已发送到" + hidePhone(phone) + "的手机上");
        daojishi();


        VerificationCodeInput input = mViewManager.findView(R.id.vertificationCodeInput);
        input.setOnCompleteListener(new VerificationCodeInput.Listener() {
            @Override
            public void onComplete(String content) {
                LogUtils.d("完成验证码输入 " + content);
                getPresenter().login(phone,content);
            }
        });
    }


    private void daojishi(){
        tvSmsCode.setText(waitTime+"秒内收到验证码");
        isSendCode = false;
        tvSmsCode.setEnabled(isSendCode);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (waitTime-- > 0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvSmsCode.setText(waitTime+"秒内收到验证码");
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
                        tvSmsCode.setText("收不到验证码？");
                        isSendCode = true;
                        tvSmsCode.setEnabled(isSendCode);
                        smsCode.setVisibility(View.VISIBLE);
                    }
                });
            }
        }).start();
    }


    @Override
    public void showSmsCode(String smsCode) {
        daojishi();
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

    /**
     * 更换手机号
     * @param view
     */
    public void changePhone(View view) {
        finish();
    }

    /**
     * 重新获取短信验证码
     * @param view
     */
    public void getSmsCode(View view) {
        waitTime = 60;
        getPresenter().getSmsCode(phone);
        smsCode.setVisibility(View.GONE);
    }

    /**
     * 隐藏 手机号 中间4位
     * @param phone
     * @return
     */
    private String hidePhone(String phone){
        if (TextUtils.isEmpty(phone)){
            return "";
        }
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
    }
}
