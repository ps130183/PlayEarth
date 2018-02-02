package com.km.rmbank.dto;

/**
 * Created by kamangkeji on 17/4/1.
 */

public class UserBalanceDto {
    private String id;//  账户id  String
    private String balance;//  余额  String
    private String freezingAmount;// 冻结金额 String
    private String createDate;// 账户创建时间 String
    private String updateDate;// 账户更新时间 String

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getFreezingAmount() {
        return freezingAmount;
    }

    public void setFreezingAmount(String freezingAmount) {
        this.freezingAmount = freezingAmount;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}
