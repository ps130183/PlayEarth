package com.km.rmbank.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.km.rmbank.R;
import com.ps.mrcyclerview.delegate.ItemDelegate;

/**
 * Created by PengSong on 18/6/13.
 */

public class OtherIdentityEntity implements ItemDelegate, Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.company);
        dest.writeString(this.position);
    }

    public OtherIdentityEntity() {
    }

    protected OtherIdentityEntity(Parcel in) {
        this.company = in.readString();
        this.position = in.readString();
    }

    public static final Parcelable.Creator<OtherIdentityEntity> CREATOR = new Parcelable.Creator<OtherIdentityEntity>() {
        @Override
        public OtherIdentityEntity createFromParcel(Parcel source) {
            return new OtherIdentityEntity(source);
        }

        @Override
        public OtherIdentityEntity[] newArray(int size) {
            return new OtherIdentityEntity[size];
        }
    };
}
