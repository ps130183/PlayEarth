package com.km.rmbank.module.main.personal.member.club;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseFragment;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.entity.ImageTextIntroduceEntity;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.ps.glidelib.progress.CircleProgressView;
import com.ps.mrcyclerview.BViewHolder;
import com.ps.mrcyclerview.ItemViewConvert;
import com.ps.mrcyclerview.MRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClubIntroduceFragment extends BaseFragment {

    @BindView(R.id.clubIntroduceRecycler)
    MRecyclerView<ImageTextIntroduceEntity> clubIntrduceRecycler;

    @BindView(R.id.jzv_player)
    JZVideoPlayerStandard jzvPlayer;

    public static ClubIntroduceFragment newInstance(Bundle bundle) {
        ClubIntroduceFragment fragment = new ClubIntroduceFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_club_introduce;
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {

        if (getArguments() == null){
            return;
        }
        ClubDto clubDto = getArguments().getParcelable("clubInfo");

        if (clubDto == null){
            return;
        }

        if (!TextUtils.isEmpty(clubDto.getVideoUrl())){
            jzvPlayer.setVisibility(View.VISIBLE);
            jzvPlayer.setUp(clubDto.getVideoUrl()
                    , JZVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
        }

        clubIntrduceRecycler.addContentLayout(R.layout.item_image_text_introduce, new ItemViewConvert<ImageTextIntroduceEntity>() {
            @Override
            public void convert(@NonNull BViewHolder holder, ImageTextIntroduceEntity mData, int position, @NonNull List<Object> payloads) {
                GlideImageView imageView =  holder.findView(R.id.image);
                CircleProgressView progressView = holder.findView(R.id.progressView);
                if (TextUtils.isEmpty(mData.getImageUrl())){
                    imageView.setVisibility(View.GONE);
                    progressView.setVisibility(View.GONE);
                } else {
                    imageView.setVisibility(View.VISIBLE);
                    progressView.setVisibility(View.VISIBLE);
                    GlideUtils.loadImageFitHeight(imageView,mData.getImageUrl(),progressView);
                }

                holder.setText(R.id.content,mData.getContent());
            }

        }).create();

        List<ImageTextIntroduceEntity> introduceEntities = new ArrayList<>();
        for (ClubDto.ClubDetailBean clubDetailBean : clubDto.getClubDetailList()){
            introduceEntities.add(new ImageTextIntroduceEntity(clubDetailBean.getClubContent(),clubDetailBean.getClubImage()));
        }
        clubIntrduceRecycler.loadDataOfNextPage(introduceEntities);
    }

}
