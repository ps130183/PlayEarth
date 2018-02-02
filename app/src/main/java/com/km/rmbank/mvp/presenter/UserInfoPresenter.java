package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.UserInfoModel;
import com.km.rmbank.mvp.model.UserModel;
import com.km.rmbank.mvp.view.IUserInfoView;
import com.tencent.connect.UserInfo;

import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/1/25.
 */

public class UserInfoPresenter extends BasePresenter<IUserInfoView,UserInfoModel> {

    public UserInfoPresenter(UserInfoModel mModel) {
        super(mModel);
    }

    public void uploadUserPortrait(String imagePath){
        getMvpModel().uploadUserPortrait("3",imagePath)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        getMvpView().uploadProtraitSuccess(s);
                    }
                }));
    }

    public void updateUserInfo(UserInfoDto userInfoDto){
        getMvpModel().createUserCart(userInfoDto)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        getMvpView().updateUserInfoResult(s);
                    }
                }));
    }


    public void createUserCard(String name,String position,String phone){
        getMvpModel().createUserCard(name,position,phone)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        getMvpView().createUserCardSuccess(s);
                    }
                }));
    }
}
