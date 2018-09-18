package com.km.rmbank.utils.timepicker;

import android.content.Context;
import android.view.View;

import com.km.rmbank.R;
import com.lvfq.pickerview.lib.WheelView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by PengSong on 18/9/18.
 */

public class WheelBookVenueTime {

    public static DateFormat dateFormat = new SimpleDateFormat("MM月dd日");
    private View view;
    private WheelView wv_date;
    private WheelView wv_time;


    public WheelBookVenueTime(View view) {
        this.view = view;
        setView(view);
    }

    public void setPicker(long startDate,int days){
        // 添加大小月月份并将其转换为list,方便之后的判断

        List<Long> dateList = new ArrayList<>();
        for (int i = 0; i < days; i++){
            dateList.add(startDate + (i * 60 * 60 * 24 * 1000));
        }

        List<String> timeList = new ArrayList<>();
        timeList.add("14点");
        timeList.add("16点");
        timeList.add("18点");

        BookVenueWheelAdapter<Long> dateAdapter = new BookVenueWheelAdapter<>(dateList, BookVenueWheelAdapter.Type.DATE);
        BookVenueWheelAdapter<String> timeAdapter = new BookVenueWheelAdapter<>(timeList, BookVenueWheelAdapter.Type.TIME);
        //月日
        wv_date = view.findViewById(R.id.date);
        wv_date.setAdapter(dateAdapter);
        wv_date.setCurrentItem(0);

        //时间
        wv_time = view.findViewById(R.id.time);
        wv_time.setAdapter(timeAdapter);
        wv_time.setCurrentItem(0);

    }

    public String getTime(){
        StringBuffer time = new StringBuffer();
        BookVenueWheelAdapter dateAdapter = (BookVenueWheelAdapter) wv_date.getAdapter();
        BookVenueWheelAdapter timeAdapter = (BookVenueWheelAdapter) wv_time.getAdapter();

        time.append(dateAdapter.getTime(wv_date.getCurrentItem()))
                .append(" ")
                .append(timeAdapter.getTime(wv_time.getCurrentItem()));
        return time.toString();
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        wv_date.setCyclic(cyclic);
        wv_time.setCyclic(cyclic);
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
