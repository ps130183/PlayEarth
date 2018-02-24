package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.MasterBannerDto;
import com.km.rmbank.dto.MasterDto;
import com.km.rmbank.mvp.base.MvpView;

import java.util.List;

/**
 * Created by PengSong on 18/2/9.
 */

public interface IHomeAppointView extends MvpView {
    void showMastersInfo(List<MasterDto> masterDtos, int pageNo);
    void showMasterBannerList(List<MasterBannerDto> bannerDtos);
    void showMasterInfo(MasterDto masterDto,String id);
}
