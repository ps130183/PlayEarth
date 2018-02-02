package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.utils.Constant;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/1/18.
 */

public class HomeModel extends BaseModel {
    /**
     * 获取用户信息
     * @return
     */
    public Observable<UserInfoDto> getUserInfo(){
        return getService().getUserInfo(Constant.userLoginInfo.getToken())
                .compose(this.<UserInfoDto>applySchedulers());
    }
}
