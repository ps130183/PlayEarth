package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;


import com.km.rmbank.base.BaseEntity;

import java.util.List;

/**
 * Created by kamangkeji on 17/4/6.
 */

public class GoodsDetailsDto extends BaseEntity implements Parcelable {

    private boolean isChecked;
    /**
     * alreadySoldCount : 0
     * bannerType : 0
     * bannerUrl : /product/normal/201704/58451dc7fe1840339c3642603945a47d.jpg#/product/normal/201704/253ab6fc17774206a568be7912a55f9a.png#/product/normal/201704/bff25c57fa524d66a13ce46837d65b88.png
     * createDate : 1491522096000
     * freightInEveryAdd : 8
     * freightInMaxCount : 12
     * freightMaxCount : 0
     * id : 10004
     * indexActivityImage : 0
     * indexActivityPosition : 0
     * isInIndexActivity : 1
     * isfollow : 0
     * name : 国字一号海参888
     * price : 888
     * productBannerList : ["http://192.168.31.216:8088/img/poduct/normal/201704/46e41daf0bf048f4898c1fe91636dc03.png","http://192.168.31.216:8088/img/product/normal/201704/f9c72e641cb14dadbd3ac0d42db5ba6b.png","http://192.168.31.216:8088/img/product/normal/201704/f56113fb1d5b45388437d8e2b59f6614.jpg"]
     * productBannerUrl : /poduct/normal/201704/46e41daf0bf048f4898c1fe91636dc03.png#/product/normal/201704/f9c72e641cb14dadbd3ac0d42db5ba6b.png#/product/normal/201704/f56113fb1d5b45388437d8e2b59f6614.jpg#
     * productDetail : /roduct/normal/201704/00206f51d2e54acc9512fe0a380d2811.png#/product/normal/201704/cf7c24e471684cb3881eed333f9c7194.png#
     * productDetailList : ["http://192.168.31.216:8088/img/roduct/normal/201704/00206f51d2e54acc9512fe0a380d2811.png","http://192.168.31.216:8088/img/product/normal/201704/cf7c24e471684cb3881eed333f9c7194.png"]
     * productInShopCarCount : 0
     * productNo : PN20170407074135556
     * productType : 海产
     * residualStock : 0
     * role : 0
     * shopId : 3
     * sort : 0
     * source : 2
     * status : 1
     * stockCount : 0
     * subtitle : 五折贱卖
     * thumbnailUrl : http://192.168.31.216:8088/img/poduct/normal/201704/46e41daf0bf048f4898c1fe91636dc03.png
     * updateDate : 1491522096000
     */

    private String alreadySoldCount;
    private String bannerType;
    private String bannerUrl;
    private String createDate;
    private String freightInEveryAdd;
    private String freightInMaxCount;
    private String freightMaxCount;
    private String id;
    private String indexActivityImage;
    private String indexActivityPosition;
    private String isInIndexActivity;
    private String isfollow;
    private String name;
    private String price;
    private String productBannerUrl;
    private String productDetail;
    private int productInShopCarCount;
    private String productNo;
    private String productType;
    private String productTypeOne;
    private String residualStock;
    private String role;
    private String shopId;
    private String sort;
    private String source;
    private String status;
    private String stockCount;
    private String subtitle;
    private String thumbnailUrl;
    private String updateDate;
    private List<String> productBannerList;
    private List<String> productDetailList;
    private String total;

    private HomeGoodsTypeDto goodsTypeDto;

    private ReceiverAddressDto receiverAddressDto;

    private String commentNum;

