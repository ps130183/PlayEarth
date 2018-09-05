package com.km.rmbank.module.main.payment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.module.main.HomeActivity;
import com.km.rmbank.mvp.model.PayMemberSuccessModel;
import com.km.rmbank.mvp.presenter.PayMemberSuccessPresenter;
import com.km.rmbank.mvp.view.PayMemberSuccessView;

public class PayMemberSuccessActivity extends BaseActivity<PayMemberSuccessView,PayMemberSuccessPresenter> implements PayMemberSuccessView {

    @Override
    public int getContentViewRes() {
        return R.layout.activity_pay_member_success;
    }

    @Override
    public String getTitleContent() {
        return "收货地址";
    }

    @Override
    protected PayMemberSuccessPresenter createPresenter() {
        return new PayMemberSuccessPresenter(new PayMemberSuccessModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {

    }

    /**
     * 提交地址信息
     * @param view
     */
    public void submitAddressInfo(View view) {
        EditText etName = mViewManager.findView(R.id.et_name);
        EditText etPhone = mViewManager.findView(R.id.et_phone);
        EditText etAddress = mViewManager.findView(R.id.et_address);

        String name = etName.getText().toString();
        String phone = etPhone.getText().toString();
        String address = etAddress.getText().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || phone.length() < 11 || TextUtils.isEmpty(address)){
            showToast("请将收货信息填写完整");
            return;
        }

        String payNumber = getIntent().getStringExtra("payNumber");
        getPresenter().saveAddress(payNumber,address,name,phone);
    }

    @Override
    public void submitSuccess() {
        showToast("提交成功，请等待收货！");
        startActivity(HomeActivity.class);
    }
}
