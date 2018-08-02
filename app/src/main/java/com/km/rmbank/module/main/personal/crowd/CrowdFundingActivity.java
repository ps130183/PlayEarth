package com.km.rmbank.module.main.personal.crowd;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.dto.CrowdFundingPersonsDto;
import com.km.rmbank.dto.MyCrowdFundingInfoDto;
import com.km.rmbank.dto.ShareDto;
import com.km.rmbank.mvp.model.CrowdFundingModel;
import com.km.rmbank.mvp.presenter.CrowdFundingPresenter;
import com.km.rmbank.mvp.view.CrowdFundingView;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.DateUtils;
import com.km.rmbank.utils.UmengShareUtils;
import com.km.rmbank.utils.dialog.WindowBottomDialog;
import com.ps.glidelib.progress.CircleProgressView;
import com.ps.mrcyclerview.BViewHolder;
import com.ps.mrcyclerview.ItemViewConvert;
import com.ps.mrcyclerview.MRecyclerView;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CrowdFundingActivity extends BaseActivity<CrowdFundingView,CrowdFundingPresenter> implements CrowdFundingView {

    private MRecyclerView<CrowdFundingPersonsDto> mRecyclerView;

    private TextView sumAmount;
    private TextView sumPrice;
    private CircleProgressView progressView;
    private TextView personNum;

    private WindowBottomDialog mShareDialog;
    private ShareDto shareDto;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_crowd_funding;
    }

    @Override
    protected CrowdFundingPresenter createPresenter() {
        return new CrowdFundingPresenter(new CrowdFundingModel());
    }

    @Override
    public String getTitleContent() {
        return "我的众筹";
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initRecycler();
        initShareDialog();
    }

    private void initRecycler(){
        mRecyclerView = mViewManager.findView(R.id.recyclerView);

        mRecyclerView.addHeaderLayout(R.layout.header_crowd_funding, new ItemViewConvert() {
            @Override
            public void convert(@NonNull BViewHolder holder, Object mData, int position, @NonNull List payloads) {
                holder.setText(R.id.userName, Constant.userInfo.getName());
                sumAmount = holder.findView(R.id.sumAmount);
                sumPrice = holder.findView(R.id.sumPrice);
                progressView = holder.findView(R.id.progress);
                personNum = holder.findView(R.id.person_number);
            }
        }).addContentLayout(R.layout.item_crowd_funding_persons, new ItemViewConvert<CrowdFundingPersonsDto>() {
            @Override
            public void convert(@NonNull BViewHolder holder, CrowdFundingPersonsDto mData, int position, @NonNull List<Object> payloads) {
                holder.setText(R.id.name,mData.getUserName());
                holder.setText(R.id.price,mData.getPrice());
                holder.setText(R.id.date, DateUtils.getInstance().dateToString(new Date(mData.getCreateDate())));
            }

        }).create();

        getPresenter().getMyCrowdFundingInfo();
    }

    private void initShareDialog(){
        mShareDialog = new WindowBottomDialog(mInstance,"取消","分享微信好友");
        mShareDialog.setOnClickShareDialog(new WindowBottomDialog.OnClickShareDialog() {
            @Override
            public void clickShareDialog(String itemName, int i) {
                if (i == 0 && shareDto != null){
                    UmengShareUtils.openShare(mInstance,shareDto, SHARE_MEDIA.WEIXIN);
                }
                mShareDialog.dimiss();
            }
        });
    }

    @Override
    public void showMyCrowdFundingInfo(MyCrowdFundingInfoDto crowdFundingInfoDto) {
        mRecyclerView.loadDataOfNextPage(crowdFundingInfoDto.getUserCrowdFundingList());
        sumAmount.setText(crowdFundingInfoDto.getSumAmount()+"");
        sumPrice.setText(crowdFundingInfoDto.getSumPrice()+"");
        personNum.setText(crowdFundingInfoDto.getPersonNum());

        int progress = (int) (crowdFundingInfoDto.getSumPrice() / crowdFundingInfoDto.getSumAmount() * 100);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressView.setProgress(progress,true);
        } else {
            progressView.setProgress(progress);
        }
        shareDto = new ShareDto();
        shareDto.setTitle(crowdFundingInfoDto.getTitle());
        shareDto.setContent(crowdFundingInfoDto.getContent());
        shareDto.setPageUrl(crowdFundingInfoDto.getLinkUrl());
        shareDto.setSharePicUrl(crowdFundingInfoDto.getImgUrl());

    }

    /**
     * 分享 众筹
     * @param view
     */
    public void crowdFunding(View view) {
        if (mShareDialog != null){
            mShareDialog.show();
        }
    }
}
