package com.km.rmbank.dto;

/**
 * Created by PengSong on 18/8/21.
 */

public class ReleaseActionDetailsDto {

    /**
     * activityType : 3
     * content : http://47.93.184.121:8081/img/clubAll/201808/20ab374e31814b99951803fccd94277c.jpg#http://47.93.184.121:8081/img/clubAll/201808/bda54abded1140fb920a6c261549f31c.jpg#http://47.93.184.121:8081/img/clubAll/201808/b3a03dc7f6e14d8e925c098ea5312fc9.jpg#http://47.93.184.121:8081/img/clubAll/201808/944b9a96e6f44e22a2144d22d11ab8a2.jpg#http://47.93.184.121:8081/img/clubAll/201808/467f610ccfdf4bc4bb96c251aa4f1a17.jpg
     * createDate : 1534795216000
     * endDate : 1535112300000
     * flow : 1.他了了了了了谨慎betray凑合，
     2，额悟空出血额聚了聚额路上。
     3，默契度裤子不
     4，集训都挺
     * id : 201
     * isDynamic : 0
     * placeReservationId : 15
     * placeTitle : 唐人汇天天英雄会
     * startDate : 1535054700000
     * title : 下午茶活动测试
     * updateDate : 1534795216000
     */

    private String activityType;//1通过  2拒绝  3 审核中
    private String content;
    private long createDate;
    private long endDate;
    private String flow;
    private String id;
    private String isDynamic;
    private String placeReservationId;
    private String placeTitle;
    private long startDate;
    private String title;
    private long updateDate;
    private String address;
    private String reason;
    private String webActivityUrl;

    public String getWebActivityUrl() {
        return webActivityUrl;
    }

    public void setWebActivityUrl(String webActivityUrl) {
        this.webActivityUrl = webActivityUrl;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }

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

    public String getPlaceReservationId() {
        return placeReservationId;
    }

    public void setPlaceReservationId(String placeReservationId) {
        this.placeReservationId = placeReservationId;
    }

    public String getPlaceTitle() {
        return placeTitle;
    }

    public void setPlaceTitle(String placeTitle) {
        this.placeTitle = placeTitle;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
