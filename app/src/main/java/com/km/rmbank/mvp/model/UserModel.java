package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.utils.Constant;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/1/19.
 */

public class UserModel extends BaseModel {
    /**
     * 获取用户信息
     * @return
     */
    public Observable<UserInfoDto> getUserInfo(){
        return getService().getUserInfo(Constant.userLoginInfo.getToken())
                .compose(this.<UserInfoDto>applySchedulers());
    }

    /**
     * 获取我的俱乐部 数据
     * @param clubId
     * @return
     */
    public Observable<ClubDto> getClubInfoOfMe(String clubId){
        return getService().getClubInfo(Constant.userLoginInfo.getToken(),clubId)
                .compose(this.<ClubDto>applySchedulers());
    }
}
