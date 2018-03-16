package com.km.rmbank.event;

/**
 * Created by PengSong on 18/3/16.
 */

public class ApplyActionEvent {
    private String actionId;
    private String type;

    public ApplyActionEvent(String actionId) {
        this.actionId = actionId;
    }

    public ApplyActionEvent(String actionId, String type) {
        this.actionId = actionId;
        this.type = type;
    }

    public String getActionId() {
        return actionId;
    }

    public String getType() {
        return type;
    }
}
