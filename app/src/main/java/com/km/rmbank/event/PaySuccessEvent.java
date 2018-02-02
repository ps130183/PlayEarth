package com.km.rmbank.event;

/**
 * Created by kamangkeji on 17/4/13.
 */

public class PaySuccessEvent {
    private int payType;//0:商品 5：约咖

    public PaySuccessEvent(int payType) {
        this.payType = payType;
    }

    public int getPayType() {
        return payType;
    }
}
