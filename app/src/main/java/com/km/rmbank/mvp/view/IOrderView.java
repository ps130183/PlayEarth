package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.OrderDto;
import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.mvp.base.MvpView;

import java.util.List;

/**
 * Created by PengSong on 18/1/24.
 */

public interface IOrderView extends MvpView {
    void showOrderList(List<OrderDto> orderEntities, int page);
    void getPayOrderSuccess(PayOrderDto payOrderDto);
}
