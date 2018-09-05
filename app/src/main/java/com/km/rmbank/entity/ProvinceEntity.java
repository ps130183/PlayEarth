package com.km.rmbank.entity;

/**
 * Created by kamangkeji on 17/3/22.
 */

public class ProvinceEntity {

    /**
     * ProID : 1
     * name : 北京市
     * ProSort : 1
     * ProRemark : 直辖市
     */

    private int ProID;
    private String name;
    private int ProSort;
    private String ProRemark;

    public int getProID() {
        return ProID;
    }

    public void setProID(int ProID) {
        this.ProID = ProID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProSort() {
        return ProSort;
    }

    public void setProSort(int ProSort) {
        this.ProSort = ProSort;
    }

    public String getProRemark() {
        return ProRemark;
    }

    public void setProRemark(String ProRemark) {
        this.ProRemark = ProRemark;
    }


    //这个用来显示在PickerView上面的字符串,PickerView会通过反射获取getPickerViewText方法显示出来。
    public String getPickerViewText() {
        //这里还可以判断文字超长截断再提供显示
        return name;
    }
}
