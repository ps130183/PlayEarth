package com.km.rmbank.module.main.personal.member;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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
        String imageRes = getArguments().getString("imageRes");
        GlideUtils.loadImageOnPregress(imageView,imageRes,null);

        GlideImageView openId = mViewManager.findView(R.id.openMember);
        String roleId = getArguments().getString("openRoleId");
        if (!TextUtils.isEmpty(roleId)){
            openId.setVisibility(View.VISIBLE);
            if ("1".equals(roleId)){
                GlideUtils.loadImageByRes(openId,R.drawable.open_member_club);
            } else if ("2".equals(roleId)){
                GlideUtils.loadImageByRes(openId,R.drawable.open_member_player);
            }
        }
    }

}
