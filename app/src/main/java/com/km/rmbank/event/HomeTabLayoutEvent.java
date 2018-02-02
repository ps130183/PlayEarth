package com.km.rmbank.event;

/**
 * Created by kamangkeji on 17/7/22.
 */

public class HomeTabLayoutEvent {
    boolean isHide;

    public HomeTabLayoutEvent(boolean isHide) {
        this.isHide = isHide;
    }

    public boolean isHide() {
        return isHide;
    }

    public void setHide(boolean hide) {
        isHide = hide;
    }
}
