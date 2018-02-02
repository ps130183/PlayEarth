package com.km.rmbank.event;

/**
 * Created by kamangkeji on 17/4/10.
 */

public class GoodsDetailNumberEvent {
    private int number;

    public GoodsDetailNumberEvent(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
