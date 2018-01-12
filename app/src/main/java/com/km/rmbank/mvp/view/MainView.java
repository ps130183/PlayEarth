package com.km.rmbank.mvp.view;


import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.mvp.base.MvpView;

import java.util.List;

/**
 * Created by PengSong on 18/1/8.
 */

public interface MainView extends MvpView {
    void showClubs(List<ClubDto> clubDtos);
}
