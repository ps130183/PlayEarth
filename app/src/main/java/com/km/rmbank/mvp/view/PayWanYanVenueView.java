package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.mvp.base.MvpView;

/**
 * Created by PengSong on 18/8/22.
 */

public interface PayWanYanVenueView extends MvpView {
    void showResult(PayOrderDto orderDto);
}
