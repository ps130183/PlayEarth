package com.km.rmbank.module.main.appoint;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.dalong.carrousellayout.CarrouselLayout;
import com.dalong.carrousellayout.OnCarrouselItemClickListener;
import com.example.zhouwei.library.CustomPopWindow;
import com.km.rmbank.R;
import com.km.rmbank.adapter.ActionRecentGuestAdapter;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.dto.ActionMemberDto;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.dto.ShareDto;
import com.km.rmbank.event.ApplyActionEvent;
import com.km.rmbank.module.login.LoginActivity;
import com.km.rmbank.mvp.model.ActionRecentInfoModel;
import com.km.rmbank.mvp.presenter.ActionRecentInfoPresenter;
import com.km.rmbank.mvp.view.IActionRecentInfoView;
import com.km.rmbank.oldrecycler.RVUtils;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.DateUtils;
import com.km.rmbank.utils.StringUtils;
import com.km.rmbank.utils.dialog.DialogUtils;
import com.km.rmbank.utils.dialog.WindowBottomDialog;
import com.km.rmbank.utils.EventBusUtils;
import com.km.rmbank.utils.UmengShareUtils;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yancy.gallerypick.utils.ScreenUtils;

import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class AppointAfternoonTeaActivity extends BaseActivity<IActionRecentInfoView, ActionRecentInfoPresenter> implements IActionRecentInfoView {

    private String actionId;
    private ShareDto shareDto;
    private WindowBottomDialog mShareDialog;
    private String appointType = "";

    private ActionDto mActionDto;

    private TextView mTitle;


    @Override
    public int getContentViewRes() {
        return R.layout.activity_appoint_afternoon_tea;
    }

    @Override
    public String getTitleContent() {
        String title = getIntent().getStringExtra("title");
        return title;
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setRightMenuRes(R.menu.toolbar_action_recent_share);
        simpleTitleBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (Constant.userLoginInfo.isEmpty()) {
                    showToast("请登录后再分享");
                    startActivity(LoginActivity.class);
                    return false;
                }
                if (mShareDialog == null) {
                    throw new IllegalArgumentException("初始化分享弹出框失败！！！");
                }
                if (item.getItemId() == R.id.share) {
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
        mTitle = mViewManager.findView(R.id.simple_tb_title_name);
        String actionId = getIntent().getStringExtra("actionId");
        getPresenter().getActionRecentInfo(actionId);
        initApplyPersons();
        initShareDialog();

    }

    private void initShareDialog() {
        mShareDialog = new WindowBottomDialog(mInstance, "取消", "分享微信好友", "分享朋友圈");
        mShareDialog.setOnClickShareDialog(new WindowBottomDialog.OnClickShareDialog() {
            @Override
            public void clickShareDialog(String itemName, int i) {
                mShareDialog.dimiss();
                switch (i) {
                    case 0://分享到微信好友
                        getPresenter().taskShare();
                        UmengShareUtils.openShare(mInstance, shareDto, SHARE_MEDIA.WEIXIN);
                        break;
                    case 1://分享朋友圈
                        getPresenter().taskShare();
                        UmengShareUtils.openShare(mInstance, shareDto, SHARE_MEDIA.WEIXIN_CIRCLE);
                        break;
                }
            }
        });
    }

    private void initApplyPersons() {
        final CarrouselLayout carrousel = mViewManager.findView(R.id.carrousel);
        carrousel.getLayoutParams().height = ScreenUtils.getScreenWidth(mInstance);
        carrousel.setR(ScreenUtils.getScreenWidth(mInstance) / 2)//设置R的大小
                .setAutoRotation(false)//是否自动切换
                .setRotationX(-30)
                .setAutoRotationTime(1500);//自动切换的时间  单位毫秒
    }


    private void loadPersonInfo(ActionDto actionDto) {
        CarrouselLayout carrousel = mViewManager.findView(R.id.carrousel);
        carrousel.setR(ScreenUtils.getScreenWidth(mInstance) / 2);
        mViewManager.setText(R.id.number, (actionDto.getSeatNum() - actionDto.getActionMemberDtos().size()) + "");

        carrousel.removeAllViews();
        for (int i = 0; i < actionDto.getSeatNum(); i++) {
            List<ActionMemberDto> actionGuestBeans = actionDto.getActionMemberDtos();

            View view = LayoutInflater.from(mInstance)
                    .inflate(R.layout.item_afternoon_tea_apply_person_info, null, false);
            TextView textView = view.findViewById(R.id.item);
            TextView userName = view.findViewById(R.id.userName);
            if (i < actionGuestBeans.size()) {
                ActionMemberDto memberDto = actionGuestBeans.get(i);
                textView.setVisibility(View.GONE);
                GlideImageView imageView = view.findViewById(R.id.userPortrait);
                imageView.setVisibility(View.VISIBLE);
                GlideUtils.loadImageOnPregress(imageView, memberDto.getHeadImage(), null);

                userName.setText(memberDto.getRegistrationName());
                TextView userPhone = view.findViewById(R.id.userPhone);
                userPhone.setVisibility(View.VISIBLE);
                userPhone.setText(StringUtils.hidePhone(memberDto.getRegistrationPhone()));

                TextView userPosition = view.findViewById(R.id.userPosition);
                userPosition.setVisibility(View.VISIBLE);
                userPosition.setText(memberDto.getUserPosition());

                TextView userCompany = view.findViewById(R.id.userCompany);
                userCompany.setVisibility(View.VISIBLE);
                userCompany.setText(memberDto.getUserCompany());

                ImageView initiator = view.findViewById(R.id.initiator);
                if (memberDto.getIsInitiator() == 1){
                    initiator.setVisibility(View.VISIBLE);
                } else {
                    initiator.setVisibility(View.GONE);
                }
            } else {
                userName.setText("可预约");
                textView.setText("" + (i + 1));
            }


            carrousel.addView(view);
        }
        carrousel.checkChildView();
        carrousel.startAnimationR();
//        carrousel.refreshLayout();
    }

    @Override
    public void showActionRecentInfo(final ActionDto actionDto) {
        mActionDto = actionDto;

        if (shareDto == null) {
            shareDto = new ShareDto();
        }
        shareDto.setTitle(actionDto.getTitle());
        shareDto.setContent(actionDto.getFlow());
        shareDto.setSharePicUrl(actionDto.getActivityPictureUrl());
        shareDto.setPageUrl(actionDto.getWebActivityUrl());

        mViewManager.setText(R.id.action_title, actionDto.getTitle());
        mViewManager.setText(R.id.action_start_time, DateUtils.getInstance().dateToString(new Date(actionDto.getStartDate()), DateUtils.YMDHM));
        mViewManager.setText(R.id.action_address, actionDto.getAddress());

        GlideImageView imageView = mViewManager.findView(R.id.imageView);
        GlideUtils.loadImageOnPregress(imageView, actionDto.getActivityPictureUrl(), null);

        loadPersonInfo(actionDto);

        mTitle.setText(TextUtils.isEmpty(actionDto.getPlaceName()) ? actionDto.getTitle() : actionDto.getPlaceName());


        Button applyAction = mViewManager.findView(R.id.applyAction);
        long curDate = System.currentTimeMillis();
        if (curDate >= mActionDto.getEndDate()) {
            applyAction.setText("已结束");
            applyAction.setBackgroundResource(R.drawable.shape_btn_gray);
            applyAction.setEnabled(false);
            return;
        }

    }

    @Override
    public void applyActionSuccess(String actionId) {
        showToast("报名成功");
        getPresenter().getActionRecentInfo(actionId);
    }

    @Override
    public void followClubSuccess(boolean isFollow) {

    }

    @Override
    public void addActiveValueSuccess(String result) {

    }

    @Override
    public void showClubInfo(ClubDto clubDto) {

    }

    /**
     * 点击立即报名
     *
     * @param view
     */
    public void clickApplyAction(View view) {
        if (Constant.userLoginInfo.isEmpty()) {
            showToast("请先登录，再报名");
            startActivity(LoginActivity.class);
            return;
        }

//        long holdDate = DateUtils.getInstance().stringDateToMillis(mActionDto.getHoldDate(), DateUtils.YMDHM);
        long curDate = System.currentTimeMillis();
        if (curDate >= mActionDto.getEndDate()) {
            showToast("报名已截止");
            return;
        }
        DialogUtils.showDefaultAlertDialog("确认是否参加该活动？", new DialogUtils.ClickListener() {
            @Override
            public void clickConfirm() {
                getPresenter().applyAction(mActionDto.getId(), Constant.userInfo.getName(), Constant.userInfo.getMobilePhone());
            }
        });

    }
}
