package com.km.rmbank.mvp.base;


import com.blankj.utilcode.util.LogUtils;
import com.km.rmbank.api.ApiService;
import com.km.rmbank.retrofit.Response;
import com.km.rmbank.retrofit.RetrofitManager;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by PengSong on 18/1/8.
 */

public abstract class BaseModel implements MvpModel {

    protected ApiService getService(){
        return RetrofitManager.getApiService();
    }

    /**
     * 对网络接口返回的Response进行分割操作
     *
     * @param response
     * @param <T>
     * @return
     */
    public <T> Observable<T> flatResponse(final Response<T> response) {
        LogUtils.json(response.toString());
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> e) throws Exception {
                if (response.isSuccess()) {
                    if (!e.isDisposed()) {
                        e.onNext(response.result);
                    }
                } else {
                    if (!e.isDisposed()) {
                        e.onError(new BaseModel.APIException(response.status, response.message));
                    }
                }
                if (!e.isDisposed()) {
                    e.onComplete();
                }
            }
        });
    }

    /**
     * <p/>
     * Transformer实际上就是一个Func1<Observable<T>, Observable<R>>，
     * 换言之就是：可以通过它将一种类型的Observable转换成另一种类型的Observable，
     * 和调用一系列的内联操作符是一模一样的。
     *
     * @param <T>
     * @return
     */
    protected <T> ObservableTransformer<Response<T>, T> applySchedulers() {
        return (ObservableTransformer<Response<T>, T>) transformer;
    }

    final ObservableTransformer transformer = new ObservableTransformer() {
        @Override
        public ObservableSource apply(Observable upstream) {
            return upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap(new Function() {
                        @Override
                        public Object apply(@NonNull Object o) throws Exception {
                            return flatResponse((Response<? extends Object>) o);
                        }
                    });
        }

    };

    /**
     * 自定义异常，当接口返回的{@link Response#status}不为{@link com.km.rmbank.retrofit.RetCode#SUCCESS}时，需要跑出此异常
     * eg：登陆时验证码错误；参数为传递等
     */
    public static class APIException extends Throwable {
        public String code;
        public String message;

        public APIException(String code, String message) {
            this.code = code;
            this.message = message;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }
}
