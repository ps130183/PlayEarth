package com.km.rmbank.mvp.presenter;

import android.support.annotation.NonNull;

import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.dto.HomeGoodsTypeDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.ShopModel;
import com.km.rmbank.mvp.view.IShopView;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/1/19.
 */

public class ShopPresenter extends BasePresenter<IShopView,ShopModel> {

    public ShopPresenter(ShopModel mModel) {
        super(mModel);
    }

    public void getGodosTypes() {
        getMvpModel().getGoodsType()
                .subscribe(newSubscriber(new Consumer<List<HomeGoodsTypeDto>>() {
                    @Override
                    public void accept(@NonNull List<HomeGoodsTypeDto> homeGoodsTypeDtos) throws Exception {
                        getMvpView().showGoodsType(homeGoodsTypeDtos);
                    }
                }));
    }

    public void getGoodsList(final int pageNo, String isInIndextActivity, int orderBy, String roleId) {
        getMvpModel().getGoodsListOfShoppingNew(pageNo,isInIndextActivity,orderBy,roleId)
                .subscribe(newSubscriber(new Consumer<List<GoodsDto>>() {
                    @Override
                    public void accept(@NonNull List<GoodsDto> goodsDtos) throws Exception {
                        getMvpView().showGoodsList(pageNo,goodsDtos);
                    }
                }));
    }

    public void addShoppingCart(String productNo, String count) {
        getMvpModel().addShoppingCart(productNo,count)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull String s) throws Exception {
                        getMvpView().addShoppingCartSuccess();
                    }
                }));
    }
}
