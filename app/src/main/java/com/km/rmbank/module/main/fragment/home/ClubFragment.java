package com.km.rmbank.module.main.fragment.home;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestOptions;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseFragment;
import com.km.rmbank.dto.BannerDto;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.module.main.club.ActionRecentInfoActivity;
import com.km.rmbank.module.main.personal.member.BecomeMemberActivity;
import com.km.rmbank.module.main.personal.member.club.ClubActivity;
import com.km.rmbank.module.main.shop.GoodsActivity;
import com.km.rmbank.mvp.model.ClubModel;
import com.km.rmbank.mvp.presenter.ClubPresenter;
import com.km.rmbank.mvp.view.IClubView;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.MultiItemTypeAdapter;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;
import com.ps.commonadapter.adapter.wrapper.LoadMoreWrapper;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.ps.glidelib.progress.CircleProgressView;
import com.ps.glidelib.progress.OnGlideImageViewListener;
import com.ps.glidelib.progress.OnProgressListener;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.PilasterSideImageLoader;
import com.zhy.magicviewpager.transformer.ScaleInTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * 首页俱乐部
 */
public class ClubFragment extends BaseFragment<IClubView, ClubPresenter> implements IClubView, View.OnClickListener {

    private List<ClubDto> clubDtos;

    private Banner mBanner;
    private RecyclerView clubRecycler;

    @BindView(R.id.iv_recommendClub1)
    GlideImageView ivRecommendClub1;
    @BindView(R.id.cpv_recommend1)
    CircleProgressView cpvRecommend1;
    @BindView(R.id.iv_recommendClub2)
    GlideImageView ivRecommendClub2;
    @BindView(R.id.cpv_recommend2)
    CircleProgressView cpvRecommend2;

    @BindView(R.id.tv_recommendClub1)
    TextView tvRecommendClub1;
    @BindView(R.id.tv_recommendClub2)
    TextView tvRecommendClub2;

    private ClubDto clubDto1;
    private ClubDto clubDto2;

    private List<Integer> bannerUrls;
    private List<BannerDto> bannerDtos;

