package com.km.rmbank.event;

//import com.tencent.mm.opensdk.modelbase.BaseResp;

import com.tencent.mm.opensdk.modelbase.BaseResp;

/**
 * Created by kamangkeji on 17/4/19.
 */

public class WXPayResult {
    private BaseResp baseResp;

    public WXPayResult(BaseResp baseResp) {
        this.baseResp = baseResp;
    }

    public BaseResp getBaseResp() {
        return baseResp;
    }
}
