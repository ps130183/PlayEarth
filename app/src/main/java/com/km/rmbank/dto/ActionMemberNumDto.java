package com.km.rmbank.dto;

/**
 * Created by kamangkeji on 17/8/9.
 */

public class ActionMemberNumDto {
    private String registrationNumber;

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    @Override
    public String toString() {
        return "ActionMemberNumDto{" +
                "registrationNumber='" + registrationNumber + '\'' +
                '}';
    }
}
