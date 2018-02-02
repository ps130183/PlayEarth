package com.km.rmbank.alipay;

import android.app.Activity;

import com.alipay.sdk.app.PayTask;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by kamangkeji on 17/4/4.
 */

public class AlipayUtils {

    /**
     * resultStatus={9000};
     * memo={};
     * result={
     *  {"alipay_trade_app_pay_response":{
     *      "code":"10000",
     *      "msg":"Success",
     *      "app_id":"2016062701556985",
     *      "auth_app_id":"2016062701556985",
     *      "charset":"utf-8",
     *      "timestamp":"2017-04-13 18:56:18",
     *      "total_amount":"0.01",
     *      "trade_no":"2017041321001004360267042869",
     *      "seller_id":"2088421328408891",
     *      "out_trade_no":"PO2017041310443164"},
     *      "sign":"Jkkq5X4sJtHDCVDn8z5tDpeA3Uo7JmzXbD378hjzMOWPiXPdtAE9v0fNS8ejvsEatCt3ELdr
     *              G14Mkn4zrW58rWVqj4uZcHbSqA8u08ZA5HPuPY2mO2zJ8EnpetdtHxlBjPmus/wlWueZV5c4AlrPW/DN
     *              arRjP/R7hBoxjVtYO3Y=",
     *       "sign_type":"RSA"}}
     */
    /**
     * alipay支付  \
     * PayTask payTask = new PayTask(activity);
     return Observable.just(payTask.pay(s,true));
     * @param activity
     * @param orderPayDto
     * @return
     */
    public static Observable<PayResult> toPay(final Activity activity, String orderPayDto){
       return Observable.just(orderPayDto)
                .map(new Function<String,Map<String,String>>() {

                    @Override
                    public Map<String,String> apply(@NonNull String s) throws Exception {
                        PayTask payTask = new PayTask(activity);
                        return payTask.payV2(s,true);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
               .map(new Function<Map<String,String>, PayResult>() {
                   @Override
                   public PayResult apply(@NonNull Map<String, String> stringStringMap) throws Exception {
                       return new PayResult(stringStringMap);
                   }
               });
//        return observable;
    }

}
