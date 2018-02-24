package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.MapMarkerDto;
import com.km.rmbank.mvp.base.MvpView;

import java.util.List;

/**
 * Created by PengSong on 18/1/18.
 */

public interface IHomeView extends MvpView {
    void showMapMarkerResult(List<MapMarkerDto> mapMarkerDtos);
}
