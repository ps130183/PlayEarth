package com.km.rmbank.delegate;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.km.rmbank.R;
import com.km.rmbank.adapter.ViewPagerTabAdapter;
import com.km.rmbank.entity.TabEntity;
import com.km.rmbank.module.main.personal.circlefriends.ForumOfMyCommentFragment;
import com.km.rmbank.module.main.personal.circlefriends.ForumsOfMyselfFragment;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.base.ItemViewDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PengSong on 18/1/25.
 */

public class ForumFooterDelegate implements ItemViewDelegate<String> {

    private String[] mTitle = {"主贴","参与"};
    private FragmentManager mFragmentManager;
    private ViewPager mViewPager;

    private ForumsOfMyselfFragment forumsOfMyselfFragment;
    private ForumOfMyCommentFragment forumOfMyCommentFragment;

    public ForumFooterDelegate(FragmentManager mFragmentManager) {

        this.mFragmentManager = mFragmentManager;
    }
    @Override
    public int getItemViewLayoutId() {
        return R.layout.delegate_my_forum_info_bottom;
    }

    @Override
    public boolean isForViewType(String item, int position) {
        return "1".equals(item);
    }

    @Override
    public void convert(CommonViewHolder holder, String s, int position) {
        initViewPager(holder);
    }

    private void initViewPager(CommonViewHolder holder) {
        mViewPager = holder.findView(R.id.viewpager);
        SlidingTabLayout mTabLayout = holder.findView(R.id.s_tab_layout);

        List<CustomTabEntity> mTitleList = new ArrayList<>();
        for (String title : mTitle) {
            mTitleList.add(new TabEntity(title,0,0));
        }

        List<Fragment> fragments = new ArrayList<>();
        forumsOfMyselfFragment = ForumsOfMyselfFragment.newInstance(null);
        forumOfMyCommentFragment = ForumOfMyCommentFragment.newInstance(null);
        fragments.add(forumsOfMyselfFragment);
        fragments.add(forumOfMyCommentFragment);

        ViewPagerTabAdapter adapter = new ViewPagerTabAdapter(mFragmentManager, fragments, mTitleList);
        mViewPager.setAdapter(adapter);
        mTabLayout.setViewPager(mViewPager);
    }


    public LinearLayoutManager getCurViewLlm(){
        int position = mViewPager.getCurrentItem();
        LinearLayoutManager llm;
        if (position == 0){
            llm = forumsOfMyselfFragment.getLayoutManager();
        } else {
            llm = forumOfMyCommentFragment.getLayoutManager();
        }

        return llm;
    }
}
