package com.km.rmbank.module.main;

import android.content.Context;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.gongwen.marqueen.SimpleMF;
import com.gongwen.marqueen.SimpleMarqueeView;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.entity.HomeRecommendEntity;
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
import java.util.Arrays;
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
    private int[] moduleImages = {R.mipmap.icon_home_module_club,R.mipmap.icon_home_module_information,
    R.mipmap.icon_home_module_friend_circle,R.mipmap.icon_home_module_huisuo,R.mipmap.icon_home_module_jidi};
    @BindView(R.id.moduleRecycler)
    RecyclerView moduleRecycler;

    //首页推荐
    @BindView(R.id.recommendRecycler)
    RecyclerView recommendRecycler;

    //跑马灯
    @BindView(R.id.simpleMarqueeView)
    SimpleMarqueeView<String> simpleMarqueeView;

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
        RefreshUtils.initXRefreshView(mXRefreshView, new RefreshUtils.OnRefreshListener() {
            @Override
            public void refresh() {
                hideLoading();
            }
        });
        initBanner();
        initModuleRecycler();
        initMarqueeView();
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
                    GlideUtils.loadCircleImageByRes(moduleImage,moduleImages[position]);
                } else {
                    GlideUtils.loadImageOnPregress(moduleImage,mData.getModelImageUrl(),null);
                }
            }
        }).create();
    }


    /**
     * 初始化跑马灯
     */
    private void initMarqueeView(){
        final List<String> datas = Arrays.asList("《赋得古原草送别》", "离离原上草，一岁一枯荣。", "野火烧不尽，春风吹又生。", "远芳侵古道，晴翠接荒城。", "又送王孙去，萋萋满别情。");
//SimpleMarqueeView<T>，SimpleMF<T>：泛型T指定其填充的数据类型，比如String，Spanned等
        SimpleMarqueeView<String> marqueeView = (SimpleMarqueeView) findViewById(R.id.simpleMarqueeView);
        SimpleMF<String> marqueeFactory = new SimpleMF<>(this);
        marqueeFactory.setData(datas);
        marqueeView.setMarqueeFactory(marqueeFactory);
        marqueeView.startFlipping();
    }

    /**
     * 首页推荐内容
     */
    private void initRecommend(){
        final List<HomeRecommendEntity> mdatas = new ArrayList<>();
        mdatas.add(new HomeRecommendEntity("推荐活动",1,R.mipmap.guanggao_home1));
        mdatas.add(new HomeRecommendEntity("推荐俱乐部",2,R.mipmap.guanggao_home2));
        RecyclerAdapterHelper<HomeRecommendEntity> mHelper = new RecyclerAdapterHelper<>(recommendRecycler);
        mHelper.addLinearLayoutManager()
                .addCommonAdapter(R.layout.item_home_recommend, mdatas, new RecyclerAdapterHelper.CommonConvert<HomeRecommendEntity>() {
            @Override
            public void convert(CommonViewHolder holder, HomeRecommendEntity mData, int position) {
                holder.setText(R.id.recommendTitle,mData.getRecommendName());
                GlideImageView guanggaoWei = holder.findView(R.id.guanggaowei);
                GlideUtils.loadImageByRes(guanggaoWei,mData.getGuangGaoWei());

                int contentLayoutRes = 0;
                final int recommendType = mData.getRecommendType();
                switch (recommendType){
                    case 1:
                        contentLayoutRes = R.layout.item_home_recommend_action;
                        break;
                    case 2:
                        contentLayoutRes = R.layout.item_home_recommend_club;
                        break;
                }
                //具体推荐的内容

                final List<String> contentList = new ArrayList<>();
                contentList.add("");
                contentList.add("");
                RecyclerView contentRecycler = holder.findView(R.id.contentRecycler);
                RecyclerAdapterHelper<String> contentHelper = new RecyclerAdapterHelper<>(contentRecycler);
                contentHelper.addLinearLayoutManager()
                        .addCommonAdapter(contentLayoutRes, contentList, new RecyclerAdapterHelper.CommonConvert<String>() {
                    @Override
                    public void convert(CommonViewHolder holder, String mData, int position) {
                        View line = holder.findView(R.id.line);
                        if (position == contentList.size() - 1){
                            line.setVisibility(View.GONE);
                        } else {
                            line.setVisibility(View.VISIBLE);
                        }
                    }
                }).create();
            }
        }).create();
    }

}
