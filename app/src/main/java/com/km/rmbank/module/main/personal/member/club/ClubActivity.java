package com.km.rmbank.module.main.personal.member.club;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.BannerDto;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.entity.TabEntity;
import com.km.rmbank.event.AttentionClubEvent;
import com.km.rmbank.module.login.LoginActivity;
import com.km.rmbank.mvp.model.ClubModel;
import com.km.rmbank.mvp.presenter.ClubPresenter;
import com.km.rmbank.mvp.view.IClubView;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.EventBusUtils;
import com.km.rmbank.utils.SystemBarHelper;
import com.ps.commonadapter.adapter.wrapper.LoadMoreWrapper;
import com.ps.glidelib.GlideUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ClubActivity extends BaseActivity<IClubView,ClubPresenter> implements IClubView {

    @BindView(R.id.toolBar)
    Toolbar mToolbar;

    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;

    @BindView(R.id.commonTabLayout)
    CommonTabLayout tabLayout;
    private String[] ctaTitles = {"简介", "活动"};

    @BindView(R.id.scrollView)
    NestedScrollView mScrollView;

    //关注
    @BindView(R.id.attention_club)
    FrameLayout attentionClub;
    @BindView(R.id.tv_attention_club)
    TextView tvAttentionClub;
    Drawable leftDrawable;

    //是否关注
    private boolean isAttention = false;

    private String title = "";

    private ClubDto mClubDto;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_club;
    }

    @Override
    public boolean statusBarTextColorIsDark() {
        return false;
    }

    @Override
    public BaseTitleBar getBaseTitleBar() {
        return null;
    }

    @Override
    protected ClubPresenter createPresenter() {
        return new ClubPresenter(new ClubModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        leftDrawable = getResources().getDrawable(R.mipmap.ic_add_white_48px);
        leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
        getData();
        initToolBar();
        initCommonTabLayout();
    }

    private void getData(){
        mClubDto = getIntent().getParcelableExtra("clubInfo");
        if (mClubDto == null){
            return;
        }
        title = mClubDto.getClubName();
        mViewManager.setText(R.id.clubIntroduce,mClubDto.getContent());
        GlideUtils.loadImage(mInstance,mClubDto.getClubLogo(),mViewManager.getImageView(R.id.clubLogo));
        GlideUtils.loadImage(mInstance,mClubDto.getBackgroundImg(),mViewManager.getImageView(R.id.iv_background));

        mViewManager.setText(R.id.attentionNum,mClubDto.getKeepCount() + "");
        mViewManager.setText(R.id.memberNum,TextUtils.isEmpty(mClubDto.getFans()) ? "0" : mClubDto.getFans());
        mViewManager.setText(R.id.actionNum,"发布过" + mClubDto.getActivityCount() + "个活动，累计参加人数" +mClubDto.getActivityPersonCount() + "人");

        isAttention = mClubDto.getKeepStatus();
        notifyAttention();
    }

    private void initToolBar() {
        SystemBarHelper.immersiveStatusBar(this);
        int statusBarHeight = SystemBarHelper.getStatusBarHeight(mInstance);

        mToolbar.setPadding(0, statusBarHeight, 0, 0);
        mToolbar.getLayoutParams().height = ConvertUtils.dp2px(48) + statusBarHeight;
        mToolbar.setTitle(title);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void initCommonTabLayout() {
        ArrayList<CustomTabEntity> ctaDatas = new ArrayList<>();
        for (int i = 0; i < ctaTitles.length; i++) {
            ctaDatas.add(new TabEntity(ctaTitles[i], 0, 0));
        }

        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(ClubIntroduceFragment.newInstance(getIntent().getExtras()));
        fragmentList.add(ClubActionsFragment.newInstance(getIntent().getExtras()));

        tabLayout.setTabData(ctaDatas, this, R.id.tab_layout_page, fragmentList);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public boolean onTabSelect(int position) {
                return true;
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }


    /**
     * 俱乐部名片
     * @param view
     */
    @OnClick(R.id.clubIntroduce)
    public void openClubIntroduceCard(View view){
        if (mClubDto == null){
            return;
        }
        if (TextUtils.isEmpty(Constant.userInfo.getTotal())){
            showToast("请先登录");
            startActivity(LoginActivity.class);
            return;
        }
        startActivity(ClubIntroCardActivity.class,getIntent().getExtras());
    }


    /**
     * 关注俱乐部
     * @param view
     */
    @OnClick({R.id.attention_club,R.id.tv_attention_club})
    public void attentionClub(View view){
        getPresenter().attentionClub(mClubDto.getId());
    }

    /**
     * 更新关注状态
     */
    private void notifyAttention(){
        if (isAttention){//已关注
            attentionClub.setBackgroundColor(Color.parseColor("#8c8c8c"));
            tvAttentionClub.setText("已关注");
            tvAttentionClub.setCompoundDrawables(null,null,null,null);
        } else { //未关注
            attentionClub.setBackgroundColor(Color.parseColor("#02af0e"));
            tvAttentionClub.setText("关注");
            tvAttentionClub.setCompoundDrawables(leftDrawable,null,null,null);

        }
    }


    @Override
    public void showClubs(List<ClubDto> clubDtos, LoadMoreWrapper wrapper) {

    }

    @Override
    public void showClubInfo(ClubDto clubDto) {

    }

    @Override
    public void showBannerList(List<BannerDto> bannerDtos) {

    }

    @Override
    public void attentionClubResult(String result) {
        isAttention = !isAttention;
        notifyAttention();
        EventBusUtils.post(new AttentionClubEvent(mClubDto.getId(),isAttention));
    }
}
