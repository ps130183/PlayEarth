package com.km.rmbank.event;

/**
 * Created by kamangkeji on 17/8/16.
 */

public class MyForumCommentEvent {
    private int position;
    private From from;
    private From to;
    private boolean isRequest;
    private String newComment;

    public MyForumCommentEvent(boolean isRequest) {
        this.isRequest = isRequest;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public From getFrom() {
        return from;
    }

    public void setFrom(From from) {
        this.from = from;
    }

    public boolean isRequest() {
        return isRequest;
    }

    public void setRequest(boolean request) {
        isRequest = request;
    }

    public String getNewComment() {
        return newComment;
    }

    public void setNewComment(String newComment) {
        this.newComment = newComment;
    }

    public From getTo() {
        return to;
    }

    public void setTo(From to) {
        this.to = to;
    }

    public enum From{
        MYFORUM,LIKEORCOMMENT;
    }
}
