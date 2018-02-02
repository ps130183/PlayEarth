package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;


import com.km.rmbank.base.BaseEntity;

import java.util.List;

/**
 * Created by kamangkeji on 17/5/24.
 */

public class HomeGoodsTypeDto extends BaseEntity implements Parcelable {

    /**
     * createDate : 1493005887000
     * describe : 11
     * id : 12
     * parentId : 0
     * productTypeImage : http://47.93.184.121/img/user/portrait/201705/0b4198ad45284403b838e98e71a1695a.jpg
     * productTypeName : 饮品
     * supply : 0.7
     */

    private String createDate;
    private String describe;
    private String id;
    private String parentId;
    private String productTypeImage;
    private String productTypeName;
    private String supply;

    private boolean isChecked;

    private int backgroundRes;
    private List<HomeGoodsTypeDto> typeList;



    public HomeGoodsTypeDto(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    /**
     * createDate : 1493005887000
     * typeList : [{"id":"12","productTypeName":"全部"},{"createDate":1493880685000,"describe":"1","id":"26","parentId":"12","productTypeImage":"http://47.93.184.121/img/user/portrait/201705/0b4198ad45284403b838e98e71a1695a.jpg","productTypeName":"logo设计","supply":"0.7"},{"createDate":1493881489000,"describe":"1","id":"27","parentId":"12","productTypeImage":"http://47.93.184.121/img/user/portrait/201705/0b4198ad45284403b838e98e71a1695a.jpg","productTypeName":"平面设计","supply":"0.7"},{"createDate":1493881515000,"describe":"1","id":"28","parentId":"12","productTypeImage":"http://47.93.184.121/img/user/portrait/201705/0b4198ad45284403b838e98e71a1695a.jpg","productTypeName":"网页排版","supply":"0.7"},{"createDate":1495014226000,"describe":"1","id":"29","parentId":"12","productTypeImage":"http://47.93.184.121/img/user/portrait/201705/0b4198ad45284403b838e98e71a1695a.jpg","productTypeName":"手机","supply":"0.7"},{"createDate":1495014249000,"describe":"12","id":"30","parentId":"12","productTypeImage":"http://47.93.184.121/img/user/portrait/201705/0b4198ad45284403b838e98e71a1695a.jpg","productTypeName":"数码","supply":"0.8"},{"createDate":1495014279000,"describe":"2121","id":"31","parentId":"12","productTypeImage":"http://47.93.184.121/img/user/portrait/201705/0b4198ad45284403b838e98e71a1695a.jpg","productTypeName":"化妆品","supply":"0.5"},{"createDate":1495177356000,"describe":"1","id":"33","parentId":"12","productTypeImage":"http://47.93.184.121/img/guest/201705/737a8ff3b4fd41b1b87f66d73706f1b2.jpg","productTypeName":"绿茶","supply":"0.66"}]
     */




    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
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

    public List<HomeGoodsTypeDto> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<HomeGoodsTypeDto> typeList) {
        this.typeList = typeList;
    }

    public HomeGoodsTypeDto() {
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getBackgroundRes() {
        return backgroundRes;
    }

    public void setBackgroundRes(int backgroundRes) {
        this.backgroundRes = backgroundRes;
    }

    @Override
    public String toString() {
        return "HomeGoodsTypeDto{" +
                "createDate='" + createDate + '\'' +
                ", describe='" + describe + '\'' +
                ", id='" + id + '\'' +
                ", parentId='" + parentId + '\'' +
                ", productTypeImage='" + productTypeImage + '\'' +
                ", productTypeName='" + productTypeName + '\'' +
                ", supply='" + supply + '\'' +
                ", isChecked=" + isChecked +
                ", typeList=" + typeList +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.createDate);
        dest.writeString(this.describe);
        dest.writeString(this.id);
        dest.writeString(this.parentId);
        dest.writeString(this.productTypeImage);
        dest.writeString(this.productTypeName);
        dest.writeString(this.supply);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
        dest.writeInt(this.backgroundRes);
        dest.writeTypedList(this.typeList);
    }

    protected HomeGoodsTypeDto(Parcel in) {
        this.createDate = in.readString();
        this.describe = in.readString();
        this.id = in.readString();
        this.parentId = in.readString();
        this.productTypeImage = in.readString();
        this.productTypeName = in.readString();
        this.supply = in.readString();
        this.isChecked = in.readByte() != 0;
        this.backgroundRes = in.readInt();
        this.typeList = in.createTypedArrayList(HomeGoodsTypeDto.CREATOR);
    }

    public static final Creator<HomeGoodsTypeDto> CREATOR = new Creator<HomeGoodsTypeDto>() {
        @Override
        public HomeGoodsTypeDto createFromParcel(Parcel source) {
            return new HomeGoodsTypeDto(source);
        }

        @Override
        public HomeGoodsTypeDto[] newArray(int size) {
            return new HomeGoodsTypeDto[size];
        }
    };

    @Override
    public boolean isEmpty() {
        if (TextUtils.isEmpty(productTypeName) && TextUtils.isEmpty(id)){
            return true;
        }
        return false;
    }
}
