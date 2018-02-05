package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.CalendarActionsDto;
import com.km.rmbank.mvp.base.MvpView;

import java.util.List;

/**
 * Created by PengSong on 18/2/5.
 */

public interface IClubActionsView extends MvpView {
    void showClubActions(List<CalendarActionsDto> calendarActionsDtos,String startDate);
}
