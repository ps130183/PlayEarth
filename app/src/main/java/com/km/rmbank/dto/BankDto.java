package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.km.rmbank.R;
import com.ps.mrcyclerview.delegate.ItemDelegate;

/**
 * Created by PengSong on 18/7/3.
 */

public class BankDto implements ItemDelegate, Parcelable {


    /**
     * bankLogo : http://47.93.184.121:8080/wzdq/Aiyg/aiygImage/2018/06/d0045fd828c74a6684f44f9fade3418e.png
     * bankName : 中信实业银行
     * id : 1
     */

    private String bankLogo;
    private String bankName;
    private String id;

    private boolean checked;

    @Override
    public int getItemViewRes() {
        return R.layout.item_select_bank;
    }

    public String getBankLogo() {
        return bankLogo;
    }

    public void setBankLogo(String bankLogo) {
        this.bankLogo = bankLogo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public BankDto() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.bankLogo);
        dest.writeString(this.bankName);
        dest.writeString(this.id);
        dest.writeByte(this.checked ? (byte) 1 : (byte) 0);
    }

    protected BankDto(Parcel in) {
        this.bankLogo = in.readString();
        this.bankName = in.readString();
        this.id = in.readString();
        this.checked = in.readByte() != 0;
    }

    public static final Creator<BankDto> CREATOR = new Creator<BankDto>() {
        @Override
        public BankDto createFromParcel(Parcel source) {
            return new BankDto(source);
        }

        @Override
        public BankDto[] newArray(int size) {
            return new BankDto[size];
        }
    };
}
