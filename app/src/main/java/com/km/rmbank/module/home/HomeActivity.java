package com.km.rmbank.module.home;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.entity.TabEntity;
import com.km.rmbank.module.home.fragment.HomeAppointFragment;
import com.km.rmbank.module.home.fragment.HomeFragment;
import com.km.rmbank.module.home.fragment.HomeFriendsFragment;
import com.km.rmbank.module.home.fragment.HomeMeFragment;
import com.km.rmbank.module.home.fragment.HomeShopFragment;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.SystemBarHelper;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity {

    private String[] mTitles = {"首页", "约咖", "人脉", "熟人购", "我的"};

    private int[] mIconUnselectIds = {
            R.mipmap.icon_home_bottom_home_unselect, R.mipmap.icon_home_bottom_appoint_unselect,
            R.mipmap.icon_home_bottom_friends_unselect, R.mipmap.icon_home_bottom_shop_unselect,
            R.mipmap.icon_home_bottom_me_unselect};
    private int[] mIconSelectIds = {
            R.mipmap.icon_home_bottom_home_selected, R.mipmap.icon_home_bottom_appoint_selected,
            R.mipmap.icon_home_bottom_friends_selected, R.mipmap.icon_home_bottom_shop_selected,
            R.mipmap.icon_home_bottom_me_selected};
    private ArrayList<Fragment> fragmentList;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_home;
    }

    @Override
    public BaseTitleBar getBaseTitleBar() {
        return null;
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        CommonTabLayout tabLayout = mViewManager.findView(R.id.tab_layout);
        initTabLayout(tabLayout);
    }

    /**
     * 初始化首页底部导航
     *
     * @param tabLayout
     */
    private void initTabLayout(CommonTabLayout tabLayout) {

        fragmentList = new ArrayList<>();
        fragmentList.add(HomeFragment.newInstance(null));
        fragmentList.add(HomeAppointFragment.newInstance(null));
        fragmentList.add(HomeFriendsFragment.newInstance(null));
        fragmentList.add(HomeShopFragment.newInstance(null));
        fragmentList.add(HomeMeFragment.newInstance(null));

        ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        tabLayout.setTabData(mTabEntities,this,R.id.main_page,fragmentList);
    }
}
