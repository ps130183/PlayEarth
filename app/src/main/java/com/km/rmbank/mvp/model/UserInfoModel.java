package com.km.rmbank.mvp.model;

import com.google.gson.Gson;
import com.km.rmbank.dto.UserCardDto;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.retrofit.image.ImageUpload;
import com.km.rmbank.utils.Constant;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/1/25.
 */

public class UserInfoModel extends BaseModel {

    /**
     * 上传头像
     * @param optionType
     * @param imagePath
     * @return
     */
    public Observable<String> uploadUserPortrait(String optionType,String imagePath){
        return ImageUpload.imageUpload(getService(),optionType,imagePath).compose(this.<String>applySchedulers());
    }

    /**
     * 创建用户名片
     * @param userInfoDto
     * @return
     */
    public Observable<String> saveUserInfo(UserInfoDto userInfoDto){
        Gson gson = new Gson();
        return getService().saveUserInfo(Constant.userLoginInfo.getToken(),
                userInfoDto.getPortraitUrl(),
                userInfoDto.getName(),userInfoDto.getCompany(),userInfoDto.getPosition(),
                userInfoDto.getIndustryId(),
                userInfoDto.getCardPhone(),userInfoDto.getAllowStutas(),
                userInfoDto.getDetailedAddress(),
                userInfoDto.getEmailAddress(),userInfoDto.getPersonalizedSignature(),
                gson.toJson(userInfoDto.getIdentityList()),gson.toJson(userInfoDto.getDemandList()),gson.toJson(userInfoDto.getProvideList()))
                .compose(this.<String>applySchedulers());
    }

    /**
     * 创建用户信息  姓名  职位
     * @param name
     * @param position
     * @param phone
     * @return
     */
    public Observable<String> createUserCard(String name,String position,String phone){
        return getService().createUserCardOnLogin(name,phone,position)
                .compose(this.<String>applySchedulers());
    }

}
