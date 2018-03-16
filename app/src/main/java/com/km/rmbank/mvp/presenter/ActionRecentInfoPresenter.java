package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.ActionRecentInfoModel;
import com.km.rmbank.mvp.view.IActionRecentInfoView;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/1/27.
 */

public class ActionRecentInfoPresenter extends BasePresenter<IActionRecentInfoView,ActionRecentInfoModel> {

    public ActionRecentInfoPresenter(ActionRecentInfoModel mModel) {
        super(mModel);
    }

    public void getActionRecentInfo(String actionId) {
        getMvpModel().getActionRecentInfo(actionId)
                .subscribe(newSubscriber(new Consumer<ActionDto>() {
                    @Override
                    public void accept(@NonNull ActionDto actionDto) throws Exception {
                        getMvpView().showActionRecentInfo(actionDto);
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

    public void followClub(String clubId, final boolean isFollow) {
        getMvpModel().followGodos("",clubId)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        getMvpView().followClubSuccess(isFollow);
                    }
                }));
    }

    public void addActiveValue(String activityId) {
        getMvpModel().addActiveValue(activityId)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        getMvpView().addActiveValueSuccess(s);
                    }
                }));
    }

    public void getClubInfo(String clubId){
        getMvpView().showLoading();
        getMvpModel().getClubInfo(clubId)
                .subscribe(newSubscriber(new Consumer<ClubDto>() {
                    @Override
                    public void accept(ClubDto clubDto) throws Exception {
                        getMvpView().showClubInfo(clubDto);
                    }
                }));
    }
}
