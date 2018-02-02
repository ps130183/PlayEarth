package com.km.rmbank.utils;

import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.dto.UserLoginDto;
import com.km.rmbank.retrofit.ApiConstant;

/**
 * Created by PengSong on 18/1/18.
 */

public class Constant {
    public static UserLoginDto userLoginInfo;
    public static UserInfoDto userInfo;

    public static boolean isAllowUserCard = true;

    //获取名片 接口
    public static final String QRCODE_URL = ApiConstant.API_BASE_URL +  "/user/saoUserCard/info/send?mobilePhone=";


    static {
        if (userLoginInfo == null){
            userLoginInfo = new UserLoginDto();
            userLoginInfo.getDataFromSp();
        }
    }
}
