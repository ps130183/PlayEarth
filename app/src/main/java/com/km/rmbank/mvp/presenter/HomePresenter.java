package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.UserInfoDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.HomeModel;
import com.km.rmbank.mvp.view.IHomeView;
import com.km.rmbank.utils.Constant;

import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/1/18.
 */

public class HomePresenter extends BasePresenter<IHomeView,HomeModel> {

    public HomePresenter(HomeModel mModel) {
        super(mModel);
    }

    public void getUserInfo(){
        getMvpModel().getUserInfo()
                .subscribe(newSubscriber(new Consumer<UserInfoDto>() {
                    @Override
                    public void accept(UserInfoDto userInfoDto) throws Exception {
                        Constant.userInfo = userInfoDto;
                    }
                }));
    }
}
