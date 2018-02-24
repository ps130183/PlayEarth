package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.dto.TicketDto;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.utils.Constant;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/2/11.
 */

public class ScenicServiceModel extends BaseModel {

    /**
     * 获取基地活动相关的 优惠券列表
     * @param id
     * @return
     */
    public Observable<List<TicketDto>> getTicketListOfScenic(String id,String clubId){
        return getService().getTicketListOfScenic(Constant.userLoginInfo.getToken(),id,clubId)
                .compose(this.<List<TicketDto>>applySchedulers());
    }
}
