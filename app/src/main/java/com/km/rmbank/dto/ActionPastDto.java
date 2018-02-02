package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.km.rmbank.base.BaseEntity;

import java.util.List;

/**
 * Created by kamangkeji on 17/7/18.
 */

public class ActionPastDto extends BaseEntity implements Parcelable {


    /**
     * avatarUrl : http://47.93.184.121:8080/wzdq/Aiyg/aiygImage/2018/01/6105f8529e634da5a71a0fc9b6c64195.png
     * bannerType : 0
     * clubId : manager
     * createDate : 1516252843000
     * detailList : [{"createDate":1516267770000,"dynamicId":"209","dynamicImage":"http://47.93.184.121:8080/wzdq/Aiyg/aiygImage/2018/01/4bc1e4ee12ce4273b82e7c79a61e7a6b.jpg","dynamicImageContent":"古人云： 《待到春风二三月，石炉敲火试新茶》《卧云歌德，对雨著》   。","id":"1849"},{"createDate":1516267770000,"dynamicId":"209","dynamicImage":"http://47.93.184.121:8080/wzdq/Aiyg/aiygImage/2018/01/14ba29ae3c3047a3b2e4763f9816754d.jpg","dynamicImageContent":"","id":"1850"},{"createDate":1516267770000,"dynamicId":"209","dynamicImage":"http://47.93.184.121:8080/wzdq/Aiyg/aiygImage/2018/01/4b73678eb9c14316a1fc78dee5f73513.jpg","dynamicImageContent":"","id":"1851"},{"createDate":1516267770000,"dynamicId":"209","dynamicImage":"http://47.93.184.121:8080/wzdq/Aiyg/aiygImage/2018/01/311e48fd9c9b462d85f4aa34db4b391c.jpg","dynamicImageContent":"茶话会由玩转地球任晓华任总和路演俱乐部创始人李英凡女士以及各商业精英强强联合。","id":"1852"},{"createDate":1516267770000,"dynamicId":"209","dynamicImage":"http://47.93.184.121:8080/wzdq/Aiyg/aiygImage/2018/01/86d46741ee834b3f9a25e25e2897adbf.jpg","dynamicImageContent":"茶会期间各位成员热烈讨论，交流心得，处处洋溢着祥和的氛围。","id":"1853"},{"createDate":1516267770000,"dynamicId":"209","dynamicImage":"http://47.93.184.121:8080/wzdq/Aiyg/aiygImage/2018/01/3fc3f57ab1d4474f872c90394cfbe66c.jpg","dynamicImageContent":"会间国际心灵疗愈师、向日葵爱心基金会创始人、国家二级心理咨询师、生命密码分析师和训练师、灵性智慧演说导师、卢红杰女士成功加入玩转地球成为合伙人，感谢您选择玩转地球商旅学院，让我们一起携手用一辈子的时间活出三辈子的精彩。","id":"1854"}]
     * id : 209
     * status : 1
     * title : 玩转年地球路演俱乐部第一期下午茶
     * videoName :
     * viewCount : 28
     * webDynamicUrl : http://47.93.184.121:8081/app/html/dynamicDetail?id=209
     */

    private String avatarUrl;
    private int bannerType;
    private String clubId;
    private long createDate;
    private String id;
    private String status;
    private String title;
    private String videoName;
    private int viewCount;
    private String webDynamicUrl;
    private List<DetailListBean> detailList;
    /**
     * detailList : []
     * videoUrl : http://oyq5v8oq4.bkt.clouddn.com/lpoFwJ2kifbZGTF3RRdoLEmJwTUR
     */

    private String videoUrl;
    /**
     * clubLogo : http://47.93.184.121:8081/img/shop/201708/3239fb74ee9a43bcb605106ddddacf8c.png
     * clubName : 玩转地球－演讲俱乐部
     */

    private String clubLogo;
    private String clubName;

