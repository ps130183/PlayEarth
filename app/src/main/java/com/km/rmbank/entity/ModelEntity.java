package com.km.rmbank.entity;

/**
 * Created by PengSong on 18/1/18.
 */

public class ModelEntity {
    private int modelRes;
    private String modelImageUrl;
    private String modelName;

    public ModelEntity(int modelRes, String modelName) {
        this.modelRes = modelRes;
        this.modelName = modelName;
    }

    public ModelEntity(String modelImageUrl, String modelName) {
        this.modelImageUrl = modelImageUrl;
        this.modelName = modelName;
    }

    public String getModelImageUrl() {
        return modelImageUrl;
    }

    public void setModelImageUrl(String modelImageUrl) {
        this.modelImageUrl = modelImageUrl;
    }

    public int getModelRes() {
        return modelRes;
    }

    public void setModelRes(int modelRes) {
        this.modelRes = modelRes;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
}
