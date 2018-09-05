package com.km.rmbank.module.main.card;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.hubert.guide.NewbieGuide;
import com.app.hubert.guide.core.Controller;
import com.app.hubert.guide.listener.OnLayoutInflatedListener;
import com.app.hubert.guide.model.GuidePage;
import com.app.hubert.guide.model.HighLight;
import com.app.hubert.guide.model.HighlightOptions;
import com.app.hubert.guide.model.RelativeGuide;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.mvp.model.UserCardModel;
import com.km.rmbank.mvp.presenter.UserCardPresenter;
import com.km.rmbank.mvp.view.UserCardView;
import com.km.rmbank.oldrecycler.AppUtils;
import com.km.rmbank.retrofit.ApiConstant;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.QRCodeUtils;
import com.km.rmbank.utils.dialog.WindowBottomDialog;
import com.km.rmbank.utils.SystemBarHelper;
import com.km.rmbank.utils.UmengShareUtils;
import com.km.rmbank.utils.ViewUtils;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class UserCardActivity extends BaseActivity<UserCardView, UserCardPresenter> implements UserCardView {
    private List<String> shareBottoms;
    private WindowBottomDialog mShareDialog;


    @Override
    public int getContentViewRes() {
        return R.layout.activity_user_card;
    }

    @Override
    public String getTitleContent() {
        return "名片";
    }

    @Override
    protected UserCardPresenter createPresenter() {
        return new UserCardPresenter(new UserCardModel());
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        UserInfoDto userInfoDto = getIntent().getParcelableExtra("userCard");
        boolean isModify = getIntent().getBooleanExtra("isModify", false);
        if (userInfoDto == null || isModify) {//是自己的名片
            SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
            simpleTitleBar.setRightMenuRes(R.menu.toolbar_user_card_more);
            simpleTitleBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.more && mShareDialog != null) {
                        mShareDialog.show();
                    }
                    return false;
                }
            });
        }

    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initUserCard();
        initShareDialog();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserInfo();
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

        ImageView userPortrait2 = mViewManager.findView(R.id.userPortrait2);
        userPortrait2.getLayoutParams().height = windowWidth;


        //引导
        Animation enterAnimation = new AlphaAnimation(0f, 1f);
        enterAnimation.setDuration(600);
        enterAnimation.setFillAfter(true);

        Animation exitAnimation = new AlphaAnimation(1f, 0f);
        exitAnimation.setDuration(600);
        exitAnimation.setFillAfter(true);

        RectF guideDot = new RectF(windowWidth - ConvertUtils.dp2px(52),
                statusBarHeight,
                windowWidth - ConvertUtils.dp2px(4),
                statusBarHeight + ConvertUtils.dp2px(48));

        NewbieGuide.with(mInstance)
                .setLabel("userCard")
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
                .addGuidePage(GuidePage.newInstance()
                        .addHighLightWithOptions(guideDot, HighLight.Shape.CIRCLE, new HighlightOptions.Builder().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mShareDialog.show();
                            }
                        }).build())
                        .setLayoutRes(R.layout.guide_card_2)
                        .setBackgroundColor(0xcc000000)
                        .setEnterAnimation(enterAnimation)//进入动画
                        .setExitAnimation(exitAnimation)
                        .setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                            @Override
                            public void onLayoutInflated(View view, Controller controller) {
                                ImageView imageView = view.findViewById(R.id.imageView);
                                ViewUtils.setMargins(imageView, ConvertUtils.dp2px(100), ConvertUtils.dp2px(20), ConvertUtils.dp2px(20), 0);
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
        if (userInfoDto == null && Constant.userInfo != null) {
            userInfoDto = Constant.userInfo;
        }
        GlideUtils.loadImageOnPregress(userPortrait, userInfoDto.getPortraitUrl(), null);
        userName.setText(userInfoDto.getName());
        userCompany.setText(userInfoDto.getCompany());
        userPosition.setText(userInfoDto.getPosition());

//        RelativeLayout shareUserCard = mViewManager.findView(R.id.shareUserCard);
//        shareUserCard.setVisibility(View.VISIBLE);
        ImageView userPortrait2 = mViewManager.findView(R.id.userPortrait2);
        GlideUtils.loadImage(mInstance, userInfoDto.getPortraitUrl(), userPortrait2);
        mViewManager.setText(R.id.userName2, userInfoDto.getName());
        mViewManager.setText(R.id.userCompany2, userInfoDto.getCompany());
        mViewManager.setText(R.id.userPosition2, userInfoDto.getPosition());
        ImageView qrcode = mViewManager.findView(R.id.qrcode);
        qrcode.setImageBitmap(QRCodeUtils.createQRCode(mInstance, userInfoDto.getShareUrl()));

    }

    private void initShareDialog() {
        mShareDialog = new WindowBottomDialog(mInstance, "取消", "编辑名片", "分享微信好友", "分享朋友圈", "保存图片");
        mShareDialog.setOnClickShareDialog(new WindowBottomDialog.OnClickShareDialog() {
            @Override
            public void clickShareDialog(String itemName, int i) {
                mShareDialog.dimiss();
                switch (i) {
                    case 0://编辑名片
//                        startActivity(CreateNewUserCardActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("fromPage", 0);
                        startActivity(UserCardModifyActivity.class, bundle);
                        break;
                    case 1://分享微信好友
                        getPresenter().taskShare();
                        shareUserCard(SHARE_MEDIA.WEIXIN);
                        break;
                    case 2://分享朋友圈
                        getPresenter().taskShare();
                        shareUserCard(SHARE_MEDIA.WEIXIN_CIRCLE);
                        break;
                    case 3://保存图片
                        shareUserCard(null);
                        break;

                    default:

                        break;
                }
            }
        });
    }

    /**
     * 分享名片
     *
     * @param media
     */
    private void shareUserCard(SHARE_MEDIA media) {
        LinearLayout userCard = mViewManager.findView(R.id.shareUserCard);
        String fileName = "card_" + Constant.userInfo.getMobilePhone();
        String filePath = AppUtils.getImagePath(fileName + ".png");
//        userCard.setVisibility(View.VISIBLE);
        Bitmap imageBitmap = ViewUtils.saveBitmap(userCard, fileName);
//        userCard.setVisibility(View.GONE);

        if (media == null) {//保存图片
            File file = new File(filePath);
            // 其次把文件插入到系统图库
            try {
                MediaStore.Images.Media.insertImage(mInstance.getContentResolver(),
                        file.getAbsolutePath(), fileName, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // 最后通知图库更新
            mInstance.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + filePath)));

            showToast("已保存到相册");
        } else if (media == SHARE_MEDIA.WEIXIN_CIRCLE) {//分享到微信或朋友圈
            UmengShareUtils.openShareForImage(mInstance, imageBitmap, media, new UMShareListener() {
                @Override
                public void onStart(SHARE_MEDIA share_media) {

                }

                @Override
                public void onResult(SHARE_MEDIA share_media) {
                    showToast("分享成功");
                }

                @Override
                public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                    LogUtils.e(throwable.toString());
                    showToast("分享失败");
                }

                @Override
                public void onCancel(SHARE_MEDIA share_media) {
                    showToast("取消分享");
                }
            });
        } else {//ApiConstant.API_BASE_URL + ApiConstant.API_MODEL +
            String webUrl = "http://www.wanzhuandiqiu.com/member/share/UserCode?mobilePhone=" + Constant.userInfo.getMobilePhone();
            UmengShareUtils.shareWeChatMin(mInstance, webUrl, imageBitmap,
                    Constant.userInfo.getName() + "的名片",
                    "token=" + Constant.userLoginInfo.getToken(), "pages/index/index?token=" + Constant.userLoginInfo.getToken(), new UMShareListener() {
                        @Override
                        public void onStart(SHARE_MEDIA share_media) {

                        }

                        @Override
                        public void onResult(SHARE_MEDIA share_media) {
                            showToast("分享成功");
                        }

                        @Override
                        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                            LogUtils.e(throwable.toString());
                            showToast("分享失败");
                        }

                        @Override
                        public void onCancel(SHARE_MEDIA share_media) {
                            showToast("取消分享");
                        }
                    });
        }
    }


    /**
     * 查看用户名片详细信息
     *
     * @param view
     */
    public void openUserInfo(View view) {
        startActivity(UserNewCardDetailsActivity.class, getIntent().getExtras());
    }
}
