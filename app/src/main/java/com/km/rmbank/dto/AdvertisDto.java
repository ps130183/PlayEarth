package com.km.rmbank.dto;

/**
 * Created by PengSong on 18/8/23.
 */

public class AdvertisDto {

    /**
     * adUrl : http://www.wanzhuandiqiu.com/accounts/advertising/index.html
     * createDate : 1535008905000
     * description : 这是一次神奇之旅
     * id : 1
     * imgUrl : http://wanzhuandiqiu.com/img/user/portrait/201709/gaotou.png
     * isPopup : 1
     * relevanceId : 11
     * sort : 0
     * title : 活动广告
     * type : 1
     * updateDate : 1535012325000
     */

    private String adUrl;
    private long createDate;
    private String description;
    private String id;
    private String imgUrl;
    private String isPopup;
    private String relevanceId;
    private String sort;
    private String title;
    private String type;
    private long updateDate;

    public String getAdUrl() {
        return adUrl;
    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getIsPopup() {
        return isPopup;
    }

    public void setIsPopup(String isPopup) {
        this.isPopup = isPopup;
    }

    public String getRelevanceId() {
        return relevanceId;
    }

    public void setRelevanceId(String relevanceId) {
        this.relevanceId = relevanceId;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
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

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }
}
