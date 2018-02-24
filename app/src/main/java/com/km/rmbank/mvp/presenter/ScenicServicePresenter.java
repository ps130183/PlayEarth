package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.dto.TicketDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.ScenicServiceModel;
import com.km.rmbank.mvp.view.IScenicServiceView;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/2/11.
 */

public class ScenicServicePresenter extends BasePresenter<IScenicServiceView,ScenicServiceModel> {

    public ScenicServicePresenter(ScenicServiceModel mModel) {
        super(mModel);
    }

    public void getTicketListOfScenic(String id,String clubId){
        getMvpModel().getTicketListOfScenic(id,clubId)
                .subscribe(newSubscriber(new Consumer<List<TicketDto>>() {
                    @Override
                    public void accept(List<TicketDto> ticketDtos) throws Exception {
                        getMvpView().showTicketList(ticketDtos);
                    }
                }));
    }
}