package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.IndustryDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.IndustryModel;
import com.km.rmbank.mvp.view.IndustryView;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/7/9.
 */

public class IndustryPresenter extends BasePresenter<IndustryView,IndustryModel> {

    public IndustryPresenter(IndustryModel mModel) {
        super(mModel);
    }

    public void getIndustryList(){
        getMvpModel().getIndustryList()
                .subscribe(new Consumer<List<IndustryDto>>() {
                    @Override
                    public void accept(List<IndustryDto> industryDtos) throws Exception {
                        getMvpView().showIndustryList(industryDtos);
                    }
                });
    }
}
