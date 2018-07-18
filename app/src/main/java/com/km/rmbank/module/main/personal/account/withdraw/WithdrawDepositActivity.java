package com.km.rmbank.module.main.personal.account.withdraw;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.UserBalanceDto;
import com.km.rmbank.dto.WithDrawAccountDto;
import com.km.rmbank.mvp.model.WithDrawModel;
import com.km.rmbank.mvp.presenter.WithDrawPresenter;
import com.km.rmbank.mvp.view.IWithDrawView;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.InputFilterUtils;
import com.km.rmbank.utils.dialog.DialogUtils;
import com.km.rmbank.utils.dialog.WindowBottomDialog;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.ps.mrcyclerview.BViewHolder;
import com.ps.mrcyclerview.ItemViewConvert;
import com.ps.mrcyclerview.MRecyclerView;
import com.ps.mrcyclerview.click.OnClickItemListener;

import java.util.List;


public class WithdrawDepositActivity extends BaseActivity<IWithDrawView,WithDrawPresenter> implements IWithDrawView {

    private EditText withdrawMoney;

    private WindowBottomDialog mSelectBankDialog;

    private MRecyclerView<WithDrawAccountDto> mBankRecycler;
    private TextView balance;
    private WithDrawAccountDto mCheckedAccount;

    private String userBalance;
    @Override
    public int getContentViewRes() {
        return R.layout.activity_withdraw_deposit;
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setTitleContent("提现");
        simpleTitleBar.setRightMenuContent("提现说明");
        simpleTitleBar.setRightMenuClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("提现说明");
            }
        });
    }

    @Override
    protected WithDrawPresenter createPresenter() {
        return new WithDrawPresenter(new WithDrawModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        balance = mViewManager.findView(R.id.balance);
        withdrawMoney = mViewManager.findView(R.id.withdraw_money);
        withdrawMoney.setFilters(InputFilterUtils.filters2);

        initSelectBankDialog();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().getUserBalance();
        getPresenter().getWithDrawList();
    }

    private void initSelectBankDialog(){
        mSelectBankDialog = new WindowBottomDialog(mInstance, R.layout.dialog_bottom_select_bank_card, new WindowBottomDialog.CustomViewConvert() {
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
                mBankRecycler.addContentLayout(R.layout.item_select_bank_card, new ItemViewConvert<WithDrawAccountDto>() {
                    @Override
                    public void convert(@NonNull BViewHolder holder, final WithDrawAccountDto mData, final int position, @NonNull List<Object> payloads) {
                        if (payloads.size() == 0){
                            boolean noBank = mData.getTypeName() == null;
                            holder.setText(R.id.bankName,noBank ? mData.getBankName() : mData.getTypeName());
                            holder.setText(R.id.bankTailNumber,"尾号"+mData.getWithdrawNumber().substring(mData.getWithdrawNumber().length()-4,mData.getWithdrawNumber().length()));
                            GlideImageView bankIcon = holder.findView(R.id.bank_icon);
                            GlideUtils.loadImageOnPregress(bankIcon,mData.getBankLogo(),null);
                        }
                        CheckBox mCheckBox = holder.findView(R.id.checkbox);
                        mCheckBox.setVisibility(mData.isChecked() ? View.VISIBLE : View.GONE);
                        mCheckBox.setChecked(mData.isChecked());

                        SwipeLayout swipeLayout = holder.findView(R.id.swipe_layout);
                        if (swipeLayout != null) {
                            //set show mode.
                            swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
                            swipeLayout.setSwipeEnabled(true);
                            swipeLayout.setClickToClose(true); //点击其他区域关闭侧滑
                        }

                        holder.findView(R.id.delete).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DialogUtils.showDefaultAlertDialog("是否删除该银行卡?", new DialogUtils.ClickListener() {
                                    @Override
                                    public void clickConfirm() {
                                        getPresenter().deleteWithdrawAccount(mData);
                                    }
                                });

                            }
                        });

                        holder.findView(R.id.rlMainContent).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                WithDrawAccountDto mAccount = (WithDrawAccountDto) mData;
                                int prePosition = -1;
                                List<WithDrawAccountDto> withDrawAccountDtos = mBankRecycler.getAllDatas();
                                for (int i = 0; i < withDrawAccountDtos.size(); i++) {
                                    WithDrawAccountDto withDrawAccountDto = withDrawAccountDtos.get(i);
                                    if (withDrawAccountDto.isChecked()) {
                                        withDrawAccountDto.setChecked(false);
                                        prePosition = i;
                                        break;
                                    }
                                }
                                mAccount.setChecked(true);
                                mBankRecycler.update(mAccount, position,"1");
                                if (prePosition >= 0) {
                                    mBankRecycler.update(withDrawAccountDtos.get(prePosition), prePosition,"1");
                                }

                                mViewManager.findView(R.id.unCheckedAccount).setVisibility(View.GONE);
                                mViewManager.findView(R.id.checkedAccount).setVisibility(View.VISIBLE);
                                GlideImageView bankIcon = mViewManager.findView(R.id.bank_icon);
                                GlideUtils.loadImageOnPregress(bankIcon, mAccount.getBankLogo(), null);
                                mViewManager.setText(R.id.bankName, mAccount.getBankName());
                                mViewManager.setText(R.id.bankTailNumber, "尾号"+mAccount.getWithdrawNumber().substring(mAccount.getWithdrawNumber().length()-4,mAccount.getWithdrawNumber().length()));
                                mCheckedAccount = mAccount;
                                mSelectBankDialog.dimiss();
                            }
                        });


                    }

                }).addFooterLayout(R.layout.footer_select_bank, new ItemViewConvert() {
                    @Override
                    public void convert(@NonNull BViewHolder holder, Object mData, int position, @NonNull List payloads) {
                        LinearLayout addBankCard = holder.findView(R.id.addBankCard);
                        addBankCard.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(AddBankCardActivity.class);
                            }
                        });
                    }
                }).create();
            }
        });

        //选择银行卡 弹出框 显示
        FrameLayout selectBank = mViewManager.findView(R.id.selectBank);
        selectBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectBankDialog.show();
            }
        });
    }


    @Override
    public void showBalance(UserBalanceDto userBalanceDto) {
        userBalance = userBalanceDto.getBalance();
        balance.setText(userBalance);
    }

    @Override
    public void withdrawSuccess(String money) {
        Bundle bundle = new Bundle();
        bundle.putString("withdrawMoney",money);
        finish();
        startActivity(WithdrawHintActivity.class,bundle);
    }

    @Override
    public void creatOrUpdateSuccess() {

    }

    @Override
    public void showWithDrawList(List<WithDrawAccountDto> withDrawAccountDtos) {
        mBankRecycler.clear();
        if (mCheckedAccount != null){
            for (WithDrawAccountDto accountDto : withDrawAccountDtos){
                if (accountDto.getId().equals(mCheckedAccount.getId())){
                    accountDto.setChecked(true);
                }
            }
        }
        mBankRecycler.loadDataOfNextPage(withDrawAccountDtos);
    }

    @Override
    public void deleteSuccess(WithDrawAccountDto withDrawAccountDto) {
        mBankRecycler.delete(withDrawAccountDto);
    }

    /**
     * 提现所有余额
     * @param view
     */
    public void withdrawAllMoney(View view) {
        EditText withdrawMoney = mViewManager.findView(R.id.withdraw_money);
        withdrawMoney.setText(userBalance);
        withdrawMoney.setSelection(userBalance.length());
    }

    /**
     * 提交提现
     * @param view
     */
    public void submitWithdraw(View view) {
        EditText withdrawMoney = mViewManager.findView(R.id.withdraw_money);
        String money = withdrawMoney.getText().toString();
        if (mCheckedAccount == null){
            showToast("请选择要提现的银行卡！");
            return;
        }
        if (TextUtils.isEmpty(money)){
            showToast("请填写提现金额");
            return;
        }

        getPresenter().submitWithdraw(mCheckedAccount,money);
    }
}
