package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.RecommendPersonalDto;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.utils.Constant;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/1/28.
 */

public class RecommendPersonalModel extends BaseModel {
    /**
     * 获取推荐的人
     * @param pageNo
     * @return
     */
    public Observable<List<RecommendPersonalDto>> getRecommendPersonas(int pageNo){
        return getService().getRecommendPersons(Constant.userLoginInfo.getToken(),pageNo)
                .compose(this.<List<RecommendPersonalDto>>applySchedulers());
    }

    /**
     * 为推荐的人物 点赞
     * @param personId
     * @return
     */
    public Observable<String> pariseRecommendPerson(String personId){
        return getService().pariseRecommendPerson(Constant.userLoginInfo.getToken(),personId)
                .compose(this.<String>applySchedulers());
    }
}
