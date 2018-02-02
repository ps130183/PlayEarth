package com.km.rmbank.mvp.view;

import com.km.rmbank.dto.BannerDto;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.mvp.base.MvpView;
import com.ps.commonadapter.adapter.wrapper.LoadMoreWrapper;

import java.util.List;

/**
 * Created by PengSong on 18/1/17.
 */

public interface IClubView extends MvpView {
    void showClubs(List<ClubDto> clubDtos, LoadMoreWrapper wrapper);

    void showClubInfo(ClubDto clubDto);

    void showBannerList(List<BannerDto> bannerDtos);

    void attentionClubResult(String result);
}
