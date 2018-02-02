package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.dto.AppointDto;
import com.km.rmbank.mvp.model.AppointModel;
import com.km.rmbank.mvp.view.AppointView;
import com.km.rmbank.mvp.base.BasePresenter;
import com.ps.commonadapter.adapter.wrapper.LoadMoreWrapper;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/1/17.
 */

public class AppointPresenter extends BasePresenter<AppointView,AppointModel> {

    public AppointPresenter(AppointModel mModel) {
        super(mModel);
    }

    public void getAppointList(int pageNo, final LoadMoreWrapper wrapper){
        getMvpModel().getAppointLists(pageNo)
                .subscribe(newSubscriber(new Consumer<List<AppointDto>>() {
                    @Override
                    public void accept(List<AppointDto> actionDtos) throws Exception {
                            getMvpView().showAppointList(wrapper,actionDtos);
                    }
                }));
    }

    public void getAppointAppliedList(int pageNo, final LoadMoreWrapper wrapper){
        getMvpModel().getAppointAppliedLists(pageNo)
                .subscribe(newSubscriber(new Consumer<List<AppointDto>>() {
                    @Override
                    public void accept(List<AppointDto> actionDtos) throws Exception {
                        getMvpView().showAppointList(wrapper,actionDtos);
                    }
                }));
    }
}
