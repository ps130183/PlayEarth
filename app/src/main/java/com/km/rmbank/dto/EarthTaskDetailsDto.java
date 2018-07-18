package com.km.rmbank.dto;

import com.km.rmbank.R;
import com.ps.mrcyclerview.delegate.ItemDelegate;

/**
 * Created by PengSong on 18/7/12.
 */

public class EarthTaskDetailsDto implements ItemDelegate {


    /**
     * amount : 2
     * ballWritId : 1
     * createDate : 1531400055000
     * describe : APP签到领取球票
     * type : 1
     * userId : 4124
     */

    private int amount;
    private String ballWritId;
    private long createDate;
    private String describe;
    private String type;
    private String userId;

    @Override
    public int getItemViewRes() {
        return R.layout.item_earth_task_details;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getBallWritId() {
        return ballWritId;
    }

    public void setBallWritId(String ballWritId) {
        this.ballWritId = ballWritId;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
