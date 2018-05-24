package com.km.rmbank.module.main.personal.ticket;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.TicketDto;
import com.km.rmbank.dto.TicketUseRecordDto;
import com.km.rmbank.module.webview.AgreementActivity;
import com.km.rmbank.mvp.model.TicketListModel;
import com.km.rmbank.mvp.view.ITicketListView;
import com.km.rmbank.mvp.view.TicketListPresenter;
import com.km.rmbank.titleBar.SimpleTitleBar;
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

    private int[] ticketbgs = {R.color.ticket_bg1,
            R.color.ticket_bg2,
            R.color.ticket_bg3,
            R.color.ticket_bg4,
            R.color.ticket_bg5};

    @Override
    public int getContentViewRes() {
        return R.layout.activity_ticket_list;
    }

    @Override
    public String getTitleContent() {
        return "我的电子券";
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setRightMenuContent("使用说明");
        simpleTitleBar.setRightMenuClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("titleName","电子券使用说明");
                bundle.putString("agreementUrl","http://www.wanzhuandiqiu.com/accounts/quansm.html");
                startActivity(AgreementActivity.class,bundle);
            }
        });
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
                RelativeLayout bottomBg = holder.findView(R.id.bottomBackground);
                switch (mData.getTicketId()){
                    case "6"://朋友基地券
                        GlideUtils.loadImageByRes(imageView,R.mipmap.icon_ticket2);
                        bottomBg.setBackgroundColor(getResources().getColor(ticketbgs[1]));
                        holder.setText(R.id.ticketSubTitle,"限朋友使用");
                        break;
                    case "7"://路演官课程
                        GlideUtils.loadImageByRes(imageView,R.mipmap.icon_ticket5);
                        bottomBg.setBackgroundColor(getResources().getColor(ticketbgs[3]));
                        holder.setText(R.id.ticketSubTitle,"限本人使用");
                        break;
                    case "8"://俱乐部通票
                        GlideUtils.loadImageByRes(imageView,R.mipmap.icon_ticket3);
                        bottomBg.setBackgroundColor(getResources().getColor(ticketbgs[2]));
                        holder.setText(R.id.ticketSubTitle,"限本人使用");
                        break;
                    case "5":
                    case "9"://自己基地券
                        GlideUtils.loadImageByRes(imageView,R.mipmap.icon_ticket1);
                        bottomBg.setBackgroundColor(getResources().getColor(ticketbgs[0]));
                        holder.setText(R.id.ticketSubTitle,"限本人使用");
                        break;

                    default://其他券
                        GlideUtils.loadImageByRes(imageView,R.mipmap.icon_ticket4);
                        bottomBg.setBackgroundColor(getResources().getColor(ticketbgs[4]));
                        holder.setText(R.id.ticketSubTitle,"限本人使用");
                        break;
                }




                //券名称
                holder.setText(R.id.ticketName,mData.getName());


                //券次数
                holder.setText(R.id.ticketCount,mData.getNum());

                //券期限
                String useDate = DateUtils.getInstance().getDateToYMD(mData.getValidateTime());
                holder.setText(R.id.useDate,useDate + "截止");
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
