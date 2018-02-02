package com.km.rmbank.entity;

/**
 * Created by PengSong on 18/1/26.
 */

public class ImageTextIntroduceEntity {
    private String content;
    private int imageRes;
    private String imageUrl;

    public ImageTextIntroduceEntity(String content, int imageRes) {
        this.content = content;
        this.imageRes = imageRes;
    }

    public ImageTextIntroduceEntity(String content, String imageUrl) {
        this.content = content;
        this.imageUrl = imageUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
