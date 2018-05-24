package com.km.rmbank.module.main.personal.contacts;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.alipay.AlipayUtils;
import com.km.rmbank.alipay.PayResult;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.dto.ContactDto;
import com.km.rmbank.dto.PayOrderContactDto;
import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.dto.UserBalanceDto;
import com.km.rmbank.dto.WeiCharParamsDto;
import com.km.rmbank.event.RefreshMyTeamDataEvent;
import com.km.rmbank.event.WXPayResult;
import com.km.rmbank.greendao.ContactManager;
import com.km.rmbank.greendao.bean.Contact;
import com.km.rmbank.module.main.payment.PaymentActivity;
import com.km.rmbank.mvp.model.PaymentModel;
import com.km.rmbank.mvp.presenter.PaymentPresenter;
import com.km.rmbank.mvp.view.IPaymentView;
import com.km.rmbank.utils.DialogUtils;
import com.km.rmbank.utils.EventBusUtils;
import com.km.rmbank.wxpay.WxUtil;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * 转换人脉支付页面
 */
public class PayTransformContactActivity extends BaseActivity<IPaymentView, PaymentPresenter> implements IPaymentView {

    private List<ContactDto> checkedContacts;
    private DialogUtils.CustomBottomDialog mPayTypeDialog;
    private PayOrderContactDto payOrderContactDto;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_pay_transform_contact;
    }

    @Override
    public String getTitleContent() {
        return "转换人脉";
    }

    @Override
    protected PaymentPresenter createPresenter() {
        return new PaymentPresenter(new PaymentModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initView();
    }

    private void initView(){
        payOrderContactDto = getIntent().getParcelableExtra("payOrder");
        checkedContacts = getIntent().getParcelableArrayListExtra("checkedContacts");
        String number = "本次可转换人脉数 <font color='#3285ff'>" + checkedContacts.size() + "</font> 个";
        TextView transformContactNumber = mViewManager.findView(R.id.transformContactNumber);
        transformContactNumber.setText(Html.fromHtml(number));

        mViewManager.setText(R.id.totalMoney,payOrderContactDto.getSumPrice());

        initRecycler(payOrderContactDto.getBookList());
        initPayTypeDialog();
    }

    private void initRecycler(List<ContactDto> contactDtos){
        String invitedNumber = "已被玩家绑定 <font color='#3285ff'>" + contactDtos.size() + "</font> 人";
        TextView invitedContactsNumber = mViewManager.findView(R.id.invitedContactsNumber);
        invitedContactsNumber.setText(Html.fromHtml(invitedNumber));


        RecyclerView invitedContact = mViewManager.findView(R.id.invitedContacts);
        RecyclerAdapterHelper<ContactDto> mHelper = new RecyclerAdapterHelper<>(invitedContact);
        mHelper.addLinearLayoutManager()
                .addCommonAdapter(R.layout.item_personal_contacts, contactDtos, new RecyclerAdapterHelper.CommonConvert<ContactDto>() {
            @Override
            public void convert(CommonViewHolder holder, ContactDto mData, int position) {
                holder.setText(R.id.nickName,mData.getNickName());
                holder.setText(R.id.personPhone,mData.getPhone());
            }
        }).create();
    }

    /**
     * 初始化支付方式 弹出框
     */
    private void initPayTypeDialog(){
        mPayTypeDialog = new DialogUtils.CustomBottomDialog(mInstance,"取消","微信支付","支付宝支付");
        mPayTypeDialog.setOnClickShareDialog(new DialogUtils.CustomBottomDialog.OnClickShareDialog() {
            @Override
            public void clickShareDialog(String itemName, int i) {
                mPayTypeDialog.dimiss();
                switch (i){
                    case 0://微信支付
//                        paySuccess();
                        getPresenter().getWeiChatParams(payOrderContactDto.getPayNumber());
//                        getPresenter().payResultResponse(payOrderContactDto.getPayNumber());
                        break;
                    case 1://支付宝支付
                        getPresenter().getAliPayOrder(payOrderContactDto.getPayNumber());
//                        getPresenter().payResultResponse(payOrderContactDto.getPayNumber());
                        break;
                }
            }
        });
    }

    /**
     * 微信支付结果
     * @param result
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void wxpayResult(WXPayResult result) {
        if (result.getBaseResp().errCode == 0) {//支付成功
            paySuccess(0);
        } else {
            showToast("支付失败");
        }
    }

    /**
     * 支付成功
     * @param type
     */
    private void paySuccess(int type){
        showLoading();
        Observable.fromIterable(checkedContacts)
                .subscribeOn(Schedulers.io())
                .filter(new Predicate<ContactDto>() {
                    @Override
                    public boolean test(ContactDto contactDto) throws Exception {
                        Contact contact = ContactManager.getInstance().getContactById(contactDto.getContactId());
                        contact.setIsInvited(true);
                        ContactManager.getInstance().saveContacts(contact);
                        return checkedContacts.indexOf(contactDto) == checkedContacts.size() - 1;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ContactDto>() {
                    @Override
                    public void accept(ContactDto contact) throws Exception {
                        hideLoading();
                        showToast("转换成功");
                        EventBusUtils.post(new RefreshMyTeamDataEvent());
                        startActivity(MyTeamActivity.class);
                    }
                });
    }


    /**
     * 去支付
     * @param view
     */
    public void toPay(View view) {
        if (mPayTypeDialog != null){
            mPayTypeDialog.show();
        }
    }

    @Override
    public void createPayOrderSuccess(PayOrderDto payOrderDto) {

    }

    @Override
    public void getAlipayParamsSuccess(String alipayParamsDto) {
        AlipayUtils.toPay(mInstance, alipayParamsDto)
                .subscribe(new Consumer<PayResult>() {
                    @Override
                    public void accept(@NonNull PayResult authResult) throws Exception {
                        switch (authResult.getResultStatus()) {
                            case "9000"://支付成功
                                paySuccess(1);
                                break;
                            case "8000"://支付结果未知（可能成功）
                            case "6004":
                                break;
                            case "4000"://支付成功
                                showToast("支付失败");
                                break;
                            case "5000"://支付成功
                                showToast("重复请求");
                                break;
                            case "6001"://支付成功
                                break;
                            case "6002"://支付成功
                                showToast("网络连接出错");
                                break;


                        }
                    }
                });
    }

    @Override
    public void getWeiCharParamsSuccess(WeiCharParamsDto weicharParams) {
        if (WxUtil.check(this, WxUtil.getWXAPI(this))) {
            WxUtil.toPayByWXAPI(weicharParams);
        }
    }

    @Override
    public void payBalanceSuccess() {

    }

    @Override
    public void checkSuccess() {

    }

    @Override
    public void showUserBalance(UserBalanceDto userBalanceDto) {

    }

    @Override
    public void applyScenicResult(PayOrderDto payOrderDto) {

    }

    @Override
    public void showPayResult(String result) {
        paySuccess(0);
    }
}
