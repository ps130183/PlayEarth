package com.km.rmbank.event;

/**
 * Created by kamangkeji on 17/4/10.
 */

public class ConfirmGoodsNumberEvent {
    private int goodsNumber;

    public ConfirmGoodsNumberEvent(int goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public int getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(int goodsNumber) {
        this.goodsNumber = goodsNumber;
    }
}
