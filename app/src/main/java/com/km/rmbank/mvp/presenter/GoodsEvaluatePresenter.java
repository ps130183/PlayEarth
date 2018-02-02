package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.EvaluateDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.GoodsEvaluateModel;
import com.km.rmbank.mvp.view.IGoodsEvaluateView;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/1/19.
 */

public class GoodsEvaluatePresenter extends BasePresenter<IGoodsEvaluateView,GoodsEvaluateModel> {

    public GoodsEvaluatePresenter(GoodsEvaluateModel mModel) {
        super(mModel);
    }

    public void getUserEvaluate(String productNo, final int pageNo) {
//        List<EvaluateDto> evaluateDtos = new ArrayList<>();
//        for (int i = 0; i < 10;i++){
//            evaluateDtos.add(new EvaluateDto("昵称"+i,"2017-2-11","看到商品后我的心凌乱了，这他妈是什么东西，吃一次我就上瘾了，\n" +
//                    "你说海参长得跟他妈大象似的有意思吗？"));
//        }
//        mView.showUserEvaluate(evaluateDtos,pageNo);
        getMvpModel().getEvaluateList(productNo,pageNo)
                .subscribe(newSubscriber(new Consumer<List<EvaluateDto>>() {
                    @Override
                    public void accept(@NonNull List<EvaluateDto> evaluateDtos) throws Exception {
                        getMvpView().showUserEvaluate(evaluateDtos,pageNo);
                    }
                }));
    }

    public void evaluateGoods(String orderNo, String content) {
        getMvpView().showLoading();
        getMvpModel().evaluateGoods(orderNo,content)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        getMvpView().evaluateSuccess();
                    }
                }));
    }
}
