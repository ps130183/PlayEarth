package com.km.rmbank.module.main.personal.profession;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.event.RefreshPersonalInfoEvent;
import com.km.rmbank.module.main.HomeActivity;
import com.km.rmbank.utils.EventBusUtils;

public class ProfessionFinishedActivity extends BaseActivity {

    @Override
    public int getContentViewRes() {
        return R.layout.activity_profession_finished;
    }

    @Override
    public String getTitleContent() {
        return "职业认证";
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {

    }

    /**
     * 确定
     * @param view
     */
    public void confirm(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("position",4);
        startActivity(HomeActivity.class,bundle);
        EventBusUtils.post(new RefreshPersonalInfoEvent());
    }
}
