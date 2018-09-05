package com.km.rmbank.mvp.presenter;

import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.SelectVenueTimeModel;
import com.km.rmbank.mvp.view.SelectVenueTimeView;

import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/8/20.
 */

public class SelectVenueTimePresenter extends BasePresenter<SelectVenueTimeView,SelectVenueTimeModel> {

    public SelectVenueTimePresenter(SelectVenueTimeModel mModel) {
        super(mModel);
    }

    public void submitBookVenue(String placeId,String startDate,String endDate){
        getMvpView().showLoading();
        getMvpModel().submitBookVenue(placeId,startDate,endDate)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        getMvpView().submitSuccess();
                    }
                }));
    }
}
