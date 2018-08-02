package com.km.rmbank.module.main.scenic;


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
public class ScenicIntroduceFragment extends BaseFragment {

    @BindView(R.id.introduceRecycler)
    MRecyclerView<ImageTextIntroduceEntity> introduceRecycler;

    @BindView(R.id.jzv_player)
    JZVideoPlayerStandard jzvPlayer;

    public static ScenicIntroduceFragment newInstance(Bundle bundle) {
        
        ScenicIntroduceFragment fragment = new ScenicIntroduceFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_scenic_introduce;
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {
        initRecycler();
    }

    private void initRecycler(){

        ClubDto clubDto = getArguments().getParcelable("scenicInfo");


        if (!TextUtils.isEmpty(clubDto.getVideoUrl())){
            jzvPlayer.setVisibility(View.VISIBLE);
            jzvPlayer.setUp(clubDto.getVideoUrl()
                    , JZVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
        }

        List<ImageTextIntroduceEntity> imageTextIntroduceEntities = new ArrayList<>();
        for (ClubDto.ClubDetailBean detailBean : clubDto.getClubDetailList()){
            ImageTextIntroduceEntity entity = new ImageTextIntroduceEntity(detailBean.getClubContent(),detailBean.getClubImage());
            imageTextIntroduceEntities.add(entity);
        }

        introduceRecycler.addContentLayout(R.layout.item_image_text_introduce, new ItemViewConvert<ImageTextIntroduceEntity>() {
            @Override
            public void convert(@NonNull BViewHolder holder, ImageTextIntroduceEntity mData, int position, @NonNull List<Object> payloads) {
                holder.setText(R.id.content,mData.getContent());
                GlideImageView imageView = holder.findView(R.id.image);
                CircleProgressView progressView = holder.findView(R.id.progressView);
                GlideUtils.loadImageOnPregress(imageView,mData.getImageUrl(),progressView);
            }
        }).create();

        introduceRecycler.loadDataOfNextPage(imageTextIntroduceEntities);
    }

}
