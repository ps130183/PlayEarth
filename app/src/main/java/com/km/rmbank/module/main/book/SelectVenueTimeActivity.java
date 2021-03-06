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
import com.km.rmbank.event.RefreshPersonalInfoEvent;
import com.km.rmbank.module.main.HomeActivity;
import com.km.rmbank.module.main.personal.book.BookVenueManageActivity;
import com.km.rmbank.mvp.model.SelectVenueTimeModel;
import com.km.rmbank.mvp.presenter.SelectVenueTimePresenter;
import com.km.rmbank.mvp.view.SelectVenueTimeView;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.DateUtils;
import com.km.rmbank.utils.EventBusUtils;
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
//                TextView end = mViewManager.findView(R.id.end_time);
                final String placeId = getIntent().getStringExtra("placeId");

                final String startTime = start.getText().toString();
//                final String endTime = end.getText().toString();

                if (!TextUtils.isEmpty(startTime)){

                    int actionType = getIntent().getIntExtra("type",-1);
                    if (actionType == 1){
                        DialogUtils.showDefaultAlertDialog("活动当天到场人数不足10人是要交费的哦，是否提交？", new DialogUtils.ClickListener() {
                            @Override
                            public void clickConfirm() {
                                getPresenter().submitBookVenue(placeId,startTime,startTime);
                            }
                        });
                    } else {
                        getPresenter().submitBookVenue(placeId,startTime,startTime);
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
//        PickerUtils.alertTimerPicker(mInstance, TimePickerView.Type.YEAR_MONTH_DAY_HOUR, curTime,
//                DateUtils.YMDHM, new PickerUtils.TimerPickerCallBack() {
//                    @Override
//                    public void onTimeSelect(String date) {
//                        textView.setText(date);
//                    }
//                });

        int maxDays = getIntent().getIntExtra("maxDays",1);
        PickerUtils.alertBookVenuePickerTime(mInstance,3,maxDays, new PickerUtils.TimerPickerCallBack() {
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
        EventBusUtils.post(new RefreshPersonalInfoEvent());
        startActivity(HomeActivity.class,bundle);
//        startActivity(BookVenueManageActivity.class);
    }
}
