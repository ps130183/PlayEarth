package com.km.rmbank.module.main.personal.setting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.oldrecycler.AppUtils;
import com.km.rmbank.titleBar.SimpleTitleBar;

import butterknife.BindView;

public class AboutMeActivity extends BaseActivity {

    @BindView(R.id.rl_logo)
    RelativeLayout rlLogo;

    @BindView(R.id.tv_cur_app_version)
    TextView tvCurAPPVersion;

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setTitleContent("关于我们");
    }

    @Override
    public int getContentViewRes() {
        return R.layout.activity_about_me;
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        rlLogo.getLayoutParams().height = AppUtils.getCurWindowWidth(this) - 200;
        tvCurAPPVersion.setText("当前版本  " + AppUtils.getAppVersionName(this));
    }
}
