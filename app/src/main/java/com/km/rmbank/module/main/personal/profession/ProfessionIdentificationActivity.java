package com.km.rmbank.module.main.personal.profession;

import android.Manifest;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.mvp.model.ProfessionIdentificationModel;
import com.km.rmbank.mvp.presenter.ProfessionIdentificationPresenter;
import com.km.rmbank.mvp.view.ProfessionIdentificationView;
import com.km.rmbank.utils.dialog.WindowBottomDialog;
import com.km.rmbank.utils.imageselector.ImageUtils;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.yancy.gallerypick.utils.ScreenUtils;

import java.util.List;

import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class ProfessionIdentificationActivity extends BaseActivity<ProfessionIdentificationView,ProfessionIdentificationPresenter> implements  ProfessionIdentificationView {

    private WindowBottomDialog mSelectImageDialog;

    private String imagePath = null;
    @Override
    public int getContentViewRes() {
        return R.layout.activity_profession_identification;
    }

    @Override
    public String getTitleContent() {
        return "职业认证";
    }

    @Override
    protected ProfessionIdentificationPresenter createPresenter() {
        return new ProfessionIdentificationPresenter(new ProfessionIdentificationModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initView();
        initDialog();
    }

    private void initView(){
        ImageView uploadImage = mViewManager.findView(R.id.uploadImage);
        int width = ScreenUtils.getScreenWidth(mInstance) * 43 / 75;
        uploadImage.getLayoutParams().width = (int) (width * 1.5);
        uploadImage.getLayoutParams().height = (int) ((width * 108 / 215) * 1.5);


    }

    private void initDialog(){
        mSelectImageDialog = new WindowBottomDialog(mInstance,"取消","拍照","从相册选择");
        mSelectImageDialog.setOnClickShareDialog(new WindowBottomDialog.OnClickShareDialog() {
            @Override
            public void clickShareDialog(String itemName, int i) {
                mSelectImageDialog.dimiss();
                if (i == 0){//拍照
                    PermissionGen.needPermission(mInstance, 1, Manifest.permission.CAMERA);
                } else if (i == 1){//相册
                    //默认多选
                    ImageUtils.getImageFromPhotoAlbum(mInstance,
                            ImageUtils.ImageType.PRODUCT,
                            ImageUtils.ImageNumber.SINGLE,
                            null,
                            selectImageListener);
                }
            }
        });
    }

    @PermissionSuccess(requestCode = 1)
    public void success() {
        ImageUtils.getImageFromCamera(mInstance, false, selectImageListener);
    }

    /**
     * 获取图片成功
     */
    private ImageUtils.SelectImageListener selectImageListener = new ImageUtils.SelectImageListener() {
        @Override
        public void onSuccess(List<String> photoList) {
            imagePath = photoList.get(0);

            GlideImageView imageView = mViewManager.findView(R.id.uploadImage);
            GlideUtils.loadImageFitWidth(imageView,imagePath,null);

            ImageView openCamera = mViewManager.findView(R.id.openCamera);
            if (openCamera.getVisibility() == View.VISIBLE){
                openCamera.setVisibility(View.GONE);

                Button submit = mViewManager.findView(R.id.submit);
                submit.setEnabled(true);
            }
        }
    };

    /**
     * 提交
     * @param view
     */
    public void submit(View view) {
        if (!TextUtils.isEmpty(imagePath)){
            getPresenter().submitProfessionIdentification(imagePath);
        }
    }

    @Override
    public void identificationSuccess() {
        startActivity(ProfessionFinishedActivity.class);
    }

    /**
     * 选择相片
     * @param view
     */
    public void selectImage(View view) {
        mSelectImageDialog.show();
    }
}
