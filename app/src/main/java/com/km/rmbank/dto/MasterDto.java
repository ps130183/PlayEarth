package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PengSong on 17/10/17.
 */

public class MasterDto implements Parcelable {

    /**
     * address : 香港
     * createDate : 1507865696000
     * delDel : 0
     * headings : 影帝好演技
     * id : 3
     * introduce : 脑子跟不上眼
     * mobilePhone : 15000000000
     * name : 张家辉
     * num : 52525522
     * portraitUrl : http://wanzhuandiqiu.com/img/user/portrait/201709/zhangtou.png
     * representativeImag : http://wanzhuandiqiu.com/img/user/portrait/201709/zhangdaibiao.jpg
     * statusDel : 1
     * updateDate : 1507865698000
     */

    private String address;
    private long createDate;
    private String delDel;
    private String headings;
    private String id;
    private String introduce;
    private String mobilePhone;
    private String name;
    private int num;
    private String portraitUrl;
    private String representativeImag;
    private String statusDel;
    private long updateDate;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getDelDel() {
        return delDel;
    }

    public void setDelDel(String delDel) {
        this.delDel = delDel;
    }

    public String getHeadings() {
        return headings;
    }

    public void setHeadings(String headings) {
        this.headings = headings;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getPortraitUrl() {
        return portraitUrl;
    }

    public void setPortraitUrl(String portraitUrl) {
        this.portraitUrl = portraitUrl;
    }

    public String getRepresentativeImag() {
        return representativeImag;
    }

    public void setRepresentativeImag(String representativeImag) {
        this.representativeImag = representativeImag;
    }

    public String getStatusDel() {
        return statusDel;
    }

    public void setStatusDel(String statusDel) {
        this.statusDel = statusDel;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "MasterDto{" +
                "address='" + address + '\'' +
                ", createDate=" + createDate +
                ", delDel='" + delDel + '\'' +
                ", headings='" + headings + '\'' +
                ", id='" + id + '\'' +
                ", introduce='" + introduce + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", name='" + name + '\'' +
                ", num=" + num +
                ", portraitUrl='" + portraitUrl + '\'' +
                ", representativeImag='" + representativeImag + '\'' +
                ", statusDel='" + statusDel + '\'' +
                ", updateDate=" + updateDate +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.address);
        dest.writeLong(this.createDate);
        dest.writeString(this.delDel);
        dest.writeString(this.headings);
        dest.writeString(this.id);
        dest.writeString(this.introduce);
        dest.writeString(this.mobilePhone);
        dest.writeString(this.name);
        dest.writeInt(this.num);
        dest.writeString(this.portraitUrl);
        dest.writeString(this.representativeImag);
        dest.writeString(this.statusDel);
        dest.writeLong(this.updateDate);
    }

    public MasterDto() {
    }

    protected MasterDto(Parcel in) {
        this.address = in.readString();
        this.createDate = in.readLong();
        this.delDel = in.readString();
        this.headings = in.readString();
        this.id = in.readString();
        this.introduce = in.readString();
        this.mobilePhone = in.readString();
        this.name = in.readString();
        this.num = in.readInt();
        this.portraitUrl = in.readString();
        this.representativeImag = in.readString();
        this.statusDel = in.readString();
        this.updateDate = in.readLong();
    }

    public static final Parcelable.Creator<MasterDto> CREATOR = new Parcelable.Creator<MasterDto>() {
        @Override
        public MasterDto createFromParcel(Parcel source) {
            return new MasterDto(source);
        }

        @Override
        public MasterDto[] newArray(int size) {
            return new MasterDto[size];
        }
    };
}
