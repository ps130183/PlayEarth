package com.km.rmbank.wxapi;


//import com.umeng.weixin.callback.WXCallbackActivity;

import com.blankj.utilcode.util.LogUtils;
import com.km.rmbank.entity.WXLoginRequestEvent;
import com.km.rmbank.utils.EventBusUtils;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

public class WXEntryActivity extends WXCallbackActivity {


    @Override
    public void onResp(BaseResp resp) {
        super.onResp(resp);
        LogUtils.d("currentThread === " + Thread.currentThread());
        if(resp instanceof SendAuth.Resp && resp.errCode == BaseResp.ErrCode.ERR_OK){//微信登录 用户同意登录
            SendAuth.Resp result = (SendAuth.Resp) resp;
            LogUtils.d(result.openId + "   " + result.code);
            EventBusUtils.post(new WXLoginRequestEvent(((SendAuth.Resp) resp).code));
        }

    }
}
