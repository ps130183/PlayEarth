package com.km.rmbank.module.main.shop;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.module.main.HomeActivity;

public class ShopActivity extends BaseActivity {

    @Override
    public int getContentViewRes() {
        return R.layout.activity_shop;
    }

    @Override
    public BaseTitleBar getBaseTitleBar() {
        return null;
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        setOnClickBackLisenter(new OnClickBackLisenter() {
            @Override
            public boolean onClickBack() {
                returnHome();
                return true;
            }
        });

        Bundle bundle = getIntent().getExtras();
//        bundle.putBoolean("isFromHome",true);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.content, ShopFragment.newInstance(bundle));
        ft.commit();
    }

    private void returnHome(){
        Bundle bundle = getIntent().getExtras();
        startActivity(HomeActivity.class,bundle);
    }
}
