package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.EarthTaskDetailsDto;
import com.km.rmbank.dto.EarthTaskDto;
import com.km.rmbank.dto.TaskSignInDto;
import com.km.rmbank.entity.EarthTaskEntity;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.utils.Constant;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/7/12.
 */

public class EarthTaskModel extends BaseModel {

    /**
     * 获取球票任务列表
     * @return
     */
    public Observable<List<EarthTaskDto>> getEarthTaskList(){
        return getService().getEarthTaskList(Constant.userLoginInfo.getToken())
                .compose(this.<List<EarthTaskDto>>applySchedulers());
    }

    /**
     * 球票签到
     * @return
     */
    public Observable<TaskSignInDto> tastSignIn(){
        return getService().taskSignIn(Constant.userLoginInfo.getToken())
                .compose(this.<TaskSignInDto>applySchedulers());
    }

    /**
     * 球票明细
     * @return
     */
    public Observable<List<EarthTaskDetailsDto>> getTaskDetailList(){
        return getService().getEarthTaskDetailList(Constant.userLoginInfo.getToken())
                .compose(this.<List<EarthTaskDetailsDto>>applySchedulers());
    }
 }
