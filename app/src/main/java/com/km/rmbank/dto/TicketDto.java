package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PengSong on 18/2/10.
 */

public class TicketDto implements Parcelable {

    /**
     * createDate : 1517996574000
     * name : 两天一日游
     * num : 5
     * status : 0
     * ticketId : 2
     * ticketLogo : http://47.93.184.121:8080/wzdq/Aiyg/aiygImage/2018/02/22411c665dbd42a6ae2ef5a5586ad7a2.png
     * ticketNo : TK2018020717425496
     * type : 0
     * userId : 235
     * validateTime : 1549532574000
     */

    private long createDate;
    private String name;
    private String num;
    private String status;
    private String ticketId;
    private String ticketLogo;
    private String ticketNo;
    private String type;
    private String userId;
    private long validateTime;

    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketLogo() {
        return ticketLogo;
    }

    public void setTicketLogo(String ticketLogo) {
        this.ticketLogo = ticketLogo;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getValidateTime() {
        return validateTime;
    }

    public void setValidateTime(long validateTime) {
        this.validateTime = validateTime;
    }

    public TicketDto() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.createDate);
        dest.writeString(this.name);
        dest.writeString(this.num);
        dest.writeString(this.status);
        dest.writeString(this.ticketId);
        dest.writeString(this.ticketLogo);
        dest.writeString(this.ticketNo);
        dest.writeString(this.type);
        dest.writeString(this.userId);
        dest.writeLong(this.validateTime);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
    }

    protected TicketDto(Parcel in) {
        this.createDate = in.readLong();
        this.name = in.readString();
        this.num = in.readString();
        this.status = in.readString();
        this.ticketId = in.readString();
        this.ticketLogo = in.readString();
        this.ticketNo = in.readString();
        this.type = in.readString();
        this.userId = in.readString();
        this.validateTime = in.readLong();
        this.isChecked = in.readByte() != 0;
    }

    public static final Creator<TicketDto> CREATOR = new Creator<TicketDto>() {
        @Override
        public TicketDto createFromParcel(Parcel source) {
            return new TicketDto(source);
        }

        @Override
        public TicketDto[] newArray(int size) {
            return new TicketDto[size];
        }
    };
}
