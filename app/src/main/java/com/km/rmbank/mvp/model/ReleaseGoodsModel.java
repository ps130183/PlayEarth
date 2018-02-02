package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.utils.Constant;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/1/24.
 */

public class ReleaseGoodsModel extends BaseModel {
    /**
     * 获取商家的商品列表
     * @param pageNo
     * @return
     */
    public Observable<List<GoodsDto>> getGoodsListOfShop(int pageNo){
        return getService().getGoodsListOfShop(Constant.userLoginInfo.getToken(),pageNo)
                .compose(this.<List<GoodsDto>>applySchedulers());
    }

    /**
     * 下架商品
     * @param productNo
     * @return
     */
    public Observable<String> goodsSlodOut(String productNo){
        return getService().goodsSoldOut(Constant.userLoginInfo.getToken(),productNo)
                .compose(this.<String>applySchedulers());
    }
}
