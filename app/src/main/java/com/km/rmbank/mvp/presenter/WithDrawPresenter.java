package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.UserBalanceDto;
import com.km.rmbank.dto.WithDrawAccountDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.WithDrawModel;
import com.km.rmbank.mvp.view.IWithDrawView;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/1/25.
 */

public class WithDrawPresenter extends BasePresenter<IWithDrawView,WithDrawModel> {

    public WithDrawPresenter(WithDrawModel mModel) {
        super(mModel);
    }

    public void getUserBalance() {
        getMvpModel().getUserAccountBalance()
                .subscribe(newSubscriber(new Consumer<UserBalanceDto>() {
                    @Override
                    public void accept(@NonNull UserBalanceDto userBalanceDto) throws Exception {
                        getMvpView().showBalance(userBalanceDto);
                    }
                }));
    }

    public void submitWithdraw(WithDrawAccountDto withDrawAccountDto, String money) {
        getMvpView().showLoading();
        getMvpModel().submitWithDraw(withDrawAccountDto,money)
                .subscribe(newSubscriber(new Consumer() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        getMvpView().withdrawSuccess();
                    }
                }));
    }

    public void createWithDrawAccount(WithDrawAccountDto withDrawAccountDto) {
        getMvpModel().createWithDrawAccount(withDrawAccountDto)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        getMvpView().creatOrUpdateSuccess();
                    }
                }));
    }

    public void updateWithDrawAccount(WithDrawAccountDto withDrawAccountDto) {
        getMvpModel().updateWithDrawAccount(withDrawAccountDto)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        getMvpView().creatOrUpdateSuccess();
                    }
                }));
    }

    public void getWithDrawList() {
        getMvpView().showLoading();
        getMvpModel().getWithDrawAccount()
                .subscribe(newSubscriber(new Consumer<List<WithDrawAccountDto>>() {
                    @Override
                    public void accept(@NonNull List<WithDrawAccountDto> withDrawAccountDtos) throws Exception {
                        getMvpView().showWithDrawList(withDrawAccountDtos);
                    }

                }));
    }

    public void deleteWithdrawAccount(final WithDrawAccountDto withDrawAccountDto) {
        getMvpModel().deleteWithdrawAccount(withDrawAccountDto.getId())
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        getMvpView().deleteSuccess(withDrawAccountDto);
                    }
                }));
    }
}
