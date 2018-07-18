package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.IndustryDto;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.utils.Constant;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/7/9.
 */

public class IndustryModel extends BaseModel {

    /**
     * 获取行业信息
     * @return
     */
    public Observable<List<IndustryDto>> getIndustryList(){
        return getService().getIndustryList(Constant.userLoginInfo.getToken())
                .compose(this.<List<IndustryDto>>applySchedulers());
    }
}
