package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kamangkeji on 17/4/6.
 */

public class PayOrderDto implements Parcelable {
    /**
     * payNumber : REO20170406112139254
     * sumPrice : 20000.00
     */

    private String payNumber;
    private String sumPrice;
    private String userAccountBalance;
    private String role;

    public String getUserAccountBalance() {
        return userAccountBalance;
    }

    public void setUserAccountBalance(String userAccountBalance) {
        this.userAccountBalance = userAccountBalance;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPayNumber() {
        return payNumber;
    }

    public void setPayNumber(String payNumber) {
        this.payNumber = payNumber;
    }

    public String getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(String sumPrice) {
        this.sumPrice = sumPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.payNumber);
        dest.writeString(this.sumPrice);
        dest.writeString(this.userAccountBalance);
        dest.writeString(this.role);
    }

    public PayOrderDto() {
    }

    protected PayOrderDto(Parcel in) {
        this.payNumber = in.readString();
        this.sumPrice = in.readString();
        this.userAccountBalance = in.readString();
        this.role = in.readString();
    }

    public static final Parcelable.Creator<PayOrderDto> CREATOR = new Parcelable.Creator<PayOrderDto>() {
        @Override
        public PayOrderDto createFromParcel(Parcel source) {
            return new PayOrderDto(source);
        }

        @Override
        public PayOrderDto[] newArray(int size) {
            return new PayOrderDto[size];
        }
    };

    @Override
    public String toString() {
        return "PayOrderDto{" +
                "payNumber='" + payNumber + '\'' +
                ", sumPrice='" + sumPrice + '\'' +
                ", userAccountBalance='" + userAccountBalance + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
