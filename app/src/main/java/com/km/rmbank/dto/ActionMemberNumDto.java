package com.km.rmbank.dto;

import java.util.List;

/**
 * Created by kamangkeji on 17/8/9.
 */

public class ActionMemberNumDto {
    private String registrationNumber;
    private List<ActionMemberDto> actionMemberDtos;

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public List<ActionMemberDto> getActionMemberDtos() {
        return actionMemberDtos;
    }

    public void setActionMemberDtos(List<ActionMemberDto> actionMemberDtos) {
        this.actionMemberDtos = actionMemberDtos;
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
