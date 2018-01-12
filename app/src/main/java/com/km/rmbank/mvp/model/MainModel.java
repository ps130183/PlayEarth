package com.km.rmbank.mvp.model;


import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.retrofit.RetrofitManager;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/1/8.
 */

public class MainModel extends BaseModel {

    public Observable<List<ClubDto>> loadClubs(int pageNo) {
        return RetrofitManager.getApiService().getClubDtos(pageNo).compose(this.<List<ClubDto>>applySchedulers());
    }
}
