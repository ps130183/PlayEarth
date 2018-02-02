package com.km.rmbank.dto;

/**
 * Created by kamangkeji on 17/4/13.
 */

public class MessageDto {

    /**
     * content : 打算的萨达撒
     * contentType : 2
     * createDate : 1492050548000
     * formatCreateDate : 2017-04-13 10:29:08
     * id : 2
     * status : 1
     * type : 2
     * userId : 3
     */

    private String content;
    private int contentType;
    private String createDate;
    private String formatCreateDate;
    private String id;
    private int status;
    private int type;
    private String userId;
    /**
     * createDate : 1492050548000
     * header : 推送消息
     */

    private String header;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getFormatCreateDate() {
        return formatCreateDate;
    }

    public void setFormatCreateDate(String formatCreateDate) {
        this.formatCreateDate = formatCreateDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
