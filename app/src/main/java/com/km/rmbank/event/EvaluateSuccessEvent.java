package com.km.rmbank.event;

/**
 * Created by kamangkeji on 17/4/15.
 */

public class EvaluateSuccessEvent {
    private String orderNo;

    public EvaluateSuccessEvent(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
