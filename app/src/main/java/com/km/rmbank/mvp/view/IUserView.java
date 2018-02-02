package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.mvp.base.MvpView;

/**
 * Created by PengSong on 18/1/19.
 */

public interface IUserView extends MvpView {
    void showUserInfo(UserInfoDto userInfoDto);
    void showClubInf(ClubDto clubDto);
}
