package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.GoodsDetailsDto;
import com.km.rmbank.dto.ReceiverAddressDto;
import com.km.rmbank.dto.UserCardDto;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.utils.Constant;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/1/19.
 */

public class GoodsModel extends BaseModel {

    /**
     * 获取商品详情
     * @param productNo
     * @return
     */
    public Observable<GoodsDetailsDto> getGoodsDetails(String productNo){
        return getService().getGoodsDetails(Constant.userLoginInfo.getToken(),productNo)
                .compose(this.<GoodsDetailsDto>applySchedulers());
    }

    /**
     * 获取默认的收货地址
     * @return
     */
    public Observable<ReceiverAddressDto> getDefaultReceiverAddress(){
        return getService().getDefaultReceiverAddress(Constant.userLoginInfo.getToken())
                .compose(this.<ReceiverAddressDto>applySchedulers());
    }

    /**
     * 关注商品
     * @param productNo
     * @return
     */
    public Observable<String> followGodos(String productNo){
        return getService().followGoods(Constant.userLoginInfo.getToken(),productNo,"")
                .compose(this.<String>applySchedulers());
    }

    /**
     * 加入购物车
     * @param productNo
     * @param count
     * @return
     */
    public Observable<String> addShoppingCart(String productNo,String count){
        return getService().addShoppingCart(Constant.userLoginInfo.getToken(),productNo,count)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 根据ID获取用户的名片
     * @param id
     * @return
     */
//    public Observable<UserCardDto> getUserCardById(String id){
//        return getService().getUserCardById(Constant.userLoginInfo.getToken(),id)
//                .compose(this.<UserCardDto>applySchedulers());
//    }

}
