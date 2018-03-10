package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.dto.ScenicDto;
import com.km.rmbank.dto.ScenicServiceDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.ScenicModel;
import com.km.rmbank.mvp.view.IScenicView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/2/11.
 */

public class ScenicPresenter extends BasePresenter<IScenicView,ScenicModel> {

    public ScenicPresenter(ScenicModel mModel) {
        super(mModel);
    }

    public void getScenicInfo(String id){
        getMvpView().showLoading();
        Observable<ClubDto> scenicInfo = getMvpModel().getScenicInfo(id);
        Observable<List<ScenicServiceDto>> scenicServiceList = getMvpModel().getScenicServiceList(id);

        Observable.zip(scenicInfo, scenicServiceList, new BiFunction<ClubDto, List<ScenicServiceDto>, ScenicDto>() {
            @Override
            public ScenicDto apply(ClubDto clubDto, List<ScenicServiceDto> scenicServiceDtos) throws Exception {
                return new ScenicDto(clubDto,scenicServiceDtos);
            }
        }).subscribe(newSubscriber(new Consumer<ScenicDto>() {
            @Override
            public void accept(ScenicDto scenicDto) throws Exception {
                getMvpView().showScenicInfo(scenicDto);
            }
        }));

    }

    public void getPlatformScenicInfo(String id,String activityId){
        getMvpView().showLoading();
        Observable<ClubDto> scenicInfo = getMvpModel().getScenicInfo(id);
        Observable<List<ScenicServiceDto>> scenicServiceList = getMvpModel().getPlatformScenicServiceList(id,activityId);

        Observable.zip(scenicInfo, scenicServiceList, new BiFunction<ClubDto, List<ScenicServiceDto>, ScenicDto>() {
            @Override
            public ScenicDto apply(ClubDto clubDto, List<ScenicServiceDto> scenicServiceDtos) throws Exception {
                return new ScenicDto(clubDto,scenicServiceDtos);
            }
        }).subscribe(newSubscriber(new Consumer<ScenicDto>() {
            @Override
            public void accept(ScenicDto scenicDto) throws Exception {
                getMvpView().showScenicInfo(scenicDto);
            }
        }));

    }

}
