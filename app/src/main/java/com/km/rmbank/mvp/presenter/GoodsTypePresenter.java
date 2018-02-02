package com.km.rmbank.mvp.presenter;

import com.km.rmbank.R;
import com.km.rmbank.dto.HomeGoodsTypeDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.GoodsTypeModel;
import com.km.rmbank.mvp.view.IGoodsTypeView;

import java.util.List;
import java.util.Random;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/1/24.
 */

public class GoodsTypePresenter extends BasePresenter<IGoodsTypeView,GoodsTypeModel> {


    private int[] backgroundRes = {R.drawable.shape_new_goods_select_goods_type_1,
            R.drawable.shape_new_goods_select_goods_type_2,
            R.drawable.shape_new_goods_select_goods_type_3,
            R.drawable.shape_new_goods_select_goods_type_4,
            R.drawable.shape_new_goods_select_goods_type_5,
            R.drawable.shape_new_goods_select_goods_type_6,
            R.drawable.shape_new_goods_select_goods_type_7};

    private Random mRandom = new Random();

    public GoodsTypePresenter(GoodsTypeModel mModel) {
        super(mModel);
    }

    public void getGoodsType() {

        getMvpModel().getGoodsTypeForCreateGoods()
                .doOnNext(new Consumer<List<HomeGoodsTypeDto>>() {
                    @Override
                    public void accept(@NonNull List<HomeGoodsTypeDto> goodsTypeDtos) throws Exception {
                        for (HomeGoodsTypeDto goodsTypeDto : goodsTypeDtos){
                            goodsTypeDto.setBackgroundRes(backgroundRes[mRandom.nextInt(backgroundRes.length)]);
                        }
                    }
                })
                .subscribe(newSubscriber(new Consumer<List<HomeGoodsTypeDto>>() {
                    @Override
                    public void accept(@NonNull List<HomeGoodsTypeDto> goodsTypeDtos) throws Exception {
                        getMvpView().showGoodsType(goodsTypeDtos);
                    }
                }));

    }
}
