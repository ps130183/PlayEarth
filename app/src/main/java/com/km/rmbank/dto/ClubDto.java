package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.km.rmbank.base.BaseEntity;

import java.util.List;

/**
 * Created by kamangkeji on 17/7/1.
 */

public class ClubDto extends BaseEntity implements Parcelable {

    /**
     * clubLogo : http://47.93.184.121/img/guest/201707/chanwu.jpg
     * clubName : 禅舞俱乐部
     */

    private String id;
    private String clubLogo;
    private String clubName;
    private String content;
    private String backgroundImg;
    private int keepStatus;
    private List<ClubDetailBean> clubDetailList;
    /**
     * activityCount : 0
     * activityPersonCount : 0
     * clubDetailList : [{"clubContent":"2313416465","clubId":"46","clubImage":"http://192.168.10.131:8080/wzdq/Aiyg/aiygImage/2018/01/2652d7e5600e4ae8addb71dac576e4fd.png","createDate":1516341915000,"id":"393"},{"clubContent":"快啦","clubId":"46","clubImage":"http://192.168.10.131:8080/wzdq/Aiyg/aiygImage/2018/01/2521196c5cbc4c06ac5ffee3eedc2510.jpg","createDate":1516341915000,"id":"394"}]
     * clubUrl : http://192.168.10.131:8083/Aiyg/club/html/getClub?id=46
     * createDate : 1516264718000
     * isRecommend : 1
     * keepCount : 1
     * keepStatus : 1
     * updateDate : 1516682994000
     */

    private String activityCount;
    private String activityPersonCount;
    private String clubUrl;
    private long createDate;
    private String isRecommend;
    private int keepCount;
    private long updateDate;

    private String clubType;
    /**
     * 基地 、 会所 数据
     * address : 北京市东城区时光记忆主题餐吧
     * clubDetailList : [{"clubContent":"美好的记忆都留在这里，儿时的记忆在这里唤起，曾经的朋友相约在一起，热泪相拥挂着笑脸，让时间定格在时光记忆餐厅。","clubId":"58","clubImage":"http://wanzhuandiqiu.com:8080/wzdq/Aiyg/aiygImage/2018/02/cec1bc3d44474aaeb4c00e7f14860522.jpg","createDate":1519368486000,"id":"676"},{"clubContent":"","clubId":"58","clubImage":"http://wanzhuandiqiu.com:8080/wzdq/Aiyg/aiygImage/2018/02/e0278a552ab24d39bfd285c4519401ec.jpg","createDate":1519368486000,"id":"677"},{"clubContent":"","clubId":"58","clubImage":"http://wanzhuandiqiu.com:8080/wzdq/Aiyg/aiygImage/2018/02/282693ce8341454da7eee3d4d9eb6e25.jpg","createDate":1519368486000,"id":"678"},{"clubContent":"","clubId":"58","clubImage":"http://wanzhuandiqiu.com:8080/wzdq/Aiyg/aiygImage/2018/02/26bb865c5b534e3d8baab01897f70f6c.jpg","createDate":1519368486000,"id":"679"},{"clubContent":"","clubId":"58","clubImage":"http://wanzhuandiqiu.com:8080/wzdq/Aiyg/aiygImage/2018/02/d59ef609bb2a426fa1dadd8a07050e83.jpg","createDate":1519368486000,"id":"680"},{"clubContent":"","clubId":"58","clubImage":"http://wanzhuandiqiu.com:8080/wzdq/Aiyg/aiygImage/2018/02/7fb6d97f727c455cbcb2da5d5c9257c0.jpg","createDate":1519368486000,"id":"681"},{"clubContent":"","clubId":"58","clubImage":"http://wanzhuandiqiu.com:8080/wzdq/Aiyg/aiygImage/2018/02/e8b29c633e444b6d9be10ae040c6634a.jpg","createDate":1519368486000,"id":"682"}]
     * commodityExplainUrl : http://wanzhuandiqiu.com/commodityExplain
     * commodityExplainUrl2 : http://wanzhuandiqiu.com/commodityExplain2
     * keepCount : 0
     * keepStatus : 0
     * latitude : 39.885937
     * longitude : 116.441957
     * userId :
     */


    private String address;
    private String commodityExplainUrl;
    private String commodityExplainUrl2;
    private String latitude;
    private String longitude;
    private String userId;


    @Override
    public String toString() {
        return "ClubDto{" +
                "id='" + id + '\'' +
                ", clubLogo='" + clubLogo + '\'' +
                ", clubName='" + clubName + '\'' +
                ", content='" + content + '\'' +
                ", backgroundImg='" + backgroundImg + '\'' +
                ", keepStatus=" + keepStatus +
                ", clubDetailList=" + clubDetailList +
                ", activityCount='" + activityCount + '\'' +
                ", activityPersonCount='" + activityPersonCount + '\'' +
                ", clubUrl='" + clubUrl + '\'' +
                ", createDate=" + createDate +
                ", isRecommend='" + isRecommend + '\'' +
                ", keepCount=" + keepCount +
                ", updateDate=" + updateDate +
                ", clubType='" + clubType + '\'' +
                ", address='" + address + '\'' +
                ", commodityExplainUrl='" + commodityExplainUrl + '\'' +
                ", commodityExplainUrl2='" + commodityExplainUrl2 + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }


    public String getActivityCount() {
        return activityCount;
    }

    public void setActivityCount(String activityCount) {
        this.activityCount = activityCount;
    }

    public String getActivityPersonCount() {
        return activityPersonCount;
    }

