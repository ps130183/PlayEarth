package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.dto.ReceiverAddressDto;
import com.km.rmbank.dto.ShoppingCartDto;
import com.km.rmbank.mvp.base.MvpView;

import java.util.List;

/**
 * Created by PengSong on 18/1/28.
 */

public interface ICreateShopOrderView extends MvpView {
    void showDefaultReceiverAddress(ReceiverAddressDto receiverAddressDto);
    void showOrderDatas(List<ShoppingCartDto> cartEntities);
    void submitOrderSuccess(PayOrderDto payOrderDto);
}
