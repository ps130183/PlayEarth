package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PengSong on 18/5/23.
 */

public class ContactDto implements Parcelable {
    private Long contactId;
    private String nickName;
    private String phone;

    public ContactDto(Long contactId, String nickName, String phone) {
        this.contactId = contactId;
        this.nickName = nickName;
        this.phone = phone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhones(String phone) {
        this.phone = phone;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    @Override
    public String toString() {
        return "ContactDto{" +
                "contactId=" + contactId +
                ", nickName='" + nickName + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.contactId);
        dest.writeString(this.nickName);
        dest.writeString(this.phone);
    }

    protected ContactDto(Parcel in) {
        this.contactId = (Long) in.readValue(Long.class.getClassLoader());
        this.nickName = in.readString();
        this.phone = in.readString();
    }

    public static final Parcelable.Creator<ContactDto> CREATOR = new Parcelable.Creator<ContactDto>() {
        @Override
        public ContactDto createFromParcel(Parcel source) {
            return new ContactDto(source);
        }

        @Override
        public ContactDto[] newArray(int size) {
            return new ContactDto[size];
        }
    };
}
