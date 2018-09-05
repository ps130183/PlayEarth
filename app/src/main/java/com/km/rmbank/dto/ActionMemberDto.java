package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.km.rmbank.R;
import com.ps.mrcyclerview.delegate.ItemDelegate;

/**
 * Created by kamangkeji on 17/7/14.
 */

public class ActionMemberDto implements Parcelable,ItemDelegate {
    private String registrationName;	//姓名
    private String registrationPhone;   //注册手机号
    private String headImage;// 头像
    private String userCompany;   //用户公司
    private String userPosition;  //用户职位
    private int isInitiator; // 0否  1是

    public int getIsInitiator() {
        return isInitiator;
    }

    public void setIsInitiator(int isInitiator) {
        this.isInitiator = isInitiator;
    }

    public String getUserCompany() {
        return userCompany;
    }

    public void setUserCompany(String userCompany) {
        this.userCompany = userCompany;
    }

    public String getUserPosition() {
        return userPosition;
    }

    public void setUserPosition(String userPosition) {
        this.userPosition = userPosition;
    }

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

    public ActionMemberDto() {
    }

    @Override
    public int getItemViewRes() {
        return R.layout.item_action_apply_statistics;
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
        dest.writeString(this.userCompany);
        dest.writeString(this.userPosition);
        dest.writeInt(this.isInitiator);
    }

    protected ActionMemberDto(Parcel in) {
        this.registrationName = in.readString();
        this.registrationPhone = in.readString();
        this.headImage = in.readString();
        this.userCompany = in.readString();
        this.userPosition = in.readString();
        this.isInitiator = in.readInt();
    }

    public static final Creator<ActionMemberDto> CREATOR = new Creator<ActionMemberDto>() {
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
