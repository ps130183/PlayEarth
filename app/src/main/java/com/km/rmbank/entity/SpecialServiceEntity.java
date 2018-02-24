package com.km.rmbank.entity;

/**
 * Created by PengSong on 18/2/7.
 */

public class SpecialServiceEntity {
    private String title;
    private float price;

    public SpecialServiceEntity(String title, float price) {
        this.title = title;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
