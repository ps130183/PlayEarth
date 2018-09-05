package com.km.rmbank.mvp.presenter;

import com.app.hubert.guide.util.LogUtil;
import com.km.rmbank.dto.ReleaseActionDetailsDto;
import com.km.rmbank.entity.ImageEntity;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.ReleaseActionModel;
import com.km.rmbank.mvp.view.ReleaseActionView;
import com.km.rmbank.utils.fileupload.FileUploadingListener;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by PengSong on 18/8/21.
 */

public class ReleaseActionPresenter extends BasePresenter<ReleaseActionView,ReleaseActionModel> {

    public ReleaseActionPresenter(ReleaseActionModel mModel) {
        super(mModel);
    }

    public void releaseAction(final String title, final String startTime,
                              final String endTime, final String placeReservationId,
                              final String textContent,
                              final List<ImageEntity> imageEntities){

        getMvpView().showLoading();
        final StringBuffer imageContents = new StringBuffer();
        final int[] count = {0};
        ImageEntity[] images = new ImageEntity[imageEntities.size()];
        ImageEntity[] imageEntitys = imageEntities.toArray(images);
        Observable.fromArray(imageEntitys)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<ImageEntity, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(ImageEntity imageEntity) throws Exception {
                        return getMvpModel().imageUpload("5", imageEntity.getImagePath(), new FileUploadingListener() {
                            @Override
                            public void onProgress(int progress) {
                                LogUtil.d("上传进度： ----- 》 " + progress);
                            }
                        });
                    }
                })
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        count[0]++;
                        imageContents.append(s).append("#");
                        return count[0] == imageEntities.size();
                    }
                })
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String s) throws Exception {
                        String imageContent = imageContents.substring(0,imageContents.length() -1);
                        return getMvpModel().releaseAction(title,startTime,endTime,placeReservationId,imageContent,textContent);
                    }
                }).subscribe(newSubscriber(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                getMvpView().releaseActionSuccess();
            }
        }));


    }

    public void getReleaseActionDetails(String id){
        getMvpModel().getReleaseActionDetails(id)
                .subscribe(newSubscriber(new Consumer<ReleaseActionDetailsDto>() {
                    @Override
                    public void accept(ReleaseActionDetailsDto releaseActionDetailsDto) throws Exception {
                        getMvpView().showReleaseActionDetails(releaseActionDetailsDto);
                    }
                }));
    }
}
