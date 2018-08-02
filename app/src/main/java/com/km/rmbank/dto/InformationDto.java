package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.km.rmbank.R;
import com.ps.mrcyclerview.delegate.ItemDelegate;

/**
 * Created by kamangkeji on 17/4/20.
 */

public class InformationDto implements Parcelable,ItemDelegate {
    private String avatarUrl;
    private String id;
    private String title;
    private String subtitle;
    private String viewCount;
    /**
     * clubLogo : http://47.93.184.121/img/guest/201707/950332fa76904fa091ce46dd38898fe7.png
     * clubName : 瑜伽俱乐部
     * viewCount : 2
     */

    private String clubLogo;
    private String clubName;
    /**
     * videoName : 带飞
     * videoUrl : http://oyq5v8oq4.bkt.clouddn.com/Fk5NKNhSfxEeeKmJS9kzAu76_hEi
     * viewCount : 0
     */

    private String videoName;
    private String videoUrl;


    @Override
    public String toString() {
        return "InformationDto{" +
                "avatarUrl='" + avatarUrl + '\'' +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", viewCount='" + viewCount + '\'' +
                ", clubLogo='" + clubLogo + '\'' +
                ", clubName='" + clubName + '\'' +
                ", videoName='" + videoName + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                '}';
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public InformationDto() {
    }

    public String getClubLogo() {
        return clubLogo;
    }

    public void setClubLogo(String clubLogo) {
        this.clubLogo = clubLogo;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.avatarUrl);
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.subtitle);
        dest.writeString(this.viewCount);
        dest.writeString(this.clubLogo);
        dest.writeString(this.clubName);
        dest.writeString(this.videoName);
        dest.writeString(this.videoUrl);
    }

    protected InformationDto(Parcel in) {
        this.avatarUrl = in.readString();
        this.id = in.readString();
        this.title = in.readString();
        this.subtitle = in.readString();
        this.viewCount = in.readString();
        this.clubLogo = in.readString();
        this.clubName = in.readString();
        this.videoName = in.readString();
        this.videoUrl = in.readString();
    }

    public static final Creator<InformationDto> CREATOR = new Creator<InformationDto>() {
        @Override
        public InformationDto createFromParcel(Parcel source) {
            return new InformationDto(source);
        }

        @Override
        public InformationDto[] newArray(int size) {
            return new InformationDto[size];
        }
    };

    @Override
    public int getItemViewRes() {
        return R.layout.item_information;
    }
}
