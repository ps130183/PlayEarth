package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.BankDto;
import com.km.rmbank.dto.WithDrawAccountDto;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.mvp.base.MvpModel;
import com.km.rmbank.utils.Constant;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/7/3.
 */

public class AddBankCardModel extends BaseModel {

    /**
     * 获取可提现的 银行的列表
     * @return
     */
    public Observable<List<BankDto>> getBankList(){
        return getService().getBankList("")
                .compose(this.<List<BankDto>>applySchedulers());
    }

    /**
     * 保存银行卡信息
     * @param accountDto
     * @return
     */
    public Observable<String> saveBankCard(WithDrawAccountDto accountDto){
        return getService().saveBankCard(Constant.userLoginInfo.getToken(),
                accountDto.getName(),
                accountDto.getWithdrawPhone(),
                accountDto.getBankId(),
                accountDto.getWithdrawNumber(),
                accountDto.getSmsCode())
                .compose(this.<String>applySchedulers());
    }
}
