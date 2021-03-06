package com.km.rmbank.module.realname;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.StringUtils;

public class CertifyIdCardSuccessActivity extends BaseActivity {

    @Override
    public int getContentViewRes() {
        return R.layout.activity_certify_id_card_success;
    }

    @Override
    public String getTitleContent() {
        return "实名认证";
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        if (Constant.userInfo != null){
            mViewManager.setText(R.id.userName,"**" + Constant.userInfo.getName().substring(Constant.userInfo.getName().length() - 1,Constant.userInfo.getName().length()));
            mViewManager.setText(R.id.idCardNumber, StringUtils.hideIdCard(Constant.userInfo.getCardId()));
        } else {
            showToast("获取不到用户信息!");
            finish();
        }
    }
}
