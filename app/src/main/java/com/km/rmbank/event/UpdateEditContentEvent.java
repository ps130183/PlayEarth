package com.km.rmbank.event;

/**
 * Created by PengSong on 18/6/25.
 */

public class UpdateEditContentEvent {
    private int editContentType;
    private String content;
    private int position;

    public UpdateEditContentEvent(int editContentType, String content, int position) {
        this.editContentType = editContentType;
        this.content = content;
        this.position = position;
    }

    public int getEditContentType() {
        return editContentType;
    }

    public void setEditContentType(int editContentType) {
        this.editContentType = editContentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
