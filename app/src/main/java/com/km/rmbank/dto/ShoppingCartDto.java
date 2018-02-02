package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.List;

/**
 * Created by kamangkeji on 17/3/20.
 */

public class ShoppingCartDto implements Parcelable {
    private List<GoodsDetailsDto> productList;
    private String shopName;
    private String freight;

    private boolean isChecked;

    public ShoppingCartDto() {
    }

    public List<GoodsDetailsDto> getProductList() {
        return productList;
    }

    public void setProductList(List<GoodsDetailsDto> productList) {
        this.productList = productList;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getFreight() {
        return TextUtils.isEmpty(freight) ? "0.00" : freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.productList);
        dest.writeString(this.shopName);
        dest.writeString(this.freight);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
    }

    protected ShoppingCartDto(Parcel in) {
        this.productList = in.createTypedArrayList(GoodsDetailsDto.CREATOR);
        this.shopName = in.readString();
        this.freight = in.readString();
        this.isChecked = in.readByte() != 0;
    }

    public static final Creator<ShoppingCartDto> CREATOR = new Creator<ShoppingCartDto>() {
        @Override
        public ShoppingCartDto createFromParcel(Parcel source) {
            return new ShoppingCartDto(source);
        }

        @Override
        public ShoppingCartDto[] newArray(int size) {
            return new ShoppingCartDto[size];
        }
    };
}
