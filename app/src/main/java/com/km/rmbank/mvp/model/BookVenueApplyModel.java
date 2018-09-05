package com.km.rmbank.mvp.model;

import com.km.rmbank.entity.BookVenueApplyDto;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.utils.Constant;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/8/20.
 */

public class BookVenueApplyModel extends BaseModel {

    /**
     * 获取预定场地的列表
     * @param status
     * @return
     */
    public Observable<List<BookVenueApplyDto>> getBookVenueApplyList(String status){
        return getService().getBookVenueApplyList(Constant.userLoginInfo.getToken(),status)
                .compose(this.<List<BookVenueApplyDto>>applySchedulers());
    }

    /**
     * 取消场地的预约申请
     * @param id
     * @return
     */
    public Observable<String> cancelBookVenueApply(String id){
        return getService().cancelBookVenueApply(Constant.userLoginInfo.getToken(),id)
                .compose(this.<String>applySchedulers());
    }
}
