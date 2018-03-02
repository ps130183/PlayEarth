package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.dto.TicketDto;
import com.km.rmbank.mvp.base.MvpView;

import java.util.List;

/**
 * Created by PengSong on 18/2/11.
 */

public interface IScenicServiceView extends MvpView {
    void showTicketList(List<TicketDto> ticketDtos);

    void applyFreeTeaSuccess();
}
