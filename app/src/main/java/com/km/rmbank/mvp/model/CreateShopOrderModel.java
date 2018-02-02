package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.dto.ReceiverAddressDto;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.utils.Constant;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Created by PengSong on 18/1/28.
 */

public class CreateShopOrderModel extends BaseModel {
    /**
     * 获取默认收货地址
     * @return
     */
    public Observable<ReceiverAddressDto> getDefaultReceiverAddress(){
        return getService().getDefaultReceiverAddress(Constant.userLoginInfo.getToken())
                .compose(this.<ReceiverAddressDto>applySchedulers());
    }

    /**
     * 提交订单
     * @param productNos
     * @param productCounts
     * @param receiveAddressId
     * @param freight
     * @return
     */
    public Observable<PayOrderDto> submitOrder(String productNos, String productCounts, String receiveAddressId,
                                             String freight, String exchange){
        return getService().submitOrder(Constant.userLoginInfo.getToken(),productNos,productCounts,
                receiveAddressId,freight,exchange).compose(this.<PayOrderDto>applySchedulers());
    }
}
