package com.km.rmbank.mvp.view;

import com.km.rmbank.mvp.base.MvpView;

/**
 * Created by PengSong on 18/1/25.
 */

public interface IUserInfoView extends MvpView {

    void saveUserInfoSuccess();

    void createUserCardSuccess(String token);
}
