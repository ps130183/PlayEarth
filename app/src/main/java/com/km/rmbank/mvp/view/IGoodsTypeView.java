package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.HomeGoodsTypeDto;
import com.km.rmbank.mvp.base.MvpView;

import java.util.List;

/**
 * Created by PengSong on 18/1/24.
 */

public interface IGoodsTypeView extends MvpView {
    void showGoodsType(List<HomeGoodsTypeDto> goodsTypeDtos);
}
