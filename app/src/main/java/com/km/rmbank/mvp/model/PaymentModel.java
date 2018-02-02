package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.dto.UserBalanceDto;
import com.km.rmbank.dto.WeiCharParamsDto;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.utils.Constant;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Created by PengSong on 18/1/25.
 */

public class PaymentModel extends BaseModel {
    /**
     * 创建支付订单
     * @param amount
     * @return
     */
    public Observable<PayOrderDto> createPayOrder(String amount, String memberType){
        return getService().createPayOrder(Constant.userLoginInfo.getToken(),amount,memberType)
                .compose(this.<PayOrderDto>applySchedulers());
    }

    /**
     * 获取支付宝支付相应参数
     * @param payNumber
     * @return
     */
    public Observable<String> getAlipayParams(String payNumber){
        return getService().getAlipayParams(Constant.userLoginInfo.getToken(),payNumber)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 获取微信支付相应参数
     * @param payNumber
     * @return
     */
    public Observable<WeiCharParamsDto> getWeiChatParams(String payNumber){
        return getService().getWeiChatParams(Constant.userLoginInfo.getToken(),payNumber)
                .compose(this.<WeiCharParamsDto>applySchedulers());
    }

    /**
     * 余额支付
     * @param payNumber
     * @return
     */
    public Observable<String> payBalance(String payNumber){
        return getService().payBalance(Constant.userLoginInfo.getToken(),payNumber)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 支付回调验证
     * @param payNumber
     * @return
     */
    public Observable<String> checkPayResult(String payNumber){
        return getService().checkPayResult(Constant.userLoginInfo.getToken(),payNumber)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 获取账户余额
     * @return
     */
    public Observable<UserBalanceDto> getUserAccountBalance(){
        return getService().getUserAccountBalance(Constant.userLoginInfo.getToken()).compose(this.<UserBalanceDto>applySchedulers());
    }


}
