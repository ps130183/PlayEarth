package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.MessageDto;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.utils.Constant;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/1/19.
 */

public class MessageModel extends BaseModel {

    /**
     * 获取消息列表
     * @param pageNo
     * @return
     */
    public Observable<List<MessageDto>> getMessage(int pageNo){
        return getService().getMessage(Constant.userLoginInfo.getToken(),pageNo)
                .compose(this.<List<MessageDto>>applySchedulers());
    }
}
