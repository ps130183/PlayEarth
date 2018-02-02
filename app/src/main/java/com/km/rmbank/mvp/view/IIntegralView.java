package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.IntegralDetailsDto;
import com.km.rmbank.dto.IntegralDto;
import com.km.rmbank.mvp.base.MvpView;

import java.util.List;

/**
 * Created by PengSong on 18/1/25.
 */

public interface IIntegralView extends MvpView {
    void showUserIntegralInfo(IntegralDto integralDto);
    void showIntegralDetails(List<IntegralDetailsDto> integralDetailsDtos, int pageNo);
}
