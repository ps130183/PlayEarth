package com.km.rmbank.module.main.personal.ticket;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.dto.TicketDto;
import com.km.rmbank.dto.TicketUseRecordDto;
import com.km.rmbank.utils.DateUtils;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.MultiItemTypeAdapter;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;

import java.util.List;

public class TicketUserRecordActivity extends BaseActivity {

    @Override
    public int getContentViewRes() {
        return R.layout.activity_ticket_user_record;
    }

    @Override
    public String getTitleContent() {
        return "使用记录";
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        TicketDto ticketDto = getIntent().getParcelableExtra("ticketDto");
        TextView personNum = mViewManager.findView(R.id.personNum);
        if (ticketDto.getType().equals("1")){
            personNum.setText("- 剩余" + ticketDto.getNum() + "次 -");
        } else if (ticketDto.getType().equals("2")){
            personNum.setText("- 剩余" + ticketDto.getNum() + "人 -");
        } else {
            personNum.setText("");
        }

        List<TicketUseRecordDto> ticketUseRecordDtos = getIntent().getParcelableArrayListExtra("ticketUsrRecord");

        RecyclerView recordRecycler = mViewManager.findView(R.id.recordRecycler);
        RecyclerAdapterHelper<TicketUseRecordDto> mHelper = new RecyclerAdapterHelper<>(recordRecycler);
        mHelper.addLinearLayoutManager().addCommonAdapter(R.layout.item_ticket_use_record, ticketUseRecordDtos, new RecyclerAdapterHelper.CommonConvert<TicketUseRecordDto>() {
            @Override
            public void convert(CommonViewHolder holder, final TicketUseRecordDto mData, int position) {
                holder.setText(R.id.scenicName, mData.getClubCommodityName());
                holder.setText(R.id.useDate, "使用时间：" + DateUtils.getInstance().getDate(mData.getTripDate()));

            }
        }).create();
        mHelper.getBasicAdapter().setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<TicketUseRecordDto>() {
            @Override
            public void onItemClick(CommonViewHolder holder, TicketUseRecordDto data, int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("ticketUseRecordDetail",data);
                startActivity(TicketRecordDetailActivity.class,bundle);
            }

            @Override
            public boolean onItemLongClick(CommonViewHolder holder, TicketUseRecordDto data, int position) {
                return false;
            }
        });
    }
}
