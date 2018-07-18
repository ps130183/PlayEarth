package com.km.rmbank.entity;

import com.km.rmbank.R;
import com.ps.mrcyclerview.delegate.ItemDelegate;

/**
 * Created by PengSong on 18/1/18.
 */

public class ModelEntity implements ItemDelegate {
    private int modelRes;
    private String modelName;
    private String modelImageUrl;
    private boolean isSingle;

    private int itemLayoutRes;

    public ModelEntity(int modelRes, String modelName) {
        this.modelRes = modelRes;
        this.modelName = modelName;
    }

    public ModelEntity(String modelImageUrl,String modelName) {
        this.modelName = modelName;
        this.modelImageUrl = modelImageUrl;
    }

    public ModelEntity(int modelRes, String modelName, boolean isSingle) {
        this.modelRes = modelRes;
        this.modelName = modelName;
        this.isSingle = isSingle;
    }

    public boolean isSingle() {
        return isSingle;
    }

    public void setSingle(boolean single) {
        isSingle = single;
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

    public void setItemLayoutRes(int itemLayoutRes) {
        this.itemLayoutRes = itemLayoutRes;
    }

    @Override
    public int getItemViewRes() {
//        return R.layout.item_home_new_module;
        return itemLayoutRes;
    }
}
