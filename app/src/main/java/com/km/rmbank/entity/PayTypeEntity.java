package com.km.rmbank.entity;

/**
 * Created by PengSong on 18/5/5.
 * 支付方式
 */

public class PayTypeEntity {
    private int payImageRes;
    private String payName;
    private boolean isChecked;

    public PayTypeEntity(int payImageRes, String payName) {
        this.payImageRes = payImageRes;
        this.payName = payName;
    }

    public int getPayImageRes() {
        return payImageRes;
    }

    public void setPayImageRes(int payImageRes) {
        this.payImageRes = payImageRes;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
