package com.km.rmbank.module.main.experience;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.module.login.LoginActivity;
import com.km.rmbank.oldrecycler.AppUtils;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.dialog.WindowBottomDialog;
import com.km.rmbank.utils.QRCodeUtils;
import com.km.rmbank.utils.UmengShareUtils;
import com.km.rmbank.utils.ViewUtils;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.File;
import java.io.FileNotFoundException;

import butterknife.OnClick;
import cn.jarlen.photoedit.operate.ImageObject;
import cn.jarlen.photoedit.operate.OperateUtils;
import cn.jarlen.photoedit.operate.OperateView;

public class ExperienceOfficerActivity extends BaseActivity {

    private OperateView operateView;

    private OperateUtils operateUtils;
    private WindowBottomDialog mShareDialog;
    @Override
    public int getContentViewRes() {
        return R.layout.activity_experience_officer;
    }

    @Override
    public String getTitleContent() {
        return "199体验官";
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setRightMenuRes(R.menu.toolbar_action_recent_share);
        simpleTitleBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.share){
                    if (Constant.userLoginInfo.isEmpty()){
                        showToast(getResources().getString(R.string.toast_not_login));
                        startActivity(LoginActivity.class);
                        return true;
                    }
                    mShareDialog.show();
                    return true;
                }
                return false;
            }
        });
    }

    private void fillContent() {
        operateUtils = new OperateUtils(mInstance);
        Bitmap resizeBmp = BitmapFactory.decodeResource(getResources(), R.mipmap.image_experience_office);//BitmapFactory.decodeFile(camera_path);
        operateView = new OperateView(mInstance, resizeBmp);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                resizeBmp.getWidth(), resizeBmp.getHeight());
        operateView.setLayoutParams(layoutParams);
        operateView.setPicScale(0.6f);
        operateView.setMultiAdd(false); // 设置此参数，可以添加多个图片
        addpic();
    }


    private void addpic() {
        String advertUrl = getIntent().getStringExtra("advertUrl");
        advertUrl = TextUtils.isEmpty(advertUrl) ? "" : advertUrl;
        if (!Constant.userLoginInfo.isEmpty()){
            advertUrl += "?token=" + Constant.userLoginInfo.getToken();
        }
        Bitmap bmp = QRCodeUtils.createQRCode(this,advertUrl);
        // ImageObject imgObject = operateUtils.getImageObject(bmp);
        ImageObject imgObject = operateUtils.getImageObject(bmp, operateView,
                OperateUtils.RIGHTBOTTOM, 88+154, 912+154);
        operateView.addItem(imgObject,false);
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initShareDialog();
        fillContent();
    }

    /**
     * 分享199体验官
     * @param view
     */
    @OnClick(R.id.applyAction)
    public void applyAction(View view){
        mShareDialog.show();
    }

    private void initShareDialog() {
        mShareDialog = new WindowBottomDialog(mInstance, "取消",  "分享微信好友", "分享朋友圈", "保存图片");
        mShareDialog.setOnClickShareDialog(new WindowBottomDialog.OnClickShareDialog() {
            @Override
            public void clickShareDialog(String itemName, int i) {
                mShareDialog.dimiss();
                switch (i) {
                    case 0://分享微信好友
                        shareExperience(SHARE_MEDIA.WEIXIN);
                        break;
                    case 1://分享朋友圈
                        shareExperience(SHARE_MEDIA.WEIXIN_CIRCLE);
                        break;
                    case 2://保存图片
                        String fileName = "experience_" + Constant.userInfo.getMobilePhone();
                        String filePath = AppUtils.getImagePath(fileName + ".png");
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
                        break;

                    default:

                        break;
                }
            }
        });
    }

    private void shareExperience(SHARE_MEDIA media){
        String fileName = "experience_" + Constant.userInfo.getMobilePhone();
        Bitmap imageBitmap = ViewUtils.saveBitmap(operateView,fileName);

        UmengShareUtils.openShareForImage(ExperienceOfficerActivity.this, imageBitmap, media, new UMShareListener() {
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
                showToast("取消分享");
            }
        });
    }
}
