package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.CalendarActionsDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.ClubActionsModel;
import com.km.rmbank.mvp.view.IClubActionsView;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/2/5.
 */

public class ClubActionsPresenter extends BasePresenter<IClubActionsView,ClubActionsModel> {

    public ClubActionsPresenter(ClubActionsModel mModel) {
        super(mModel);
    }

    public void getClubActionsByMonth(String clubId, final String startDate){
        getMvpModel().getClubActionsByMonth(clubId,startDate)
                .subscribe(newSubscriber(new Consumer<List<CalendarActionsDto>>() {
                    @Override
                    public void accept(List<CalendarActionsDto> calendarActionsDtos) throws Exception {
                        getMvpView().showClubActions(calendarActionsDtos,startDate);
                    }
                }));
    }
}
