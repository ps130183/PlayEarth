package com.km.rmbank.module.realname;

import android.Manifest;
import android.app.Dialog;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.entity.IDCardEntity;
import com.km.rmbank.mvp.model.CertifyIDCardModel;
import com.km.rmbank.mvp.presenter.CertifyIDCardPresenter;
import com.km.rmbank.mvp.view.CertifyIDCardView;
import com.km.rmbank.oldrecycler.AppUtils;
import com.km.rmbank.utils.DialogUtils;
import com.km.rmbank.utils.imageselector.ImageUtils;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.yancy.gallerypick.utils.ScreenUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class IDCardHandActivity extends BaseActivity<CertifyIDCardView,CertifyIDCardPresenter> implements CertifyIDCardView {

    private static final String ID_CARD_FRONT_PATH = AppUtils.getImagePath("idCardFront.jpg");
    private static final String ID_CARD_BACK_PATH = AppUtils.getImagePath("idCardBack.jpg");

    private String[] idCardImagePaths = {ID_CARD_FRONT_PATH,ID_CARD_BACK_PATH,""};

    private DialogUtils.IdCardScanHintDialog mDialog;
    private String[] locationPermission = {Manifest.permission.CAMERA};

    @BindView(R.id.btn_submit)
    Button btnSubmit;


    @Override
    public int getContentViewRes() {
        return R.layout.activity_idcard_hand;
    }

    @Override
    protected CertifyIDCardPresenter createPresenter() {
        return new CertifyIDCardPresenter(new CertifyIDCardModel());
    }

    @Override
    public String getTitleContent() {
        return "实名认证";
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        init();
    }

    private void init() {
        GlideImageView idCardHand = mViewManager.findView(R.id.ivIdCardHand);

        TextView idCardHnadHint = mViewManager.findView(R.id.tvIdCardHand);

        int windowHeight = ScreenUtils.getScreenHeight(mInstance);
        int mHeight = windowHeight - ConvertUtils.dp2px(142) - ScreenUtils.getStatusHeight(mInstance);
        int idcardHeight = mHeight / 2 - ConvertUtils.sp2px(70);

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) idCardHand.getLayoutParams();
        lp.topMargin = 170;
        lp.height = idcardHeight;
        lp.width = (int) (idcardHeight / 330.0 * 524);

        idCardHand.setLayoutParams(lp);

        LinearLayout.LayoutParams tvlp = (LinearLayout.LayoutParams) idCardHnadHint.getLayoutParams();
        tvlp.topMargin = 60;
        idCardHnadHint.setLayoutParams(tvlp);
        idCardHnadHint.setLayoutParams(tvlp);
    }

    @OnClick(R.id.ivIdCardHand)
    public void idCardHandHint(View view){
        if (mDialog == null){
            mDialog = new DialogUtils.IdCardScanHintDialog(mInstance, R.drawable.eg_idcard_hand, new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    PermissionGen.needPermission(mInstance, 1, locationPermission);
                }
            });
        }
        mDialog.show();
    }

    @OnClick(R.id.btn_submit)
    public void submit(View view){
        IDCardEntity entity = getIntent().getParcelableExtra("idCardInfo");
        getPresenter().uploadCardImage(idCardImagePaths,entity);
    }

    @PermissionSuccess(requestCode = 1)
    public void success(){
        openCamera();
    }

    /**
     * 打开照相机
     */
    private void openCamera(){
        ImageUtils.getImageFromCamera(mInstance, false, new ImageUtils.SelectImageListener() {
            @Override
            public void onSuccess(List<String> photoList) {
                GlideImageView idCardHand = mViewManager.findView(R.id.ivIdCardHand);
                idCardImagePaths[2] = photoList.get(0);
                GlideUtils.loadLocalImage(idCardHand,photoList.get(0));
                btnSubmit.setEnabled(true);
            }
        });
    }

    @Override
    public void certifyIDCardSuccess(String result) {
        if ("1".equals(result)){
            startActivity(CertifyCheckActivity.class);
        } else {
            showToast("实名认真失败，请检查身份证是否正确！");
        }

    }
}
