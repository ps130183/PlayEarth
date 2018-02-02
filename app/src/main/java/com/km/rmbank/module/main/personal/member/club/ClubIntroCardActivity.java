package com.km.rmbank.module.main.personal.member.club;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.module.main.card.CreateUserCardActivity;
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

public class ClubIntroCardActivity extends BaseActivity {

    @BindView(R.id.clubName)
    TextView clubName;
    @BindView(R.id.clubPosition)
    TextView clubPosition;
    @BindView(R.id.clubIntroduce)
    TextView clubIntroduce;

    private ClubDto mClubDto;

    @BindView(R.id.cardView)
    CardView cardView;
    @Override
    public int getContentViewRes() {
        return R.layout.activity_club_intro_card;
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
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setTitleContent("俱乐部名片");
        simpleTitleBar.setTitleColor(Color.parseColor("#ffffff"));
        simpleTitleBar.setToolBarBackgroundColor(Color.parseColor("#666666"));

    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        SystemBarHelper.tintStatusBar(this, 0xFF666666,0f);

        mClubDto = getIntent().getParcelableExtra("clubInfo");
        mViewManager.getImageView(R.id.iv_user_card_qr_code).setImageBitmap(QRCodeUtils.createQRCode(this,mClubDto.getClubUrl()));

        ImageView userPortrait = mViewManager.getImageView(R.id.userPortrait);
        GlideUtils.loadImage(this, mClubDto.getClubLogo(),userPortrait);

        ImageView clubBackGround = mViewManager.getImageView(R.id.clubBackground);
        GlideUtils.loadImage(mInstance,mClubDto.getBackgroundImg(),clubBackGround);

        clubName.setText(mClubDto.getClubName());
        clubPosition.setText("发布过" + mClubDto.getActivityCount() + "个活动，累计参加人数" +mClubDto.getActivityPersonCount() + "人");
        clubIntroduce.setText(mClubDto.getContent());
    }

    @OnClick({R.id.iv_weixin,R.id.iv_pengyouquan,R.id.iv_xiazai})
    public void openShare(View view){
        String fileName = "card_" + mClubDto.getClubName();
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
