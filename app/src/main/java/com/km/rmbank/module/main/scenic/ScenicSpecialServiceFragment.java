package com.km.rmbank.module.main.scenic;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseFragment;
import com.km.rmbank.dto.ScenicServiceDto;
import com.km.rmbank.entity.SpecialServiceEntity;
import com.km.rmbank.event.SpecialServiceEvent;
import com.km.rmbank.utils.EventBusUtils;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.MultiItemTypeAdapter;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScenicSpecialServiceFragment extends BaseFragment {

    @BindView(R.id.specialRecycler)
    RecyclerView specialRecycler;

    public static ScenicSpecialServiceFragment newInstance(Bundle bundle) {
        ScenicSpecialServiceFragment fragment = new ScenicSpecialServiceFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_scenic_special;
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {
        initRecycler();
    }

    private void initRecycler(){
        List<ScenicServiceDto> scenicServiceDtos = getArguments().getParcelableArrayList("scenicServiceList");
        if (scenicServiceDtos == null){
            return;
        }

        RecyclerAdapterHelper<ScenicServiceDto> mHelper = new RecyclerAdapterHelper<>(specialRecycler);

        mHelper.addLinearLayoutManager()
                .addDividerItemDecoration(LinearLayoutManager.VERTICAL)
                .addCommonAdapter(R.layout.item_special_service, scenicServiceDtos, new RecyclerAdapterHelper.CommonConvert<ScenicServiceDto>() {
            @Override
            public void convert(CommonViewHolder holder, ScenicServiceDto mData, int position) {
                holder.addRippleEffectOnClick();
                TextView price = holder.getTextView(R.id.price_person);
                price.setText(Html.fromHtml("<font color='#ea6f5a'>¥ " + mData.getPrice() + "</font>/人"));
                holder.setText(R.id.title,mData.getName());
            }
        }).create();

        mHelper.getBasicAdapter().setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<ScenicServiceDto>() {
            @Override
            public void onItemClick(CommonViewHolder holder, ScenicServiceDto data, int position) {
                EventBusUtils.post(new SpecialServiceEvent(data));
            }

            @Override
            public boolean onItemLongClick(CommonViewHolder holder, ScenicServiceDto data, int position) {
                return false;
            }
        });
    }

}
