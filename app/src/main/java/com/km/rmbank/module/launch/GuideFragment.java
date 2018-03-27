package com.km.rmbank.module.launch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.blankj.utilcode.util.SPUtils;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseFragment;
import com.km.rmbank.module.main.HomeActivity;

import butterknife.OnClick;

/**
 * Created by kamangkeji on 17/4/15.
 */

public class GuideFragment extends BaseFragment {


    @OnClick(R.id.to_home)
    public void toHome(View view){
        startActivity(HomeActivity.class);
        SPUtils.getInstance().put("isFirst",false);
        getActivity().finish();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_guide;
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {

    }
}
