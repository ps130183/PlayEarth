package com.km.rmbank.dto;

/**
 * Created by kamangkeji on 17/4/15.
 */

public class IntegralDetailsDto {

    /**
     * amount : 1
     * content : 您购买商品时使用积分抵现
     * createDate : 1492252378000
     * date : 2017-04-15
     * end : 0
     * id : 15
     * start : 0
     * tradeDirection : 2
     * userId : 3
     */

    private int amount;
    private String content;
    private long createDate;
    private String date;
    private int end;
    private String id;
    private int start;
    private String tradeDirection;
    private String userId;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getTradeDirection() {
        return tradeDirection;
    }

    public void setTradeDirection(String tradeDirection) {
        this.tradeDirection = tradeDirection;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
