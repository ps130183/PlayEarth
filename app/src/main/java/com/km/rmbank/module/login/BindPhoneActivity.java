package com.km.rmbank.module.login;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.customview.ObtainCodeButton;
import com.km.rmbank.dto.UserLoginDto;
import com.km.rmbank.module.main.HomeActivity;
import com.km.rmbank.mvp.model.LoginModel;
import com.km.rmbank.mvp.presenter.LoginPresenter;
import com.km.rmbank.mvp.view.ILoginView;
import com.km.rmbank.utils.Constant;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class BindPhoneActivity extends BaseActivity<ILoginView,LoginPresenter> implements ILoginView {

    UserLoginDto userLoginDto;
    @Override
    public int getContentViewRes() {
        return R.layout.activity_bind_phone;
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(new LoginModel());
    }

    @Override
    public String getTitleContent() {
        return "绑定手机号";
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        userLoginDto = getIntent().getParcelableExtra("userLoginInfo");
        mViewManager.setText(R.id.wx_nickname,userLoginDto.getNickname());

        GlideImageView portrait = mViewManager.findView(R.id.wx_protrait);
        GlideUtils.loadImageOnPregress(portrait,userLoginDto.getHeadimgurl(),null);

    }

    /**
     * 获取验证码
     * @param view
     */
    public void obtainPhoneCode(View view) {
        EditText etPhone = mViewManager.findView(R.id.user_phone);
        String phone = etPhone.getText().toString();
        if (TextUtils.isEmpty(phone) || phone.length() < 11){
            showToast("请填写要绑定的手机号");
            return;
        }
        getPresenter().getSmsCode(phone);
    }

    /**
     * 绑定手机号
     * @param view
     */
    public void bindPhone(View view) {
        EditText etPhone = mViewManager.findView(R.id.user_phone);
        EditText etVertiCode = mViewManager.findView(R.id.et_verti_code);

        String phone = etPhone.getText().toString();
        String smsCode = etVertiCode.getText().toString();
        if (TextUtils.isEmpty(phone) || phone.length() < 11){
            showToast("请填写要绑定的手机号");
            return;
        }

        if (TextUtils.isEmpty(smsCode)){
            showToast("请填写验证码");
            return;
        }

        getPresenter().bindPhoneForWx(phone,smsCode,userLoginDto.getUnionid());
    }

    @Override
    public void showSmsCode(String smsCode) {
        ObtainCodeButton button = mViewManager.findView(R.id.obtain_phone_code);
        button.startObtainPhoneCode();
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

    @Override
    public void loginWxResult(UserLoginDto userLoginDto) {

    }
}
