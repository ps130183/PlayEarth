package com.km.rmbank.module.main;

import android.Manifest;
import android.content.Intent;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.hubert.guide.NewbieGuide;
import com.app.hubert.guide.core.Builder;
import com.app.hubert.guide.core.Controller;
import com.app.hubert.guide.listener.OnGuideChangedListener;
import com.app.hubert.guide.listener.OnLayoutInflatedListener;
import com.app.hubert.guide.listener.OnPageChangedListener;
import com.app.hubert.guide.model.GuidePage;
import com.app.hubert.guide.model.HighLight;
import com.app.hubert.guide.model.HighlightOptions;
import com.app.hubert.guide.model.RelativeGuide;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.AdvertisDto;
import com.km.rmbank.dto.BannerDto;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.dto.HomeRecommendDto;
import com.km.rmbank.dto.MapMarkerDto;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.entity.HomeBookVenueEntity;
import com.km.rmbank.entity.TabEntity;
import com.km.rmbank.event.DownloadAppEvent;
import com.km.rmbank.event.HomeTabLayoutEvent;
import com.km.rmbank.event.RefreshPersonalInfoEvent;
import com.km.rmbank.module.login.LoginActivity;
import com.km.rmbank.module.main.action.PromotionActivity;
import com.km.rmbank.module.main.advert.AdvertisingActivity;
import com.km.rmbank.module.main.book.BookVenueTypeActivity;
import com.km.rmbank.module.main.fragment.HomeAppointActionFragment;
import com.km.rmbank.module.main.fragment.HomeNewFragment;
import com.km.rmbank.module.main.fragment.HomeSearchCompanyFragment;
import com.km.rmbank.module.main.fragment.PersonalCenterFragment;
import com.km.rmbank.module.main.personal.member.BecomeMemberActivity;
import com.km.rmbank.module.realname.CertifyRulesActivity;
import com.km.rmbank.module.webview.WebBrowserActivity;
import com.km.rmbank.mvp.model.HomeModel;
import com.km.rmbank.mvp.presenter.HomePresenter;
import com.km.rmbank.mvp.view.IHomeView;
import com.km.rmbank.retrofit.ApiConstant;
import com.km.rmbank.service.ContractService;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.DateUtils;
import com.km.rmbank.utils.ViewUtils;
import com.km.rmbank.utils.WebViewUtils;
import com.km.rmbank.utils.dialog.DialogUtils;
import com.km.rmbank.utils.EventBusUtils;
import com.km.rmbank.utils.SystemBarHelper;
import com.km.rmbank.utils.dialog.WindowCenterDialog;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.ps.mrcyclerview.BViewHolder;
import com.ps.mrcyclerview.ItemViewConvert;
import com.ps.mrcyclerview.MRecyclerView;

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

    private String[] mTitles = {"首页","约么","","地球云", "我的"};

    private int[] mIconUnselectIds = {
            R.mipmap.icon_home_bottom_unselect1,
//            R.mipmap.icon_home_bottom_unselect2,
            R.mipmap.icon_home_bottom_unselect3,
            R.mipmap.icon_home_bottom_unselect3,
            R.mipmap.icon_home_bottom_unselect4,
            R.mipmap.icon_home_bottom_unselect5};
    private int[] mIconSelectIds = {
            R.mipmap.icon_home_bottom_selected1,
//            R.mipmap.icon_home_bottom_selected2,
            R.mipmap.icon_home_bottom_selected3,
            R.mipmap.icon_home_bottom_selected3,
            R.mipmap.icon_home_bottom_selected4,
            R.mipmap.icon_home_bottom_selected5};
    private ArrayList<Fragment> fragmentList;

    private CommonTabLayout tabLayout;

    private int lastPosition;

    private boolean isExsit;

    private WindowCenterDialog mBookVenueDialog;
    private WindowCenterDialog mAdvertisingDialog;//广告弹出框

    private Controller controller;

    private ImageView ivBook;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_home;
    }


    @Override
    public BaseTitleBar getBaseTitleBar() {
        return null;
    }

//    @Override
//    public boolean statusBarTextColorIsDark() {
//        SystemBarHelper.setStatusBarDarkMode(mInstance);
//        return false;
//    }

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

        getPresenter().getHomeAdvert();
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

        boolean isApply = intent.getBooleanExtra("isApply",false);

        if (mBookVenueDialog != null){
            if (isApply){
                mBookVenueDialog.show();
            } else {
                mBookVenueDialog.dissmis();
            }
        }

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
//        if (position != 0){
//            isShowStatusBar = true;
//        }
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

//        tabLayout.getBackground().setAlpha(255);
        fragmentList = new ArrayList<>();
        fragmentList.add(HomeNewFragment.newInstance(null));
