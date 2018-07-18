package com.km.rmbank.mvp.model;

import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.utils.Constant;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/7/12.
 */

public class UserCardModel extends BaseModel {
    /**
     * 分享赚球票
     * @return
     */
    public Observable<String> taskShare(){
        return getService().taskShare(Constant.userLoginInfo.getToken(),3)
                .compose(this.<String>applySchedulers());
    }
}
