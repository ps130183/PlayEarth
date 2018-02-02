package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.dto.GoodsTypeDto;
import com.km.rmbank.dto.HomeGoodsTypeDto;
import com.km.rmbank.mvp.base.MvpView;

import java.util.List;

/**
 * Created by PengSong on 18/1/19.
 */

public interface IShopView extends MvpView {
    void getGoodsTypeSuccess(List<GoodsTypeDto> goodsTypeDtos);
    void showGoodsType(List<HomeGoodsTypeDto> goodsTypeDtos);
    void showGoodsList(int pageNo,List<GoodsDto> goodsDtos);
}
