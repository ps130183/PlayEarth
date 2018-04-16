package com.km.rmbank.module.main.card;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyItemDialogListener;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.oldrecycler.AppUtils;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.DialogUtils;
import com.km.rmbank.utils.QRCodeUtils;
import com.km.rmbank.utils.SystemBarHelper;
import com.km.rmbank.utils.UmengShareUtils;
import com.km.rmbank.utils.ViewUtils;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yancy.gallerypick.utils.ScreenUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class UserNewCardActivity extends BaseActivity {

    private List<String> shareBottoms;
    private DialogUtils.CustomBottomDialog mShareDialog;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_user_new_card;
    }

    @Override
    public String getTitleContent() {
        return "微名片";
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
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

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        loadUserQRCode();
        initUserCard();
        initShareDialog();
    }

    private void initUserCard() {
        //屏幕高度
        int windowWidth = ScreenUtils.getScreenWidth(this);
        int windowHeight = ScreenUtils.getScreenHeight(this);
        int statusBarHeight = SystemBarHelper.getStatusBarHeight(this);
        int toolbarHeight = ConvertUtils.dp2px(48);
        int userCardMatgin = ConvertUtils.dp2px(48);

        int userCardHeight = windowHeight - statusBarHeight - toolbarHeight - userCardMatgin * 2;
        int userCardWidth = windowWidth - ConvertUtils.dp2px(64);

        CardView userCard = mViewManager.findView(R.id.userCard);
        userCard.getLayoutParams().height = userCardHeight;
        userCard.getLayoutParams().width = userCardWidth;


        FrameLayout userInfo = mViewManager.findView(R.id.userInfo);
        GlideImageView userPortrait = mViewManager.findView(R.id.userPortrait);
        TextView userName = mViewManager.findView(R.id.userName);
        TextView userPosition = mViewManager.findView(R.id.userPosition);
        TextView userCompany = mViewManager.findView(R.id.userCompany);

        int userInfoHeihgt = (int) (userCardHeight * (57.0 / 90));
        userInfo.getLayoutParams().height = userInfoHeihgt;

        float per = 285.0f;
        LinearLayout.LayoutParams userPortraitLp = (LinearLayout.LayoutParams) userPortrait.getLayoutParams();
        userPortraitLp.topMargin = (int) (userInfoHeihgt * (28 / per));
        userPortraitLp.height = (int) (userInfoHeihgt * (126 / per));
        userPortraitLp.width = (int) (userInfoHeihgt * (126 / per));

        LinearLayout.LayoutParams userNameLp = (LinearLayout.LayoutParams) userName.getLayoutParams();
        userNameLp.topMargin = (int) (userInfoHeihgt * (16 / per));
        userName.setTextSize(ConvertUtils.px2sp((float) (userInfoHeihgt * (25 / per))));

        LinearLayout.LayoutParams userPositionLp = (LinearLayout.LayoutParams) userPosition.getLayoutParams();
        userPositionLp.topMargin = (int) (userInfoHeihgt * (10 / per));
        userPosition.setTextSize(ConvertUtils.px2sp((float) (userInfoHeihgt * (13 / per))));

        LinearLayout.LayoutParams userCompanyLp = (LinearLayout.LayoutParams) userCompany.getLayoutParams();
        userCompanyLp.topMargin = (int) (userInfoHeihgt * (4 / per));
        userCompany.setTextSize(ConvertUtils.px2sp((float) (userInfoHeihgt * (13 / per))));


        int codeHeight = userCardHeight - userInfoHeihgt;
        int qrcodeWidth = (int) (codeHeight * (160 / 296.0));
        ImageView qrCode = mViewManager.findView(R.id.userQRCode);
        qrCode.getLayoutParams().width = qrcodeWidth;
        qrCode.getLayoutParams().height = qrcodeWidth;


        UserInfoDto userInfoDto = Constant.userInfo;
        if (userInfoDto == null){
            return;
        }
        GlideUtils.loadImageOnPregress(userPortrait,userInfoDto.getPortraitUrl(),null);
        userName.setText(userInfoDto.getName());
        userPosition.setText(userInfoDto.getPosition());

        Bitmap userQrcode = QRCodeUtils.createQRCode(mInstance,userInfoDto.getShareUrl());
        qrCode.setImageBitmap(userQrcode);

    }


    private void loadUserQRCode() {
        ImageView userQRCode = mViewManager.findView(R.id.userQRCode);
        Bitmap qrcode = QRCodeUtils.createQRCode(this, "http://www.baidu,com");
        userQRCode.setImageBitmap(qrcode);
    }

    private void initShareDialog() {
        mShareDialog = new DialogUtils.CustomBottomDialog(mInstance, "取消", "编辑名片", "分享微信好友", "分享朋友圈", "保存图片");
        mShareDialog.setOnClickShareDialog(new DialogUtils.CustomBottomDialog.OnClickShareDialog() {
            @Override
            public void clickShareDialog(String itemName, int i) {
                mShareDialog.dimiss();
                switch (i) {
                    case 0://编辑名片
                        break;
                    case 1://分享微信好友
                        shareUserCard(SHARE_MEDIA.WEIXIN);
                        break;
                    case 2://分享朋友圈
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
     * @param media
     */
    private void shareUserCard(SHARE_MEDIA media){
        CardView userCard = mViewManager.findView(R.id.userCard);
        String fileName = "card_" + Constant.userInfo.getMobilePhone();
        String filePath = AppUtils.getImagePath(fileName + ".png");
        Bitmap imageBitmap = ViewUtils.saveBitmap(userCard, fileName);

        if (media == null){//保存图片
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
        } else {//分享到微信或朋友圈
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
        }
    }


}
