package com.km.rmbank.module.main.club;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestOptions;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.ActionPastDto;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.dto.ShareDto;
import com.km.rmbank.entity.ImageTextIntroduceEntity;
import com.km.rmbank.module.main.personal.member.club.ClubActivity;
import com.km.rmbank.mvp.model.ActionPastDetailModel;
import com.km.rmbank.mvp.presenter.ActionPastDetailPresenter;
import com.km.rmbank.mvp.view.IActionPastDetailView;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.DateUtils;
import com.km.rmbank.utils.UmengShareUtils;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.ps.glidelib.progress.CircleProgressView;
import com.ps.glidelib.progress.OnGlideImageViewListener;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class ActionPastDetailActivity extends BaseActivity<IActionPastDetailView,ActionPastDetailPresenter> implements IActionPastDetailView {

    @BindView(R.id.simple_tb_title_name)
    TextView tvTitle;

    @BindView(R.id.tv_action_name)
    TextView tvActionName;
    @BindView(R.id.tv_club_name)
    TextView tvClubName;
    @BindView(R.id.iv_club_logo)
    ImageView ivClubLogo;
    @BindView(R.id.tv_release_time)
    TextView tvReleaseTime;
    @BindView(R.id.rv_action_past_details)
    RecyclerView rvActionPastDetails;

    @BindView(R.id.jzv_player)
    JZVideoPlayerStandard jzvPlayer;


    private String actionPastId;

    private String clubId;
    private boolean isMyClub;

    private ShareDto mShareDto;

    private SimpleTitleBar simpleTitleBar;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_action_past_detail;
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        simpleTitleBar = (SimpleTitleBar) titleBar;
    }

    @Override
    protected ActionPastDetailPresenter createPresenter() {
        return new ActionPastDetailPresenter(new ActionPastDetailModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        actionPastId = getIntent().getStringExtra("actionPastId");
        String activityId = getIntent().getStringExtra("activityId");
        isMyClub = getIntent().getBooleanExtra("isMyClub",false);
//        initActionPastDetails();
        getPresenter().getActionPastDetails(actionPastId,activityId);

        if (!isMyClub){
            simpleTitleBar.setRightMenuRes(R.menu.toolbar_action_recent_share);
            simpleTitleBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (mShareDto == null){
                        showToast("获取分享内容失败");
                        return false;
                    }
                    UmengShareUtils.openShare(ActionPastDetailActivity.this, mShareDto, new UMShareListener() {
                        @Override
                        public void onStart(SHARE_MEDIA share_media) {

                        }

                        @Override
                        public void onResult(SHARE_MEDIA share_media) {
                            showToast("分享成功");
                        }

                        @Override
                        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                            showToast("分享失败");
                        }

                        @Override
                        public void onCancel(SHARE_MEDIA share_media) {

                        }
                    });
                    return false;
                }
            });
        }
    }

    @Override
    public void showActionPastDetails(final ActionPastDto actionPastDto) {
        clubId = actionPastDto.getClubId();
        tvTitle.setText(actionPastDto.getTitle());
        tvActionName.setText(actionPastDto.getTitle());
        tvTitle.setText(actionPastDto.getTitle());
        tvClubName.setText(TextUtils.isEmpty(actionPastDto.getClubName()) ? "玩转地球商旅学苑" : actionPastDto.getClubName());
        GlideUtils.loadImage(mInstance,actionPastDto.getClubLogo(),ivClubLogo);
        tvReleaseTime.setText("发布时间：" + DateUtils.getInstance().getDate(actionPastDto.getCreateDate()));

        List<ImageTextIntroduceEntity> imageTextIntroduceEntities = new ArrayList<>();
        for (ActionPastDto.DetailListBean bean : actionPastDto.getDetailList()){
            imageTextIntroduceEntities.add(new ImageTextIntroduceEntity(bean.getDynamicImageContent(),bean.getDynamicImage()));
        }
        RecyclerAdapterHelper<ImageTextIntroduceEntity> mHelper = new RecyclerAdapterHelper<>(rvActionPastDetails);
        mHelper.addLinearLayoutManager().addCommonAdapter(R.layout.item_image_text_introduce, imageTextIntroduceEntities, new RecyclerAdapterHelper.CommonConvert<ImageTextIntroduceEntity>() {
            @Override
            public void convert(CommonViewHolder holder, ImageTextIntroduceEntity mData, int position) {
                holder.setText(R.id.content,mData.getContent());
                holder.getTextView(R.id.content).setGravity(Gravity.LEFT);

                GlideImageView imageView =  holder.findView(R.id.image);
                CircleProgressView progressView = holder.findView(R.id.progressView);
                GlideUtils.loadImageFitHeight(imageView,mData.getImageUrl(),progressView);
            }
        }).create();

        //视频
        if (!TextUtils.isEmpty(actionPastDto.getVideoUrl())){
            jzvPlayer.setVisibility(View.VISIBLE);
            jzvPlayer.setUp(actionPastDto.getVideoUrl()
                    , JZVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, actionPastDto.getVideoName());
            GlideUtils.loadImage(mInstance,actionPastDto.getAvatarUrl(),jzvPlayer.thumbImageView);
        }

        mShareDto = new ShareDto();
        mShareDto.setTitle(TextUtils.isEmpty(actionPastDto.getClubName()) ? "玩转地球商旅学苑" : actionPastDto.getClubName());
        mShareDto.setContent(actionPastDto.getTitle());
        mShareDto.setPageUrl(actionPastDto.getWebDynamicUrl());
        mShareDto.setSharePicUrl(actionPastDto.getAvatarUrl());
    }

    @Override
    public void showClubInfo(ClubDto clubDto) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("clubInfo",clubDto);
        startActivity(ClubActivity.class,bundle);
    }

    @OnClick({R.id.iv_club_logo, R.id.tv_club_name})
    public void onClickClubName(View view){
        if ("manager".equals(clubId)){
            return;
        }
        getPresenter().getClubInfo(clubId);
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }


}
