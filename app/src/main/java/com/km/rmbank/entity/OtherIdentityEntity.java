package com.km.rmbank.entity;

import com.km.rmbank.R;
import com.ps.mrcyclerview.delegate.ItemDelegate;

/**
 * Created by PengSong on 18/6/13.
 */

public class OtherIdentityEntity implements ItemDelegate {

    private String company;
    private String position;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "OtherIdentityEntity{" +
                "company='" + company + '\'' +
                ", position='" + position + '\'' +
                '}';
    }

    @Override
    public int getItemViewRes() {
        return R.layout.item_user_card_other_identity;
    }
}