    @Override
    public boolean isEmpty() {
        return false;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getBannerType() {
        return bannerType;
    }

    public void setBannerType(int bannerType) {
        this.bannerType = bannerType;
    }

    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
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

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public String getWebDynamicUrl() {
        return webDynamicUrl;
    }

    public void setWebDynamicUrl(String webDynamicUrl) {
        this.webDynamicUrl = webDynamicUrl;
    }

    public List<DetailListBean> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<DetailListBean> detailList) {
        this.detailList = detailList;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
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

    public static class DetailListBean implements Parcelable {
        /**
         * createDate : 1516267770000
         * dynamicId : 209
         * dynamicImage : http://47.93.184.121:8080/wzdq/Aiyg/aiygImage/2018/01/4bc1e4ee12ce4273b82e7c79a61e7a6b.jpg
         * dynamicImageContent : 古人云： 《待到春风二三月，石炉敲火试新茶》《卧云歌德，对雨著》   。
         * id : 1849
         */

        private long createDate;
        private String dynamicId;
        private String dynamicImage;
        private String dynamicImageContent;
        private String id;

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }

        public String getDynamicId() {
            return dynamicId;
        }

        public void setDynamicId(String dynamicId) {
            this.dynamicId = dynamicId;
        }

        public String getDynamicImage() {
            return dynamicImage;
        }

        public void setDynamicImage(String dynamicImage) {
            this.dynamicImage = dynamicImage;
        }

        public String getDynamicImageContent() {
            return dynamicImageContent;
        }

        public void setDynamicImageContent(String dynamicImageContent) {
            this.dynamicImageContent = dynamicImageContent;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(this.createDate);
            dest.writeString(this.dynamicId);
            dest.writeString(this.dynamicImage);
            dest.writeString(this.dynamicImageContent);
            dest.writeString(this.id);
        }

        public DetailListBean() {
        }

        protected DetailListBean(Parcel in) {
            this.createDate = in.readLong();
            this.dynamicId = in.readString();
            this.dynamicImage = in.readString();
            this.dynamicImageContent = in.readString();
            this.id = in.readString();
        }

        public static final Creator<DetailListBean> CREATOR = new Creator<DetailListBean>() {
            @Override
            public DetailListBean createFromParcel(Parcel source) {
                return new DetailListBean(source);
            }

            @Override
            public DetailListBean[] newArray(int size) {
                return new DetailListBean[size];
            }
        };
    }

    public ActionPastDto() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.avatarUrl);
        dest.writeInt(this.bannerType);
        dest.writeString(this.clubId);
        dest.writeLong(this.createDate);
        dest.writeString(this.id);
        dest.writeString(this.status);
        dest.writeString(this.title);
        dest.writeString(this.videoName);
        dest.writeInt(this.viewCount);
        dest.writeString(this.webDynamicUrl);
        dest.writeTypedList(this.detailList);
        dest.writeString(this.videoUrl);
        dest.writeString(this.clubLogo);
        dest.writeString(this.clubName);
    }

    protected ActionPastDto(Parcel in) {
        this.avatarUrl = in.readString();
        this.bannerType = in.readInt();
        this.clubId = in.readString();
        this.createDate = in.readLong();
        this.id = in.readString();
        this.status = in.readString();
        this.title = in.readString();
        this.videoName = in.readString();
        this.viewCount = in.readInt();
        this.webDynamicUrl = in.readString();
        this.detailList = in.createTypedArrayList(DetailListBean.CREATOR);
        this.videoUrl = in.readString();
        this.clubLogo = in.readString();
        this.clubName = in.readString();
    }

    public static final Creator<ActionPastDto> CREATOR = new Creator<ActionPastDto>() {
        @Override
        public ActionPastDto createFromParcel(Parcel source) {
            return new ActionPastDto(source);
        }

        @Override
        public ActionPastDto[] newArray(int size) {
            return new ActionPastDto[size];
        }
    };
}
