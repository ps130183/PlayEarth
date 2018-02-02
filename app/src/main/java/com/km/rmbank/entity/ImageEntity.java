package com.km.rmbank.entity;

/**
 * Created by kamangkeji on 17/3/29.
 */

public class ImageEntity {
    private String imagePath;
    private int progress;
    public ImageEntity(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
