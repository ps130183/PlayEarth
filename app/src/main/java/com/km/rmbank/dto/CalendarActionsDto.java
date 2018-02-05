package com.km.rmbank.dto;

/**
 * Created by PengSong on 18/2/5.
 */

public class CalendarActionsDto {

    /**
     * id : 112
     * isDynamic : 0   是否编辑资讯
     0未编辑 1已编辑
     * startDate : 1523062800000
     */

    private String id;
    private String isDynamic;
    private long startDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsDynamic() {
        return isDynamic;
    }

    public void setIsDynamic(String isDynamic) {
        this.isDynamic = isDynamic;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "CalendarActionsDto{" +
                "id='" + id + '\'' +
                ", isDynamic='" + isDynamic + '\'' +
                ", startDate=" + startDate +
                '}';
    }
}
