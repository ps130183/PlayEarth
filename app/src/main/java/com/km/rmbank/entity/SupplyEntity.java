package com.km.rmbank.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.km.rmbank.R;
import com.km.rmbank.dto.IndustryDto;
import com.ps.mrcyclerview.delegate.ItemDelegate;

/**
 * Created by PengSong on 18/7/11.
 */

public class SupplyEntity implements ItemDelegate, Parcelable {

    private String industryId;
    private String industryName;
    private String resources;

    public SupplyEntity() {

    }


    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    @Override
    public int getItemViewRes() {
        return R.layout.item_user_card_other_identity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.industryId);
        dest.writeString(this.industryName);
        dest.writeString(this.resources);
    }

    protected SupplyEntity(Parcel in) {
        this.industryId = in.readString();
        this.industryName = in.readString();
        this.resources = in.readString();
    }

    public static final Parcelable.Creator<SupplyEntity> CREATOR = new Parcelable.Creator<SupplyEntity>() {
        @Override
        public SupplyEntity createFromParcel(Parcel source) {
            return new SupplyEntity(source);
        }

        @Override
        public SupplyEntity[] newArray(int size) {
            return new SupplyEntity[size];
        }
    };
}
