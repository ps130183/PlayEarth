package com.km.rmbank.module.main.personal.book;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.entity.BookVenueApplyDto;
import com.km.rmbank.module.main.payment.PaymentActivity;
import com.km.rmbank.mvp.model.PayWanYanVenueModel;
import com.km.rmbank.mvp.presenter.PayWanYanVenuePresenter;
import com.km.rmbank.mvp.view.PayWanYanVenueView;
import com.km.rmbank.utils.DateUtils;

import java.util.Date;

public class PayWanYanVenueActivity extends BaseActivity<PayWanYanVenueView,PayWanYanVenuePresenter> implements PayWanYanVenueView{

    private BookVenueApplyDto mBookVenueApplyDto;
    @Override
    public int getContentViewRes() {
        return R.layout.activity_pay_wan_yan_venue;
    }

    @Override
    protected PayWanYanVenuePresenter createPresenter() {
        return new PayWanYanVenuePresenter(new PayWanYanVenueModel());
    }

    @Override
    public String getTitleContent() {
        mBookVenueApplyDto = getIntent().getParcelableExtra("bookVenueApplyInfo");
        return mBookVenueApplyDto.getPlaceTitle();
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {

        StringBuffer content = new StringBuffer();
        content.append("<div style=\"width: 100%;font-size: 14px;line-height: 20px;\"><span style=\"display: block;\">尊敬的用户你好：</span>")
                .append("<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;你申请的")
                .append(DateUtils.getInstance().dateToString(new Date(mBookVenueApplyDto.getStartDate()),DateUtils.YMDHM2))
                .append("-")
                .append(DateUtils.getInstance().dateToString(new Date(mBookVenueApplyDto.getEndDate()),DateUtils.YMDHM2))
                .append(mBookVenueApplyDto.getPlaceTitle())
                .append("审核已通过。</span>")
                .append("<span style=\"display: block;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本次活动费用")
                .append(mBookVenueApplyDto.getPlacePrice())
                .append("元,请在2小时内完成支付，过期将自动取消本次申请，费用支付完成后你可以去发布活动内容</span></div>");
        WebView webView = mViewManager.findView(R.id.webView);
        webView.loadData(content.toString(),"text/html; charset=UTF-8", null);

        mViewManager.setText(R.id.money,mBookVenueApplyDto.getPlacePrice());
    }

    /**
     * 去支付
     * @param view
     */
    public void toPay(View view) {
        getPresenter().getWanYanOrder(mBookVenueApplyDto.getId(),mBookVenueApplyDto.getPlacePrice());
    }

    @Override
    public void showResult(PayOrderDto orderDto) {
        Bundle bundle = getIntent().getExtras();
        bundle.putParcelable("payOrderDto",orderDto);
        bundle.putInt("payType",4);
        startActivity(PaymentActivity.class,bundle);
    }
}
