package com.km.rmbank.dto;

/**
 * Created by kamangkeji on 17/4/15.
 */

public class ShareDto {

    /**
     * sharePicUrl : localhost:8080/Aiyg/images/logoEarth.png
     * content : 玩转地球合伙人会议如期举行，在这里您可以找到您的合伙人也能结识您所需要的生意伙伴，来吧~
     * title : 玩转地球商旅学院 — 在等你哦
     * pageUrl : http://bobytest.com/member/share/view?code=Mw==
     */

    private String sharePicUrl;
    private String content;
    private String title;
    private String pageUrl;

    public String getSharePicUrl() {
        return sharePicUrl;
    }

    public void setSharePicUrl(String sharePicUrl) {
        this.sharePicUrl = sharePicUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }
}
