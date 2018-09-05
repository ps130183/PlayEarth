package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.dto.AppointDto;
import com.km.rmbank.dto.MapMarkerDto;
import com.km.rmbank.entity.BookVenueSitEntity;
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
    public Observable<List<AppointDto>> getAppointLists(int pageNo,int newType){
        return getService().getActionRecentList(pageNo,newType)
                .compose(this.<List<AppointDto>>applySchedulers());
    }

    /**
     * 通过场地  获取约吗列表
     * @param pageNo
     * @return
     */
    public Observable<List<AppointDto>> getActionListByPlace(int pageNo,String placeId,String clubId){
        return getService().getActionListByPlace(pageNo,placeId,clubId)
                .compose(this.<List<AppointDto>>applySchedulers());
    }

    /**
     * 获取已报名的活动列表
     * @param pageNo
     * @return
     */
    public Observable<List<AppointDto>> getAppointAppliedLists(String timeType,int pageNo){
        return getService().getActionAppliedList(Constant.userLoginInfo.getToken(),pageNo,timeType)
                .compose(this.<List<AppointDto>>applySchedulers());
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
     * 获取可预订场地的列表
     * @param type
     * @return
     */
    public Observable<List<BookVenueSitEntity>> getBookVenueSitList(int type){
        return getService().getBookVenueSitList(type)
                .compose(this.<List<BookVenueSitEntity>>applySchedulers());
    }

    /**
     * 获取约么基地列表
     * @return
     */
    public Observable<List<MapMarkerDto>> getBaseList(){
        return getService().getBaseList(2,1)
                .compose(this.<List<MapMarkerDto>>applySchedulers());
    }



}
