package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.OrderDto;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.utils.Constant;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/1/24.
 */

public class OrderManagerModel extends BaseModel {

    /**
     * 获取商品列表
     * @param pageNo
     * @return
     */
    public Observable<List<OrderDto>> getSellGoodsList(int pageNo){
        return getService().getSellGoodsList(Constant.userLoginInfo.getToken(),pageNo)
                .compose(this.<List<OrderDto>>applySchedulers());
    }
}
