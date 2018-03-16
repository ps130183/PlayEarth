package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.BannerDto;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.mvp.model.ClubModel;
import com.km.rmbank.mvp.view.IClubView;
import com.km.rmbank.mvp.base.BasePresenter;
import com.ps.commonadapter.adapter.wrapper.LoadMoreWrapper;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/1/17.
 */

public class ClubPresenter extends BasePresenter<IClubView,ClubModel> {

    public ClubPresenter(ClubModel mModel) {
        super(mModel);
    }

    public void getRecommendClubs(int pageNo, final LoadMoreWrapper wrapper){
        getMvpModel().getRecommendClubs(pageNo)
                .subscribe(newSubscriber(new Consumer<List<ClubDto>>() {
                    @Override
                    public void accept(List<ClubDto> clubDtos) throws Exception {
                        getMvpView().showClubs(clubDtos,wrapper);
                    }
                }));
    }

    public void getAllClub(int pageNo, final LoadMoreWrapper wrapper){
        getMvpModel().getClubList(pageNo)
                .subscribe(newSubscriber(new Consumer<List<ClubDto>>() {
                    @Override
                    public void accept(List<ClubDto> clubDtos) throws Exception {
                        getMvpView().showClubs(clubDtos,wrapper);
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

    public void getBannerList(){
        getMvpModel().getBannerList()
                .subscribe(newSubscriber(new Consumer<List<BannerDto>>() {
                    @Override
                    public void accept(List<BannerDto> bannerDtos) throws Exception {
                        getMvpView().showBannerList(bannerDtos);
                    }
                }));
    }

    public void attentionClub(final String clubId){
//        getMvpView().showLoading();
        getMvpModel().attentionClub(clubId)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        getMvpView().attentionClubResult(clubId);
                    }
                }));
    }
}
