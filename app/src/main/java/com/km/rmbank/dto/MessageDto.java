package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.km.rmbank.R;
import com.ps.mrcyclerview.delegate.ItemDelegate;

/**
 * Created by kamangkeji on 17/4/13.
 */

public class MessageDto implements ItemDelegate, Parcelable {

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

    private String title;
    private String content;
    private int contentType;
    private long createDate;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
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

    @Override
    public int getItemViewRes() {
        return R.layout.item_rv_home_message;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeInt(this.contentType);
        dest.writeLong(this.createDate);
        dest.writeString(this.formatCreateDate);
        dest.writeString(this.id);
        dest.writeInt(this.status);
        dest.writeInt(this.type);
        dest.writeString(this.userId);
        dest.writeString(this.header);
    }

    public MessageDto() {
    }

    protected MessageDto(Parcel in) {
        this.title = in.readString();
        this.content = in.readString();
        this.contentType = in.readInt();
        this.createDate = in.readLong();
        this.formatCreateDate = in.readString();
        this.id = in.readString();
        this.status = in.readInt();
        this.type = in.readInt();
        this.userId = in.readString();
        this.header = in.readString();
    }

    public static final Parcelable.Creator<MessageDto> CREATOR = new Parcelable.Creator<MessageDto>() {
        @Override
        public MessageDto createFromParcel(Parcel source) {
            return new MessageDto(source);
        }

        @Override
        public MessageDto[] newArray(int size) {
            return new MessageDto[size];
        }
    };
}
