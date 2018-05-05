package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by PengSong on 18/4/2.
 */

public class ContractDto implements Parcelable {
    private String personId;

    private String nickName;
    private String personNamePinyin;

    private List<String> phones;

    private boolean isChecked;
    /**
     * createDate : 1522660819000
     * id : 581
     * name : 王赫
     * phone : 15100805753,18501053623
     * roleId : 0
     * status : 0
     */

    private long createDate;
    private String id;
    private String phone;
    private String roleId;
    private String status; //0:没绑定  1：被他人绑定 2：被自己绑定 3：待绑定

    public ContractDto() {
    }

    public ContractDto(String personId, String personName) {
        this.personId = personId;
        this.nickName = personName;
    }


    @Override
    public String toString() {
        return "ContractDto{" +
                "personId='" + personId + '\'' +
                ", nickName='" + nickName + '\'' +
                ", personNamePinyin='" + personNamePinyin + '\'' +
                ", phones=" + phones +
                ", isChecked=" + isChecked +
                ", createDate=" + createDate +
                ", id='" + id + '\'' +
                ", phone='" + phone + '\'' +
                ", roleId='" + roleId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public String getPersonNamePinyin() {
        return personNamePinyin;
    }

    public void setPersonNamePinyin(String personNamePinyin) {
        this.personNamePinyin = personNamePinyin;
    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.personId);
        dest.writeString(this.nickName);
        dest.writeString(this.personNamePinyin);
        dest.writeStringList(this.phones);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
        dest.writeLong(this.createDate);
        dest.writeString(this.id);
        dest.writeString(this.phone);
        dest.writeString(this.roleId);
        dest.writeString(this.status);
    }

    protected ContractDto(Parcel in) {
        this.personId = in.readString();
        this.nickName = in.readString();
        this.personNamePinyin = in.readString();
        this.phones = in.createStringArrayList();
        this.isChecked = in.readByte() != 0;
        this.createDate = in.readLong();
        this.id = in.readString();
        this.phone = in.readString();
        this.roleId = in.readString();
        this.status = in.readString();
    }

    public static final Parcelable.Creator<ContractDto> CREATOR = new Parcelable.Creator<ContractDto>() {
        @Override
        public ContractDto createFromParcel(Parcel source) {
            return new ContractDto(source);
        }

        @Override
        public ContractDto[] newArray(int size) {
            return new ContractDto[size];
        }
    };
}
