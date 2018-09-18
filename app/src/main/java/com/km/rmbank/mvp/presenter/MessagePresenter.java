package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.MessageAllDto;
import com.km.rmbank.dto.MessageDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.MessageModel;
import com.km.rmbank.mvp.view.IMessageView;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/1/19.
 */

public class MessagePresenter extends BasePresenter<IMessageView,MessageModel> {

    public MessagePresenter(MessageModel mModel) {
        super(mModel);
    }

    public void getMessage(final int pageNo,String contentType) {
//        getMvpView().showLoading();
        getMvpModel().getMessage(pageNo,contentType)
                .subscribe(newSubscriber(new Consumer<List<MessageDto>>() {
                    @Override
                    public void accept(@NonNull List<MessageDto> messageDtos) throws Exception {
                        getMvpView().showMessage(messageDtos,pageNo);
                    }
                }));
    }

    public void getAllMessage(){
        getMvpModel().getMessageAllInfo()
                .subscribe(newSubscriber(new Consumer<MessageAllDto>() {
                    @Override
                    public void accept(MessageAllDto messageAllDto) throws Exception {
                        getMvpView().showMessageAl(messageAllDto);
                    }
                }));
    }

    public void updateMessageStatus(String id){
        getMvpModel().updateMessageStatus(id)
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(String String) throws Exception {
                        getMvpView().updateMessageResult();
                    }
                }));
    }

    public void deleteMessage(final MessageDto messageDto){
        getMvpModel().deleteMessage(messageDto.getId())
                .subscribe(newSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        getMvpView().deleteSuccess(messageDto);
                    }
                }));
    }
}
