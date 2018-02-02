package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.IntegralDetailsDto;
import com.km.rmbank.dto.IntegralDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.IntegralModel;
import com.km.rmbank.mvp.view.IIntegralView;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/1/25.
 */

public class IntegralPresenter extends BasePresenter<IIntegralView,IntegralModel> {

    public IntegralPresenter(IntegralModel mModel) {
        super(mModel);
    }

    public void getUserIntegralInfo() {
        getMvpModel().getIntegralInfo()
                .subscribe(newSubscriber(new Consumer<IntegralDto>() {
                    @Override
                    public void accept(@NonNull IntegralDto integralDto) throws Exception {
                        getMvpView().showUserIntegralInfo(integralDto);
                    }
                }));
    }

    public void getIntegralDetails(final int pageNo) {
        getMvpModel().getIntegralDetailsList(pageNo)
                .subscribe(newSubscriber(new Consumer<List<IntegralDetailsDto>>() {
                    @Override
                    public void accept(@NonNull List<IntegralDetailsDto> integralDetailsDtos) throws Exception {
                        getMvpView().showIntegralDetails(integralDetailsDtos,pageNo);
                    }
                }));
    }
}
