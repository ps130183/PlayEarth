package com.km.rmbank.event;

/**
 * Created by kamangkeji on 17/8/14.
 */

public class UpdateViewPagerHeightEvent {
    private int height;

    public UpdateViewPagerHeightEvent(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }
}
