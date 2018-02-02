package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;


import com.km.rmbank.base.BaseEntity;

import java.util.List;

/**
 * Created by kamangkeji on 17/3/31.
 */

public class UserCardDto extends BaseEntity implements Parcelable {
    private String name;//真实姓名

    private String cardPhone;//名片手机号

    private String position;// 身份

    private String provideResources;//提供资源

    private String demandResources;//需求资源

    private List<String> provideResourcesMap;
    private List<String> demandResourcesMap;


    private String detailedAddress;//详细地址

    private String emailAddress;//邮箱

    private int status;

    private String friendMobilePhone;

    private String portraitUrl;
    private String mobilePhone;
    private String nickName;

    private String shareUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardPhone() {
        return cardPhone;
    }

    public void setCardPhone(String cardPhone) {
        this.cardPhone = cardPhone;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getProvideResources() {
        return provideResources;
    }

    public void setProvideResources(String provideResources) {
        this.provideResources = provideResources;
    }

    public String getDemandResources() {
        return demandResources;
    }

    public void setDemandResources(String demandResources) {
        this.demandResources = demandResources;
    }

    public String getDetailedAddress() {
        return detailedAddress;
    }

    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFriendMobilePhone() {
        return friendMobilePhone;
    }

    public void setFriendMobilePhone(String friendMobilePhone) {
        this.friendMobilePhone = friendMobilePhone;
    }

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

    public List<String> getProvideResourcesMap() {
        return provideResourcesMap;
    }

    public void setProvideResourcesMap(List<String> provideResourcesMap) {
        this.provideResourcesMap = provideResourcesMap;
    }

    public List<String> getDemandResourcesMap() {
        return demandResourcesMap;
    }

    public void setDemandResourcesMap(List<String> demandResourcesMap) {
        this.demandResourcesMap = demandResourcesMap;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    /**
     * 是否为空
     * @return
     */
    @Override
    public boolean isEmpty(){
        if (TextUtils.isEmpty(name)
                || TextUtils.isEmpty(position)){

            return true;
        }
        return false;
    }

    public boolean isEmpty2(){
        if (TextUtils.isEmpty(name)
                || TextUtils.isEmpty(position)){

            return true;
        }
        return false;
    }

    public UserCardDto() {
    }

    @Override
    public String toString() {
        return "UserCardDto{" +
                "name='" + name + '\'' +
                ", cardPhone='" + cardPhone + '\'' +
                ", position='" + position + '\'' +
                ", provideResources='" + provideResources + '\'' +
                ", demandResources='" + demandResources + '\'' +
                ", provideResourcesMap=" + provideResourcesMap +
                ", demandResourcesMap=" + demandResourcesMap +
                ", detailedAddress='" + detailedAddress + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", status=" + status +
                ", friendMobilePhone='" + friendMobilePhone + '\'' +
                ", portraitUrl='" + portraitUrl + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", nickName='" + nickName + '\'' +
                ", shareUrl='" + shareUrl + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.cardPhone);
        dest.writeString(this.position);
        dest.writeString(this.provideResources);
        dest.writeString(this.demandResources);
        dest.writeStringList(this.provideResourcesMap);
        dest.writeStringList(this.demandResourcesMap);
        dest.writeString(this.detailedAddress);
        dest.writeString(this.emailAddress);
        dest.writeInt(this.status);
        dest.writeString(this.friendMobilePhone);
        dest.writeString(this.portraitUrl);
        dest.writeString(this.mobilePhone);
        dest.writeString(this.nickName);
        dest.writeString(this.shareUrl);
    }

    protected UserCardDto(Parcel in) {
        this.name = in.readString();
        this.cardPhone = in.readString();
        this.position = in.readString();
        this.provideResources = in.readString();
        this.demandResources = in.readString();
        this.provideResourcesMap = in.createStringArrayList();
        this.demandResourcesMap = in.createStringArrayList();
        this.detailedAddress = in.readString();
        this.emailAddress = in.readString();
        this.status = in.readInt();
        this.friendMobilePhone = in.readString();
        this.portraitUrl = in.readString();
        this.mobilePhone = in.readString();
        this.nickName = in.readString();
        this.shareUrl = in.readString();
    }

    public static final Creator<UserCardDto> CREATOR = new Creator<UserCardDto>() {
        @Override
        public UserCardDto createFromParcel(Parcel source) {
            return new UserCardDto(source);
        }

        @Override
        public UserCardDto[] newArray(int size) {
            return new UserCardDto[size];
        }
    };
}
