package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.mvp.base.BaseModel;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/1/19.
 */

public class SearchModel extends BaseModel {
    /**
     * 搜索商品
     * @param pageNo
     * @param name
     * @return
     */
    public Observable<List<GoodsDto>> getGoodsListOfSearch(final int pageNo, String name){
        return getService().getGoodsListOfSearch(pageNo,name)
                .compose(this.<List<GoodsDto>>applySchedulers());
    }
}
