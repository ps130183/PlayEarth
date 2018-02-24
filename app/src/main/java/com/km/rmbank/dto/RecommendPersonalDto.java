package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PengSong on 18/1/28.
 */

public class RecommendPersonalDto implements Parcelable {

    /**
     * backImage : http://192.168.10.131:8080/wzdq/Aiyg/aiygImage/2018/01/e58b23eb11d14b0cb3a9914fdb44d0a8.png
     * id : 2
     * joinDate : 1517116685000
     * likeCount : 0
     * likeStatus : 0
     * personContent : 第三方sad#开门#
     * personImage : http://192.168.10.131:8080/wzdq/Aiyg/aiygImage/2018/01/f976107f985d4456b17fb03ef748bfcb.jpg#http://192.168.10.131:8080/wzdq/Aiyg/aiygImage/2018/01/8d81d7bad06f4cb0a26469279e17276f.jpg#
     * personIntroduce : az
     * personName : 松根
     * status : 1
     */

    private String backImage;
    private String id;
    private long joinDate;
    private String likeCount;
    private String likeStatus;
    private String personIntroduce;
    private String personName;
    private String status;
    private String title;
    private List<AtlasListBean> atlasList;
    private String shareUrl;
    /**
     *
     * day : 28
     * personContent : 第三方sad#开门#
     * personImage : http://192.168.10.131:8080/wzdq/Aiyg/aiygImage/2018/01/f976107f985d4456b17fb03ef748bfcb.jpg#http://192.168.10.131:8080/wzdq/Aiyg/aiygImage/2018/01/8d81d7bad06f4cb0a26469279e17276f.jpg#
     * yearMonth : Jan.2018
     */

    private String day;
    private String yearMonth;


    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackImage() {
        return backImage;
    }

    public void setBackImage(String backImage) {
        this.backImage = backImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(long joinDate) {
        this.joinDate = joinDate;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getLikeStatus() {
        return likeStatus;
    }

    public void setLikeStatus(String likeStatus) {
        this.likeStatus = likeStatus;
    }

    public String getPersonIntroduce() {
        return personIntroduce;
    }

    public void setPersonIntroduce(String personIntroduce) {
        this.personIntroduce = personIntroduce;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RecommendPersonalDto{" +
                "backImage='" + backImage + '\'' +
                ", id='" + id + '\'' +
                ", joinDate=" + joinDate +
                ", likeCount='" + likeCount + '\'' +
                ", likeStatus='" + likeStatus + '\'' +
                ", personIntroduce='" + personIntroduce + '\'' +
                ", personName='" + personName + '\'' +
                ", status='" + status + '\'' +
                ", atlasList=" + atlasList +
                '}';
    }

    public List<AtlasListBean> getAtlasList() {
        return atlasList;
    }

    public void setAtlasList(List<AtlasListBean> atlasList) {
        this.atlasList = atlasList;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public static class AtlasListBean {
        /**
         * content : 第三方sad
         * image : http://192.168.10.131:8080/wzdq/Aiyg/aiygImage/2018/01/f976107f985d4456b17fb03ef748bfcb.jpg
         */

        private String content;
        private String image;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.backImage);
        dest.writeString(this.id);
        dest.writeLong(this.joinDate);
        dest.writeString(this.likeCount);
        dest.writeString(this.likeStatus);
        dest.writeString(this.personIntroduce);
        dest.writeString(this.personName);
        dest.writeString(this.status);
        dest.writeString(this.title);
        dest.writeList(this.atlasList);
        dest.writeString(this.shareUrl);
        dest.writeString(this.day);
        dest.writeString(this.yearMonth);
    }

    public RecommendPersonalDto() {
    }

    protected RecommendPersonalDto(Parcel in) {
        this.backImage = in.readString();
        this.id = in.readString();
        this.joinDate = in.readLong();
        this.likeCount = in.readString();
        this.likeStatus = in.readString();
        this.personIntroduce = in.readString();
        this.personName = in.readString();
        this.status = in.readString();
        this.title = in.readString();
        this.atlasList = new ArrayList<AtlasListBean>();
        in.readList(this.atlasList, AtlasListBean.class.getClassLoader());
        this.shareUrl = in.readString();
        this.day = in.readString();
        this.yearMonth = in.readString();
    }

    public static final Parcelable.Creator<RecommendPersonalDto> CREATOR = new Parcelable.Creator<RecommendPersonalDto>() {
        @Override
        public RecommendPersonalDto createFromParcel(Parcel source) {
            return new RecommendPersonalDto(source);
        }

        @Override
        public RecommendPersonalDto[] newArray(int size) {
            return new RecommendPersonalDto[size];
        }
    };
}
