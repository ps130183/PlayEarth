package com.km.rmbank.module.main.card;

import android.Manifest;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.hss01248.dialog.interfaces.MyItemDialogListener;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.module.main.HomeActivity;
import com.km.rmbank.mvp.model.UserInfoModel;
import com.km.rmbank.mvp.presenter.UserInfoPresenter;
import com.km.rmbank.mvp.view.IUserInfoView;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.DialogUtils;
import com.km.rmbank.utils.imageselector.ImageUtils;
import com.ps.glidelib.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class CreateUserCardActivity extends BaseActivity<IUserInfoView,UserInfoPresenter> implements IUserInfoView {

    @BindView(R.id.userPortrait)
    ImageView userPortrait;
    private String userPortraitUrl;
    private String newUserPortraitUrl;
    private UserInfoDto userInfoDto;

    @BindView(R.id.userName)
    EditText userName;

    @BindView(R.id.userPhone)
    EditText userPhone;

    @BindView(R.id.userPosition)
    EditText userPosition;

    @BindView(R.id.userAddress)
    EditText userAddress;

    @BindView(R.id.userIntro)
    EditText userIntro;


    @Override
    public int getContentViewRes() {
        return R.layout.activity_create_user_card;
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setTitleContent("编辑名片");
        simpleTitleBar.setRightMenuContent("保存");
        simpleTitleBar.setRightMenuClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInfo();
            }
        });
    }

    @Override
    protected UserInfoPresenter createPresenter() {
        return new UserInfoPresenter(new UserInfoModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initUserInfo();
    }

    private void initUserInfo(){
        try {
            userInfoDto = (UserInfoDto) Constant.userInfo.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        if (userInfoDto != null){
            userPortraitUrl = userInfoDto.getPortraitUrl();
            newUserPortraitUrl = userPortraitUrl;
            GlideUtils.loadImage(mInstance,userInfoDto.getPortraitUrl(),userPortrait);
            userName.setText(userInfoDto.getName());
            userPhone.setText(userInfoDto.getMobilePhone());
            userPosition.setText(userInfoDto.getPosition());
            userAddress.setText(userInfoDto.getDetailedAddress());
            userIntro.setText(userInfoDto.getPersonalizedSignature());
        }
    }

    @OnClick(R.id.userPortrait)
    public void selectUserPortrait(View view){
        PermissionGen.with(mInstance)
                .addRequestCode(1)
                .permissions(Manifest.permission.CAMERA)
                .request();
    }

    @PermissionSuccess(requestCode = 1)
    public void requestCameraSuccess(){
        DialogUtils.showBottomDialogForChoosePhoto(new MyItemDialogListener() {
            @Override
            public void onItemClick(CharSequence charSequence, int i) {
                switch (i){
                    case 0:
                        ImageUtils.getImageFromCamera(mInstance,true,selectImageListener);
                        break;
                    case 1:
                        ImageUtils.getImageFromPhotoAlbum(mInstance,
                                ImageUtils.ImageType.PROTRAIT,
                                ImageUtils.ImageNumber.SINGLE,
                                null,
                                selectImageListener);
                        break;
                }
            }
        });
    }
    @PermissionFail(requestCode = 1)
    public void requestCameraFail(){
        showToast("没有相机的使用权限");
    }

    private ImageUtils.SelectImageListener selectImageListener =  new ImageUtils.SelectImageListener() {
        @Override
        public void onSuccess(List<String> photoList) {
            newUserPortraitUrl = "";
            getPresenter().uploadUserPortrait(photoList.get(0));
        }
    };

    @Override
    public void uploadProtraitSuccess(String imageUri) {
        LogUtils.d("上传头像 ==  " + imageUri);
        GlideUtils.loadImage(mInstance, imageUri,userPortrait);
        newUserPortraitUrl = imageUri;
//        Constant.userInfo.setPortraitUrl(imageUri);
    }

    @Override
    public void updateUserInfoResult(String result) {
        showToast("修改成功");
        Bundle bundle = new Bundle();
        bundle.putInt("position",4);
        startActivity(HomeActivity.class);
        finish();
    }

    @Override
    public void createUserCardSuccess(String token) {

    }

    /**
     * 保存用户信息
     */
    private void saveUserInfo(){
        String name = userName.getText().toString();
        String position = userPosition.getText().toString();
        String address = userAddress.getText().toString();
        String introduce = userIntro.getText().toString();
        if (TextUtils.isEmpty(newUserPortraitUrl)
                || TextUtils.isEmpty(name)
                || TextUtils.isEmpty(position)
                || TextUtils.isEmpty(address)
                || TextUtils.isEmpty(introduce)){
            showToast("请将名片信息补充完整");
            return;
        }
        if (!userPortrait.equals(newUserPortraitUrl) && "".equals(newUserPortraitUrl)){
            showToast("正在上传图片。。。");
            return;
        }
        userInfoDto.setPortraitUrl(newUserPortraitUrl);
        userInfoDto.setName(name);
        userInfoDto.setPosition(position);
        userInfoDto.setDetailedAddress(address);
        userInfoDto.setPersonalizedSignature(introduce);
        getPresenter().updateUserInfo(userInfoDto);
    }
}
