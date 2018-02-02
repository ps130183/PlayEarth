package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamangkeji on 17/3/23.
 */

public class IndustryDto implements Parcelable {

    private int id;
    private String industryName;
    private int parentId;

    private boolean isShow;

    private boolean isChecked;

    private List<IndustryDto> industryList;

    public IndustryDto(String name) {
        this.industryName = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return industryName;
    }

    public void setName(String name) {
        this.industryName = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.industryName);
        dest.writeInt(this.parentId);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
        dest.writeList(this.industryList);
    }

    protected IndustryDto(Parcel in) {
        this.id = in.readInt();
        this.industryName = in.readString();
        this.parentId = in.readInt();
        this.isChecked = in.readByte() != 0;
        this.industryList = new ArrayList<IndustryDto>();
        in.readList(this.industryList, IndustryDto.class.getClassLoader());
    }

    public static final Parcelable.Creator<IndustryDto> CREATOR = new Parcelable.Creator<IndustryDto>() {
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
