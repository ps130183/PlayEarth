package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.km.rmbank.base.BaseEntity;


/**
 * Created by kamangkeji on 17/3/30.
 */

public class ReceiverAddressDto extends BaseEntity implements Parcelable {
    private int isDefault;

    private String id;
    private String receivePerson;
    private String receivePersonPhone;
    private String receiveAddress;

    private String addressPosition;
    private int isDel;

    public int isDefault() {
        return isDefault;
    }

    public void setDefault(int aDefault) {
        isDefault = aDefault;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAddressPosition() {
        return addressPosition;
    }

    public void setAddressPosition(String addressPosition) {
        this.addressPosition = addressPosition;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    @Override
    public boolean isEmpty() {
        if (TextUtils.isEmpty(receivePerson) || TextUtils.isEmpty(receivePersonPhone) || TextUtils.isEmpty(receiveAddress)){
            return true;
        }
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.isDefault);
        dest.writeString(this.id);
        dest.writeString(this.receivePerson);
        dest.writeString(this.receivePersonPhone);
        dest.writeString(this.receiveAddress);
        dest.writeString(this.addressPosition);
        dest.writeInt(this.isDel);
    }

    public ReceiverAddressDto() {
    }

    protected ReceiverAddressDto(Parcel in) {
        this.isDefault = in.readInt();
        this.id = in.readString();
        this.receivePerson = in.readString();
        this.receivePersonPhone = in.readString();
        this.receiveAddress = in.readString();
        this.addressPosition = in.readString();
        this.isDel = in.readInt();
    }

    public static final Parcelable.Creator<ReceiverAddressDto> CREATOR = new Parcelable.Creator<ReceiverAddressDto>() {
        @Override
        public ReceiverAddressDto createFromParcel(Parcel source) {
            return new ReceiverAddressDto(source);
        }

        @Override
        public ReceiverAddressDto[] newArray(int size) {
            return new ReceiverAddressDto[size];
        }
    };
}
