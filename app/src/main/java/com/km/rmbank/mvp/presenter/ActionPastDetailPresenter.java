package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.ActionPastDto;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.ActionPastDetailModel;
import com.km.rmbank.mvp.view.IActionPastDetailView;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/1/28.
 */

public class ActionPastDetailPresenter extends BasePresenter<IActionPastDetailView,ActionPastDetailModel> {

    public ActionPastDetailPresenter(ActionPastDetailModel mModel) {
        super(mModel);
    }

    public void getActionPastDetails(String id) {
        getMvpView().showLoading();
        getMvpModel().getActionPastDetail(id)
                .subscribe(newSubscriber(new Consumer<ActionPastDto>() {
                    @Override
                    public void accept(@NonNull ActionPastDto actionPastDto) throws Exception {
                        getMvpView().showActionPastDetails(actionPastDto);
                    }
                }));
    }

    public void getClubInfo(String clubId){
        getMvpModel().getClubInfo(clubId)
                .subscribe(newSubscriber(new Consumer<ClubDto>() {
                    @Override
                    public void accept(ClubDto clubDto) throws Exception {
                        getMvpView().showClubInfo(clubDto);
                    }
                }));
    }
}
