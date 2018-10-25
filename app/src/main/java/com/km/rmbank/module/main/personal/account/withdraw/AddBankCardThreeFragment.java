package com.km.rmbank.module.main.personal.account.withdraw;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseFragment;
import com.km.rmbank.dto.UserLoginDto;
import com.km.rmbank.dto.WithDrawAccountDto;
import com.km.rmbank.event.StepEvent;
import com.km.rmbank.mvp.model.LoginModel;
import com.km.rmbank.mvp.presenter.LoginPresenter;
import com.km.rmbank.mvp.view.ILoginView;
import com.km.rmbank.utils.EventBusUtils;

import butterknife.OnClick;

/**
 * 添加银行卡第三步
 * 获取手机号验证码
 * 使用登录的接口 presenter  只是为了使用其 获取短信验证码的接口
 */
public class AddBankCardThreeFragment extends BaseFragment<ILoginView,LoginPresenter> implements ILoginView {

    private TextView tvSmsCode;
    private int waitTime = 60;
    private boolean isSendCode = false;
    private String phone;

    public AddBankCardThreeFragment() {
        // Required empty public constructor
    }


    public static AddBankCardThreeFragment newInstance(Bundle bundle) {
        AddBankCardThreeFragment fragment = new AddBankCardThreeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(new LoginModel());
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_add_bank_card_three;
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {
        phone = getArguments().getString("phone");
        mViewManager.setText(R.id.userPhone,phone);
        tvSmsCode = mViewManager.findView(R.id.tvSmsCode);
        tvSmsCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getSmsCode(phone);
            }
        });
        getPresenter().getSmsCode(phone);
    }

    /**
     * 获取短信验证码的 等待
     */
    private void daojishi(){
        waitTime = 60;
        tvSmsCode.setText("重新发送(" + waitTime + ")");
        isSendCode = false;
        tvSmsCode.setEnabled(isSendCode);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (waitTime-- > 0){
                    if (getActivity() == null){
                        return;
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvSmsCode.setText("重新发送(" + waitTime + ")");
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvSmsCode.setText("重新发送");
                        isSendCode = true;
                        tvSmsCode.setEnabled(isSendCode);
                    }
                });
            }
        }).start();
    }

    /**
     * 完成
     * @param view
     */
    @OnClick(R.id.nextStep)
    public void nextStep(View view){
        EditText smsCode = mViewManager.findView(R.id.smsCode);
        String code = smsCode.getText().toString().replace(" ","");
        if (TextUtils.isEmpty(code) || code.length() < 6){
            showToast("请填写正确的验证码！");
            return;
        }
        WithDrawAccountDto accountDto = new WithDrawAccountDto();
        accountDto.setSmsCode(code);
        EventBusUtils.post(new StepEvent(1,accountDto));
    }

    @Override
    public void showSmsCode(String smsCode) {
        daojishi();
    }

    @Override
    public void loginSuccess(UserLoginDto userInfoDto) {

    }

    @Override
    public void createUserInfo(String userPhone) {

    }

    @Override
    public void loginWxResult(UserLoginDto userLoginDto) {

    }
}
