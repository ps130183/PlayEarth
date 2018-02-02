package com.km.rmbank.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kamangkeji on 17/4/4.
 */

public class WeiCharParamsDto {

    /**
     * noncestr : 605178563939402ca1d7fe694c38cb38
     * partnerid : 1381321402
     * prepayid : wx20170409154555e64ba2dcf60042943943
     * package : Sign=WXPay
     * appid : wxed2472eaa0db4e8d
     */

    private String noncestr;
    private String partnerid;
    private String prepayid;
    @SerializedName("package")
    private String packageX;
    private String appid;

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    @Override
    public String toString() {
        return "WeiCharParamsDto{" +
                "noncestr='" + noncestr + '\'' +
                ", partnerid='" + partnerid + '\'' +
                ", prepayid='" + prepayid + '\'' +
                ", packageX='" + packageX + '\'' +
                ", appid='" + appid + '\'' +
                '}';
    }
}
