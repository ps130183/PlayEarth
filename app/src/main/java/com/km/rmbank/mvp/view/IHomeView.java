package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.AdvertisDto;
import com.km.rmbank.dto.BannerDto;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.dto.HomeRecommendDto;
import com.km.rmbank.dto.MapMarkerDto;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.mvp.base.MvpView;

import java.util.List;

/**
 * Created by PengSong on 18/1/18.
 */

public interface IHomeView extends MvpView {
    void showMapMarkerResult(List<MapMarkerDto> mapMarkerDtos);
    void showHomeRecommend(List<HomeRecommendDto> recommendDtos);
    void showClubInfo(ClubDto clubDto);
    void applyActionSuccess(String actionId,String type);
    void showHomeBanner(List<BannerDto> bannerDtoList);
    void showHomeAdvert(AdvertisDto advertisDto);

    void showUserCard(UserInfoDto cardDto);
}
