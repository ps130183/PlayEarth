package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PengSong on 18/1/17.
 */

public class CircleFriendsDto implements Parcelable {

    private String id;
    private String userId;
    private String isNotPraise;
    private String portraitUrl;
    private String nickName;
    private String createTime;

    private String ruleTitle;
    private String ruleContent;

    private String rulePictureUrl;//图片，以#号分割
    private List<String> forumImgContents;

    private List<CircleFriendsCommentDto> ruleCommentsList;
    private int praise;
    private List<CircleFriendsCommentDto> moreForumCommentDtos;
    private int commentNumberStatus;
    private int commentsNumber;

    private String updateDate;
    private long createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIsNotPraise() {
        return isNotPraise;
    }

    public void setIsNotPraise(String isNotPraise) {
        this.isNotPraise = isNotPraise;
    }

    public String getPortraitUrl() {
        return portraitUrl;
    }

    public void setPortraitUrl(String portraitUrl) {
        this.portraitUrl = portraitUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCreateTime() {
        long curData = System.currentTimeMillis();
        long releaseTime = curData - createDate;
        long minute = (releaseTime / 1000 / 60);
        long hour = minute / 60;
        long day = hour / 24;
        long month = day / 30;
        long year = month / 12;
        if (minute < 60){
            createTime = minute + "分钟前发布";
        } else if (minute >= 60 && hour < 24){
            createTime = hour + "小时前发布";
        } else if (hour >= 24 && day < 30){
            createTime = day + "天前发布";
        } else if (day >= 30 && month < 12){
            createTime = month + "个月前发布";
        } else {
            createTime = year + "年前发布";
        }
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRuleTitle() {
        return ruleTitle;
    }

    public void setRuleTitle(String ruleTitle) {
        this.ruleTitle = ruleTitle;
    }

    public String getRuleContent() {
        return ruleContent;
    }

    public void setRuleContent(String ruleContent) {
        this.ruleContent = ruleContent;
    }

    public String getRulePictureUrl() {
        return rulePictureUrl;
    }

    public void setRulePictureUrl(String rulePictureUrl) {
        this.rulePictureUrl = rulePictureUrl;
    }

    public List<String> getForumImgContents() {
        if (!TextUtils.isEmpty(rulePictureUrl) && forumImgContents == null){
            forumImgContents = new ArrayList<>();
            String[] images = rulePictureUrl.split("#");
            for (String image : images){
                forumImgContents.add(image);
            }
        }
        return forumImgContents;
    }

    public void setForumImgContents(List<String> forumImgContents) {
        this.forumImgContents = forumImgContents;
        if (forumImgContents != null && forumImgContents.size() > 0){
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < forumImgContents.size(); i++){
                buffer.append(forumImgContents.get(i));
                if (i < forumImgContents.size() - 1){
                    buffer.append("#");
                }
            }
            setRulePictureUrl(buffer.toString());
        }
    }

    public List<CircleFriendsCommentDto> getRuleCommentsList() {
        if (ruleCommentsList == null){
            ruleCommentsList = new ArrayList<>();
        }
        return ruleCommentsList;
    }

    public void setRuleCommentsList(List<CircleFriendsCommentDto> ruleCommentsList) {
        this.ruleCommentsList = ruleCommentsList;
    }

    public int getPraise() {
        return praise;
    }

    public void setPraise(int praise) {
        this.praise = praise;
    }

    public List<CircleFriendsCommentDto> getMoreForumCommentDtos() {
        return moreForumCommentDtos;
    }

    public void setMoreForumCommentDtos(List<CircleFriendsCommentDto> moreForumCommentDtos) {
        this.moreForumCommentDtos = moreForumCommentDtos;
    }

    public int getCommentNumberStatus() {
        return commentNumberStatus;
    }

    public void setCommentNumberStatus(int commentNumberStatus) {
        this.commentNumberStatus = commentNumberStatus;
    }

    public int getCommentsNumber() {
        return commentsNumber;
    }

    public void setCommentsNumber(int commentsNumber) {
        this.commentsNumber = commentsNumber;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public boolean isEmpty() {
        if(TextUtils.isEmpty(ruleTitle)){
            return true;
        }

        if (TextUtils.isEmpty(ruleContent) && TextUtils.isEmpty(rulePictureUrl)){
            return true;
        }
        return false;
    }

    public static class CircleFriendsCommentDto implements Parcelable {
        private String ruleId;
        private String userId;
        private String nickName;
        private String ruleCommentContent;
        private String createData;

        public CircleFriendsCommentDto() {
        }

        public CircleFriendsCommentDto(String nickName, String commentContent) {
            this.nickName = nickName;
            this.ruleCommentContent = commentContent;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getRuleCommentContent() {
            return ruleCommentContent;
        }

        public void setRuleCommentContent(String ruleCommentContent) {
            this.ruleCommentContent = ruleCommentContent;
        }

        public String getRuleId() {
            return ruleId;
        }

        public void setRuleId(String ruleId) {
            this.ruleId = ruleId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getCreateData() {
            return createData;
        }

        public void setCreateData(String createData) {
            this.createData = createData;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.ruleId);
            dest.writeString(this.userId);
            dest.writeString(this.nickName);
            dest.writeString(this.ruleCommentContent);
            dest.writeString(this.createData);
        }

        protected CircleFriendsCommentDto(Parcel in) {
            this.ruleId = in.readString();
            this.userId = in.readString();
            this.nickName = in.readString();
            this.ruleCommentContent = in.readString();
            this.createData = in.readString();
        }

        public static final Parcelable.Creator<CircleFriendsCommentDto> CREATOR = new Parcelable.Creator<CircleFriendsCommentDto>() {
            @Override
            public CircleFriendsCommentDto createFromParcel(Parcel source) {
                return new CircleFriendsCommentDto(source);
            }

            @Override
            public CircleFriendsCommentDto[] newArray(int size) {
                return new CircleFriendsCommentDto[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.userId);
        dest.writeString(this.isNotPraise);
        dest.writeString(this.portraitUrl);
        dest.writeString(this.nickName);
        dest.writeString(this.createTime);
        dest.writeString(this.ruleTitle);
        dest.writeString(this.ruleContent);
        dest.writeString(this.rulePictureUrl);
        dest.writeStringList(this.forumImgContents);
        dest.writeTypedList(this.ruleCommentsList);
        dest.writeInt(this.praise);
        dest.writeTypedList(this.moreForumCommentDtos);
        dest.writeInt(this.commentNumberStatus);
        dest.writeInt(this.commentsNumber);
        dest.writeString(this.updateDate);
        dest.writeLong(this.createDate);
    }

    public CircleFriendsDto() {
    }

    protected CircleFriendsDto(Parcel in) {
        this.id = in.readString();
        this.userId = in.readString();
        this.isNotPraise = in.readString();
        this.portraitUrl = in.readString();
        this.nickName = in.readString();
        this.createTime = in.readString();
        this.ruleTitle = in.readString();
        this.ruleContent = in.readString();
        this.rulePictureUrl = in.readString();
        this.forumImgContents = in.createStringArrayList();
        this.ruleCommentsList = in.createTypedArrayList(CircleFriendsCommentDto.CREATOR);
        this.praise = in.readInt();
        this.moreForumCommentDtos = in.createTypedArrayList(CircleFriendsCommentDto.CREATOR);
        this.commentNumberStatus = in.readInt();
        this.commentsNumber = in.readInt();
        this.updateDate = in.readString();
        this.createDate = in.readLong();
    }

    public static final Parcelable.Creator<CircleFriendsDto> CREATOR = new Parcelable.Creator<CircleFriendsDto>() {
        @Override
        public CircleFriendsDto createFromParcel(Parcel source) {
            return new CircleFriendsDto(source);
        }

        @Override
        public CircleFriendsDto[] newArray(int size) {
            return new CircleFriendsDto[size];
        }
    };
}
