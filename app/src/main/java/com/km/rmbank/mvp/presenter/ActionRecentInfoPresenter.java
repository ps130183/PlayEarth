package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.dto.ActionMemberDto;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.ActionRecentInfoModel;
import com.km.rmbank.mvp.view.IActionRecentInfoView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/1/27.
 */

public class ActionRecentInfoPresenter extends BasePresenter<IActionRecentInfoView,ActionRecentInfoModel> {

    public ActionRecentInfoPresenter(ActionRecentInfoModel mModel) {
        super(mModel);
    }

    public void getActionRecentInfo(final String actionId) {
        Observable observable1 = getMvpModel().getActionRecentInfo(actionId);
        Observable observable2 = getMvpModel().getActionMemberList(actionId,1);
        Observable.zip(observable1, observable2, new BiFunction<ActionDto,List<ActionMemberDto>,ActionDto>() {
            @Override
            public ActionDto apply(ActionDto actionDto, List<ActionMemberDto> actionMemberDtos) throws Exception {
                actionDto.setActionMemberDtos(actionMemberDtos);
                return actionDto;
            }
        }).subscribe(newSubscriber(new Consumer<ActionDto>() {
            @Override
            public void accept(ActionDto actionDto) throws Exception {
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

    public void taskShare(){
        getMvpModel().taskShare(2)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                    }
                });
    }

}
