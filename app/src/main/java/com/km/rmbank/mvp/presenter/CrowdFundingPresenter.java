package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.MyCrowdFundingInfoDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.CrowdFundingModel;
import com.km.rmbank.mvp.view.CrowdFundingView;

import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/7/31.
 */

public class CrowdFundingPresenter extends BasePresenter<CrowdFundingView,CrowdFundingModel> {

    public CrowdFundingPresenter(CrowdFundingModel mModel) {
        super(mModel);
    }

    public void getMyCrowdFundingInfo(){
        getMvpView().showLoading();
        getMvpModel().getMyCrowdFundingInfo()
                .subscribe(newSubscriber(new Consumer<MyCrowdFundingInfoDto>() {
                    @Override
                    public void accept(MyCrowdFundingInfoDto crowdFundingInfoDto) throws Exception {
                        getMvpView().showMyCrowdFundingInfo(crowdFundingInfoDto);
                    }
                }));
    }
}
