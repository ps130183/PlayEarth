package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.RecommendPersonalDto;
import com.km.rmbank.mvp.base.MvpView;

import java.util.List;

/**
 * Created by PengSong on 18/1/28.
 */

public interface IRecommendPersonalView extends MvpView {
    void showRecommendPersons(List<RecommendPersonalDto> recommendPersonalDtos,int pageNo);
    void pariseResult(int position);
}
