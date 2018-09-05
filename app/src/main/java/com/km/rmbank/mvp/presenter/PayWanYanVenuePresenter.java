package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.PayWanYanVenueModel;
import com.km.rmbank.mvp.view.PayWanYanVenueView;

import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/8/22.
 */

public class PayWanYanVenuePresenter extends BasePresenter<PayWanYanVenueView,PayWanYanVenueModel> {

    public PayWanYanVenuePresenter(PayWanYanVenueModel mModel) {
        super(mModel);
    }

    public void getWanYanOrder(String placeReservationId,String amount){
        getMvpView().showLoading();
        getMvpModel().getWanYanVenueOrder(placeReservationId,amount)
                .subscribe(newSubscriber(new Consumer<PayOrderDto>() {
                    @Override
                    public void accept(PayOrderDto orderDto) throws Exception {
                        getMvpView().showResult(orderDto);
                    }
                }));
    }
}
