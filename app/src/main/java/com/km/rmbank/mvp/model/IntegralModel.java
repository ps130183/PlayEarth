package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.IntegralDetailsDto;
import com.km.rmbank.dto.IntegralDto;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.utils.Constant;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Created by PengSong on 18/1/25.
 */

public class IntegralModel extends BaseModel {
    /**
     * 获取我的积分
     * @return
     */
    public Observable<IntegralDto> getIntegralInfo(){
        return getService().getIntegralInfo(Constant.userLoginInfo.getToken())
                .compose(this.<IntegralDto>applySchedulers());
    }

    /**
     * 获取积分明细列表
     * @param pageNo
     * @return
     */
    public Observable<List<IntegralDetailsDto>> getIntegralDetailsList(int pageNo){
        return getService().getIntegralDetailsList(Constant.userLoginInfo.getToken(),pageNo)
                .compose(this.<List<IntegralDetailsDto>>applySchedulers());
    }
}
