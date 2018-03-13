package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.HomeGoodsTypeDto;
import com.km.rmbank.mvp.base.BaseModel;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/1/24.
 */

public class GoodsTypeModel extends BaseModel {
    /**
     * 获取商品分类
     * @return
     */
    public Observable<List<HomeGoodsTypeDto>> getGoodsTypeForCreateGoods(){
        return getService().getGoodsTypeForCreateGoods("")
                .compose(this.<List<HomeGoodsTypeDto>>applySchedulers());
    }
}
