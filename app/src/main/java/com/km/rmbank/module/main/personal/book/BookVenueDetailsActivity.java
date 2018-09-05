package com.km.rmbank.module.main.personal.book;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.dto.ShareDto;
import com.km.rmbank.entity.BookVenueApplyDto;
import com.km.rmbank.module.main.HomeActivity;
import com.km.rmbank.utils.Constant;
import com.km.rmbank.utils.DateUtils;
import com.km.rmbank.utils.UmengShareUtils;
import com.km.rmbank.utils.dialog.DialogUtils;
import com.km.rmbank.utils.dialog.WindowBottomDialog;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Date;

public class BookVenueDetailsActivity extends BaseActivity {

    private WindowBottomDialog mShareDialog;
    private ShareDto mShareDto;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_book_venue_details;
    }

    @Override
    public String getTitleContent() {
        return "场地申请详情";
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        final BookVenueApplyDto bookVenueApplyDto = getIntent().getParcelableExtra("bookVenueApplyInfo");
        if (bookVenueApplyDto == null) {
            finish();
        }

        boolean isFinished = getIntent().getBooleanExtra("isFinished", false);
        TextView tvStatus = mViewManager.findView(R.id.status);
        String status = bookVenueApplyDto.getStatus();
        switch (status) {
            case "1"://审核中
                tvStatus.setText("审核中");
                tvStatus.setTextColor(getResources().getColor(R.color.venue_apply));
                break;
            case "2"://已通过

                if (!isFinished) {
                    DialogUtils.showDefaultAlertDialog("场地申请已通过，是否需要玩转地球帮你约人？", "需要", "暂不需要", new DialogUtils.ClickListener() {
                        @Override
                        public void clickConfirm() {
                            Bundle bundle = getIntent().getExtras();
                            bundle.putString("placeReservationId", bookVenueApplyDto.getId());
                            startActivity(ReleaseActionActivity.class, bundle);
                        }
                    });
                }
            case "5"://已通过
                tvStatus.setText("已通过");
                tvStatus.setTextColor(getResources().getColor(R.color.venue_passed));
                if (!isFinished){
                    mViewManager.findView(R.id.restarApply).setVisibility(View.VISIBLE);
                    mViewManager.setText(R.id.restarApply, "邀请好友参加");
                }

                break;
            case "3"://已拒绝
                tvStatus.setText("已拒绝");
                tvStatus.setTextColor(getResources().getColor(R.color.venue_not_passed));

                WebView reason = mViewManager.findView(R.id.reason);
                reason.loadData(bookVenueApplyDto.getReason(), "text/html; charset=UTF-8", null);
                mViewManager.findView(R.id.line).setVisibility(View.VISIBLE);
                mViewManager.findView(R.id.restarApply).setVisibility(View.VISIBLE);
                break;
            case "4"://已取消
                tvStatus.setText("已取消");
                tvStatus.setTextColor(getResources().getColor(R.color.venue_cancel));
                break;

        }

        mViewManager.setText(R.id.tv_venue_name, bookVenueApplyDto.getPlaceTitle());
        String type = "";
        switch (bookVenueApplyDto.getPlaceType()) {
            case "1":
                type = "下午茶";
                break;
            case "2":
                type = "结缘晚宴";
                break;
            case "3":
                type = "路演大会";
                break;
        }
        mViewManager.setText(R.id.tv_venue_type, type);
        mViewManager.setText(R.id.tv_venue_apply_time, DateUtils.getInstance().dateToString(new Date(bookVenueApplyDto.getCreateDate()), DateUtils.YMDHM));
        mViewManager.setText(R.id.tv_venue_time, DateUtils.getInstance().dateToString(new Date(bookVenueApplyDto.getStartDate()), DateUtils.YMDHM) + "   至\n"
                + DateUtils.getInstance().dateToString(new Date(bookVenueApplyDto.getEndDate()), DateUtils.YMDHM));

        initShareDialog();
    }

    /**
     * 重新申请
     *
     * @param view
     */
    public void reserApply(View view) {
        BookVenueApplyDto bookVenueApplyDto = getIntent().getParcelableExtra("bookVenueApplyInfo");
        if ("3".equals(bookVenueApplyDto.getStatus())) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("isApply", true);
            startActivity(HomeActivity.class, bundle);
        } else {//2  场地申请通过
            mShareDialog.show();
        }

    }

    private void initShareDialog() {
        BookVenueApplyDto bookVenueApplyDto = getIntent().getParcelableExtra("bookVenueApplyInfo");
        String venueName = "";
        if ("1".equals(bookVenueApplyDto.getPlaceType())){
            venueName = "下午茶";
        } else if ("2".equals(bookVenueApplyDto.getPlaceType())){
            venueName = "结缘晚宴";
        } else if ("3".equals(bookVenueApplyDto.getPlaceType())){
            venueName = "路演大会";
        }
        mShareDto = new ShareDto();
        mShareDto.setTitle(Constant.userInfo.getName() + "邀请你参加主题" + venueName);
        mShareDto.setContent("活动时间：" + DateUtils.getInstance().dateToString(new Date(bookVenueApplyDto.getStartDate()), DateUtils.YMDHM));
        mShareDto.setSharePicUrl(bookVenueApplyDto.getPlaceUrl());
        mShareDto.setPageUrl("http://www.baidu.com");
        mShareDialog = new WindowBottomDialog(mInstance, "取消", "分享微信好友", "分享到朋友圈");
        mShareDialog.setOnClickShareDialog(new WindowBottomDialog.OnClickShareDialog() {
            @Override
            public void clickShareDialog(String itemName, int i) {
                mShareDialog.dimiss();
                if (i == 0) {
                    UmengShareUtils.openShare(mInstance, mShareDto, SHARE_MEDIA.WEIXIN);
                } else if (i == 1) {
                    UmengShareUtils.openShare(mInstance, mShareDto, SHARE_MEDIA.WEIXIN_CIRCLE);
                }
            }
        });
    }
}
