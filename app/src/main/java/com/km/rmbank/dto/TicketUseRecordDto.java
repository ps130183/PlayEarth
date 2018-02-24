package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PengSong on 18/2/12.
 */

public class TicketUseRecordDto implements Parcelable {

    /**
     * clubCommodityId : 1
     * clubId : 48
     * createDate : 1518447091000
     * day :
     * orderNo : TK20180212225131116
     * payNumber : TK20180212225131114
     * payType : 6
     * personNum : 1
     * price : 0
     * status : 1
     * ticketNo : 10000001
     * tripDate : 1519056000000
     * updateDate : 1518447091000
     * userId : 235
     */

    private String clubCommodityId;
    private String clubId;
    private long createDate;
    private String day;
    private String orderNo;
    private String payNumber;
    private int payType;
    private int personNum;
    private String price;
    private String status;
    private String ticketNo;
    private long tripDate;
    private long updateDate;
    private String userId;
    /**
     * clubCommodityName : 两天一日任尔游
     */

    private String clubCommodityName;

    public String getClubCommodityId() {
        return clubCommodityId;
    }

    public void setClubCommodityId(String clubCommodityId) {
        this.clubCommodityId = clubCommodityId;
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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPayNumber() {
        return payNumber;
    }

    public void setPayNumber(String payNumber) {
        this.payNumber = payNumber;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int getPersonNum() {
        return personNum;
    }

    public void setPersonNum(int personNum) {
        this.personNum = personNum;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public long getTripDate() {
        return tripDate;
    }

    public void setTripDate(long tripDate) {
        this.tripDate = tripDate;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public TicketUseRecordDto() {
    }

    public String getClubCommodityName() {
        return clubCommodityName;
    }

    public void setClubCommodityName(String clubCommodityName) {
        this.clubCommodityName = clubCommodityName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.clubCommodityId);
        dest.writeString(this.clubId);
        dest.writeLong(this.createDate);
        dest.writeString(this.day);
        dest.writeString(this.orderNo);
        dest.writeString(this.payNumber);
        dest.writeInt(this.payType);
        dest.writeInt(this.personNum);
        dest.writeString(this.price);
        dest.writeString(this.status);
        dest.writeString(this.ticketNo);
        dest.writeLong(this.tripDate);
        dest.writeLong(this.updateDate);
        dest.writeString(this.userId);
        dest.writeString(this.clubCommodityName);
    }

    protected TicketUseRecordDto(Parcel in) {
        this.clubCommodityId = in.readString();
        this.clubId = in.readString();
        this.createDate = in.readLong();
        this.day = in.readString();
        this.orderNo = in.readString();
        this.payNumber = in.readString();
        this.payType = in.readInt();
        this.personNum = in.readInt();
        this.price = in.readString();
        this.status = in.readString();
        this.ticketNo = in.readString();
        this.tripDate = in.readLong();
        this.updateDate = in.readLong();
        this.userId = in.readString();
        this.clubCommodityName = in.readString();
    }

    public static final Creator<TicketUseRecordDto> CREATOR = new Creator<TicketUseRecordDto>() {
        @Override
        public TicketUseRecordDto createFromParcel(Parcel source) {
            return new TicketUseRecordDto(source);
        }

        @Override
        public TicketUseRecordDto[] newArray(int size) {
            return new TicketUseRecordDto[size];
        }
    };
}
