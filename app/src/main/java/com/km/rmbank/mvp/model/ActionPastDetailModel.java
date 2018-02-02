package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.ActionPastDto;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.utils.Constant;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Created by PengSong on 18/1/28.
 */

public class ActionPastDetailModel extends BaseModel {
    /**
     * 获取往期资讯 详情
     * @param id
     * @return
     */
    public Observable<ActionPastDto> getActionPastDetail(String id){
        return getService().getActionPastDetail(id)
                .compose(this.<ActionPastDto>applySchedulers());
    }

    /**
     * 获取俱乐部数据
     * @param clubId
     * @return
     */
    public Observable<ClubDto> getClubInfo(String clubId){
        return getService().getClubInfo(Constant.userLoginInfo.getToken(),clubId)
                .compose(this.<ClubDto>applySchedulers());
    }

}
