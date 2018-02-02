package com.km.rmbank.dto;

/**
 * Created by kamangkeji on 17/7/27.
 */

public class ServiceDto {
    private String service;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    @Override
    public String toString() {
        return "ServiceDto{" +
                "service='" + service + '\'' +
                '}';
    }
}
