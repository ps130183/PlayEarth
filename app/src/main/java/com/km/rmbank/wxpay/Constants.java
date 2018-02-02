package com.km.rmbank.wxpay;

/**
 * Created by Huang on 2016/4/20.
 */
public class Constants {
    // APP_ID 替换为你的应用从官方网站申请到的合法appId
    public static final String APP_ID = "wx637ea06a1c6d1fff";
    public static final String PRIVATE_KEY = "1b2n3v4fx5v56vj8f3hd5t7g5f6hj8fh";
    //商户号
    public static final String MCH_ID = "1381321402";//
    //  API密钥，在商户平台设置
//    public static final  String API_KEY="1b2n3v4fx5v56vj8f3hd5t7g5f6hj8fh";//口小袋
    public static final  String API_KEY="qwertyuiopqwertyuiopqwertyuiopqw";//口小袋
//    支付结果异步通知链接
    public static final  String NOTIFY_URL="http://wxpay.weixin.qq.com/pub_v2/pay/notify.v2.php";
    //订单生成的机器IP，指用户浏览器端IP，也可以写成固定值
    public static final  String SPBILL_CREATE_IP="196.168.1.1";

}
