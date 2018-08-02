package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.km.rmbank.R;
import com.ps.mrcyclerview.delegate.ItemDelegate;

/**
 * Created by PengSong on 18/1/17.
 */

public class AppointDto implements Parcelable,ItemDelegate {

    /**
     * activityPictureUrl : http://47.93.184.121:8080/wzdq/Aiyg/aiygImage/2018/01/394afd846a1e4be48308e8f6c2ea06e8.png
     * address :
     * applyCount :
     * createDate : 1517026817000
     * id : 226
     * status : 活动已结束
     * title : 本期封面人物-姚丹丹
     * type : 2
     * viewCount : 10
     */

    private String activityPictureUrl;
    private String address;
    private String applyCount;
    private long createDate;
    private String id;
    private String status;
    private String title;
    private String type;
    private String viewCount;
    /**
     * startDate : 1516527000000
     */

    private long startDate;
    /**
     * 基地数据
     * clubId : 88
     * newType : 3
     * videoUrl :
     */

    private String clubId;
    private String newType;
    private String videoUrl;

    private String isDynamic;
    private String activityId;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getIsDynamic() {
        return isDynamic;
    }

    public void setIsDynamic(String isDynamic) {
        this.isDynamic = isDynamic;
    }

    public String getActivityPictureUrl() {
        return activityPictureUrl;
    }

    public void setActivityPictureUrl(String activityPictureUrl) {
        this.activityPictureUrl = activityPictureUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getApplyCount() {
        return applyCount;
    }

    public void setApplyCount(String applyCount) {
        this.applyCount = applyCount;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getNewType() {
        return newType;
    }

    public void setNewType(String newType) {
        this.newType = newType;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public AppointDto() {
    }

    @Override
    public int getItemViewRes() {
        return R.layout.item_appoint;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.activityPictureUrl);
        dest.writeString(this.address);
        dest.writeString(this.applyCount);
        dest.writeLong(this.createDate);
        dest.writeString(this.id);
        dest.writeString(this.status);
        dest.writeString(this.title);
        dest.writeString(this.type);
        dest.writeString(this.viewCount);
        dest.writeLong(this.startDate);
        dest.writeString(this.clubId);
        dest.writeString(this.newType);
        dest.writeString(this.videoUrl);
        dest.writeString(this.isDynamic);
    }

    protected AppointDto(Parcel in) {
        this.activityPictureUrl = in.readString();
        this.address = in.readString();
        this.applyCount = in.readString();
        this.createDate = in.readLong();
        this.id = in.readString();
        this.status = in.readString();
        this.title = in.readString();
        this.type = in.readString();
        this.viewCount = in.readString();
        this.startDate = in.readLong();
        this.clubId = in.readString();
        this.newType = in.readString();
        this.videoUrl = in.readString();
        this.isDynamic = in.readString();
    }

    public static final Creator<AppointDto> CREATOR = new Creator<AppointDto>() {
        @Override
        public AppointDto createFromParcel(Parcel source) {
            return new AppointDto(source);
        }

        @Override
        public AppointDto[] newArray(int size) {
            return new AppointDto[size];
        }
    };
}
