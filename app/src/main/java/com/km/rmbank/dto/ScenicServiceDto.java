package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PengSong on 18/2/11.
 */

public class ScenicServiceDto implements Parcelable {
    private String id;
    private String name;
    private String clubName;
    private double price;
    private String content;

    private int maxDay;
    /**
     * price : 188
     * startDate : 1521704869000
     */

    private long startDate;
    /**
     * maxDay : 2
     * maxReserve : -13
     * price : 188
     */

    private int maxReserve;


    public ScenicServiceDto() {
    }


    public int getMaxDay() {
        if (maxDay == 0){
            maxDay = 1;
        }
        return maxDay;
    }

    public void setMaxDay(int maxDay) {
        this.maxDay = maxDay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ScenicServiceDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", clubName='" + clubName + '\'' +
                ", price=" + price +
                ", content='" + content + '\'' +
                ", maxDay=" + maxDay +
                ", startDate=" + startDate +
                '}';
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.clubName);
        dest.writeDouble(this.price);
        dest.writeString(this.content);
        dest.writeInt(this.maxDay);
        dest.writeLong(this.startDate);
    }

    protected ScenicServiceDto(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.clubName = in.readString();
        this.price = in.readDouble();
        this.content = in.readString();
        this.maxDay = in.readInt();
        this.startDate = in.readLong();
    }

    public static final Creator<ScenicServiceDto> CREATOR = new Creator<ScenicServiceDto>() {
        @Override
        public ScenicServiceDto createFromParcel(Parcel source) {
            return new ScenicServiceDto(source);
        }

        @Override
        public ScenicServiceDto[] newArray(int size) {
            return new ScenicServiceDto[size];
        }
    };

    public int getMaxReserve() {
        return maxReserve;
    }

    public void setMaxReserve(int maxReserve) {
        this.maxReserve = maxReserve;
    }
}
