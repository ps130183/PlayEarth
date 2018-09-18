package com.km.rmbank.module.main.personal.profession;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.module.login.LoginActivity;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.DateUtils;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;

import java.util.Date;

public class ProfessionSuccessActivity extends BaseActivity {

    @Override
    public int getContentViewRes() {
        return R.layout.activity_profession_success;
    }

    @Override
    public String getTitleContent() {
        return "职业认证";
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setRightMenuContent("修改");
        simpleTitleBar.setRightMenuClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ProfessionIntroduceActivity.class);
            }
        });
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {

        if (Constant.userInfo == null){
            showToast("尚未登录，请登录后再试！");
            startActivity(LoginActivity.class);
            return;
        }

        mViewManager.setText(R.id.time,"认证时间：" + DateUtils.getInstance().dateToString(new Date(Constant.userInfo.getPositionValidateDate()),DateUtils.YMD));
        mViewManager.setText(R.id.company, Constant.userInfo.getCompany());
        mViewManager.setText(R.id.position,Constant.userInfo.getPosition());

        GlideImageView imageView = mViewManager.findView(R.id.imageView);
        GlideUtils.loadImageOnPregress(imageView,Constant.userInfo.getPositionUrl(),null);
    }
}
