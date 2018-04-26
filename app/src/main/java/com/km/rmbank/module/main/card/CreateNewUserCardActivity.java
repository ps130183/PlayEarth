package com.km.rmbank.module.main.card;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.event.UserInfoEvent;
import com.km.rmbank.mvp.model.UserInfoModel;
import com.km.rmbank.mvp.presenter.UserInfoPresenter;
import com.km.rmbank.mvp.view.IUserInfoView;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.DialogUtils;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

public class CreateNewUserCardActivity extends BaseActivity<IUserInfoView,UserInfoPresenter> implements IUserInfoView {

    private String userPortraitPath;
    private String userIntroduceContent;

    @BindView(R.id.userPortrait)
    GlideImageView userPortrait;

    @BindView(R.id.userIntroduce)
    TextView userIntroduce;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_create_new_user_card;
    }

    @Override
    public String getTitleContent() {
        return "个人资料";
    }

    @Override
    protected UserInfoPresenter createPresenter() {
        return new UserInfoPresenter(new UserInfoModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initUserInfo();
    }

    /**
     * 加载用户信息
     */
    private void initUserInfo(){
        //头像
        userPortraitPath = Constant.userInfo.getPortraitUrl();
        GlideUtils.loadImageOnPregress(userPortrait, userPortraitPath,null);

        //姓名
        mViewManager.setText(R.id.userName,Constant.userInfo.getName());

        //个性签名
        userIntroduceContent = Constant.userInfo.getPersonalizedSignature();
        mViewManager.setText(R.id.userIntroduce,userIntroduceContent);
    }

    /**
     * 编辑头像
     * @param view
     */
    public void editUserPortrait(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("editType",0);
        bundle.putString("portraitUrl",userPortraitPath);
        startActivity(EditUserInfoActivity.class,bundle);
    }


    /**
     * 编辑个人简介  个性签名
     * @param view
     */
    public void editUserIntroduce(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("editType",6);
        bundle.putString("content",userIntroduce.getText().toString());
        startActivity(EditUserInfoActivity.class,bundle);
    }
    /**
     * 接收设置的个人头像
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userPortraitSubscribe(UserInfoEvent event){
        int type = event.getInfoType();
        switch (type){
            case 0:
                userPortraitPath = event.getContent();
                GlideUtils.loadLocalImage(userPortrait,userPortraitPath);
                break;
            case 6:
                userIntroduceContent = event.getContent();
                mViewManager.setText(R.id.userIntroduce,userIntroduceContent);
                break;
        }

    }

    @Override
    public void saveUserInfoSuccess() {
        showToast("保存成功！");
        finish();
    }

    @Override
    public void createUserCardSuccess(String token) {

    }

    /**
     * 保存用户信息
     * @param view
     */
    public void saveUserInfo(View view) {
        if (Constant.userInfo == null){
            showToast("获取不到用户信息！");
            return;
        }
        getPresenter().saveUserInfo(userPortraitPath,userIntroduceContent);
    }
}
