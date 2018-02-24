package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.MapMarkerDto;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.utils.Constant;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/1/18.
 */

public class HomeModel extends BaseModel {
    /**
     * 获取用户信息
     * @return
     */
    public Observable<UserInfoDto> getUserInfo(){
        return getService().getUserInfo(Constant.userLoginInfo.getToken())
                .compose(this.<UserInfoDto>applySchedulers());
    }

    /**
     * 获取地图上 所有的 基地 会所 数据
     * @return
     */
    public Observable<List<MapMarkerDto>> getMapMarkers(){
        return getService().getMapMarkers("").compose(this.<List<MapMarkerDto>>applySchedulers());
    }
}
