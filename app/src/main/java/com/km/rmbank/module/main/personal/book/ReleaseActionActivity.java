package com.km.rmbank.module.main.personal.book;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hss01248.dialog.interfaces.MyItemDialogListener;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.dto.ReleaseActionDetailsDto;
import com.km.rmbank.entity.BookVenueApplyDto;
import com.km.rmbank.entity.ImageEntity;
import com.km.rmbank.event.UploadImageEvent;
import com.km.rmbank.module.main.personal.member.goodsmanager.CreateNewGoodsActivity;
import com.km.rmbank.mvp.model.ReleaseActionModel;
import com.km.rmbank.mvp.presenter.ReleaseActionPresenter;
import com.km.rmbank.mvp.view.ReleaseActionView;
import com.km.rmbank.utils.DateUtils;
import com.km.rmbank.utils.EventBusUtils;
import com.km.rmbank.utils.PickerUtils;
import com.km.rmbank.utils.dialog.DialogUtils;
import com.km.rmbank.utils.dialog.WindowBottomDialog;
import com.km.rmbank.utils.imageselector.ImageUtils;
import com.lvfq.pickerview.TimePickerView;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.ps.mrcyclerview.BViewHolder;
import com.ps.mrcyclerview.ItemViewConvert;
import com.ps.mrcyclerview.MRecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class ReleaseActionActivity extends BaseActivity<ReleaseActionView,ReleaseActionPresenter> implements ReleaseActionView {


    private WindowBottomDialog mSelectImageDialog;
    @Override
    public int getContentViewRes() {
        return R.layout.activity_release_action;
    }

    @Override
    public String getTitleContent() {
        return "活动发布";
    }

    @Override
    protected ReleaseActionPresenter createPresenter() {
        return new ReleaseActionPresenter(new ReleaseActionModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initRecycler();
        initSelectImageDialog();
        initView();
    }

    private void initView(){
        BookVenueApplyDto bookVenueApplyDto = getIntent().getParcelableExtra("bookVenueApplyInfo");
        mViewManager.setText(R.id.start_time,DateUtils.getInstance().dateToString(new Date(bookVenueApplyDto.getStartDate()),DateUtils.YMDHM));
//        mViewManager.setText(R.id.end_time,DateUtils.getInstance().dateToString(new Date(bookVenueApplyDto.getEndDate()),DateUtils.YMDHM));
    }

    private void initRecycler(){
        final MRecyclerView<ImageEntity> mRecyclerView = mViewManager.findView(R.id.imageRecycler);
        mRecyclerView.addLoadMoreFinishRes(0)
                .refreshRecycler()
                .addContentLayout(R.layout.item_select_image, new ItemViewConvert<ImageEntity>() {
            @Override
            public void convert(@NonNull BViewHolder holder, final ImageEntity mData, int position, @NonNull List<Object> payloads) {
                GlideImageView imageView = holder.findView(R.id.iv_image);
                GlideUtils.loadLocalImage(imageView,mData.getImagePath(),false);

                holder.findView(R.id.iv_delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogUtils.showDefaultAlertDialog("是否要删除改照片？", new DialogUtils.ClickListener() {
                            @Override
                            public void clickConfirm() {
                                mRecyclerView.delete(mData);
                            }
                        });
                    }
                });
            }

        }).addFooterLayout(R.layout.item_rv_addimage, new ItemViewConvert() {
            @Override
            public void convert(@NonNull BViewHolder holder, Object mData, int position, @NonNull List payloads) {
                holder.setOnItemClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PermissionGen.needPermission(mInstance, 1, Manifest.permission.CAMERA);
                    }
                });
            }
        }).create();
    }

    @PermissionSuccess(requestCode = 1)
    public void getCarmeraPermissionSuccess() {
        if (mSelectImageDialog != null){
            mSelectImageDialog.show();
        }
    }

    @PermissionFail(requestCode = 1)
    public void getCameraPermissionFail() {
        showToast("没有相机的使用权限");
    }


    /**
     * 初始化选择相片弹出框
     */
    private void initSelectImageDialog(){
        mSelectImageDialog = new WindowBottomDialog(mInstance,"取消","拍照","我的相册");
        mSelectImageDialog.setOnClickShareDialog(new WindowBottomDialog.OnClickShareDialog() {
            @Override
            public void clickShareDialog(String itemName, int i) {
                mSelectImageDialog.dimiss();
                switch (i){
                    case 0://拍照
                        ImageUtils.getImageFromCamera(mInstance, false, selectImageListener);
                        break;
                    case 1://我的相册
                        //默认多选
                        ImageUtils.getImageFromPhotoAlbum(mInstance,
                                ImageUtils.ImageType.PRODUCT,
                                ImageUtils.ImageNumber.MULTIPLE,
                                null,
                                selectImageListener);
                        break;
                }
            }
        });
    }

    private ImageUtils.SelectImageListener selectImageListener = new ImageUtils.SelectImageListener() {
        @Override
        public void onSuccess(final List<String> photoList) {
            List<ImageEntity> imageEntities = new ArrayList<>();
            for (String imagePath : photoList){
                imageEntities.add(new ImageEntity(imagePath));
            }
            MRecyclerView<ImageEntity> mRecyclerView = mViewManager.findView(R.id.imageRecycler);
            mRecyclerView.loadDataOfNextPage(imageEntities);
        }
    };

//    public void selectTime(View view) {
//        final EditText textView = (EditText) view;
//        String curTime = DateUtils.getInstance().dateToString(new Date(), DateUtils.YMDHM);
//        if (view.getId() == R.id.end_time){
//            EditText startTime = mViewManager.findView(R.id.start_time);
//            curTime = startTime.getText().toString();
//        }
//        PickerUtils.alertTimerPicker(mInstance, TimePickerView.Type.ALL, curTime,
//                DateUtils.YMDHM, new PickerUtils.TimerPickerCallBack() {
//                    @Override
//                    public void onTimeSelect(String date) {
//                        textView.setText(date);
//                    }
//                });
//    }

    @Override
    public void releaseActionSuccess() {
        startActivity(BookVenueManageActivity.class);
        showToast("提交成功，请等待工作人员审核！");

    }

    @Override
    public void showReleaseActionDetails(ReleaseActionDetailsDto releaseActionDetailsDto) {

    }

    /**
     * 提交发布活动
     * @param view
     */
    public void submitReleaseAction(View view) {
        EditText etActionTitle = mViewManager.findView(R.id.et_action_title);
        TextView etStartTime = mViewManager.findView(R.id.start_time);
        TextView etEndTime = mViewManager.findView(R.id.end_time);
        MRecyclerView<ImageEntity> mRecyclerView = mViewManager.findView(R.id.imageRecycler);
        EditText tvTextContent = mViewManager.findView(R.id.text_content);

        String placeReservationId = getIntent().getStringExtra("placeReservationId");
        String title = etActionTitle.getText().toString();
        String startTime = etStartTime.getText().toString();
        String endTime = etEndTime.getText().toString();
        List<ImageEntity> imageEntities = mRecyclerView.getAllDatas();
        String textContent = tvTextContent.getText().toString();
        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime)
                && !TextUtils.isEmpty(textContent) && imageEntities.size() > 0){
            getPresenter().releaseAction(title,startTime,endTime,placeReservationId,textContent,imageEntities);
        }
    }
}
