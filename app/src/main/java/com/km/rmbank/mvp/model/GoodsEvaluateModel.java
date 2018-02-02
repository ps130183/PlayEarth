package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.EvaluateDto;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.utils.Constant;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/1/19.
 */

public class GoodsEvaluateModel extends BaseModel {

    /**
     * 获取评论列表
     * @param productNo
     * @param pageNo
     * @return
     */
    public Observable<List<EvaluateDto>> getEvaluateList(String productNo,int pageNo){
        return getService().getEvaluateList(Constant.userLoginInfo.getToken(),productNo,pageNo)
                .compose(this.<List<EvaluateDto>>applySchedulers());
    }

    /**
     * 评价商品
     * @param orderNo
     * @param content
     * @return
     */
    public Observable<String> evaluateGoods(String orderNo,String content){
        return getService().evaluateGoods(Constant.userLoginInfo.getToken(),orderNo,content)
                .compose(this.<String>applySchedulers());
    }
}
