package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.mvp.base.MvpView;

/**
 * Created by PengSong on 18/9/2.
 */

public interface ISearchCompanyView extends MvpView {
    void showOtherUserInfo(UserInfoDto otherUserInfo);
    void showUserInfo(UserInfoDto userInfo);
}
