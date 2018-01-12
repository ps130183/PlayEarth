package com.km.rmbank.mvp.presenter;


import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.MainModel;
import com.km.rmbank.mvp.view.MainView;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/1/8.
 */

public class MainPresenter extends BasePresenter<MainView,MainModel> {

    public MainPresenter(MainModel mModel) {
        super(mModel);
    }

    public void getClubs(int page){
        getMvpModel().loadClubs(page)
                .subscribe(newSubscriber(new Consumer<List<ClubDto>>() {
                    @Override
                    public void accept(List<ClubDto> clubDtos) throws Exception {
                        getMvpView().showClubs(clubDtos);
                    }
                }));
    }
}
