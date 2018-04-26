package com.km.rmbank.module.main.personal.hpage;

import android.support.annotation.ColorInt;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.entity.TabEntity;
import com.km.rmbank.event.TranslationScaleEvent;
import com.km.rmbank.utils.ColorUtils;
import com.km.rmbank.utils.SystemBarHelper;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;

public class PersonalHomePageActivity extends BaseActivity {

    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsing;
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;
//    @BindView(R.id.tab_layout2)
//    CommonTabLayout tabLayout2;
    private String[] tabLaoutNames = {"动态","供应","需求","活动"};
//    private int[] tabLayoutImages = {R.mipmap.icon_php_dongtai,R.mipmap.icon_php_gongying,R.mipmap.icon_php_xuqiu,R.mipmap.icon_php_huodong};

    private ArrayList<Fragment> fragmentList;
    @Override
    public int getContentViewRes() {
        return R.layout.activity_personal_home_page;
    }

    @Override
    public BaseTitleBar getBaseTitleBar() {
        return null;
    }

    @Override
    public boolean statusBarTextColorIsDark() {
        return false;
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initPageTop();
        initTabLayout();
    }

    /**
     * 初始化 标题栏
     */
    private void initToolbar(){
        mCollapsing.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        mCollapsing.setExpandedTitleColor(getResources().getColor(R.color.white));

        Toolbar mToolbar = mViewManager.findView(R.id.toolbar);
//        int statusBarHeight = SystemBarHelper.getStatusBarHeight(this);
//        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mToolbar.getLayoutParams();
//        lp.topMargin = statusBarHeight;
//        mToolbar.setLayoutParams(lp);

        mToolbar.inflateMenu(R.menu.toolbar_home_personal_home_page);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.more){
                    showToast("更多功能有待更新");
                }
                return false;
            }
        });
    }

    /**
     * 初始化 顶部
     */
    private void initPageTop(){
        initToolbar();
        SystemBarHelper.tintStatusBar(mInstance,getResources().getColor(R.color.statusColor),0);
//        SystemBarHelper.immersiveStatusBar(this);
//        SystemBarHelper.setTranslucentView((ViewGroup) this.getWindow().getDecorView(),false,0);

        LinearLayout bgContent = mViewManager.findView(R.id.bgContent);
        int statusBarHeight = SystemBarHelper.getStatusBarHeight(this);
        int bgContentHeight = statusBarHeight + ConvertUtils.dp2px(205);
        bgContent.getLayoutParams().height = bgContentHeight;

        TextView authentication = mViewManager.findView(R.id.authentication);
        SpannableString content = new SpannableString("去认证");
        content.setSpan(new UnderlineSpan(),0,3,0);
        authentication.setText(content);
    }

    private void initTabLayout(){
        ArrayList<CustomTabEntity> tabEntities = new ArrayList<>();
        ArrayList<CustomTabEntity> tabEntities2 = new ArrayList<>();
        fragmentList = new ArrayList<>();
        for (int i = 0; i < tabLaoutNames.length; i++){
            tabEntities.add(new TabEntity(tabLaoutNames[i]+i,0,0));
            Bundle bundle = new Bundle();
            bundle.putString("title",tabLaoutNames[i]);
            fragmentList.add(PersonalDynamicFragment.newInstance(bundle));

            tabEntities2.add(new TabEntity(tabLaoutNames[i],0,0));
        }

        tabLayout.setTabData(tabEntities);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public boolean onTabSelect(int position) {
//                tabLayout2.setCurrentTab(position);
                showFragment(position);
                return true;
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

//        tabLayout2.setTabData(tabEntities2);
//        tabLayout2.setOnTabSelectListener(new OnTabSelectListener() {
//            @Override
//            public boolean onTabSelect(int position) {
//                tabLayout.setCurrentTab(position);
//                showFragment(position);
//                return true;
//            }
//
//            @Override
//            public void onTabReselect(int position) {
//
//            }
//        });

        showFragment(0);

    }


    private void showFragment(int position){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.flContent,fragmentList.get(position))
                .commit();
    }

    /**
     * 根据滑动距离 变化 状态栏颜色
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void headerTranslationScale(TranslationScaleEvent event){
        int color = ColorUtils.caculateColor(getResources().getColor(R.color.statusColor2),getResources().getColor(R.color.statusColor),event.getScale());
        if (event.getScale() > 0.25){
            SystemBarHelper.tintStatusBar(mInstance,color,0);
        }
        LogUtils.d("translationY scale = " + event.getScale() + "  color = " + color);
    }

}
