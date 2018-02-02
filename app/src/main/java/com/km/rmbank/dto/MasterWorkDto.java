package com.km.rmbank.dto;

/**
 * Created by PengSong on 17/10/17.
 */

public class MasterWorkDto {

    /**
     * createDate : 1508223539000
     * headings : 男神成长记
     * id : 6
     * macaId : 3
     * money : 5.0
     * quantity : 0
     * updateDate : 1508223539000
     */

    private long createDate;
    private String headings;
    private String id;
    private String macaId;
    private double money;
    private int quantity;
    private long updateDate;

    private boolean isChecked;

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
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

    public String getMacaId() {
        return macaId;
    }

    public void setMacaId(String macaId) {
        this.macaId = macaId;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
