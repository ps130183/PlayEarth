package com.km.rmbank.module.main.card;

import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.hubert.guide.NewbieGuide;
import com.app.hubert.guide.core.Controller;
import com.app.hubert.guide.listener.OnLayoutInflatedListener;
import com.app.hubert.guide.model.GuidePage;
import com.app.hubert.guide.model.HighLight;
import com.app.hubert.guide.model.HighlightOptions;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.mvp.presenter.UserCardPresenter;
import com.km.rmbank.mvp.view.UserCardView;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.QRCodeUtils;
import com.km.rmbank.utils.SystemBarHelper;
import com.km.rmbank.utils.ViewUtils;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;

public class RecommendUserCardActivity extends BaseActivity {

    @Override
    public int getContentViewRes() {
        return R.layout.activity_recommend_user_card;
    }

    @Override
    public String getTitleContent() {
        return "名片";
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initUserCard();
        loadUserInfo();
    }


    /**
     * 查看推荐人详细信息
     * @param view
     */
    public void openUserInfo(View view) {
        startActivity(UserNewCardDetailsActivity.class, getIntent().getExtras());
    }

    private void initUserCard() {
        //屏幕高度
        int windowWidth = ScreenUtils.getScreenWidth();
        int windowHeight = ScreenUtils.getScreenHeight();
        final int statusBarHeight = SystemBarHelper.getStatusBarHeight(this);
        final int toolbarHeight = ConvertUtils.dp2px(48);
        int userCardMatgin = ConvertUtils.dp2px(48);

        int userCardHeight = windowHeight - statusBarHeight - toolbarHeight - userCardMatgin * 2;
        int userCardWidth = windowWidth - ConvertUtils.dp2px(64);

        CardView userCard = mViewManager.findView(R.id.userCard);
        userCard.getLayoutParams().height = userCardHeight;
        userCard.getLayoutParams().width = userCardWidth;

        GlideImageView userPortrait = mViewManager.findView(R.id.userPortrait);
        int portraitWidth = userCardWidth - ConvertUtils.dp2px(25) * 2;
        userPortrait.getLayoutParams().width = portraitWidth;
        userPortrait.getLayoutParams().height = portraitWidth;

//        ImageView userPortrait2 = mViewManager.findView(R.id.userPortrait2);
//        userPortrait2.getLayoutParams().height = windowWidth;


        //引导
        Animation enterAnimation = new AlphaAnimation(0f, 1f);
        enterAnimation.setDuration(600);
        enterAnimation.setFillAfter(true);

        Animation exitAnimation = new AlphaAnimation(1f, 0f);
        exitAnimation.setDuration(600);
        exitAnimation.setFillAfter(true);

        NewbieGuide.with(mInstance)
                .setLabel("recommendUserCard")
                .setShowCounts(1)
                .addGuidePage(GuidePage.newInstance()
                        .addHighLightWithOptions(userCard, new HighlightOptions.Builder().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                openUserInfo(v);
                            }
                        }).build())
                        .setLayoutRes(R.layout.guide_card_1)
                        .setBackgroundColor(0xcc000000)
                        .setEnterAnimation(enterAnimation)//进入动画
                        .setExitAnimation(exitAnimation)
                        .setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                            @Override
                            public void onLayoutInflated(View view, Controller controller) {
                                ImageView imageView = view.findViewById(R.id.imageView);
                                imageView.setScaleX(0.5f);
                                imageView.setScaleY(0.5f);
                                imageView.setTranslationY(-ConvertUtils.dp2px(20));
                            }
                        }))
                .show();
    }

    private void loadUserInfo() {
        GlideImageView userPortrait = mViewManager.findView(R.id.userPortrait);
        TextView userName = mViewManager.findView(R.id.userName);
        TextView userCompany = mViewManager.findView(R.id.userCompany);
        TextView userPosition = mViewManager.findView(R.id.userPosition);

        UserInfoDto userInfoDto = getIntent().getParcelableExtra("userCard");//= Constant.userInfo;

        GlideUtils.loadImageOnPregress(userPortrait, userInfoDto.getPortraitUrl(), null);
        userName.setText(userInfoDto.getName());
        userCompany.setText(userInfoDto.getCompany());
        userPosition.setText(userInfoDto.getPosition());
    }
}
