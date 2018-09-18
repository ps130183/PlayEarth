package com.km.rmbank.utils.timepicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.km.rmbank.R;
import com.lvfq.pickerview.TimePickerView;
import com.lvfq.pickerview.view.BasePickerView;
import com.lvfq.pickerview.view.WheelTime;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by PengSong on 18/9/18.
 */

public class BookVenueTimePickerView extends BasePickerView implements View.OnClickListener {

    WheelTime wheelTime;
    private View btnSubmit, btnCancel;
    private TextView tvTitle;
    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";
    private OnTimeSelectedListener timeSelectListener;

    private WheelBookVenueTime mWheelTime;

    public BookVenueTimePickerView(Context context,int afterDays,int daysCount) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.pickerview_book_venue_time, contentContainer);

        // -----确定和取消按钮
        btnSubmit = findViewById(com.bigkoo.pickerview.R.id.btnSubmit);
        btnSubmit.setTag(TAG_SUBMIT);
        btnCancel = findViewById(com.bigkoo.pickerview.R.id.btnCancel);
        btnCancel.setTag(TAG_CANCEL);
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        //顶部标题
        tvTitle = (TextView) findViewById(com.bigkoo.pickerview.R.id.tvTitle);
        tvTitle.setText("选择时间");

        LinearLayout pickerView = (LinearLayout) findViewById(R.id.timepicker);
        mWheelTime = new WheelBookVenueTime(pickerView);
        //默认选中当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        mWheelTime.setPicker(System.currentTimeMillis() + (afterDays * 60 * 60 * 24 * 1000),daysCount);
    }


    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        mWheelTime.setCyclic(cyclic);
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_CANCEL)) {
            dismiss();
            return;
        } else {
            if (timeSelectListener != null) {
                timeSelectListener.timeSelected(mWheelTime.getTime());
            }
            dismiss();
            return;
        }
    }


    public interface OnTimeSelectedListener{
        void timeSelected(String time);
    }

    public void setTimeSelectListener(OnTimeSelectedListener timeSelectListener) {
        this.timeSelectListener = timeSelectListener;
    }
}
