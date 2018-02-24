package com.km.rmbank.module.main.personal.ticket;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.dto.TicketUseRecordDto;
import com.km.rmbank.utils.DateUtils;

public class TicketRecordDetailActivity extends BaseActivity {


    @Override
    public int getContentViewRes() {
        return R.layout.activity_ticket_record_detail;
    }

    @Override
    public String getTitleContent() {
        return "使用详情";
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        TicketUseRecordDto ticketUseRecordDto = getIntent().getParcelableExtra("ticketUseRecordDetail");
        mViewManager.setText(R.id.scenicName,ticketUseRecordDto.getClubCommodityName());
        mViewManager.setText(R.id.personNum,ticketUseRecordDto.getPersonNum()+"");
        mViewManager.setText(R.id.price,ticketUseRecordDto.getPrice() + "元");
        mViewManager.setText(R.id.useDate, DateUtils.getInstance().getDate(ticketUseRecordDto.getTripDate()));
    }
}
