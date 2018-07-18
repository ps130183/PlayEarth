package com.km.rmbank.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.km.rmbank.R;
import com.ps.mrcyclerview.delegate.ItemDelegate;

/**
 * Created by PengSong on 18/7/11.
 */

public class DemandEntity implements ItemDelegate, Parcelable {

    private String industryId;
    private String industryName;
    private String resources;

    public DemandEntity() {
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

    protected DemandEntity(Parcel in) {
        this.industryId = in.readString();
        this.industryName = in.readString();
        this.resources = in.readString();
    }

    public static final Parcelable.Creator<DemandEntity> CREATOR = new Parcelable.Creator<DemandEntity>() {
        @Override
        public DemandEntity createFromParcel(Parcel source) {
            return new DemandEntity(source);
        }

        @Override
        public DemandEntity[] newArray(int size) {
            return new DemandEntity[size];
        }
    };
}
