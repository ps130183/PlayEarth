package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.AttentionDto;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.dto.GoodsDto;
import com.km.rmbank.mvp.base.MvpView;

import java.util.List;

/**
 * Created by PengSong on 18/1/25.
 */

public interface IAttentionView extends MvpView {
    void getAttentionGoodsSuccess(List<AttentionDto> goodsDtos, int pageNo);
    void getAttentionGoodsSuccess1(List<GoodsDto> goodsDtos, int pageNo);
    void showClubInfo(ClubDto clubDto);
}
