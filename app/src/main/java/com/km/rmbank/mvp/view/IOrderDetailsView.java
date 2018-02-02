package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.OrderDto;
import com.km.rmbank.mvp.base.MvpView;

/**
 * Created by PengSong on 18/1/24.
 */

public interface IOrderDetailsView extends MvpView {
    void initOrderDetail(OrderDto orderDto);
    void sendGoodsSuccess();
    void receiverGoodsSuccess();
}
