package com.km.rmbank.utils.selectcity;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.km.rmbank.entity.ProvinceBean;
import com.lvfq.pickerview.OptionsPickerView;

import java.util.ArrayList;

/**
 * Created by kamangkeji on 17/4/11.
 */

public class SelectCityPick {


    private int mOption1;
    private int mOption2;
    private int mOption3;

    private ArrayList<ProvinceBean> options1Items = CityPickData.options1Items;
    private ArrayList<ArrayList<String>> options2Items = CityPickData.options2Items;
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = CityPickData.options3Items;

    /**
     * 弹出省市区 三级联动
     * @param mContext
     * @param optionView
     * @param showOption
     */
    public void showOptions(Context mContext, final TextView optionView, final View showOption){
        showOptions(mContext,optionView,showOption,mOption1,mOption2,mOption3);
    }

    public void showOptions(Context mContext, final TextView optionView, final View showOption, int option1, int option2, int option3){


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
                mOption1 = options1;
                mOption2 = option2;
                mOption3 = options3;
                //返回的分别是三个级别的选中位置
                optionView.setText(getSelectedContent());
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


    public void saveOptionsPosition(String id){
        SPUtils.getInstance().put(id+"_option1",mOption1);
        SPUtils.getInstance().put(id+"_option2",mOption2);
        SPUtils.getInstance().put(id+"_option3",mOption3);
    }

    public void deleteOptionsPosition(String id){
        SPUtils.getInstance().remove(id+"_option1");
        SPUtils.getInstance().remove(id+"_option2");
        SPUtils.getInstance().remove(id+"_option3");
    }

    public void getOptionsPosition(String id){
        mOption1 = SPUtils.getInstance().getInt(id+"_option1",0);
        mOption2 = SPUtils.getInstance().getInt(id+"_option2",0);
        mOption3 = SPUtils.getInstance().getInt(id+"_option3",0);
    }

    public String getSelectedContent(){
        String tx = options1Items.get(mOption1).getPickerViewText()
                + options2Items.get(mOption1).get(mOption2)
                + options3Items.get(mOption1).get(mOption2).get(mOption3);
        return tx;
    }

}
