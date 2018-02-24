package com.km.rmbank.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.flyco.tablayout.listener.CustomTabEntity;

import java.util.List;

/**
 * Created by PengSong on 18/1/16.
 */

public class ViewPagerTabAdapter extends FragmentPagerAdapter {

    private FragmentManager mFragmentManager;
    private List<Fragment> mFragments;
    private List<CustomTabEntity> mTitles;


    public ViewPagerTabAdapter(FragmentManager fm, List<Fragment> fragmentList, List<CustomTabEntity> titleList) {
        super(fm);
        this.mFragmentManager = fm;
        this.mFragments = fragmentList;
        this.mTitles = titleList;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles == null){
            return "";
        }
        return mTitles.get(position).getTabTitle();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

}
