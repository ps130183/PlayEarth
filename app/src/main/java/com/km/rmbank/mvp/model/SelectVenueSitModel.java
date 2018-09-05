package com.km.rmbank.mvp.model;

import com.km.rmbank.entity.BookVenueSitEntity;
import com.km.rmbank.mvp.base.BaseModel;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/8/20.
 */

public class SelectVenueSitModel extends BaseModel {

    /**
     * 获取可预订场地的列表
     * @param type
     * @return
     */
    public Observable<List<BookVenueSitEntity>> getBookVenueSitList(int type){
        return getService().getBookVenueSitList(type)
                .compose(this.<List<BookVenueSitEntity>>applySchedulers());
    }
}
