package com.km.rmbank.module.main.personal.member;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseFragment;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemberTypeFragment extends BaseFragment {


    public MemberTypeFragment() {
        // Required empty public constructor
    }

    public static MemberTypeFragment newInstance(Bundle bundle) {
        MemberTypeFragment fragment = new MemberTypeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_member_type;
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {
        GlideImageView imageView = mViewManager.findView(R.id.imageView);
        int imageRes = getArguments().getInt("imageRes");
        GlideUtils.loadImageByRes(imageView,imageRes);
    }

}
