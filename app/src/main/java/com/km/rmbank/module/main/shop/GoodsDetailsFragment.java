package com.km.rmbank.module.main.shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseFragment;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.ps.glidelib.progress.CircleProgressView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by kamangkeji on 17/3/17.
 */

public class GoodsDetailsFragment extends BaseFragment {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;

    public static GoodsDetailsFragment newInstance(Bundle bundle) {
        GoodsDetailsFragment fragment = new GoodsDetailsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public int getLayoutRes() {
        return R.layout.fragment_goods_details;
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {
        List<String> goodsDetails = getArguments().getStringArrayList("goodsDetails");
        initRecyclerView(goodsDetails);
    }

    private void initRecyclerView(List<String> goodsDetails){

        RecyclerAdapterHelper<String> mHelper = new RecyclerAdapterHelper<>(mRecyclerview);
        mHelper.addLinearLayoutManager()
                .addCommonAdapter(R.layout.item_rv_goods_details, goodsDetails, new RecyclerAdapterHelper.CommonConvert<String>() {
            @Override
            public void convert(CommonViewHolder holder, String mData, int position) {
                GlideImageView imageView =  holder.findView(R.id.iv_goods_details);
                CircleProgressView progressView = holder.findView(R.id.progressView);
                GlideUtils.loadImageFitHeight(imageView,mData,progressView);
            }
        }).create();

    }


}
