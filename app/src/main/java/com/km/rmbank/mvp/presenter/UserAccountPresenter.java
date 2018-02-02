package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.UserAccountDetailDto;
import com.km.rmbank.dto.UserBalanceDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.UserAccountModel;
import com.km.rmbank.mvp.view.IUserAccountView;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/1/25.
 */

public class UserAccountPresenter extends BasePresenter<IUserAccountView,UserAccountModel> {

    public UserAccountPresenter(UserAccountModel mModel) {
        super(mModel);
    }

    public void loadBalance() {
        getMvpModel().getUserAccountBalance()
                .subscribe(newSubscriber(new Consumer<UserBalanceDto>() {
                    @Override
                    public void accept(@NonNull UserBalanceDto userBalanceDto) throws Exception {
                        getMvpView().showBalance(userBalanceDto);
                    }
                }));
    }

    public void loadAccountDetail(final int pageNo) {
        getMvpModel().getUserAccountDetail(pageNo)
                .subscribe(newSubscriber(new Consumer<List<UserAccountDetailDto>>() {
                    @Override
                    public void accept(@NonNull List<UserAccountDetailDto> userAccountDetailDtos) throws Exception {
                        getMvpView().showAccountDetail(userAccountDetailDtos,pageNo);
                    }

                }));
    }

}
