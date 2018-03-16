package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.InformationDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.InformationModel;
import com.km.rmbank.mvp.view.InformationView;
import com.ps.commonadapter.adapter.wrapper.LoadMoreWrapper;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/3/15.
 */

public class InformationPresenter extends BasePresenter<InformationView,InformationModel> {

    public InformationPresenter(InformationModel mModel) {
        super(mModel);
    }

    public void getInfomationList(final int pageNo, final LoadMoreWrapper wrapper){
        getMvpModel().getInformationList(pageNo)
                .subscribe(newSubscriber(new Consumer<List<InformationDto>>() {
                    @Override
                    public void accept(List<InformationDto> informationDtos) throws Exception {
                        getMvpView().showInformation(pageNo,informationDtos,wrapper);
                    }
                }));
    }
}
