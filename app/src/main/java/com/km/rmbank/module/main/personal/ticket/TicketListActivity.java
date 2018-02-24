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
import com.km.rmbank.mvp.model.TicketListModel;
import com.km.rmbank.mvp.view.ITicketListView;
import com.km.rmbank.mvp.view.TicketListPresenter;
import com.km.rmbank.utils.DateUtils;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.MultiItemTypeAdapter;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;
import com.ps.commonadapter.adapter.wrapper.LoadMoreWrapper;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.ps.glidelib.progress.CircleProgressView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TicketListActivity extends BaseActivity<ITicketListView,TicketListPresenter> implements ITicketListView {

    @BindView(R.id.ticketRecycler)
    RecyclerView ticketRecycler;
    private List<TicketDto> ticketDtoList;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_ticket_list;
    }

    @Override
    public String getTitleContent() {
        return "优惠券";
    }

    @Override
    protected TicketListPresenter createPresenter() {
        return new TicketListPresenter(new TicketListModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initRecycler();
    }

    private void initRecycler(){

        ticketDtoList = new ArrayList<>();

        RecyclerAdapterHelper<TicketDto> mHelper = new RecyclerAdapterHelper<>(ticketRecycler);
        mHelper.addLinearLayoutManager()
                .addCommonAdapter(R.layout.item_ticket, ticketDtoList, new RecyclerAdapterHelper.CommonConvert<TicketDto>() {
            @Override
            public void convert(CommonViewHolder holder, TicketDto mData, int position) {
                //券logo
                GlideImageView imageView = holder.findView(R.id.ticketLogo);
                CircleProgressView progressView = holder.findView(R.id.progressView);
                GlideUtils.loadImageOnPregress(imageView,mData.getTicketLogo(),progressView);

                //券名称
                holder.setText(R.id.ticketName,mData.getName());

                //券状态
                TextView ticketStatus = holder.getTextView(R.id.ticketStatus);
                ticketStatus.setVisibility(View.VISIBLE);
                String status = mData.getStatus();
                if ("0".equals(status)){
                    ticketStatus.setText("未使用");
                    ticketStatus.setBackgroundResource(R.drawable.shape_ticket_use_no);
                } else if ("1".equals(status)){
                    ticketStatus.setText("已使用");
                    ticketStatus.setBackgroundResource(R.drawable.shape_ticket_use_yes);
                } else if ("2".equals(status)){
                    ticketStatus.setText("已过期");
                    ticketStatus.setBackgroundResource(R.drawable.shape_ticket_past_due);
                } else {
                    ticketStatus.setVisibility(View.GONE);
                }

                //券期限
                String useDate = DateUtils.getInstance().getDateToYMD(mData.getValidateTime());
                holder.setText(R.id.useDate,"优惠券使用期限：" + useDate + "止");
            }
        }).addEmptyWrapper(0)
                .addLoadMoreWrapper(new LoadMoreWrapper.OnLoadMoreListener() {
                    @Override
                    public void onLoadMoreRequest(LoadMoreWrapper wrapper, int nextPage) {
                        getPresenter().getAllTicketList(wrapper,nextPage);
                    }
                }).create();

        mHelper.getBasicAdapter().setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<TicketDto>() {
            @Override
            public void onItemClick(CommonViewHolder holder, TicketDto data, int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("ticketDto",data);
                startActivity(TicketSignActivity.class,bundle);
            }

            @Override
            public boolean onItemLongClick(CommonViewHolder holder, TicketDto data, int position) {
                return false;
            }

        });

        getPresenter().getAllTicketList(null,1);
    }

    @Override
    public void showTicketList(LoadMoreWrapper wrapper, List<TicketDto> ticketDtos) {
        if (wrapper != null){
            wrapper.setLoadMoreFinish(ticketDtos.size());
        }
        ticketDtoList.addAll(ticketDtos);
        ticketRecycler.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showTicketUseRcordList(List<TicketUseRecordDto> ticketUseRecordDtos) {

    }
}
