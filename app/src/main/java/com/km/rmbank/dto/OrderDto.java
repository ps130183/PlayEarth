package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kamangkeji on 17/3/24.
 */

public class OrderDto implements Parcelable {

    /**
     * createDate : 1491969851000
     * exchange : 0
     * freight : 8
     * id : 67
     * isDel : 0
     * isNeedInvoice : 0
     * isUserselfPick : 0
     * orderNo : PO20170412120410943
     * productCount : 4
     * productName : 高仿小猪佩奇大型玩具
     * productNo : PN20170411021840852
     * productTotalPrice : 4
     * productType : 1
     * productUnitPrice : 0.01
     * receiveAddress : 北京市北京市丰台区被骂录取线啊几乎
     * receivePerson : 彭松大哥
     * receivePersonPhone : 15631258567
     * shopName : 18232580820
     * shopUserId : 5
     * status : 1
     * thumbnailUrl : http://192.168.31.216:8088/img/shop/201704/60452b1687a945e2b713a8197d540205.png
     * totalPrice : 8.04
     * tradeChannel : 0
     * updateDate : 1491969851000
     * userId : 3
     */

    private String createDate;
    private String exchange;
    private String freight;
    private String id;
    private int isDel;
    private int isNeedInvoice;
    private int isUserselfPick;
    private String orderNo;
    private int productCount;
    private String productName;
    private String productNo;
    private String productTotalPrice;
    private int productType;
    private String productUnitPrice;
    private String receiveAddress;
    private String receivePerson;
    private String receivePersonPhone;
    private String shopName;
    private String shopUserId;
    private int status;
    private String thumbnailUrl;
    private String totalPrice;
    private int tradeChannel;
    private String updateDate;
    private String userId;
    /**
     * isComment : 0
     * totalPrice : 0
     * freight : 0
     * productUnitPrice : 0.01
     * commentStartLevel : 0
     * courierNumber : 123456799
     * expressCompany : 卡忙快递
     */

    private int isComment;
    private String productUnitPriceX;
    private int commentStartLevel;
    private String courierNumber;
    private String expressCompany;

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public int getIsNeedInvoice() {
        return isNeedInvoice;
    }

    public void setIsNeedInvoice(int isNeedInvoice) {
        this.isNeedInvoice = isNeedInvoice;
    }

    public int getIsUserselfPick() {
        return isUserselfPick;
    }

    public void setIsUserselfPick(int isUserselfPick) {
        this.isUserselfPick = isUserselfPick;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(String productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    public String getProductUnitPrice() {
        return productUnitPrice;
    }

    public void setProductUnitPrice(String productUnitPrice) {
        this.productUnitPrice = productUnitPrice;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getReceivePerson() {
        return receivePerson;
    }

    public void setReceivePerson(String receivePerson) {
        this.receivePerson = receivePerson;
    }

    public String getReceivePersonPhone() {
        return receivePersonPhone;
    }

    public void setReceivePersonPhone(String receivePersonPhone) {
        this.receivePersonPhone = receivePersonPhone;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopUserId() {
        return shopUserId;
    }

    public void setShopUserId(String shopUserId) {
        this.shopUserId = shopUserId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTradeChannel() {
        return tradeChannel;
    }

    public void setTradeChannel(int tradeChannel) {
        this.tradeChannel = tradeChannel;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public OrderDto() {
    }

    public int getIsComment() {
        return isComment;
    }

    public void setIsComment(int isComment) {
        this.isComment = isComment;
    }
    public String getProductUnitPriceX() {
        return productUnitPriceX;
    }

    public void setProductUnitPriceX(String productUnitPriceX) {
        this.productUnitPriceX = productUnitPriceX;
    }

    public int getCommentStartLevel() {
        return commentStartLevel;
    }

    public void setCommentStartLevel(int commentStartLevel) {
        this.commentStartLevel = commentStartLevel;
    }

    public String getCourierNumber() {
        return courierNumber;
    }

    public void setCourierNumber(String courierNumber) {
        this.courierNumber = courierNumber;
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.createDate);
        dest.writeString(this.exchange);
        dest.writeString(this.freight);
        dest.writeString(this.id);
        dest.writeInt(this.isDel);
        dest.writeInt(this.isNeedInvoice);
        dest.writeInt(this.isUserselfPick);
        dest.writeString(this.orderNo);
        dest.writeInt(this.productCount);
        dest.writeString(this.productName);
        dest.writeString(this.productNo);
        dest.writeString(this.productTotalPrice);
        dest.writeInt(this.productType);
        dest.writeString(this.productUnitPrice);
        dest.writeString(this.receiveAddress);
        dest.writeString(this.receivePerson);
        dest.writeString(this.receivePersonPhone);
        dest.writeString(this.shopName);
        dest.writeString(this.shopUserId);
        dest.writeInt(this.status);
        dest.writeString(this.thumbnailUrl);
        dest.writeString(this.totalPrice);
        dest.writeInt(this.tradeChannel);
        dest.writeString(this.updateDate);
        dest.writeString(this.userId);
        dest.writeInt(this.isComment);
        dest.writeString(this.productUnitPriceX);
        dest.writeInt(this.commentStartLevel);
        dest.writeString(this.courierNumber);
        dest.writeString(this.expressCompany);
    }

    protected OrderDto(Parcel in) {
        this.createDate = in.readString();
        this.exchange = in.readString();
        this.freight = in.readString();
        this.id = in.readString();
        this.isDel = in.readInt();
        this.isNeedInvoice = in.readInt();
        this.isUserselfPick = in.readInt();
        this.orderNo = in.readString();
        this.productCount = in.readInt();
        this.productName = in.readString();
        this.productNo = in.readString();
        this.productTotalPrice = in.readString();
        this.productType = in.readInt();
        this.productUnitPrice = in.readString();
        this.receiveAddress = in.readString();
        this.receivePerson = in.readString();
        this.receivePersonPhone = in.readString();
        this.shopName = in.readString();
        this.shopUserId = in.readString();
        this.status = in.readInt();
        this.thumbnailUrl = in.readString();
        this.totalPrice = in.readString();
        this.tradeChannel = in.readInt();
        this.updateDate = in.readString();
        this.userId = in.readString();
        this.isComment = in.readInt();
        this.productUnitPriceX = in.readString();
        this.commentStartLevel = in.readInt();
        this.courierNumber = in.readString();
        this.expressCompany = in.readString();
    }

    public static final Creator<OrderDto> CREATOR = new Creator<OrderDto>() {
        @Override
        public OrderDto createFromParcel(Parcel source) {
            return new OrderDto(source);
        }

        @Override
        public OrderDto[] newArray(int size) {
            return new OrderDto[size];
        }
    };
}
