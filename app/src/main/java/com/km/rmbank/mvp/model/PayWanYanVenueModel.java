package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.utils.Constant;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/8/22.
 */

public class PayWanYanVenueModel extends BaseModel {

    /**
     * 获取晚宴场地支付订单
     * @param placeReservationId
     * @param amount
     * @return
     */
    public Observable<PayOrderDto> getWanYanVenueOrder(String placeReservationId,String amount){
        return getService().getWanYanVenueOrder(Constant.userLoginInfo.getToken(),placeReservationId,amount)
                .compose(this.<PayOrderDto>applySchedulers());
    }
}
