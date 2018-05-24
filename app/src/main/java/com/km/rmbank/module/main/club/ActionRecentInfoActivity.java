package com.km.rmbank.module.main.club;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.km.rmbank.R;
import com.km.rmbank.adapter.ActionRecentGuestAdapter;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.dto.ShareDto;
import com.km.rmbank.event.ApplyActionEvent;
import com.km.rmbank.module.login.LoginActivity;
import com.km.rmbank.module.main.personal.member.club.ClubActivity;
import com.km.rmbank.mvp.model.ActionRecentInfoModel;
import com.km.rmbank.mvp.presenter.ActionRecentInfoPresenter;
import com.km.rmbank.mvp.view.IActionRecentInfoView;
import com.km.rmbank.oldrecycler.AppUtils;
import com.km.rmbank.oldrecycler.RVUtils;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.DateUtils;
import com.km.rmbank.utils.DialogUtils;
import com.km.rmbank.utils.EventBusUtils;
import com.km.rmbank.utils.UmengShareUtils;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ObjectAnimator;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import butterknife.BindView;
import butterknife.OnClick;

public class ActionRecentInfoActivity extends BaseActivity<IActionRecentInfoView,ActionRecentInfoPresenter> implements  IActionRecentInfoView {
    @BindView(R.id.iv_club_background)
    ImageView ivClubBackground;

    @BindView(R.id.simple_tb_title_name)
    TextView titleName;


    @BindView(R.id.tv_action_title)
    TextView tvActionTitle;
    @BindView(R.id.iv_club_logo)
    GlideImageView ivClubLogo;
    @BindView(R.id.tv_club_name)
    TextView tvClubName;

    @BindView(R.id.tv_action_start_time)
    TextView tvActionStartTime;
    @BindView(R.id.tv_action_address)
    TextView tvActionAddress;
    @BindView(R.id.iv_club_logo2)
    GlideImageView ivClubLogo2;
    @BindView(R.id.tv_club_name1)
    TextView getTvClubName1;
    @BindView(R.id.tv_club_introduce)
    TextView tvClubIntroduce;
    @BindView(R.id.btn_keep_club)
    Button btnKeepClub;

    @BindView(R.id.tv_action_flow)
    TextView tvActionFlow;

    @BindView(R.id.rv_invitation_mans)
    RecyclerView rvInvitationMans;

    @BindView(R.id.tv_add_active_value)
    TextView tvAddActiveValue;

    private String actionId;
    private ActionDto mActionDto;
    private String clubId;
    private boolean isMyClub;

