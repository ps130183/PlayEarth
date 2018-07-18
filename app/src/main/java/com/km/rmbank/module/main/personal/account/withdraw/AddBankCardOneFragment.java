package com.km.rmbank.module.main.personal.account.withdraw;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseFragment;
import com.km.rmbank.dto.WithDrawAccountDto;
import com.km.rmbank.event.StepEvent;
import com.km.rmbank.utils.BankCardTextWatcher;
import com.km.rmbank.utils.EventBusUtils;

import butterknife.OnClick;

/**
 * 添加银行卡第一步
 */
public class AddBankCardOneFragment extends BaseFragment {


    public AddBankCardOneFragment() {
        // Required empty public constructor
    }

    public static AddBankCardOneFragment newInstance(Bundle bundle) {
        AddBankCardOneFragment fragment = new AddBankCardOneFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_add_bank_card_one;
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {
        Button nextStep = mViewManager.findView(R.id.nextStep);
        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText userName = mViewManager.findView(R.id.userName);
                EditText bankCardNo = mViewManager.findView(R.id.bankCardNo);
                String name = userName.getText().toString();
                String cardNo = bankCardNo.getText().toString().replace(" ","");
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(cardNo)){
                    showToast("请将信息填写完整！");
                    return;
                }
                WithDrawAccountDto withDrawAccountDto = new WithDrawAccountDto();
                withDrawAccountDto.setName(name);
                withDrawAccountDto.setWithdrawNumber(cardNo);
                EventBusUtils.post(new StepEvent(1,withDrawAccountDto));
            }
        });

        EditText etBankCardNo =mViewManager.findView(R.id.bankCardNo);
        BankCardTextWatcher.bind(etBankCardNo);
    }


}
