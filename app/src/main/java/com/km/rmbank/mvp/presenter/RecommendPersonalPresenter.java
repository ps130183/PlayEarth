package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.RecommendPersonalDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.RecommendPersonalModel;
import com.km.rmbank.mvp.view.IRecommendPersonalView;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/1/28.
 */

public class RecommendPersonalPresenter extends BasePresenter<IRecommendPersonalView,RecommendPersonalModel> {

    public RecommendPersonalPresenter(RecommendPersonalModel mModel) {
        super(mModel);
    }

    public void getRecommendPersons(final int pageNo){
        getMvpModel().getRecommendPersonas(pageNo)
                .subscribe(newSubscriber(new Consumer<List<RecommendPersonalDto>>() {
                    @Override
                    public void accept(List<RecommendPersonalDto> recommendPersonalDtos) throws Exception {
                        getMvpView().showRecommendPersons(recommendPersonalDtos,pageNo);
                    }
                }));
    }

    public void pariseRecommendPerson(String personId, final int position){
        getMvpModel().pariseRecommendPerson(personId)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        getMvpView().pariseResult(position);
                    }
                }));
    }
}
