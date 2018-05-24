package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.zaihuishou.expandablerecycleradapter.model.ExpandableListItem;

import java.util.List;

/**
 * Created by kamangkeji on 17/4/19.
 */

public class MyTeamDto extends Object implements ExpandableListItem {

    /**
     * memberDtoList : [{"age":"0","birthday":567964800000,"createDate":1492582613000,"gender":"0","id":"16","mobilePhone":"15303202521","nickName":"15303202521","parentId":"3","personalizedSignature":"这家伙很懒没有留下个人介绍","portraitUrl":"/default/default_icon_head.png","roleId":"4","roleUpdateDate":1492582613000,"total":0,"type":"1","updateDate":1492582613000}]
     * num : 1
     * roleName : 普通用户
     */

    private int num;
    private String roleName;
    private List<MemberDtoListBean> memberDtoList;
    private boolean mExpanded = true;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<MemberDtoListBean> getMemberDtoList() {
        return memberDtoList;
    }

    public void setMemberDtoList(List<MemberDtoListBean> memberDtoList) {
        this.memberDtoList = memberDtoList;
    }

    @Override
    public List<?> getChildItemList() {
        return memberDtoList;
    }

    @Override
    public boolean isExpanded() {
        return mExpanded;
    }

    @Override
    public void setExpanded(boolean isExpanded) {
        mExpanded = isExpanded;
    }

    public static class MemberDtoListBean implements Parcelable {
        /**
         * age : 0
         * birthday : 567964800000
         * createDate : 1492582613000
         * gender : 0
         * id : 16
         * mobilePhone : 15303202521
         * nickName : 15303202521
         * parentId : 3
         * personalizedSignature : 这家伙很懒没有留下个人介绍
         * portraitUrl : /default/default_icon_head.png
         * roleId : 4
         * roleUpdateDate : 1492582613000
         * total : 0
         * type : 1
         * updateDate : 1492582613000
         */

        private String age;
        private String birthday;
        private String createDate;
        private String gender;
        private String id;
        private String mobilePhone;
        private String name;
        private String nickName;
        private String parentId;
        private String personalizedSignature;
        private String portraitUrl;
        private String roleId;
        private String roleUpdateDate;
        private String total;
        private String type;
        private String updateDate;
        private String referrerPhone;
        private String source;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMobilePhone() {
            return mobilePhone;
        }

        public void setMobilePhone(String mobilePhone) {
            this.mobilePhone = mobilePhone;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getPersonalizedSignature() {
            return personalizedSignature;
        }

        public void setPersonalizedSignature(String personalizedSignature) {
            this.personalizedSignature = personalizedSignature;
        }

        public String getPortraitUrl() {
            return portraitUrl;
        }

        public void setPortraitUrl(String portraitUrl) {
            this.portraitUrl = portraitUrl;
        }

        public String getRoleId() {
            return roleId;
        }

        public void setRoleId(String roleId) {
            this.roleId = roleId;
        }

        public String getRoleUpdateDate() {
            return roleUpdateDate;
        }

        public void setRoleUpdateDate(String roleUpdateDate) {
            this.roleUpdateDate = roleUpdateDate;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public String getReferrerPhone() {
            return referrerPhone;
        }

        public void setReferrerPhone(String referrerPhone) {
            this.referrerPhone = referrerPhone;
        }

        public MemberDtoListBean() {
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.age);
            dest.writeString(this.birthday);
            dest.writeString(this.createDate);
            dest.writeString(this.gender);
            dest.writeString(this.id);
            dest.writeString(this.mobilePhone);
            dest.writeString(this.name);
            dest.writeString(this.nickName);
            dest.writeString(this.parentId);
            dest.writeString(this.personalizedSignature);
            dest.writeString(this.portraitUrl);
            dest.writeString(this.roleId);
            dest.writeString(this.roleUpdateDate);
            dest.writeString(this.total);
            dest.writeString(this.type);
            dest.writeString(this.updateDate);
            dest.writeString(this.referrerPhone);
            dest.writeString(this.source);
        }

        protected MemberDtoListBean(Parcel in) {
            this.age = in.readString();
            this.birthday = in.readString();
            this.createDate = in.readString();
            this.gender = in.readString();
            this.id = in.readString();
            this.mobilePhone = in.readString();
            this.name = in.readString();
            this.nickName = in.readString();
            this.parentId = in.readString();
            this.personalizedSignature = in.readString();
            this.portraitUrl = in.readString();
            this.roleId = in.readString();
            this.roleUpdateDate = in.readString();
            this.total = in.readString();
            this.type = in.readString();
            this.updateDate = in.readString();
            this.referrerPhone = in.readString();
            this.source = in.readString();
        }

        public static final Creator<MemberDtoListBean> CREATOR = new Creator<MemberDtoListBean>() {
            @Override
            public MemberDtoListBean createFromParcel(Parcel source) {
                return new MemberDtoListBean(source);
            }

            @Override
            public MemberDtoListBean[] newArray(int size) {
                return new MemberDtoListBean[size];
            }
        };
    }
}
