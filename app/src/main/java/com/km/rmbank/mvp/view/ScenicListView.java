package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.MapMarkerDto;
import com.km.rmbank.mvp.base.MvpView;

import java.util.List;

/**
 * Created by PengSong on 18/3/16.
 */

public interface ScenicListView extends MvpView {
    void showScenicList(List<MapMarkerDto> mapMarkerDtos);
    void showMapMarkerResult(List<MapMarkerDto> mapMarkerDtos);
}
