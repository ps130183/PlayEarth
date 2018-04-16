package com.km.rmbank.dto;

import java.util.List;

/**
 * Created by kamangkeji on 17/4/12.
 */

public class HomeRecommendDto {

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

    public static class DetailListBean {
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
        /**
         * applyCount : 1
         * startDate : 1521264600000
         */

        private String applyCount;
        private long startDate;


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
    }
}
