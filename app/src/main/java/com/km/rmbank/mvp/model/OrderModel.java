package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.OrderDto;
import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.utils.Constant;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/1/24.
 */

public class OrderModel extends BaseModel {

    /**
     * 获取订单列表
     * @param page
     * @param status
     * @return
     */
    public Observable<List<OrderDto>> getOrderList(final int page, String status){
        return getService().getOrderList(Constant.userLoginInfo.getToken(),status,page)
                .compose(this.<List<OrderDto>>applySchedulers());
    }

    /**
     * 支付 获取支付订单
     * @param orderNo
     * @return
     */
    public Observable<PayOrderDto> toPayOnMyOrder(String orderNo){
        return getService().toPayOnMyOrder(Constant.userLoginInfo.getToken(),orderNo)
                .compose(this.<PayOrderDto>applySchedulers());
    }
}
