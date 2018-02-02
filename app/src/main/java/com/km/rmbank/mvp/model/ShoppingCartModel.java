package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.GoodsDetailsDto;
import com.km.rmbank.dto.ShoppingCartDto;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.utils.Constant;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/1/19.
 */

public class ShoppingCartModel extends BaseModel {

    /**
     * 获取购物车商品数据
     * @return
     */
    public Observable<List<ShoppingCartDto>> getShoppingCartList(){
        return getService().getShoppingCartList(Constant.userLoginInfo.getToken())
                .compose(this.<List<ShoppingCartDto>>applySchedulers());
    }

    /**
     * 更新购物车商品数量
     * @param productNo
     * @param optionType
     * @return
     */
    public Observable<String> updateCountOnShopCartForGoods(String productNo,String optionType){
        return getService().updateCountOnShopCartForGoods(Constant.userLoginInfo.getToken(),productNo,optionType)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 创建支付订单
     * @param productNos
     * @return
     */
    public Observable<List<ShoppingCartDto>> createOrder(String productNos){
        return getService().createOrder(Constant.userLoginInfo.getToken(),productNos)
                .compose(this.<List<ShoppingCartDto>>applySchedulers());
    }

    /**
     * 删除购物车商品
     * @param goodsDetailsDto
     * @return
     */
    public Observable<String> deleteShoppingCartGoods(GoodsDetailsDto goodsDetailsDto){
        return getService().deleteShoppingCartGoods(Constant.userLoginInfo.getToken(),goodsDetailsDto.getProductNo())
                .compose(this.<String>applySchedulers());
    }
}
