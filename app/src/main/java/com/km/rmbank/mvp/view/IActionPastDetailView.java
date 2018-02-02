package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.ActionPastDto;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.mvp.base.MvpView;

/**
 * Created by PengSong on 18/1/28.
 */

public interface IActionPastDetailView extends MvpView {
    void showActionPastDetails(ActionPastDto actionPastDto);
    void showClubInfo(ClubDto clubDto);
}
