package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.km.rmbank.R;
import com.ps.mrcyclerview.delegate.ItemDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamangkeji on 17/3/23.
 */

public class IndustryDto implements ItemDelegate,Parcelable {

    private String id;
    private String industryName;
    private String parentId;

    private boolean isShow;

    private boolean isChecked;

    private List<IndustryDto> industryList;

    public IndustryDto(String name) {
        this.industryName = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return industryName;
    }

    public void setName(String name) {
        this.industryName = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public List<IndustryDto> getIndustryList() {
        return industryList;
    }

    public void setIndustryList(List<IndustryDto> industryList) {
        this.industryList = industryList;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    @Override
    public String toString() {
        return "IndustryDto{" +
                "id=" + id +
                ", industryName='" + industryName + '\'' +
                ", parentId=" + parentId +
                ", isChecked=" + isChecked +
                ", industryList=" + industryList +
                '}';
    }


    @Override
    public int getItemViewRes() {
        return R.layout.item_industry;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.industryName);
        dest.writeString(this.parentId);
        dest.writeByte(this.isShow ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.industryList);
    }

    protected IndustryDto(Parcel in) {
        this.id = in.readString();
        this.industryName = in.readString();
        this.parentId = in.readString();
        this.isShow = in.readByte() != 0;
        this.isChecked = in.readByte() != 0;
        this.industryList = in.createTypedArrayList(IndustryDto.CREATOR);
    }

    public static final Creator<IndustryDto> CREATOR = new Creator<IndustryDto>() {
        @Override
        public IndustryDto createFromParcel(Parcel source) {
            return new IndustryDto(source);
        }

        @Override
        public IndustryDto[] newArray(int size) {
            return new IndustryDto[size];
        }
    };
}
