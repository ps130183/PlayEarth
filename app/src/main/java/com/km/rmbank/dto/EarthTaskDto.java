package com.km.rmbank.dto;

/**
 * Created by PengSong on 18/7/12.
 */

public class EarthTaskDto {

    /**
     * createDate : 2018-07-12 12:40:28.0
     * describe : 签到
     * id : 1
     * number : 1
     * title : 每日签到
     * type : 1
     * value : 2
     */

    private String createDate;
    private String describe;
    private String id;
    private String number;
    private String title;
    private String type;
    private String value;
    private String shareNumber;

    public String getShareNumber() {
        return shareNumber;
    }

    public void setShareNumber(String shareNumber) {
        this.shareNumber = shareNumber;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
