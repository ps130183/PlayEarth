package com.km.rmbank.entity;

/**
 * Created by PengSong on 18/1/18.
 */

public class ModelEntity {
    private int modelRes;
    private String modelName;

    public ModelEntity(int modelRes, String modelName) {
        this.modelRes = modelRes;
        this.modelName = modelName;
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
