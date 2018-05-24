package com.km.rmbank.mvp.model;

import com.google.gson.Gson;
import com.km.rmbank.dto.ContactDto;
import com.km.rmbank.dto.ContractDto;
import com.km.rmbank.dto.PayOrderContactDto;
import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.greendao.bean.Contact;
import com.km.rmbank.mvp.base.BaseModel;
import com.km.rmbank.utils.Constant;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by PengSong on 18/4/2.
 */

public class ContactsModel extends BaseModel {

    /**
     * 获取绑定通讯录 人员的 订单
     * @param contactDtos
     * @return
     */
    public Observable<PayOrderContactDto> getContactsPayOrder(List<ContactDto> contactDtos){
        Gson gson = new Gson();
        return getService().getContactsOrder(Constant.userLoginInfo.getToken(),gson.toJson(contactDtos))
                .compose(this.<PayOrderContactDto>applySchedulers());
    }
}
