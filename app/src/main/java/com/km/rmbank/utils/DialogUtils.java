package com.km.rmbank.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.adapter.SuperLvHolder;
import com.hss01248.dialog.bottomsheet.BottomSheetBean;
import com.hss01248.dialog.config.BottomSheetStyle;
import com.hss01248.dialog.config.ConfigBean;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.hss01248.dialog.interfaces.MyItemDialogListener;
import com.km.rmbank.R;
import com.km.rmbank.oldrecycler.AppUtils;
import com.km.rmbank.oldrecycler.ViewUtils;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.MultiItemTypeAdapter;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamangkeji on 17/2/15.
 */

public class DialogUtils {

    private static List<String> optionsPhoto;
    private static List<BottomSheetBean> shareBottoms;
    static {
        optionsPhoto = new ArrayList<>();
        optionsPhoto.add("拍照");
        optionsPhoto.add("我的相册");

        shareBottoms = new ArrayList<>();
        shareBottoms.add(new BottomSheetBean(R.mipmap.icon_share_weixin,""));
        shareBottoms.add(new BottomSheetBean(R.mipmap.icon_share_pengyouquan,""));
        shareBottoms.add(new BottomSheetBean(R.mipmap.icon_share_xiazia,""));
    }

    //单例
    private static DialogUtils instance = null;

    private DialogUtils() {
    }

    public static DialogUtils getInstance() {
        if (instance == null) {
            synchronized (DialogUtils.class) {
                if (instance == null) {
                    instance = new DialogUtils();
                }
            }
        }
        return instance;
    }

    private AlertDialog.Builder getAlertBuilder(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        return builder;
    }

