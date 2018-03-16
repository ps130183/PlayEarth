package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.MapMarkerDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.ScenicListModel;
import com.km.rmbank.mvp.view.ScenicListView;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/3/16.
 */

public class ScenicListPresenter extends BasePresenter<ScenicListView,ScenicListModel> {

    public ScenicListPresenter(ScenicListModel mModel) {
        super(mModel);
    }

    public void getScenicLisit(int type){
        getMvpModel().getScenicList(type)
                .subscribe(newSubscriber(new Consumer<List<MapMarkerDto>>() {
                    @Override
                    public void accept(List<MapMarkerDto> mapMarkerDtos) throws Exception {
                        getMvpView().showScenicList(mapMarkerDtos);
                    }
                }));
    }

    public void getMapMarkers(){
        getMvpModel().getScenicList(0)
                .subscribe(newSubscriber(new Consumer<List<MapMarkerDto>>() {
                    @Override
                    public void accept(List<MapMarkerDto> mapMarkerDtos) throws Exception {
                        getMvpView().showMapMarkerResult(mapMarkerDtos);
                    }
                }));
    }
}
