package com.km.rmbank.mvp.model;

import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.utils.Constant;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/8/20.
 */

public class SelectVenueTimeModel extends BaseModel {

    /**
     * 提交预定的场地
     * @param placeId
     * @param startDate
     * @param endDate
     * @return
     */
    public Observable<String> submitBookVenue(String placeId,String startDate,String endDate){
        return getService().submitBookVenue(Constant.userLoginInfo.getToken(),
                placeId,startDate,endDate)
                .compose(this.<String>applySchedulers());
    }
}
