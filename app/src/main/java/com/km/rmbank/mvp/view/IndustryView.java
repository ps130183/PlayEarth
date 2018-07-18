package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.IndustryDto;
import com.km.rmbank.mvp.base.MvpView;

import java.util.List;

/**
 * Created by PengSong on 18/7/9.
 */

public interface IndustryView extends MvpView {
    void showIndustryList(List<IndustryDto> industryDtos);
}
