package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.UserLoginDto;
import com.km.rmbank.mvp.base.BaseModel;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/1/18.
 */

public class LoginModel extends BaseModel {

    /**
     * 登录
     * @param mobilePhone
     * @param smsCode
     * @return
     */
    public Observable<UserLoginDto> login(String mobilePhone, String smsCode){
        return getService().login(mobilePhone,smsCode)
                .compose(this.<UserLoginDto>applySchedulers());
    }


    /**
     * 获取短信验证码
     * @param mobilePhone
     * @return
     */
    public Observable<String> getSmsCode(String mobilePhone){
        return getService().getPhoneCode(mobilePhone)
                .compose(this.<String>applySchedulers());
    }


}
