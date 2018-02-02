package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kamangkeji on 17/7/14.
 */

public class ActionMemberDto implements Parcelable {
    private String registrationName;	//姓名
    private String registrationPhone;   //注册手机号
    private String headImage;// 头像

    public String getRegistrationName() {
        return registrationName;
    }

    public void setRegistrationName(String registrationName) {
        this.registrationName = registrationName;
    }

    public String getRegistrationPhone() {
        return registrationPhone;
    }

    public void setRegistrationPhone(String registrationPhone) {
        this.registrationPhone = registrationPhone;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.registrationName);
        dest.writeString(this.registrationPhone);
        dest.writeString(this.headImage);
    }

    public ActionMemberDto() {
    }

    protected ActionMemberDto(Parcel in) {
        this.registrationName = in.readString();
        this.registrationPhone = in.readString();
        this.headImage = in.readString();
    }

    public static final Parcelable.Creator<ActionMemberDto> CREATOR = new Parcelable.Creator<ActionMemberDto>() {
        @Override
        public ActionMemberDto createFromParcel(Parcel source) {
            return new ActionMemberDto(source);
        }

        @Override
        public ActionMemberDto[] newArray(int size) {
            return new ActionMemberDto[size];
        }
    };
}
