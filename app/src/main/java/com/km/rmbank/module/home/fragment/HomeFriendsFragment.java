package com.km.rmbank.module.home.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFriendsFragment extends BaseFragment {

    public static HomeFriendsFragment newInstance(Bundle bundle) {

        HomeFriendsFragment fragment = new HomeFriendsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_home_friends;
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {

    }

}
