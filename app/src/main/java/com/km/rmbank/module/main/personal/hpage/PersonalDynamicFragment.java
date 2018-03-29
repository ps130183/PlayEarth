package com.km.rmbank.module.main.personal.hpage;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ConvertUtils;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseFragment;
import com.km.rmbank.dto.PersonalDynamicDto;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.DividerItemDecoration;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.yancy.gallerypick.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * 个人动态
 */
public class PersonalDynamicFragment extends BaseFragment {


    public PersonalDynamicFragment() {
        // Required empty public constructor
    }

    public static PersonalDynamicFragment newInstance(Bundle bundle) {
        PersonalDynamicFragment fragment = new PersonalDynamicFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_dong_tai;
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {
        initRecycler();
    }

    private void initRecycler(){
        List<PersonalDynamicDto> personalDynamicDtos = new ArrayList<>();
        personalDynamicDtos.add(new PersonalDynamicDto());
        personalDynamicDtos.add(new PersonalDynamicDto());
        personalDynamicDtos.add(new PersonalDynamicDto());
        personalDynamicDtos.add(new PersonalDynamicDto());
        personalDynamicDtos.add(new PersonalDynamicDto());
        personalDynamicDtos.add(new PersonalDynamicDto());
        RecyclerView dynamicRecycler = mViewManager.findView(R.id.dynamicRecycler);
        RecyclerAdapterHelper<PersonalDynamicDto> mHelper = new RecyclerAdapterHelper<>(dynamicRecycler);
        mHelper.addLinearLayoutManager()
                .addDividerItemDecoration(DividerItemDecoration.VERTICAL_LIST,R.drawable.recycler_item_divider2)
                .addCommonAdapter(R.layout.item_personl_dynamic_image, personalDynamicDtos, new RecyclerAdapterHelper.CommonConvert<PersonalDynamicDto>() {
            @Override
            public void convert(CommonViewHolder holder, PersonalDynamicDto mData, int position) {

                RecyclerView imageRecycler = holder.findView(R.id.imageRecycler);
                List<Integer> imageRes = new ArrayList<>();
                for (int i = 0; i <= position; i++){
                    imageRes.add(R.mipmap.banner_dinner_party);
                }
                if (imageRes.size() > 0){
                    imageRecycler.setVisibility(View.VISIBLE);
                    final int spanCount;
                    if (imageRes.size() == 4){
                        spanCount = 2;
                        int width = ScreenUtils.getScreenWidth(getContext());
                        int imageWidth = (width - ConvertUtils.dp2px(30 + (spanCount - 1) * 6)) / 3;
                        imageRecycler.getLayoutParams().width = width - imageWidth - ConvertUtils.dp2px(12);
                    } else {
                        spanCount = 3;
                        imageRecycler.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
                    }
                    final RecyclerAdapterHelper<Integer> imageHelper = new RecyclerAdapterHelper<>(imageRecycler);
                    imageHelper.addGrigLayoutMnager(spanCount)
                            .addDividerItemDecoration(DividerItemDecoration.VERTICAL_LIST,R.drawable.recycler_item_divider3)
                            .addCommonAdapter(R.layout.item_php_dynamic_image, imageRes, new RecyclerAdapterHelper.CommonConvert<Integer>() {
                        @Override
                        public void convert(CommonViewHolder holder, Integer mData, int position) {
                            GlideImageView imageView = holder.findView(R.id.imageView);
                            GlideUtils.loadImageByRes(imageView,mData);

                            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) imageView.getLayoutParams();
                            int width = ScreenUtils.getScreenWidth(getContext());
                            int imageWidth = (width - ConvertUtils.dp2px(30 + (spanCount - 1) * 6)) / 3;
                            lp.height = imageWidth;
                            lp.width = imageWidth;
                            imageView.setLayoutParams(lp);
                        }
                    }).create();

                } else {
                    imageRecycler.setVisibility(View.GONE);
                }

            }
        }).create();
    }

}
