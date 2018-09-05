package com.km.rmbank.entity;

import java.util.ArrayList;

/**
 * Created by kamangkeji on 17/3/22.
 */

public class CityEntity {

    /**
     * CityID : 1
     * name : 北京市
     * ProID : 1
     * CitySort : 1
     */

    private int CityID;
    private String name;
    private int ProID;
    private int CitySort;

    private ArrayList<String> subArea;

    public int getCityID() {
        return CityID;
    }

    public void setCityID(int CityID) {
        this.CityID = CityID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProID() {
        return ProID;
    }

    public void setProID(int ProID) {
        this.ProID = ProID;
    }

    public int getCitySort() {
        return CitySort;
    }

    public void setCitySort(int CitySort) {
        this.CitySort = CitySort;
    }

    public ArrayList<String> getSubArea() {
        return subArea;
    }

    public void setSubArea(ArrayList<String> subArea) {
        this.subArea = subArea;
    }

    //这个用来显示在PickerView上面的字符串,PickerView会通过反射获取getPickerViewText方法显示出来。
    public String getPickerViewText() {
        //这里还可以判断文字超长截断再提供显示
        return name;
    }
}
