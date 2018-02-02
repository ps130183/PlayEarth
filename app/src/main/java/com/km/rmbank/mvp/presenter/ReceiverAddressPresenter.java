package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.ReceiverAddressDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.ReceiverAddressModel;
import com.km.rmbank.mvp.view.IReceiverAddressView;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/1/23.
 */

public class ReceiverAddressPresenter extends BasePresenter<IReceiverAddressView,ReceiverAddressModel> {
    
    public ReceiverAddressPresenter(ReceiverAddressModel mModel) {
        super(mModel);
    }

    public void loadReceiverAddressData() {
        getMvpView().showLoading();
        getMvpModel().getReceiverAddressList()
                .subscribe(newSubscriber(new Consumer<List<ReceiverAddressDto>>() {
                    @Override
                    public void accept(@NonNull List<ReceiverAddressDto> receiverAddressDtos) throws Exception {
                        getMvpView().showReceiverAddressList(receiverAddressDtos);
                    }
                }));
    }

    public void setDefaultReceiverAddress(ReceiverAddressDto receiverAddress) {
        getMvpModel().setDefaultReceiverAddress(receiverAddress.getId())
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        getMvpView().setDefaultReceiverAddressSuccess();
                    }
                }));
    }

    public void deleteReceiverAddress(final ReceiverAddressDto receiverAddressDto) {
        getMvpView().showLoading();
        getMvpModel().deleteReceiverAddress(receiverAddressDto.getId())
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        getMvpView().deleteReceiverSuccess(receiverAddressDto);
                    }
                }));
    }
}