    public void setActivityPersonCount(String activityPersonCount) {
        this.activityPersonCount = activityPersonCount;
    }


    public String getClubType() {
        return clubType;
    }

    public void setClubType(String clubType) {
        this.clubType = clubType;
    }

    public String getClubUrl() {
        return clubUrl;
    }

    public void setClubUrl(String clubUrl) {
        this.clubUrl = clubUrl;
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

    public int getKeepCount() {
        return keepCount;
    }

    public void setKeepCount(int keepCount) {
        this.keepCount = keepCount;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBackgroundImg() {
        return backgroundImg;
    }

    public void setBackgroundImg(String backgroundImg) {
        this.backgroundImg = backgroundImg;
    }

    public List<ClubDetailBean> getClubDetailList() {
        return clubDetailList;
    }

    public void setClubDetailList(List<ClubDetailBean> clubDetailList) {
        this.clubDetailList = clubDetailList;
    }

    public boolean getKeepStatus() {
        return keepStatus == 0 ? false : true;
    }

    public void setKeepStatus(int keepStatus) {
        this.keepStatus = keepStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCommodityExplainUrl() {
        return commodityExplainUrl;
    }

    public void setCommodityExplainUrl(String commodityExplainUrl) {
        this.commodityExplainUrl = commodityExplainUrl;
    }

    public String getCommodityExplainUrl2() {
        return commodityExplainUrl2;
    }

    public void setCommodityExplainUrl2(String commodityExplainUrl2) {
        this.commodityExplainUrl2 = commodityExplainUrl2;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean isEmpty() {
        if (TextUtils.isEmpty(clubLogo) || TextUtils.isEmpty(clubName) || TextUtils.isEmpty(content)){
            return true;
        }
//        if (clubDetailList != null){
//            boolean isEmpty = false;
//            for (ClubDetailBean bean : clubDetailList){
//                isEmpty = bean.isEmpty();
//                if (isEmpty){
//                    break;
//                }
//            }
//            return isEmpty;
//        }
        return false;
    }

    public static class ClubDetailBean extends BaseEntity implements Parcelable {
        private String clubImage;
        private List<String> clubImageList;
        private String clubContent;

        public String getClubImage() {
            return clubImage;
        }

        public void setClubImage(String clubImage) {
            this.clubImage = clubImage;
        }

        public String getClubContent() {
            return clubContent;
        }

        public void setClubContent(String clubContent) {
            this.clubContent = clubContent;
        }

        public List<String> getClubImageList() {
            return clubImageList;
        }

        public void setClubImageList(List<String> clubImageList) {
            clubImageList = clubImageList;
        }

        @Override
        public String toString() {
            return "ClubDetailBean{" +
                    "clubImage='" + clubImage + '\'' +
                    ", ClubImageList=" + clubImageList +
                    ", clubContent='" + clubContent + '\'' +
                    '}';
        }

        @Override
        public boolean isEmpty() {
            if (TextUtils.isEmpty(clubImage) || TextUtils.isEmpty(clubContent)){
                return true;
            }
            return false;
        }

        public ClubDetailBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.clubImage);
            dest.writeStringList(this.clubImageList);
            dest.writeString(this.clubContent);
        }

        protected ClubDetailBean(Parcel in) {
            this.clubImage = in.readString();
            this.clubImageList = in.createStringArrayList();
            this.clubContent = in.readString();
        }

        public static final Creator<ClubDetailBean> CREATOR = new Creator<ClubDetailBean>() {
            @Override
            public ClubDetailBean createFromParcel(Parcel source) {
                return new ClubDetailBean(source);
            }

            @Override
            public ClubDetailBean[] newArray(int size) {
                return new ClubDetailBean[size];
            }
        };
    }

    public ClubDto() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.clubLogo);
        dest.writeString(this.clubName);
        dest.writeString(this.content);
        dest.writeString(this.backgroundImg);
        dest.writeInt(this.keepStatus);
        dest.writeTypedList(this.clubDetailList);
        dest.writeString(this.activityCount);
        dest.writeString(this.activityPersonCount);
        dest.writeString(this.clubUrl);
        dest.writeLong(this.createDate);
        dest.writeString(this.isRecommend);
        dest.writeInt(this.keepCount);
        dest.writeLong(this.updateDate);
        dest.writeString(this.clubType);
        dest.writeString(this.address);
        dest.writeString(this.commodityExplainUrl);
        dest.writeString(this.commodityExplainUrl2);
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
        dest.writeString(this.userId);
    }

    protected ClubDto(Parcel in) {
        this.id = in.readString();
        this.clubLogo = in.readString();
        this.clubName = in.readString();
        this.content = in.readString();
        this.backgroundImg = in.readString();
        this.keepStatus = in.readInt();
        this.clubDetailList = in.createTypedArrayList(ClubDetailBean.CREATOR);
        this.activityCount = in.readString();
        this.activityPersonCount = in.readString();
        this.clubUrl = in.readString();
        this.createDate = in.readLong();
        this.isRecommend = in.readString();
        this.keepCount = in.readInt();
        this.updateDate = in.readLong();
        this.clubType = in.readString();
        this.address = in.readString();
        this.commodityExplainUrl = in.readString();
        this.commodityExplainUrl2 = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
        this.userId = in.readString();
    }

    public static final Creator<ClubDto> CREATOR = new Creator<ClubDto>() {
        @Override
        public ClubDto createFromParcel(Parcel source) {
            return new ClubDto(source);
        }

        @Override
        public ClubDto[] newArray(int size) {
            return new ClubDto[size];
        }
    };
}
