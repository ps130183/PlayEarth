package com.km.rmbank.module.main.fragment.home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.chrisbanes.photoview.PhotoView;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseFragment;
import com.ps.glidelib.GlideUtils;
import com.ps.glidelib.progress.GlideApp;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowBigImageFragment extends BaseFragment {

    public static ShowBigImageFragment newInstance(Bundle bundle) {

        ShowBigImageFragment fragment = new ShowBigImageFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_show_big_image;
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {
        String imagePath = getArguments().getString("imagePath");
        if (!TextUtils.isEmpty(imagePath)){
            PhotoView photoView = mViewManager.findView(R.id.photoView);
            GlideApp.with(getContext())
                    .load(imagePath)
                    .into(photoView);
        }

    }

}
