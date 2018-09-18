package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.MessageAllDto;
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
    public Observable<List<MessageDto>> getMessage(int pageNo,String contentType){
        return getService().getMessageList(Constant.userLoginInfo.getToken(),pageNo,contentType)
                .compose(this.<List<MessageDto>>applySchedulers());
    }

    /**
     * 获取所有的消息信息
     * @return
     */
    public Observable<MessageAllDto> getMessageAllInfo(){
        return getService().getMessageAllInfo(Constant.userLoginInfo.getToken())
                .compose(this.<MessageAllDto>applySchedulers());
    }

    /**
     * 获取消息详情
     * @param id
     * @return
     */
    public Observable<String> updateMessageStatus(String id){
        return getService().updateMessageStatus(Constant.userLoginInfo.getToken(),id)
                .compose(this.<String>applySchedulers());
    }

    /**
     * 删除消息
     * @param id
     * @return
     */
    public Observable<String> deleteMessage(String id){
        return getService().deleteMessage(Constant.userLoginInfo.getToken(),id)
                .compose(this.<String>applySchedulers());
    }
}
