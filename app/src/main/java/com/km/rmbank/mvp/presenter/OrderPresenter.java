package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.OrderDto;
import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.OrderModel;
import com.km.rmbank.mvp.view.IOrderView;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/1/24.
 */

public class OrderPresenter extends BasePresenter<IOrderView,OrderModel> {

    public OrderPresenter(OrderModel mModel) {
        super(mModel);
    }

    public void loadOrder(final int page, String status) {
        getMvpView().showLoading();
        getMvpModel().getOrderList(page,status)
                .subscribe(newSubscriber(new Consumer<List<OrderDto>>() {
                    @Override
                    public void accept(@NonNull List<OrderDto> orderEntities) throws Exception {
                        getMvpView().showOrderList(orderEntities,page);
                    }
                }));
    }

    public void getPayOrder(OrderDto orderDto) {
        getMvpView().showLoading();
        getMvpModel().toPayOnMyOrder(orderDto.getOrderNo())
                .subscribe(newSubscriber(new Consumer<PayOrderDto>() {
                    @Override
                    public void accept(@NonNull PayOrderDto payOrderDto) throws Exception {
                        getMvpView().getPayOrderSuccess(payOrderDto);
                    }
                }));
    }
}
