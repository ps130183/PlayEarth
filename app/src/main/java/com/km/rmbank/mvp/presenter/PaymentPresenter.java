package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.dto.UserBalanceDto;
import com.km.rmbank.dto.WeiCharParamsDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.PaymentModel;
import com.km.rmbank.mvp.view.IPaymentView;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/1/25.
 */

public class PaymentPresenter extends BasePresenter<IPaymentView,PaymentModel> {

    public PaymentPresenter(PaymentModel mModel) {
        super(mModel);
    }

    public void createPayOrder(String amount,String memberType) {
        getMvpView().showLoading();
        getMvpModel().createPayOrder(amount,memberType)
                .subscribe(newSubscriber(new Consumer<PayOrderDto>() {
                    @Override
                    public void accept(@NonNull PayOrderDto payOrderDto) throws Exception {
                        getMvpView().createPayOrderSuccess(payOrderDto);
                    }
                }));
    }


    public void getAliPayOrder(String payNumber) {
        getMvpView().showLoading();
        getMvpModel().getAlipayParams(payNumber)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String alipayParamsDto) throws Exception {
                        getMvpView().getAlipayParamsSuccess(alipayParamsDto);
                    }

                }));
    }

    public void getWeiChatParams(String payNumber) {
        getMvpView().showLoading();
        getMvpModel().getWeiChatParams(payNumber)
                .subscribe(newSubscriber(new Consumer<WeiCharParamsDto>() {
                    @Override
                    public void accept(@NonNull WeiCharParamsDto weiCharParamsDto) throws Exception {
                        getMvpView().getWeiCharParamsSuccess(weiCharParamsDto);
                    }
                }));
    }

    public void payBalance(String payNumber) {
        getMvpView().showLoading();
        getMvpModel().payBalance(payNumber)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        getMvpView().payBalanceSuccess();
                    }
                }));
    }

    public void checkPayResult(String payNumber) {
        getMvpView().showLoading();
        getMvpModel().checkPayResult(payNumber)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        getMvpView().checkSuccess();
                    }
                }));
    }

    public void getBalance() {
        getMvpModel().getUserAccountBalance()
                .subscribe(newSubscriber(new Consumer<UserBalanceDto>() {
                    @Override
                    public void accept(@NonNull UserBalanceDto userBalanceDto) throws Exception {
                        getMvpView().showUserBalance(userBalanceDto);
                    }
                }));
    }
}
