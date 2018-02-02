package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.MyTeamDto;
import com.km.rmbank.dto.UserCardDto;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.utils.Constant;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/1/24.
 */

public class MyTeamModel extends BaseModel {

    /**
     * 获取我的团队成员
     * @return
     */
    public Observable<List<MyTeamDto>> getMyTeam(){
        return getService().getMyTeam(Constant.userLoginInfo.getToken())
                .compose(this.<List<MyTeamDto>>applySchedulers());
    }

    /**
     * 通过用户的ID 获取 名片信息
     * @param userId
     * @return
     */
    public Observable<UserInfoDto> getUserCardById(String userId){
        return getService().getUserCardById(Constant.userLoginInfo.getToken(),userId)
                .compose(this.<UserInfoDto>applySchedulers());
    }
}
