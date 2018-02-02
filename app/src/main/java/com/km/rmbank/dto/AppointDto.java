package com.km.rmbank.dto;

/**
 * Created by PengSong on 18/1/17.
 */

public class AppointDto {

    /**
     * activityPictureUrl : http://47.93.184.121:8080/wzdq/Aiyg/aiygImage/2018/01/394afd846a1e4be48308e8f6c2ea06e8.png
     * address :
     * applyCount :
     * createDate : 1517026817000
     * id : 226
     * status : 活动已结束
     * title : 本期封面人物-姚丹丹
     * type : 2
     * viewCount : 10
     */

    private String activityPictureUrl;
    private String address;
    private String applyCount;
    private long createDate;
    private String id;
    private String status;
    private String title;
    private String type;
    private String viewCount;
    /**
     * startDate : 1516527000000
     */

    private long startDate;

    public String getActivityPictureUrl() {
        return activityPictureUrl;
    }

    public void setActivityPictureUrl(String activityPictureUrl) {
        this.activityPictureUrl = activityPictureUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getApplyCount() {
        return applyCount;
    }

    public void setApplyCount(String applyCount) {
        this.applyCount = applyCount;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }
}
