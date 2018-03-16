package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.dto.HomeRecommendDto;
import com.km.rmbank.dto.MapMarkerDto;
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
}
