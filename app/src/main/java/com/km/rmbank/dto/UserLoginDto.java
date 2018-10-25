package com.km.rmbank.dto;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;

/**
 * Created by PengSong on 18/1/18.
 */

public class UserLoginDto implements Parcelable {


    /**
     * mobilePhone : 15631707132
     * token : d19c3db506b44ce096456a3a08f2d6bf
     * name : 彭松
     * HXpwd : 577ef1154f3240ad5b9b413aa7346a1e
     * roleId : 4
     */

    private String mobilePhone;
    private String token;
    private String name;
    private String HXpwd;
    private String roleId;

    private String unionid;//微信登录用
    private String headimgurl;
    private String nickname;

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHXpwd() {
        return HXpwd;
    }

    public void setHXpwd(String HXpwd) {
        this.HXpwd = HXpwd;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public boolean isEmpty(){
        return TextUtils.isEmpty(token);
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 将登录信息保存到 sharePreference
     */
    public void saveToSp(){
        SPUtils spUtils = SPUtils.getInstance();
        spUtils.put("mobilePhone",TextUtils.isEmpty(mobilePhone)?"":mobilePhone);
        spUtils.put("HXpwd",TextUtils.isEmpty(HXpwd)?"": HXpwd);
        spUtils.put("token",TextUtils.isEmpty(token)?"":token);
        spUtils.put("roleId",TextUtils.isEmpty(roleId)?"":roleId);

    }

    /**
     * 清空sharePreference 中的数据
     */
    public void clear(){
        SPUtils spUtils = SPUtils.getInstance();
        spUtils.remove("mobilePhone");
        spUtils.remove("HXpwd");
        spUtils.remove("token");
        spUtils.remove("roleId");
        getDataFromSp();
    }

    /**
     * 从sharePreference 获取用户信息
     */
    public void getDataFromSp(){
        SPUtils spUtils = SPUtils.getInstance();
        mobilePhone = spUtils.getString("mobilePhone","");
        HXpwd = spUtils.getString("HXpwd","");
        token = spUtils.getString("token","");
        roleId = spUtils.getString("roleId","");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mobilePhone);
        dest.writeString(this.token);
        dest.writeString(this.name);
        dest.writeString(this.HXpwd);
        dest.writeString(this.roleId);
        dest.writeString(this.unionid);
        dest.writeString(this.headimgurl);
        dest.writeString(this.nickname);
    }

    public UserLoginDto() {
    }

    protected UserLoginDto(Parcel in) {
        this.mobilePhone = in.readString();
        this.token = in.readString();
        this.name = in.readString();
        this.HXpwd = in.readString();
        this.roleId = in.readString();
        this.unionid = in.readString();
        this.headimgurl = in.readString();
        this.nickname = in.readString();
    }

    public static final Parcelable.Creator<UserLoginDto> CREATOR = new Parcelable.Creator<UserLoginDto>() {
        @Override
        public UserLoginDto createFromParcel(Parcel source) {
            return new UserLoginDto(source);
        }

        @Override
        public UserLoginDto[] newArray(int size) {
            return new UserLoginDto[size];
        }
    };
}
