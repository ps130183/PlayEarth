package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.CalendarActionsDto;
import com.km.rmbank.mvp.base.BaseModel;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/2/5.
 */

public class ClubActionsModel extends BaseModel {

    /**
     * 根据日期获取 俱乐部活动列表
     * @param clubId
     * @param startDate
     * @return
     */
    public Observable<List<CalendarActionsDto>> getClubActionsByMonth(String clubId, String startDate){
        return getService().getClubActionsByMonth(clubId,startDate).compose(this.<List<CalendarActionsDto>>applySchedulers());
    }
}
