package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.OrderDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.OrderManagerModel;
import com.km.rmbank.mvp.view.IOrderManagerView;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/1/24.
 */

public class OrderManagerPresenter extends BasePresenter<IOrderManagerView,OrderManagerModel> {

    public OrderManagerPresenter(OrderManagerModel mModel) {
        super(mModel);
    }

    public void getSellOrder(final int pageNo) {
        getMvpModel().getSellGoodsList(pageNo)
                .subscribe(newSubscriber(new Consumer<List<OrderDto>>() {
                    @Override
                    public void accept(@NonNull List<OrderDto> goodsDtos) throws Exception {
                        getMvpView().getSellOrderSuccess(goodsDtos,pageNo);
                    }
                }));
    }
}
