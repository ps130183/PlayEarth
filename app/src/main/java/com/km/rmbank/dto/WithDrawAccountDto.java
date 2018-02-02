package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.km.rmbank.base.BaseEntity;


/**
 * Created by kamangkeji on 17/3/19.
 */

public class WithDrawAccountDto extends BaseEntity implements Parcelable {

    private String id;
    private String name;
    private String withdrawPhone;
    private String typeName;
    private String withdrawNumber;

    private int delete;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWithdrawPhone() {
        return withdrawPhone;
    }

    public void setWithdrawPhone(String withdrawPhone) {
        this.withdrawPhone = withdrawPhone;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getWithdrawNumber() {
        return withdrawNumber;
    }

    public void setWithdrawNumber(String withdrawNumber) {
        this.withdrawNumber = withdrawNumber;
    }

    public int getDelete() {
        return delete;
    }

    public void setDelete(int delete) {
        this.delete = delete;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public WithDrawAccountDto() {
    }

    @Override
    public boolean isEmpty() {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(withdrawPhone)
                || TextUtils.isEmpty(typeName) || TextUtils.isEmpty(withdrawNumber)){
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
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.withdrawPhone);
        dest.writeString(this.typeName);
        dest.writeString(this.withdrawNumber);
        dest.writeInt(this.delete);
    }

    protected WithDrawAccountDto(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.withdrawPhone = in.readString();
        this.typeName = in.readString();
        this.withdrawNumber = in.readString();
        this.delete = in.readInt();
    }

    public static final Creator<WithDrawAccountDto> CREATOR = new Creator<WithDrawAccountDto>() {
        @Override
        public WithDrawAccountDto createFromParcel(Parcel source) {
            return new WithDrawAccountDto(source);
        }

        @Override
        public WithDrawAccountDto[] newArray(int size) {
            return new WithDrawAccountDto[size];
        }
    };
}