    public static ClubFragment newInstance(Bundle bundle) {

        ClubFragment fragment = new ClubFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected ClubPresenter createPresenter() {
        return new ClubPresenter(new ClubModel());
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_club;
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {
        clubDtos = new ArrayList<>();
        mBanner = mViewManager.findView(R.id.banner);
        clubRecycler = mViewManager.findView(R.id.rv_clubs);
        bannerUrls = new ArrayList<>();
        bannerDtos = new ArrayList<>();
        setRecommendClubList(clubRecycler);
        setBannerInfo(mBanner);
        mViewManager.setClickListener(R.id.allClub, this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        mBanner.stopAutoPlay();
    }

    /**
     * 设置轮播图
     *
     * @param banner
     */
    private void setBannerInfo(final Banner banner) {

        bannerUrls.add(R.mipmap.icon_banner_1);
        bannerUrls.add(R.mipmap.icon_banner_2);
        bannerUrls.add(R.mipmap.icon_banner_3);

        banner.setImages(bannerUrls)
                .isAutoPlay(true)
                .setPagerMargin(20)
                .setOffscreenPageLimit(3)
                .setPageTransformer(true, new ScaleInTransformer(0.9f))
                .setDelayTime(3000)
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        if (position != 2){
                            startActivity(BecomeMemberActivity.class);
                        }
                    }
                })
                .setImageLoader(new PilasterSideImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, CardView cardView) {
                        int imageRes = (int) path;
                        ImageView imageView = cardView.findViewById(R.id.banner_image);
                        if (imageView != null) {
                            GlideUtils.loadImage(context, imageRes, imageView);
                        } else {
                            showToast("请设置imageView");
                        }

                    }
                }).start();

    }

    /**
     * 设置推荐的俱乐部列表
     *
     * @param clubRecycler
     */
    private void setRecommendClubList(RecyclerView clubRecycler) {

        RecyclerAdapterHelper<ClubDto> mHelper = new RecyclerAdapterHelper<>(clubRecycler);
        mHelper.addLinearLayoutManager()
                .addCommonAdapter(R.layout.item_club, clubDtos, new RecyclerAdapterHelper.CommonConvert<ClubDto>() {
                    @Override
                    public void convert(CommonViewHolder holder, ClubDto mData, int position) {
                        holder.setText(R.id.clubName, mData.getClubName());
                        GlideImageView imageView = holder.findView(R.id.clubLogo);
                        CircleProgressView progressView = holder.findView(R.id.progressView);
                        GlideUtils.loadImageOnPregress(imageView,mData.getClubLogo(),progressView);
                        holder.setText(R.id.clubIntroduce, mData.getContent());
                    }
                }).addRefreshView(mXRefreshView)
                .addRefreshListener(new RecyclerAdapterHelper.OnRefreshListener() {
                    @Override
                    public void refresh() {
                        getPresenter().getRecommendClubs(1, null);
//                        getPresenter().getBannerList();
                    }
                })
                .addLoadMoreWrapper(new LoadMoreWrapper.OnLoadMoreListener() {
                    @Override
                    public void onLoadMoreRequest(LoadMoreWrapper wrapper, int nextPage) {
                        getPresenter().getRecommendClubs(nextPage, wrapper);
                    }
                })
                .create();
        showLoading();

        mHelper.getBasicAdapter().setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<ClubDto>() {
            @Override
            public void onItemClick(CommonViewHolder holder, ClubDto data, int position) {
                getPresenter().getClubInfo(data.getId());
            }

            @Override
            public boolean onItemLongClick(CommonViewHolder holder, ClubDto data, int position) {
                return false;
            }

        });
    }


    @Override
    public void showClubs(List<ClubDto> clubDtos, LoadMoreWrapper wrapper) {
        if (wrapper != null) {
            wrapper.setLoadMoreFinish(clubDtos.size());
        } else {
            this.clubDtos.clear();
            clubDto1 = clubDtos.get(0);
            clubDto2 = clubDtos.get(1);
            GlideUtils.loadImageOnPregress(ivRecommendClub1,clubDto1.getClubLogo(),cpvRecommend1);

            GlideUtils.loadImageOnPregress(ivRecommendClub2,clubDto2.getClubLogo(),cpvRecommend2);

            tvRecommendClub1.setText(clubDto1.getClubName());
            tvRecommendClub2.setText(clubDto2.getClubName());
            clubDtos.remove(clubDto1);
            clubDtos.remove(clubDto2);
        }
        this.clubDtos.addAll(clubDtos);
        hideLoading();
        clubRecycler.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showClubInfo(ClubDto clubDto) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("clubInfo", clubDto);
        startActivity(ClubActivity.class, bundle);
    }

    @Override
    public void showBannerList(List<BannerDto> bannerDtos) {
        this.bannerDtos.clear();
        this.bannerDtos.addAll(bannerDtos);
        this.bannerUrls.clear();
        for (BannerDto bannerDto : bannerDtos) {
//            bannerUrls.add(bannerDto.getBannerUrl());
        }
//        setBannerInfo(mBanner);
    }

    @Override
    public void attentionClubResult(String result) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.allClub:
                ActivityUtils.startActivity(AllClubActivity.class);
                break;
        }
    }

    /**
     * 打开俱乐部详情
     *
     * @param view
     */
    @OnClick({R.id.iv_recommendClub1, R.id.tv_recommendClub1, R.id.iv_recommendClub2, R.id.tv_recommendClub2})
    public void openClubInfo(View view) {
        switch (view.getId()) {
            case R.id.iv_recommendClub1:
            case R.id.tv_recommendClub1:
                if (clubDto1 != null) {
                    getPresenter().getClubInfo(clubDto1.getId());
                }
                break;
            case R.id.iv_recommendClub2:
            case R.id.tv_recommendClub2:
                if (clubDto2 != null) {
                    getPresenter().getClubInfo(clubDto2.getId());
                }
                break;
        }

    }

}
