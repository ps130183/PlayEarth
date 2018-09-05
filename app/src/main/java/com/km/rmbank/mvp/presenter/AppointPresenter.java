package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.dto.AppointDto;
import com.km.rmbank.dto.MapMarkerDto;
import com.km.rmbank.entity.BookVenueSitEntity;
import com.km.rmbank.mvp.model.AppointModel;
import com.km.rmbank.mvp.view.AppointView;
import com.km.rmbank.mvp.base.BasePresenter;
import com.ps.commonadapter.adapter.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/1/17.
 */

public class AppointPresenter extends BasePresenter<AppointView,AppointModel> {

    public AppointPresenter(AppointModel mModel) {
        super(mModel);
    }

    public void getAppointList(final int pageNo, int newType){
        getMvpModel().getAppointLists(pageNo,newType)
                .subscribe(newSubscriber(new Consumer<List<AppointDto>>() {
                    @Override
                    public void accept(List<AppointDto> actionDtos) throws Exception {
                            getMvpView().showAppointList(pageNo,actionDtos);
                    }
                }));
    }

    public void getActionListByPlace(final int pageNo, String placeId,String clubId){
        getMvpModel().getActionListByPlace(pageNo,placeId,clubId)
                .subscribe(newSubscriber(new Consumer<List<AppointDto>>() {
                    @Override
                    public void accept(List<AppointDto> appointDtos) throws Exception {
                        getMvpView().showAppointList(pageNo,appointDtos);
                    }
                }));
    }

    public void getAppointAppliedList(String timeType, final int pageNo){
        getMvpModel().getAppointAppliedLists(timeType,pageNo)
                .subscribe(newSubscriber(new Consumer<List<AppointDto>>() {
                    @Override
                    public void accept(List<AppointDto> actionDtos) throws Exception {
                        getMvpView().showAppointList(pageNo,actionDtos);
                    }
                }));
    }


    public void applyAction(final String activityId, String name, String phone) {
        getMvpModel().applyAction(activityId,name,phone)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        getMvpView().applyActionSuccess(activityId);
                    }
                }));
    }

    public void getBookVenueSitList(int type){
        getMvpModel().getBookVenueSitList(type)
                .subscribe(newSubscriber(new Consumer<List<BookVenueSitEntity>>() {
                    @Override
                    public void accept(List<BookVenueSitEntity> bookVenueSitEntities) throws Exception {
                        getMvpView().showVenueSitList(bookVenueSitEntities);
                    }
                }));
    }

    public void getBaseList(){
        getMvpModel().getBaseList()
                .subscribe(newSubscriber(new Consumer<List<MapMarkerDto>>() {
                    @Override
                    public void accept(List<MapMarkerDto> mapMarkerDtos) throws Exception {
                        List<BookVenueSitEntity> bookVenueSitEntities = new ArrayList<>();
                        for (MapMarkerDto mapMarkerDto : mapMarkerDtos){
                            BookVenueSitEntity entity = new BookVenueSitEntity();
                            entity.setImageUrl(mapMarkerDto.getBackgroundImg());
                            entity.setTitle(mapMarkerDto.getClubName());
                            entity.setId(mapMarkerDto.getId());
                            bookVenueSitEntities.add(entity);
                        }
                        getMvpView().showVenueSitList(bookVenueSitEntities);
                    }
                }));
    }
}
