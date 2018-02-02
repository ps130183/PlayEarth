package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.OrderDto;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.utils.Constant;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/1/24.
 */

public class OrderDetailsModel extends BaseModel {
    /**
     * 获取订单详情
     * @param orderDto
     * @return
     */
    public Observable<OrderDto> getOrderDetails(OrderDto orderDto){
        return getService().getOrderDetails(Constant.userLoginInfo.getToken(),orderDto.getOrderNo())
                .compose(this.<OrderDto>applySchedulers());
    }

    /**
     * 发货
     * @param orderNO
     * @param expressCompany
     * @param expressNumber
     * @return
     */
    public Observable<String> sendGoods(String orderNO, String expressCompany, String expressNumber){
        return getService().sendGoods(Constant.userLoginInfo.getToken(),orderNO,expressCompany,expressNumber)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 确认收货
     * @param orderDto
     * @return
     */
    public Observable<String> confirmReceiverGoods(OrderDto orderDto){
        return getService().confirmReceiverGoods(Constant.userLoginInfo.getToken(),orderDto.getOrderNo())
                .compose(this.<String>applySchedulers());
    }
}
