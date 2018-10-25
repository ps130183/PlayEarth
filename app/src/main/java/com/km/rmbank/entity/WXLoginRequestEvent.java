package com.km.rmbank.entity;

/**
 * Created by PengSong on 18/10/22.
 */

public class WXLoginRequestEvent {
    String code;

    public WXLoginRequestEvent(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
