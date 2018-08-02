package com.km.rmbank.dto;

import com.km.rmbank.R;
import com.ps.mrcyclerview.delegate.ItemDelegate;

/**
 * Created by PengSong on 18/7/30.
 */

public class CrowdFundingPersonsDto implements ItemDelegate {

    private String userName;
    private String price;
    private long createDate;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "CrowdFundingPersonsDto{" +
                "userName='" + userName + '\'' +
                ", price='" + price + '\'' +
                ", createDate=" + createDate +
                '}';
    }

    @Override
    public int getItemViewRes() {
        return R.layout.item_crowd_funding_persons;
    }
}
