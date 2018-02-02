package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.SignInDto;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.utils.Constant;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/1/24.
 */

public class SignInListModel extends BaseModel {
    /**
     * 获取签到人列表
     * @param actionId
     * @return
     */
    public Observable<List<SignInDto>> getSignInLists(String actionId){
        return getService().getSignInLists(Constant.userLoginInfo.getToken(),actionId)
                .compose(this.<List<SignInDto>>applySchedulers());
    }
}
