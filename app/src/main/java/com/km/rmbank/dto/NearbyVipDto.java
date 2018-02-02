package com.km.rmbank.dto;

/**
 * Created by kamangkeji on 17/6/5.
 */

public class NearbyVipDto {

    /**
     * portraitUrl : http://47.93.184.121/img/user/portrait/201705/29a948052b19412caaefa4108a4b0e99.png
     * distance : 2
     * mobilePhone : 15201200083
     * nickName : 韩小莲
     * allowStutas :
     */

    private String portraitUrl;
    private String distance;
    private String mobilePhone;
    private String nickName;
    private String allowStutas;

    public String getPortraitUrl() {
        return portraitUrl;
    }

    public void setPortraitUrl(String portraitUrl) {
        this.portraitUrl = portraitUrl;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAllowStutas() {
        return allowStutas;
    }

    public void setAllowStutas(String allowStutas) {
        this.allowStutas = allowStutas;
    }
}
