package com.km.rmbank.module.main.personal.task;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.dto.EarthTaskDetailsDto;
import com.km.rmbank.dto.EarthTaskDto;
import com.km.rmbank.dto.TaskSignInDto;
import com.km.rmbank.mvp.model.EarthTaskModel;
import com.km.rmbank.mvp.presenter.EarthTaskPresenter;
import com.km.rmbank.mvp.view.EarthTaskView;
import com.km.rmbank.utils.DateUtils;
import com.ps.mrcyclerview.BViewHolder;
import com.ps.mrcyclerview.ItemViewConvert;
import com.ps.mrcyclerview.MRecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EarthTaskDetailsActivity extends BaseActivity<EarthTaskView,EarthTaskPresenter> implements EarthTaskView {

    private MRecyclerView<EarthTaskDetailsDto> etdRecycler;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_eearth_task_details;
    }

    @Override
    public String getTitleContent() {
        return "球票明细";
    }

    @Override
    protected EarthTaskPresenter createPresenter() {
        return new EarthTaskPresenter(new EarthTaskModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initView();
    }

    private void initView(){
        etdRecycler = mViewManager.findView(R.id.etdRecycler);
        etdRecycler.addContentLayout(R.layout.item_earth_task_details, new ItemViewConvert<EarthTaskDetailsDto>() {
            @Override
            public void convert(@NonNull BViewHolder holder, EarthTaskDetailsDto mData, int position, @NonNull List<Object> payloads) {
                holder.setText(R.id.content,mData.getDescribe());
                holder.setText(R.id.time, DateUtils.getInstance().dateToString(new Date(mData.getCreateDate()),DateUtils.YMDHMS));
                holder.setText(R.id.addNumber,"+" + mData.getAmount());
            }
        }).create();
        getPresenter().getEarthTaskDetailList();
    }

    @Override
    public void showEarthTaskList(List<EarthTaskDto> earthTaskEntities) {

    }

    @Override
    public void signInResult(TaskSignInDto signInDto) {

    }

    @Override
    public void showEarthTaskDetailList(List<EarthTaskDetailsDto> taskDetailsDtos) {
        etdRecycler.loadDataOfNextPage(taskDetailsDtos);
    }
}
