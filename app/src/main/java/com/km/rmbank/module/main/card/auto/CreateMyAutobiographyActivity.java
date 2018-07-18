package com.km.rmbank.module.main.card.auto;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.dto.AutobiographyDto;
import com.km.rmbank.event.UpdateEditContentEvent;
import com.km.rmbank.utils.dialog.WindowBottomDialog;
import com.km.rmbank.utils.imageselector.ImageUtils;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.ps.mrcyclerview.BViewHolder;
import com.ps.mrcyclerview.ItemViewConvert;
import com.ps.mrcyclerview.MRecyclerView;
import com.ps.mrcyclerview.click.OnClickItemListener;
import com.ruffian.library.RTextView;
import com.yancy.gallerypick.utils.ScreenUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class CreateMyAutobiographyActivity extends BaseActivity {

    private RTextView autoTitle;
    private MRecyclerView<AutobiographyDto> mRecyclerView;

    private WindowBottomDialog mImageDialog;

    private int curImagePosition = -1;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_create_my_autobiography;
    }

    @Override
    public String getTitleContent() {
        return "自传编辑";
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initRecycler();
        initDialog();
    }

    private void initRecycler() {

        List<AutobiographyDto> mDatas = new ArrayList<>();

        mRecyclerView = mViewManager.findView(R.id.autobiography);
        mRecyclerView.addContentLayout(R.layout.item_autobiography_image, new ItemViewConvert<AutobiographyDto>() {
            @Override
            public void convert(@NonNull BViewHolder holder, final AutobiographyDto mData, int position, @NonNull List<Object> payloads) {
                GlideImageView imageContent = holder.findView(R.id.imageContent);
                GlideUtils.loadLocalImage(imageContent, mData.getImagePath());
                ImageView remove = holder.findView(R.id.remove);
                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mRecyclerView.delete(mData);
                    }
                });
            }

        }).addContentLayout(R.layout.item_autobiography_text, new ItemViewConvert<AutobiographyDto>() {
            @Override
            public void convert(@NonNull BViewHolder holder, final AutobiographyDto mData, int position, @NonNull List<Object> payloads) {
                TextView content = holder.findView(R.id.content);
                content.setText(mData.getContent());
                ImageView remove = holder.findView(R.id.remove);
                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mRecyclerView.delete(mData);
                    }
                });
            }

        }).addHeaderLayout(R.layout.header_edit_autobiography, new ItemViewConvert() {
            @Override
            public void convert(@NonNull BViewHolder holder, Object mData, int position, @NonNull List payloads) {
                autoTitle = holder.findView(R.id.title);
                autoTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("editContentType", 0);//编辑的内容类型 0 代表标题
                        bundle.putString("content", autoTitle.getText().toString());
                        startActivity(EditAutoTextActivity.class, bundle);
                    }
                });
            }

        }).create();

        mRecyclerView.addClickItemListener(new OnClickItemListener() {
            @Override
            public void clickItem(Object mData, int position) {
                AutobiographyDto mAutobiography = (AutobiographyDto) mData;
                if (mAutobiography.getContentType() == 1) {//文字
                    Bundle bundle = new Bundle();
                    bundle.putInt("editContentType", mAutobiography.getContentType());//编辑的内容类型 1 代表正文内容
                    bundle.putInt("position", position);
                    bundle.putString("content", mAutobiography.getContent());
                    startActivity(EditAutoTextActivity.class, bundle);
                } else if (mAutobiography.getContentType() == 0) {//图片
                    curImagePosition = position;
                    mImageDialog.show();
                }

            }
        });
        mRecyclerView.loadDataOfNextPage(mDatas);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateEditContent(UpdateEditContentEvent event) {
        if (event.getEditContentType() == 0) {//更新标题
            autoTitle.setText(event.getContent());
        } else if (event.getEditContentType() == 1) {//更新文字内容
            AutobiographyDto autobiographyDto = new AutobiographyDto();
            autobiographyDto.setContent(event.getContent());
            autobiographyDto.setContentType(event.getEditContentType());
            if (event.getPosition() < 0) {
                mRecyclerView.insert(autobiographyDto);
            } else {
                mRecyclerView.update(autobiographyDto, event.getPosition(),"1");
            }

        }
    }

    /**
     * 初始化 弹出框
     */
    private void initDialog() {
        mImageDialog = new WindowBottomDialog(mInstance, "取消", "拍照", "从相册选择");
        mImageDialog.setOnClickShareDialog(new WindowBottomDialog.OnClickShareDialog() {
            @Override
            public void clickShareDialog(String itemName, int i) {
                mImageDialog.dimiss();
                int windowWidth = ScreenUtils.getScreenWidth(mInstance);
                switch (i) {
                    case 0:
                        ImageUtils.getSingleImageByCrop(mInstance, true, windowWidth, windowWidth / 3 * 2, selectImageListener);
                        break;
                    case 1:
                        ImageUtils.getSingleImageByCrop(mInstance, false, windowWidth, windowWidth / 3 * 2, selectImageListener);
                        break;
                    default:
                        curImagePosition = -1;
                        break;
                }
            }
        });
    }

    ImageUtils.SelectImageListener selectImageListener = new ImageUtils.SelectImageListener() {
        @Override
        public void onSuccess(List<String> photoList) {
            AutobiographyDto autobiographyDto = new AutobiographyDto();
            autobiographyDto.setContentType(0);
            autobiographyDto.setImagePath(photoList.get(0));
            if (curImagePosition >= 0){
                mRecyclerView.update(autobiographyDto,curImagePosition,"1");
            } else {
                mRecyclerView.insert(autobiographyDto);
            }
        }
    };

    /**
     * 添加文字内容
     *
     * @param view
     */
    public void addTextContent(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("editContentType", 1);//编辑的内容类型 1 代表正文内容
        startActivity(EditAutoTextActivity.class, bundle);
    }

    /**
     * 添加图片内容
     *
     * @param view
     */
    public void addImageContent(View view) {
        if (mImageDialog != null) {
            mImageDialog.show();
            curImagePosition = -1;
        }
    }
}
