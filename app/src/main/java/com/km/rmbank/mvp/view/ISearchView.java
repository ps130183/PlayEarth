package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.mvp.base.MvpView;

import java.util.List;

/**
 * Created by PengSong on 18/1/19.
 */

public interface ISearchView extends MvpView {
    void showSearchResult(List<GoodsDto> goodsDtos, int pageNo);
}
