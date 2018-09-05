package com.km.rmbank.module.main.personal.book;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.dto.ActionMemberDto;
import com.km.rmbank.mvp.model.ActionJoinMemberModel;
import com.km.rmbank.mvp.presenter.ActionJoinMemberPresenter;
import com.km.rmbank.mvp.view.IActionJoinMemberView;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.ps.mrcyclerview.BViewHolder;
import com.ps.mrcyclerview.ItemViewConvert;
import com.ps.mrcyclerview.MRecyclerView;
import com.ps.mrcyclerview.utils.RefreshUtils;

import java.util.List;

public class ActionApplyStatisticsActivity extends BaseActivity<IActionJoinMemberView,ActionJoinMemberPresenter> implements IActionJoinMemberView {

    private TextView personNumber;
    @Override
    public int getContentViewRes() {
        return R.layout.activity_action_apply_statistics;
    }

    @Override
    protected ActionJoinMemberPresenter createPresenter() {
        return new ActionJoinMemberPresenter(new ActionJoinMemberModel());
    }

    @Override
    public String getTitleContent() {
        return "活动统计";
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initRecycler();
    }

    private void initRecycler(){
        MRecyclerView<ActionMemberDto> mRecyclerView = mViewManager.findView(R.id.recyclerView);
        mRecyclerView.addContentLayout(R.layout.item_action_apply_statistics, new ItemViewConvert<ActionMemberDto>() {
            @Override
            public void convert(@NonNull BViewHolder holder, ActionMemberDto mData, int position, @NonNull List<Object> payloads) {
                GlideImageView imageView = holder.findView(R.id.imageView);
                GlideUtils.loadImageOnPregress(imageView,mData.getHeadImage(),null);

                holder.setText(R.id.userName,mData.getRegistrationName() + " | " + mData.getUserPosition());
                holder.setText(R.id.userPhone,mData.getRegistrationPhone());
                holder.setText(R.id.userCompany,mData.getUserCompany());
            }

        }).addHeaderLayout(R.layout.header_action_apply_statistics, new ItemViewConvert() {
            @Override
            public void convert(@NonNull BViewHolder holder, Object mData, int position, @NonNull List payloads) {
                personNumber = holder.findView(R.id.person_number);
            }
        }).create();

        mRecyclerView.addRefreshListener(new RefreshUtils.OnRefreshListener() {
            @Override
            public void refresh() {
                String actionId = getIntent().getStringExtra("actionId");
                getPresenter().getActionMemberList(actionId,1);
            }
        });
        mRecyclerView.startRefresh();
    }

    @Override
    public void showActionMemberList(List<ActionMemberDto> actionMemberDtos, int pageNo) {
        personNumber.setText(actionMemberDtos.size() + "");
        MRecyclerView<ActionMemberDto> mRecyclerView = mViewManager.findView(R.id.recyclerView);
        mRecyclerView.clear();
        mRecyclerView.stopRefresh();
        mRecyclerView.loadDataOfNextPage(actionMemberDtos);
    }

    @Override
    public void showActionMemberNum(String number) {

    }
}
