package com.km.rmbank.mvp.presenter;

import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.PayMemberSuccessModel;
import com.km.rmbank.mvp.view.PayMemberSuccessView;

import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/8/19.
 */

public class PayMemberSuccessPresenter extends BasePresenter<PayMemberSuccessView,PayMemberSuccessModel> {

    public PayMemberSuccessPresenter(PayMemberSuccessModel mModel) {
        super(mModel);
    }

    public void saveAddress(String orderNo,String address,String name,String phone){
        getMvpView().showLoading();
        getMvpModel().saveAddress(orderNo,address,name,phone)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        getMvpView().submitSuccess();
                    }
                }));
    }
}
