package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by kamangkeji on 17/9/6.
 */

public class ActiveGoodsDto implements Parcelable {

    /**
     * activeValue : 2
     * alreadySoldCount : 0
     * bannerType : 0
     * freightMaxCount : 0
     * name : javaceshi
     * productNo : AV20170908154548759
     * sort : 0
     * stockCount : 0
     * subtitle : java
     * thumbnailUrl : http://47.93.184.121/img/shop/201706/b03dd475c07f40e49a46cdb6d69ec79c.png
     */

    private int activeValue;
    private int alreadySoldCount;
    private int bannerType;
    private int freightMaxCount;
    private String name;
    private String productNo;
    private int sort;
    private int stockCount;
    private String subtitle;
    private String thumbnailUrl;
    /**
     * createDate : 1504856749000
     * freightInEveryAdd : 0
     * freightInMaxCount : 0
     * id : 40
     * productBannerList : ["http://47.93.184.121/img/shop/201706/b03dd475c07f40e49a46cdb6d69ec79c.png","http://47.93.184.121/img/shop/201706/655144c0e71048df8a1084c9ca37f6f9.png","http://47.93.184.121/img/shop/201706/ebf472660065413a8fd7ca69deb95e68.png"]
     * productBannerUrl : http://47.93.184.121/img/shop/201706/b03dd475c07f40e49a46cdb6d69ec79c.png#http://47.93.184.121/img/shop/201706/655144c0e71048df8a1084c9ca37f6f9.png#http://47.93.184.121/img/shop/201706/ebf472660065413a8fd7ca69deb95e68.png
     * productDetail : http://47.93.184.121/img/shop/201706/b03dd475c07f40e49a46cdb6d69ec79c.png#http://47.93.184.121/img/shop/201706/655144c0e71048df8a1084c9ca37f6f9.png#http://47.93.184.121/img/shop/201706/ebf472660065413a8fd7ca69deb95e68.png
     * productDetailList : ["http://47.93.184.121/img/shop/201706/b03dd475c07f40e49a46cdb6d69ec79c.png","http://47.93.184.121/img/shop/201706/655144c0e71048df8a1084c9ca37f6f9.png","http://47.93.184.121/img/shop/201706/ebf472660065413a8fd7ca69deb95e68.png"]
     * updateDate : 1504857389000
     */

    private long createDate;
    private int freightInEveryAdd;
    private int freightInMaxCount;
    private String id;
    private String productBannerUrl;
    private String productDetail;
    private long updateDate;
    private List<String> productBannerList;
    private List<String> productDetailList;

    private ReceiverAddressDto receiverAddressDto;

    public int getActiveValue() {
        return activeValue;
    }

    public void setActiveValue(int activeValue) {
        this.activeValue = activeValue;
    }

    public int getAlreadySoldCount() {
        return alreadySoldCount;
    }

    public void setAlreadySoldCount(int alreadySoldCount) {
        this.alreadySoldCount = alreadySoldCount;
    }

    public int getBannerType() {
        return bannerType;
    }

    public void setBannerType(int bannerType) {
        this.bannerType = bannerType;
    }

    public int getFreightMaxCount() {
        return freightMaxCount;
    }

