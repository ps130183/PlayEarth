package com.km.rmbank.module.main.card;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.IndustryDto;
import com.km.rmbank.event.SelectIndustryEvent;
import com.km.rmbank.mvp.model.IndustryModel;
import com.km.rmbank.mvp.presenter.IndustryPresenter;
import com.km.rmbank.mvp.view.IndustryView;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.EventBusUtils;
import com.ps.mrcyclerview.BViewHolder;
import com.ps.mrcyclerview.ItemViewConvert;
import com.ps.mrcyclerview.MRecyclerView;
import com.ps.mrcyclerview.click.OnClickItemListener;

import java.util.ArrayList;
import java.util.List;

public class SelectIndustryActivity extends BaseActivity<IndustryView,IndustryPresenter> implements IndustryView {

    private List<IndustryDto> industryDtoList;
    private int curLevel = 0;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_select_industry;
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setTitleContent("行业选择");
        simpleTitleBar.setRightMenuContent("确定");
        simpleTitleBar.setRightMenuClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int industryType = getIntent().getIntExtra("industryType",0);
                int position = getIntent().getIntExtra("position",-1);
                MRecyclerView<IndustryDto> industryRecycler = mViewManager.findView(R.id.industryRecycler);
                List<IndustryDto> industryDtos = industryRecycler.getAllDatas();
                IndustryDto industry = null;
                for (IndustryDto industryDto : industryDtos){
                    if(industryDto.isChecked()){
                        industry = industryDto;
                        break;
                    }
                }
                if (industry == null){
                    showToast("请选择所属行业！");
                    return;
                }
                EventBusUtils.post(new SelectIndustryEvent(industry,industryType,position));
                finish();
            }
        });
    }

    @Override
    protected IndustryPresenter createPresenter() {
        return new IndustryPresenter(new IndustryModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initRecycler();
        setOnClickBackLisenter(new OnClickBackLisenter() {
            @Override
            public boolean onClickBack() {
                if (curLevel > 0){
                    curLevel--;
                    MRecyclerView<IndustryDto> industryRecycler = mViewManager.findView(R.id.industryRecycler);
                    industryRecycler.clear();
                    industryRecycler.loadDataOfNextPage(industryDtoList);
                } else {
                    finish();
                }
                return false;
            }
        });
    }

    @Override
    public View.OnClickListener getLeftClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (curLevel > 0){
                    curLevel--;
                    MRecyclerView<IndustryDto> industryRecycler = mViewManager.findView(R.id.industryRecycler);
                    industryRecycler.clear();
                    industryRecycler.loadDataOfNextPage(industryDtoList);
                } else {
                    finish();
                }
            }
        };
    }

    private void initRecycler(){
        final MRecyclerView<IndustryDto> industryRecycler = mViewManager.findView(R.id.industryRecycler);
        industryRecycler.addContentLayout(R.layout.item_industry, new ItemViewConvert<IndustryDto>() {
            @Override
            public void convert(@NonNull BViewHolder holder, IndustryDto mData, int position, @NonNull List<Object> payloads) {
                holder.setText(R.id.industryName,mData.getName());

                CheckBox checkBox = holder.findView(R.id.checkbox);
                checkBox.setChecked(mData.isChecked());
                checkBox.setVisibility(mData.isChecked() ? View.VISIBLE : View.GONE);
            }
        }).addHeaderLayout(R.layout.header_space_divider_10, new ItemViewConvert() {
            @Override
            public void convert(@NonNull BViewHolder holder, Object mData, int position, @NonNull List payloads) {

            }
        }).create();

        industryRecycler.addClickItemListener(new OnClickItemListener() {
            @Override
            public void clickItem(Object mData, int position) {
                IndustryDto industryDto = (IndustryDto) mData;
                if (curLevel == 0){//第一级
                    for (IndustryDto industryDto1 : industryDtoList){
                        industryDto1.setChecked(false);
                    }
                    industryDto.setChecked(true);
                    List<IndustryDto> industryDtos = industryDto.getIndustryList();
                    for (IndustryDto industryDto1 : industryDtos){
                        industryDto1.setChecked(false);
                    }
                    industryRecycler.clear();
                    industryRecycler.loadDataOfNextPage(industryDtos);
                    curLevel++;
                } else {
                    List<IndustryDto> industryDtos = industryRecycler.getAllDatas();
                    int industryPosition = -1;
                    for (int i = 0; i < industryDtos.size(); i++){
                        IndustryDto industryDto1 = industryDtos.get(i);
                        if (industryDto1.isChecked()){
                            industryDto1.setChecked(false);
                            industryPosition = i;
                            break;
                        }
                    }
                    industryDto.setChecked(true);
                    industryRecycler.update(industryDto,position,null);
                    if (industryPosition >= 0){
                        industryRecycler.update(industryDtos.get(industryPosition),industryPosition,null);
                    }
                }
            }
        });

        getPresenter().getIndustryList();
    }

    @Override
    public void showIndustryList(List<IndustryDto> industryDtos) {
        if (industryDtoList == null){
            industryDtoList = new ArrayList<>();
        }
        industryDtoList.clear();
        industryDtoList.addAll(industryDtos);
        MRecyclerView<IndustryDto> industryRecycler = mViewManager.findView(R.id.industryRecycler);
        industryRecycler.clear();
        industryRecycler.loadDataOfNextPage(industryDtoList);
    }
}
