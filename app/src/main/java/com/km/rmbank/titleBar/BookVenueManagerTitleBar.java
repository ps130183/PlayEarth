package com.km.rmbank.titleBar;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.base.ViewManager;
import com.km.rmbank.entity.TabEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PengSong on 18/8/12.
 */

public class BookVenueManagerTitleBar extends BaseTitleBar {

    String[] titleNames;
    SlidingTabLayout tabLayout;
    private String rightName;
    private OnClickBackListener onClickBackListener;
    private OnClickRightListener onClickRightListener;

    @Override
    public int getTitleBarViewRes() {
        return R.layout.title_bar_book_venue_manager;
    }

    @Override
    public void createTitleBar(ViewManager viewManager) {
        Toolbar toolbar = viewManager.findView(R.id.toolBar);
        toolbar.setNavigationIcon(R.mipmap.icon_arrow_left_black_block);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickBackListener != null){
                    onClickBackListener.clickBack();
                }
            }
        });

        TextView right = viewManager.findView(R.id.tv_right);
        if (onClickRightListener != null){
            right.setVisibility(View.VISIBLE);
            right.setText(rightName);
            right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickRightListener.click();
                }
            });

        }

    }

    public void setTitleNames(String[] titleNames) {
        this.titleNames = titleNames;
    }

    public void attachViewPager(final ViewPager mViewPager) {
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public boolean onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
                return true;
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setRightName(String rightName) {
        this.rightName = rightName;
    }

    public interface OnClickBackListener{
        void clickBack();
    }

    public void setOnClickBackListener(OnClickBackListener onClickBackListener) {
        this.onClickBackListener = onClickBackListener;
    }

    public interface OnClickRightListener{
        void click();
    }

    public void setOnClickRightListener(OnClickRightListener onClickRightListener) {
        this.onClickRightListener = onClickRightListener;
    }
}
