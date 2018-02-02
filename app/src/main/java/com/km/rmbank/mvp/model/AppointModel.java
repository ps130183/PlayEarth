package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.dto.AppointDto;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/1/17.
 */

public class AppointModel extends BaseModel {

    /**
     * 获取约吗列表
     * @param pageNo
     * @return
     */
    public Observable<List<ActionDto>> getAppointLists(int pageNo){
        return getService().getActionRecentList(pageNo)
                .compose(this.<List<ActionDto>>applySchedulers());
    }

    /**
     * 获取已报名的活动列表
     * @param pageNo
     * @return
     */
    public Observable<List<ActionDto>> getAppointAppliedLists(int pageNo){
        return getService().getActionAppliedList(Constant.userLoginInfo.getToken(),pageNo)
                .compose(this.<List<ActionDto>>applySchedulers());
    }



}
