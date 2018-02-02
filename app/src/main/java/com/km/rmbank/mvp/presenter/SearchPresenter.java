package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.SearchModel;
import com.km.rmbank.mvp.view.ISearchView;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/1/19.
 */

public class SearchPresenter extends BasePresenter<ISearchView,SearchModel> {

    public SearchPresenter(SearchModel mModel) {
        super(mModel);
    }

    public void getSearchGoods(final int pageNo, String name) {
        getMvpView().showLoading();
        getMvpModel().getGoodsListOfSearch(pageNo,name)
                .subscribe(newSubscriber(new Consumer<List<GoodsDto>>() {
                    @Override
                    public void accept(@NonNull List<GoodsDto> goodsDtos) throws Exception {
                        getMvpView().showSearchResult(goodsDtos,pageNo);
                    }
                }));
    }
}
