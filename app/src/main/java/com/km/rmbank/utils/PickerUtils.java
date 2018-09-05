package com.km.rmbank.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.km.rmbank.R;
import com.km.rmbank.entity.ProvinceBean;
import com.km.rmbank.utils.selectcity.CityPickData;
import com.lvfq.pickerview.OptionsPickerView;
import com.lvfq.pickerview.TimePickerView;
import com.lvfq.pickerview.adapter.ArrayWheelAdapter;
import com.lvfq.pickerview.lib.WheelView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by kamangkeji on 17/3/22.
 */

public class PickerUtils {
    /**
     * 时间选择回调
     */
    public interface TimerPickerCallBack {
        void onTimeSelect(String date);
    }

    /**
     * 弹出时间选择
     *
     * @param context
     * @param type     TimerPickerView 中定义的 选择时间类型
     * @param format   时间格式化
     * @param callBack 时间选择回调
     */
    public static void alertTimerPicker(Context context, TimePickerView.Type type, String curDate,
                                        final String format, final TimerPickerCallBack callBack) {
        TimePickerView pvTime = new TimePickerView(context, type);
        final SimpleDateFormat sdf = new SimpleDateFormat(format);
        //控制时间范围
        Calendar calendar = Calendar.getInstance();
//        pvTime.setRange(curDate,format);
//        pvTime.setRange(1900, calendar.get(Calendar.YEAR));
        pvTime.setRange(calendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR));
        Date date = null;
        if (TextUtils.isEmpty(curDate)){
            date = new Date();
        } else {
            try {
                date = sdf.parse(curDate);
            } catch (ParseException e) {
                e.printStackTrace();
                ToastUtils.showShort("日期格式错误");
                return;
            }
        }
        pvTime.setTime(date);
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
//                        tvTime.setText(getTime(date));

                callBack.onTimeSelect(sdf.format(date));
            }
        });
        pvTime.setTextSize(16);
        //弹出时间选择器
        pvTime.show();
        KeyboardUtils.hideSoftInput((Activity) context);
    }


    /**
     * 底部滚轮点击事件回调
     */
    public interface OnWheelViewClick {
        void onClick(View view, int postion);
    }

    /**
     * 弹出底部滚轮选择
     *
     * @param context
     * @param list
     * @param click
     */
    public static void alertBottomWheelOption(Context context, ArrayList<?> list, final OnWheelViewClick click) {

        final PopupWindow popupWindow = new PopupWindow();

        View view = LayoutInflater.from(context).inflate(R.layout.layout_bottom_wheel_option, null);
        TextView tv_confirm = (TextView) view.findViewById(R.id.btnSubmit);
        final WheelView wv_option = (WheelView) view.findViewById(R.id.wv_option);
        wv_option.setAdapter(new ArrayWheelAdapter(list));
        wv_option.setCyclic(false);
        wv_option.setTextSize(16);
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                click.onClick(view, wv_option.getCurrentItem());
            }
        });

        view.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2016/8/11 0011 取消
                popupWindow.dismiss();
            }
        });
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int top = view.findViewById(R.id.ll_container).getTop();
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    int y = (int) motionEvent.getY();
                    if (y < top) {
                        popupWindow.dismiss();
                    }
                }
                return true;
            }
        });
        popupWindow.setContentView(view);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.showAtLocation(((ViewGroup) ((Activity) context).findViewById(android.R.id.content)).getChildAt(0), Gravity.CENTER, 0, 0);
    }


    /**
     * 弹出省市区 三级联动
     * @param mContext
     * @param optionView
     * @param showOption
     */
    public static void showOptions(Context mContext, final TextView optionView, final View showOption){
        showOptions(mContext,optionView,showOption,0,0,0);
    }

    public static void showOptions(Context mContext, final TextView optionView, final View showOption, int option1, int option2, int option3){
        final ArrayList<ProvinceBean> options1Items = CityPickData.options1Items;
        final ArrayList<ArrayList<String>> options2Items = CityPickData.options2Items;
        final ArrayList<ArrayList<ArrayList<String>>> options3Items = CityPickData.options3Items;

        final OptionsPickerView pvOptions;
        //选项选择器
        pvOptions = new OptionsPickerView(mContext);
        // 初始化三个列表数据
//        DataModel.initData(options1Items, options2Items, options3Items);

        //三级联动效果
        pvOptions.setPicker(options1Items, options2Items,options3Items, true);
        //设置选择的三级单位
//  pwOptions.setLabels("省", "市", "区");
        pvOptions.setTitle("选择城市");
        pvOptions.setCyclic(false, false, false);
        //设置默认选中的三级项目
        //监听确定选择按钮
        pvOptions.setSelectOptions(option1, option2, option3);
        pvOptions.setTextSize(18);
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {

                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()
                        + options2Items.get(options1).get(option2)
                        + options3Items.get(options1).get(option2).get(options3);
                optionView.setText(tx);
                showOption.setVisibility(View.GONE);
            }
        });
        //点击弹出选项选择器
        optionView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pvOptions.show();
            }
        });
    }


}
