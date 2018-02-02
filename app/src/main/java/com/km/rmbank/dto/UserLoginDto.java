package com.km.rmbank.dto;

import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;

/**
 * Created by PengSong on 18/1/18.
 */

public class UserLoginDto {


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
}
