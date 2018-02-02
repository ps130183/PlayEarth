package com.km.rmbank.wxpay;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.km.rmbank.dto.WeiCharParamsDto;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * 微信支付工具类
 *
 * @author hfk
 *         create at 2016/5/3 10:33
 */
public class WxUtil {
    private static IWXAPI iwxapi;


    /**
     * 获取 IWXAPI
     *
     * @param context
     * @return
     */
    public static IWXAPI getWXAPI(Context context) {
//        Utils.showLog(appid);
        iwxapi = WXAPIFactory.createWXAPI(context, Constants.APP_ID);
        iwxapi.registerApp(Constants.APP_ID);

        return iwxapi;
    }

    /**
     * 获取随机字符串
     *
     * @return
     */
    public static String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    /**
     * 时间戳
     *
     * @return
     */
    public static long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }


    /**
     * 检测是否有微信与是否支持微信支付
     *
     * @return
     */
    public static boolean check(Context context, IWXAPI api) {
        if (api == null) {
            ToastUtils.showShort("请先打开微信");
            return false;
        }
        boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
        if (!api.isWXAppInstalled()) {
            Toast.makeText(context, "没有安装微信", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!api.isWXAppSupportAPI()) {
            Toast.makeText(context, "你使用的微信版本不支持微信支付！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return isPaySupported;
    }


    /**
     * 得到app签名
     *
     * @param params
     * @return
     */
    public static String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
//        ,StringBuilder stringBuilder
        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
//        sb.append(params.toString());
        sb.append("key=");
        sb.append(Constants.PRIVATE_KEY);
        Log.e("sb----", sb.toString());
        String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
                //MD5.MD5Encode(sb.toString(),"UTF-8").toUpperCase();
        Log.e("orion5", appSign);
        return appSign;
    }

    /**
     * 微信注册app
     *
     * @param iwxapi
     * @param req
     */
    public static void sendPayReq(IWXAPI iwxapi, PayReq req) {
        if (iwxapi != null) {
            iwxapi.registerApp(Constants.APP_ID);
            LogUtils.d("微信吊起支付结果：" + iwxapi.sendReq(req));
        }
    }

    public static void toPayByWXAPI(WeiCharParamsDto weiCharParamsDto) {

        PayReq payReq = new PayReq();

        payReq.appId = weiCharParamsDto.getAppid();
        payReq.partnerId =weiCharParamsDto.getPartnerid();
        payReq.prepayId = weiCharParamsDto.getPrepayid();
        payReq.packageValue = weiCharParamsDto.getPackageX();
        payReq.nonceStr = weiCharParamsDto.getNoncestr();
        payReq.timeStamp = String.valueOf(genTimeStamp());

        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", payReq.appId));
        signParams.add(new BasicNameValuePair("noncestr", payReq.nonceStr));
        signParams.add(new BasicNameValuePair("package", payReq.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", payReq.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", payReq.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", payReq.timeStamp));

        payReq.sign = genAppSign(signParams);
        if (iwxapi != null) {
            sendPayReq(iwxapi, payReq);
        } else {
            LogUtils.d("支付失败");
        }

    }
}