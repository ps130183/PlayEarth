package com.km.rmbank.mvp.presenter;

import com.km.rmbank.entity.BookVenueApplyDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.BookVenueApplyModel;
import com.km.rmbank.mvp.view.BookVenueApplyView;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/8/20.
 */

public class BookVenueApplyPresenter extends BasePresenter<BookVenueApplyView,BookVenueApplyModel> {

    public BookVenueApplyPresenter(BookVenueApplyModel mModel) {
        super(mModel);
    }

    public void getBookVenueApplyList(String status){
//        getMvpView().showLoading();
        getMvpModel().getBookVenueApplyList(status)
                .subscribe(newSubscriber(new Consumer<List<BookVenueApplyDto>>() {
                    @Override
                    public void accept(List<BookVenueApplyDto> bookVenueApplyDtos) throws Exception {
                        getMvpView().showBookVenueApplyList(bookVenueApplyDtos);
                    }
                }));
    }

    public void cancelBookVenueApply(final BookVenueApplyDto bookVenueApplyDto){
        getMvpModel().cancelBookVenueApply(bookVenueApplyDto.getId())
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        getMvpView().cancelSuccess(bookVenueApplyDto);
                    }
                }));
    }
}
