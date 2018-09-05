package com.km.rmbank.module.main.personal.book;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.km.rmbank.R;
import com.km.rmbank.adapter.ViewPagerTabAdapter;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.entity.TabEntity;
import com.km.rmbank.titleBar.BookVenueManagerTitleBar;
import com.km.rmbank.titleBar.GoodsToolBar;

import java.util.ArrayList;
import java.util.List;

public class BookVenueManageActivity extends BaseActivity {

    private SlidingTabLayout tabLayout;
    private String[] titleNames = {"申请中","已通过"};
    private BookVenueManagerTitleBar bvmTitleBar;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_book_venue_manage;
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        bvmTitleBar = (BookVenueManagerTitleBar) titleBar;
        bvmTitleBar.setTitleNames(titleNames);
        bvmTitleBar.setOnClickBackListener(new BookVenueManagerTitleBar.OnClickBackListener() {
            @Override
            public void clickBack() {
                finish();
            }
        });

        bvmTitleBar.setRightName("申请记录");
        bvmTitleBar.setOnClickRightListener(new BookVenueManagerTitleBar.OnClickRightListener() {
            @Override
            public void click() {
                startActivity(BookVenueApplyRecordActivity.class);
            }
        });
    }

    @Override
    public BaseTitleBar getBaseTitleBar() {
        return new BookVenueManagerTitleBar();
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initViewPager();
    }

    private void initViewPager(){
        tabLayout = mViewManager.findView(R.id.tab_layout);

        List<CustomTabEntity> mTitleList = new ArrayList<>();
        for (String title : titleNames) {
            mTitleList.add(new TabEntity(title,0,0));
        }

        ViewPager viewPager = mViewManager.findView(R.id.viewPager);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(BookVenueApplyingFragment.newInstance(null));
        fragments.add(BookVenuePassedFragment.newInstance(null));
        ViewPagerTabAdapter adapter = new ViewPagerTabAdapter(getSupportFragmentManager(),fragments,mTitleList);
        viewPager.setAdapter(adapter);

        tabLayout.setViewPager(viewPager);
    }
}
