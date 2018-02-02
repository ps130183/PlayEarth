package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.ReceiverAddressDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.ReceiverAddressCreateModel;
import com.km.rmbank.mvp.view.IReceiverAddressCreateView;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/1/23.
 */

public class ReceiverAddressCreatePresenter extends BasePresenter<IReceiverAddressCreateView,ReceiverAddressCreateModel> {

    public ReceiverAddressCreatePresenter(ReceiverAddressCreateModel mModel) {
        super(mModel);
    }

    public void createReceiverAddress(ReceiverAddressDto receiverAddressDto) {
        getMvpView().showLoading();
        getMvpModel().newReceiverAddress(receiverAddressDto)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        getMvpView().createReceiverAddressSuccess(s);
                    }
                }));
    }

    public void updateReceiverAddress(final ReceiverAddressDto receiverAddressDto) {
        getMvpView().showLoading();
        getMvpModel().updateReceiverAddress(receiverAddressDto)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        getMvpView().createReceiverAddressSuccess(receiverAddressDto.getId());
                    }
                }));
    }
}