    private ShareDto shareDto;
    private DialogUtils.CustomBottomDialog mShareDialog;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_action_recent_info;
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
//        simpleTitleBar.setTitleContent("路演大会");
        simpleTitleBar.setRightMenuRes(R.menu.toolbar_action_recent_share);
        simpleTitleBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (Constant.userLoginInfo.isEmpty()){
                    showToast("请登录后再分享");
                    startActivity(LoginActivity.class);
                    return false;
                }
                if (mShareDialog == null){
                    throw new IllegalArgumentException("初始化分享弹出框失败！！！");
                }
                if (item.getItemId() == R.id.share){
                    mShareDialog.show();
                }
                return false;
            }
        });
    }


    @Override
    protected ActionRecentInfoPresenter createPresenter() {
        return new ActionRecentInfoPresenter(new ActionRecentInfoModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {

        isMyClub = getIntent().getBooleanExtra("isMyClub",false);
        actionId = getIntent().getStringExtra("actionId");
        clubId = getIntent().getStringExtra("clubId");
        getPresenter().getActionRecentInfo(actionId);
        shareDto = new ShareDto();

        initInvitationMans();
        initShareDialog();
    }


    private void initInvitationMans(){
        RVUtils.setLinearLayoutManage(rvInvitationMans, LinearLayoutManager.VERTICAL);
        ActionRecentGuestAdapter adapter = new ActionRecentGuestAdapter(this);
        rvInvitationMans.setAdapter(adapter);
    }


    private void initShareDialog(){
        mShareDialog = new DialogUtils.CustomBottomDialog(mInstance,"取消","分享微信好友","分享朋友圈");
        mShareDialog.setOnClickShareDialog(new DialogUtils.CustomBottomDialog.OnClickShareDialog() {
            @Override
            public void clickShareDialog(String itemName, int i) {
                mShareDialog.dimiss();
                switch (i){
                    case 0://分享到微信好友
                        UmengShareUtils.openShare(mInstance,shareDto,SHARE_MEDIA.WEIXIN);
                        break;
                    case 1://分享朋友圈
                        UmengShareUtils.openShare(mInstance,shareDto,SHARE_MEDIA.WEIXIN_CIRCLE);
                        break;
                }
            }
        });
    }

    /**
     * 增加 活跃值 动画
     */
    private void addActiveValue(){
        int midHeight = AppUtils.getCurWindowHeight(this) / 2;

        final ObjectAnimator alphaAnimator1 = ObjectAnimator.ofFloat(tvAddActiveValue,"alpha",0,1,0);
        alphaAnimator1.setDuration(2000);

        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(tvAddActiveValue, "Y",midHeight,midHeight - 200);
        translationAnimator.setDuration(2000);
        translationAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                tvAddActiveValue.setVisibility(View.VISIBLE);
                alphaAnimator1.start();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                tvAddActiveValue.setVisibility(View.GONE);
            }
        });

        translationAnimator.start();
    }

    @Override
    public void showActionRecentInfo(ActionDto actionDto) {
//        mTitle.setText(actionDto.getTitle());
        mActionDto = actionDto;

        shareDto.setTitle(actionDto.getTitle());
        shareDto.setContent(actionDto.getFlow());
        shareDto.setSharePicUrl(actionDto.getActivityPictureUrl());
        shareDto.setPageUrl(actionDto.getWebActivityUrl());

//        initShareDialog();

//        if (isMyClub && !"1".equals(actionDto.getActivityType())){
//            setRightBtnClick("编辑", new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Bundle bundle = new Bundle();
//                    bundle.putParcelable("actionDto",mActionDto);
//                    bundle.putString("clubId",clubId);
//                    startActivity(ReleaseActionRecentActivity.class,bundle);
//                }
//            });
//        } else {
//            setRightIconClick(R.mipmap.ic_action_recent_share, new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    openShare();
//                }
//            });
//        }

        GlideUtils.loadImage(mInstance,actionDto.getBackgroundImg(),ivClubBackground);
        tvActionTitle.setText(actionDto.getTitle());
        titleName.setText(actionDto.getTitle());
        GlideUtils.loadImageOnPregress(ivClubLogo,actionDto.getClubLogo(),null);
        tvClubName.setText(actionDto.getClubName());

        tvActionStartTime.setText("   " + actionDto.getHoldDate());
        tvActionAddress.setText("   " + actionDto.getAddress());
        GlideUtils.loadImageOnPregress(ivClubLogo2,actionDto.getClubLogo(),null);
        getTvClubName1.setText(actionDto.getClubName());
        tvClubIntroduce.setText(actionDto.getClubContent());
        if (actionDto.getKeepStatus() == 0){//未关注
            btnKeepClub.setText("+ 关注");
        } else {
            btnKeepClub.setText("已关注");
        }

        tvActionFlow.setText(actionDto.getFlow());

        if (Constant.userInfo.getMobilePhone().equals(mActionDto.getMobilePhone())){
            isMyClub = true;

        }

        ActionRecentGuestAdapter adapter = (ActionRecentGuestAdapter) rvInvitationMans.getAdapter();
        adapter.addData(actionDto.getGuestList());

    }

    @Override
    public void applyActionSuccess(String actionId) {
        showToast("报名成功");
        EventBusUtils.post(new ApplyActionEvent(actionId));
    }

    @Override
    public void followClubSuccess(boolean isFollow) {
        mActionDto.setKeepStatus(isFollow ? 1 : 0);
        if (mActionDto.getKeepStatus() == 0){//未关注
            btnKeepClub.setText("+ 关注");
        } else {
            btnKeepClub.setText("已关注");
        }
    }

    @Override
    public void addActiveValueSuccess(String result) {
//        Logger.d("activeValue  ====  " + result);
        tvAddActiveValue.setText("活跃 +" + result);
        addActiveValue();
    }

    @Override
    public void showClubInfo(ClubDto clubDto) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("clubInfo",clubDto);
        startActivity(ClubActivity.class,bundle);
    }

    /**
     * 点击 都有谁去 按钮
     * @param view
     */
    @OnClick({R.id.ll_join_members, R.id.tv_join_members})
    public void clickMembers(View view){
        Bundle bundle = new Bundle();
        bundle.putParcelable("actionDto",mActionDto);
        bundle.putBoolean("isMyClub",isMyClub);
        startActivity(ActionJoinMemberActivity.class,bundle);
    }

    /**
     * 报名
     * @param view
     */
    @OnClick({R.id.rl_action_apply, R.id.tv_action_apply})
    public void clickApply(View view){
        if (Constant.userLoginInfo.isEmpty()){
            showToast("请先登录，再报名");
            startActivity(LoginActivity.class);
            return;
        }

        long holdDate = DateUtils.getInstance().stringDateToMillis(mActionDto.getHoldDate(),DateUtils.YMDHM);
        long curDate = System.currentTimeMillis();
        if (curDate >= holdDate){
            showToast("报名已截止");
            return;
        }

        DialogUtils.showDefaultAlertDialog("是否报名《" + mActionDto.getTitle() + "》活动？", new DialogUtils.ClickListener() {
            @Override
            public void clickConfirm() {
                getPresenter().applyAction(mActionDto.getId(),Constant.userInfo.getName(),Constant.userInfo.getMobilePhone());
            }
        });

    }

    /**
     * 在线咨询
     * @param view
     */
    @OnClick({R.id.ll_relation, R.id.tv_relation})
    public void clickRelation(View view){
        if (isMyClub){
            showToast("这是您自己的活动");
            return;
        }
//        Bundle bundle = new Bundle();
//        bundle.putString("to_user_id",mActionDto.getMobilePhone());
//        bundle.putString("user_nick_name",mActionDto.getName());
//        bundle.putBoolean("isService",true);
//        startActivity(EaseChatActivity.class,bundle);
    }

    /**
     * 关注俱乐部
     * @param view
     */
    @OnClick(R.id.btn_keep_club)
    public void keepClub(View view){
        if (isMyClub){
            showToast("不能关注自己的俱乐部");
            return;
        }
        String content;
        if (mActionDto.getKeepStatus() == 0){//未关注
            content = "是否关注该俱乐部?";
        } else {
            content = "是否取消对该俱乐部的关注？";
        }
        DialogUtils.showDefaultAlertDialog(content, new DialogUtils.ClickListener() {
            @Override
            public void clickConfirm() {
                getPresenter().followClub(mActionDto.getClubId(),mActionDto.getKeepStatus() == 0);
            }
        });
    }

    /**
     * 查看俱乐部详情
     * @param view
     */
    @OnClick({R.id.iv_club_logo, R.id.iv_club_logo2, R.id.tv_club_name, R.id.tv_club_name1})
    public void toClubInfos(View view){
        getPresenter().getClubInfo(mActionDto.getClubId());
    }


}
