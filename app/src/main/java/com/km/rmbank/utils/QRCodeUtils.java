package com.km.rmbank.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.km.rmbank.oldrecycler.AppUtils;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

/**
 * Created by kamangkeji on 17/3/23.
 */

public class QRCodeUtils {

    private static Bitmap createQRCode(String content, int widthPix, int heightPix, Bitmap logoBm){
        Bitmap mBitmap = EncodingUtils.createQRCode(content, widthPix, heightPix, logoBm);
        return mBitmap;
    }

    public static Bitmap createQRCode(Context mContext, String content){
        return createQRCode(content, AppUtils.dip2px(mContext,200),AppUtils.dip2px(mContext,200),null);
    }
}
