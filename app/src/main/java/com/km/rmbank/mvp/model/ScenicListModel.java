package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.MapMarkerDto;
import com.km.rmbank.dto.ScenicDto;
import com.km.rmbank.mvp.base.BaseModel;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/3/16.
 */

public class ScenicListModel extends BaseModel {


    /**
     * 获取 基地 或会所 列表
     * @param type
     * @return
     */
    public Observable<List<MapMarkerDto>> getScenicList(int type){
        return getService().getScenicList(type+"")
                .compose(this.<List<MapMarkerDto>>applySchedulers());
    }
}
