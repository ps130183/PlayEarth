package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.InformationDto;
import com.km.rmbank.mvp.base.MvpView;
import com.ps.commonadapter.adapter.wrapper.LoadMoreWrapper;

import java.util.List;

/**
 * Created by PengSong on 18/3/15.
 */

public interface InformationView extends MvpView {

    void showInformation(int pageNo, List<InformationDto> informationDtos, LoadMoreWrapper wrapper);
}
