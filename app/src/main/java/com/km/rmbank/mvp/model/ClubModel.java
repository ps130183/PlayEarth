package com.km.rmbank.mvp.model;

import com.km.rmbank.R;
import com.km.rmbank.dto.BannerDto;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/1/17.
 */

public class ClubModel extends BaseModel {

    /**
     * 获取推荐的俱乐部
     * @param pageNo
     * @return
     */
    public Observable<List<ClubDto>> getRecommendClubs(int pageNo){
        return getService().getClubList(Constant.userLoginInfo.getToken(),pageNo,"1")
                .compose(this.<List<ClubDto>>applySchedulers());
    }

    /**
     * 查看所有的俱乐部
     * @param pageNo
     * @return
     */
    public Observable<List<ClubDto>> getClubList(int pageNo){
        return getService().getClubList(Constant.userLoginInfo.getToken(),pageNo,"")
                .compose(this.<List<ClubDto>>applySchedulers());
    }

    /**
     * 获取俱乐部详细信息
     * @param clubId
     * @return
     */
    public Observable<ClubDto> getClubInfo(String clubId){
        return getService().getClubInfo(Constant.userLoginInfo.getToken(),clubId)
                .compose(this.<ClubDto>applySchedulers());
    }

    /**
     * 获取首页banner轮播图
     * @return
     */
    public Observable<List<BannerDto>> getBannerList(){
        return getService().getBannerList("")
                .compose(this.<List<BannerDto>>applySchedulers());
    }

    /**
     * 关注俱乐部
     * @param clubId
     * @return
     */
    public Observable<String> attentionClub(String clubId){
        return getService().followGoods(Constant.userLoginInfo.getToken(),"",clubId)
                .compose(this.<String>applySchedulers());
    }

}
