package com.km.rmbank.dto;

import java.util.List;

/**
 * Created by PengSong on 18/7/31.
 */

public class MyCrowdFundingInfoDto {
    private double sumAmount;
    private double sumPrice;
    private String personNum;

    private List<CrowdFundingPersonsDto> userCrowdFundingList;

    //分享的内容
    private String linkUrl;
    private String title;
    private String content;
    private String imgUrl;

    public double getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(double sumAmount) {
        this.sumAmount = sumAmount;
    }

    public double getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(double sumPrice) {
        this.sumPrice = sumPrice;
    }

    public String getPersonNum() {
        return personNum;
    }

    public void setPersonNum(String personNum) {
        this.personNum = personNum;
    }

    public List<CrowdFundingPersonsDto> getUserCrowdFundingList() {
        return userCrowdFundingList;
    }

    public void setUserCrowdFundingList(List<CrowdFundingPersonsDto> userCrowdFundingList) {
        this.userCrowdFundingList = userCrowdFundingList;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "MyCrowdFundingInfoDto{" +
                "sumAmount=" + sumAmount +
                ", sumPrice=" + sumPrice +
                ", personNum='" + personNum + '\'' +
                ", userCrowdFundingList=" + userCrowdFundingList +
                ", linkUrl='" + linkUrl + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
