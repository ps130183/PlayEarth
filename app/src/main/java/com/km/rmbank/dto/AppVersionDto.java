package com.km.rmbank.dto;

/**
 * Created by kamangkeji on 17/4/19.
 */

public class AppVersionDto {

    /**
     * appUrl : http://bobytest.com/down/201704/f48c01291a5e4b3189c848720401184b.apk
     * createDate : 1492858506000
     * id : 2
     * isForce : 1
     * os : android
     * version : 4
     * versionView : 1.0.1
     */

    private String appUrl;
    private String createDate;
    private String id;
    private int foce;//1:强制更新  0：不强制
    private String os;
    private String version;
    private String versionView;

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getFoce() {
        return foce;
    }

    public void setFoce(int foce) {
        this.foce = foce;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersionView() {
        return versionView;
    }

    public void setVersionView(String versionView) {
        this.versionView = versionView;
    }

    @Override
    public String toString() {
        return "AppVersionDto{" +
                "appUrl='" + appUrl + '\'' +
                ", createDate='" + createDate + '\'' +
                ", id='" + id + '\'' +
                ", isForce=" + foce +
                ", os='" + os + '\'' +
                ", version='" + version + '\'' +
                ", versionView='" + versionView + '\'' +
                '}';
    }
}
