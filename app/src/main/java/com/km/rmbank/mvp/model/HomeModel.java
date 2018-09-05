package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.AdvertisDto;
import com.km.rmbank.dto.BannerDto;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.dto.HomeRecommendDto;
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

    /**
     * 获取首页推荐内容
     * @return
     */
    public Observable<List<HomeRecommendDto>> getHomeRecommend(){
        return getService().getHomeRecommend(1).compose(this.<List<HomeRecommendDto>>applySchedulers());
    }

    /**
     * 获取俱乐部详细信息
     * @param clubId
     * @return
     */
    public Observable<ClubDto> getClubInfo(String clubId){
        return getService().getClubInfo(Constant.userLoginInfo.getToken(),clubId)
                .compose(this.<ClubDto>applySchedulers());
    }

    /**
     * 活动报名
     * @param name
     * @param phone
     * @return
     */
    public Observable<String> applyAction(String activityId,String name,String phone){
        return getService().applyAction(Constant.userLoginInfo.getToken(),activityId,name,phone)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 获取首页banner轮播图
     * @return
     */
    public Observable<List<BannerDto>> getHomeBannerList(){
        return getService().getBannerList("")
                .compose(this.<List<BannerDto>>applySchedulers());
    }

    /**
     * 获取首页的广告
     * @return
     */
    public Observable<AdvertisDto> getAdvertis(){
        return getService().getHomeAdvertis("")
                .compose(this.<AdvertisDto>applySchedulers());
    }

    /**
     * 通过用户的ID 获取 名片信息
     * @param userId
     * @return
     */
    public Observable<UserInfoDto> getUserCardById(String userId){
        return getService().getOtherUserCardById(userId)
                .compose(this.<UserInfoDto>applySchedulers());
    }
}
