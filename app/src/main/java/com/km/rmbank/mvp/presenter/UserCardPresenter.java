package com.km.rmbank.mvp.presenter;

import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.UserCardModel;
import com.km.rmbank.mvp.view.UserCardView;

import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/7/12.
 */

public class UserCardPresenter extends BasePresenter<UserCardView,UserCardModel> {
    public UserCardPresenter(UserCardModel mModel) {
        super(mModel);
    }

    public void taskShare(){
        getMvpModel().taskShare()
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                    }
                });
    }
}
