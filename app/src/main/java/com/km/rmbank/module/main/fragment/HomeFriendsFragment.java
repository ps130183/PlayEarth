package com.km.rmbank.module.main.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseFragment;
import com.km.rmbank.dto.RecommendPersonalDto;
import com.km.rmbank.dto.ShareDto;
import com.km.rmbank.entity.ImageTextIntroduceEntity;
import com.km.rmbank.mvp.model.RecommendPersonalModel;
import com.km.rmbank.mvp.presenter.RecommendPersonalPresenter;
import com.km.rmbank.mvp.view.IRecommendPersonalView;
import com.km.rmbank.retrofit.ApiConstant;
import com.km.rmbank.utils.UmengShareUtils;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;
import com.ps.commonadapter.adapter.wrapper.HeaderAndFooterWrapper;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.ps.glidelib.progress.CircleProgressView;
import com.ruffian.library.RTextView;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFriendsFragment extends BaseFragment<IRecommendPersonalView,RecommendPersonalPresenter> implements IRecommendPersonalView {

    @BindView(R.id.recommendRecycler)
    RecyclerView recommendRecycler;
    private List<ImageTextIntroduceEntity> introduceEntities;
    private RecommendPersonalDto recommendPersonalDto;

    public static HomeFriendsFragment newInstance(Bundle bundle) {

        HomeFriendsFragment fragment = new HomeFriendsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_home_friends;
    }

    @Override
    protected RecommendPersonalPresenter createPresenter() {
        return new RecommendPersonalPresenter(new RecommendPersonalModel());
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {

        introduceEntities = new ArrayList<>();

        initRecycler();
        getPresenter().getRecommendPersons(1);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            getPresenter().getRecommendPersons(1);
        }
    }

    @Override
    public void showRecommendPersons(List<RecommendPersonalDto> recommendPersonalDtos) {
        if (recommendPersonalDtos != null && recommendPersonalDtos.size() > 0){
            recommendPersonalDto = recommendPersonalDtos.get(0);
            mViewManager.setText(R.id.tv_day,recommendPersonalDto.getDay());
            mViewManager.setText(R.id.tv_month_year,recommendPersonalDto.getYearMonth());
            mViewManager.setText(R.id.introduce,recommendPersonalDto.getTitle());
            notifyData(recommendPersonalDto);
        }

    }

    @Override
    public void pariseResult(String s) {
        String likeStatus = recommendPersonalDto.getLikeStatus();
        int likeCount = Integer.parseInt(recommendPersonalDto.getLikeCount());
        if (likeStatus.equals("0")){//未点赞
            recommendPersonalDto.setLikeStatus("1");
            recommendPersonalDto.setLikeCount((likeCount + 1) + "");
        } else {
            recommendPersonalDto.setLikeStatus("0");
            recommendPersonalDto.setLikeCount((likeCount - 1) + "");
        }
        recommendRecycler.getAdapter().notifyDataSetChanged();
    }

    private void initRecycler(){

        RecyclerAdapterHelper<ImageTextIntroduceEntity> mHelper = new RecyclerAdapterHelper<>(recommendRecycler);
        mHelper.addLinearLayoutManager().addCommonAdapter(R.layout.item_image_text_introduce, introduceEntities, new RecyclerAdapterHelper.CommonConvert<ImageTextIntroduceEntity>() {
            @Override
            public void convert(CommonViewHolder holder, ImageTextIntroduceEntity mData, int position) {
                GlideImageView imageView =  holder.findView(R.id.image);
                CircleProgressView progressView = holder.findView(R.id.progressView);
                GlideUtils.loadImageFitHeight(imageView,mData.getImageUrl(),progressView);

                holder.setText(R.id.content,mData.getContent());
            }
        }).addHeaderAndFooterWrapper(new Integer[]{R.layout.header_friends_recommend}, null, new HeaderAndFooterWrapper.LoadHeaderAndFooterData() {
            @Override
            public void loadHeaderData(CommonViewHolder holder, int position) {
                GlideUtils.loadImage(getContext(),recommendPersonalDto.getBackImage(),holder.getImageView(R.id.backgroundImage));
                RTextView tvPraise = holder.findView(R.id.tvPraise);
                tvPraise.setText(recommendPersonalDto.getLikeCount());
                if (recommendPersonalDto.getLikeStatus().equals("0")){
                    tvPraise.setIconNormal(getResources().getDrawable(R.mipmap.icon_home_friends_zan1));
                } else {
                    tvPraise.setIconNormal(getResources().getDrawable(R.mipmap.icon_home_friends_zan2));
                }
                tvPraise.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getPresenter().pariseRecommendPerson(recommendPersonalDto.getId());
                    }
                });

                holder.setText(R.id.userName,recommendPersonalDto.getPersonName() + " 丨 " + recommendPersonalDto.getPersonIntroduce());

                holder.findView(R.id.ivShare).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShareDto shareDto = new ShareDto();
                        shareDto.setTitle(recommendPersonalDto.getPersonName());
                        shareDto.setContent(recommendPersonalDto.getPersonIntroduce() + "邀请您点赞！！！");
                        shareDto.setSharePicUrl(recommendPersonalDto.getAtlasList().get(0).getImage());
                        shareDto.setPageUrl(recommendPersonalDto.getShareUrl());
                        share(shareDto);
                    }
                });
            }

            @Override
            public void loadFooterData(CommonViewHolder holder, int position) {

            }
        }).create();

    }

    private void notifyData(RecommendPersonalDto recommendPersonalDto){
        introduceEntities.clear();
        List<RecommendPersonalDto.AtlasListBean> atlasListBeans = recommendPersonalDto.getAtlasList();
        for (RecommendPersonalDto.AtlasListBean atlasListBean : atlasListBeans){
            introduceEntities.add(new ImageTextIntroduceEntity(atlasListBean.getContent(),atlasListBean.getImage()));
        }
        recommendRecycler.getAdapter().notifyDataSetChanged();
    }

    private void share(ShareDto shareDto){
        UmengShareUtils.openShare(getActivity(), shareDto, new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {

            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                LogUtils.d(throwable.getMessage());
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {

            }
        });
    }
}
