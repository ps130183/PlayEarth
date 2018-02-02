package com.km.rmbank.dto;

/**
 * Created by kamangkeji on 17/8/10.
 */

public class JPushDto {
    private int type;
    private String id;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "JPushDto{" +
                "type=" + type +
                ", id='" + id + '\'' +
                '}';
    }
}
