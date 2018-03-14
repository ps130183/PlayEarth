package com.km.rmbank.module.main;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.Glide;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.entity.ModelEntity;
import com.km.rmbank.module.main.map.MapActivity;
import com.km.rmbank.module.main.personal.member.BecomeMemberActivity;
import com.km.rmbank.titleBar.HomeNewToolBar;
import com.km.rmbank.utils.RefreshUtils;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.DefaultImageLoader;
import com.youth.banner.loader.PilasterSideImageLoader;
import com.zhy.magicviewpager.transformer.ScaleInTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 新版首页 5.0
 */
public class HomeNewActivity extends BaseActivity {

    @BindView(R.id.banner)
    Banner mBanner;

    //模块
    private String[] moduleNames = {"俱乐部","资讯","人脉圈","会所","基地"};
    private int[] moduleImageReses = {R.mipmap.icon_home_module_club,R.mipmap.icon_home_module_information,
    R.mipmap.icon_home_module_friend_circle,R.mipmap.icon_home_module_huisuo,R.mipmap.icon_home_module_jidi};
    @BindView(R.id.moduleRecycler)
    RecyclerView moduleRecycler;

    //首页推荐
    @BindView(R.id.recommendRecycler)
    RecyclerView recommendRecycler;

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
        RefreshUtils.initXRefreshView(mXRefreshView);
        initBanner();
        initModuleRecycler();
        initRecommend();
    }

    /**
     * 初始化轮播图
     */
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

    /**
     * 初始化模块
     */
    private void initModuleRecycler(){
        List<ModelEntity> modelEntities = new ArrayList<>();
        for (String moduleName : moduleNames){
            modelEntities.add(new ModelEntity("",moduleName));
        }
        RecyclerAdapterHelper<ModelEntity> mHelper = new RecyclerAdapterHelper<>(moduleRecycler);
        mHelper.addGrigLayoutMnager(5)
                .addCommonAdapter(R.layout.item_home_new_module, modelEntities, new RecyclerAdapterHelper.CommonConvert<ModelEntity>() {
            @Override
            public void convert(CommonViewHolder holder, ModelEntity mData, int position) {
                holder.setText(R.id.moduleName,mData.getModelName());
                GlideImageView moduleImage = holder.findView(R.id.moduleImage);
                if (TextUtils.isEmpty(mData.getModelImageUrl())){
                    GlideUtils.loadCircleImageByRes(moduleImage,moduleImageReses[position]);
                } else {
                    GlideUtils.loadCircleImageByUrl(moduleImage,mData.getModelImageUrl());
                }
            }
        }).create();
    }

    private void initRecommend(){
        List<String> mdatas = new ArrayList<>();
        mdatas.add("");
        mdatas.add("");
        RecyclerAdapterHelper<String> mHelper = new RecyclerAdapterHelper<>(recommendRecycler);
        mHelper.addLinearLayoutManager()
                .addCommonAdapter(R.layout.item_home_recommend, mdatas, new RecyclerAdapterHelper.CommonConvert<String>() {
            @Override
            public void convert(CommonViewHolder holder, String mData, int position) {

            }
        }).create();
    }

}
