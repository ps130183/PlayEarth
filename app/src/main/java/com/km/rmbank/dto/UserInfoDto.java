package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.km.rmbank.entity.DemandEntity;
import com.km.rmbank.entity.OtherIdentityEntity;
import com.km.rmbank.entity.SupplyEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PengSong on 18/1/18.
 */

public class UserInfoDto implements Cloneable, Parcelable {


    /**
     * total : 0
     * birthday : 1989-12-01
     * registerDate : 207
     * allowStutas : 0
     * isTeach : 1
     * roleName : 普通会员
     * updateDate : 2017-07-20
     * roleDate : 0
     * clubStatus : 1
     * portraitUrl : http://47.93.184.121/img/user/portrait/201707/ed777a250fc246169c9c83d1b03f519f.jpg
     * nickName : 彭松
     * activeValueAll : 2
     * isNotEditCard : 1
     * service : service
     * roleId : 4
     */

    private String total;
    private String birthday;
    private int registerDate;
    private String allowStutas;
    private int isTeach;
    private String roleName;
    private String updateDate;
    private int roleDate;
    private String clubStatus;
    private String portraitUrl;
    private String nickName;
    private int activeValueAll;
    private int isNotEditCard;
    private String service;
    private String roleId; //5:众筹合伙人
    private String clubId;
    /**
     * keepCount : 5
     * mobilePhone : 18888888888
     * balance : 997.04
     * detailedAddress : 玩转地球科学
     * name : 技术测试
     * shareUrl : http://192.168.10.131:8083/Aiyg/member/share/UserCode?mobilePhone=18888888888
     * position : 玩转气球
     * personalizedSignature : 我是玩转地球测试账号
     */

    private String keepCount;
    private String mobilePhone;
    private String cardPhone;
    private double balance;
    private String detailedAddress;
    private String name;
    private String shareUrl;
    private String position;
    private String personalizedSignature;
    private String company;
    private String emailAddress;
    private List<OtherIdentityEntity> identityList;
    private List<DemandEntity> demandList;
    private List<SupplyEntity> provideList;

    private String industryId;//行业Id
    private String industryName;//行业名称
    private String ticketCount;

    private int ballWritTotal;//球票总数

    private String cardId;

    private int status;//实名认证  0：未验证，1：验证中，2：通过，3：失败
    private int type;//2:商家


    public int getBallWritTotal() {
        return ballWritTotal;
    }

    public void setBallWritTotal(int ballWritTotal) {
        this.ballWritTotal = ballWritTotal;
    }

    public String getCardPhone() {
        return cardPhone;
    }

