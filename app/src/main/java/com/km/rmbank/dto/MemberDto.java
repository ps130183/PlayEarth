package com.km.rmbank.dto;

/**
 * Created by PengSong on 18/1/27.
 */

public class MemberDto {

    /**
     * id : 10
     * memberId : 2
     * memberMoney : 2000
     * memberName : 玩家合伙人
     * smemberRecommend : 这里有你需要的
     快过来吧
     我们能给你很多好玩的
     和好吃的
     * scene : 1
     */

    private String id;
    private String memberId;
    private String memberMoney;
    private String memberName;
    private String smemberRecommend;
    private String scene;

    private boolean isChecked = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberMoney() {
        return memberMoney;
    }

    public void setMemberMoney(String memberMoney) {
        this.memberMoney = memberMoney;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getSmemberRecommend() {
        return smemberRecommend;
    }

    public void setSmemberRecommend(String smemberRecommend) {
        this.smemberRecommend = smemberRecommend;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
