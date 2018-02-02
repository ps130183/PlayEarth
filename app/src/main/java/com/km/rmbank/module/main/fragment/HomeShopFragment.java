package com.km.rmbank.module.main.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeShopFragment extends BaseFragment {


    public static HomeShopFragment newInstance(Bundle bundle) {
        HomeShopFragment fragment = new HomeShopFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_home_shop;
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {

    }

}
