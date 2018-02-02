package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.EvaluateDto;
import com.km.rmbank.mvp.base.MvpView;

import java.util.List;

/**
 * Created by PengSong on 18/1/19.
 */

public interface IGoodsEvaluateView extends MvpView {
    void showUserEvaluate(List<EvaluateDto> evaluateDtos, int pageNo);
    void evaluateSuccess();
}
