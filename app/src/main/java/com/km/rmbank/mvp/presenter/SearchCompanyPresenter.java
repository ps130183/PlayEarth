package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.SearchCompanyModel;
import com.km.rmbank.mvp.view.ISearchCompanyView;

import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/9/2.
 */

public class SearchCompanyPresenter extends BasePresenter<ISearchCompanyView,SearchCompanyModel> {

    public SearchCompanyPresenter(SearchCompanyModel mModel) {
        super(mModel);
    }

    public void getUserCardById(String userId){
//        getMvpView().showLoading();
        getMvpModel().getUserCardById(userId)
                .subscribe(newSubscriber(new Consumer<UserInfoDto>() {
                    @Override
                    public void accept(UserInfoDto userCardDto) throws Exception {
                        getMvpView().showOtherUserInfo(userCardDto);
                    }
                }));
    }

    public void getUserInfo(){
        getMvpModel().getUserInfo()
                .subscribe(newSubscriber(new Consumer<UserInfoDto>() {
                    @Override
                    public void accept(UserInfoDto userInfoDto) throws Exception {
                        getMvpView().showUserInfo(userInfoDto);
                    }
                }));
    }
}
