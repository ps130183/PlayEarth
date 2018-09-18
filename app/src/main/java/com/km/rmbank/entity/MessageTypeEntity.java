package com.km.rmbank.entity;

import com.km.rmbank.R;
import com.ps.mrcyclerview.delegate.ItemDelegate;

/**
 * Created by PengSong on 18/9/7.
 */

public class MessageTypeEntity implements ItemDelegate {

    private String typeName;
    private int imageRes;
    private long messageTime;
    private String messageContent;
    private boolean isNewMessage;

    public MessageTypeEntity(String typeName, int imageRes) {
        this.typeName = typeName;
        this.imageRes = imageRes;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public boolean isNewMessage() {
        return isNewMessage;
    }

    public void setNewMessage(boolean newMessage) {
        isNewMessage = newMessage;
    }

    @Override
    public int getItemViewRes() {
        return R.layout.item_message_type;
    }
}
