package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kamangkeji on 17/9/13.
 */

public class ActiveGoodsOrderListDto implements Parcelable {
    /**
     * productNo : AV20170908154548759
     * orderNo : PO20170913104424466
     * thumbnailUrl : http://47.93.184.121/img/shop/201706/b03dd475c07f40e49a46cdb6d69ec79c.png
     * name : javaceshi
     * subtitle : java
     * activeValue : 2
     */

    private String productNo;
    private String orderNo;
    private String thumbnailUrl;
    private String name;
    private String subtitle;
    private int activeValue;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public int getActiveValue() {
        return activeValue;
    }

    public void setActiveValue(int activeValue) {
        this.activeValue = activeValue;
    }

    public ActiveGoodsOrderListDto() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.productNo);
        dest.writeString(this.orderNo);
        dest.writeString(this.thumbnailUrl);
        dest.writeString(this.name);
        dest.writeString(this.subtitle);
        dest.writeInt(this.activeValue);
        dest.writeInt(this.status);
    }

    protected ActiveGoodsOrderListDto(Parcel in) {
        this.productNo = in.readString();
        this.orderNo = in.readString();
        this.thumbnailUrl = in.readString();
        this.name = in.readString();
        this.subtitle = in.readString();
        this.activeValue = in.readInt();
        this.status = in.readInt();
    }

    public static final Creator<ActiveGoodsOrderListDto> CREATOR = new Creator<ActiveGoodsOrderListDto>() {
        @Override
        public ActiveGoodsOrderListDto createFromParcel(Parcel source) {
            return new ActiveGoodsOrderListDto(source);
        }

        @Override
        public ActiveGoodsOrderListDto[] newArray(int size) {
            return new ActiveGoodsOrderListDto[size];
        }
    };
}
