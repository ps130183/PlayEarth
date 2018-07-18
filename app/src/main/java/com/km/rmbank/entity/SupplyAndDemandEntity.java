package com.km.rmbank.entity;

import com.km.rmbank.R;
import com.ps.mrcyclerview.delegate.ItemDelegate;

/**
 * Created by PengSong on 18/7/12.
 */

public class SupplyAndDemandEntity implements ItemDelegate {

    private String content;

    public SupplyAndDemandEntity(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public int getItemViewRes() {
        return R.layout.item_supply_demand;
    }
}
