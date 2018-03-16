package com.km.rmbank.event;

/**
 * Created by PengSong on 18/3/16.
 */

public class AttentionClubEvent {
    private String clubId;
    private boolean isAttention;

    public AttentionClubEvent(String clubId, boolean isAttention) {
        this.clubId = clubId;
        this.isAttention = isAttention;
    }

    public String getClubId() {
        return clubId;
    }

    public boolean isAttention() {
        return isAttention;
    }
}
