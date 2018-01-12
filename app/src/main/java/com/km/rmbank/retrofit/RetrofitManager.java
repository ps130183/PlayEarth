package com.km.rmbank.retrofit;

import com.km.rmbank.api.ApiService;
import com.km.rmbank.base.BaseApplication;
import com.km.rmbank.retrofit.interceptor.CacheInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by PengSong on 17/12/25.
 */

public class RetrofitManager {

    public static ApiService service;

    public static ApiService getApiService(){
        if (service == null){
            service = getRetrofit().create(ApiService.class);
        }
        return service;
    }

    public static <A> A createService(Class<A> apiClass){
        return getRetrofit().create(apiClass);
    }

    /**
     * 初始化retrofit信息
     */
    private static Retrofit getRetrofit(){

        //cache url
        File httpCacheDirectory = new File(BaseApplication.getInstance().getCacheDir(), "cache");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB  缓存大小
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        //缓存拦截器
        CacheInterceptor cacheInterceptor = new CacheInterceptor(BaseApplication.getInstance());

        OkHttpClient client = new OkHttpClient.Builder()
                //log请求参数
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))//日志拦截器
//                    .addInterceptor(basicParamsInterceptor)//公共请求参数
                //设置缓存
                .cache(cache)
                .addInterceptor(cacheInterceptor) //缓存拦截器
//                    .addNetworkInterceptor(cacheInterceptor)//网络拦截器
                .retryOnConnectionFailure(true)//错误重连
                .connectTimeout(15, TimeUnit.SECONDS)//连接超时 15秒
                //网络拦截器，可以用于重试或重写
//                    .addNetworkInterceptor(authorizationInterceptor)
                //网络请求缓存，未实现
                .build();

        //配置retrofit的基础信息
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiContract.API_BASE_URL)//配置基础的请求URL
                .client(client)
                .addConverterFactory(GsonConverterFactory.create()) //配置Gson请求结果转换器
                .addConverterFactory(ScalarsConverterFactory.create())//将返回结果转成基本数据类型
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//配置Rxjava返回结果接收器
                .build();

        return retrofit;
    }

}
