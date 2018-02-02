package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.UserLoginDto;
import com.km.rmbank.mvp.base.MvpView;

/**
 * Created by PengSong on 18/1/18.
 */

public interface ILoginView extends MvpView {
    void showSmsCode(String smsCode);
    void loginSuccess(UserLoginDto userInfoDto);
    void createUserInfo(String userPhone);
}
