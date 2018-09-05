package com.km.rmbank.module.main.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.km.rmbank.R;
import com.km.rmbank.adapter.ViewPagerTabAdapter;
import com.km.rmbank.base.BaseFragment;
import com.km.rmbank.entity.TabEntity;
import com.km.rmbank.module.main.appoint.AppointFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeAppointActionFragment extends BaseFragment {

    private String[] actionTypeNames = {"全部","下午茶","路演大会","结缘晚宴","户外基地"};

    @BindView(R.id.actionTypes)
    SlidingTabLayout actionTypesTab;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    public HomeAppointActionFragment() {
        // Required empty public constructor
    }

    public static HomeAppointActionFragment newInstance(Bundle bundle) {

        HomeAppointActionFragment fragment = new HomeAppointActionFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_appoint_action_listk;
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {
        initViewPager();
    }

    private void initViewPager(){
        List<Fragment> fragmentList = new ArrayList<>();
        List<CustomTabEntity> titleList = new ArrayList<>();
        for (int i = 0; i < actionTypeNames.length; i++){
            String name = actionTypeNames[i];
            Bundle bundle = new Bundle();
            bundle.putInt("type",i);
            fragmentList.add(AppointFragment.newInstance(bundle));
            TabEntity tabEntity = new TabEntity(name,0,0);
            titleList.add(tabEntity);
        }
        ViewPagerTabAdapter adapter = new ViewPagerTabAdapter(getChildFragmentManager(),fragmentList,titleList);
        mViewPager.setAdapter(adapter);
        actionTypesTab.setViewPager(mViewPager);
    }

}
