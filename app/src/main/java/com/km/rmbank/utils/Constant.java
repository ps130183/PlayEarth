package com.km.rmbank.utils;

import com.km.rmbank.dto.ContractDto;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.dto.UserLoginDto;
import com.km.rmbank.retrofit.ApiConstant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PengSong on 18/1/18.
 */

public class Constant {
    public static UserLoginDto userLoginInfo;
    public static UserInfoDto userInfo;

    public static List<ContractDto> mBindingContractList;
    public static List<ContractDto> mUnBindingContractList;
    public static boolean isAllowUserCard = true;

    //客服电话
    public final static String SERVICE_PHONE = "13699231246";

    //获取名片 接口
    public static final String QRCODE_URL = ApiConstant.API_BASE_URL +  "/user/saoUserCard/info/send?mobilePhone=";

    public static final String SAVE_PATH_DEFAULT = File.separator + "WanZhuanDiQiu" + File.separator + "app" + File.separator;

    static {
        if (userLoginInfo == null){
            userLoginInfo = new UserLoginDto();
            userLoginInfo.getDataFromSp();
        }
    }
}
