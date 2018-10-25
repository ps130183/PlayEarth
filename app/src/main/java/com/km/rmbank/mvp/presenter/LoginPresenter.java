package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.UserLoginDto;
import com.km.rmbank.mvp.model.LoginModel;
import com.km.rmbank.mvp.view.ILoginView;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.utils.Constant;

import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/1/18.
 */

public class LoginPresenter extends BasePresenter<ILoginView,LoginModel> {

    public LoginPresenter(LoginModel mModel) {
        super(mModel);
    }

    public void login(String mobilePhone,String smsCode){
        getMvpView().showLoading();
        getMvpModel().login(mobilePhone,smsCode)
                .subscribe(newSubscriber(new Consumer<UserLoginDto>() {
                    @Override
                    public void accept(UserLoginDto userInfoDto) throws Exception {
                        userInfoDto.saveToSp();
                        Constant.userLoginInfo.getDataFromSp();
                        if (userInfoDto.isEmpty()){
                            getMvpView().createUserInfo(userInfoDto.getMobilePhone());
                        } else {
                            getMvpView().loginSuccess(userInfoDto);
                        }
                    }
                }));
    }

    public void getSmsCode(String mobilePhone){
        getMvpView().showLoading();
        getMvpModel().getSmsCode(mobilePhone)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        getMvpView().showSmsCode(s);
                    }
                }));
    }

    public void loginByWX(String code){
        getMvpModel().loginByWX(code)
                .subscribe(new Consumer<UserLoginDto>() {
                    @Override
                    public void accept(UserLoginDto userLoginDto) throws Exception {
                        getMvpView().loginWxResult(userLoginDto);
                    }
                });
    }

    public void bindPhoneForWx(String mobilePhone, String smsCode,String unionid){
        getMvpModel().bindPhoneForWx(mobilePhone,smsCode,unionid)
                .subscribe(newSubscriber(new Consumer<UserLoginDto>() {
                    @Override
                    public void accept(UserLoginDto userInfoDto) throws Exception {

                        userInfoDto.saveToSp();
                        Constant.userLoginInfo.getDataFromSp();
                        if (userInfoDto.isEmpty()){
                            getMvpView().createUserInfo(userInfoDto.getMobilePhone());
                        } else {
                            getMvpView().loginSuccess(userInfoDto);
                        }

//                        getMvpView().loginSuccess(userLoginDto);
                    }
                }));
    }

}
