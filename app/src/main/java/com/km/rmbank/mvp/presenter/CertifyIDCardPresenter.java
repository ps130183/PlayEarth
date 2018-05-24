package com.km.rmbank.mvp.presenter;

import com.baidu.ocr.ui.util.ImageUtil;
import com.blankj.utilcode.util.LogUtils;
import com.km.rmbank.entity.IDCardEntity;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.CertifyIDCardModel;
import com.km.rmbank.mvp.view.CertifyIDCardView;
import com.km.rmbank.utils.fileupload.FileUploadingListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by PengSong on 18/4/27.
 */

public class CertifyIDCardPresenter extends BasePresenter<CertifyIDCardView, CertifyIDCardModel> {

    public CertifyIDCardPresenter(CertifyIDCardModel mModel) {
        super(mModel);
    }

    public void uploadCardImage(final String[] imagePaths, final IDCardEntity entity) {
        getMvpView().showLoading();
        final List<String> imageUrls = new ArrayList<>();
        Observable.fromArray(imagePaths)
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
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        imageUrls.add(s);
                        LogUtils.d("当前第 " +imageUrls.size() + " 图片上传成功 === 》" + s);
                        return imageUrls.size() == 3;
                    }
                })
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String s) throws Exception {
                        StringBuffer imageUrl = new StringBuffer();
                        for (String url : imageUrls) {
                            imageUrl.append(url).append("#");
                        }
                        String url = imageUrl.substring(0, imageUrl.length() - 1);
                        return getMvpModel().certifyIDCard(entity, url);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        getMvpView().certifyIDCardSuccess(s);
                    }
                }));
    }

}
