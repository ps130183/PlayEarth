package com.km.rmbank.module.main.payment;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.module.main.HomeActivity;

import butterknife.OnClick;

public class PaySuccessActivity extends BaseActivity {


    @Override
    public int getContentViewRes() {
        return R.layout.activity_pay_success;
    }

    @Override
    public String getTitleContent() {
        return "支付结果";
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {

    }

    @OnClick(R.id.btnBack)
    public void back(View view){
        startActivity(HomeActivity.class);
    }
}
