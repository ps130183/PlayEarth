package com.km.rmbank.module.main.personal.address;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.ReceiverAddressDto;
import com.km.rmbank.mvp.model.ReceiverAddressCreateModel;
import com.km.rmbank.mvp.presenter.ReceiverAddressCreatePresenter;
import com.km.rmbank.mvp.view.IReceiverAddressCreateView;
import com.km.rmbank.titleBar.SimpleTitleBar;

import butterknife.BindView;

public class CreateReceiverAddressActivity extends BaseActivity<IReceiverAddressCreateView,ReceiverAddressCreatePresenter> implements IReceiverAddressCreateView {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_address_detail)
    EditText etAddressDetail;

    private SimpleTitleBar simpleTitleBar;

//    @BindView(R.id.et_address_area)
//    EditText etAddressArea;
//    @BindView(R.id.vMasker)
//    View vMasker;

//    private SelectCityPick cityPick;
    private ReceiverAddressDto mReceiverAddressDto;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_create_receiver_address;
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setTitleContent("添加收货地址");
        simpleTitleBar.setRightMenuContent("保存");
        simpleTitleBar.setRightMenuClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    @Override
    protected ReceiverAddressCreatePresenter createPresenter() {
        return new ReceiverAddressCreatePresenter(new ReceiverAddressCreateModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        mReceiverAddressDto = getIntent().getParcelableExtra("receiverAddressDto");

        if (mReceiverAddressDto != null){//修改编辑
            simpleTitleBar.setTitleContent("修改收货地址");
//            cityPick.getOptionsPosition(mReceiverAddressDto.getId());
            initReceiverAddress();
        }
//        cityPick.showOptions(this,etAddressArea,vMasker);
    }

    /**
     * 编辑收货地址时   初始化页面
     */
    private void initReceiverAddress(){
        etName.setText(mReceiverAddressDto.getReceivePerson());
        etPhone.setText(mReceiverAddressDto.getReceivePersonPhone());
//        String area = cityPick.getSelectedContent();
        String detail = mReceiverAddressDto.getReceiveAddress();
//        detail = detail.replace(area,"");
//        etAddressArea.setText(area);
        etAddressDetail.setText(detail);
    }

    public void save(){
        ReceiverAddressDto receiverAddressDto = new ReceiverAddressDto();
        receiverAddressDto.setReceivePerson(etName.getText().toString());
        receiverAddressDto.setReceivePersonPhone(etPhone.getText().toString());
        receiverAddressDto.setReceiveAddress(etAddressDetail.getText().toString());

        if (receiverAddressDto.isEmpty()){
            showToast("请将收货地址信息填写完整");
            return;
        }
        if (mReceiverAddressDto != null){ //更新
            receiverAddressDto.setId(mReceiverAddressDto.getId());
            getPresenter().updateReceiverAddress(receiverAddressDto);
        } else { //新增
            getPresenter().createReceiverAddress(receiverAddressDto);
        }

    }

    @Override
    public void createReceiverAddressSuccess(String id) {
//        cityPick.saveOptionsPosition(id);
        finish();
    }
}
