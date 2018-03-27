package com.km.rmbank.module.main.personal.hpage;


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
public class DongTaiFragment extends BaseFragment {


    public DongTaiFragment() {
        // Required empty public constructor
    }

    public static DongTaiFragment newInstance(Bundle bundle) {
        DongTaiFragment fragment = new DongTaiFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_dong_tai;
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        mViewManager.setText(R.id.title,title);
    }

}
