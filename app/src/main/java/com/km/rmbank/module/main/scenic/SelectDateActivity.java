package com.km.rmbank.module.main.scenic;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.CalendarActionsDto;
import com.km.rmbank.entity.CheckDateEntity;
import com.km.rmbank.event.SelectDateResultEvent;
import com.km.rmbank.module.main.club.ActionPastDetailActivity;
import com.km.rmbank.module.main.club.ActionRecentInfoActivity;
import com.km.rmbank.utils.DateUtils;
import com.km.rmbank.utils.EventBusUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SelectDateActivity extends BaseActivity {

    @BindView(R.id.curYearMonth)
    TextView curYearMonth;

    @BindView(R.id.calendarView)
    CalendarView mCalendarView;

    //当日年
    private int mCurYear;
    //当日月
    private int mCurMonth;
    //当日
    private int mCurDay;
    private int showYear;
    private int showMonth;
    //当前年月
    private String mCurYearMonth;
    private int maxDay = 1;

    SparseArray<CheckDateEntity> checkDateArray;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_select_date;
    }

    @Override
    public BaseTitleBar getBaseTitleBar() {
        return null;
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        checkDateArray = new SparseArray<>();
        List<CheckDateEntity> checkDateEntities = getIntent().getParcelableArrayListExtra("checkDates");
        maxDay = getIntent().getIntExtra("maxDay",1);
        if (checkDateEntities != null){
            for (CheckDateEntity entity : checkDateEntities){
                checkDateArray.append(entity.getKey(),entity);
            }
        }
        mCurYear = mCalendarView.getCurYear();
        mCurMonth = mCalendarView.getCurMonth();
        mCurDay = mCalendarView.getCurDay();

        initCalendarView();

    }

    /**
     * 初始化日历
     */
    private void initCalendarView(){
        mCalendarView.scrollToCurrent();
        notifyCurYearMonth(mCalendarView.getCurYear(),mCalendarView.getCurMonth());

        mCalendarView.setOnMonthChangeListener(new CalendarView.OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month) {
                notifyCurYearMonth(year,month);
//                mCurYearMonth = year + "-" + getCalendarMonth(month);
//                notifyCalendarDatas(mCurYearMonth);
                notifyAllCheckDate();
            }
        });

        mCalendarView.setOnDateSelectedListener(new CalendarView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Calendar calendar, boolean isClick) {
                if (isClick){
                    if ((calendar.getYear() < mCurYear)
                            || (calendar.getYear() == mCurYear && calendar.getMonth() < mCurMonth )
                            || (calendar.getYear() == mCurYear && calendar.getMonth() == mCurMonth && calendar.getDay() < mCurDay)){
                        showToast("不能选择过去的日期");
                        return;
                    }


                    int key = calendar.getYear() + calendar.getMonth() + calendar.getDay();
                    CheckDateEntity entity = checkDateArray.get(key);
                    if (entity == null){
                        checkDateArray.clear();
                        List<Date> selectedDates = DateUtils.getInstance().getNextDate(maxDay,calendar.getYear(),calendar.getMonth(),calendar.getDay());
                        LogUtils.d(selectedDates.toString());
                        entity = new CheckDateEntity(calendar.getYear(),calendar.getMonth(),calendar.getDay());
                        entity.setCheck(true);
                        checkDateArray.append(key,entity);
                        for (Date date : selectedDates){
                            int[] ymd = DateUtils.getInstance().getYMD(DateUtils.getInstance().dateToString(date));
                            key = ymd[0] + ymd[1] + ymd[2];
                            CheckDateEntity dateEntity = new CheckDateEntity(ymd[0],ymd[1],ymd[2]);
                            dateEntity.setCheck(true);
                            checkDateArray.append(key,dateEntity);
                        }
                    } else if (entity.isCheck()){
                        checkDateArray.clear();
                    }
                    notifyAllCheckDate();
                } else {
                    notifyAllCheckDate();
                }
            }
        });
    }



    private String getCalendarMonth(int curMonth){
        return curMonth < 10 ? "0" + curMonth : curMonth + "";
    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        return calendar;
    }

    /**
     * 更新当前 年月
     * @param curYear
     * @param curMonth
     */
    private void notifyCurYearMonth(int curYear,int curMonth){
        curYearMonth.setText(curYear + "年" + curMonth + "月");
        showYear = curYear;
        showMonth = curMonth;
    }

    /**
     * 查看下一个月
     * @param view
     */
    @OnClick(R.id.toNext)
    public void toNextMonth(View view){
        mCalendarView.scrollToNext();
    }

    /**
     * 查看上一个月
     * @param view
     */
    @OnClick(R.id.toPrevious)
    public void toPreviousMonth(View view){
        mCalendarView.scrollToPre();
    }

    private void notifyAllCheckDate(){
        List<Calendar> schemes = new ArrayList<>();
        boolean hasCurDay = false;
        for (int i = 0; i < checkDateArray.size(); i++){
            CheckDateEntity entity = checkDateArray.valueAt(i);
            schemes.add(getSchemeCalendar(entity.getYear(),entity.getMonth(),entity.getDayOfMonty(),0xFFff0000,""));
            if (mCurYear == entity.getYear() && mCurMonth == entity.getMonth() && mCurDay == entity.getDayOfMonty()){
                hasCurDay = true;
            }
        }
        if (mCurYear == showYear && mCurMonth == showMonth && !hasCurDay){
            schemes.add(getSchemeCalendar(mCurYear,mCurMonth,mCurDay,0xFF999999,""));
        }
        mCalendarView.setSchemeDate(schemes);
    }

    /**
     * 关闭
     * @param view
     */
    @OnClick(R.id.close)
    public void close(View view){
        List<CheckDateEntity> checkDateEntities = new ArrayList<>();
        for (int i = 0; i < checkDateArray.size(); i++){
            checkDateEntities.add(checkDateArray.valueAt(i));
        }
        EventBusUtils.post(new SelectDateResultEvent(checkDateEntities));
        finish();
    }
}
