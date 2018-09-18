package com.km.rmbank.utils;

import android.support.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by kamangkeji on 17/1/23.
 */

public class DateUtils {

    public static final String YMD = "yyyy-MM-dd";
    public static final String YMD_DOT = "yyyy.MM.dd";
    public static final String YMD_SLASH = "yyyy/MM/dd";
    public static final String YMDHM = "yyyy-MM-dd HH:mm";
    public static final String YMDHM2 = "yyyy年MM月dd日 HH:mm";
    public static final String YMDHMS = "yyyy-MM-dd HH:mm:ss";

    private Calendar calendar;
    private static DateUtils instance = null;
    private SimpleDateFormat dateFormat;
    private DateUtils(){
        dateFormat = new SimpleDateFormat(YMD);
        calendar = Calendar.getInstance();
    }

    public static DateUtils getInstance(){
        if (instance == null){
            synchronized (DateUtils.class){
                if (instance == null){
                    instance = new DateUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 日期类型 转换成字符串
     * @param date
     * @return
     */
    public String dateToString(Date date){
        return dateFormat.format(date);
    }

    /**
     * 日期类型转为字符串
     * @param date
     * @param mode
     * @return
     */
    public String dateToString(Date date,String mode){
        SimpleDateFormat format = new SimpleDateFormat(mode);
        return format.format(date);
    }

    /**
     * 获取年月日 日期   格式如 2017年3月21日
     * @param millisecond
     * @return
     */
    public String getDateToYMD(Long millisecond){
        int [] ymd = getYMD(getDate(millisecond));
        return ymd[0] + "年" + ymd[1] + "月" + ymd[2] + "日";
    }

    /**
     * 字符串日期转换成日期类型
     * @param date
     * @return
     */
    public Date stringToDate(String date){
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 字符串日期转成日期类型
     * @param date
     * @param mode
     * @return
     */
    public Date stringToDate(String date,String mode){
        SimpleDateFormat format = new SimpleDateFormat(mode);
        try {
            return format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将日期毫秒数转换成日期格式
     * @param milliSecond
     * @return
     */
    public String getDate(long milliSecond){
        return dateToString(new Date(milliSecond));
    }

    /**
     * 整形 年月日 转换为字符串
     * @param year
     * @param month
     * @param day
     * @return
     */
    public String getDate(int year,int month,int day){
        StringBuffer ymd = new StringBuffer();
        ymd.append(year).append("-");
        if (month > 0 && month < 10){
            ymd.append(0).append(month);
        } else {
            ymd.append(month);
        }

        ymd.append("-");

        if (day > 0 && day < 10){
            ymd.append(0).append(day);
        } else {
            ymd.append(day);
        }
        return ymd.toString();
    }

    public int getYear(){
        return calendar.get(Calendar.YEAR);
    }

    public int  getMonth(){
        return calendar.get(Calendar.MONTH);
    }

    public int getMonthDay(){
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public int getDay(long curDateMilli){
        String curDate = getDate(curDateMilli);
        int day = Integer.parseInt(curDate.split("-")[2]);
        return day;
    }

    /**
     * 分割如2017-02-02 类型的日期
     * @param curDate
     * @return
     */
    public int[] getYMD(@NonNull String curDate){
        String[] ymd = curDate.split("-");
        int[] iymd = {Integer.parseInt(ymd[0]),Integer.parseInt(ymd[1]),Integer.parseInt(ymd[2])};
        return iymd;
    }

    /**
     * 字符串日期 转 毫秒
     * @param date
     * @param state
     * @return
     */
    public long stringDateToMillis(String date,String state){
        long millis = 0;
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat(state).parse(date));
            millis = calendar.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return millis;
    }


    /**
     * 根据maxDay 获取当前时间的 后几天
     * @param maxDay
     * @param curYear
     * @param curMonth
     * @param curDay
     * @return
     */
    public List<Date> getNextDate(int maxDay, int curYear, int curMonth, int curDay){
        List<Date> nextDates = new ArrayList<>();
        Date curDate = DateUtils.getInstance().stringToDate(curYear+"-"+curMonth+"-"+curDay);
        for (int i = 0; i < maxDay - 1; i++){
            curDate = getAfterDay(curDate);
            LogUtils.d(curDate.getDay()+"");
            nextDates.add(curDate);
        }
        return nextDates;
    }

    /**
     * 获取当前时间的后一天时间
     * @param curDate
     * @return
     */
    private Date getAfterDay(Date curDate){
        Date dueDate = null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(curDate);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        dueDate = cal.getTime();
        return dueDate;
    }


}
