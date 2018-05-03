package com.baidu.ocr.ui;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * Created by PengSong on 18/4/27.
 */

public class IDCardEntity implements Parcelable {
    //身份证 正面信息
    private String name;
    private String sex;
    private String nation;
    private String num;
    private String address;

    //身份证 反面信息
    private long startDate;
    private long endDate;
    private String unit;

    public IDCardEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "IDCardEntity{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", nation='" + nation + '\'' +
                ", num='" + num + '\'' +
                ", address='" + address + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", unit='" + unit + '\'' +
                '}';
    }

    /**
     * 身份证前面是否为空
     * @return
     */
    public boolean idCardFrontIsEmpty(){
        if (TextUtils.isEmpty(name)
                || TextUtils.isEmpty(sex)
                || TextUtils.isEmpty(nation)
                || TextUtils.isEmpty(num)
                || TextUtils.isEmpty(address)){
            return true;
        }
        return false;
    }

    /**
     * 身份证后边是否为空
     * @return
     */
    public boolean idCardBackIsEmpty(){
        if (startDate == 0 || endDate == 0 || TextUtils.isEmpty(unit)){
            return true;
        }
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.sex);
        dest.writeString(this.nation);
        dest.writeString(this.num);
        dest.writeString(this.address);
        dest.writeLong(this.startDate);
        dest.writeLong(this.endDate);
        dest.writeString(this.unit);
    }

    protected IDCardEntity(Parcel in) {
        this.name = in.readString();
        this.sex = in.readString();
        this.nation = in.readString();
        this.num = in.readString();
        this.address = in.readString();
        this.startDate = in.readLong();
        this.endDate = in.readLong();
        this.unit = in.readString();
    }

    public static final Creator<IDCardEntity> CREATOR = new Creator<IDCardEntity>() {
        @Override
        public IDCardEntity createFromParcel(Parcel source) {
            return new IDCardEntity(source);
        }

        @Override
        public IDCardEntity[] newArray(int size) {
            return new IDCardEntity[size];
        }
    };
}
