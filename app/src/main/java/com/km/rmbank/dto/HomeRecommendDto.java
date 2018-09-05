package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.km.rmbank.R;
import com.ps.mrcyclerview.delegate.ItemDelegate;

import java.util.List;

/**
 * Created by kamangkeji on 17/4/12.
 */

public class HomeRecommendDto implements ItemDelegate {

    /**
     * advertImage : http://192.168.10.131:8080/wzdq/Aiyg/aiygImage/2018/03/06da586f052647448a91c1a65e265aff.jpg
     * advertUrl :
     * detailList : [{"content":"","fans":"0","id":"4","keepCount":"0","recommendId":"2","relevanceId":"83","sort":0,"thumbnail":"http://47.93.184.121:8080/wzdq/Aiyg/aiygImage/2018/03/b6bf21cca1d342d197850e52b8dab367.jpg","title":"平谷养老基地","type":"2"},{"content":"","fans":"0","id":"5","keepCount":"0","recommendId":"2","relevanceId":"88","sort":0,"thumbnail":"http://47.93.184.121:8080/wzdq/Aiyg/aiygImage/2018/03/07f71e9aedbd408db692bb362101596d.jpg","title":"朝阳禅寺基地","type":"2"},{"content":"以奉献、友爱、互助、进步为宗旨，以帮助他人、完善自己、服务社会、传播文明为工作目标。","fans":"62","id":"6","keepCount":"3","recommendId":"2","relevanceId":"46","sort":0,"thumbnail":"http://47.93.184.121:8080/wzdq/Aiyg/aiygImage/2018/01/a2ac72845afb4e2bb85c14d0ccbd61f7.png","title":"巾帼名媛俱乐部","type":"1"}]
     * id : 2
     * levelName : 推荐元宵
     * sort : 10
     * status : 1
     * type : 2
     */

    private String advertImage;
    private String advertUrl;
    private String urlType;
    private String id;
    private String levelName;
    private int sort;
    private String status;
    private String type;
    private List<DetailListBean> detailList;

    public String getAdvertImage() {
        return advertImage;
    }

    public void setAdvertImage(String advertImage) {
        this.advertImage = advertImage;
    }

    public String getAdvertUrl() {
        return advertUrl;
    }

    public void setAdvertUrl(String advertUrl) {
        this.advertUrl = advertUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<DetailListBean> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<DetailListBean> detailList) {
        this.detailList = detailList;
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }

    @Override
    public int getItemViewRes() {
        return R.layout.item_home_recommend;
    }

    public static class DetailListBean implements ItemDelegate, Parcelable {
        /**
         * content :
         * fans : 0
         * id : 4
         * keepCount : 0
         * recommendId : 2
         * relevanceId : 83
         * sort : 0
         * thumbnail : http://47.93.184.121:8080/wzdq/Aiyg/aiygImage/2018/03/b6bf21cca1d342d197850e52b8dab367.jpg
         * title : 平谷养老基地
         * type : 2
         */

        private String activityId;
        private String clubId;
        private String content;
        private String fans;
        private String id;
        private String keepCount;
        private String recommendId;
        private String relevanceId;
        private int sort;
        private String thumbnail;
        private String title;
        private String type;
        private String classifyName;
        /**
         * applyCount : 1
         * startDate : 1521264600000
         */

        private String applyCount;
        private long startDate;

        private int layoutRes;
        private String isDynamic; //0是活动 1是资讯


        @Override
        public String toString() {
            return "DetailListBean{" +
                    "activityId='" + activityId + '\'' +
                    ", clubId='" + clubId + '\'' +
                    ", content='" + content + '\'' +
                    ", fans='" + fans + '\'' +
                    ", id='" + id + '\'' +
                    ", keepCount='" + keepCount + '\'' +
                    ", recommendId='" + recommendId + '\'' +
                    ", relevanceId='" + relevanceId + '\'' +
                    ", sort=" + sort +
                    ", thumbnail='" + thumbnail + '\'' +
                    ", title='" + title + '\'' +
                    ", type='" + type + '\'' +
                    ", classifyName='" + classifyName + '\'' +
                    ", applyCount='" + applyCount + '\'' +
                    ", startDate=" + startDate +
                    ", layoutRes=" + layoutRes +
                    ", isDynamic='" + isDynamic + '\'' +
                    '}';
        }

        public String getIsDynamic() {
            return isDynamic;
        }

        public void setIsDynamic(String isDynamic) {
            this.isDynamic = isDynamic;
        }

        public String getClassifyName() {
            return classifyName;
        }

        public void setClassifyName(String classifyName) {
            this.classifyName = classifyName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getFans() {
            return fans;
        }

        public void setFans(String fans) {
            this.fans = fans;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getKeepCount() {
            return keepCount;
        }

        public void setKeepCount(String keepCount) {
            this.keepCount = keepCount;
        }

        public String getRecommendId() {
            return recommendId;
        }

        public void setRecommendId(String recommendId) {
            this.recommendId = recommendId;
        }

        public String getRelevanceId() {
            return relevanceId;
        }

        public void setRelevanceId(String relevanceId) {
            this.relevanceId = relevanceId;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
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

        public String getApplyCount() {
            return applyCount;
        }

        public void setApplyCount(String applyCount) {
            this.applyCount = applyCount;
        }

        public long getStartDate() {
            return startDate;
        }

        public void setStartDate(long startDate) {
            this.startDate = startDate;
        }

        public void setLayoutRes(int layoutRes) {
            this.layoutRes = layoutRes;
        }

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public String getClubId() {
            return clubId;
        }

        public void setClubId(String clubId) {
            this.clubId = clubId;
        }

        @Override
        public int getItemViewRes() {
            return layoutRes;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.activityId);
            dest.writeString(this.clubId);
            dest.writeString(this.content);
            dest.writeString(this.fans);
            dest.writeString(this.id);
            dest.writeString(this.keepCount);
            dest.writeString(this.recommendId);
            dest.writeString(this.relevanceId);
            dest.writeInt(this.sort);
            dest.writeString(this.thumbnail);
            dest.writeString(this.title);
            dest.writeString(this.type);
            dest.writeString(this.classifyName);
            dest.writeString(this.applyCount);
            dest.writeLong(this.startDate);
            dest.writeInt(this.layoutRes);
            dest.writeString(this.isDynamic);
        }

        public DetailListBean() {
        }

        protected DetailListBean(Parcel in) {
            this.activityId = in.readString();
            this.clubId = in.readString();
            this.content = in.readString();
            this.fans = in.readString();
            this.id = in.readString();
            this.keepCount = in.readString();
            this.recommendId = in.readString();
            this.relevanceId = in.readString();
            this.sort = in.readInt();
            this.thumbnail = in.readString();
            this.title = in.readString();
            this.type = in.readString();
            this.classifyName = in.readString();
            this.applyCount = in.readString();
            this.startDate = in.readLong();
            this.layoutRes = in.readInt();
            this.isDynamic = in.readString();
        }

        public static final Parcelable.Creator<DetailListBean> CREATOR = new Parcelable.Creator<DetailListBean>() {
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

    @Override
    public String toString() {
        return "HomeRecommendDto{" +
                "advertImage='" + advertImage + '\'' +
                ", advertUrl='" + advertUrl + '\'' +
                ", urlType='" + urlType + '\'' +
                ", id='" + id + '\'' +
                ", levelName='" + levelName + '\'' +
                ", sort=" + sort +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", detailList=" + detailList +
                '}';
    }
}
