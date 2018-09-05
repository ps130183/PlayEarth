package com.km.rmbank.module.main.fragment;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.dalong.carrousellayout.CarrouselLayout;
import com.gongwen.marqueen.SimpleMF;
import com.gongwen.marqueen.SimpleMarqueeView;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseFragment;
import com.km.rmbank.dto.AdvertisDto;
import com.km.rmbank.dto.BannerDto;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.dto.HomeRecommendDto;
import com.km.rmbank.dto.MapMarkerDto;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.entity.ModelEntity;
import com.km.rmbank.event.ApplyActionEvent;
import com.km.rmbank.module.main.appoint.ActionOutdoorActivity;
import com.km.rmbank.module.main.appoint.ActionPastDetailActivity;
import com.km.rmbank.module.main.appoint.ActionRecentInfoActivity;
import com.km.rmbank.module.main.appoint.AppointAfternoonTeaActivity;
import com.km.rmbank.module.main.card.UserCardActivity;
import com.km.rmbank.module.main.fragment.home.AllClubActivity;
import com.km.rmbank.module.main.experience.ExperienceOfficerActivity;
import com.km.rmbank.module.main.fragment.home.HomeRecommendCompanyDetailsActivity;
import com.km.rmbank.module.main.fragment.home.HomeRecommendMemberActivity;
import com.km.rmbank.module.main.fragment.home.InformationActivity;
import com.km.rmbank.module.main.fragment.home.MoreActionActivity;
import com.km.rmbank.module.main.fragment.home.ScenicListActivity;
import com.km.rmbank.module.main.map.MapActivity;
import com.km.rmbank.module.main.message.MessageActivity;
import com.km.rmbank.module.main.personal.member.club.ClubActivity;
import com.km.rmbank.module.main.shop.GoodsActivity;
import com.km.rmbank.module.webview.AgreementActivity;
import com.km.rmbank.module.webview.WebBrowserActivity;
import com.km.rmbank.mvp.model.HomeModel;
import com.km.rmbank.mvp.presenter.HomePresenter;
import com.km.rmbank.mvp.view.IHomeView;
import com.km.rmbank.retrofit.ApiConstant;
import com.km.rmbank.utils.DateUtils;
import com.km.rmbank.utils.EventBusUtils;
import com.km.rmbank.utils.RefreshUtils;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.ps.mrcyclerview.BViewHolder;
import com.ps.mrcyclerview.ItemViewConvert;
import com.ps.mrcyclerview.MRecyclerView;
import com.ps.mrcyclerview.click.OnClickItemListener;
import com.ruffian.library.RTextView;
import com.yancy.gallerypick.utils.ScreenUtils;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.CustomLoader;
import com.youth.banner.loader.DefaultImageLoader;
import com.youth.banner.transformer.ScaleInOutTransformer;
import com.youth.banner.transformer.ThreePagerTransformer;
import com.zhy.magicviewpager.transformer.ScaleInTransformer;
import com.zhy.view.flowlayout.FlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeNewFragment extends BaseFragment<IHomeView, HomePresenter> implements IHomeView {

    //    @BindView(R.id.toolBar)
//    Toolbar mToolbar;
    @BindView(R.id.banner)
    Banner mBanner;

    //模块
    private String[] moduleNames = {"活动", "俱乐部", "基地", "会所"};
    private int[] moduleImages = {R.mipmap.icon_home_module_information, R.mipmap.icon_home_module_club,
            R.mipmap.icon_home_module_jidi, R.mipmap.icon_home_module_huisuo};
    @BindView(R.id.moduleRecycler)
    MRecyclerView<ModelEntity> moduleRecycler;

    //首页推荐
    @BindView(R.id.recommendRecycler)
    MRecyclerView<HomeRecommendDto> recommendRecycler;
    //跑马灯
    @BindView(R.id.simpleMarqueeView)
    SimpleMarqueeView<String> simpleMarqueeView;

    private CarrouselLayout mCarrouse;

    public HomeNewFragment() {
        // Required empty public constructor
    }

    public static HomeNewFragment newInstance(Bundle bundle) {

        HomeNewFragment fragment = new HomeNewFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(new HomeModel());
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_home_new;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {
        RefreshUtils.initXRefreshView(mXRefreshView, new RefreshUtils.OnRefreshListener() {
            @Override
            public void refresh() {
                hideLoading();
            }
        });
        initToolbar();
        initAction199();
        initModuleRecycler();
        initMarqueeView();
        initRecommend();
        initRefresh();
        initScrollViewListener();
        getPresenter().getUserInfo();
    }


    //解决fragment中 marqueeView 切换 重影的问题
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            simpleMarqueeView.stopFlipping();
        } else {
            simpleMarqueeView.startFlipping();
        }
    }

    private void initRefresh() {
        RefreshUtils.initXRefreshView(mXRefreshView, new RefreshUtils.OnRefreshListener() {
            @Override
            public void refresh() {
                getPresenter().getHomeRecommend();
                getPresenter().getHomeBannerList();
            }
        });
        showLoading();
    }

    private void initAction199(){
        ImageView actionImage = mViewManager.findView(R.id.openAction199);
        int width = com.blankj.utilcode.util.ScreenUtils.getScreenWidth();
        int heith = width / 75 * 14;
        actionImage.getLayoutParams().height = heith;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initScrollViewListener() {
        final Toolbar toolbar = mViewManager.findView(R.id.toolBar);
        NestedScrollView scrollView = mViewManager.findView(R.id.scrollView);
        toolbar.getBackground().mutate().setAlpha(0);
        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                LogUtils.d("scrollY ==== " + scrollY + "   oldScrollY ==== " + oldScrollY);
                int height = ConvertUtils.dp2px(152);
                float scale = 0;
                if (scrollY - height >= 0) {
                    scale = 1;
                } else {
                    scale = (float) (scrollY * 1.0 / height);
                }
                toolbar.getBackground().setAlpha((int) (scale * 255));
            }
        });

        ImageView messageView = mViewManager.findView(R.id.message);
        messageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MessageActivity.class);
            }
        });
    }

    private void initToolbar() {
//        mToolbar.inflateMenu(R.menu.toolbar_home_message);
//        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//
//                return false;
//            }
//        });
    }

    /**
     * 初始化轮播图
     */
    private void initBanner(final List<BannerDto> bannerDtoList) {

        mBanner.setImages(bannerDtoList)
                .isAutoPlay(true)
                .setDelayTime(3000)
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        BannerDto bannerDto = bannerDtoList.get(position);
                        Bundle bundle = new Bundle();
                        if ("1".equals(bannerDto.getType())) {
                            bundle.putString("actionPastId", bannerDto.getId());
                            startActivity(ActionPastDetailActivity.class, bundle);
                        } else if ("2".equals(bannerDto.getType())) {
                            bundle.putString("titleName", bannerDto.getTitle());
                            bundle.putString("imageUrl", bannerDto.getImageUrl());
//                            String webUrl = ApiConstant.API_BASE_URL + ApiConstant.API_MODEL + "/accounts/banner/index.html";
                            bundle.putString("webUrl", bannerDto.getLinkUrl());
                            startActivity(WebBrowserActivity.class, bundle);
                        } else if ("3".equals(bannerDto.getType())) {
                            bundle.putString("actionId", bannerDto.getId());
                            startActivity(ActionRecentInfoActivity.class, bundle);
                        } else if ("4".equals(bannerDto.getType())) {
                            bundle.putString("productNo", bannerDto.getId());
                            startActivity(GoodsActivity.class, bundle);
                        }
                    }
                })
                .setImageLoader(new DefaultImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        BannerDto bannerDto = (BannerDto) path;
                        GlideUtils.loadImage(context, bannerDto.getImageUrl(), imageView);
                    }
                }).start();
    }

    /**
     * 初始化模块
     */
    private void initModuleRecycler() {
        List<ModelEntity> modelEntities = new ArrayList<>();
        for (String moduleName : moduleNames) {
            ModelEntity entity = new ModelEntity("", moduleName);
            entity.setItemLayoutRes(R.layout.item_home_new_module);
            modelEntities.add(entity);
        }
        moduleRecycler.addContentLayout(R.layout.item_home_new_module, new ItemViewConvert<ModelEntity>() {
            @Override
            public void convert(@NonNull BViewHolder holder, ModelEntity mData, int position, @NonNull List<Object> payloads) {
                holder.setText(R.id.moduleName, mData.getModelName());
                GlideImageView moduleImage = holder.findView(R.id.moduleImage);
                if (TextUtils.isEmpty(mData.getModelImageUrl())) {
                    GlideUtils.loadCircleImageByRes(moduleImage, moduleImages[position]);
                } else {
                    GlideUtils.loadImageOnPregress(moduleImage, mData.getModelImageUrl(), null);
                }
            }
        }).create();
        moduleRecycler.loadDataOfNextPage(modelEntities);

        moduleRecycler.addClickItemListener(new OnClickItemListener() {
            @Override
            public void clickItem(Object o, int i) {
                Bundle bundle = new Bundle();
                switch (i) {
                    case 0://俱乐部
                        startActivity(InformationActivity.class);
                        break;
                    case 1://资讯
                        startActivity(AllClubActivity.class);
                        break;
//                    case 2://人脉圈
////                        showToast(getResources().getString(R.string.notOpen));
//                        startActivity(CircleFriendsActivity.class);
//                        break;
                    case 2://会所
                        bundle.putInt("scenicType", 2);
                        startActivity(ScenicListActivity.class, bundle);
                        break;
                    case 3://基地
                        bundle.putInt("scenicType", 3);
                        startActivity(ScenicListActivity.class, bundle);
                        break;

                }
            }
        });
    }


    /**
     * 初始化跑马灯
     */
    private void initMarqueeView() {
        LogUtils.d("homenewFragment initmarqueeView");
        final List<String> datas = Arrays.asList("累计会员10800余位",
                "合作商会与俱乐部500多个",
                "对接全球项目15个");
//SimpleMarqueeView<T>，SimpleMF<T>：泛型T指定其填充的数据类型，比如String，Spanned等
//        SimpleMarqueeView<String> marqueeView = (SimpleMarqueeView) findViewById(R.id.simpleMarqueeView);
        SimpleMF<String> marqueeFactory = new SimpleMF<>(getContext());
        marqueeFactory.setData(datas);
        simpleMarqueeView.setMarqueeFactory(marqueeFactory);
        simpleMarqueeView.startFlipping();
    }

    /**
     * 199广告
     *
     * @param view
     */
    @OnClick(R.id.openAction199)
    public void openAction199(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("advertUrl", ApiConstant.API_BASE_URL + ApiConstant.API_MODEL + "/accounts/advertUrl");
        startActivity(ExperienceOfficerActivity.class, bundle);
    }

    /**
     * 首页推荐内容
     */
    private void initRecommend() {
        recommendRecycler.addContentLayout(R.layout.item_home_recommend, new ItemViewConvert<HomeRecommendDto>() {
            @Override
            public void convert(@NonNull BViewHolder holder, final HomeRecommendDto mData, int position, @NonNull List<Object> payloads) {
                holder.setText(R.id.recommendTitle, mData.getLevelName());

                //更多
                final RTextView recommendMore = holder.findView(R.id.recommendMore);
                if ("0300".equals(mData.getType()) || "0400".equals(mData.getType())) {
                    recommendMore.setVisibility(View.GONE);
                } else {
                    recommendMore.setVisibility(View.VISIBLE);
                }
                recommendMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String type = mData.getType();
                        Bundle bundle = new Bundle();
                        bundle.putString("type", type);
                        switch (type) {
                            case "0100":
                            case "0101":
                            case "0102":
                            case "0103":
                            case "0104":
                                startActivity(MoreActionActivity.class, bundle);
                                break;

                            case "0201":
                                startActivity(AllClubActivity.class);
                                break;
                        }
                    }
                });

                int contentLayoutRes = 0;
                final String recommendType = mData.getType().substring(0, 2);
                switch (recommendType) {
                    case "01":
                        contentLayoutRes = R.layout.item_home_recommend_action;
                        break;
                    case "02":
                        contentLayoutRes = R.layout.item_home_recommend_club;
                        break;
                    case "03":
                        contentLayoutRes = R.layout.item_home_recommend_member;
                        break;

                    case "04":
                        contentLayoutRes = R.layout.item_home_recommend_company;
                        break;
                }
                if (contentLayoutRes == 0) {
                    return;
                }
                //具体推荐的内容
                final List<HomeRecommendDto.DetailListBean> contentList = mData.getDetailList();
                FrameLayout flContent = holder.findView(R.id.content);
                if (!recommendType.equals("03")) {
                    MRecyclerView<HomeRecommendDto.DetailListBean> contentRecycler = (MRecyclerView<HomeRecommendDto.DetailListBean>) LayoutInflater.from(getActivity()).inflate(R.layout._default_recycler, null, false);
                    if ("02".equals(recommendType)) {
                        contentRecycler.setLmType(MRecyclerView.LM_GRID)
                                .setSpanCount(3)
                                .setDividerWidth(0)
                                .setDividerColor(getResources().getColor(R.color.white))
                                .refreshRecycler();

                    } else {
                        contentRecycler.setLmType(MRecyclerView.LM_LINEAR)
                                .setDividerWidth(1)
                                .refreshRecycler();
                    }
                    if (!recommendType.equals("03")) {
                        contentRecycler
                                .addContentLayout(contentLayoutRes, new ItemViewConvert<HomeRecommendDto.DetailListBean>() {
                                    @Override
                                    public void convert(@NonNull BViewHolder holder, HomeRecommendDto.DetailListBean mData, int position, @NonNull List<Object> payloads) {
                                        holder.addRippleEffectOnClick();

                                        switch (recommendType) {
                                            case "01"://活动
                                                loadRecommendActionData(holder, mData, recommendType);
                                                break;
                                            case "02"://机构
                                                loadRecommendScenicData(holder, mData);
                                                break;
                                            case "03"://会员
//                                loadRecommendMemeber(holder, mData);
                                                break;
                                            case "04"://企业
                                                loadRecommendComapny(holder, mData);
                                                break;
                                        }
                                    }

                                }).create();


                        contentRecycler.loadDataOfNextPage(contentList);

                        contentRecycler.addClickItemListener(new OnClickItemListener() {
                            @Override
                            public void clickItem(Object mData, int position) {
                                HomeRecommendDto.DetailListBean data = (HomeRecommendDto.DetailListBean) mData;
                                Bundle bundle = new Bundle();
                                switch (recommendType) {
                                    case "01"://活动
                                        if ("0".equals(data.getIsDynamic())) {
                                            if ("1".equals(data.getType()) || "2".equals(data.getType())) {//下午茶  结缘晚宴
                                                bundle.putString("actionId", data.getActivityId());
                                                startActivity(AppointAfternoonTeaActivity.class, bundle);
                                            } else if ("4".equals(data.getType())) {//路演大会
                                                bundle.putString("actionId", data.getRelevanceId());
                                                startActivity(ActionRecentInfoActivity.class, bundle);
                                            } else {//基地活动 3
                                                bundle.putString("activityId", data.getActivityId());
                                                bundle.putString("scenicId", data.getClubId());
                                                startActivity(ActionOutdoorActivity.class, bundle);
                                            }
                                        } else {
                                            bundle.putString("activityId",data.getId());
                                            startActivity(ActionPastDetailActivity.class,bundle);
                                        }

                                        break;
                                    case "02"://机构
                                        if (data.getType().equals("1")) {//俱乐部
                                            getPresenter().getClubInfo(data.getRelevanceId());
                                        }
                                        break;
                                    case "03"://会员
                                        break;
                                    case "04":
                                        bundle.putString("id", data.getRelevanceId());
                                        startActivity(HomeRecommendCompanyDetailsActivity.class, bundle);
                                        break;
                                }
                            }
                        });
                    }
                    flContent.addView(contentRecycler);
                } else {
                    View view = LayoutInflater.from(getActivity()).inflate(R.layout.header_home_recommend_member, null, false);
                    flContent.addView(view);

                    Banner banner = view.findViewById(R.id.banner);
                    banner.getLayoutParams().height = ConvertUtils.dp2px(190);
                    banner.setImages(contentList)
                            .isAutoPlay(true)
                            .setDelayTime(3000)
                            .setOffscreenPageLimit(3)
                            .setPagerMargin(20)
                            .setPageTransformer(false, new ScaleInTransformer(0.8f))
                            .setOnBannerListener(new OnBannerListener() {
                                @Override
                                public void OnBannerClick(int position) {
//                                    showToast(contentList.get(position).getTitle() + "  " + contentList.get(position).getRelevanceId());
                                    getPresenter().getUserCardById((contentList.get(position)).getRelevanceId());
                                }
                            })
                            .setImageLoader(new CustomLoader() {
                                @Override
                                public void displayImage(Context context, Object path, FrameLayout imageView) {
                                    HomeRecommendDto.DetailListBean bean = (HomeRecommendDto.DetailListBean) path;
                                    View itemView = LayoutInflater.from(context).inflate(R.layout.item_home_recommend_member, null, false);
                                    loadRecommendMemeber(itemView, bean);
                                    imageView.addView(itemView);
                                }
                            }).start();
                }

            }

        }).create();
    }

    @Override
    public void showMapMarkerResult(List<MapMarkerDto> mapMarkerDtos) {
        Bundle bundle = new Bundle();

        bundle.putParcelableArrayList("mapMarkers", (ArrayList<? extends Parcelable>) mapMarkerDtos);
        startActivity(MapActivity.class, bundle);
    }

    @Override
    public void showHomeRecommend(List<HomeRecommendDto> recommendDtos) {
        recommendRecycler.clear();
        for (HomeRecommendDto recommendDto : recommendDtos) {
            int contentLayoutRes = 0;
            final String recommendType = recommendDto.getType().substring(0, 2);
            switch (recommendType) {
                case "01":
                    contentLayoutRes = R.layout.item_home_recommend_action;
                    break;
                case "02":
                    contentLayoutRes = R.layout.item_home_recommend_club;
                    break;
                case "03":
                    contentLayoutRes = R.layout.item_home_recommend_member;
                    break;

                case "04":
                    contentLayoutRes = R.layout.item_home_recommend_company;
                    break;
            }

            if (recommendDto.getDetailList() != null) {
                for (HomeRecommendDto.DetailListBean listBean : recommendDto.getDetailList()) {
                    listBean.setLayoutRes(contentLayoutRes);
                }
            } else {
                recommendDto.setDetailList(new ArrayList<HomeRecommendDto.DetailListBean>());
            }
        }

        recommendRecycler.loadDataOfNextPage(recommendDtos);
    }

    @Override
    public void showClubInfo(ClubDto clubDto) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("clubInfo", clubDto);
        startActivity(ClubActivity.class, bundle);
    }

    @Override
    public void applyActionSuccess(String actionId, String type) {
        showToast("报名成功");
        EventBusUtils.post(new ApplyActionEvent(actionId, type));
    }

    @Override
    public void showHomeBanner(List<BannerDto> bannerDtoList) {
        initBanner(bannerDtoList);
    }

    @Override
    public void showHomeAdvert(AdvertisDto advertisDto) {

    }

    @Override
    public void showUserCard(UserInfoDto cardDto) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("userCard", cardDto);
        startActivity(UserCardActivity.class, bundle);
    }

    /**
     * 加载推荐的活动数据
     *
     * @param holder
     * @param mData
     */
    private void loadRecommendActionData(BViewHolder holder, final HomeRecommendDto.DetailListBean mData, final String type) {
        GlideImageView contentImage = holder.findView(R.id.contentImage);
        GlideUtils.loadImageOnPregress(contentImage, mData.getThumbnail(), null);

        TextView actionName = holder.findView(R.id.actionName);
        actionName.setText(mData.getTitle());

        TextView actionTime = holder.findView(R.id.actionTime);
        actionTime.setText("举办时间：" + DateUtils.getInstance().getDate(mData.getStartDate()));

        TextView appliedPersonNum = holder.findView(R.id.appliedPersonNum);
        appliedPersonNum.setText(mData.getApplyCount());
    }

    /**
     * 加载推荐的机构数据
     *
     * @param holder
     * @param mData
     */
    private void loadRecommendScenicData(BViewHolder holder, HomeRecommendDto.DetailListBean mData) {
        GlideImageView imageView = holder.findView(R.id.imageView);
        GlideUtils.loadImageOnPregress(imageView, mData.getThumbnail(), null);

        TextView clubName = holder.findView(R.id.clubName);
        clubName.setText(mData.getTitle());
    }

    /**
     * 加载推荐的会员信息
     *
     * @param view
     * @param mData
     */
    private void loadRecommendMemeber(View view, HomeRecommendDto.DetailListBean mData) {
        GlideImageView userPortrait = view.findViewById(R.id.userPortrait);
        GlideUtils.loadImageOnPregress(userPortrait, mData.getThumbnail(), null);

        TextView name = view.findViewById(R.id.name);
        name.setText(mData.getTitle());
        TextView company = view.findViewById(R.id.company);
        company.setText(mData.getContent());
//        holder.setText(R.id.name,mData.getTitle());
//        holder.setText(R.id.company,mData.getContent());
    }

    private void loadRecommendComapny(BViewHolder holder, HomeRecommendDto.DetailListBean mData) {
        GlideImageView userPortrait = holder.findView(R.id.imageView);
        GlideUtils.loadImageOnPregress(userPortrait, mData.getThumbnail(), null);

        holder.setText(R.id.companyName, mData.getTitle());
        holder.setText(R.id.companyMain, "负责人：" + mData.getContent());

        FlowLayout flowLayout = holder.findView(R.id.flowLayout);
        String[] flows = mData.getClassifyName().split("#");
        for (String flow : flows) {
            TextView textView = (TextView) LayoutInflater.from(getContext())
                    .inflate(R.layout.flowlayout_item, null, false);
            textView.setText(flow);
            flowLayout.addView(textView);
        }
    }
}
