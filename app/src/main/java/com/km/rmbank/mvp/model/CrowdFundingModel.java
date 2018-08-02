package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.MyCrowdFundingInfoDto;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.utils.Constant;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/7/31.
 */

public class CrowdFundingModel extends BaseModel {

    /**
     * 获取我的众筹信息
     * @return
     */
    public Observable<MyCrowdFundingInfoDto> getMyCrowdFundingInfo(){
        return getService().getMyCrowdFundingInfo(Constant.userLoginInfo.getToken())
                .compose(this.<MyCrowdFundingInfoDto>applySchedulers());
    }
}
