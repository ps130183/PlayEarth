package com.km.rmbank.module.main.personal.member.club;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseFragment;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.entity.ImageTextIntroduceEntity;
import com.km.rmbank.event.ImageTextInfoEvent;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.ps.glidelib.progress.CircleProgressView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClubIntroduceFragment extends BaseFragment {

    @BindView(R.id.clubIntroduceRecycler)
    RecyclerView clubIntrduceRecycler;

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
        List<ImageTextIntroduceEntity> introduceEntities = new ArrayList<>();
        for (ClubDto.ClubDetailBean clubDetailBean : clubDto.getClubDetailList()){
            introduceEntities.add(new ImageTextIntroduceEntity(clubDetailBean.getClubContent(),clubDetailBean.getClubImage()));
        }
        RecyclerAdapterHelper<ImageTextIntroduceEntity> mHelper = new RecyclerAdapterHelper<>(clubIntrduceRecycler);
        mHelper.addLinearLayoutManager()
                .addCommonAdapter(R.layout.item_image_text_introduce, introduceEntities, new RecyclerAdapterHelper.CommonConvert<ImageTextIntroduceEntity>() {
            @Override
            public void convert(CommonViewHolder holder, ImageTextIntroduceEntity mData, int position) {

                GlideImageView imageView =  holder.findView(R.id.image);
                CircleProgressView progressView = holder.findView(R.id.progressView);
                GlideUtils.loadImageFitHeight(imageView,mData.getImageUrl(),progressView);

                holder.setText(R.id.content,mData.getContent());
            }
        }).create();

    }




}
