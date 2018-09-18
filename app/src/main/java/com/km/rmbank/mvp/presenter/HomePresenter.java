package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.AdvertisDto;
import com.km.rmbank.dto.BannerDto;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.dto.HomeRecommendDto;
import com.km.rmbank.dto.MapMarkerDto;
import com.km.rmbank.dto.MessageAllDto;
import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.HomeModel;
import com.km.rmbank.mvp.view.IHomeView;
import com.km.rmbank.utils.Constant;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/1/18.
 */

public class HomePresenter extends BasePresenter<IHomeView,HomeModel> {

    public HomePresenter(HomeModel mModel) {
        super(mModel);
    }

    public void getUserInfo(){
        getMvpModel().getUserInfo()
                .subscribe(newSubscriber(new Consumer<UserInfoDto>() {
                    @Override
                    public void accept(UserInfoDto userInfoDto) throws Exception {
                        Constant.userInfo = userInfoDto;
                    }
                }));
    }

    public void getMapMarkers(){
        getMvpModel().getMapMarkers()
                .subscribe(newSubscriber(new Consumer<List<MapMarkerDto>>() {
                    @Override
                    public void accept(List<MapMarkerDto> mapMarkerDtos) throws Exception {
                        getMvpView().showMapMarkerResult(mapMarkerDtos);
                    }
                }));
    }

    public void getHomeRecommend(){
        getMvpModel().getHomeRecommend()
                .subscribe(newSubscriber(new Consumer<List<HomeRecommendDto>>() {
                    @Override
                    public void accept(List<HomeRecommendDto> homeRecommendDtos) throws Exception {
                        getMvpView().showHomeRecommend(homeRecommendDtos);
                    }
                }));
    }

    public void getClubInfo(String clubId){
        getMvpModel().getClubInfo(clubId)
                .subscribe(newSubscriber(new Consumer<ClubDto>() {
                    @Override
                    public void accept(ClubDto clubDto) throws Exception {
                        getMvpView().showClubInfo(clubDto);
                    }
                }));
    }

    public void applyAction(final String activityId, String name, String phone, final String type) {
        getMvpModel().applyAction(activityId,name,phone)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        getMvpView().applyActionSuccess(activityId,type);
                    }
                }));
    }

    public void getHomeBannerList(){
        getMvpModel().getHomeBannerList()
                .subscribe(newSubscriber(new Consumer<List<BannerDto>>() {
                    @Override
                    public void accept(List<BannerDto> bannerDtos) throws Exception {
                        getMvpView().showHomeBanner(bannerDtos);
                    }
                }));
    }

    public void getHomeAdvert(){
        getMvpModel().getAdvertis()
                .subscribe(newSubscriber(new Consumer<AdvertisDto>() {
                    @Override
                    public void accept(AdvertisDto advertisDto) throws Exception {
                        getMvpView().showHomeAdvert(advertisDto);
                    }
                }));
    }

    public void getUserCardById(String userId){
//        getMvpView().showLoading();
        getMvpModel().getUserCardById(userId)
                .subscribe(newSubscriber(new Consumer<UserInfoDto>() {
                    @Override
                    public void accept(UserInfoDto userCardDto) throws Exception {
                        getMvpView().showUserCard(userCardDto);
                    }
                }));
    }

    public void getAllMessage(){
        getMvpModel().getMessageAllInfo()
                .subscribe(newSubscriber(new Consumer<MessageAllDto>() {
                    @Override
                    public void accept(MessageAllDto messageAllDto) throws Exception {
                        getMvpView().showMessageAl(messageAllDto);
                    }
                }));
    }
}
