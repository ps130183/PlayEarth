package com.km.rmbank.module.main.fragment.home;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.dto.RecommendMemberDto;
import com.ps.mrcyclerview.BViewHolder;
import com.ps.mrcyclerview.ItemViewConvert;
import com.ps.mrcyclerview.MRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeRecommendMemberActivity extends BaseActivity {

    @Override
    public int getContentViewRes() {
        return R.layout.activity_home_recommend_member;
    }

    @Override
    public String getTitleContent() {
        return "会员";
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initRecycler();
    }

    private void initRecycler(){
        MRecyclerView<RecommendMemberDto> mRecyclerView = mViewManager.findView(R.id.recyclerView);
        mRecyclerView.addContentLayout(R.layout.item_home_recommend_more, new ItemViewConvert<RecommendMemberDto>() {
            @Override
            public void convert(@NonNull BViewHolder holder, RecommendMemberDto mData, int position, @NonNull List<Object> payloads) {

            }
        }).create();

        List<RecommendMemberDto> memberDtos = new ArrayList<>();
        memberDtos.add(new RecommendMemberDto());
        memberDtos.add(new RecommendMemberDto());
        memberDtos.add(new RecommendMemberDto());
        memberDtos.add(new RecommendMemberDto());
        memberDtos.add(new RecommendMemberDto());
        memberDtos.add(new RecommendMemberDto());
        memberDtos.add(new RecommendMemberDto());
        memberDtos.add(new RecommendMemberDto());
        mRecyclerView.loadDataOfNextPage(memberDtos);
    }
}
