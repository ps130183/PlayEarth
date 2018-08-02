package com.km.rmbank.module.main.personal.ticket;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

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
import com.ps.commonadapter.adapter.wrapper.LoadMoreWrapper;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.ps.mrcyclerview.BViewHolder;
import com.ps.mrcyclerview.ItemViewConvert;
import com.ps.mrcyclerview.LoadMoreListener;
import com.ps.mrcyclerview.MRecyclerView;
import com.ps.mrcyclerview.click.OnClickItemListener;
import com.ps.mrcyclerview.utils.RefreshUtils;

import java.util.List;

import butterknife.BindView;

public class TicketListActivity extends BaseActivity<ITicketListView,TicketListPresenter> implements ITicketListView {

    @BindView(R.id.ticketRecycler)
    MRecyclerView<TicketDto> ticketRecycler;
//    private List<TicketDto> ticketDtoList;

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


        ticketRecycler.addContentLayout(R.layout.item_ticket, new ItemViewConvert<TicketDto>() {
            @Override
            public void convert(@NonNull BViewHolder holder, TicketDto mData, int position, @NonNull List<Object> payloads) {
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

        }).create();
        ticketRecycler.addLoadMoreListener(new LoadMoreListener() {
            @Override
            public void loadMore(int nextPage) {
                getPresenter().getAllTicketList(null,nextPage);
            }
        });
        ticketRecycler.addRefreshListener(new RefreshUtils.OnRefreshListener() {
            @Override
            public void refresh() {
                getPresenter().getAllTicketList(null,1);
            }
        });

        ticketRecycler.addClickItemListener(new OnClickItemListener() {
            @Override
            public void clickItem(Object mData, int position) {
                TicketDto data = (TicketDto) mData;
                Bundle bundle = new Bundle();
                bundle.putParcelable("ticketDto",data);
                startActivity(TicketSignActivity.class,bundle);
            }
        });
        ticketRecycler.startRefresh();
    }

    @Override
    public void showTicketList(LoadMoreWrapper wrapper, List<TicketDto> ticketDtos) {
        ticketRecycler.stopRefresh();
        ticketRecycler.loadDataOfNextPage(ticketDtos);
    }

    @Override
    public void showTicketUseRcordList(List<TicketUseRecordDto> ticketUseRecordDtos) {

    }
}
