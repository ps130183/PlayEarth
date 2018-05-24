package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by PengSong on 18/5/23.
 */

public class PayOrderContactDto implements Parcelable {
    private String payNumber;
    private String sumPrice;
    private List<ContactDto> bookList;

    public PayOrderContactDto() {
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

    public List<ContactDto> getBookList() {
        return bookList;
    }

    public void setBookList(List<ContactDto> bookList) {
        this.bookList = bookList;
    }

    @Override
    public String toString() {
        return "PayOrderContactDto{" +
                "payNumber='" + payNumber + '\'' +
                ", sumPrice='" + sumPrice + '\'' +
                ", bookList=" + bookList +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.payNumber);
        dest.writeString(this.sumPrice);
        dest.writeTypedList(this.bookList);
    }

    protected PayOrderContactDto(Parcel in) {
        this.payNumber = in.readString();
        this.sumPrice = in.readString();
        this.bookList = in.createTypedArrayList(ContactDto.CREATOR);
    }

    public static final Parcelable.Creator<PayOrderContactDto> CREATOR = new Parcelable.Creator<PayOrderContactDto>() {
        @Override
        public PayOrderContactDto createFromParcel(Parcel source) {
            return new PayOrderContactDto(source);
        }

        @Override
        public PayOrderContactDto[] newArray(int size) {
            return new PayOrderContactDto[size];
        }
    };
}
