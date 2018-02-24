package com.km.rmbank.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.km.rmbank.utils.DateUtils;

import java.util.Date;

/**
 * Created by PengSong on 18/2/8.
 */

public class CheckDateEntity implements Parcelable {

    private int year;
    private int month;
    private int dayOfMonty;

    private boolean isCheck;


    public CheckDateEntity(int year, int month, int dayOfMonty) {
        this.year = year;
        this.month = month;
        this.dayOfMonty = dayOfMonty;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDayOfMonty() {
        return dayOfMonty;
    }

    public void setDayOfMonty(int dayOfMonty) {
        this.dayOfMonty = dayOfMonty;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    /**
     * 获取年月日所对应的毫秒数
     * @return
     */
    public int getKey(){
        return year + month + dayOfMonty;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.year);
        dest.writeInt(this.month);
        dest.writeInt(this.dayOfMonty);
        dest.writeByte(this.isCheck ? (byte) 1 : (byte) 0);
    }

    protected CheckDateEntity(Parcel in) {
        this.year = in.readInt();
        this.month = in.readInt();
        this.dayOfMonty = in.readInt();
        this.isCheck = in.readByte() != 0;
    }

    public static final Parcelable.Creator<CheckDateEntity> CREATOR = new Parcelable.Creator<CheckDateEntity>() {
        @Override
        public CheckDateEntity createFromParcel(Parcel source) {
            return new CheckDateEntity(source);
        }

        @Override
        public CheckDateEntity[] newArray(int size) {
            return new CheckDateEntity[size];
        }
    };
}
