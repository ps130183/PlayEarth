package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.utils.Constant;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/9/2.
 */

public class SearchCompanyModel extends BaseModel {
    /**
     * 通过用户的ID 获取 名片信息
     * @param userId
     * @return
     */
    public Observable<UserInfoDto> getUserCardById(String userId){
        return getService().getOtherUserCardById(userId)
                .compose(this.<UserInfoDto>applySchedulers());
    }

    /**
     * 获取用户信息
     * @return
     */
    public Observable<UserInfoDto> getUserInfo(){
        return getService().getUserInfo(Constant.userLoginInfo.getToken())
                .compose(this.<UserInfoDto>applySchedulers());
    }
}
