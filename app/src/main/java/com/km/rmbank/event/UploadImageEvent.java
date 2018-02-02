package com.km.rmbank.event;

/**
 * Created by kamangkeji on 17/8/9.
 */

public class UploadImageEvent {
    private String imagePath;
    private int position;

    public UploadImageEvent(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
