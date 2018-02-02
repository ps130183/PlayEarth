package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.GoodsDetailsDto;
import com.km.rmbank.dto.ReceiverAddressDto;
import com.km.rmbank.dto.UserCardDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.GoodsModel;
import com.km.rmbank.mvp.view.IGoodsView;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by PengSong on 18/1/19.
 */

public class GoodsPresenter extends BasePresenter<IGoodsView,GoodsModel> {
    
    public GoodsPresenter(GoodsModel mModel) {
        super(mModel);
    }

    public void getGoodsDetails(String productNo) {
        getMvpView().showLoading();
        Observable<GoodsDetailsDto> goodsDetailsDtoFlowable = getMvpModel().getGoodsDetails(productNo).subscribeOn(Schedulers.io());
        Observable<ReceiverAddressDto> receiverAddressDtoFlowable = getMvpModel().getDefaultReceiverAddress().subscribeOn(Schedulers.io());
        Observable.zip(goodsDetailsDtoFlowable, receiverAddressDtoFlowable, new BiFunction<GoodsDetailsDto, ReceiverAddressDto, GoodsDetailsDto>() {
            @Override
            public GoodsDetailsDto apply(@NonNull GoodsDetailsDto goodsDetailsDto, @NonNull ReceiverAddressDto receiverAddressDto) throws Exception {
                goodsDetailsDto.setReceiverAddressDto(receiverAddressDto);
                return goodsDetailsDto;
            }
        }).subscribe(newSubscriber(new Consumer<GoodsDetailsDto>() {
            @Override
            public void accept(@NonNull GoodsDetailsDto goodsDetailsDto) throws Exception {
                getMvpView().showGoodsDetails(goodsDetailsDto);
            }
        }));
    }

    public void followGoods(String productNo) {
        getMvpModel().followGodos(productNo)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        getMvpView().followGoodsSuccess();
                    }
                }));
    }

    public void addShoppingCart(String productNo, String count) {
        getMvpModel().addShoppingCart(productNo,count)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        getMvpView().addShoppingCartSuccess();
                    }
                }));
    }

//    public void getShopUserCartInfo(String id) {
//        getMvpModel().getUserCardById(id)
//                .subscribe(newSubscriber(new Consumer<UserCardDto>() {
//                    @Override
//                    public void accept(@NonNull UserCardDto userCardDto) throws Exception {
//                        getMvpView().showShopUserCartInfo(userCardDto);
//                    }
//                }));
//    }
}
