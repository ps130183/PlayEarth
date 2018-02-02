package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.MeetingListModel;
import com.km.rmbank.mvp.view.IMeetingListView;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/1/24.
 */

public class MeetingListPresenter extends BasePresenter<IMeetingListView,MeetingListModel> {

    public MeetingListPresenter(MeetingListModel mModel) {
        super(mModel);
    }

    public void loadActionList() {
        getMvpView().showLoading();
        getMvpModel().getActionLists()
                .subscribe(newSubscriber(new Consumer<List<ActionDto>>() {
                    @Override
                    public void accept(List<ActionDto> meetingDtos) throws Exception {
                        getMvpView().showActionList(meetingDtos);
                    }
                }));
    }
}
