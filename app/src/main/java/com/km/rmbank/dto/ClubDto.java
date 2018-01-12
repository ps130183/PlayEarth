package com.km.rmbank.dto;

/**
 * Created by PengSong on 17/12/25.
 */

public class ClubDto {

    /**
     * avatarUrl : http://wanzhuandiqiu.com:8080/wzdq/Aiyg/aiygImage/2017/11/1e5639f498e04f11899230a2f05aecd7.jpg
     * clubLogo : http://wanzhuandiqiu.com/img/shop/201709/fa57e229366e43ea849f1fcbcaf6cf96.png
     * clubName : 玩转地球-商旅学院
     * id : 200
     * status : 1
     * title : 路演事业合伙人内部密训系列之赚钱教练
     * videoName :
     * viewCount : 8566
     */

    private String avatarUrl;
    private String clubLogo;
    private String clubName;
    private String id;
    private String status;
    private String title;
    private String videoName;
    private int viewCount;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getClubLogo() {
        return clubLogo;
    }

    public void setClubLogo(String clubLogo) {
        this.clubLogo = clubLogo;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }
}
