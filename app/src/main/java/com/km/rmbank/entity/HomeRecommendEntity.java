package com.km.rmbank.entity;

/**
 * Created by PengSong on 18/3/14.
 */

public class HomeRecommendEntity {

    private String recommendName;
    private int recommendType;
    private boolean isHasImage;
    private int guangGaoWei;

    public HomeRecommendEntity(String recommendName, int recommendType) {
        this.recommendName = recommendName;
        this.recommendType = recommendType;
    }

    public HomeRecommendEntity(String recommendName, int recommendType, int guangGaoWei) {
        this.recommendName = recommendName;
        this.recommendType = recommendType;
        this.guangGaoWei = guangGaoWei;
    }

    public String getRecommendName() {
        return recommendName;
    }

    public void setRecommendName(String recommendName) {
        this.recommendName = recommendName;
    }

    public boolean isHasImage() {
        return isHasImage;
    }

    public void setHasImage(boolean hasImage) {
        isHasImage = hasImage;
    }

    public int getRecommendType() {
        return recommendType;
    }

    public void setRecommendType(int recommendType) {
        this.recommendType = recommendType;
    }

    public int getGuangGaoWei() {
        return guangGaoWei;
    }

    public void setGuangGaoWei(int guangGaoWei) {
        this.guangGaoWei = guangGaoWei;
    }
}




