package com.flyco.tablayout.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.flyco.tablayout.R;

import java.util.ArrayList;
import java.util.List;

public class CommonFragmentChangeManager {
    private FragmentManager mFragmentManager;
    private int mContainerViewId;
    /** Fragment切换数组 */
    private ArrayList<Fragment> mFragments;
    /** 当前选中的Tab */
    private int mCurrentTab;
    private String[] fragmentTags;
    private List<ViewHolder> viewHolders;

    public CommonFragmentChangeManager(FragmentManager fm, int containerViewId, ArrayList<Fragment> fragments) {
        this.mFragmentManager = fm;
        this.mContainerViewId = containerViewId;
        this.mFragments = fragments;
        initFragments();
    }

    /** 初始化fragments */
    private void initFragments() {
        viewHolders = new ArrayList<>();
//        for (Fragment fragment : mFragments) {
//            mFragmentManager.beginTransaction().add(mContainerViewId, fragment).hide(fragment).commit();
//        }
        for (int i = 0; i < mFragments.size(); i++){
            Fragment fragment = mFragments.get(i);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.fragmentClass = fragment.getClass();
            viewHolder.tag = fragment.getClass().getName();
            viewHolder.viewIndex = i;
            viewHolders.add(viewHolder);
//            mFragmentManager.beginTransaction().add(mContainerViewId, fragment,viewHolder.tag).hide(fragment).commit();
        }

//        Log.d("FragmentChangeManager","current fragments size == " + mFragments.size());
        fragmentTags = new String[mFragments.size()];

        setFragments(getViewHolder(0));
    }

    public void setFragments(ViewHolder holder){
        mCurrentTab = holder.viewIndex;
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_right_in,R.anim.slide_left_out);
//        ft.setCustomAnimations()
        for (ViewHolder viewHolder : viewHolders){

            Fragment fragment = mFragmentManager.findFragmentByTag(viewHolder.tag);
            if (fragment == null){
                fragment = mFragments.get(viewHolder.viewIndex);
                ft.add(mContainerViewId,fragment,viewHolder.tag);
            }

            if (mCurrentTab == viewHolder.viewIndex){
                ft.show(fragment);
            } else {
                ft.hide(fragment);
            }

        }
        ft.commit();


    }

    public ViewHolder getViewHolder(int index){
        return viewHolders.get(index);
    }

    /** 界面切换控制 */
    public void setFragments(int index) {
        for (int i = 0; i < mFragments.size(); i++) {
            FragmentTransaction ft = mFragmentManager.beginTransaction();
//            Fragment fragment = mFragments.get(i);
            Fragment fragment = null;
            if (fragmentTags[i] != null){
                fragment = mFragmentManager.findFragmentByTag(fragmentTags[i]);
            }

            if (fragment == null){
                fragment = mFragments.get(i);
            }
            if (i == index) {
                if (!fragment.isAdded() && fragmentTags[i] == null){
                    fragmentTags[i] = "" + i;
                    ft.add(mContainerViewId,fragment,fragmentTags[i]);
                }
//                ft.replace(mContainerViewId,fragment);

                ft.show(fragment);
            } else {
                ft.hide(fragment);
            }
            ft.commit();
        }
        mCurrentTab = index;
    }

    public int getCurrentTab() {
        return mCurrentTab;
    }

    public Fragment getCurrentFragment() {
        return mFragments.get(mCurrentTab);
    }


    public class ViewHolder{
        public String tag;
        public int viewIndex;
        public Class fragmentClass;
    }
}