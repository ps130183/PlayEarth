package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.MasterBannerDto;
import com.km.rmbank.dto.MasterDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.HomeAppointModel;
import com.km.rmbank.mvp.view.IHomeAppointView;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/2/9.
 */

public class HomeAppointPresenter extends BasePresenter<IHomeAppointView,HomeAppointModel> {

    public HomeAppointPresenter(HomeAppointModel mModel) {
        super(mModel);
    }

    public void getMasterList(final int pageNo) {
        getMvpView().showLoading();
        getMvpModel().getMasters(pageNo)
                .subscribe(newSubscriber(new Consumer<List<MasterDto>>() {
                    @Override
                    public void accept(@NonNull List<MasterDto> masterDtos) throws Exception {
                        getMvpView().showMastersInfo(masterDtos,pageNo);
                    }
                }));
    }

    public void getMasterBannerList() {
        getMvpModel().getMasterBanners()
                .subscribe(newSubscriber(new Consumer<List<MasterBannerDto>>() {
                    @Override
                    public void accept(@NonNull List<MasterBannerDto> bannerDtos) throws Exception {
                        getMvpView().showMasterBannerList(bannerDtos);
                    }
                }));
    }

    public void getMasterInfo(final String id) {
        getMvpView().showLoading();
        getMvpModel().getMasterInfo(id)
                .subscribe(newSubscriber(new Consumer<MasterDto>() {
                    @Override
                    public void accept(@NonNull MasterDto masterDto) throws Exception {
                        getMvpView().showMasterInfo(masterDto,id);
                    }
                }));
    }
}