    /**
     * Android 样式的弹出提示框
     * @param activity
     * @param title
     * @param message
     * @param layoutRes
     * @param clickConfirmListener
     * @return
     */
    private AlertDialog showAlertDialog(@NonNull Activity activity, @NonNull String title, String message, @LayoutRes int layoutRes,
                                        final OnClickConfirmListener clickConfirmListener) {
        View customView = null;
        AlertDialog.Builder builder = getAlertBuilder(activity);
        builder.setTitle(title);
        if (!TextUtils.isEmpty(message)) {
            builder.setMessage(message);
        }
        if (layoutRes > 0) {
            customView = ViewUtils.getView(activity, layoutRes);
            builder.setView(customView);
        }
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    dismissDialog(dialog,clickConfirmListener.clickConfirm(dialog,which));
            }
        });
        builder.setCancelable(false);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismissDialog(dialog,true);
            }
        });
        final AlertDialog ad = builder.create();
        ad.show();
        return ad;
    }

    /**
     * ios风格
     * @param hintContent
     * @param clickListener
     */
    public static void showDefaultAlertDialog(String hintContent, final ClickListener clickListener){
//        StyledDialog
        StyledDialog.buildIosAlert("提示", hintContent, new MyDialogListener() {
            @Override
            public void onFirst() {
//                clickConfirmListener.clickConfirm(null,0);

            }

            @Override
            public void onSecond() {
                clickListener.clickConfirm();
            }
        }).setBtnColor(R.color.text_color_blocka,R.color.colorAccent,R.color.colorAccent)
                .setBtnText("取消","确定").show();
    }

    /**
     * 显示加载提示框
     * @param content
     * @return
     */
    public static Dialog showLoadingDialog(String content){
        Dialog dialog = StyledDialog.buildMdLoading(content).show();
        return dialog;
    }

    /**
     * 带取消按钮的 底部弹出选择框
     * @param listContent
     * @param listener
     */
    public static void showBottomDialog(List<String> listContent, MyItemDialogListener listener){
        StyledDialog.buildBottomItemDialog(listContent, "取消", listener).show();
    }

    /**
     * 选照片提示框
     * @param listener
     */
    public static void showBottomDialogForChoosePhoto(MyItemDialogListener listener){
        showBottomDialog(optionsPhoto,listener);
    }

    /**
     * 分享dialog
     * @param listener
     */
    public static void showShareDialog(final ShareDialogClickListener listener){
        ConfigBean configBean = StyledDialog.buildBottomSheetGv("分享", shareBottoms, "取消", 3, new MyItemDialogListener() {
            @Override
            public void onItemClick(CharSequence charSequence, int i) {
                if (listener == null){
                    return;
                }
                if (i == 0){
                    listener.clickWeixin();
                } else if (i == 1){
                    listener.clickPengyouQuan();
                } else if ( i == 2){
                    listener.clickDownload();
                }
            }
        });

        BottomSheetStyle sheetStyle = BottomSheetStyle.newBuilder().iconSizeDp(30).build();
        configBean.setBottomSheetStyle(sheetStyle);
        configBean.show();

    }


    public interface ShareDialogClickListener{
        void clickWeixin();
        void clickPengyouQuan();
        void clickDownload();
    }
    /**
     * 隐藏
     * @param dialog
     * @param dismiss
     */
    private void dismissDialog(DialogInterface dialog, boolean dismiss){
        try {
            Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
            field.setAccessible(true);
            //   将mShowing变量设为false，表示对话框已关闭
            field.set(dialog, dismiss);
            dialog.dismiss();

        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
    }

    /**
     * 一般提示框
     *
     * @param activity
     * @param title
     * @param message
     * @param clickConfirmListener
     */
    public void showAlertDialog(@NonNull Activity activity, @NonNull String title, String message,
                                OnClickConfirmListener clickConfirmListener) {
        showAlertDialog(activity, title, message, 0, clickConfirmListener);
    }

    /**
     * 自定义弹出框
     *
     * @param activity
     * @param title
     * @param layoutRes
     * @param clickConfirmListener
     */
    public AlertDialog showCustomDialog(@NonNull Activity activity, @NonNull String title, @LayoutRes int layoutRes,
                                        OnClickConfirmListener clickConfirmListener) {
        return showAlertDialog(activity, title, null, layoutRes, clickConfirmListener);
    }

    public interface OnClickConfirmListener{
        boolean clickConfirm(DialogInterface dialog, int which);
    }

    public interface ClickListener{
        void clickConfirm();
//        void clickCancel();
    }


    /**
     * 带进度条的提示
     * @param message
     */
    public static ProgressDialog pd = null;
    public static ProgressDialog showDownloadDialog(Context context, String message, boolean isDismissOnTouchOut){
        if (pd == null){
            pd = new ProgressDialog(context);
        }
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setOnDismissListener(null);
        pd.setCancelable(isDismissOnTouchOut);
        pd.setCanceledOnTouchOutside(isDismissOnTouchOut);
        pd.setMessage(message);
        pd.setMax(100);
        pd.show();
        return pd;
    }


    public static void setDownloadPrompt(String message){
        if (pd != null){
            pd.setMessage(message);
        }
    }
    /**
     * 设置条形进度条值，，和showDownloadDialog一起用
     * @param count
     */
    public static void setProgressValue(int count){
        if (pd != null) {
            pd.setProgress(count);
        }
    }
    public static void DismissLoadDialog() {
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
            pd.cancel();
        }
    }


    public static void showHomeDialog(Context context, View.OnClickListener clickListener){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_home_guanggao,null,false);
        int width = ScreenUtils.getScreenWidth();

        ImageView toPay = view.findViewById(R.id.toPay);
        toPay.getLayoutParams().height = width - 180;
        toPay.getLayoutParams().width = width - 180;
        if (clickListener != null){
            toPay.setOnClickListener(clickListener);
        }
        RelativeLayout rlClose = view.findViewById(R.id.rlClose);
        final ConfigBean dialog = StyledDialog.buildCustom(view, Gravity.CENTER);
        dialog.setForceHeightPercent(1);
        dialog.setForceWidthPercent(1);
        final Dialog dialog1 = dialog.show();

        rlClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog1.isShowing()){
                    dialog1.dismiss();
                }
            }
        });
    }


    public static class CustomBottomDialog{
        private Context mContext;
        private OnClickShareDialog onClickShareDialog;
        private View shareView;
        private Dialog mDialog;

        public CustomBottomDialog(Context mContext,String bottomName,String... shareDates) {
            this.mContext = mContext;
            init(mContext,bottomName,shareDates);
        }


        private void init(Context context, final String bottomName, String... shareDates){
            shareView = LayoutInflater.from(context).inflate(R.layout.dialog_bottom_share,null,false);
            List<String> shareNames = new ArrayList<>();
            for (String name : shareDates){
                shareNames.add(name);
            }
            RecyclerView shareRecycler = shareView.findViewById(R.id.shareRecycler);
            RecyclerAdapterHelper<String> mHelper = new RecyclerAdapterHelper<>(shareRecycler);
            mHelper.addLinearLayoutManager()
                    .addDividerItemDecorationForGrid(DividerItemDecoration.VERTICAL)
                    .addCommonAdapter(R.layout.item_custom_share, shareNames, new RecyclerAdapterHelper.CommonConvert<String>() {
                @Override
                public void convert(CommonViewHolder holder, String mData, int position) {
                    holder.setText(R.id.shareDate,mData);
                }
            }).create();

            mHelper.getBasicAdapter().setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<String>() {
                @Override
                public void onItemClick(CommonViewHolder holder, String data, int position) {
                    if (onClickShareDialog != null){
                        onClickShareDialog.clickShareDialog(bottomName,position);
                    }
                }

                @Override
                public boolean onItemLongClick(CommonViewHolder holder, String data, int position) {
                    return false;
                }
            });

            TextView cancel = shareView.findViewById(R.id.cancel);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickShareDialog != null){
                        onClickShareDialog.clickShareDialog(bottomName,-1);
                    }
                }
            });
        }

        public void show(){
            if (mDialog == null){
                BottomSheetDialog bsd = (BottomSheetDialog) StyledDialog.buildCustomBottomSheet(shareView).show();
                bsd.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
                mDialog = bsd;
            } else {
                mDialog.show();
            }
        }

        public void dimiss(){
            if (mDialog != null){
                mDialog.dismiss();
            }
        }


        public interface OnClickShareDialog{
            void clickShareDialog(String itemName,int i);
        }

        public void setOnClickShareDialog(OnClickShareDialog onClickShareDialog) {
            this.onClickShareDialog = onClickShareDialog;
        }
    }
}
