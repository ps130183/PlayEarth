package com.km.rmbank.event;

import com.km.rmbank.entity.BookVenueApplyDto;

/**
 * Created by PengSong on 18/8/22.
 */

public class PayWanYanVenueEvent {
    private BookVenueApplyDto bookVenueApplyDto;

    public PayWanYanVenueEvent(BookVenueApplyDto bookVenueApplyDto) {
        this.bookVenueApplyDto = bookVenueApplyDto;
    }

    public BookVenueApplyDto getBookVenueApplyDto() {
        return bookVenueApplyDto;
    }
}
