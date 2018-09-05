package com.km.rmbank.mvp.presenter;

import com.km.rmbank.entity.BookVenueSitEntity;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.SelectVenueSitModel;
import com.km.rmbank.mvp.view.SelectVenueSitView;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/8/20.
 */

public class SelectVenueSitPresenter extends BasePresenter<SelectVenueSitView,SelectVenueSitModel> {

    public SelectVenueSitPresenter(SelectVenueSitModel mModel) {
        super(mModel);
    }

    public void getBookVenueSitList(int type){
        getMvpModel().getBookVenueSitList(type)
                .subscribe(newSubscriber(new Consumer<List<BookVenueSitEntity>>() {
                    @Override
                    public void accept(List<BookVenueSitEntity> bookVenueSitEntities) throws Exception {
                        getMvpView().showBookVenueSitList(bookVenueSitEntities);
                    }
                }));
    }
}
