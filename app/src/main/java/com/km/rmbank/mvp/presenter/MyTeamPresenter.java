package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.MyTeamDto;
import com.km.rmbank.dto.UserCardDto;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.MyTeamModel;
import com.km.rmbank.mvp.view.IMyTeamView;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/1/24.
 */

public class MyTeamPresenter extends BasePresenter<IMyTeamView,MyTeamModel> {

    public MyTeamPresenter(MyTeamModel mModel) {
        super(mModel);
    }

    public void getMyTeamData() {
        getMvpView().showLoading();
        getMvpModel().getMyTeam()
                .subscribe(newSubscriber(new Consumer<List<MyTeamDto>>() {
                    @Override
                    public void accept(@NonNull List<MyTeamDto> myTeamDtos) throws Exception {
                        getMvpView().showMyTeam(myTeamDtos);
                    }
                }));

    }

    public void getUserCardById(String userId){
        getMvpView().showLoading();
        getMvpModel().getUserCardById(userId)
                .subscribe(newSubscriber(new Consumer<UserInfoDto>() {
                    @Override
                    public void accept(UserInfoDto userCardDto) throws Exception {
                        getMvpView().showUserCard(userCardDto);
                    }
                }));
    }
}
