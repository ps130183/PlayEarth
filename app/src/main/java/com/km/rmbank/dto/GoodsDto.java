package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kamangkeji on 17/3/14.
 */

public class GoodsDto implements Parcelable {

    private boolean isChecked;
    /**
     * alreadySoldCount : 20
     * avgStartLevel : 3
     * bannerType : 1
     * bannerUrl : 4144444
     * freightInEveryAdd : 1524
     * freightInMaxCount : 12341
     * freightMaxCount : 0
     * id : 10000
     * indexActivityImage : 12
     * indexActivityPosition : 1212
     * isInIndexActivity : 0
     * longitude : 12
     * name : 苹果
     * price : 153.41
     * productBannerUrl : 1414
     * productDetail : 我们的亚洲
     * productInShopCarCount : 0
     * productNo : 10000
     * productParams : 我发的授课计划
     * residualStock : 80
     * shopId : 1
     * sort : 0
     * source : 1
     * status : 1
     * stockCount : 100
     * subtitle : 水果
     * thumbnailUrl : http://122.114.162.140:8889/image131141
     */

    private String access;
    private int alreadySoldCount;
    private String avgStartLevel;
    private int bannerType;
    private String bannerUrl;
    private int freightInEveryAdd;
    private int freightInMaxCount;
    private int freightMaxCount;
    private String id;
    private String indexActivityImage;
    private int indexActivityPosition;
    private int isInIndexActivity;
    private String longitude;
    private String name;
    private double price;
    private int role;
    private String productBannerUrl;
    private String productDetail;
    private int productInShopCarCount;
    private String productNo;
    private String productParams;
    private int residualStock;
    private String shopId;
    private int sort;
    private int source;
    private int status;
    private int stockCount;
    private String subtitle;
    private String thumbnailUrl;
    private int type;

    public GoodsDto() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getAlreadySoldCount() {
        return alreadySoldCount;
    }

    public void setAlreadySoldCount(int alreadySoldCount) {
        this.alreadySoldCount = alreadySoldCount;
    }

    public String getAvgStartLevel() {
        return avgStartLevel;
    }

    public void setAvgStartLevel(String avgStartLevel) {
        this.avgStartLevel = avgStartLevel;
    }

    public int getBannerType() {
        return bannerType;
    }

