package com.km.rmbank.mvp.presenter;

import com.daimajia.swipe.SwipeLayout;
import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.ReleaseGoodsModel;
import com.km.rmbank.mvp.view.IReleaseGoodsView;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/1/24.
 */

public class ReleaseGoodsPresenter extends BasePresenter<IReleaseGoodsView,ReleaseGoodsModel> {

    public ReleaseGoodsPresenter(ReleaseGoodsModel mModel) {
        super(mModel);
    }

    public void loadRepleaseGoods(final int page) {
        getMvpModel().getGoodsListOfShop(page)
                .subscribe(newSubscriber(new Consumer<List<GoodsDto>>() {
                    @Override
                    public void accept(@NonNull List<GoodsDto> goodsDtos) throws Exception {
                        getMvpView().showRepleaseGoods(goodsDtos,page);
                    }

                }));
    }

    public void goodsSoldOut(final GoodsDto goodsDto, final SwipeLayout swipeLayout) {
        getMvpModel().goodsSlodOut(goodsDto.getProductNo())
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        getMvpView().goodsSoldOutSuccess(goodsDto,swipeLayout);
                    }
                }));
    }
}
