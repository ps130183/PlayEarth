package com.km.rmbank.module.main.fragment.home;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;

import butterknife.BindView;

public class MoreActionActivity extends BaseActivity {

    private String type;
    private int newType= 0;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_more_action;
    }

    @Override
    public String getTitleContent() {
        type = getIntent().getStringExtra("type");
        String title = "";
        switch (type){
            case "0100":
                title = "全部活动";
                newType = 0;
                break;
            case "0101":
                title = "下午茶";
                newType = 3;
                break;
            case "0102":
                title = "结缘晚宴";
                newType = 4;
                break;
            case "0103":
                title = "户外活动";
                newType = 2;
                break;
            case "0104":
                title = "路演大会";
                newType = 1;
                break;
        }
        return title;
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        Bundle bundle = new Bundle();
        bundle.putInt("type",newType);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.actionList,AppointFragment.newInstance(bundle))
                .commit();
    }
}
