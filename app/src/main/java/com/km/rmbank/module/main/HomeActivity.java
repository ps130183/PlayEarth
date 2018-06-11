package com.km.rmbank.module.main;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.BannerDto;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.dto.HomeRecommendDto;
import com.km.rmbank.dto.MapMarkerDto;
import com.km.rmbank.entity.TabEntity;
import com.km.rmbank.event.DownloadAppEvent;
import com.km.rmbank.event.HomeTabLayoutEvent;
import com.km.rmbank.event.RefreshPersonalInfoEvent;
import com.km.rmbank.module.login.LoginActivity;
import com.km.rmbank.module.main.action.PromotionActivity;
import com.km.rmbank.module.main.fragment.HomeAppointActionFragment;
import com.km.rmbank.module.main.fragment.HomePersonalCenterFragment;
import com.km.rmbank.module.main.fragment.HomeRecommendFragment;
import com.km.rmbank.module.main.fragment.HomeMeFragment;
import com.km.rmbank.module.main.fragment.HomeShopFragment;
import com.km.rmbank.module.main.fragment.HomeNewFragment;
import com.km.rmbank.mvp.model.HomeModel;
import com.km.rmbank.mvp.presenter.HomePresenter;
import com.km.rmbank.mvp.view.IHomeView;
import com.km.rmbank.service.ContractService;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.DateUtils;
import com.km.rmbank.utils.DialogUtils;
import com.km.rmbank.utils.EventBusUtils;
import com.km.rmbank.utils.SystemBarHelper;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class HomeActivity extends BaseActivity<IHomeView, HomePresenter> implements IHomeView {

    public final static int REQUEST_PERMISSION_CAMERA = 1;
    public final static int REQUEST_PERMISSION_LOCATION = 2;

    private String[] mTitles = {"首页","约么", "我的"};

    private int[] mIconUnselectIds = {
            R.mipmap.icon_home_bottom_unselect1,
//            R.mipmap.icon_home_bottom_unselect2,
            R.mipmap.icon_home_bottom_unselect3,
//            R.mipmap.icon_home_bottom_unselect4,
            R.mipmap.icon_home_bottom_unselect5};
    private int[] mIconSelectIds = {
            R.mipmap.icon_home_bottom_selected1,
//            R.mipmap.icon_home_bottom_selected2,
            R.mipmap.icon_home_bottom_selected3,
//            R.mipmap.icon_home_bottom_selected4,
            R.mipmap.icon_home_bottom_selected5};
    private ArrayList<Fragment> fragmentList;

    private CommonTabLayout tabLayout;

    private int lastPosition;

    private boolean isExsit;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_home;
    }


    @Override
    public BaseTitleBar getBaseTitleBar() {
        return null;
    }

    @Override
    public boolean statusBarTextColorIsDark() {
        SystemBarHelper.setStatusBarDarkMode(mInstance);
        return false;
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(new HomeModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
        }

        changeStatusBar(0);
        tabLayout = mViewManager.findView(R.id.tab_layout);
//        keyBorad((ViewGroup) mViewManager.findView(R.id.rl_root));
//        FrameLayout flContent = mViewManager.findView(R.id.main_page);
//        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) flContent.getLayoutParams();
//        lp.height = ScreenUtils.getScreenHeight() - ConvertUtils.dp2px(48) - SystemBarHelper.getStatusBarHeight(this);
        initTabLayout(tabLayout);


        JPushInterface.setAlias(this, Constant.userLoginInfo.getMobilePhone(), new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                LogUtils.d("极光别名设置成功 = " + s + "    i =" + i);
            }
        });


        requestLocationPremission();

        //退出程序
        setOnClickBackLisenter(new OnClickBackLisenter() {
            @Override
            public boolean onClickBack() {
                exsit();
                return false;
            }
        });

        //检测app版本
        EventBusUtils.post(new DownloadAppEvent(this));

        showPromotionDialog();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().getUserInfo();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int position = intent.getIntExtra("position", 0);
