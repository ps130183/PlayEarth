package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.AttentionDto;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.utils.Constant;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Created by PengSong on 18/1/25.
 */

public class AttentionModel extends BaseModel {
    /**
     * 获取关注商品列表
     * @param pageNo
     * @return
     */
    public Observable<List<AttentionDto>> getAttentionGoodsList(int pageNo){
        return getService()
                .getAttentionGoodsList(Constant.userLoginInfo.getToken(),pageNo)
                .compose(this.<List<AttentionDto>>applySchedulers());
    }

    /**
     * 获取关注商品列表
     * @param pageNo
     * @return
     */
    public Observable<List<GoodsDto>> getAttentionGoodsList1(int pageNo){
        return getService()
                .getAttentionGoodsList1(Constant.userLoginInfo.getToken(),pageNo)
                .compose(this.<List<GoodsDto>>applySchedulers());
    }

    /**
     * 获取俱乐部信息
     * @param clubId
     * @return
     */
    public Observable<ClubDto> getClubInfo(String clubId){
        return getService().getClubInfo(Constant.userLoginInfo.getToken(),clubId)
                .compose(this.<ClubDto>applySchedulers());
    }

}
