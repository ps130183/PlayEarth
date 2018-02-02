package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.ActionMemberDto;
import com.km.rmbank.dto.ActionMemberNumDto;
import com.km.rmbank.mvp.base.BaseModel;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Created by PengSong on 18/1/27.
 */

public class ActionJoinMemberModel extends BaseModel {
    /**
     * 获取俱乐部 活动 的参加人员 列表
     * @param actionId
     * @return
     */
    public Observable<List<ActionMemberDto>> getActionMemberList(String actionId, int pageNo){
        return getService().getActionMemberList(actionId,pageNo)
                .compose(this.<List<ActionMemberDto>>applySchedulers());
    }
    /**
     * 获取 近期活动报名人数
     * @param actionId
     * @return
     */
    public Observable<ActionMemberNumDto> getActionMemberNum(String actionId){
        return getService().getActionMemberNum(actionId)
                .compose(this.<ActionMemberNumDto>applySchedulers());
    }
}
