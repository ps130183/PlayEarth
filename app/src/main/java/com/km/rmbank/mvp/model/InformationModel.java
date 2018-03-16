package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.InformationDto;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.utils.Constant;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/3/15.
 */

public class InformationModel extends BaseModel {

    /**
     * 获取资讯列表
     * @param pageNo
     * @return
     */
    public Observable<List<InformationDto>> getInformationList(int pageNo){
        return getService().getInformationList(pageNo)
                .compose(this.<List<InformationDto>>applySchedulers());
    }
}
