package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.GoodsDetailsDto;
import com.km.rmbank.dto.ShoppingCartDto;
import com.km.rmbank.mvp.base.MvpView;

import java.util.List;

/**
 * Created by PengSong on 18/1/19.
 */

public interface IShoppingCartView extends MvpView {
    void showShoppingCartDatas(List<ShoppingCartDto> shoppingCartEntities);
    void updateGoodsCountSuccess(GoodsDetailsDto goodsDto, String optiontType);
    void createOrderSuccess(List<ShoppingCartDto> shoppingCartDtos);
    void deleteSuccess(int positionOnParent, int positionOnSub);
}
