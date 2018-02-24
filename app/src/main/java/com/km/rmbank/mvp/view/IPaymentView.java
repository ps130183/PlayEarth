package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.dto.UserBalanceDto;
import com.km.rmbank.dto.WeiCharParamsDto;
import com.km.rmbank.mvp.base.MvpView;

/**
 * Created by PengSong on 18/1/25.
 */

public interface IPaymentView extends MvpView {
    void createPayOrderSuccess(PayOrderDto payOrderDto);
    void getAlipayParamsSuccess(String alipayParamsDto);
    void getWeiCharParamsSuccess(WeiCharParamsDto weicharParams);
    void payBalanceSuccess();
    void checkSuccess();

    void showUserBalance(UserBalanceDto userBalanceDto);

    void applyScenicResult(PayOrderDto payOrderDto);
}
