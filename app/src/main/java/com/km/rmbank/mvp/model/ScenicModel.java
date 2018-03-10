package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.dto.ScenicServiceDto;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.utils.Constant;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/2/11.
 */

public class ScenicModel extends BaseModel {

    /**
     * 获取基地的基本信息
     * @param id
     * @return
     */
    public Observable<ClubDto> getScenicInfo(String id){
        return getService().getClubInfo(Constant.userLoginInfo.getToken(),id)
                .compose(this.<ClubDto>applySchedulers());
    }

    /**
     * 获取基地的特色服务列表
     * @param id
     * @return
     */
    public Observable<List<ScenicServiceDto>> getScenicServiceList(String id){
        return getService().getCommodityList(id)
                .compose(this.<List<ScenicServiceDto>>applySchedulers());
    }


    /**
     * 获取平台基地活动的特色服务列表
     * @param id
     * @return
     */
    public Observable<List<ScenicServiceDto>> getPlatformScenicServiceList(String id,String activityId){
        return getService().getPlatformCommodityList(id,activityId)
                .compose(this.<List<ScenicServiceDto>>applySchedulers());
    }

    /**
     * 获取基地的特色服务详情
     * @param id
     * @return
     */
    public Observable<ScenicServiceDto> getScenicServiceInfo(String id){
        return getService().getScenicServiceInfo(id)
                .compose(this.<ScenicServiceDto>applySchedulers());
    }


}
