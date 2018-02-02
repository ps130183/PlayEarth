package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.dto.AppointDto;
import com.km.rmbank.mvp.base.MvpView;
import com.ps.commonadapter.adapter.wrapper.LoadMoreWrapper;

import java.util.List;

/**
 * Created by PengSong on 18/1/17.
 */

public interface AppointView extends MvpView {
    void showAppointList(LoadMoreWrapper wrapper,List<AppointDto> appointDtos);
}
