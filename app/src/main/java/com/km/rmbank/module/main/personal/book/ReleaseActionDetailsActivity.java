package com.km.rmbank.module.main.personal.book;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.ReleaseActionDetailsDto;
import com.km.rmbank.dto.ShareDto;
import com.km.rmbank.entity.BookVenueApplyDto;
import com.km.rmbank.entity.ImageEntity;
import com.km.rmbank.mvp.model.ReleaseActionModel;
import com.km.rmbank.mvp.presenter.ReleaseActionPresenter;
import com.km.rmbank.mvp.view.ReleaseActionView;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.DateUtils;
import com.km.rmbank.utils.UmengShareUtils;
import com.km.rmbank.utils.dialog.DialogUtils;
import com.km.rmbank.utils.dialog.WindowBottomDialog;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.ps.mrcyclerview.BViewHolder;
import com.ps.mrcyclerview.ItemViewConvert;
import com.ps.mrcyclerview.MRecyclerView;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kr.co.namee.permissiongen.PermissionGen;

public class ReleaseActionDetailsActivity extends BaseActivity<ReleaseActionView,ReleaseActionPresenter> implements ReleaseActionView {

    private SimpleTitleBar simpleTitleBar;
    private ReleaseActionDetailsDto mActionDetailsDto;
    private ShareDto mShareDto;
    private WindowBottomDialog mShareDialog;
    @Override
    public int getContentViewRes() {
        return R.layout.activity_release_action_details;
    }

    @Override
    public String getTitleContent() {
        return "活动详情";
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        super.onCreateTitleBar(titleBar);
        simpleTitleBar = (SimpleTitleBar) titleBar;
    }

    @Override
    protected ReleaseActionPresenter createPresenter() {
        return new ReleaseActionPresenter(new ReleaseActionModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initRecycler();
        String id = getIntent().getStringExtra("placeReservationId");
        getPresenter().getReleaseActionDetails(id);
    }

    private void initRecycler(){
        final MRecyclerView<ImageEntity> mRecyclerView = mViewManager.findView(R.id.imageRecycler);
        mRecyclerView.addLoadMoreFinishRes(0)
                .refreshRecycler()
                .addContentLayout(R.layout.item_select_image, new ItemViewConvert<ImageEntity>() {
                    @Override
                    public void convert(@NonNull BViewHolder holder, final ImageEntity mData, int position, @NonNull List<Object> payloads) {
                        GlideImageView imageView = holder.findView(R.id.iv_image);
                        GlideUtils.loadImageOnPregress(imageView,mData.getImagePath(),null);

                        holder.findView(R.id.iv_delete).setVisibility(View.GONE);
                    }

                }).create();

    }

    @Override
    public void releaseActionSuccess() {

    }

    @Override
    public void showReleaseActionDetails(final ReleaseActionDetailsDto releaseActionDetailsDto) {
        mActionDetailsDto = releaseActionDetailsDto;
        initShareDialog();
        mViewManager.setText(R.id.action_title,releaseActionDetailsDto.getTitle());
        mViewManager.setText(R.id.action_venue,releaseActionDetailsDto.getPlaceTitle());
        mViewManager.setText(R.id.action_address,releaseActionDetailsDto.getAddress());
        mViewManager.setText(R.id.action_time, DateUtils.getInstance().dateToString(new Date(releaseActionDetailsDto.getStartDate()),DateUtils.YMDHM) + "  至\n"
                                            + DateUtils.getInstance().dateToString(new Date(releaseActionDetailsDto.getEndDate()),DateUtils.YMDHM));

        String[] imageUrls = releaseActionDetailsDto.getContent().split("#");
        List<ImageEntity> imageEntities = new ArrayList<>();
        for (String imageUrl : imageUrls){
            imageEntities.add(new ImageEntity(imageUrl));
        }
        MRecyclerView<ImageEntity> mRecyclerView = mViewManager.findView(R.id.imageRecycler);
        mRecyclerView.loadDataOfNextPage(imageEntities);

        mViewManager.setText(R.id.text_content,releaseActionDetailsDto.getFlow());

        TextView tvStatus = mViewManager.findView(R.id.status);
        switch (releaseActionDetailsDto.getActivityType()){
            case "1"://通过
                tvStatus.setText("已通过");
                tvStatus.setTextColor(getResources().getColor(R.color.venue_passed));

                simpleTitleBar.setRightMenuContent("活动统计");
                simpleTitleBar.setRightMenuClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("actionId",releaseActionDetailsDto.getId());
                        startActivity(ActionApplyStatisticsActivity.class,bundle);
                    }
                });
                simpleTitleBar.createTitleBar(mViewManager);

                if (System.currentTimeMillis() - releaseActionDetailsDto.getEndDate() < 0){//未结束
                    mViewManager.findView(R.id.resverRelease).setVisibility(View.VISIBLE);
                    mViewManager.setText(R.id.resverRelease,"邀请好友参加");
                }

                break;
            case "2"://拒绝
                tvStatus.setText("已拒绝");
                tvStatus.setTextColor(getResources().getColor(R.color.venue_not_passed));

                WebView reason = mViewManager.findView(R.id.reason);
                reason.loadData(releaseActionDetailsDto.getReason(),"text/html; charset=UTF-8", null);
                mViewManager.findView(R.id.line).setVisibility(View.VISIBLE);
                mViewManager.findView(R.id.resverRelease).setVisibility(View.VISIBLE);
                break;
            case "3"://审核中
                tvStatus.setText("审核中");
                tvStatus.setTextColor(getResources().getColor(R.color.venue_apply));
                break;
        }
    }

    /**
     * 重新发布活动
     * @param view
     */
    public void resverRelease(View view) {
        if ("2".equals(mActionDetailsDto.getActivityType())){//拒绝
            Bundle bundle = getIntent().getExtras();
            startActivity(ReleaseActionActivity.class,bundle);
            finish();
        } else if ("1".equals(mActionDetailsDto.getActivityType())){//通过
            mShareDialog.show();
        }

    }

    private void initShareDialog() {

        String imageUrl = getIntent().getStringExtra("imageUrl");
        mShareDto = new ShareDto();
        mShareDto.setTitle(Constant.userInfo.getName() + "邀请你参加" + mActionDetailsDto.getTitle());
        mShareDto.setContent("活动时间：" + DateUtils.getInstance().dateToString(new Date(mActionDetailsDto.getStartDate()), DateUtils.YMDHM) + "\n" +
                "活动地址：" + mActionDetailsDto.getAddress());
        mShareDto.setSharePicUrl(imageUrl);
        mShareDto.setPageUrl(mActionDetailsDto.getWebActivityUrl());
        mShareDialog = new WindowBottomDialog(mInstance, "取消", "分享微信好友", "分享到朋友圈");
        mShareDialog.setOnClickShareDialog(new WindowBottomDialog.OnClickShareDialog() {
            @Override
            public void clickShareDialog(String itemName, int i) {
                mShareDialog.dimiss();
                if (i == 0) {
                    UmengShareUtils.openShare(mInstance, mShareDto, SHARE_MEDIA.WEIXIN);
                } else if (i == 1) {
                    UmengShareUtils.openShare(mInstance, mShareDto, SHARE_MEDIA.WEIXIN_CIRCLE);
                }
            }
        });
    }
}
