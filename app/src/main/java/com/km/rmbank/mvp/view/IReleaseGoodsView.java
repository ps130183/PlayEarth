package com.km.rmbank.mvp.view;

import com.daimajia.swipe.SwipeLayout;
import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.mvp.base.MvpView;

import java.util.List;

/**
 * Created by PengSong on 18/1/24.
 */

public interface IReleaseGoodsView extends MvpView {
    void showRepleaseGoods(List<GoodsDto> goodsEntities, int pageNo);
    void goodsSoldOutSuccess(GoodsDto goodsDto,SwipeLayout swipeLayout);
}
