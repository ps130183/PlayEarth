package com.km.rmbank.module.realname;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.baidu.ocr.ui.camera.CameraNativeHelper;
import com.baidu.ocr.ui.camera.CameraView;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.entity.IDCardEntity;
import com.km.rmbank.oldrecycler.AppUtils;
import com.km.rmbank.utils.StringUtils;
import com.km.rmbank.utils.dialog.WindowCenterDialog;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.yancy.gallerypick.utils.ScreenUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 实名认证
 */
public class IdentityVerificationActivity extends BaseActivity {

    @BindView(R.id.btn_submit)
    Button btnSubmit;

    private TextView idCardFrontHint;
    private TextView idCardBackHint;

    private static final String ID_CARD_FRONT_PATH = AppUtils.getImagePath("idCardFront.jpg");
    private static final String ID_CARD_BACK_PATH = AppUtils.getImagePath("idCardBack.jpg");

    private WindowCenterDialog frontDialog;
    private WindowCenterDialog backDialog;

    private IDCardEntity mIDCardEntity;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_identity_verification;
    }

    @Override
    public String getTitleContent() {
        return "实名认证";
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        init();
    }

    @Override
    protected void onDestroy() {
        CameraNativeHelper.release();
        // 释放内存资源
        OCR.getInstance().release();
        super.onDestroy();

    }

    private void init() {
        GlideImageView idCardFront = mViewManager.findView(R.id.ivIdCardFront);
        GlideImageView idCardBack = mViewManager.findView(R.id.ivIdCardBack);

        idCardFrontHint = mViewManager.findView(R.id.tvIdCardFront);
        idCardBackHint = mViewManager.findView(R.id.tvIdCardBack);

        int windowHeight = ScreenUtils.getScreenHeight(mInstance);
        int mHeight = windowHeight - ConvertUtils.dp2px(142) - ScreenUtils.getStatusHeight(mInstance);
        int idcardHeight = mHeight / 2 - ConvertUtils.sp2px(70);

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) idCardFront.getLayoutParams();
        lp.topMargin = 40;
        lp.height = idcardHeight;
        lp.width = (int) (idcardHeight / 330.0 * 524);

        idCardFront.setLayoutParams(lp);
        idCardBack.setLayoutParams(lp);

        LinearLayout.LayoutParams tvlp = (LinearLayout.LayoutParams) idCardFrontHint.getLayoutParams();
        tvlp.topMargin = 20;
        idCardFrontHint.setLayoutParams(tvlp);
        idCardBackHint.setLayoutParams(tvlp);

        initAccessTokenWithAkSk();
    }

    private void initAccessTokenWithAkSk() {
        OCR.getInstance().initAccessTokenWithAkSk(
                new OnResultListener<AccessToken>() {
                    @Override
                    public void onResult(AccessToken result) {

                        // 本地自动识别需要初始化
                        initLicense();

                        Log.d("MainActivity", "onResult: " + result.toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                showToast("初始化认证成功");
//                                Toast.makeText(MainActivity.this, "初始化认证成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onError(OCRError error) {
                        error.printStackTrace();
                        Log.e("MainActivity", "onError: " + error.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showToast("初始化认证失败");
//                                Toast.makeText(MainActivity.this, "初始化认证失败,请检查 key", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }, getApplicationContext(),
                // 需要自己配置 https://console.bce.baidu.com
                "G5CL7IhRxohKLsDIgpG5uN48",
                // 需要自己配置 https://console.bce.baidu.com
                "7CA91oC4acgPVwyKPQfGcCnMCWwvyNvQ");
    }

    private void initLicense() {
        CameraNativeHelper.init(this, OCR.getInstance().getLicense(),
                new CameraNativeHelper.CameraNativeInitCallback() {
                    @Override
                    public void onError(int errorCode, Throwable e) {
                        final String msg;
                        switch (errorCode) {
                            case CameraView.NATIVE_SOLOAD_FAIL:
                                msg = "加载so失败，请确保apk中存在ui部分的so";
                                break;
                            case CameraView.NATIVE_AUTH_FAIL:
                                msg = "授权本地质量控制token获取失败";
                                break;
                            case CameraView.NATIVE_INIT_FAIL:
                                msg = "本地质量控制";
                                break;
                            default:
                                msg = String.valueOf(errorCode);
                        }
                        LogUtils.d(e.toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showToast("本地质量控制初始化错误，错误原因： " + msg);
                            }
                        });
                    }
                });
    }

    /**
     * 拍摄身份证  正面照
     *
     * @param view
     */
    @OnClick(R.id.ivIdCardFront)
    public void cameraIdCardFront(View view) {
        if (frontDialog == null) {
            frontDialog = new WindowCenterDialog(mInstance, R.drawable.eg_idcard_front, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scanIdCardFront();
                }
            });
        }
        frontDialog.show();
    }

    /**
     * 拍摄身份证  反面照
     *
     * @param view
     */
    @OnClick(R.id.ivIdCardBack)
    public void cameraIdCardBack(View view) {
        if (backDialog == null) {
            backDialog = new WindowCenterDialog(mInstance, R.drawable.eg_idcard_back, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scanIdCardBack();
                }
            });
        }
        backDialog.show();
    }

    /**
     * 扫描身份证正面
     */
    private void scanIdCardFront() {
        Intent intent = new Intent(mInstance, CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, ID_CARD_FRONT_PATH);
        intent.putExtra(CameraActivity.KEY_NATIVE_ENABLE, true);
        // KEY_NATIVE_MANUAL设置了之后CameraActivity中不再自动初始化和释放模型
        // 请手动使用CameraNativeHelper初始化和释放模型
        // 推荐这样做，可以避免一些activity切换导致的不必要的异常
        intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL, true);
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
        startActivityForResult(intent, 1);
    }

    /**
     * 扫描身份证反面
     */
    private void scanIdCardBack() {
        Intent intent = new Intent(mInstance, CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                ID_CARD_BACK_PATH);
        intent.putExtra(CameraActivity.KEY_NATIVE_ENABLE, true);
        // KEY_NATIVE_MANUAL设置了之后CameraActivity中不再自动初始化和释放模型
        // 请手动使用CameraNativeHelper初始化和释放模型
        // 推荐这样做，可以避免一些activity切换导致的不必要的异常
        intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL, true);
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_BACK);
        startActivityForResult(intent, 1);
    }


    @OnClick(R.id.btn_submit)
    public void submit(View view) {
        if (mIDCardEntity.idCardBackIsEmpty() || mIDCardEntity.idCardFrontIsEmpty()) {
            showToast("请拍摄身份证正反面！");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable("idCardInfo", mIDCardEntity);
        startActivity(IDCardHandActivity.class, bundle);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
                if (!TextUtils.isEmpty(contentType)) {
                    if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
                        recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, ID_CARD_FRONT_PATH);
                    } else if (CameraActivity.CONTENT_TYPE_ID_CARD_BACK.equals(contentType)) {
                        recIDCard(IDCardParams.ID_CARD_SIDE_BACK, ID_CARD_BACK_PATH);
                    }
                }
            }
        }

    }

    /**
     * 解析身份证图片
     *
     * @param idCardSide 身份证正反面
     * @param filePath   图片路径
     */
    private void recIDCard(final String idCardSide, String filePath) {
        showLoading();
        GlideImageView idCard;
        if (IDCardParams.ID_CARD_SIDE_FRONT.equals(idCardSide)) {//正面
            idCard = mViewManager.findView(R.id.ivIdCardFront);
        } else {//反面
            idCard = mViewManager.findView(R.id.ivIdCardBack);
        }
        GlideUtils.loadLocalImage(idCard, filePath);

        IDCardParams param = new IDCardParams();
        param.setImageFile(new File(filePath));
        // 设置身份证正反面
        param.setIdCardSide(idCardSide);
        // 设置方向检测
        param.setDetectDirection(true);
        // 设置图像参数压缩质量0-100, 越大图像质量越好但是请求时间越长。 不设置则默认值为20
        param.setImageQuality(40);
        OCR.getInstance().recognizeIDCard(param, new OnResultListener<IDCardResult>() {
            @Override
            public void onResult(IDCardResult result) {
                if (result != null) {
                    if (mIDCardEntity == null) {
                        mIDCardEntity = new IDCardEntity();
                    }

                    if (result.getName() != null) {//姓名
                        mIDCardEntity.setName(result.getName().toString());
                    }
                    if (result.getGender() != null) {//性别
                        mIDCardEntity.setSex(result.getGender().toString());
                    }
                    if (result.getEthnic() != null) {//民族
                        mIDCardEntity.setNation(result.getEthnic().toString());
                    }
                    if (result.getIdNumber() != null) {//身份证号
                        mIDCardEntity.setNum(result.getIdNumber().toString());
                    }
                    if (result.getAddress() != null) {//籍贯地址
                        mIDCardEntity.setAddress(result.getAddress().toString());
                    }
                    if (result.getSignDate() != null) {//开始时间
                        mIDCardEntity.setStartDate(result.getSignDate().toString());
                    }
                    if (result.getExpiryDate() != null) {//结束时间
                        mIDCardEntity.setEndDate(result.getExpiryDate().toString());
                    }
                    if (result.getIssueAuthority() != null) {//签发机关
                        mIDCardEntity.setUnit(result.getIssueAuthority().toString());
                    }

                    if (IDCardParams.ID_CARD_SIDE_FRONT.equals(idCardSide)) {//正面
                        if (mIDCardEntity.idCardFrontIsEmpty()) {
                            idCardFrontHint.setText(StringUtils.getSpannableTextColor("身份证正面照(不可用，请重新拍摄)", "不可用，请重新拍摄", Color.RED));
                        } else {
                            idCardFrontHint.setText(StringUtils.getSpannableTextColor("身份证正面照(可用)", "可用", Color.GREEN));
                        }
                    } else {//反面
                        if (mIDCardEntity.idCardBackIsEmpty()) {
                            idCardBackHint.setText(StringUtils.getSpannableTextColor("身份证反面照(不可用，请重新拍摄)", "不可用，请重新拍摄", Color.RED));
                        } else {
                            idCardBackHint.setText(StringUtils.getSpannableTextColor("身份证反面照(可用)", "可用", Color.GREEN));
                        }
                    }


                    if (!mIDCardEntity.idCardFrontIsEmpty() && !mIDCardEntity.idCardBackIsEmpty()) {
                        LogUtils.d(mIDCardEntity.toString());
                        btnSubmit.setEnabled(true);
                    }
                    hideLoading();
                }
            }

            @Override
            public void onError(OCRError error) {
                hideLoading();
                if (IDCardParams.ID_CARD_SIDE_FRONT.equals(idCardSide)) {//正面
                    idCardFrontHint.setText(StringUtils.getSpannableTextColor("身份证正面照(不可用，请重新拍摄)", "不可用，请重新拍摄", Color.RED));
                } else {//反面
                    idCardBackHint.setText(StringUtils.getSpannableTextColor("身份证反面照(不可用，请重新拍摄)", "不可用，请重新拍摄", Color.RED));
                }
                showToast("证件照片模糊，请重新拍摄身份证" + (idCardSide.equals(IDCardParams.ID_CARD_SIDE_FRONT) ? "正面照!" : "反面照!"));
                Log.d("MainActivity", "onError: " + error.getMessage());
            }
        });
    }
}
