package com.km.rmbank.dto;

/**
 * Created by kamangkeji on 17/9/13.
 */

public class ActiveGoodsOrderDetailDto {

    /**
     * createDate : 1505273877000
     * freight : 0
     * id : 105
     * isDel : 0
     * orderNo : PO2017091311375740
     * productCount : 1
     * productName : javaceshi
     * productNo : AV20170908154548759
     * productTotalActiveValue : 2
     * productUnitActiveValue : 2
     * receiveAddress : 北京市昌平区霍营街道国风美唐511
     * receivePerson : 彭松
     * receivePersonPhone : 15631707132
     * status : 1
     * updateDate : 1505273877000
     * userId : 235
     */

    private long createDate;
    private int freight;
    private String id;
    private int isDel;
    private String orderNo;
    private int productCount;
    private String productName;
    private String productNo;
    private int productTotalActiveValue;
    private int productUnitActiveValue;
    private String receiveAddress;
    private String receivePerson;
    private String receivePersonPhone;
    private int status;
    private long updateDate;
    private String userId;
    /**
     * courierNumber : 88888888888
     * expressCompany : 极速快递
     */

    private String courierNumber;
    private String expressCompany;

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public int getFreight() {
        return freight;
    }

    public void setFreight(int freight) {
        this.freight = freight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public int getProductTotalActiveValue() {
        return productTotalActiveValue;
    }

    public void setProductTotalActiveValue(int productTotalActiveValue) {
        this.productTotalActiveValue = productTotalActiveValue;
    }

    public int getProductUnitActiveValue() {
        return productUnitActiveValue;
    }

    public void setProductUnitActiveValue(int productUnitActiveValue) {
        this.productUnitActiveValue = productUnitActiveValue;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getReceivePerson() {
        return receivePerson;
    }

    public void setReceivePerson(String receivePerson) {
        this.receivePerson = receivePerson;
    }

    public String getReceivePersonPhone() {
        return receivePersonPhone;
    }

    public void setReceivePersonPhone(String receivePersonPhone) {
        this.receivePersonPhone = receivePersonPhone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCourierNumber() {
        return courierNumber;
    }

    public void setCourierNumber(String courierNumber) {
        this.courierNumber = courierNumber;
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }
}
