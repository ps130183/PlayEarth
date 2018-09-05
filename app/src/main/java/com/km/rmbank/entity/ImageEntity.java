package com.km.rmbank.entity;

import com.km.rmbank.R;
import com.ps.mrcyclerview.delegate.ItemDelegate;

/**
 * Created by kamangkeji on 17/3/29.
 */

public class ImageEntity implements ItemDelegate {
    private String imagePath;
    private String imageUrl;
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

    @Override
    public int getItemViewRes() {
        return R.layout.item_select_image;
    }
}
