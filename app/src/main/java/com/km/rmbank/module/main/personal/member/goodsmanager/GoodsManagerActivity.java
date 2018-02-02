package com.km.rmbank.module.main.personal.member.goodsmanager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.titleBar.GoodsManagerToolBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GoodsManagerActivity extends BaseActivity {

    private String[] mTitles = {"已发布", "已售出"};

    @BindView(R.id.stl_title)
    SegmentTabLayout stlTitle;
//    @BindView(R.id.viewpager)
//    ViewPager mViewpager;

    @BindView(R.id.activity_goods_manager)
    FrameLayout mFrameLayout;

    RepleaseGoodsListFragment repleaseGoodsListFragment1;
    OrderManagerFragment orderManagerFragment;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_goods_manager;
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        super.onCreateTitleBar(titleBar);
        GoodsManagerToolBar managerToolBar = (GoodsManagerToolBar) titleBar;
        managerToolBar.setRightMenu(R.menu.toolbar_goods_manager, new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.add_new_goods){
                    startActivity(CreateNewGoodsActivity.class);
                }
                return false;
            }
        });

        managerToolBar.setBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public BaseTitleBar getBaseTitleBar() {
        return new GoodsManagerToolBar();
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initTitle();
    }


    private void initTitle(){

        stlTitle.setTabData(mTitles);

        stlTitle.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public boolean onTabSelect(int position) {
                setPageByTitleBar(position);
                return false;
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        stlTitle.setCurrentTab(0);
        setPageByTitleBar(0);
    }

    private void setPageByTitleBar(int position){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (position){
            case 0:
                if (repleaseGoodsListFragment1 == null){
                    repleaseGoodsListFragment1 = RepleaseGoodsListFragment.newInstance(null);
                }
                ft.replace(R.id.activity_goods_manager,repleaseGoodsListFragment1);
                break;
            case 1:
                if (orderManagerFragment == null){
                    orderManagerFragment = OrderManagerFragment.newInstance(null);
                }
                ft.replace(R.id.activity_goods_manager,orderManagerFragment);
                break;
        }
        ft.commit();
    }
}
