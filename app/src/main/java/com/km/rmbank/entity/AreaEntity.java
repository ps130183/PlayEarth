package com.km.rmbank.entity;

/**
 * Created by kamangkeji on 17/3/22.
 */

public class AreaEntity {

    /**
     * Id : 1
     * name : 东城区
     * CityID : 1
     * DisSort : null
     */

    private int Id;
    private String name;
    private int CityID;
    private Object DisSort;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCityID() {
        return CityID;
    }

    public void setCityID(int CityID) {
        this.CityID = CityID;
    }

    public Object getDisSort() {
        return DisSort;
    }

    public void setDisSort(Object DisSort) {
        this.DisSort = DisSort;
    }
}
