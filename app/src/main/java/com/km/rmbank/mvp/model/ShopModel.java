package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.dto.HomeGoodsTypeDto;
import com.km.rmbank.mvp.base.BaseModel;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/1/19.
 */

public class ShopModel extends BaseModel {
    /**
     * 获取当前商城的 商品分类
     * @return
     */
    public Observable<List<HomeGoodsTypeDto>> getGoodsType(){
        return getService().getGoodsType("1")
                .compose(this.<List<HomeGoodsTypeDto>>applySchedulers());
    }

    /**
     * 获取商城的所有商品
     * @param pageNo
     * @param isInIndextActivity
     * @param orderBy
     * @param roleId
     * @return
     */
    public Observable<List<GoodsDto>> getGoodsListOfShoppingNew(final int pageNo, String isInIndextActivity, int orderBy, String roleId){
        return getService().getGoodsListOfShoppingNew(pageNo,isInIndextActivity,orderBy,roleId)
                .compose(this.<List<GoodsDto>>applySchedulers());
    }
}
