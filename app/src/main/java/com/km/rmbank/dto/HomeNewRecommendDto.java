package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by kamangkeji on 17/4/12.
 */

public class HomeNewRecommendDto implements Parcelable {

    /**
     * indexActivityImage : http://47.93.184.121/img/guest/201705/24dc9608309341e29d6209bc18f2b70d.jpeg
     * productType : 26#27#28#29
     * productTypeName : 饮品
     * productTypeParentId : 12
     * recommendId : 3a299211a1974024a0d8867e6e1c9b32
     * recommendName : 饮品
     * status : 0
     * subtitle : 销量第一
     * type : 1
     * typeList : [{"createDate":1493880685000,"describes":"1","id":"26","parentId":"12","productTypeImage":"http://47.93.184.121/img/user/portrait/201705/0b4198ad45284403b838e98e71a1695a.jpg","productTypeName":"logo设计","supply":"0.7"},{"createDate":1493881489000,"describes":"1","id":"27","parentId":"12","productTypeImage":"http://47.93.184.121/img/user/portrait/201705/0b4198ad45284403b838e98e71a1695a.jpg","productTypeName":"平面设计","supply":"0.7"},{"createDate":1493881515000,"describes":"1","id":"28","parentId":"12","productTypeImage":"http://47.93.184.121/img/user/portrait/201705/0b4198ad45284403b838e98e71a1695a.jpg","productTypeName":"网页排版","supply":"0.7"},{"createDate":1495014226000,"describes":"1","id":"29","parentId":"12","productTypeImage":"http://47.93.184.121/img/user/portrait/201705/0b4198ad45284403b838e98e71a1695a.jpg","productTypeName":"手机","supply":"0.7"}]
     */

    private String indexActivityImage;
    private String productType;
    private String productTypeName;
    private String productTypeParentId;
    private String recommendId;
    private String recommendName;
    private String subtitle;
    private String type;
    private List<TypeListBean> typeList;

    public String getIndexActivityImage() {
        return indexActivityImage;
    }

    public void setIndexActivityImage(String indexActivityImage) {
        this.indexActivityImage = indexActivityImage;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public String getProductTypeParentId() {
        return productTypeParentId;
    }

    public void setProductTypeParentId(String productTypeParentId) {
        this.productTypeParentId = productTypeParentId;
    }

    public String getRecommendId() {
        return recommendId;
    }

    public void setRecommendId(String recommendId) {
        this.recommendId = recommendId;
    }

    public String getRecommendName() {
        return recommendName;
    }

    public void setRecommendName(String recommendName) {
        this.recommendName = recommendName;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<TypeListBean> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<TypeListBean> typeList) {
        this.typeList = typeList;
    }

    public static class TypeListBean implements Parcelable {
        /**
         * createDate : 1493880685000
         * describes : 1
         * id : 26
         * parentId : 12
         * productTypeImage : http://47.93.184.121/img/user/portrait/201705/0b4198ad45284403b838e98e71a1695a.jpg
         * productTypeName : logo设计
         * supply : 0.7
         */

        private String describes;
        private String id;
        private String parentId;
        private String productTypeImage;
        private String productTypeName;
        private String supply;

        public String getDescribes() {
            return describes;
        }

        public void setDescribes(String describes) {
            this.describes = describes;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getProductTypeImage() {
            return productTypeImage;
        }

        public void setProductTypeImage(String productTypeImage) {
            this.productTypeImage = productTypeImage;
        }

        public String getProductTypeName() {
            return productTypeName;
        }

        public void setProductTypeName(String productTypeName) {
            this.productTypeName = productTypeName;
        }

        public String getSupply() {
            return supply;
        }

        public void setSupply(String supply) {
            this.supply = supply;
        }

        public TypeListBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.describes);
            dest.writeString(this.id);
            dest.writeString(this.parentId);
            dest.writeString(this.productTypeImage);
            dest.writeString(this.productTypeName);
            dest.writeString(this.supply);
        }

        protected TypeListBean(Parcel in) {
            this.describes = in.readString();
            this.id = in.readString();
            this.parentId = in.readString();
            this.productTypeImage = in.readString();
            this.productTypeName = in.readString();
            this.supply = in.readString();
        }

        public static final Creator<TypeListBean> CREATOR = new Creator<TypeListBean>() {
            @Override
            public TypeListBean createFromParcel(Parcel source) {
                return new TypeListBean(source);
            }

            @Override
            public TypeListBean[] newArray(int size) {
                return new TypeListBean[size];
            }
        };
    }

    public HomeNewRecommendDto() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.indexActivityImage);
        dest.writeString(this.productType);
        dest.writeString(this.productTypeName);
        dest.writeString(this.productTypeParentId);
        dest.writeString(this.recommendId);
        dest.writeString(this.recommendName);
        dest.writeString(this.subtitle);
        dest.writeString(this.type);
        dest.writeTypedList(this.typeList);
    }

    protected HomeNewRecommendDto(Parcel in) {
        this.indexActivityImage = in.readString();
        this.productType = in.readString();
        this.productTypeName = in.readString();
        this.productTypeParentId = in.readString();
        this.recommendId = in.readString();
        this.recommendName = in.readString();
        this.subtitle = in.readString();
        this.type = in.readString();
        this.typeList = in.createTypedArrayList(TypeListBean.CREATOR);
    }

    public static final Creator<HomeNewRecommendDto> CREATOR = new Creator<HomeNewRecommendDto>() {
        @Override
        public HomeNewRecommendDto createFromParcel(Parcel source) {
            return new HomeNewRecommendDto(source);
        }

        @Override
        public HomeNewRecommendDto[] newArray(int size) {
            return new HomeNewRecommendDto[size];
        }
    };
}
