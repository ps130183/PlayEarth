package com.km.rmbank.dto;

/**
 * Created by kamangkeji on 17/4/21.
 */

public class BannerDto {


    /**
     * avatarUrl : http://192.168.31.136:8080/img/guest/201704/a37a53697e2e4cfc8377876466f376ef.jpeg
     * id : 16
     * type : 1
     */

    private String avatarUrl;
    private String id;
    private String type;
    private String title;
    /**
     * bannerToPage : 0
     * bannerUrl : http://47.93.184.121/img/shop/201706/b03dd475c07f40e49a46cdb6d69ec79c.png
     */

    private int bannerToPage;
    private String bannerUrl;
    /**
     * imageUrl : http://192.168.10.131:8083/Aiyg/images/jidic.png
     * linkUrl : http://www.baidu.com
     */

    private String imageUrl;
    private String linkUrl;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBannerToPage() {
        return bannerToPage;
    }

    public void setBannerToPage(int bannerToPage) {
        this.bannerToPage = bannerToPage;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }
}