    public void setCardPhone(String cardPhone) {
        this.cardPhone = cardPhone;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(int registerDate) {
        this.registerDate = registerDate;
    }

    public String getAllowStutas() {
        return allowStutas;
    }

    public void setAllowStutas(String allowStutas) {
        this.allowStutas = allowStutas;
    }

    public int getIsTeach() {
        return isTeach;
    }

    public void setIsTeach(int isTeach) {
        this.isTeach = isTeach;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public int getRoleDate() {
        return roleDate;
    }

    public void setRoleDate(int roleDate) {
        this.roleDate = roleDate;
    }

    public String getClubStatus() {
        return clubStatus;
    }

    public void setClubStatus(String clubStatus) {
        this.clubStatus = clubStatus;
    }

    public String getPortraitUrl() {
        return portraitUrl;
    }

    public void setPortraitUrl(String portraitUrl) {
        this.portraitUrl = portraitUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getActiveValueAll() {
        return activeValueAll;
    }

    public void setActiveValueAll(int activeValueAll) {
        this.activeValueAll = activeValueAll;
    }

    public int getIsNotEditCard() {
        return isNotEditCard;
    }

    public void setIsNotEditCard(int isNotEditCard) {
        this.isNotEditCard = isNotEditCard;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getKeepCount() {
        return keepCount;
    }

    public void setKeepCount(String keepCount) {
        this.keepCount = keepCount;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getDetailedAddress() {
        return detailedAddress;
    }

    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPersonalizedSignature() {
        return personalizedSignature;
    }

    public void setPersonalizedSignature(String personalizedSignature) {
        this.personalizedSignature = personalizedSignature;
    }

    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(String ticketCount) {
        this.ticketCount = ticketCount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }


    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public List<OtherIdentityEntity> getIdentityList() {
        if (identityList == null){
            identityList = new ArrayList<>();
        }
        return identityList;
    }

    public void setIdentityList(List<OtherIdentityEntity> identityList) {
        this.identityList = identityList;
    }

    public List<DemandEntity> getDemandList() {
        if (demandList == null){
            demandList = new ArrayList<>();
        }
        return demandList;
    }

    public void setDemandList(List<DemandEntity> demandList) {
        this.demandList = demandList;
    }

    public List<SupplyEntity> getProvideList() {
        if (provideList == null){
            provideList = new ArrayList<>();
        }
        return provideList;
    }

    public void setProvideList(List<SupplyEntity> provideList) {
        this.provideList = provideList;
    }

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    @Override
    public String toString() {
        return "UserInfoDto{" +
                "total='" + total + '\'' +
                ", birthday='" + birthday + '\'' +
                ", registerDate=" + registerDate +
                ", allowStutas='" + allowStutas + '\'' +
                ", isTeach=" + isTeach +
                ", roleName='" + roleName + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", roleDate=" + roleDate +
                ", clubStatus='" + clubStatus + '\'' +
                ", portraitUrl='" + portraitUrl + '\'' +
                ", nickName='" + nickName + '\'' +
                ", activeValueAll=" + activeValueAll +
                ", isNotEditCard=" + isNotEditCard +
                ", service='" + service + '\'' +
                ", roleId='" + roleId + '\'' +
                ", clubId='" + clubId + '\'' +
                ", keepCount='" + keepCount + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", cardPhone='" + cardPhone + '\'' +
                ", balance=" + balance +
                ", detailedAddress='" + detailedAddress + '\'' +
                ", name='" + name + '\'' +
                ", shareUrl='" + shareUrl + '\'' +
                ", position='" + position + '\'' +
                ", personalizedSignature='" + personalizedSignature + '\'' +
                ", company='" + company + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", identityList=" + identityList +
                ", demandList=" + demandList +
                ", provideList=" + provideList +
                ", industryId='" + industryId + '\'' +
                ", industryName='" + industryName + '\'' +
                ", ticketCount='" + ticketCount + '\'' +
                ", ballWritTotal='" + ballWritTotal + '\'' +
                ", cardId='" + cardId + '\'' +
                ", status=" + status +
                ", type=" + type +
                '}';
    }

    public UserInfoDto() {
    }

    /**
     * 有数据为空
     * @return
     */
    public boolean isEmpty(){
        if (TextUtils.isEmpty(portraitUrl) || TextUtils.isEmpty(name)){
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
        dest.writeString(this.total);
        dest.writeString(this.birthday);
        dest.writeInt(this.registerDate);
        dest.writeString(this.allowStutas);
        dest.writeInt(this.isTeach);
        dest.writeString(this.roleName);
        dest.writeString(this.updateDate);
        dest.writeInt(this.roleDate);
        dest.writeString(this.clubStatus);
        dest.writeString(this.portraitUrl);
        dest.writeString(this.nickName);
        dest.writeInt(this.activeValueAll);
        dest.writeInt(this.isNotEditCard);
        dest.writeString(this.service);
        dest.writeString(this.roleId);
        dest.writeString(this.clubId);
        dest.writeString(this.keepCount);
        dest.writeString(this.mobilePhone);
        dest.writeString(this.cardPhone);
        dest.writeDouble(this.balance);
        dest.writeString(this.detailedAddress);
        dest.writeString(this.name);
        dest.writeString(this.shareUrl);
        dest.writeString(this.position);
        dest.writeString(this.personalizedSignature);
        dest.writeString(this.company);
        dest.writeString(this.emailAddress);
        dest.writeTypedList(this.identityList);
        dest.writeTypedList(this.demandList);
        dest.writeTypedList(this.provideList);
        dest.writeString(this.industryId);
        dest.writeString(this.industryName);
        dest.writeString(this.ticketCount);
        dest.writeInt(this.ballWritTotal);
        dest.writeString(this.cardId);
        dest.writeInt(this.status);
        dest.writeInt(this.type);
    }

    protected UserInfoDto(Parcel in) {
        this.total = in.readString();
        this.birthday = in.readString();
        this.registerDate = in.readInt();
        this.allowStutas = in.readString();
        this.isTeach = in.readInt();
        this.roleName = in.readString();
        this.updateDate = in.readString();
        this.roleDate = in.readInt();
        this.clubStatus = in.readString();
        this.portraitUrl = in.readString();
        this.nickName = in.readString();
        this.activeValueAll = in.readInt();
        this.isNotEditCard = in.readInt();
        this.service = in.readString();
        this.roleId = in.readString();
        this.clubId = in.readString();
        this.keepCount = in.readString();
        this.mobilePhone = in.readString();
        this.cardPhone = in.readString();
        this.balance = in.readDouble();
        this.detailedAddress = in.readString();
        this.name = in.readString();
        this.shareUrl = in.readString();
        this.position = in.readString();
        this.personalizedSignature = in.readString();
        this.company = in.readString();
        this.emailAddress = in.readString();
        this.identityList = in.createTypedArrayList(OtherIdentityEntity.CREATOR);
        this.demandList = in.createTypedArrayList(DemandEntity.CREATOR);
        this.provideList = in.createTypedArrayList(SupplyEntity.CREATOR);
        this.industryId = in.readString();
        this.industryName = in.readString();
        this.ticketCount = in.readString();
        this.ballWritTotal = in.readInt();
        this.cardId = in.readString();
        this.status = in.readInt();
        this.type = in.readInt();
    }

    public static final Creator<UserInfoDto> CREATOR = new Creator<UserInfoDto>() {
        @Override
        public UserInfoDto createFromParcel(Parcel source) {
            return new UserInfoDto(source);
        }

        @Override
        public UserInfoDto[] newArray(int size) {
            return new UserInfoDto[size];
        }
    };
}
