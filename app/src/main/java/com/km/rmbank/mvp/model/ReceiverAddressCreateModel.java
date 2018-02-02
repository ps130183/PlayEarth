package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.ReceiverAddressDto;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.utils.Constant;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/1/23.
 */

public class ReceiverAddressCreateModel extends BaseModel {

    /**
     * 新增收货地址
     * @param receiverAddressDto
     * @return
     */
    public Observable<String> newReceiverAddress(ReceiverAddressDto receiverAddressDto){
        return getService().newReceiverAddress(Constant.userLoginInfo.getToken(),
                receiverAddressDto.getReceivePerson(),receiverAddressDto.getReceivePersonPhone(),receiverAddressDto.getReceiveAddress())
                .compose(this.<String>applySchedulers());
    }

    /**
     * 更新收货地址信息
     * @param receiverAddressDto
     * @return
     */
    public Observable<String> updateReceiverAddress(ReceiverAddressDto receiverAddressDto){
        return getService().updateReceiverAddress(Constant.userLoginInfo.getToken(),receiverAddressDto.getId(),
                receiverAddressDto.getReceivePerson(),receiverAddressDto.getReceivePersonPhone(),receiverAddressDto.getReceiveAddress())
                .compose(this.<String>applySchedulers());
    }
}
