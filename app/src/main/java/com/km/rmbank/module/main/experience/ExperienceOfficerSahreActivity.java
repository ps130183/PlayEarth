package com.km.rmbank.module.main.experience;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.zxing.qrcode.encoder.QRCode;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.utils.QRCodeUtils;
import com.yancy.gallerypick.utils.ScreenUtils;

import cn.jarlen.photoedit.operate.ImageObject;
import cn.jarlen.photoedit.operate.OperateUtils;
import cn.jarlen.photoedit.operate.OperateView;

public class ExperienceOfficerSahreActivity extends BaseActivity {

    private OperateView operateView;
    private FrameLayout flContent;

    private OperateUtils operateUtils;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_experience_officer_sahre;
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        flContent = mViewManager.findView(R.id.flContent);
        operateUtils = new OperateUtils(this);
        fillContent();
    }

    private void fillContent() {
        Bitmap resizeBmp = BitmapFactory.decodeResource(getResources(), R.mipmap.image_experience_office);//BitmapFactory.decodeFile(camera_path);
        operateView = new OperateView(mInstance, resizeBmp);
        int width = ScreenUtils.getScreenWidth(this);
        int height = ScreenUtils.getScreenHeight(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                resizeBmp.getWidth(), resizeBmp.getHeight());
        operateView.setLayoutParams(layoutParams);
        flContent.addView(operateView);
        operateView.setMultiAdd(true); // 设置此参数，可以添加多个图片
        addpic();
    }

    private void addpic() {

        Bitmap bmp = QRCodeUtils.createQRCode(this,"http://www.baidu.com");
        // ImageObject imgObject = operateUtils.getImageObject(bmp);
        ImageObject imgObject = operateUtils.getImageObject(bmp, operateView,
                1, 0, 0);
        operateView.addItem(imgObject,false);
    }
}


