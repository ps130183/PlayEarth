package com.km.rmbank.module.main.personal.member.club;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseFragment;
import com.km.rmbank.dto.CalendarActionsDto;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.module.main.appoint.ActionPastDetailActivity;
import com.km.rmbank.module.main.appoint.ActionRecentInfoActivity;
import com.km.rmbank.mvp.model.ClubActionsModel;
import com.km.rmbank.mvp.presenter.ClubActionsPresenter;
import com.km.rmbank.mvp.view.IClubActionsView;
import com.km.rmbank.utils.DateUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClubActionsFragment extends BaseFragment<IClubActionsView,ClubActionsPresenter> implements IClubActionsView {

    @BindView(R.id.curYearMonth)
    TextView curYearMonth;

    @BindView(R.id.calendarView)
    CalendarView mCalendarView;

    private ClubDto mClubDto;

    //当日年
    private int mCurYear;
    //当日月
    private int mCurMonth;
    //当日
    private int mCurDay;
    //当前年月
    private String mCurYearMonth;

    private HashMap<String,SparseArray<CalendarActionsDto>> calendarActionsMap;

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
    protected ClubActionsPresenter createPresenter() {
        return new ClubActionsPresenter(new ClubActionsModel());
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {
        mClubDto = getArguments().getParcelable("clubInfo");
        mCurYear = mCalendarView.getCurYear();
        mCurMonth = mCalendarView.getCurMonth();
        mCurDay = mCalendarView.getCurDay();
        calendarActionsMap = new HashMap<>();
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
                mCurYearMonth = year + "-" + getCalendarMonth(month);
                notifyCalendarDatas(mCurYearMonth);
            }
        });

        mCalendarView.setOnDateSelectedListener(new CalendarView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Calendar calendar, boolean isClick) {
                if (isClick){
                    String curYearMonth = calendar.getYear() + "-" + getCalendarMonth(calendar.getMonth());
                    SparseArray<CalendarActionsDto> calendarActionsDtos = getCalendarActions(curYearMonth);
                    CalendarActionsDto actionsDto = calendarActionsDtos.get(calendar.getDay());
                    if (actionsDto != null){
                        Bundle bundle = new Bundle();
                        if (actionsDto.getIsDynamic() == null || actionsDto.getIsDynamic().equals("0")){//未编辑
                            bundle.putString("actionId",actionsDto.getId());
                            startActivity(ActionRecentInfoActivity.class,bundle);
                        } else {//已编辑
                            bundle.putString("activityId",actionsDto.getId());
                            startActivity(ActionPastDetailActivity.class,bundle);
                        }
                    }
                }
            }
        });

        if (mClubDto != null){
            String month = getCalendarMonth(mCalendarView.getCurMonth());
            //当前年月
            mCurYearMonth = mCalendarView.getCurYear() + "-" + month;
            getPresenter().getClubActionsByMonth(mClubDto.getId(), mCurYearMonth);
        }



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

    @Override
    public void showClubActions(List<CalendarActionsDto> calendarActionsDtos, String startDate) {
        putCalendarActions(startDate,calendarActionsDtos);

        if (startDate.equals(mCurYearMonth)){
            notifyCalendarDatas(startDate);
        }
    }

    /**
     * 缓存当前日期的数据
     * @param date
     * @param calendarActionsDtos
     */
    private void putCalendarActions(String date,List<CalendarActionsDto> calendarActionsDtos){
        if (!calendarActionsMap.containsKey(date)){
            SparseArray<CalendarActionsDto> calendarActionsDtoSparseArray = new SparseArray<>();
            if (calendarActionsDtos == null){
                calendarActionsMap.put(date,calendarActionsDtoSparseArray);
            } else {
                for (CalendarActionsDto actionsDto : calendarActionsDtos){
                    int day = DateUtils.getInstance().getDay(actionsDto.getStartDate());
                    calendarActionsDtoSparseArray.append(day,actionsDto);
                }
                calendarActionsMap.put(date,calendarActionsDtoSparseArray);
            }

        }
    }

    /**
     * 获取缓存的日期的数据
     * @param date
     * @return
     */
    private SparseArray<CalendarActionsDto> getCalendarActions(String date){
        if (calendarActionsMap.containsKey(date)){
            return calendarActionsMap.get(date);
        }
        return null;
    }

    /**
     * 刷新当前年月的活动数据
     * @param date
     */
    private void notifyCalendarDatas(String date){
        SparseArray<CalendarActionsDto> calendarActionsDtos = getCalendarActions(date);

        String[] curDate = date.split("-");
        List<Calendar> schemes = new ArrayList<>();
        int year = Integer.parseInt(curDate[0]);
        int month = Integer.parseInt(curDate[1]);

        if (calendarActionsDtos == null){//没有当前date的数据
            getPresenter().getClubActionsByMonth(mClubDto.getId(),date);
        } else if (calendarActionsDtos.size() > 0){
            for (int i = 0; i < calendarActionsDtos.size(); i++){
                CalendarActionsDto actionsDto = calendarActionsDtos.valueAt(i);
                int day = DateUtils.getInstance().getDay(actionsDto.getStartDate());
                int color = 0;
                if (year < mCurYear){//前年
                    color = 0xFFff0000;//红
                } else if (month < mCurMonth){//前月
                    color = 0xFFff0000;//红
                } else if (day < mCurDay){//前日
                    color = 0xFFff0000;//红
                } else {
                    color = 0xFF00c93b;//绿
                }

                schemes.add(getSchemeCalendar(year, month, day, color, ""));

            }
            if (mCurYear == year && mCurMonth == month){
                schemes.add(getSchemeCalendar(mCurYear,mCurMonth,mCurDay,0xFF999999,""));
            }

            mCalendarView.setSchemeDate(schemes);
        }


        //预加载上一个月 和 下一个月的数据
        String preYearMonth = month == 1 ? (year-1) + "-" + 12 : year + "-" + getCalendarMonth(month - 1);
        String nextYearMonth = month == 12 ? (year+1) + "-" + getCalendarMonth(1) : year + "-" + getCalendarMonth(month + 1);
        if (getCalendarActions(preYearMonth) == null){
            getPresenter().getClubActionsByMonth(mClubDto.getId(),preYearMonth);
        }
        if (getCalendarActions(nextYearMonth) == null){
            getPresenter().getClubActionsByMonth(mClubDto.getId(),nextYearMonth);
        }

    }
}
