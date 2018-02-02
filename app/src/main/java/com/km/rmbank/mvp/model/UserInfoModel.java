package com.km.rmbank.mvp.model;

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
    public Observable<String> createUserCart(UserInfoDto userInfoDto){
        return getService().createUserCard(Constant.userLoginInfo.getToken(),
                userInfoDto.getPortraitUrl(),
                userInfoDto.getName(),userInfoDto.getMobilePhone(),userInfoDto.getPosition(),
                userInfoDto.getPersonalizedSignature(),userInfoDto.getDetailedAddress())
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
