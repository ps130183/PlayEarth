package com.km.rmbank.module.main.payment;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;

public class PayContractsActivity extends BaseActivity {


    @Override
    public int getContentViewRes() {
        return R.layout.activity_pay_contracts;
    }

    @Override
    public String getTitleContent() {
        return "支付";
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {

        mViewManager.setText(R.id.hint,"亲爱的玩家，您可以扩充自己的人脉库啦！只要给新用户发送短信，就可以绑定人脉关系，每个短信收费仅需" + Html.fromHtml("<font color=\"#3285ff\">" + 0.1 + "</font>元。"));
    }
}
