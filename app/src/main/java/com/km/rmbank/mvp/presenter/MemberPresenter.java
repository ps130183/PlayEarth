package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.MemberDto;
import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.model.MemberModel;
import com.km.rmbank.mvp.view.IMemberView;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/1/27.
 */

public class MemberPresenter extends BasePresenter<IMemberView,MemberModel> {

    public MemberPresenter(MemberModel mModel) {
        super(mModel);
    }

    public void getMemberList(){
        getMvpModel().getMemberList()
                .subscribe(newSubscriber(new Consumer<List<MemberDto>>() {
                    @Override
                    public void accept(List<MemberDto> memberDtos) throws Exception {
                        getMvpView().showMemberList(memberDtos);
                    }
                }));
    }

    public void createPayOrder(String amount,String memberType){
        getMvpModel().createPayOrder(amount,memberType)
                .subscribe(newSubscriber(new Consumer<PayOrderDto>() {
                    @Override
                    public void accept(PayOrderDto payOrderDto) throws Exception {
                        getMvpView().showPayOrderResult(payOrderDto);
                    }
                }));
    }
}
