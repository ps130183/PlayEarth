package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.MyCrowdFundingInfoDto;
import com.km.rmbank.mvp.base.MvpView;

/**
 * Created by PengSong on 18/7/31.
 */

public interface CrowdFundingView extends MvpView {
    void showMyCrowdFundingInfo(MyCrowdFundingInfoDto crowdFundingInfoDto);
}
