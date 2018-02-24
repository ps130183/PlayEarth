package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.TicketDto;
import com.km.rmbank.dto.TicketUseRecordDto;
import com.km.rmbank.mvp.base.MvpView;
import com.ps.commonadapter.adapter.wrapper.LoadMoreWrapper;

import java.util.List;

/**
 * Created by PengSong on 18/2/10.
 */

public interface ITicketListView extends MvpView {
    void showTicketList(LoadMoreWrapper wrapper, List<TicketDto> ticketDtos);
    void showTicketUseRcordList(List<TicketUseRecordDto> ticketUseRecordDtos);
}
