package com.km.rmbank.dto;

import java.util.List;

/**
 * Created by kamangkeji on 17/4/12.
 */

public class HomeRecommendDto {

    /**
     * productReconmmendList : [{"alreadySoldCount":0,"bannerType":0,"freightMaxCount":0,"goodsImg":"/user/portrait/201704/a00561d287a14c3e82a9a20635f3a86b.png","goodsName":"高射炮","goodsPrice":100,"isInIndexActivity":0,"price":1,"productNo":"12345","recommendName":"今日推荐","status":0,"stockCount":0,"thumbnailUrl":"http://192.168.31.216:8088/img/user/portrait/201704/a00561d287a14c3e82a9a20635f3a86b.png"},{"alreadySoldCount":0,"bannerType":0,"freightMaxCount":0,"goodsImg":"/user/portrait/201704/a00561d287a14c3e82a9a20635f3a86b.png","goodsName":"苹果","goodsPrice":15341,"isInIndexActivity":0,"price":153.41,"productNo":"10000","recommendName":"今日推荐","status":0,"stockCount":0,"thumbnailUrl":"http://192.168.31.216:8088/img/user/portrait/201704/a00561d287a14c3e82a9a20635f3a86b.png"},{"alreadySoldCount":0,"bannerType":0,"freightMaxCount":0,"goodsImg":"/shop/201704/c36bc94ccbc140529387c985896a4f33.png","goodsName":"静静的静静的家","goodsPrice":1,"isInIndexActivity":0,"price":0.01,"productNo":"PN20170407072747176","recommendName":"今日推荐","status":0,"stockCount":0,"thumbnailUrl":"http://192.168.31.216:8088/img/shop/201704/c36bc94ccbc140529387c985896a4f33.png"}]
     * recommendName : 今日推荐
     */

    private String recommendName;
    private List<ProductReconmmendListBean> productReconmmendList;

    public String getRecommendName() {
        return recommendName;
    }

    public void setRecommendName(String recommendName) {
        this.recommendName = recommendName;
    }

    public List<ProductReconmmendListBean> getProductReconmmendList() {
        return productReconmmendList;
    }

    public void setProductReconmmendList(List<ProductReconmmendListBean> productReconmmendList) {
        this.productReconmmendList = productReconmmendList;
    }

    public static class ProductReconmmendListBean {
        /**
         * alreadySoldCount : 0
         * bannerType : 0
         * freightMaxCount : 0
         * goodsImg : /user/portrait/201704/a00561d287a14c3e82a9a20635f3a86b.png
         * goodsName : 高射炮
         * goodsPrice : 100
         * isInIndexActivity : 0
         * price : 1
         * productNo : 12345
         * recommendName : 今日推荐
         * status : 0
         * stockCount : 0
         * thumbnailUrl : http://192.168.31.216:8088/img/user/portrait/201704/a00561d287a14c3e82a9a20635f3a86b.png
         */

        private String alreadySoldCount;
        private int bannerType;
        private int freightMaxCount;
        private String goodsImg;
        private String goodsName;
        private String goodsPrice;
        private int isInIndexActivity;
        private String price;
        private String productNo;
        private String recommendName;
        private int status;
        private int stockCount;
        private String thumbnailUrl;

        public String getAlreadySoldCount() {
            return alreadySoldCount;
        }

        public void setAlreadySoldCount(String alreadySoldCount) {
            this.alreadySoldCount = alreadySoldCount;
        }

        public int getBannerType() {
            return bannerType;
        }

        public void setBannerType(int bannerType) {
            this.bannerType = bannerType;
        }

        public int getFreightMaxCount() {
            return freightMaxCount;
        }

        public void setFreightMaxCount(int freightMaxCount) {
            this.freightMaxCount = freightMaxCount;
        }

        public String getGoodsImg() {
            return goodsImg;
        }

        public void setGoodsImg(String goodsImg) {
            this.goodsImg = goodsImg;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGoodsPrice() {
            return goodsPrice;
        }

        public void setGoodsPrice(String goodsPrice) {
            this.goodsPrice = goodsPrice;
        }

        public int getIsInIndexActivity() {
            return isInIndexActivity;
        }

        public void setIsInIndexActivity(int isInIndexActivity) {
            this.isInIndexActivity = isInIndexActivity;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getProductNo() {
            return productNo;
        }

        public void setProductNo(String productNo) {
            this.productNo = productNo;
        }

        public String getRecommendName() {
            return recommendName;
        }

        public void setRecommendName(String recommendName) {
            this.recommendName = recommendName;
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

        public String getThumbnailUrl() {
            return thumbnailUrl;
        }

        public void setThumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
        }
    }
}
