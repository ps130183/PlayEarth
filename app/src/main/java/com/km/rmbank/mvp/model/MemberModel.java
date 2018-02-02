package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.MemberDto;
import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.utils.Constant;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/1/27.
 */

public class MemberModel extends BaseModel {

    /**
     * 获取会员列表
     * @return
     */
    public Observable<List<MemberDto>> getMemberList(){
        return getService().getMemberList("")
                .compose(this.<List<MemberDto>>applySchedulers());
    }

    /**
     * 创建支付订单
     * @param amount
     * @param memberType
     * @return
     */
    public Observable<PayOrderDto> createPayOrder(String amount, String memberType){
        return getService().createPayOrder(Constant.userLoginInfo.getToken(),amount,memberType)
                .compose(this.<PayOrderDto>applySchedulers());
    }
}
