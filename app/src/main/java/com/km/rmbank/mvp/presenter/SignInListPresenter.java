package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.SignInDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.SignInListModel;
import com.km.rmbank.mvp.view.ISignInListView;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/1/24.
 */

public class SignInListPresenter extends BasePresenter<ISignInListView,SignInListModel> {

    public SignInListPresenter(SignInListModel mModel) {
        super(mModel);
    }
    public void loadSignInLists(String actionId) {
        getMvpView().showLoading();
        getMvpModel().getSignInLists(actionId)
                .subscribe(newSubscriber(new Consumer<List<SignInDto>>() {
                    @Override
                    public void accept(List<SignInDto> signInDtos) throws Exception {
                        getMvpView().showSignInLists(signInDtos);
                    }
                }));
    }
}
