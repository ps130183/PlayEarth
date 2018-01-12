package com.km.rmbank.module.home.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseFragment;
import com.km.rmbank.entity.TabEntity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {

    private String[] mTitles = {"俱乐部","约么","人脉圈"};


    public static HomeFragment newInstance(Bundle bundle) {
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {
        Toolbar mToolbar = mViewManager.findView(R.id.toolBar);
        initToolBar(mToolbar);
        CommonTabLayout tabLayout = mViewManager.findView(R.id.tab_layout);
        initTablayout(tabLayout);

    }

    private void initToolBar(Toolbar toolbar){
        toolbar.inflateMenu(R.menu.toolbar_map);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.map){
                    showToast("打开基地地图");
                }
                return false;
            }
        });
    }

    private void initTablayout(CommonTabLayout tabLayout){
        ArrayList<CustomTabEntity> tabEntities = new ArrayList<>();
        for (String title : mTitles){
            tabEntities.add(new TabEntity(title,0,0));
        }
        tabLayout.setTabData(tabEntities);
    }

}
