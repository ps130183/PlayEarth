package com.km.rmbank.retrofit.interceptor;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Sunflower on 2016/1/19.
 */
public class CacheInterceptor implements Interceptor {
    private Context mContext;

    public CacheInterceptor(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (!isNetworkReachable(mContext)) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        Response response = chain.proceed(request);
        if (isNetworkReachable(mContext)) {
            //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
            String cacheControl = request.cacheControl().toString();
//            Logger.i("has network ,cacheControl=" + cacheControl);
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", cacheControl)
                    .build();
        } else {
            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
//            Logger.i("network error ,maxStale="+maxStale);
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale="+maxStale)
                    .build();
        }
    }

    /**
     * 判断网络连接
     * @param mContext
     * @return
     */
    private boolean isNetworkReachable(Context mContext){
        ConnectivityManager cm = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = cm.getActiveNetworkInfo();
        if (current == null) {
            return false;
        }
        return (current.isAvailable());
    }
}
