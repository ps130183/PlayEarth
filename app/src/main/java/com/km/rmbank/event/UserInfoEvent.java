package com.km.rmbank.event;

/**
 * Created by PengSong on 18/4/17.
 */

public class UserInfoEvent {
    private int infoType;
    private String content;

    public UserInfoEvent(int infoType, String content) {
        this.infoType = infoType;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public int getInfoType() {
        return infoType;
    }
}
