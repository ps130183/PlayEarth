package com.km.rmbank.api;


import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.retrofit.Response;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by PengSong on 17/12/22.
 */

public interface ApiService {

    @GET("information/list")
    Observable<Response<List<ClubDto>>> getClubDtos(@Query("pageNo") int pageNo);
}
