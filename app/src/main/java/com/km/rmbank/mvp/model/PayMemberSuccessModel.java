package com.km.rmbank.mvp.model;

import com.km.rmbank.mvp.base.BaseModel;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/8/19.
 */

public class PayMemberSuccessModel extends BaseModel {

    /**
     * 购买会员成功 以后提交收货信息
     * @param orderNo
     * @param address
     * @param name
     * @param phone
     * @return
     */
    public Observable<String> saveAddress(String orderNo,String address,String name,String phone){
        return getService().saveAddress(orderNo,address,name,phone)
                .compose(this.<String>applySchedulers());
    }
}
