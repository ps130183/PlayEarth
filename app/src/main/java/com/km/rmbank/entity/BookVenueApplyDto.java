package com.km.rmbank.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.ps.mrcyclerview.delegate.ItemDelegate;

/**
 * Created by PengSong on 18/8/12.
 */

public class BookVenueApplyDto implements ItemDelegate, Parcelable {


    /**
     * createDate : 1534764429000
     * endDate : 1534779180000
     * id : 3
     * placeId : 1
     * placeTitle : 唐人汇天天英雄会
     * placeType : 1
     * startDate : 1534764780000
     * status : 1
     * userId : 4124
     */

    private String placeUrl;
    private long createDate;
    private long endDate;
    private String id;
    private String placeId;
    private String placeTitle;
    private String placeType;
    private long startDate;
    private String status;//1 审核中 2、通过 3、拒绝  4、取消  5活动已发布
    private String userId;
    private String payStatus;//0 未支付 1已支付
    private String placePrice;

    private int itemViewRes;
    /**
     * reason : &lt;p&gt;
     你好，本次审核不通过，下次再来吧，行，就这样吧！ 发空间和速度快福建省地方还款计划&lt;/p&gt;
     &lt;p&gt;
     快点通过吧！&lt;/p&gt;
     &lt;p style=&quot;text-align: right;&quot;&gt;
     感谢理解与支持&lt;/p&gt;
     &lt;p style=&quot;text-align: right;&quot;&gt;
     2018年5月26号&lt;/p&gt;
     */

    private String reason;

    public void setItemViewRes(int itemViewRes) {
        this.itemViewRes = itemViewRes;
    }

    @Override
    public int getItemViewRes() {
        return itemViewRes;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getPlacePrice() {
        return placePrice;
    }

    public void setPlacePrice(String placePrice) {
        this.placePrice = placePrice;
    }

    public String getPlaceUrl() {
        return placeUrl;
    }

    public void setPlaceUrl(String placeUrl) {
        this.placeUrl = placeUrl;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getPlaceTitle() {
        return placeTitle;
    }

    public void setPlaceTitle(String placeTitle) {
        this.placeTitle = placeTitle;
    }

    public String getPlaceType() {
        return placeType;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BookVenueApplyDto() {
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "BookVenueApplyDto{" +
                "placeUrl='" + placeUrl + '\'' +
                ", createDate=" + createDate +
                ", endDate=" + endDate +
                ", id='" + id + '\'' +
                ", placeId='" + placeId + '\'' +
                ", placeTitle='" + placeTitle + '\'' +
                ", placeType='" + placeType + '\'' +
                ", startDate=" + startDate +
                ", status='" + status + '\'' +
                ", userId='" + userId + '\'' +
                ", payStatus='" + payStatus + '\'' +
                ", placePrice='" + placePrice + '\'' +
                ", itemViewRes=" + itemViewRes +
                ", reason='" + reason + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.placeUrl);
        dest.writeLong(this.createDate);
        dest.writeLong(this.endDate);
        dest.writeString(this.id);
        dest.writeString(this.placeId);
        dest.writeString(this.placeTitle);
        dest.writeString(this.placeType);
        dest.writeLong(this.startDate);
        dest.writeString(this.status);
        dest.writeString(this.userId);
        dest.writeString(this.payStatus);
        dest.writeString(this.placePrice);
        dest.writeInt(this.itemViewRes);
        dest.writeString(this.reason);
    }

    protected BookVenueApplyDto(Parcel in) {
        this.placeUrl = in.readString();
        this.createDate = in.readLong();
        this.endDate = in.readLong();
        this.id = in.readString();
        this.placeId = in.readString();
        this.placeTitle = in.readString();
        this.placeType = in.readString();
        this.startDate = in.readLong();
        this.status = in.readString();
        this.userId = in.readString();
        this.payStatus = in.readString();
        this.placePrice = in.readString();
        this.itemViewRes = in.readInt();
        this.reason = in.readString();
    }

    public static final Creator<BookVenueApplyDto> CREATOR = new Creator<BookVenueApplyDto>() {
        @Override
        public BookVenueApplyDto createFromParcel(Parcel source) {
            return new BookVenueApplyDto(source);
        }

        @Override
        public BookVenueApplyDto[] newArray(int size) {
            return new BookVenueApplyDto[size];
        }
    };
}
