package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.km.rmbank.R;
import com.ps.mrcyclerview.delegate.ItemDelegate;

/**
 * Created by PengSong on 18/2/7.
 */

public class MapMarkerDto implements Parcelable,ItemDelegate {
    private String clubName;
    private String id;

    private String address;
    private double latitude;
    private double longitude;
    /**
     * backgroundImg : http://wanzhuandiqiu.com:8080/wzdq/Aiyg/aiygImage/2018/02/417263329c9f4c81b8735d4ef7149fc2.jpg#
     * clubLogo : http://wanzhuandiqiu.com:8080/wzdq/Aiyg/aiygImage/2018/02/dfd06008298c4fb5ba50a1efe93022eb.jpg
     * clubType : 3
     * content : 介绍
     * createDate : 1518490403000
     * isRecommend : 0
     * latitude : 39.954469
     * longitude : 116.380598
     * updateDate : 1518494537000
     * userId :
     */

    private String backgroundImg;
    private String clubLogo;
    private String clubType;
    private String content;
    private long createDate;
    private String isRecommend;
    private long updateDate;
    private String userId;

    private int applyCount;

    public MapMarkerDto(String clubName, double latidude, double longitude) {
        this.clubName = clubName;
        this.latitude = latidude;
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String name) {
        this.clubName = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getApplyCount() {
        return applyCount;
    }

    public void setApplyCount(int applyCount) {
        this.applyCount = applyCount;
    }

    @Override
    public String toString() {
        return "MapMarkerDto{" +
                "clubName='" + clubName + '\'' +
                ", id='" + id + '\'' +
                ", address='" + address + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", backgroundImg='" + backgroundImg + '\'' +
                ", clubLogo='" + clubLogo + '\'' +
                ", clubType='" + clubType + '\'' +
                ", content='" + content + '\'' +
                ", createDate=" + createDate +
                ", isRecommend='" + isRecommend + '\'' +
                ", updateDate=" + updateDate +
                ", userId='" + userId + '\'' +
                ", applyCount=" + applyCount +
                '}';
    }

    public String getBackgroundImg() {
        return backgroundImg;
    }

    public void setBackgroundImg(String backgroundImg) {
        this.backgroundImg = backgroundImg;
    }

    public String getClubLogo() {
        return clubLogo;
    }

    public void setClubLogo(String clubLogo) {
        this.clubLogo = clubLogo;
    }

    public String getClubType() {
        return clubType;
    }

    public void setClubType(String clubType) {
        this.clubType = clubType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(String isRecommend) {
        this.isRecommend = isRecommend;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.clubName);
        dest.writeString(this.id);
        dest.writeString(this.address);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeString(this.backgroundImg);
        dest.writeString(this.clubLogo);
        dest.writeString(this.clubType);
        dest.writeString(this.content);
        dest.writeLong(this.createDate);
        dest.writeString(this.isRecommend);
        dest.writeLong(this.updateDate);
        dest.writeString(this.userId);
        dest.writeInt(this.applyCount);
    }

    protected MapMarkerDto(Parcel in) {
        this.clubName = in.readString();
        this.id = in.readString();
        this.address = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.backgroundImg = in.readString();
        this.clubLogo = in.readString();
        this.clubType = in.readString();
        this.content = in.readString();
        this.createDate = in.readLong();
        this.isRecommend = in.readString();
        this.updateDate = in.readLong();
        this.userId = in.readString();
        this.applyCount = in.readInt();
    }

    public static final Creator<MapMarkerDto> CREATOR = new Creator<MapMarkerDto>() {
        @Override
        public MapMarkerDto createFromParcel(Parcel source) {
            return new MapMarkerDto(source);
        }

        @Override
        public MapMarkerDto[] newArray(int size) {
            return new MapMarkerDto[size];
        }
    };

    @Override
    public int getItemViewRes() {
        return R.layout.item_scenic_list;
    }
}
