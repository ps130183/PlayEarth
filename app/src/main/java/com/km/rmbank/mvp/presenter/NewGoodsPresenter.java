package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.GoodsDetailsDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.NewGoodsModel;
import com.km.rmbank.mvp.view.INewGoodsView;
import com.km.rmbank.utils.fileupload.FileUploadingListener;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by PengSong on 18/1/24.
 */

public class NewGoodsPresenter extends BasePresenter<INewGoodsView,NewGoodsModel> {

    public NewGoodsPresenter(NewGoodsModel mModel) {
        super(mModel);
    }

    public void uploadImage(String photoList, final int photoType, final int position) {
        FileUploadingListener listener = new FileUploadingListener() {
            @Override
            public void onProgress(final int progress) {
                Observable.just(progress)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(@NonNull Integer integer) throws Exception {
                                getMvpView().showImageUploadingProgress(photoType,progress,position);
                            }
                        });
            }
        };
        getMvpModel().imageUpload("2", photoList, listener)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        getMvpView().showImageUploadResult(photoType,s);
                    }
                });
    }

    public void createNewGoods(GoodsDetailsDto goodsDetailsDto) {
        getMvpView().showLoading();
        getMvpModel().createNewGoods(goodsDetailsDto)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        getMvpView().createNewGoodsSuccess();
                    }
                }));
    }

    public void getGoodsInfo(String productNo) {
        getMvpView().showLoading();
        Observable<GoodsDetailsDto> goodsInfo = getMvpModel().getGoodsInfo(productNo).subscribeOn(Schedulers.io());
        goodsInfo.subscribe(newSubscriber(new Consumer<GoodsDetailsDto>() {
            @Override
            public void accept(@NonNull GoodsDetailsDto goodsDetailsDto) throws Exception {
                getMvpView().showGoodsInfo(goodsDetailsDto);
            }
        }));
    }

    public void updateGoodsInfo(GoodsDetailsDto goodsDetailsDto) {
        getMvpView().showLoading();
        getMvpModel().updateGoods(goodsDetailsDto)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        getMvpView().createNewGoodsSuccess();
                    }
                }));
    }
}
