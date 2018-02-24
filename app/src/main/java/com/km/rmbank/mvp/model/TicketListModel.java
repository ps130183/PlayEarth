package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.TicketDto;
import com.km.rmbank.dto.TicketUseRecordDto;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.utils.Constant;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/2/10.
 */

public class TicketListModel extends BaseModel {

    /**
     * 获取个人券的 列表
     * @param pageNo
     * @return
     */
    public Observable<List<TicketDto>> getAllTicketList(int pageNo){
        return getService().getAllTicketList(Constant.userLoginInfo.getToken(),pageNo)
                .compose(this.<List<TicketDto>>applySchedulers());
    }

    /**
     * 获取券的使用记录
     * @param ticketNo
     * @return
     */
    public Observable<List<TicketUseRecordDto>> getTicketUseRecordList(String ticketNo){
        return getService().getTicketUseRecordList(Constant.userLoginInfo.getToken(),ticketNo)
                .compose(this.<List<TicketUseRecordDto>>applySchedulers());
    }
}
