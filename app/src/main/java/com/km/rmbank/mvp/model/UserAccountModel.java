package com.km.rmbank.mvp.model;

import com.km.rmbank.dto.UserAccountDetailDto;
import com.km.rmbank.dto.UserBalanceDto;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.utils.Constant;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/1/25.
 */

public class UserAccountModel extends BaseModel {

    /**
     * 获取用户余额
     * @return
     */
    public Observable<UserBalanceDto> getUserAccountBalance(){
        return getService().getUserAccountBalance(Constant.userLoginInfo.getToken())
                .compose(this.<UserBalanceDto>applySchedulers());
    }

    /**
     * 获取账户详情
     * @param pageNo
     * @return
     */
    public Observable<List<UserAccountDetailDto>> getUserAccountDetail(int pageNo){
        return getService().getUserAccountDetail(Constant.userLoginInfo.getToken(),pageNo)
                .compose(this.<List<UserAccountDetailDto>>applySchedulers());
    }
}
