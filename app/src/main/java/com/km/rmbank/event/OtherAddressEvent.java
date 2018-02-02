package com.km.rmbank.event;

import com.km.rmbank.dto.ReceiverAddressDto;

/**
 * Created by kamangkeji on 17/4/11.
 */

public class OtherAddressEvent {
    private ReceiverAddressDto receiverAddressDto;

    public OtherAddressEvent(ReceiverAddressDto receiverAddressDto) {
        this.receiverAddressDto = receiverAddressDto;
    }

    public ReceiverAddressDto getReceiverAddressDto() {
        return receiverAddressDto;
    }

    public void setReceiverAddressDto(ReceiverAddressDto receiverAddressDto) {
        this.receiverAddressDto = receiverAddressDto;
    }
}
