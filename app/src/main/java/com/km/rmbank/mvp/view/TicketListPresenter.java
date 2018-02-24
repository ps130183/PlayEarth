package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.TicketDto;
import com.km.rmbank.dto.TicketUseRecordDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.TicketListModel;
import com.ps.commonadapter.adapter.wrapper.LoadMoreWrapper;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/2/10.
 */

public class TicketListPresenter extends BasePresenter<ITicketListView,TicketListModel> {

    public TicketListPresenter(TicketListModel mModel) {
        super(mModel);
    }

    public void getAllTicketList(final LoadMoreWrapper wrapper, int pageNo){
        getMvpModel().getAllTicketList(pageNo)
                .subscribe(newSubscriber(new Consumer<List<TicketDto>>() {
                    @Override
                    public void accept(List<TicketDto> ticketDtos) throws Exception {
                        getMvpView().showTicketList(wrapper,ticketDtos);
                    }
                }));
    }

    public void getTicketUseRecordList(String ticketNo){
        getMvpModel().getTicketUseRecordList(ticketNo)
                .subscribe(newSubscriber(new Consumer<List<TicketUseRecordDto>>() {
                    @Override
                    public void accept(List<TicketUseRecordDto> ticketUseRecordDtos) throws Exception {
                        getMvpView().showTicketUseRcordList(ticketUseRecordDtos);
                    }
                }));
    }
}