    public void setBannerType(int bannerType) {
        this.bannerType = bannerType;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public int getFreightInEveryAdd() {
        return freightInEveryAdd;
    }

    public void setFreightInEveryAdd(int freightInEveryAdd) {
        this.freightInEveryAdd = freightInEveryAdd;
    }

    public int getFreightInMaxCount() {
        return freightInMaxCount;
    }

    public void setFreightInMaxCount(int freightInMaxCount) {
        this.freightInMaxCount = freightInMaxCount;
    }

    public int getFreightMaxCount() {
        return freightMaxCount;
    }

    public void setFreightMaxCount(int freightMaxCount) {
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

    public int getIndexActivityPosition() {
        return indexActivityPosition;
    }

    public void setIndexActivityPosition(int indexActivityPosition) {
        this.indexActivityPosition = indexActivityPosition;
    }

    public int getIsInIndexActivity() {
        return isInIndexActivity;
    }

    public void setIsInIndexActivity(int isInIndexActivity) {
        this.isInIndexActivity = isInIndexActivity;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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

    public String getProductParams() {
        return productParams;
    }

    public void setProductParams(String productParams) {
        this.productParams = productParams;
    }

    public int getResidualStock() {
        return residualStock;
    }

    public void setResidualStock(int residualStock) {
        this.residualStock = residualStock;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStockCount() {
        return stockCount;
    }

    public void setStockCount(int stockCount) {
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


    @Override
    public String toString() {
        return "GoodsDto{" +
                "isChecked=" + isChecked +
                ", access='" + access + '\'' +
                ", alreadySoldCount=" + alreadySoldCount +
                ", avgStartLevel='" + avgStartLevel + '\'' +
                ", bannerType=" + bannerType +
                ", bannerUrl='" + bannerUrl + '\'' +
                ", freightInEveryAdd=" + freightInEveryAdd +
                ", freightInMaxCount=" + freightInMaxCount +
                ", freightMaxCount=" + freightMaxCount +
                ", id='" + id + '\'' +
                ", indexActivityImage='" + indexActivityImage + '\'' +
                ", indexActivityPosition=" + indexActivityPosition +
                ", isInIndexActivity=" + isInIndexActivity +
                ", longitude='" + longitude + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", role=" + role +
                ", productBannerUrl='" + productBannerUrl + '\'' +
                ", productDetail='" + productDetail + '\'' +
                ", productInShopCarCount=" + productInShopCarCount +
                ", productNo='" + productNo + '\'' +
                ", productParams='" + productParams + '\'' +
                ", residualStock=" + residualStock +
                ", shopId='" + shopId + '\'' +
                ", sort=" + sort +
                ", source=" + source +
                ", status=" + status +
                ", stockCount=" + stockCount +
                ", subtitle='" + subtitle + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", type=" + type +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
        dest.writeString(this.access);
        dest.writeInt(this.alreadySoldCount);
        dest.writeString(this.avgStartLevel);
        dest.writeInt(this.bannerType);
        dest.writeString(this.bannerUrl);
        dest.writeInt(this.freightInEveryAdd);
        dest.writeInt(this.freightInMaxCount);
        dest.writeInt(this.freightMaxCount);
        dest.writeString(this.id);
        dest.writeString(this.indexActivityImage);
        dest.writeInt(this.indexActivityPosition);
        dest.writeInt(this.isInIndexActivity);
        dest.writeString(this.longitude);
        dest.writeString(this.name);
        dest.writeDouble(this.price);
        dest.writeInt(this.role);
        dest.writeString(this.productBannerUrl);
        dest.writeString(this.productDetail);
        dest.writeInt(this.productInShopCarCount);
        dest.writeString(this.productNo);
        dest.writeString(this.productParams);
        dest.writeInt(this.residualStock);
        dest.writeString(this.shopId);
        dest.writeInt(this.sort);
        dest.writeInt(this.source);
        dest.writeInt(this.status);
        dest.writeInt(this.stockCount);
        dest.writeString(this.subtitle);
        dest.writeString(this.thumbnailUrl);
        dest.writeInt(this.type);
    }

    protected GoodsDto(Parcel in) {
        this.isChecked = in.readByte() != 0;
        this.access = in.readString();
        this.alreadySoldCount = in.readInt();
        this.avgStartLevel = in.readString();
        this.bannerType = in.readInt();
        this.bannerUrl = in.readString();
        this.freightInEveryAdd = in.readInt();
        this.freightInMaxCount = in.readInt();
        this.freightMaxCount = in.readInt();
        this.id = in.readString();
        this.indexActivityImage = in.readString();
        this.indexActivityPosition = in.readInt();
        this.isInIndexActivity = in.readInt();
        this.longitude = in.readString();
        this.name = in.readString();
        this.price = in.readDouble();
        this.role = in.readInt();
        this.productBannerUrl = in.readString();
        this.productDetail = in.readString();
        this.productInShopCarCount = in.readInt();
        this.productNo = in.readString();
        this.productParams = in.readString();
        this.residualStock = in.readInt();
        this.shopId = in.readString();
        this.sort = in.readInt();
        this.source = in.readInt();
        this.status = in.readInt();
        this.stockCount = in.readInt();
        this.subtitle = in.readString();
        this.thumbnailUrl = in.readString();
        this.type = in.readInt();
    }

    public static final Creator<GoodsDto> CREATOR = new Creator<GoodsDto>() {
        @Override
        public GoodsDto createFromParcel(Parcel source) {
            return new GoodsDto(source);
        }

        @Override
        public GoodsDto[] newArray(int size) {
            return new GoodsDto[size];
        }
    };
}
