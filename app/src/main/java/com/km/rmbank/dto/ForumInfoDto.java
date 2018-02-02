package com.km.rmbank.dto;

/**
 * Created by kamangkeji on 17/8/16.
 */

public class ForumInfoDto {


    /**
     * commentsNumber : 4
     * praises : 7
     * posts : 5
     */

    private int commentsNumber;
    private int praises;
    private int posts;

    public int getCommentsNumber() {
        return commentsNumber;
    }

    public void setCommentsNumber(int commentsNumber) {
        this.commentsNumber = commentsNumber;
    }

    public int getPraises() {
        return praises;
    }

    public void setPraises(int praises) {
        this.praises = praises;
    }

    public int getPosts() {
        return posts;
    }

    public void setPosts(int posts) {
        this.posts = posts;
    }
}
