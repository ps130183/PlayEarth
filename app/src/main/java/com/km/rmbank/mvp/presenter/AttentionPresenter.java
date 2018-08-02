package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.AttentionDto;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.AttentionModel;
import com.km.rmbank.mvp.view.IAttentionView;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/1/25.
 */

public class AttentionPresenter extends BasePresenter<IAttentionView,AttentionModel> {

    public AttentionPresenter(AttentionModel mModel) {
        super(mModel);
    }

    public void getAttentionGoods(final int pageNo) {
        getMvpView().showLoading();
        getMvpModel().getAttentionGoodsList(pageNo)
                .subscribe(newSubscriber(new Consumer<List<AttentionDto>>() {
                    @Override
                    public void accept(@NonNull List<AttentionDto> goodsDtos) throws Exception {
                        getMvpView().getAttentionGoodsSuccess(goodsDtos,pageNo);
                    }
                }));
    }

    public void getAttentionGoods1(final int pageNo) {
        getMvpView().showLoading();
        getMvpModel().getAttentionGoodsList1(pageNo)
                .subscribe(newSubscriber(new Consumer<List<GoodsDto>>() {
                    @Override
                    public void accept(@NonNull List<GoodsDto> goodsDtos) throws Exception {
                        getMvpView().getAttentionGoodsSuccess1(goodsDtos,pageNo);
                    }
                }));
    }

    public void getClubInfo(String clubId){
        getMvpModel().getClubInfo(clubId)
                .subscribe(newSubscriber(new Consumer<ClubDto>() {
                    @Override
                    public void accept(ClubDto clubDto) throws Exception {
                        getMvpView().showClubInfo(clubDto);
                    }
                }));
    }
}