//        LogUtils.e("fragmentList ====== " + fragmentList.size());
//        LogUtils.e("tablayout CurrentTab ====== " + tabLayout.getCurrentTab());
        tabLayout.setCurrentTab(position);
        changeStatusBar(position);
    }

    /**
     * 改变状态栏的 状态
     * @param position
     */
    private void changeStatusBar(int position){
//        SystemBarHelper.immersiveStatusBar(this);
        boolean isShowStatusBar = false;
        if (position < 5){
            isShowStatusBar = true;
        }
        SystemBarHelper.setTranslucentView((ViewGroup) this.getWindow().getDecorView(),isShowStatusBar,0);
//        SystemBarHelper.setStatusBar((ViewGroup) this.getWindow().getDecorView(),Color.WHITE,isShowStatusBar);

    }


    /**
     * 退出
     */
    private void exsit() {
        if (isExsit) {
            finish();
            System.exit(0);
        } else {
            isExsit = true;
            showToast("再按一次退出程序");
            Observable.timer(2, TimeUnit.SECONDS)
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(@io.reactivex.annotations.NonNull Long aLong) throws Exception {
                            isExsit = false;
                        }
                    });
        }
    }

    /**
     * 请求使用权限
     */
    private void requestLocationPremission() {
        String[] locationPermission = {Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_CONTACTS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        PermissionGen.needPermission(this, REQUEST_PERMISSION_LOCATION, locationPermission);
    }

    @PermissionSuccess(requestCode = REQUEST_PERMISSION_LOCATION)
    public void getLocationPermissionSuccess() {
        startContract();
    }

    /**
     * 获取当前手机联系人信息，并分析是否可邀请加入我的人脉
     */
    private void startContract(){
        startService(new Intent(this,ContractService.class));
    }

    /**
     * 初始化首页底部导航
     *
     * @param tabLayout
     */
    private void initTabLayout(CommonTabLayout tabLayout) {

        fragmentList = new ArrayList<>();
        fragmentList.add(HomeNewFragment.newInstance(null));
//        fragmentList.add(HomeRecommendFragment.newInstance(null));
        fragmentList.add(HomeAppointActionFragment.newInstance(null));
//        fragmentList.add(HomeShopFragment.newInstance(null));
        fragmentList.add(HomePersonalCenterFragment.newInstance(null));
//        fragmentList.add(HomeMeFragment.newInstance(null));

        ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        tabLayout.setTabData(mTabEntities, this, R.id.main_page, fragmentList);

        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public boolean onTabSelect(int position) {
                boolean result = true;
                changeStatusBar(position);
                Bundle bundle = new Bundle();
                bundle.putInt("lastPosition", lastPosition);
                switch (position) {
                    case 0://首页
//                        showDialog();
                        break;

                    case 1://人脉
                        break;
//                    case 3://熟人购
////                        startActivity(ShopActivity.class, bundle);
//                        break;
                    case 2://我的
                        if (Constant.userLoginInfo.isEmpty()){
                            showToast(getResources().getString(R.string.toast_not_login));
                            startActivity(LoginActivity.class);
                            break;
                        }
                        EventBusUtils.post(new RefreshPersonalInfoEvent());
                        break;
                    default:
                        break;
                }

                lastPosition = position;

                return result;
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void hideTabLayout(HomeTabLayoutEvent event) {
        tabLayout.setVisibility(event.isHide() ? View.GONE : View.VISIBLE);
//        RelativeLayout.LayoutParams tabParams = (RelativeLayout.LayoutParams) tabLayout.getLayoutParams();
//        View line = mViewManager.findView(R.id.line);
//        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) line.getLayoutParams();
//        if (event.isHide()){
//            tabParams.height = 0;
//            lp.setMargins(0,0,0,0);
//        } else {
//            tabParams.height = ConvertUtils.dp2px(54);
//            lp.setMargins(0,0,0,ConvertUtils.dp2px(54));
//        }
    }

    @Override
    public void showMapMarkerResult(List<MapMarkerDto> mapMarkerDtos) {

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

    @Override
    public void showHomeBanner(List<BannerDto> bannerDtoList) {

    }


    private void showPromotionDialog() {
        long curMilli = System.currentTimeMillis();
        long targetMilli = DateUtils.getInstance().stringDateToMillis("2018-03-04", DateUtils.YMD);
        if (curMilli > targetMilli) {
            return;
        }
        DialogUtils.showHomeDialog(mInstance, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(PromotionActivity.class);
            }
        });
    }
}
