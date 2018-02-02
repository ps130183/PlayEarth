package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.GoodsDetailsDto;
import com.km.rmbank.dto.UserCardDto;
import com.km.rmbank.mvp.base.MvpView;

/**
 * Created by PengSong on 18/1/19.
 */

public interface IGoodsView extends MvpView {
    void showGoodsDetails(GoodsDetailsDto goodsDetailsDto);
    void followGoodsSuccess();
    void addShoppingCartSuccess();
//    void showShopUserCartInfo(UserCardDto userCardDto);
}
