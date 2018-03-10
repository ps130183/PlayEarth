package com.km.rmbank.module.main.scenic;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.example.zhouwei.library.CustomPopWindow;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hss01248.dialog.interfaces.MyItemDialogListener;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.BannerDto;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.dto.MapMarkerDto;
import com.km.rmbank.dto.ScenicDto;
import com.km.rmbank.dto.ScenicServiceDto;
import com.km.rmbank.entity.TabEntity;
import com.km.rmbank.event.SpecialServiceEvent;
import com.km.rmbank.module.main.personal.member.BecomeMemberActivity;
import com.km.rmbank.module.main.personal.member.club.ClubActionsFragment;
import com.km.rmbank.module.main.personal.member.club.ClubIntroduceFragment;
import com.km.rmbank.mvp.model.ScenicModel;
import com.km.rmbank.mvp.presenter.ScenicPresenter;
import com.km.rmbank.mvp.view.IScenicView;
import com.km.rmbank.utils.DialogUtils;
import com.km.rmbank.utils.SystemBarHelper;
import com.ps.glidelib.GlideUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.DefaultImageLoader;
import com.youth.banner.loader.PilasterSideImageLoader;
import com.zhy.magicviewpager.transformer.ScaleInTransformer;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ScenicActivity extends BaseActivity<IScenicView,ScenicPresenter> implements IScenicView {

    @BindView(R.id.toolBar)
    Toolbar mToolbar;

    @BindView(R.id.commonTabLayout)
    CommonTabLayout tabLayout;
    private String[] ctaTitles = {"基地介绍", "特色服务"};

    @BindView(R.id.moreImage)
    ImageView moreImage;

    @BindView(R.id.banner)
    Banner mBanner;
    private List<Integer> bannerUrls;
    private List<BannerDto> bannerDtos;

    private FragmentManager fragmentManager;

    private boolean isPopBack = false;

    private CustomPopWindow makeMoneyPop;

    private String activityId;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_scenic;
    }

    @Override
    public boolean statusBarTextColorIsDark() {
        return false;
    }

    @Override
    public BaseTitleBar getBaseTitleBar() {
        return null;
    }

    @Override
    protected ScenicPresenter createPresenter() {
        return new ScenicPresenter(new ScenicModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initToolBar();
        String scenicId = getIntent().getStringExtra("scenicId");
        activityId = getIntent().getStringExtra("activityId");
        if (TextUtils.isEmpty(scenicId)){
            showToast("获取不到基地信息");
            return;
        }
        if (TextUtils.isEmpty(activityId)){//基地 或 会所
            getPresenter().getScenicInfo(scenicId);
        } else {//平台基地活动
            getPresenter().getPlatformScenicInfo(scenicId,activityId);
        }

    }

    /**
     * 初始化标题栏
     */
    private void initToolBar() {
        SystemBarHelper.immersiveStatusBar(this);
        int statusBarHeight = SystemBarHelper.getStatusBarHeight(mInstance);

        mToolbar.setPadding(0, statusBarHeight, 0, 0);
        mToolbar.getLayoutParams().height = ConvertUtils.dp2px(48) + statusBarHeight;

        mViewManager.setClickListener(R.id.backImage, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragmentManager != null && isPopBack){
                    fragmentManager.popBackStack();
                    isPopBack = false;
                } else {
                    finish();
                }
            }
        });

        setOnClickBackLisenter(new OnClickBackLisenter() {
            @Override
            public boolean onClickBack() {
                if (fragmentManager != null && isPopBack){
                    fragmentManager.popBackStack();
                    isPopBack = false;
                } else {
                    finish();
                }
                return true;
            }
        });
    }

    /**
     * 设置轮播图
     *
     * @param banner
     */
    private void setBannerInfo(final Banner banner,ScenicDto scenicDto) {
        bannerDtos = new ArrayList<>();
        ClubDto clubDto = scenicDto.getClubDto();
        String[] bannerUrls = clubDto.getBackgroundImg().split("#");
        for (int i = 0; i < bannerUrls.length; i++){
            BannerDto bannerDto = new BannerDto();
            bannerDto.setAvatarUrl(bannerUrls[i]);
            bannerDtos.add(bannerDto);
        }

        banner.setImages(bannerDtos)
                .isAutoPlay(true)
                .setDelayTime(3000)
                .setBannerStyle(BannerConfig.NUM_INDICATOR)
                .setIndicatorGravity(BannerConfig.RIGHT)
                .setImageLoader(new DefaultImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        BannerDto bannerDto = (BannerDto) path;
                        if (imageView != null) {
                            GlideUtils.loadImage(context, bannerDto.getAvatarUrl(), imageView);
                        } else {
                            showToast("请设置imageView");
                        }
                    }
                }).start();

    }

    private void initCommonTabLayout(final ScenicDto scenicDto) {
        ArrayList<CustomTabEntity> ctaDatas = new ArrayList<>();
        if (scenicDto.getClubDto().getClubType().equals("3")){
            ctaTitles[0] = "驿站介绍";
            ctaTitles[1] = "免费喝茶";
            moreImage.setVisibility(View.GONE);
        }
        for (int i = 0; i < ctaTitles.length; i++) {
            ctaDatas.add(new TabEntity(ctaTitles[i], 0, 0));
        }

        ArrayList<Fragment> fragmentList = new ArrayList<>();
        Bundle fragment1 = new Bundle();
        fragment1.putParcelable("scenicInfo",scenicDto.getClubDto());
        fragmentList.add(ScenicIntroduceFragment.newInstance(fragment1));

        Bundle fragment2 = getIntent().getExtras();


        if (TextUtils.isEmpty(activityId)){
            if ("3".equals(scenicDto.getClubDto().getClubType())){//会所
                fragment2.putParcelable("clubDto",scenicDto.getClubDto());
                fragmentList.add(ScenicSpecialServiceContentFragment.newInstance(fragment2));
            } else if ("2".equals(scenicDto.getClubDto().getClubType())){//基地
                fragment2.putParcelableArrayList("scenicServiceList", (ArrayList<? extends Parcelable>) scenicDto.getScenicServiceDtos());
                fragmentList.add(ScenicSpecialServiceFragment.newInstance(fragment2));
            }
        } else { //平台基地活动
            fragment2.putParcelable("scenicService",scenicDto.getScenicServiceDtos().get(0));
            fragment2.putParcelable("clubDto",scenicDto.getClubDto());
            fragment2.putBoolean("isPlatformActivity",true);
            fragmentList.add(ScenicSpecialServiceContentFragment.newInstance(fragment2));
        }



        tabLayout.setTabData(ctaDatas, this, R.id.scenicContent, fragmentList);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public boolean onTabSelect(int position) {
                if (!scenicDto.getClubDto().getClubType().equals("3") && position == 0){
                    if (fragmentManager != null && isPopBack){
                        fragmentManager.popBackStack();
                        isPopBack = false;
                    }
                }
                return true;
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    /**
     * 查看特色服务
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void specialService(SpecialServiceEvent event){
        Bundle bundle = getIntent().getExtras();
        bundle.putParcelable("scenicService",event.getScenicServiceDto());
        fragmentManager = getSupportFragmentManager();
        isPopBack = true;
        fragmentManager.beginTransaction().replace(R.id.scenicContent,ScenicSpecialServiceContentFragment.newInstance(bundle))
                .addToBackStack("special")
                .commit();
    }

    /**
     * 赚赏金
     * @param view
     */
    @OnClick(R.id.moreImage)
    public void more(View view){
        openMakeMoney();
    }

    private void openMakeMoney(){
        if (makeMoneyPop == null){
            View view = LayoutInflater.from(mInstance).inflate(R.layout.popup_window_scenic_right,null,false);
            LinearLayout makeMoney = view.findViewById(R.id.makeMoney);
            makeMoney.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogUtils.showShareDialog(mInstance,new DialogUtils.ShareDialogClickListener() {
                        @Override
                        public void clickWeixin() {
                            showToast(getString(R.string.notOpen));
                        }

                        @Override
                        public void clickPengyouQuan() {
                            showToast(getString(R.string.notOpen));
                        }

                        @Override
                        public void clickDownload() {
                            showToast(getString(R.string.notOpen));
                        }
                    });
                }
            });
            makeMoneyPop = new CustomPopWindow.PopupWindowBuilder(mInstance)
                    .setView(view)
                    .size(ConvertUtils.dp2px(110),ConvertUtils.dp2px(70))
                    .setFocusable(true)
                    .create();
            makeMoneyPop.showAsDropDown(moreImage);
        } else {
            makeMoneyPop.showAsDropDown(moreImage);
        }


    }

    @Override
    public void showScenicInfo(ScenicDto scenicDto) {
        setBannerInfo(mBanner,scenicDto);
        initCommonTabLayout(scenicDto);
    }

}
