package com.km.rmbank.entity;

import com.km.rmbank.R;
import com.ps.mrcyclerview.delegate.ItemDelegate;

/**
 * Created by PengSong on 18/8/11.
 */

public class HomeBookVenueEntity implements ItemDelegate {

    private int imageRes;
    private String venueName;

    public HomeBookVenueEntity(int imageRes, String venueName) {
        this.imageRes = imageRes;
        this.venueName = venueName;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    @Override
    public int getItemViewRes() {
        return R.layout.item_home_can_book_venue;
    }
}
