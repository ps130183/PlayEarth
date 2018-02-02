package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;


import com.km.rmbank.base.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamangkeji on 17/8/11.
 */

public class ForumDto extends BaseEntity implements Parcelable {

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

    private List<ForumCommentDto> ruleCommentsList;
    private int praise;
    private List<ForumCommentDto> moreForumCommentDtos;
    private int commentNumberStatus;
    private int commentsNumber;

    private String updateDate;
    private long createDate;

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

    public String getRuleContent() {
        return ruleContent;
    }

    public void setRuleContent(String ruleContent) {
        this.ruleContent = ruleContent;
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

    public List<ForumCommentDto> getRuleCommentsList() {
        if (ruleCommentsList == null){
            ruleCommentsList = new ArrayList<>();
        }
        return ruleCommentsList;
    }

    public void setRuleCommentsList(List<ForumCommentDto> ruleCommentsList) {
        this.ruleCommentsList = ruleCommentsList;
    }

    public List<ForumCommentDto> getMoreForumCommentDtos() {
        return moreForumCommentDtos;
    }

    public void setMoreForumCommentDtos(List<ForumCommentDto> moreForumCommentDtos) {
        this.moreForumCommentDtos = moreForumCommentDtos;
    }

    public String getRuleTitle() {
        return ruleTitle;
    }

    public void setRuleTitle(String ruleTitle) {
        this.ruleTitle = ruleTitle;
    }

    public String getRulePictureUrl() {
        return rulePictureUrl;
    }

    public void setRulePictureUrl(String rulePictureUrl) {
        this.rulePictureUrl = rulePictureUrl;
    }

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

    public int getPraise() {
        return praise;
    }

    public void setPraise(int praise) {
        this.praise = praise;
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

    public int getCommentNumberStatus() {
        return commentNumberStatus;
    }

    public void setCommentNumberStatus(int commentNumberStatus) {
        this.commentNumberStatus = commentNumberStatus;
    }

    @Override
    public boolean isEmpty() {
        if(TextUtils.isEmpty(ruleTitle)){
            return true;
        }

        if (TextUtils.isEmpty(ruleContent) && TextUtils.isEmpty(rulePictureUrl)){
            return true;
        }
        return false;
    }

    public static class ForumCommentDto implements Parcelable {
        private String ruleId;
        private String userId;
        private String nickName;
        private String ruleCommentContent;
        private String createData;

        public ForumCommentDto() {
        }

        public ForumCommentDto(String nickName, String commentContent) {
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

        protected ForumCommentDto(Parcel in) {
            this.ruleId = in.readString();
            this.userId = in.readString();
            this.nickName = in.readString();
            this.ruleCommentContent = in.readString();
            this.createData = in.readString();
        }

        public static final Parcelable.Creator<ForumCommentDto> CREATOR = new Parcelable.Creator<ForumCommentDto>() {
            @Override
            public ForumCommentDto createFromParcel(Parcel source) {
                return new ForumCommentDto(source);
            }

            @Override
            public ForumCommentDto[] newArray(int size) {
                return new ForumCommentDto[size];
            }
        };
    }

    public ForumDto() {
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

    protected ForumDto(Parcel in) {
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
        this.ruleCommentsList = in.createTypedArrayList(ForumCommentDto.CREATOR);
        this.praise = in.readInt();
        this.moreForumCommentDtos = in.createTypedArrayList(ForumCommentDto.CREATOR);
        this.commentNumberStatus = in.readInt();
        this.commentsNumber = in.readInt();
        this.updateDate = in.readString();
        this.createDate = in.readLong();
    }

    public static final Creator<ForumDto> CREATOR = new Creator<ForumDto>() {
        @Override
        public ForumDto createFromParcel(Parcel source) {
            return new ForumDto(source);
        }

        @Override
        public ForumDto[] newArray(int size) {
            return new ForumDto[size];
        }
    };
}
