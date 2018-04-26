package com.km.rmbank.entity;

/**
 * Created by PengSong on 18/4/18.
 */

public class IndustryEntity {
    private String industryName;
    private boolean isChecked;

    public IndustryEntity(String industryName) {
        this.industryName = industryName;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
