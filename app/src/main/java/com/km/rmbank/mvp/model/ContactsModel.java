package com.km.rmbank.mvp.model;

import com.google.gson.Gson;
import com.km.rmbank.dto.ContractDto;
import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.utils.Constant;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/4/2.
 */

public class ContactsModel extends BaseModel {

    /**
     * 提交本地手机联系人信息，跟后台比对以后 返回 相应的状态，绑定、未绑定
     * @param contractDtoList
     * @return
     */
    public Observable<List<ContractDto>> getAllContracts(List<ContractDto> contractDtoList){
        Gson gson = new Gson();
        return getService().getContracts(Constant.userLoginInfo.getToken(),gson.toJson(contractDtoList))
                .compose(this.<List<ContractDto>>applySchedulers());
    }

    /**
     * 获取绑定通讯录 人员的 订单
     * @param phones
     * @return
     */
    public Observable<PayOrderDto> getContactsPayOrder(List<String> phones){
        Gson gson = new Gson();
        return getService().getContactsPayOrder(Constant.userLoginInfo.getToken(),gson.toJson(phones))
                .compose(this.<PayOrderDto>applySchedulers());
    }
}
