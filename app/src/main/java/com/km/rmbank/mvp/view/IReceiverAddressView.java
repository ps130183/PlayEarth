package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.ReceiverAddressDto;
import com.km.rmbank.mvp.base.MvpView;

import java.util.List;

/**
 * Created by PengSong on 18/1/23.
 */

public interface IReceiverAddressView extends MvpView {
    void showReceiverAddressList(List<ReceiverAddressDto> receiverAddressDtos);
    void setDefaultReceiverAddressSuccess();
    void deleteReceiverSuccess(ReceiverAddressDto receiverAddressDto);
}
