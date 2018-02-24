package com.km.rmbank.module.main.payment;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
        String hint1 = getIntent().getStringExtra("hint1");
        String hint2 = getIntent().getStringExtra("hint2");
        if (!TextUtils.isEmpty(hint1) && !TextUtils.isEmpty(hint2)){
            mViewManager.setText(R.id.hint1,hint1);
            mViewManager.setText(R.id.hint2,hint2);
        }
    }

    @OnClick(R.id.btnBack)
    public void back(View view){
        startActivity(HomeActivity.class);
    }
}
