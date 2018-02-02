package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.ReceiverAddressDto;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.utils.Constant;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/1/23.
 */

public class ReceiverAddressModel extends BaseModel {

    /**
     * 获取收货地址列表
     * @return
     */
    public Observable<List<ReceiverAddressDto>> getReceiverAddressList(){
        return getService().getReceiverAddressList(Constant.userLoginInfo.getToken())
                .compose(this.<List<ReceiverAddressDto>>applySchedulers());
    }

    /**
     * 设置默认的收货地址
     * @param id
     * @return
     */
    public Observable<String> setDefaultReceiverAddress(String id){
        return getService().setDefaultReceiverAddress(Constant.userLoginInfo.getToken(),id)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 删除指定的收货地址
     * @param id
     * @return
     */
    public Observable<String> deleteReceiverAddress(String id){
        return getService().deleteReceiverAddress(Constant.userLoginInfo.getToken(),id)
                .compose(this.<String>applySchedulers());
    }
}
