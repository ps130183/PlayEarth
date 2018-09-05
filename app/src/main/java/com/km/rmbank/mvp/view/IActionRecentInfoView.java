package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.dto.ActionMemberDto;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.mvp.base.MvpView;

import java.util.List;

/**
 * Created by PengSong on 18/1/27.
 */

public interface IActionRecentInfoView extends MvpView {
    void showActionRecentInfo(ActionDto actionDto);
    void applyActionSuccess(String actionId);
    void followClubSuccess(boolean isFollow);

    void addActiveValueSuccess(String result);

    void showClubInfo(ClubDto clubDto);
}
