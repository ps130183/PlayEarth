package com.km.rmbank.module.main.personal.setting;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.dialog.DialogUtils;

public class ServiceActivity extends BaseActivity {

    @Override
    public int getContentViewRes() {
        return R.layout.activity_service;
    }

    @Override
    public String getTitleContent() {
        return "联系客服";
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {

    }

    private void callService(String phone){
        DialogUtils.showDefaultAlertDialog("是否拨打客服电话：" + Constant.SERVICE_PHONE + "?", new DialogUtils.ClickListener() {
            @Override
            public void clickConfirm() {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + Constant.SERVICE_PHONE);
                intent.setData(data);
                startActivity(intent);
            }
        });
    }
}
