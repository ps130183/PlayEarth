package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.utils.Constant;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Created by PengSong on 18/1/27.
 */

public class ActionRecentInfoModel extends BaseModel {
    /**
     * 获取俱乐部 发布近期活动的详细细腻
     * @param actionId
     * @return
     */
    public Observable<ActionDto> getActionRecentInfo(String actionId){
        return getService().getActionRecentInfo(Constant.userLoginInfo.getToken(),actionId)
                .compose(this.<ActionDto>applySchedulers());
    }

    /**
     * 活动报名
     * @param name
     * @param phone
     * @return
     */
    public Observable<String> applyAction(String activityId,String name,String phone){
        return getService().applyAction(Constant.userLoginInfo.getToken(),activityId,name,phone)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 关注商品
     * @param productNo
     * @return
     */
    public Observable<String> followGodos(String productNo,String clubId){
        return getService().followGoods(Constant.userLoginInfo.getToken(),productNo,clubId)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 分享 近期活动  增加活跃值
     * @param activityId
     * @return
     */
    public Observable<String> addActiveValue(String activityId){
        return getService().addActiveValue(Constant.userLoginInfo.getToken(),activityId)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 获取俱乐部详情
     * @param clubId
     * @return
     */
    public Observable<ClubDto> getClubInfo(String clubId){
        return getService().getClubInfo(Constant.userLoginInfo.getToken(),clubId)
                .compose(this.<ClubDto>applySchedulers());
    }
}
