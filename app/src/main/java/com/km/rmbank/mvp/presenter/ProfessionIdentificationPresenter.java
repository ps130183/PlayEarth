package com.km.rmbank.mvp.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.ProfessionIdentificationModel;
import com.km.rmbank.mvp.view.ProfessionIdentificationView;
import com.km.rmbank.utils.fileupload.FileUploadingListener;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by PengSong on 18/7/20.
 */

public class ProfessionIdentificationPresenter extends BasePresenter<ProfessionIdentificationView,ProfessionIdentificationModel> {

    public ProfessionIdentificationPresenter(ProfessionIdentificationModel mModel) {
        super(mModel);
    }

    /**
     * 提交职业认证
     * @param imagePath
     */
    public void submitProfessionIdentification(String imagePath){
        getMvpView().showLoading();
        Observable.fromArray(imagePath)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(final String s) throws Exception {
                        return getMvpModel().uploadImage("3", s, new FileUploadingListener() {
                            @Override
                            public void onProgress(int progress) {
//                                LogUtils.d("当前图片：" + s + "   进度 ： --- 》" + progress);
                            }
                        });
                    }
                })
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String s) throws Exception {

                        return getMvpModel().professionIdentification(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        getMvpView().identificationSuccess();
                    }
                }));

    }
}
