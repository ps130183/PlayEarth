package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.MasterBannerDto;
import com.km.rmbank.dto.MasterDto;
import com.km.rmbank.mvp.base.BaseModel;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Created by PengSong on 18/2/9.
 */

public class HomeAppointModel extends BaseModel {

    /**
     * 获取
     * @param pageNo
     * @return
     */
    public Observable<List<MasterDto>> getMasters(int pageNo){
        return getService().getMasters(pageNo)
                .compose(this.<List<MasterDto>>applySchedulers());
    }

    /**
     * 获取大咖banner列表
     * @return
     */
    public Observable<List<MasterBannerDto>> getMasterBanners(){
        return getService().getMasterBanners(0)
                .compose(this.<List<MasterBannerDto>>applySchedulers());
    }

    /**
     * 获取大咖信息
     * @param id
     * @return
     */
    public Observable<MasterDto> getMasterInfo(String id){
        return getService().getMasterInfo(id)
                .compose(this.<MasterDto>applySchedulers());
    }
}
