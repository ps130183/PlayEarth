package com.km.rmbank.module.main.personal.ticket;

import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;

import com.google.zxing.qrcode.encoder.QRCode;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.TicketDto;
import com.km.rmbank.dto.TicketUseRecordDto;
import com.km.rmbank.mvp.model.TicketListModel;
import com.km.rmbank.mvp.view.ITicketListView;
import com.km.rmbank.mvp.view.TicketListPresenter;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.DateUtils;
import com.km.rmbank.utils.QRCodeUtils;
import com.ps.commonadapter.adapter.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TicketSignActivity extends BaseActivity<ITicketListView,TicketListPresenter> implements ITicketListView {

    private TicketDto ticketDto;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_ticket_sign;
    }

    @Override
    public String getTitleContent() {
        return "电子券签到";
    }

    @Override
    protected TicketListPresenter createPresenter() {
        return new TicketListPresenter(new TicketListModel());
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setRightMenuContent("使用记录");
        simpleTitleBar.setRightMenuClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getTicketUseRecordList(ticketDto.getTicketNo());
            }
        });
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        ticketDto = getIntent().getParcelableExtra("ticketDto");
        if (ticketDto == null){
            return;
        }

        mViewManager.setText(R.id.ticketName,ticketDto.getName());
        mViewManager.setText(R.id.ticketDate,"使用期限：" + DateUtils.getInstance().getDateToYMD(ticketDto.getValidateTime()));
        mViewManager.setText(R.id.ticketCount, ticketDto.getNum() + "次");

        ImageView qrCodeSign = mViewManager.findView(R.id.qrCodeSign);
        qrCodeSign.setImageBitmap(QRCodeUtils.createQRCode(mInstance,"http://www.baidu.com"));

        mViewManager.setText(R.id.signCode,"签到码：" + ticketDto.getTicketNo());
    }

    @Override
    public void showTicketList(LoadMoreWrapper wrapper, List<TicketDto> ticketDtos) {

    }

    @Override
    public void showTicketUseRcordList(List<TicketUseRecordDto> ticketUseRecordDtos) {
        Bundle bundle = getIntent().getExtras();
        bundle.putParcelableArrayList("ticketUsrRecord", (ArrayList<? extends Parcelable>) ticketUseRecordDtos);
        startActivity(TicketUserRecordActivity.class,bundle);
    }
}
