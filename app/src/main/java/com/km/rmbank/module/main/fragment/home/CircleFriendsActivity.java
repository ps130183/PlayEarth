package com.km.rmbank.module.main.fragment.home;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;

import butterknife.BindView;

public class CircleFriendsActivity extends BaseActivity {


    @Override
    public int getContentViewRes() {
        return R.layout.activity_circle_friends;
    }

    @Override
    public String getTitleContent() {
        return "人脉圈";
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.flContent,CircleFriendsFragment.newInstance(null))
                .commit();
    }
}
