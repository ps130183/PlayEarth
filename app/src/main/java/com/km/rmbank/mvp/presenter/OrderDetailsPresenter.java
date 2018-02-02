package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.OrderDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.OrderDetailsModel;
import com.km.rmbank.mvp.view.IOrderDetailsView;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/1/24.
 */

public class OrderDetailsPresenter extends BasePresenter<IOrderDetailsView,OrderDetailsModel> {

    public OrderDetailsPresenter(OrderDetailsModel mModel) {
        super(mModel);
    }

    public void getOrderDetail(OrderDto orderDto) {
        getMvpView().showLoading();
        getMvpModel().getOrderDetails(orderDto)
                .subscribe(newSubscriber(new Consumer<OrderDto>() {
                    @Override
                    public void accept(@NonNull OrderDto orderDto) throws Exception {
                        getMvpView().initOrderDetail(orderDto);
                    }
                }));
    }

    public void shopSendGoods(OrderDto orderDto, String expressCompany, String expressNumber) {
        getMvpView().showLoading();
        getMvpModel().sendGoods(orderDto.getOrderNo(),expressCompany,expressNumber)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        getMvpView().sendGoodsSuccess();
                    }
                }));
    }

    public void confirmReceiverGoods(OrderDto orderDto) {
        getMvpView().showLoading();
        getMvpModel().confirmReceiverGoods(orderDto)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        getMvpView().receiverGoodsSuccess();
                    }
                }));
    }
}
