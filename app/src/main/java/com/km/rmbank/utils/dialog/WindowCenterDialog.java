package com.km.rmbank.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.adapter.SuperLvHolder;
import com.hss01248.dialog.config.ConfigBean;
import com.km.rmbank.R;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;

/**
 * 身份证扫描提示
 */
public class WindowCenterDialog {
    private Dialog mDialog;
    private SuperLvHolder<String> superLvHolder;
    private Context context;
    private int mContentImageRes;
    private View.OnClickListener mOnClickListener;
    private View contentView;

    public WindowCenterDialog(Context context, int contentImageRes, View.OnClickListener onClickListener) {
        this.context = context;
        this.mContentImageRes = contentImageRes;
        this.mOnClickListener = onClickListener;

        superLvHolder = new SuperLvHolder<String>(context) {
            private GlideImageView imageView;
            @Override
            protected void findViews() {
                imageView = rootView.findViewById(R.id.imageView);
                int mWidth = ScreenUtils.getScreenWidth();
                imageView.getLayoutParams().width = mWidth / 3 * 2;
                imageView.getLayoutParams().height = mWidth / 3 * 2;
                GlideUtils.loadImageByRes(imageView,mContentImageRes);

                TextView confirm = rootView.findViewById(R.id.confirm);
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mDialog != null && mDialog.isShowing()){
                            mDialog.dismiss();
                        }
                        if (mOnClickListener != null){
                            mOnClickListener.onClick(v);
                        }
                    }
                });
            }

            @Override
            protected int setLayoutRes() {
                return R.layout.dialog_id_card_front_hint;
            }

            @Override
            public void assingDatasAndEvents(Context context, @Nullable String s) {

            }
        };
    }

    public WindowCenterDialog(Context context, @LayoutRes final int centerViewRes, final IViewConvert convert){
        this.context = context;
        contentView = LayoutInflater.from(context).inflate(centerViewRes,null,false);
        if (convert != null){
            convert.convert(contentView);
        }
//        mDialog = dialog.show();
//        superLvHolder = new SuperLvHolder<String>(context) {
//            @Override
//            protected void findViews() {
//                if (convert != null){
//                    convert.convert(rootView);
//                }
//            }
//
//            @Override
//            protected int setLayoutRes() {
//                return centerViewRes;
//            }
//
//            @Override
//            public void assingDatasAndEvents(Context context, @Nullable String s) {
//
//            }
//        };
    }

    public void show(){
        if (mDialog == null){
            if (mOnClickListener != null){
                mDialog =  StyledDialog.buildCustom(superLvHolder).show();
            } else {
                ConfigBean dialog = StyledDialog.buildCustom(contentView, Gravity.CENTER);
                dialog.setForceHeightPercent(1);
                dialog.setForceWidthPercent(1);
                mDialog = dialog.show();
            }
        } else {
            mDialog.show();
        }
    }

    public void dissmis(){
        if (mDialog != null){
            mDialog.dismiss();
        }
    }

    public interface IViewConvert{
        void convert(View view);
    }
}
