package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.GoodsDetailsDto;
import com.km.rmbank.dto.ShoppingCartDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.ShoppingCartModel;
import com.km.rmbank.mvp.view.IShoppingCartView;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/1/19.
 */

public class ShoppingCartPresenter extends BasePresenter<IShoppingCartView,ShoppingCartModel> {

    public ShoppingCartPresenter(ShoppingCartModel mModel) {
        super(mModel);
    }

    public void loadShoppingCartDatas() {
//        Random random = new Random();
        getMvpView().showLoading();

        getMvpModel().getShoppingCartList()
                .subscribe(newSubscriber(new Consumer<List<ShoppingCartDto>>() {
                    @Override
                    public void accept(@NonNull List<ShoppingCartDto> shoppingCartDtos) throws Exception {
                        getMvpView().showShoppingCartDatas(shoppingCartDtos);
                    }
                }));
    }

    public void updateGoodsCount(final GoodsDetailsDto productNo, final String optionType) {
        getMvpModel().updateCountOnShopCartForGoods(productNo.getProductNo(),optionType)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        getMvpView().updateGoodsCountSuccess(productNo,optionType);
                    }
                }));
    }

    public void createOrder(String productNos) {
        getMvpView().showLoading();
        getMvpModel().createOrder(productNos)
                .subscribe(newSubscriber(new Consumer<List<ShoppingCartDto>>() {
                    @Override
                    public void accept(@NonNull List<ShoppingCartDto> shoppingCartDtos) throws Exception {
                        getMvpView().createOrderSuccess(shoppingCartDtos);
                    }
                }));
    }

    public void deleteGoods(GoodsDetailsDto goodsDetailsDto, final int positionOnParent, final int positionOnSub) {
        getMvpModel().deleteShoppingCartGoods(goodsDetailsDto)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        getMvpView().deleteSuccess(positionOnParent,positionOnSub);
                    }
                }));
    }
}
