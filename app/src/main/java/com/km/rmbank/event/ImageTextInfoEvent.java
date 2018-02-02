package com.km.rmbank.event;

import java.util.List;

/**
 * Created by kamangkeji on 17/8/30.
 */

public class ImageTextInfoEvent {
    private String textContent;
    private List<String> imageList;
    private int position;

    public ImageTextInfoEvent(String textContent, List<String> imageList) {
        this.textContent = textContent;
        this.imageList = imageList;
        position = -1;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
