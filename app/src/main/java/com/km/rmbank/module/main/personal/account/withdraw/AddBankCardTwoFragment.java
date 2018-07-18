package com.km.rmbank.module.main.personal.account.withdraw;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.blankj.utilcode.util.RegexUtils;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseFragment;
import com.km.rmbank.dto.BankDto;
import com.km.rmbank.dto.WithDrawAccountDto;
import com.km.rmbank.event.StepEvent;
import com.km.rmbank.utils.EventBusUtils;
import com.km.rmbank.utils.dialog.WindowBottomDialog;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.ps.mrcyclerview.BViewHolder;
import com.ps.mrcyclerview.ItemViewConvert;
import com.ps.mrcyclerview.MRecyclerView;

import java.util.List;

import butterknife.OnClick;

/**
 * 添加银行卡第二布
 */
public class AddBankCardTwoFragment extends BaseFragment {

    private WindowBottomDialog mSelectBankDialog;

    private MRecyclerView<BankDto> mBankRecycler;
    private List<BankDto> bankEntities;
    private BankDto mCheckedBank;

    public AddBankCardTwoFragment() {
        // Required empty public constructor
    }

    public static AddBankCardTwoFragment newInstance(Bundle bundle) {
        AddBankCardTwoFragment fragment = new AddBankCardTwoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_add_bank_card_two;
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {
        bankEntities = getArguments().getParcelableArrayList("bankList");
        initView();
        initSelectBankDialog();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mCheckedBank != null){
            mViewManager.findView(R.id.unCheckedBank).setVisibility(View.GONE);
            mViewManager.findView(R.id.checkedBank).setVisibility(View.VISIBLE);
            GlideImageView bankIcon = mViewManager.findView(R.id.bankIcon);
            GlideUtils.loadImageOnPregress(bankIcon, mCheckedBank.getBankLogo(), null);
            mViewManager.setText(R.id.bankName, mCheckedBank.getBankName());
        }

    }

    private void initView() {
        Button nextStep = mViewManager.findView(R.id.nextStep);
        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText bankPhone = mViewManager.findView(R.id.bankPhone);
                String phone = bankPhone.getText().toString();
                if (mCheckedBank == null){
                    showToast("请选择所属银行！");
                    return;
                } else if (TextUtils.isEmpty(phone) || !RegexUtils.isMobileExact(phone)){
                    showToast("请填写正确的手机号！");
                    return;
                }
                WithDrawAccountDto withDrawAccountDto = new WithDrawAccountDto();
                withDrawAccountDto.setBankId(mCheckedBank.getId());
                withDrawAccountDto.setWithdrawPhone(phone);
                EventBusUtils.post(new StepEvent(1,withDrawAccountDto));
            }
        });


    }

    private void initSelectBankDialog() {
        mSelectBankDialog = new WindowBottomDialog(getContext(), R.layout.dialog_bottom_select_bank, new WindowBottomDialog.CustomViewConvert() {
            @Override
            public void convert(View contentView) {
                ImageView close = contentView.findViewById(R.id.ivClose);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mSelectBankDialog.dimiss();
                    }
                });


                mBankRecycler = contentView.findViewById(R.id.bankRecycler);
//                mBankRecycler.getLayoutParams().height = height/2;
                mBankRecycler.addContentLayout(R.layout.item_select_bank, new ItemViewConvert<BankDto>() {
                    @Override
                    public void convert(@NonNull BViewHolder holder, final BankDto mData, final int position, @NonNull List<Object> payloads) {
                        if (payloads.size() == 0){
                            GlideImageView bankIcon = holder.findView(R.id.bank_icon);
                            GlideUtils.loadImageOnPregress(bankIcon, mData.getBankLogo(), null);
                            holder.setText(R.id.bankName, mData.getBankName());

                            RelativeLayout rlContent = holder.findView(R.id.rlContent);
                            CheckBox checkBox = holder.findView(R.id.checkbox);
                            checkBox.setVisibility(mData.isChecked() ? View.VISIBLE : View.GONE);
                            checkBox.setChecked(mData.isChecked());
                            rlContent.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    int prePosition = -1;
                                    for (int i = 0; i < bankEntities.size(); i++) {
                                        BankDto bankDto = bankEntities.get(i);
                                        if (bankDto.isChecked()) {
                                            bankDto.setChecked(false);
                                            prePosition = i;
                                            break;
                                        }
                                    }
                                    mData.setChecked(true);
                                    mBankRecycler.update(mData, position,"1");
                                    if (prePosition >= 0) {
                                        mBankRecycler.update(bankEntities.get(prePosition), prePosition,"1");
                                    }

                                    mViewManager.findView(R.id.unCheckedBank).setVisibility(View.GONE);
                                    mViewManager.findView(R.id.checkedBank).setVisibility(View.VISIBLE);
                                    GlideImageView bankIcon = mViewManager.findView(R.id.bankIcon);
                                    GlideUtils.loadImageOnPregress(bankIcon, mData.getBankLogo(), null);
                                    mViewManager.setText(R.id.bankName, mData.getBankName());
                                    mCheckedBank = mData;
                                    mSelectBankDialog.dimiss();
                                }
                            });
                        } else {//刷新布局
                            CheckBox checkBox = holder.findView(R.id.checkbox);
                            checkBox.setVisibility(mData.isChecked() ? View.VISIBLE : View.GONE);
                            checkBox.setChecked(mData.isChecked());
                        }

                    }
                }).create();

                mBankRecycler.loadDataOfNextPage(bankEntities);
            }
        });
    }

    @OnClick(R.id.selectBank)
    public void selectBank(View view) {
        if (mSelectBankDialog != null) {
            mSelectBankDialog.show();
        }
    }

}
