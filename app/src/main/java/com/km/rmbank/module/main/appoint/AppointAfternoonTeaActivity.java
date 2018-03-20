package com.km.rmbank.module.main.appoint;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
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
import com.km.rmbank.mvp.model.ActionRecentInfoModel;
import com.km.rmbank.mvp.presenter.ActionRecentInfoPresenter;
import com.km.rmbank.mvp.view.IActionRecentInfoView;
import com.km.rmbank.oldrecycler.RVUtils;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.DateUtils;
import com.km.rmbank.utils.EventBusUtils;
import com.km.rmbank.utils.UmengShareUtils;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.w3c.dom.Text;

import java.util.Date;

import butterknife.BindView;

public class AppointAfternoonTeaActivity extends BaseActivity<IActionRecentInfoView,ActionRecentInfoPresenter> implements IActionRecentInfoView {

    private String actionId;
    private ShareDto shareDto;
    private String appointType="";

    private ActionDto mActionDto;


    @BindView(R.id.headerImageView)
    GlideImageView headerImageView;
    @BindView(R.id.invitation_content)
    TextView invitationContent;

    //活动相关
    @BindView(R.id.actionName)
    TextView actionName;
    @BindView(R.id.actionTitle)
    TextView actionTitle;
    @BindView(R.id.actionTime)
    TextView actionTime;
    @BindView(R.id.actionAddress)
    TextView actionAddress;
    @BindView(R.id.actionContent)
    TextView actionContent;

    @BindView(R.id.guestRecycler)
    RecyclerView guestRecycler;


    @Override
    public int getContentViewRes() {
        return R.layout.activity_appoint_afternoon_tea;
    }

    @Override
    public String getTitleContent() {
        appointType = getIntent().getStringExtra("appointType");
        if ("1".equals(appointType)){
            return "下午茶";
        } else if ("2".equals(appointType)){
            return "结缘晚宴";
        }
        return "";
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setRightMenuRes(R.menu.toolbar_action_recent_share);
        simpleTitleBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (Constant.userLoginInfo.isEmpty()){
                    showToast("请登录后再分享");
                    startActivity(LoginActivity.class);
                    return false;
                }
                if (item.getItemId() == R.id.share){
                    openShare();
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
        actionId = getIntent().getStringExtra("actionId");
        getPresenter().getActionRecentInfo(actionId);
        shareDto = new ShareDto();

        initInvitationMans();
    }

    /**
     * 初始化邀请函内容
     */
    private void initInvitationMans(){

        if ("1".equals(appointType)){
            GlideUtils.loadImageByRes(headerImageView,R.mipmap.banner_afternoon_tea);
            invitationContent.setText(getResources().getString(R.string.appoint_afternoon_tea));
        } else if ("2".equals(appointType)){
            GlideUtils.loadImageByRes(headerImageView,R.mipmap.banner_dinner_party);
            invitationContent.setText(getResources().getString(R.string.appoint_dineer_party));
        }

        //初始化嘉宾列表
        RVUtils.setLinearLayoutManage(guestRecycler, LinearLayoutManager.VERTICAL);
        ActionRecentGuestAdapter adapter = new ActionRecentGuestAdapter(this);
        adapter.setmExistEmptyView(false);
        guestRecycler.setAdapter(adapter);
    }



    private void openShare(){
        UmengShareUtils.openShare(this, shareDto, new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {
                showToast("分享成功");
                getPresenter().addActiveValue(actionId);
            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                LogUtils.d(throwable.getMessage());
//                showToast("分享失败");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {
                showToast("取消分享");
            }
        });
    }


    @Override
    public void showActionRecentInfo(ActionDto actionDto) {
        mActionDto = actionDto;

        shareDto.setTitle(actionDto.getTitle());
        shareDto.setContent(actionDto.getFlow());
        shareDto.setSharePicUrl(actionDto.getActivityPictureUrl());
        shareDto.setPageUrl(actionDto.getWebActivityUrl());

        actionName.setText(actionDto.getTitle() + "·邀请函");
        actionTitle.setText("主题：" +actionDto.getTitle());
        actionTime.setText("时间：" + DateUtils.getInstance().dateToString(new Date(actionDto.getStartDate()),DateUtils.YMDHM));
        actionAddress.setText("地址：" + actionDto.getAddress());
        actionContent.setText(actionDto.getContent());


        ActionRecentGuestAdapter adapter = (ActionRecentGuestAdapter) guestRecycler.getAdapter();
        adapter.addData(actionDto.getGuestList());
    }

    @Override
    public void applyActionSuccess(String actionId) {
        showToast("报名成功");
        EventBusUtils.post(new ApplyActionEvent(actionId));
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
     * @param view
     */
    public void clickApplyAction(View view) {
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
        getPresenter().applyAction(mActionDto.getId(),Constant.userInfo.getName(),Constant.userInfo.getMobilePhone());
    }
}