    public void setFreightMaxCount(int freightMaxCount) {
        this.freightMaxCount = freightMaxCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getStockCount() {
        return stockCount;
    }

    public void setStockCount(int stockCount) {
        this.stockCount = stockCount;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public int getFreightInEveryAdd() {
        return freightInEveryAdd;
    }

    public void setFreightInEveryAdd(int freightInEveryAdd) {
        this.freightInEveryAdd = freightInEveryAdd;
    }

    public int getFreightInMaxCount() {
        return freightInMaxCount;
    }

    public void setFreightInMaxCount(int freightInMaxCount) {
        this.freightInMaxCount = freightInMaxCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductBannerUrl() {
        return productBannerUrl;
    }

    public void setProductBannerUrl(String productBannerUrl) {
        this.productBannerUrl = productBannerUrl;
    }

    public String getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    public List<String> getProductBannerList() {
        return productBannerList;
    }

    public void setProductBannerList(List<String> productBannerList) {
        this.productBannerList = productBannerList;
    }

    public List<String> getProductDetailList() {
        return productDetailList;
    }

    public void setProductDetailList(List<String> productDetailList) {
        this.productDetailList = productDetailList;
    }

    public ReceiverAddressDto getReceiverAddressDto() {
        return receiverAddressDto;
    }

    public void setReceiverAddressDto(ReceiverAddressDto receiverAddressDto) {
        this.receiverAddressDto = receiverAddressDto;
    }

    @Override
    public String toString() {
        return "ActiveGoodsDto{" +
                "activeValue=" + activeValue +
                ", alreadySoldCount=" + alreadySoldCount +
                ", bannerType=" + bannerType +
                ", freightMaxCount=" + freightMaxCount +
                ", name='" + name + '\'' +
                ", productNo='" + productNo + '\'' +
                ", sort=" + sort +
                ", stockCount=" + stockCount +
                ", subtitle='" + subtitle + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", createDate=" + createDate +
                ", freightInEveryAdd=" + freightInEveryAdd +
                ", freightInMaxCount=" + freightInMaxCount +
                ", id='" + id + '\'' +
                ", productBannerUrl='" + productBannerUrl + '\'' +
                ", productDetail='" + productDetail + '\'' +
                ", updateDate=" + updateDate +
                ", productBannerList=" + productBannerList +
                ", productDetailList=" + productDetailList +
                ", receiverAddressDto=" + receiverAddressDto +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.activeValue);
        dest.writeInt(this.alreadySoldCount);
        dest.writeInt(this.bannerType);
        dest.writeInt(this.freightMaxCount);
        dest.writeString(this.name);
        dest.writeString(this.productNo);
        dest.writeInt(this.sort);
        dest.writeInt(this.stockCount);
        dest.writeString(this.subtitle);
        dest.writeString(this.thumbnailUrl);
        dest.writeLong(this.createDate);
        dest.writeInt(this.freightInEveryAdd);
        dest.writeInt(this.freightInMaxCount);
        dest.writeString(this.id);
        dest.writeString(this.productBannerUrl);
        dest.writeString(this.productDetail);
        dest.writeLong(this.updateDate);
        dest.writeStringList(this.productBannerList);
        dest.writeStringList(this.productDetailList);
        dest.writeParcelable(this.receiverAddressDto, flags);
    }

    public ActiveGoodsDto() {
    }

    protected ActiveGoodsDto(Parcel in) {
        this.activeValue = in.readInt();
        this.alreadySoldCount = in.readInt();
        this.bannerType = in.readInt();
        this.freightMaxCount = in.readInt();
        this.name = in.readString();
        this.productNo = in.readString();
        this.sort = in.readInt();
        this.stockCount = in.readInt();
        this.subtitle = in.readString();
        this.thumbnailUrl = in.readString();
        this.createDate = in.readLong();
        this.freightInEveryAdd = in.readInt();
        this.freightInMaxCount = in.readInt();
        this.id = in.readString();
        this.productBannerUrl = in.readString();
        this.productDetail = in.readString();
        this.updateDate = in.readLong();
        this.productBannerList = in.createStringArrayList();
        this.productDetailList = in.createStringArrayList();
        this.receiverAddressDto = in.readParcelable(ReceiverAddressDto.class.getClassLoader());
    }

    public static final Parcelable.Creator<ActiveGoodsDto> CREATOR = new Parcelable.Creator<ActiveGoodsDto>() {
        @Override
        public ActiveGoodsDto createFromParcel(Parcel source) {
            return new ActiveGoodsDto(source);
        }

        @Override
        public ActiveGoodsDto[] newArray(int size) {
            return new ActiveGoodsDto[size];
        }
    };
}