//        fragmentList.add(HomeRecommendFragment.newInstance(null));
        fragmentList.add(HomeAppointActionFragment.newInstance(null));
        fragmentList.add(HomeSearchCompanyFragment.newInstance(null));
        fragmentList.add(HomeSearchCompanyFragment.newInstance(null));
//        fragmentList.add(HomePersonalCenterFragment.newInstance(null));
        fragmentList.add(PersonalCenterFragment.newInstance(null));

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
                    case  2:
                        result = false;
                        break;
//                    case 3://熟人购
////                        startActivity(ShopActivity.class, bundle);
//                        break;
                    case 4://我的
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

        initBookVenueDialog();
        ImageView middle = mViewManager.findView(R.id.iv_middle);
        middle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showToast("this is middle");
                mBookVenueDialog.show();
            }
        });

//        mBookVenueDialog.show();
        showGuide1();

    }


    private void showGuide1(){

        Animation enterAnimation = new AlphaAnimation(0f, 1f);
        enterAnimation.setDuration(600);
        enterAnimation.setFillAfter(true);

        Animation exitAnimation = new AlphaAnimation(1f, 0f);
        exitAnimation.setDuration(600);
        exitAnimation.setFillAfter(true);

        ImageView middle = mViewManager.findView(R.id.iv_middle);

        int windowHeight = ScreenUtils.getScreenHeight();
        int windowWidth = ScreenUtils.getScreenWidth();

        int perWidth = windowWidth / 5;
        int bottomTop = ConvertUtils.dp2px(48);
        RectF rect = new RectF(perWidth * 3,windowHeight - bottomTop,perWidth * 4,windowHeight);

        RectF memberRect = new RectF(0,ConvertUtils.dp2px(370),windowWidth,ConvertUtils.dp2px(610));

        controller = NewbieGuide.with(mInstance)
                .setLabel("guide1")
//                .alwaysShow(true)
                .setShowCounts(1)
                .addGuidePage(GuidePage.newInstance()
                        .addHighLightWithOptions(middle, HighLight.Shape.CIRCLE,new HighlightOptions.Builder().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mBookVenueDialog.show();
                            }
                        }).build())
                        .setLayoutRes(R.layout.guide_home_1)
                        .setBackgroundColor(0xcc000000)
                        .setEnterAnimation(enterAnimation)//进入动画
                        .setExitAnimation(exitAnimation))//退出动画)
                .addGuidePage(GuidePage.newInstance()
                        .addHighLight(rect, HighLight.Shape.CIRCLE)
                        .setLayoutRes(R.layout.guide_home_2)
                        .setBackgroundColor(0xcc000000)
                        .setEnterAnimation(enterAnimation)//进入动画
                        .setExitAnimation(exitAnimation))//退出动画)
                .addGuidePage(GuidePage.newInstance()
                        .addHighLight(memberRect)
                        .setBackgroundColor(0xcc000000)
                        .setLayoutRes(R.layout.guide_home_3)
                        .setEnterAnimation(enterAnimation)//进入动画
                        .setExitAnimation(exitAnimation))//退出动画)
                .setOnPageChangedListener(new OnPageChangedListener() {
                    @Override
                    public void onPageChanged(int i) {
                    }
                })
                .setOnGuideChangedListener(new OnGuideChangedListener() {
                    @Override
                    public void onShowed(Controller controller) {
                    }

                    @Override
                    public void onRemoved(Controller controller) {
                    }
                })
                .show();
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

    @Override
    public void showHomeAdvert(AdvertisDto advertisDto) {
        if (advertisDto != null){
            initAdvertisingDialog(advertisDto);
        }
    }

    @Override
    public void showUserCard(UserInfoDto cardDto) {

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

    private void initBookVenueDialog(){
        mBookVenueDialog = new WindowCenterDialog(mInstance, R.layout.dialog_bottom_book_venue, new WindowCenterDialog.IViewConvert() {
            @Override
            public void convert(View view) {
                MRecyclerView<HomeBookVenueEntity> mRecyclerView = view.findViewById(R.id.recyclerView);
                mRecyclerView.addContentLayout(R.layout.item_home_can_book_venue, new ItemViewConvert<HomeBookVenueEntity>() {
                    @Override
                    public void convert(@NonNull BViewHolder holder, HomeBookVenueEntity mData, int position, @NonNull List<Object> payloads) {
                        GlideImageView imageView = holder.findView(R.id.imageView);
                        GlideUtils.loadImageByRes(imageView,mData.getImageRes());

                        holder.setText(R.id.venueName,mData.getVenueName());
                    }
                }).create();

                List<HomeBookVenueEntity> venueEntities = new ArrayList<>();
                venueEntities.add(new HomeBookVenueEntity(R.mipmap.icon_book_wanyan,"结缘晚宴"));
                venueEntities.add(new HomeBookVenueEntity(R.mipmap.icon_book_luyan,"路演大会"));
                venueEntities.add(new HomeBookVenueEntity(R.mipmap.icon_book_xiawucha,"下午茶"));
                mRecyclerView.loadDataOfNextPage(venueEntities);


                ImageView ivClose = view.findViewById(R.id.ivClose);
                ivClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBookVenueDialog.dissmis();
                    }
                });

                ivBook = view.findViewById(R.id.iv_book);
                TextView tvBook = view.findViewById(R.id.tv_book);
                ivBook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bookVenue();
                    }
                });
                tvBook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bookVenue();
                    }
                });

                RelativeLayout rlLeft = view.findViewById(R.id.rl_left);
                RelativeLayout rlRight = view.findViewById(R.id.rl_right);

                rlLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("webUrl", ApiConstant.API_BASE_URL + ApiConstant.API_MODEL + "/accounts/Scheduledrules/left.html");
                        startActivity(WebBrowserActivity.class,bundle);
                    }
                });
                rlRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("webUrl", ApiConstant.API_BASE_URL + ApiConstant.API_MODEL + "/accounts/Scheduledrules/right.html");
                        startActivity(WebBrowserActivity.class,bundle);
                    }
                });

                //预定引导
                FrameLayout rlContent = view.findViewById(R.id.rl_content);
                NewbieGuide.with(HomeActivity.this)
                        .setLabel("book")
                        .anchor(rlContent)
                        .setShowCounts(1)
                        .addGuidePage(GuidePage.newInstance()
//                                .addHighLight(ivBook,HighLight.Shape.CIRCLE)
                                .addHighLightWithOptions(ivBook, HighLight.Shape.CIRCLE,new HighlightOptions.Builder().setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        bookVenue();
                                    }
                                }).build())
                        .setLayoutRes(R.layout.guide_book_1)
                                .setBackgroundColor(0xcc000000)
                        .setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                            @Override
                            public void onLayoutInflated(View view, Controller controller) {
                                ImageView imageView = view.findViewById(R.id.imageView);
                                ViewUtils.setMargins(imageView,100,ScreenUtils.getScreenHeight() / 2 - 100,100,100);
                            }
                        }))
                        .show();

            }
        });

    }

    /**
     * 立即预定场地
     */
    private void bookVenue(){
        if (Constant.userLoginInfo.isEmpty()){
            showToast("请登录后再来预定场地！");
            startActivity(LoginActivity.class);
            return;
        }
        if ("4".equals(Constant.userInfo.getRoleId())){
            DialogUtils.showDefaultAlertDialog("预约场地需成为玩家合伙人，是否成为玩家合伙人？", new DialogUtils.ClickListener() {
                @Override
                public void clickConfirm() {
                    startActivity(BecomeMemberActivity.class);
                }
            });
            return;
        }

        if (Constant.userInfo.getStatus() != 2){
            DialogUtils.showDefaultAlertDialog("预约场地需实名认证，是否去实名认证？", new DialogUtils.ClickListener() {
                @Override
                public void clickConfirm() {
                    startActivity(CertifyRulesActivity.class);
                }
            });
            return;
        }
        startActivity(BookVenueTypeActivity.class);
    }

    private void initAdvertisingDialog(final AdvertisDto advertisDto){
        mAdvertisingDialog = new WindowCenterDialog(mInstance, R.layout.dialog_home_advertising, new WindowCenterDialog.IViewConvert() {
            @JavascriptInterface
            public void closeCover(){
                mAdvertisingDialog.dissmis();
            }

            @Override
            public void convert(View view) {
                WebView webView = view.findViewById(R.id.webView);
//                webView.getLayoutParams().width = width / 3 * 2;
//                webView.getLayoutParams().height = width;

                WebViewUtils.setUpWebViewDefaults(webView);
                webView.setBackgroundColor(0);
                webView.getBackground().setAlpha(104);
                webView.addJavascriptInterface(this,"wzdq");
                webView.setWebChromeClient(new WebChromeClient(){
                    @Override
                    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                        showToast(message);
                        result.cancel();
                        return true;
                    }
                });
                webView.setWebViewClient(new WebViewClient(){

                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        Bundle bundle = new Bundle();
                        bundle.putString("url",url);
                        startActivity(AdvertisingActivity.class,bundle);
                        mAdvertisingDialog.dissmis();
                        return super.shouldOverrideUrlLoading(view, url);
                    }
                });
                webView.loadUrl(advertisDto.getAdUrl() + "?fun=1");


                ImageView close = view.findViewById(R.id.close);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAdvertisingDialog.dissmis();
                    }
                });
            }
        });
        mAdvertisingDialog.show();
    }
}
