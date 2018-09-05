package com.km.rmbank.module.main.book;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.module.main.HomeActivity;
import com.km.rmbank.mvp.model.SelectVenueTimeModel;
import com.km.rmbank.mvp.presenter.SelectVenueTimePresenter;
import com.km.rmbank.mvp.view.SelectVenueTimeView;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.DateUtils;
import com.km.rmbank.utils.PickerUtils;
import com.km.rmbank.utils.dialog.DialogUtils;
import com.lvfq.pickerview.TimePickerView;

import java.util.Date;

public class SelectVenueTimeActivity extends BaseActivity<SelectVenueTimeView,SelectVenueTimePresenter> implements SelectVenueTimeView {

    @Override
    public int getContentViewRes() {
        return R.layout.activity_select_venue_time;
    }

    @Override
    public String getTitleContent() {
        return "选择时间";
    }

    @Override
    protected SelectVenueTimePresenter createPresenter() {
        return new SelectVenueTimePresenter(new SelectVenueTimeModel());
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setRightMenuContent("提交");
        simpleTitleBar.setRightMenuClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView start = mViewManager.findView(R.id.start_time);
                TextView end = mViewManager.findView(R.id.end_time);
                final String placeId = getIntent().getStringExtra("placeId");

                final String startTime = start.getText().toString();
                final String endTime = end.getText().toString();

                if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime)){
                    String mStartTime = startTime.substring(0,10) + " 23:59";

                    long startDate = DateUtils.getInstance().stringDateToMillis(startTime,DateUtils.YMDHM);
                    long centerDate = DateUtils.getInstance().stringDateToMillis(mStartTime,DateUtils.YMDHM);
                    long endDate = DateUtils.getInstance().stringDateToMillis(endTime,DateUtils.YMDHM);
                    if (endDate > centerDate){
                        showToast("只能选择同一天的时间段");
                        return;
                    }
                    if (endDate <= startDate){
                        showToast("结束时间不能小于开始时间");
                        return;
                    }

                    int type = getIntent().getIntExtra("type",-1);
                    String price = getIntent().getStringExtra("price");
                    if (type == 2){
                        DialogUtils.showDefaultAlertDialog("你申请的结缘晚宴的场地，审核通过后需支付" + price + "元人民币的场地使用费用。", new DialogUtils.ClickListener() {
                            @Override
                            public void clickConfirm() {
                                getPresenter().submitBookVenue(placeId,startTime,endTime);
                            }
                        });
                    } else {
                        getPresenter().submitBookVenue(placeId,startTime,endTime);
                    }


                } else {
                    showToast("请选择场地的使用时间");
                }
            }
        });
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {

    }

    public void selectTime(View view) {
        final TextView textView = (TextView) view;
        String curTime = DateUtils.getInstance().dateToString(new Date(), DateUtils.YMDHM);
        if (view.getId() == R.id.end_time){
            TextView startTime = mViewManager.findView(R.id.start_time);
            curTime = startTime.getText().toString();
        }
        PickerUtils.alertTimerPicker(mInstance, TimePickerView.Type.ALL, curTime,
                DateUtils.YMDHM, new PickerUtils.TimerPickerCallBack() {
                    @Override
                    public void onTimeSelect(String date) {
                        textView.setText(date);
                    }
                });
    }

    @Override
    public void submitSuccess() {
        Bundle bundle = new Bundle();
        bundle.putInt("position",4);
        startActivity(HomeActivity.class,bundle);
    }
}
