package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.ActionMemberDto;
import com.km.rmbank.dto.ActionMemberNumDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.ActionJoinMemberModel;
import com.km.rmbank.mvp.view.IActionJoinMemberView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/1/27.
 */

public class ActionJoinMemberPresenter extends BasePresenter<IActionJoinMemberView,ActionJoinMemberModel> {

    public ActionJoinMemberPresenter(ActionJoinMemberModel mModel) {
        super(mModel);
    }
    public void getActionMemberList(String actionId, final int pageNo) {
        getMvpView().showLoading();
        getMvpModel().getActionMemberList(actionId,pageNo)
                .subscribe(newSubscriber(new Consumer<List<ActionMemberDto>>() {
                    @Override
                    public void accept(@NonNull List<ActionMemberDto> actionMemberDtos) throws Exception {
                        getMvpView().showActionMemberList(actionMemberDtos,pageNo);
                    }
                }));
    }

    public void getActionMemberNum(String actionId) {
        getMvpView().showLoading();
        getMvpModel().getActionMemberNum(actionId)
                .subscribe(newSubscriber(new Consumer<ActionMemberNumDto>() {
                    @Override
                    public void accept(@NonNull ActionMemberNumDto actionMemberNumDto) throws Exception {
                        getMvpView().showActionMemberNum(actionMemberNumDto.getRegistrationNumber());
                    }
                }));
    }
}
