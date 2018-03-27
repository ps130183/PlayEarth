package com.km.rmbank.module.main.personal.hpage;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.entity.TabEntity;
import com.km.rmbank.utils.SystemBarHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PersonalHomePageActivity extends BaseActivity {

    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;
    private String[] tabLaoutNames = {"动态","供应","需求","活动"};
    private int[] tabLayoutImages = {R.mipmap.icon_php_dongtai,R.mipmap.icon_php_gongying,R.mipmap.icon_php_xuqiu,R.mipmap.icon_php_huodong};

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
        Toolbar mToolbar = mViewManager.findView(R.id.toolbar);
        int statusBarHeight = SystemBarHelper.getStatusBarHeight(this);
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mToolbar.getLayoutParams();
        lp.topMargin = statusBarHeight;
        mToolbar.setLayoutParams(lp);

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
        SystemBarHelper.immersiveStatusBar(this);
        SystemBarHelper.setTranslucentView((ViewGroup) this.getWindow().getDecorView(),false,0);

        LinearLayout bgContent = mViewManager.findView(R.id.bgContent);
        int statusBarHeight = SystemBarHelper.getStatusBarHeight(this);
        int bgContentHeight = statusBarHeight + ConvertUtils.dp2px(205);
        bgContent.getLayoutParams().height = bgContentHeight;
    }

    private void initTabLayout(){
        ArrayList<CustomTabEntity> tabEntities = new ArrayList<>();
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        for (int i = 0; i < tabLaoutNames.length; i++){
            tabEntities.add(new TabEntity(tabLaoutNames[i],tabLayoutImages[i],tabLayoutImages[i]));
            Bundle bundle = new Bundle();
            bundle.putString("title",tabLaoutNames[i]);
            fragmentList.add(DongTaiFragment.newInstance(bundle));
        }
        tabLayout.setTabData(tabEntities);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public boolean onTabSelect(int position) {
                return true;
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

}
