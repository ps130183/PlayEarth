package com.km.rmbank.mvp.presenter;

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

    public void getMessage(final int pageNo) {
        getMvpView().showLoading();
        getMvpModel().getMessage(pageNo)
                .subscribe(newSubscriber(new Consumer<List<MessageDto>>() {
                    @Override
                    public void accept(@NonNull List<MessageDto> messageDtos) throws Exception {
                        getMvpView().showMessage(messageDtos,pageNo);
                    }
                }));
    }
}
