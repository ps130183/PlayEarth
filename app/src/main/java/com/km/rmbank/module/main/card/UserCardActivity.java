package com.km.rmbank.module.main.card;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.oldrecycler.AppUtils;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.QRCodeUtils;
import com.km.rmbank.utils.SystemBarHelper;
import com.km.rmbank.utils.UmengShareUtils;
import com.km.rmbank.utils.ViewUtils;
import com.ps.glidelib.GlideUtils;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.File;
import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.OnClick;

public class UserCardActivity extends BaseActivity {

    private UserInfoDto userInfoDto;

    @BindView(R.id.cardView)
    CardView cardView;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_user_card;
    }

    @Override
    public boolean statusBarTextColorIsDark() {
        return false;
    }

    @Override
    public int getLeftIcon() {
        return R.mipmap.icon_arrow_left_black_white;
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        userInfoDto = getIntent().getParcelableExtra("userCard");
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setTitleContent("微名片");
        simpleTitleBar.setTitleColor(Color.parseColor("#ffffff"));
        simpleTitleBar.setToolBarBackgroundColor(Color.parseColor("#666666"));
        if (userInfoDto == null) {
            userInfoDto = Constant.userInfo;
            simpleTitleBar.setRightMenuContent("编辑");
            simpleTitleBar.setRightMenuClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(CreateUserCardActivity.class);
                }
            });
        }

    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        SystemBarHelper.tintStatusBar(this, 0xFF666666, 0f);

        mViewManager.getImageView(R.id.iv_user_card_qr_code).setImageBitmap(QRCodeUtils.createQRCode(this, userInfoDto.getShareUrl()));

        ImageView userPortrait = mViewManager.getImageView(R.id.userPortrait);
        GlideUtils.loadImage(this, userInfoDto.getPortraitUrl(), userPortrait);

        mViewManager.setText(R.id.userName, userInfoDto.getName());
        mViewManager.setText(R.id.userPosition, userInfoDto.getPosition());
        mViewManager.setText(R.id.userPhone, userInfoDto.getMobilePhone());
        mViewManager.setText(R.id.userAddress, userInfoDto.getDetailedAddress());
        mViewManager.setText(R.id.userIntro, userInfoDto.getPersonalizedSignature());


    }

    @OnClick({R.id.iv_weixin, R.id.iv_pengyouquan, R.id.iv_xiazai})
    public void openShare(View view) {

        String fileName = "card_" + Constant.userInfo.getMobilePhone();
        String filePath = AppUtils.getImagePath(fileName + ".png");
        Bitmap imageBitmap;
        if (!FileUtils.isFile(filePath)) {
            imageBitmap = ViewUtils.saveBitmap(cardView, fileName);
            LogUtils.d("没有名片，创建");
        } else {
            imageBitmap = BitmapFactory.decodeFile(filePath);
            LogUtils.d("有名片，直接获取");
        }
        SHARE_MEDIA share_media = null;
        switch (view.getId()) {
            case R.id.iv_weixin:
                share_media = SHARE_MEDIA.WEIXIN;
                break;
            case R.id.iv_pengyouquan:
                share_media = SHARE_MEDIA.WEIXIN_CIRCLE;
                break;

            default:
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

                showToast("已将名片保存到相册");
                break;
        }

        if (share_media == null) {
            return;
        }

        UmengShareUtils.openShareForImage(mInstance, imageBitmap, share_media, new UMShareListener() {
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
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {

            }
        });
    }
}
