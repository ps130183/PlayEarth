package com.km.rmbank.dto;

/**
 * Created by kamangkeji on 17/4/1.
 */

public class UserAccountDetailDto {

    /**
     * amount : 100
     * tradeDirection : 1
     * 0已提现1提现中2已购买商品3卖出商品4邀请合伙人会员5邀请体验式会员6子级邀请合伙人7子级卖出商品获得金额
     * tradeType : 026
     * mobilePhone : 15631707132
     * createDate : 2017-04-06 14:47:13
     */

    private String amount;
    private String tradeDirection;
    private String tradeType;
    private String mobilePhone;
    private String createDate;
    private String explain;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTradeDirection() {
        return tradeDirection;
    }

    public void setTradeDirection(String tradeDirection) {
        this.tradeDirection = tradeDirection;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}
