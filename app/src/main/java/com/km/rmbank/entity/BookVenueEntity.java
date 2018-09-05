package com.km.rmbank.entity;

import com.km.rmbank.R;
import com.ps.mrcyclerview.delegate.ItemDelegate;

/**
 * Created by PengSong on 18/8/11.
 */

public class BookVenueEntity implements ItemDelegate {

    private int logoRes;
    private String name;
    private boolean checked;

    public BookVenueEntity(int logoRes, String name) {
        this.logoRes = logoRes;
        this.name = name;
    }

    public int getLogoRes() {
        return logoRes;
    }

    public void setLogoRes(int logoRes) {
        this.logoRes = logoRes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public int getItemViewRes() {
        return R.layout.item_book_venue_type;
    }
}
