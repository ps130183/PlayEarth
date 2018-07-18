package com.km.rmbank.mvp.presenter;

import com.km.rmbank.dto.BankDto;
import com.km.rmbank.dto.WithDrawAccountDto;
import com.km.rmbank.mvp.base.BasePresenter;
import com.km.rmbank.mvp.base.MvpPresenter;
import com.km.rmbank.mvp.model.AddBankCardModel;
import com.km.rmbank.mvp.view.AddBankCardView;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 18/7/3.
 */

public class AddBankCardPresenter extends BasePresenter<AddBankCardView,AddBankCardModel> {

    public AddBankCardPresenter(AddBankCardModel mModel) {
        super(mModel);
    }

    /**
     * 获取可提现 银行列表
     */
    public void getBankList(){
        getMvpModel().getBankList()
                .subscribe(new Consumer<List<BankDto>>() {
                    @Override
                    public void accept(List<BankDto> bankDtos) throws Exception {
                        getMvpView().showBankList(bankDtos);
                    }
                });
    }

    /**
     * 保存银行卡信息
     * @param accountDto
     */
    public void saveBankCard(WithDrawAccountDto accountDto){
        getMvpModel().saveBankCard(accountDto)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        getMvpView().saveBankCardSuccess();
                    }
                });
    }
}
