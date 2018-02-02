package com.km.rmbank.dto;

/**
 * Created by PengSong on 17/10/25.
 */

public class MasterOrderDto {

    /**
     * createDate : 1508139757000
     * id : 20
     * macaId : 2
     * macaName : 范冰冰
     * macaWorks : 苹果
     * macaWorksId : 2
     * money : 1000.0
     * orderNo : MO2017101615423776
     * status : 3
     * tradeChannel : 0
     * updateDate : 1508139757000
     * userId : 241
     * userName : 松
     * userPhone : 18888888888
     */

    private long createDate;
    private String id;
    private String macaId;
    private String macaName;
    private String macaWorks;
    private String macaWorksId;
    private double money;
    private String orderNo;
    private String status;
    private int tradeChannel;
    private long updateDate;
    private String userId;
    private String userName;
    private String userPhone;
    private String portraitUrl;

    public String getPortraitUrl() {
        return portraitUrl;
    }

    public void setPortraitUrl(String portraitUrl) {
        this.portraitUrl = portraitUrl;
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

    public String getMacaId() {
        return macaId;
    }

    public void setMacaId(String macaId) {
        this.macaId = macaId;
    }

    public String getMacaName() {
        return macaName;
    }

    public void setMacaName(String macaName) {
        this.macaName = macaName;
    }

    public String getMacaWorks() {
        return macaWorks;
    }

    public void setMacaWorks(String macaWorks) {
        this.macaWorks = macaWorks;
    }

    public String getMacaWorksId() {
        return macaWorksId;
    }

    public void setMacaWorksId(String macaWorksId) {
        this.macaWorksId = macaWorksId;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTradeChannel() {
        return tradeChannel;
    }

    public void setTradeChannel(int tradeChannel) {
        this.tradeChannel = tradeChannel;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
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

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}
