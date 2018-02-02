package com.km.rmbank.mvp.presenter;

import android.os.Handler;

import com.km.rmbank.dto.GoodsDetailsDto;
import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.dto.ReceiverAddressDto;
import com.km.rmbank.dto.ShoppingCartDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.CreateShopOrderModel;
import com.km.rmbank.mvp.view.ICreateShopOrderView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/1/28.
 */

public class CreateShopOrderPresenter extends BasePresenter<ICreateShopOrderView,CreateShopOrderModel> {

    public CreateShopOrderPresenter(CreateShopOrderModel mModel) {
        super(mModel);
    }

    public void getDefaultReceiverAddress() {
        getMvpModel().getDefaultReceiverAddress()
                .subscribe(newSubscriber(new Consumer<ReceiverAddressDto>() {
                    @Override
                    public void accept(@NonNull ReceiverAddressDto receiverAddressDto) throws Exception {
                        getMvpView().showDefaultReceiverAddress(receiverAddressDto);
                    }
                }));
    }

    public void loadOrderDatas() {
        Random random = new Random();
        final List<ShoppingCartDto> shoppingCartEntities = new ArrayList<>();
        for (int i = 0; i < 2; i++){
            List<GoodsDetailsDto> goodsEntities = new ArrayList<>();
            for (int j = 0; j < random.nextInt(3)+1; j++){
                goodsEntities.add(new GoodsDetailsDto());
            }
            ShoppingCartDto entity = new ShoppingCartDto();
            entity.setProductList(goodsEntities);
            shoppingCartEntities.add(entity);
        }
        getMvpView().showLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getMvpView().hideLoading();
                getMvpView().showOrderDatas(shoppingCartEntities);
            }
        },3000);
    }

    public void submitOrder(String productNos, String productCounts, String receiveAddressId, String freight,String integral) {
        getMvpView().showLoading();
        getMvpModel().submitOrder(productNos,productCounts,receiveAddressId,freight,integral)
                .subscribe(newSubscriber(new Consumer<PayOrderDto>() {
                    @Override
                    public void accept(@NonNull PayOrderDto payOrderDto) throws Exception {
                        getMvpView().submitOrderSuccess(payOrderDto);
                    }
                }));
    }
}
