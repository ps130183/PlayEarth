package com.km.rmbank.module.main.personal.action;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.km.rmbank.R;
import com.km.rmbank.adapter.ViewPagerTabAdapter;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.entity.TabEntity;
import com.km.rmbank.titleBar.AppliedActionTitleBar;
import com.km.rmbank.titleBar.GoodsToolBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AppliedActionActivity extends BaseActivity {

    private String[] mTitle = {"已报名", "已结束"};
    @BindView(R.id.s_tab_layout)
    SlidingTabLayout mTabLayout;

    @BindView(R.id.viewPager)
    ViewPager mViewPager;




    @Override
    public int getContentViewRes() {
        return R.layout.activity_applied_action;
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        AppliedActionTitleBar actionTitleBar = (AppliedActionTitleBar) titleBar;
        actionTitleBar.setOnClickBackListener(new GoodsToolBar.OnClickBackListener() {
            @Override
            public void clickBack() {
                finish();
            }
        });
    }

    @Override
    public BaseTitleBar getBaseTitleBar() {
        return new AppliedActionTitleBar();
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initViewPager();
    }


    /**
     * 加载已报名 和 已结束 两个fragment
     */
    private void initViewPager() {

        List<CustomTabEntity> mTitleList = new ArrayList<>();
        for (String title : mTitle) {
            mTitleList.add(new TabEntity(title,0,0));
        }

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(ActionAppliedFragment.newInstance(null));//商品信息
        fragments.add(ActionFinishedFragment.newInstance(null));//商品详情
        ViewPagerTabAdapter adapter = new ViewPagerTabAdapter(this.getSupportFragmentManager(), fragments, mTitleList);
        mViewPager.setAdapter(adapter);
        mTabLayout.setViewPager(mViewPager);
    }
}
