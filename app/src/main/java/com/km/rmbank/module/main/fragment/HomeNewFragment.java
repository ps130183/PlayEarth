package com.km.rmbank.module.main.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.gongwen.marqueen.SimpleMF;
import com.gongwen.marqueen.SimpleMarqueeView;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseFragment;
import com.km.rmbank.dto.BannerDto;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.dto.HomeRecommendDto;
import com.km.rmbank.dto.MapMarkerDto;
import com.km.rmbank.entity.HomeRecommendEntity;
import com.km.rmbank.entity.ModelEntity;
import com.km.rmbank.event.ApplyActionEvent;
import com.km.rmbank.module.main.appoint.ActionPastDetailActivity;
import com.km.rmbank.module.main.appoint.ActionRecentInfoActivity;
import com.km.rmbank.module.main.fragment.home.AllClubActivity;
import com.km.rmbank.module.main.fragment.home.CircleFriendsActivity;
import com.km.rmbank.module.main.experience.ExperienceOfficerActivity;
import com.km.rmbank.module.main.fragment.home.InformationActivity;
import com.km.rmbank.module.main.fragment.home.MoreActionActivity;
import com.km.rmbank.module.main.fragment.home.ScenicListActivity;
import com.km.rmbank.module.main.map.MapActivity;
import com.km.rmbank.module.main.personal.member.club.ClubActivity;
import com.km.rmbank.module.main.shop.GoodsActivity;
import com.km.rmbank.module.webview.AgreementActivity;
import com.km.rmbank.mvp.model.HomeModel;
import com.km.rmbank.mvp.presenter.HomePresenter;
import com.km.rmbank.mvp.view.IHomeView;
import com.km.rmbank.utils.DateUtils;
import com.km.rmbank.utils.EventBusUtils;
import com.km.rmbank.utils.RefreshUtils;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.MultiItemTypeAdapter;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.ps.mrcyclerview.BViewHolder;
import com.ps.mrcyclerview.ItemViewConvert;
import com.ps.mrcyclerview.MRecyclerView;
import com.ps.mrcyclerview.click.OnClickItemListener;
import com.ruffian.library.RTextView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.DefaultImageLoader;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeNewFragment extends BaseFragment<IHomeView, HomePresenter> implements IHomeView {

    @BindView(R.id.toolBar)
    Toolbar mToolbar;
    @BindView(R.id.banner)
    Banner mBanner;

    //模块
    private String[] moduleNames = {"俱乐部", "动态", "人脉圈", "会所", "基地"};
    private int[] moduleImages = {R.mipmap.icon_home_module_club, R.mipmap.icon_home_module_information,
            R.mipmap.icon_home_module_friend_circle, R.mipmap.icon_home_module_huisuo, R.mipmap.icon_home_module_jidi};
    @BindView(R.id.moduleRecycler)
    MRecyclerView moduleRecycler;

    //首页推荐
    @BindView(R.id.recommendRecycler)
    RecyclerView recommendRecycler;
    private List<HomeRecommendDto> recommendDtoList;
    //跑马灯
    @BindView(R.id.simpleMarqueeView)
    SimpleMarqueeView<String> simpleMarqueeView;


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

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {
        RefreshUtils.initXRefreshView(mXRefreshView, new RefreshUtils.OnRefreshListener() {
            @Override
            public void refresh() {
                hideLoading();
            }
        });
        initToolbar();
        initModuleRecycler();
        initMarqueeView();
        initRecommend();
        initRefresh();
        getPresenter().getUserInfo();
    }


    //解决fragment中 marqueeView 切换 重影的问题
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){
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

    private void initToolbar() {
        mToolbar.inflateMenu(R.menu.toolbar_home_new_map);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                getPresenter().getMapMarkers();
                return false;
            }
        });
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
                        if ("1".equals(bannerDto.getType())){
                            bundle.putString("actionPastId",bannerDto.getId());
                            startActivity(ActionPastDetailActivity.class,bundle);
                        } else if ("2".equals(bannerDto.getType())){
                            bundle.putString("titleName",bannerDto.getTitle());
                            bundle.putString("agreementUrl",bannerDto.getLinkUrl());
                            startActivity(AgreementActivity.class,bundle);
                        } else if ("3".equals(bannerDto.getType())){
                            bundle.putString("actionId",bannerDto.getId());
                            startActivity(ActionRecentInfoActivity.class,bundle);
                        } else if ("4".equals(bannerDto.getType())){
                            bundle.putString("productNo",bannerDto.getId());
                            startActivity(GoodsActivity.class,bundle);
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
        List<Object> modelEntities = new ArrayList<>();
        for (String moduleName : moduleNames) {
            modelEntities.add(new ModelEntity("", moduleName));
        }
        moduleRecycler.addContentLayout(R.layout.item_home_new_module, new ItemViewConvert() {
            @Override
            public void convert(@NonNull BViewHolder holder, Object o, int position) {
                ModelEntity mData = (ModelEntity) o;
                holder.setText(R.id.moduleName, mData.getModelName());
                GlideImageView moduleImage = holder.findView(R.id.moduleImage);
                if (TextUtils.isEmpty(mData.getModelImageUrl())) {
                    GlideUtils.loadCircleImageByRes(moduleImage, moduleImages[position]);
                } else {
                    GlideUtils.loadImageOnPregress(moduleImage, mData.getModelImageUrl(), null);
                }

            }
        }).create();
        moduleRecycler.update(modelEntities);

        moduleRecycler.addClickItemListener(new OnClickItemListener() {
            @Override
            public void clickItem(Object o, int i) {
                Bundle bundle = new Bundle();
                switch (i) {
                    case 0://俱乐部
                        startActivity(AllClubActivity.class);
                        break;
                    case 1://资讯
                        startActivity(InformationActivity.class);
                        break;
                    case 2://人脉圈
//                        showToast(getResources().getString(R.string.notOpen));
                        startActivity(CircleFriendsActivity.class);
                        break;
                    case 3://会所
                        bundle.putInt("scenicType", 3);
                        startActivity(ScenicListActivity.class, bundle);
                        break;
                    case 4://基地
                        bundle.putInt("scenicType", 2);
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
        final List<String> datas = Arrays.asList("成功对接项目：1083个",
                "成功对接资本7.2亿元",
                "累计接待人数120万人次",
                "已合作商会和俱乐部累计42个",
                "现北京周边已开设基地达48个",
                "现北京城区已开设会所102个",
                "成功路演会员项目320余个",
                "累计合伙人数已经突破1500人",
                "商城累计售出商品价值超过30万元",
                "为1000余位会员找到了自己的客户");
//SimpleMarqueeView<T>，SimpleMF<T>：泛型T指定其填充的数据类型，比如String，Spanned等
//        SimpleMarqueeView<String> marqueeView = (SimpleMarqueeView) findViewById(R.id.simpleMarqueeView);
        SimpleMF<String> marqueeFactory = new SimpleMF<>(getContext());
        marqueeFactory.setData(datas);
        simpleMarqueeView.setMarqueeFactory(marqueeFactory);
        simpleMarqueeView.startFlipping();
    }

    /**
     * 首页推荐内容
     */
    private void initRecommend() {
        recommendDtoList = new ArrayList<>();
        RecyclerAdapterHelper<HomeRecommendEntity> mHelper = new RecyclerAdapterHelper<>(recommendRecycler);
        mHelper.addLinearLayoutManager()
                .addCommonAdapter(R.layout.item_home_recommend, recommendDtoList, new RecyclerAdapterHelper.CommonConvert<HomeRecommendDto>() {
                    @Override
                    public void convert(CommonViewHolder holder, final HomeRecommendDto mData, int position) {
                        holder.setText(R.id.recommendTitle, mData.getLevelName());
                        GlideImageView guanggaoWei = holder.findView(R.id.guanggaowei);
                        GlideUtils.loadImageOnPregress(guanggaoWei, mData.getAdvertImage(), null);
                        guanggaoWei.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if ("1".equals(mData.getUrlType())){//199体验官
                                    Bundle bundle = new Bundle();
                                    bundle.putString("advertUrl",mData.getAdvertUrl());
                                    startActivity(ExperienceOfficerActivity.class,bundle);
                                }
                            }
                        });
                        //更多
                        RTextView recommendMore = holder.findView(R.id.recommendMore);
                        recommendMore.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String type = mData.getType();
                                Bundle bundle = new Bundle();
                                bundle.putString("type",type);
                                switch (type){
                                    case "0100":
                                    case "0101":
                                    case "0102":
                                    case "0103":
                                    case "0104":
                                        startActivity(MoreActionActivity.class,bundle);
                                        break;

                                    case "0201":
                                        startActivity(AllClubActivity.class);
                                        break;
                                }
                            }
                        });

                        int contentLayoutRes = 0;
                        final String recommendType = mData.getType().substring(0,2);
                        switch (recommendType) {
                            case "01":
                                contentLayoutRes = R.layout.item_home_recommend_action;
                                break;
                            case "02":
                                contentLayoutRes = R.layout.item_home_recommend_club;
                                break;
                        }
                        if (contentLayoutRes == 0){
                            return;
                        }
                        //具体推荐的内容
                        final List<HomeRecommendDto.DetailListBean> contentList = mData.getDetailList();

                        RecyclerView contentRecycler = holder.findView(R.id.contentRecycler);
                        RecyclerAdapterHelper<String> contentHelper = new RecyclerAdapterHelper<>(contentRecycler);
                        contentHelper.addLinearLayoutManager()
                                .addCommonAdapter(contentLayoutRes, contentList, new RecyclerAdapterHelper.CommonConvert<HomeRecommendDto.DetailListBean>() {
                                    @Override
                                    public void convert(CommonViewHolder holder, HomeRecommendDto.DetailListBean mData, int position) {
                                        holder.addRippleEffectOnClick();
                                        View line = holder.findView(R.id.line);
                                        if (position == contentList.size() - 1) {
                                            line.setVisibility(View.GONE);
                                        } else {
                                            line.setVisibility(View.VISIBLE);
                                        }

                                        switch (recommendType) {
                                            case "01"://活动
                                                loadRecommendActionData(holder, mData,recommendType);
                                                break;
                                            case "02"://机构
                                                loadRecommendScenicData(holder, mData);
                                                break;
                                        }
                                    }
                                }).create();

                        contentHelper.getBasicAdapter().setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<HomeRecommendDto.DetailListBean>() {
                            @Override
                            public void onItemClick(CommonViewHolder holder, HomeRecommendDto.DetailListBean data, int position) {
                                Bundle bundle = new Bundle();
                                switch (recommendType) {
                                    case "01"://活动
                                        bundle.putString("actionId", data.getRelevanceId());
                                        startActivity(ActionRecentInfoActivity.class, bundle);
                                        break;
                                    case "02"://机构
                                        if (data.getType().equals("1")) {//俱乐部
                                            getPresenter().getClubInfo(data.getRelevanceId());
                                        }
                                        break;
                                }
                            }

                            @Override
                            public boolean onItemLongClick(CommonViewHolder holder, HomeRecommendDto.DetailListBean data, int position) {
                                return false;
                            }

                        });
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
        recommendDtoList.clear();
        recommendDtoList.addAll(recommendDtos);
        recommendRecycler.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showClubInfo(ClubDto clubDto) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("clubInfo", clubDto);
        startActivity(ClubActivity.class, bundle);
    }

    @Override
    public void applyActionSuccess(String actionId,String type) {
        showToast("报名成功");
        EventBusUtils.post(new ApplyActionEvent(actionId,type));
    }

    @Override
    public void showHomeBanner(List<BannerDto> bannerDtoList) {
        initBanner(bannerDtoList);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void applySuccess(ApplyActionEvent event){
        for (int i = 0; i < recommendDtoList.size(); i++){
            HomeRecommendDto recommendDto = recommendDtoList.get(i);
            if (event.getType().equals(recommendDto.getType())){
                List<HomeRecommendDto.DetailListBean> detailListBeans = recommendDto.getDetailList();
                for (int j = 0; j < detailListBeans.size(); j++){
                    HomeRecommendDto.DetailListBean detailListBean = detailListBeans.get(j);
                    if (event.getActionId().equals(detailListBean.getRelevanceId())){
                        int count = Integer.parseInt(detailListBean.getApplyCount());
                        detailListBean.setApplyCount((count + 1) + "");
                        recommendRecycler.getAdapter().notifyItemChanged(i);
                        return;
                    }
                }
            }
        }
    }
    /**
     * 加载推荐的活动数据
     *
     * @param holder
     * @param mData
     */
    private void loadRecommendActionData(CommonViewHolder holder, final HomeRecommendDto.DetailListBean mData, final String type) {
        GlideImageView contentImage = holder.findView(R.id.contentImage);
        GlideUtils.loadImageOnPregress(contentImage, mData.getThumbnail(), null);

        TextView actionName = holder.findView(R.id.actionName);
        actionName.setText(mData.getTitle());

        TextView actionTime = holder.findView(R.id.actionTime);
        actionTime.setText("举办时间：" + DateUtils.getInstance().getDate(mData.getStartDate()));

        TextView appliedPersonNum = holder.findView(R.id.appliedPersonNum);
        appliedPersonNum.setText(mData.getApplyCount());

//        RTextView rtApplyAction = holder.findView(R.id.rt_apply_action);
//        rtApplyAction.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Constant.userLoginInfo.isEmpty()) {
//                    showToast("请先登录，再报名");
//                    startActivity(LoginActivity.class);
//                    return;
//                }
//                long holdDate = DateUtils.getInstance().stringDateToMillis(DateUtils.getInstance().dateToString(new Date(mData.getStartDate()), DateUtils.YMDHM), DateUtils.YMDHM);
//                long curDate = System.currentTimeMillis();
//                if (curDate >= holdDate) {
//                    showToast("报名已截止");
//                    return;
//                }
//                getPresenter().applyAction(mData.getRelevanceId(), Constant.userInfo.getName(), Constant.userInfo.getMobilePhone(),type);
//            }
//        });
    }

    /**
     * 加载推荐的机构数据
     *
     * @param holder
     * @param mData
     */
    private void loadRecommendScenicData(CommonViewHolder holder, HomeRecommendDto.DetailListBean mData) {
        GlideImageView imageView = holder.findView(R.id.imageView);
        GlideUtils.loadImageOnPregress(imageView, mData.getThumbnail(), null);

        TextView clubName = holder.findView(R.id.clubName);
        clubName.setText(mData.getTitle());

        TextView introduce = holder.findView(R.id.introduce);
        introduce.setText(mData.getContent());

        TextView keepCount = holder.findView(R.id.keppCount);
        keepCount.setText(mData.getKeepCount());

        TextView fans = holder.findView(R.id.fans);
        fans.setText(mData.getFans());
    }
}
