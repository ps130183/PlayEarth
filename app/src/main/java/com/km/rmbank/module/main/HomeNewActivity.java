package com.km.rmbank.module.main;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.ImageView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.module.main.map.MapActivity;
import com.km.rmbank.module.main.personal.member.BecomeMemberActivity;
import com.km.rmbank.titleBar.HomeNewToolBar;
import com.ps.glidelib.GlideUtils;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.DefaultImageLoader;
import com.youth.banner.loader.PilasterSideImageLoader;
import com.zhy.magicviewpager.transformer.ScaleInTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeNewActivity extends BaseActivity {

    @BindView(R.id.banner)
    Banner mBanner;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_home_new;
    }

    @Override
    public BaseTitleBar getBaseTitleBar() {
        HomeNewToolBar toolBar = new HomeNewToolBar();
        toolBar.setOnMapClickListener(new HomeNewToolBar.OnMapClickListener() {
            @Override
            public void clickMap() {
                startActivity(MapActivity.class);
            }
        });
        return toolBar;
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initBanner();
    }

    private void initBanner(){
        List<Integer> bannerUrls = new ArrayList<>();
        bannerUrls.add(R.mipmap.icon_banner_1);
        bannerUrls.add(R.mipmap.icon_banner_2);
        bannerUrls.add(R.mipmap.icon_banner_3);

        mBanner.setImages(bannerUrls)
                .isAutoPlay(true)
                .setDelayTime(3000)
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        if (position != 2){
                            startActivity(BecomeMemberActivity.class);
                        }
                    }
                })
                .setImageLoader(new DefaultImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        int imageRes = (int) path;
                        GlideUtils.loadImage(context, imageRes, imageView);
                    }
                }).start();
    }

}
