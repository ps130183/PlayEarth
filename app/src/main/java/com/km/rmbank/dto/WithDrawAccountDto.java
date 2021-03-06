package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseEntity;
import com.ps.mrcyclerview.delegate.ItemDelegate;


/**
 * Created by kamangkeji on 17/3/19.
 */

public class WithDrawAccountDto extends BaseEntity implements ItemDelegate,Parcelable {

    private String id;
    private String name;
    private String withdrawPhone;
    private String typeName;
    private String withdrawNumber;

    private String bankId;
    private String bankName;
    private String bankLogo;

    private String smsCode;

    private int delete;

    private boolean checked;

    private int layoutRes = R.layout.item_select_bank_card;

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

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankLogo() {
        return bankLogo;
    }

    public void setBankLogo(String bankLogo) {
        this.bankLogo = bankLogo;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void setLayoutRes(int layoutRes) {
        this.layoutRes = layoutRes;
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
    public int getItemViewRes() {
        return layoutRes;
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
        dest.writeString(this.bankId);
        dest.writeString(this.bankName);
        dest.writeString(this.bankLogo);
        dest.writeString(this.smsCode);
        dest.writeInt(this.delete);
        dest.writeByte(this.checked ? (byte) 1 : (byte) 0);
    }

    protected WithDrawAccountDto(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.withdrawPhone = in.readString();
        this.typeName = in.readString();
        this.withdrawNumber = in.readString();
        this.bankId = in.readString();
        this.bankName = in.readString();
        this.bankLogo = in.readString();
        this.smsCode = in.readString();
        this.delete = in.readInt();
        this.checked = in.readByte() != 0;
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
