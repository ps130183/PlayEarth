package com.km.rmbank.dto;

/**
 * Created by kamangkeji on 17/4/13.
 */

public class EvaluateDto {

    /**
     * content : 这个伤情不太好
     * createDate : 1492226720000
     * formatCreateDate : 2017-04-15 11:25:20
     * id : 23424
     * orderNo : PO20170414191004361
     * portraitUrl : http://192.168.31.136:8080/img/user/portrait/201704/2bab076f9c414890a954d24ff0dc272a.jpg
     * productNo : PN20170414154209658
     * starLevel : 0
     * userId : 3
     * userName : 我是老好人007
     * userNickName : 0
     */

    private String content;
    private String createDate;
    private String formatCreateDate;
    private String id;
    private String orderNo;
    private String portraitUrl;
    private String productNo;
    private int starLevel;
    private String userId;
    private String userName;
    private String userNickName;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getFormatCreateDate() {
        return formatCreateDate;
    }

    public void setFormatCreateDate(String formatCreateDate) {
        this.formatCreateDate = formatCreateDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPortraitUrl() {
        return portraitUrl;
    }

    public void setPortraitUrl(String portraitUrl) {
        this.portraitUrl = portraitUrl;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public int getStarLevel() {
        return starLevel;
    }

    public void setStarLevel(int starLevel) {
        this.starLevel = starLevel;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }
}
