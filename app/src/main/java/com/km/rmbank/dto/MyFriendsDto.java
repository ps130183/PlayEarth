package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kamangkeji on 17/6/8.
 */

public class MyFriendsDto implements Parcelable {
    /**
     * portraitUrl : http://47.93.184.121/img/user/portrait/201705/29a948052b19412caaefa4108a4b0e99.png
     * mobilePhone : 15201200083
     * nickName : 韩小莲
     */

    private String portraitUrl;
    private String mobilePhone;
    private String nickName;

    public String getPortraitUrl() {
        return portraitUrl;
    }

    public void setPortraitUrl(String portraitUrl) {
        this.portraitUrl = portraitUrl;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.portraitUrl);
        dest.writeString(this.mobilePhone);
        dest.writeString(this.nickName);
    }

    public MyFriendsDto() {
    }

    protected MyFriendsDto(Parcel in) {
        this.portraitUrl = in.readString();
        this.mobilePhone = in.readString();
        this.nickName = in.readString();
    }

    public static final Parcelable.Creator<MyFriendsDto> CREATOR = new Parcelable.Creator<MyFriendsDto>() {
        @Override
        public MyFriendsDto createFromParcel(Parcel source) {
            return new MyFriendsDto(source);
        }

        @Override
        public MyFriendsDto[] newArray(int size) {
            return new MyFriendsDto[size];
        }
    };
}
