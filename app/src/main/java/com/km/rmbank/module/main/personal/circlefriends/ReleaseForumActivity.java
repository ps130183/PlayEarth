package com.km.rmbank.module.main.personal.circlefriends;

import android.Manifest;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.hss01248.dialog.interfaces.MyItemDialogListener;
import com.km.rmbank.R;
import com.km.rmbank.adapter.ForumImageAdapter;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.CircleFriendsDto;
import com.km.rmbank.dto.ForumDto;
import com.km.rmbank.dto.ForumInfoDto;
import com.km.rmbank.event.CompressImageEvent;
import com.km.rmbank.event.UploadImageEvent;
import com.km.rmbank.mvp.model.CircleFriendsModel;
import com.km.rmbank.mvp.presenter.CircleFriendsPresenter;
import com.km.rmbank.mvp.view.ICircleFriendsView;
import com.km.rmbank.oldrecycler.BaseAdapter;
import com.km.rmbank.oldrecycler.RVUtils;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.DialogUtils;
import com.km.rmbank.utils.EventBusUtils;
import com.km.rmbank.utils.imageselector.ImageUtils;
import com.ps.commonadapter.adapter.wrapper.LoadMoreWrapper;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class ReleaseForumActivity extends BaseActivity<ICircleFriendsView,CircleFriendsPresenter> implements ICircleFriendsView {

    @BindView(R.id.et_rule_title)
    EditText etRuleTitle;

    @BindView(R.id.et_rule_content)
    EditText etRuleContent;

    @BindView(R.id.rv_forum_image)
    RecyclerView rvForumImage;

    private int imgPosition;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_release_forum;
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setTitleContent("编辑内容");
        simpleTitleBar.setRightMenuContent("发表");
        simpleTitleBar.setRightMenuClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showDefaultAlertDialog("是否要发表该信息？", new DialogUtils.ClickListener() {
                    @Override
                    public void clickConfirm() {
                        releaseFroum();
                    }
                });
            }
        });
    }

    @Override
    protected CircleFriendsPresenter createPresenter() {
        return new CircleFriendsPresenter(new CircleFriendsModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initRecyclerImage();
    }

    /**
     * 初始化 图片选择
     */
    private void initRecyclerImage() {
        RVUtils.setGridLayoutManage(rvForumImage, 3);
        final ForumImageAdapter adapter = new ForumImageAdapter(this);
        adapter.addData("");
        rvForumImage.setAdapter(adapter);
        adapter.setItemClickListener(new BaseAdapter.ItemClickListener<String>() {
            @Override
            public void onItemClick(final String itemData, int position) {
                if (TextUtils.isEmpty(itemData)) {
                    imgPosition = position;
                    openCamera();
                } else {
                    DialogUtils.showDefaultAlertDialog("是否要删除这张图片？", new DialogUtils.ClickListener() {
                        @Override
                        public void clickConfirm() {
                            int count = adapter.getItemCount();
                            if (count == 3 && !"".equals(adapter.getItemData(2))) {
                                adapter.addData("");
                            }
                            adapter.removeData(itemData);
                        }
                    });
                }
            }
        });
    }


    /**
     * 发表捡漏
     */
    private void releaseFroum() {
        ForumDto forumDto = new ForumDto();
        forumDto.setRuleTitle(etRuleTitle.getText().toString());
        forumDto.setRuleContent(etRuleContent.getText().toString());
        ForumImageAdapter adapter = (ForumImageAdapter) rvForumImage.getAdapter();
        forumDto.setForumImgContents(adapter.getAllData());
        if (forumDto.isEmpty()){
            showToast("请将内容补充完整");
            return;
        }
        getPresenter().releaseForum(forumDto);
    }

    /**
     * 打开选择 图片弹出框
     */
    private void openCamera() {
        PermissionGen.with(ReleaseForumActivity.this)
                .addRequestCode(1)
                .permissions(Manifest.permission.CAMERA)
                .request();
    }

    @PermissionSuccess(requestCode = 1)
    public void requestCameraSuccess() {
        DialogUtils.showBottomDialogForChoosePhoto(new MyItemDialogListener() {
            @Override
            public void onItemClick(CharSequence charSequence, int i) {
                switch (i) {
                    case 0:
                        ImageUtils.getImageFromCamera(ReleaseForumActivity.this, false, selectImageListener);
                        break;
                    case 1:
                        ImageUtils.getImageFromPhotoAlbum(ReleaseForumActivity.this,
                                ImageUtils.ImageType.PRODUCT,
                                ImageUtils.ImageNumber.SINGLE,
                                null,
                                selectImageListener);
                        break;
                }
            }
        });
    }

    @PermissionFail(requestCode = 1)
    public void requestCameraFail() {
        showToast("没有相机的使用权限");
    }

    private ImageUtils.SelectImageListener selectImageListener = new ImageUtils.SelectImageListener() {
        @Override
        public void onSuccess(List<String> photoList) {
            String imagePath = photoList.get(0);
            EventBusUtils.post(new UploadImageEvent(imagePath));

        }


    };
    private Dialog compressImageDialog;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void compressImageing(CompressImageEvent event) {
        if (compressImageDialog == null) {
            compressImageDialog = DialogUtils.showLoadingDialog("正在上传图片，请稍后。。。");
        } else {
            compressImageDialog.show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSelecedPhoto(final UploadImageEvent event) {
        com.km.rmbank.utils.ImageUtils.compressImage(event.getImagePath())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        getPresenter().uploadImage(s, imgPosition);
                    }
                });

    }

    @Override
    public void showForum(List<CircleFriendsDto> forumDtos, LoadMoreWrapper wrapper) {

    }

    @Override
    public void showMoreComment(List<CircleFriendsDto.CircleFriendsCommentDto> forumCommentDtos, int position) {

    }

    @Override
    public void praiseForumSuccess(int position) {

    }

    @Override
    public void addForumCommentSuccess(String commentContent, int position) {

    }

    @Override
    public void showMyForumInfo(ForumInfoDto forumInfoDto) {

    }

    @Override
    public void showImage(String imageUrl, int position) {
        compressImageDialog.dismiss();
        ForumImageAdapter adapter = (ForumImageAdapter) rvForumImage.getAdapter();
//        adapter.setImageUrl(imageUrl,position);
        adapter.addDataOnFirst(imageUrl);
        if (adapter.getItemCount() > 3) {
            adapter.removeData("");
        }
    }

    @Override
    public void releaseForumSuccess() {
        showToast("发表成功");
        finish();
    }


}
