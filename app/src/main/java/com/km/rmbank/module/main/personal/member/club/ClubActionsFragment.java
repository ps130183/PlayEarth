package com.km.rmbank.module.main.personal.member.club;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClubActionsFragment extends BaseFragment {

    @BindView(R.id.curYearMonth)
    TextView curYearMonth;

    @BindView(R.id.calendarView)
    CalendarView mCalendarView;

    public static ClubActionsFragment newInstance(Bundle bundle) {

        ClubActionsFragment fragment = new ClubActionsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_clun_actions;
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {
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
            }
        });

        mCalendarView.setOnDateSelectedListener(new CalendarView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Calendar calendar, boolean isClick) {
                if (isClick){
                    showToast("点击的是 ： " + calendar.getDay());
                }
            }
        });

        List<Calendar> schemes = new ArrayList<>();
        int year = mCalendarView.getCurYear();
        int month = mCalendarView.getCurMonth();

        schemes.add(getSchemeCalendar(year, month, 3, 0xFF40db25, "假"));
        schemes.add(getSchemeCalendar(year, month, 6, 0xFFe69138, "事"));
        schemes.add(getSchemeCalendar(year, month, 9, 0xFFdf1356, "议"));
        schemes.add(getSchemeCalendar(year, month, 13, 0xFFedc56d, "记"));
        schemes.add(getSchemeCalendar(year, month, 14, 0xFFedc56d, "记"));
        schemes.add(getSchemeCalendar(year, month, 15, 0xFFaacc44, "假"));
        schemes.add(getSchemeCalendar(year, month, 18, 0xFFbc13f0, "记"));
        schemes.add(getSchemeCalendar(year, month, 25, 0xFF13acf0, "假"));
        mCalendarView.setSchemeDate(schemes);
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
}
