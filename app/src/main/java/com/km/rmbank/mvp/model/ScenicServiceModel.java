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


    /**
     * 获取平台基地活动相关的 优惠券列表
     * @param id
     * @return
     */
    public Observable<List<TicketDto>> getPlatformTicketListOfScenic(String id,String clubId,String activityId){
        return getService().getPlatformTicketListOfScenic(Constant.userLoginInfo.getToken(),id,id,clubId,activityId)
                .compose(this.<List<TicketDto>>applySchedulers());
    }

    /**
     * 报名驿站  免费喝茶
     * @param clubId
     * @param personNum
     * @param startDate
     * @return
     */
    public Observable<PayOrderDto> freeTea(String clubId, String personNum, String startDate){
        return getService().freeTea(Constant.userLoginInfo.getToken(),clubId,personNum,startDate)
                .compose(this.<PayOrderDto>applySchedulers());
    }

    /**
     * 分享赚球票
     * @return
     */
    public Observable<String> taskShare(){
        return getService().taskShare(Constant.userLoginInfo.getToken(),2)
                .compose(this.<String>applySchedulers());
    }
}
