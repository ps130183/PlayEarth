package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.utils.Constant;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/1/24.
 */

public class MeetingListModel extends BaseModel {
    /**
     *
     * @return
     */
    public Observable<List<ActionDto>> getActionLists(){
        return getService().getActionLists(Constant.userLoginInfo.getToken())
                .compose(this.<List<ActionDto>>applySchedulers());
    }
}
