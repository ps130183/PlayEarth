package com.km.rmbank.module.main.fragment;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.flyco.tablayout.SlidingTabLayout;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseFragment;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.dto.HomeRecommendDto;
import com.km.rmbank.dto.MapMarkerDto;
import com.km.rmbank.module.main.appoint.AppointFragment;
import com.km.rmbank.module.main.fragment.home.CircleFriendsFragment;
import com.km.rmbank.module.main.fragment.home.ClubFragment;
import com.km.rmbank.module.main.map.MapActivity;
import com.km.rmbank.mvp.model.HomeModel;
import com.km.rmbank.mvp.presenter.HomePresenter;
import com.km.rmbank.mvp.view.IHomeView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment<IHomeView,HomePresenter> implements IHomeView {

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
    protected HomePresenter createPresenter() {
        return new HomePresenter(new HomeModel());
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
                    getPresenter().getMapMarkers();
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

    @Override
    public void showMapMarkerResult(List<MapMarkerDto> mapMarkerDtos) {
        Bundle bundle =new Bundle();

        bundle.putParcelableArrayList("mapMarkers", (ArrayList<? extends Parcelable>) mapMarkerDtos);
        startActivity(MapActivity.class,bundle);
    }

    @Override
    public void showHomeRecommend(List<HomeRecommendDto> recommendDtos) {

    }

    @Override
    public void showClubInfo(ClubDto clubDto) {

    }

    @Override
    public void applyActionSuccess(String actionId, String type) {

    }

}
