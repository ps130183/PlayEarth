package com.km.rmbank.module.main.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.flyco.tablayout.SlidingTabLayout;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseFragment;
import com.km.rmbank.module.main.HomeActivity;
import com.km.rmbank.module.main.fragment.home.AppointFragment;
import com.km.rmbank.module.main.fragment.home.CircleFriendsFragment;
import com.km.rmbank.module.main.fragment.home.ClubFragment;

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
        SlidingTabLayout tabLayout = mViewManager.findView(R.id.tab_layout);
        ViewPager viewPager = mViewManager.findView(R.id.viewPager);



        initToolBar(mToolbar);
        initTablayout(tabLayout,viewPager);

    }

    private void initToolBar(Toolbar toolbar){
        toolbar.inflateMenu(R.menu.toolbar_map);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.map){
                    showToast(getResources().getString(R.string.notOpen));
                }
                return false;
            }
        });
    }

    private void initTablayout(final SlidingTabLayout tabLayout, final ViewPager viewPager){

        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(ClubFragment.newInstance(null));
        fragmentList.add(AppointFragment.newInstance(null));
        fragmentList.add(CircleFriendsFragment.newInstance(null));

        tabLayout.setViewPager(viewPager,mTitles,getActivity(),fragmentList);
    }

}