    @Override
    public boolean isEmpty() {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(subtitle) || TextUtils.isEmpty(price) || TextUtils.isEmpty(isInIndexActivity)
                || TextUtils.isEmpty(freightInMaxCount) || TextUtils.isEmpty(freightInEveryAdd)
                || TextUtils.isEmpty(productBannerUrl) || TextUtils.isEmpty(productDetail) || TextUtils.isEmpty(bannerUrl)){
            return true;
        }
        return false;
    }

    public String getAlreadySoldCount() {
        return alreadySoldCount;
    }

    public void setAlreadySoldCount(String alreadySoldCount) {
        this.alreadySoldCount = alreadySoldCount;
    }

    public String getBannerType() {
        return bannerType;
    }

    public void setBannerType(String bannerType) {
        this.bannerType = bannerType;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getFreightInEveryAdd() {
        return freightInEveryAdd;
    }

    public void setFreightInEveryAdd(String freightInEveryAdd) {
        this.freightInEveryAdd = freightInEveryAdd;
    }

    public String getFreightInMaxCount() {
        return freightInMaxCount;
    }

    public void setFreightInMaxCount(String freightInMaxCount) {
        this.freightInMaxCount = freightInMaxCount;
    }

    public String getFreightMaxCount() {
        return freightMaxCount;
    }

    public void setFreightMaxCount(String freightMaxCount) {
        this.freightMaxCount = freightMaxCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIndexActivityImage() {
        return indexActivityImage;
    }

    public void setIndexActivityImage(String indexActivityImage) {
        this.indexActivityImage = indexActivityImage;
    }

    public String getIndexActivityPosition() {
        return indexActivityPosition;
    }

    public void setIndexActivityPosition(String indexActivityPosition) {
        this.indexActivityPosition = indexActivityPosition;
    }

    public String getIsInIndexActivity() {
        return isInIndexActivity;
    }

    public void setIsInIndexActivity(String isInIndexActivity) {
        this.isInIndexActivity = isInIndexActivity;
    }

    public String getIsfollow() {
        return isfollow;
    }

    public void setIsfollow(String isfollow) {
        this.isfollow = isfollow;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductBannerUrl() {
        return productBannerUrl;
    }

    public void setProductBannerUrl(String productBannerUrl) {
        this.productBannerUrl = productBannerUrl;
    }

    public String getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail;
    }

    public int getProductInShopCarCount() {
        return productInShopCarCount;
    }

    public void setProductInShopCarCount(int productInShopCarCount) {
        this.productInShopCarCount = productInShopCarCount;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getResidualStock() {
        return residualStock;
    }

    public void setResidualStock(String residualStock) {
        this.residualStock = residualStock;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStockCount() {
        return stockCount;
    }

    public void setStockCount(String stockCount) {
        this.stockCount = stockCount;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public List<String> getProductBannerList() {
        return productBannerList;
    }

    public void setProductBannerList(List<String> productBannerList) {
        this.productBannerList = productBannerList;
    }

    public List<String> getProductDetailList() {
        return productDetailList;
    }

    public void setProductDetailList(List<String> productDetailList) {
        this.productDetailList = productDetailList;
    }

    public ReceiverAddressDto getReceiverAddressDto() {
        return receiverAddressDto;
    }

    public void setReceiverAddressDto(ReceiverAddressDto receiverAddressDto) {
        this.receiverAddressDto = receiverAddressDto;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public HomeGoodsTypeDto getGoodsTypeDto() {
        return goodsTypeDto;
    }

    public void setGoodsTypeDto(HomeGoodsTypeDto goodsTypeDto) {
        this.goodsTypeDto = goodsTypeDto;
    }

    public String getProductTypeOne() {
        return productTypeOne;
    }

    public void setProductTypeOne(String productTypeOne) {
        this.productTypeOne = productTypeOne;
    }

    public String getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
    }

    @Override
    public String toString() {
        return "GoodsDetailsDto{" +
                "isChecked=" + isChecked +
                ", alreadySoldCount='" + alreadySoldCount + '\'' +
                ", bannerType='" + bannerType + '\'' +
                ", bannerUrl='" + bannerUrl + '\'' +
                ", createDate='" + createDate + '\'' +
                ", freightInEveryAdd='" + freightInEveryAdd + '\'' +
                ", freightInMaxCount='" + freightInMaxCount + '\'' +
                ", freightMaxCount='" + freightMaxCount + '\'' +
                ", id='" + id + '\'' +
                ", indexActivityImage='" + indexActivityImage + '\'' +
                ", indexActivityPosition='" + indexActivityPosition + '\'' +
                ", isInIndexActivity='" + isInIndexActivity + '\'' +
                ", isfollow='" + isfollow + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", productBannerUrl='" + productBannerUrl + '\'' +
                ", productDetail='" + productDetail + '\'' +
                ", productInShopCarCount=" + productInShopCarCount +
                ", productNo='" + productNo + '\'' +
                ", productType='" + productType + '\'' +
                ", productTypeOne='" + productTypeOne + '\'' +
                ", residualStock='" + residualStock + '\'' +
                ", role='" + role + '\'' +
                ", shopId='" + shopId + '\'' +
                ", sort='" + sort + '\'' +
                ", source='" + source + '\'' +
                ", status='" + status + '\'' +
                ", stockCount='" + stockCount + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", productBannerList=" + productBannerList +
                ", productDetailList=" + productDetailList +
                ", total='" + total + '\'' +
                ", goodsTypeDto=" + goodsTypeDto +
                ", receiverAddressDto=" + receiverAddressDto +
                ", commentNum='" + commentNum + '\'' +
                '}';
    }

    public GoodsDetailsDto() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
        dest.writeString(this.alreadySoldCount);
        dest.writeString(this.bannerType);
        dest.writeString(this.bannerUrl);
        dest.writeString(this.createDate);
        dest.writeString(this.freightInEveryAdd);
        dest.writeString(this.freightInMaxCount);
        dest.writeString(this.freightMaxCount);
        dest.writeString(this.id);
        dest.writeString(this.indexActivityImage);
        dest.writeString(this.indexActivityPosition);
        dest.writeString(this.isInIndexActivity);
        dest.writeString(this.isfollow);
        dest.writeString(this.name);
        dest.writeString(this.price);
        dest.writeString(this.productBannerUrl);
        dest.writeString(this.productDetail);
        dest.writeInt(this.productInShopCarCount);
        dest.writeString(this.productNo);
        dest.writeString(this.productType);
        dest.writeString(this.productTypeOne);
        dest.writeString(this.residualStock);
        dest.writeString(this.role);
        dest.writeString(this.shopId);
        dest.writeString(this.sort);
        dest.writeString(this.source);
        dest.writeString(this.status);
        dest.writeString(this.stockCount);
        dest.writeString(this.subtitle);
        dest.writeString(this.thumbnailUrl);
        dest.writeString(this.updateDate);
        dest.writeStringList(this.productBannerList);
        dest.writeStringList(this.productDetailList);
        dest.writeString(this.total);
        dest.writeParcelable(this.goodsTypeDto, flags);
        dest.writeParcelable(this.receiverAddressDto, flags);
        dest.writeString(this.commentNum);
    }

    protected GoodsDetailsDto(Parcel in) {
        this.isChecked = in.readByte() != 0;
        this.alreadySoldCount = in.readString();
        this.bannerType = in.readString();
        this.bannerUrl = in.readString();
        this.createDate = in.readString();
        this.freightInEveryAdd = in.readString();
        this.freightInMaxCount = in.readString();
        this.freightMaxCount = in.readString();
        this.id = in.readString();
        this.indexActivityImage = in.readString();
        this.indexActivityPosition = in.readString();
        this.isInIndexActivity = in.readString();
        this.isfollow = in.readString();
        this.name = in.readString();
        this.price = in.readString();
        this.productBannerUrl = in.readString();
        this.productDetail = in.readString();
        this.productInShopCarCount = in.readInt();
        this.productNo = in.readString();
        this.productType = in.readString();
        this.productTypeOne = in.readString();
        this.residualStock = in.readString();
        this.role = in.readString();
        this.shopId = in.readString();
        this.sort = in.readString();
        this.source = in.readString();
        this.status = in.readString();
        this.stockCount = in.readString();
        this.subtitle = in.readString();
        this.thumbnailUrl = in.readString();
        this.updateDate = in.readString();
        this.productBannerList = in.createStringArrayList();
        this.productDetailList = in.createStringArrayList();
        this.total = in.readString();
        this.goodsTypeDto = in.readParcelable(HomeGoodsTypeDto.class.getClassLoader());
        this.receiverAddressDto = in.readParcelable(ReceiverAddressDto.class.getClassLoader());
        this.commentNum = in.readString();
    }

    public static final Creator<GoodsDetailsDto> CREATOR = new Creator<GoodsDetailsDto>() {
        @Override
        public GoodsDetailsDto createFromParcel(Parcel source) {
            return new GoodsDetailsDto(source);
        }

        @Override
        public GoodsDetailsDto[] newArray(int size) {
            return new GoodsDetailsDto[size];
        }
    };
}
