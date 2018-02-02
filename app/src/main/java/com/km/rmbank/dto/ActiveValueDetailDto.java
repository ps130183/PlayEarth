package com.km.rmbank.dto;

/**
 * Created by kamangkeji on 17/9/11.
 */

public class ActiveValueDetailDto {

    /**
     * activeContent : 报名呼伦贝尔大草原（测试）活动
     * activeValue : +3
     * createDate : 1505110572000
     */

    private String activeContent;
    private String activeValue;
    private long createDate;

    public String getActiveContent() {
        return activeContent;
    }

    public void setActiveContent(String activeContent) {
        this.activeContent = activeContent;
    }

    public String getActiveValue() {
        return activeValue;
    }

    public void setActiveValue(String activeValue) {
        this.activeValue = activeValue;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }
}
