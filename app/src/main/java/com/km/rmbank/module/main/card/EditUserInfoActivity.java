package com.km.rmbank.module.main.card;

import android.Manifest;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.entity.IndustryEntity;
import com.km.rmbank.event.UserInfoEvent;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.DialogUtils;
import com.km.rmbank.utils.EventBusUtils;
import com.km.rmbank.utils.imageselector.ImageUtils;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.MultiItemTypeAdapter;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.yancy.gallerypick.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class EditUserInfoActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener {


    @BindView(R.id.userPortrait)
    GlideImageView userPortrait;
    private DialogUtils.CustomBottomDialog mSelectPhotoDialog;
    private String userPortraitPath;

    private int editType = -1;
    private int maxTextNum = 25;


    @Override
    public int getContentViewRes() {
        return R.layout.activity_edit_user_info;
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        editType = getIntent().getIntExtra("editType", -1);
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        switch (editType) {
            case 0:
                simpleTitleBar.setTitleContent("设置个人头像");
                simpleTitleBar.setRightMenuRes(R.menu.toolbar_user_card_more);
                simpleTitleBar.setOnMenuItemClickListener(this);
                break;
            case 6:
                simpleTitleBar.setTitleContent("个性签名");
                break;
        }

        if (editType == 0) {
            simpleTitleBar.setRightMenuRes(R.menu.toolbar_user_card_more);
            simpleTitleBar.setOnMenuItemClickListener(this);
        } else if (editType > 0) {
            simpleTitleBar.setRightMenuContent("确定");
            simpleTitleBar.setRightMenuClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirmSaveUserInfo();
                }
            });
        }

    }

    @Override
    public View.OnClickListener getLeftClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                onClickBackListener();
            }
        };

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onClickBackListener();
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        init();
    }

    private void init() {
        switch (editType) {
            case 0://头像
                initUserPortrait();
                break;
            case 6://个性签名
                editUserIntroduce();
                break;
            default:
                break;
        }
    }

    /**
     * 初始化 用户头像
     */
    private void initUserPortrait() {
        userPortrait.setVisibility(View.VISIBLE);
        int mWidth = ScreenUtils.getScreenWidth(mInstance);
        int portraitWidth = mWidth - ConvertUtils.dp2px(64);
        userPortrait.getLayoutParams().height = portraitWidth;
        userPortrait.getLayoutParams().width = portraitWidth;
        initSelectPhotoDialog();

        userPortraitPath = getIntent().getStringExtra("portraitUrl");
        if (!TextUtils.isEmpty(userPortraitPath)) {
            GlideUtils.loadImageOnPregress(userPortrait, userPortraitPath, null);
        } else {
            GlideUtils.loadImageByRes(userPortrait, R.mipmap.icon_default_protrait);
        }
    }


    /**
     * 编辑个性签名
     */
    private void editUserIntroduce(){
        RelativeLayout userIntroduce = mViewManager.findView(R.id.userIntroduce);
        final EditText etUserIntroduce = mViewManager.findView(R.id.etUserIntroduce);
        final TextView introduceLimit = mViewManager.findView(R.id.introduceLimit);
        userIntroduce.setVisibility(View.VISIBLE);

        etUserIntroduce.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.length();
                if (length > maxTextNum){
                    etUserIntroduce.setText(s.subSequence(0,maxTextNum));
                    etUserIntroduce.setSelection(maxTextNum);
                }
                introduceLimit.setText(etUserIntroduce.getText().toString().length() + " / " + maxTextNum);
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.more) {
            if (mSelectPhotoDialog != null) {
                mSelectPhotoDialog.show();
            }
        }
        return false;
    }

    private void initSelectPhotoDialog() {
        mSelectPhotoDialog = new DialogUtils.CustomBottomDialog(mInstance, "取消", "拍照", "从相册选择");
        mSelectPhotoDialog.setOnClickShareDialog(new DialogUtils.CustomBottomDialog.OnClickShareDialog() {
            @Override
            public void clickShareDialog(String itemName, int i) {
                mSelectPhotoDialog.dimiss();
                switch (i) {
                    case 0://拍照
                        PermissionGen.needPermission(mInstance, 1, Manifest.permission.CAMERA);
                        break;
                    case 1://从相册选择
                        //默认多选
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

    @PermissionSuccess(requestCode = 1)
    public void success() {
        ImageUtils.getSingleImageByCrop(mInstance, true, userPortrait.getWidth(), userPortrait.getHeight(), selectImageListener);
    }


    /**
     * 获取图片成功
     */
    private ImageUtils.SelectImageListener selectImageListener = new ImageUtils.SelectImageListener() {
        @Override
        public void onSuccess(List<String> photoList) {
            userPortraitPath = photoList.get(0);
            GlideUtils.loadLocalImage(userPortrait, userPortraitPath);
        }
    };

    /**
     * 返回监听
     */
    private void onClickBackListener() {
        if (editType == 0) {
            if (userPortraitPath.indexOf("http") >= 0) {
                return;
            }
            EventBusUtils.post(new UserInfoEvent(editType,userPortraitPath));
        }
    }

    /**
     * 保存用户信息
     */
    private void confirmSaveUserInfo(){
        if (editType == 6){//个性签名
            EditText introduce = mViewManager.findView(R.id.etUserIntroduce);
            EventBusUtils.post(new UserInfoEvent(editType,introduce.getText().toString()));
        }
        finish();
    }

}
