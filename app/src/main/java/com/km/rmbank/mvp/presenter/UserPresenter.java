package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.UserModel;
import com.km.rmbank.mvp.view.IUserView;

import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/1/19.
 */

public class UserPresenter extends BasePresenter<IUserView,UserModel> {

    public UserPresenter(UserModel mModel) {
        super(mModel);
    }

    public void getUserInfo(){
        getMvpView().showLoading();
        getMvpModel().getUserInfo()
                .subscribe(newSubscriber(new Consumer<UserInfoDto>() {
                    @Override
                    public void accept(UserInfoDto userInfoDto) throws Exception {
                        getMvpView().showUserInfo(userInfoDto);
                    }
                }));
    }

    public void getClubInfOfMe(String clubId){
        getMvpModel().getClubInfoOfMe(clubId)
                .subscribe(newSubscriber(new Consumer<ClubDto>() {
                    @Override
                    public void accept(ClubDto clubDto) throws Exception {
                        getMvpView().showClubInf(clubDto);
                    }
                }));
    }
}
