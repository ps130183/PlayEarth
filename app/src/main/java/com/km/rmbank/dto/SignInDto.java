package com.km.rmbank.dto;

/**
 * Created by PengSong on 17/12/28.
 */

public class SignInDto {

    /**
     * activityId : 106
     * activityName : 测试活动
     * backMoney : 50
     * createDate : 1514442983000
     * id : 10181
     * registrationName : 刘汉梁
     * registrationPhone : 13693640758
     * signCode : 8442
     * status : 1
     * teachingPhone : 15631707132
     */

    private String activityId;
    private String activityName;
    private int backMoney;
    private long createDate;
    private String id;
    private String registrationName;
    private String registrationPhone;
    private String signCode;
    private String status;
    private String teachingPhone;
    private String sumTotal;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public int getBackMoney() {
        return backMoney;
    }

    public String getSumTotal() {
        return sumTotal;
    }

    public void setSumTotal(String sumTotal) {
        this.sumTotal = sumTotal;
    }

    public void setBackMoney(int backMoney) {
        this.backMoney = backMoney;
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

    public String getRegistrationName() {
        return registrationName;
    }

    public void setRegistrationName(String registrationName) {
        this.registrationName = registrationName;
    }

    public String getRegistrationPhone() {
        return registrationPhone;
    }

    public void setRegistrationPhone(String registrationPhone) {
        this.registrationPhone = registrationPhone;
    }

    public String getSignCode() {
        return signCode;
    }

    public void setSignCode(String signCode) {
        this.signCode = signCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTeachingPhone() {
        return teachingPhone;
    }

    public void setTeachingPhone(String teachingPhone) {
        this.teachingPhone = teachingPhone;
    }
}
