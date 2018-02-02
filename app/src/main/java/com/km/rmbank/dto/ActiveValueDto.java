package com.km.rmbank.dto;

/**
 * Created by kamangkeji on 17/9/11.
 */

public class ActiveValueDto {

    /**
     * freezingActiveValue : 0
     * activeValue : 36
     */

    private int freezingActiveValue;
    private int activeValue;

    public int getFreezingActiveValue() {
        return freezingActiveValue;
    }

    public void setFreezingActiveValue(int freezingActiveValue) {
        this.freezingActiveValue = freezingActiveValue;
    }

    public int getActiveValue() {
        return activeValue;
    }

    public void setActiveValue(int activeValue) {
        this.activeValue = activeValue;
    }
}
