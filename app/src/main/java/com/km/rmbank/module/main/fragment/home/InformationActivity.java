package com.km.rmbank.module.main.fragment.home;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.dto.InformationDto;
import com.km.rmbank.module.main.appoint.ActionPastDetailActivity;
import com.km.rmbank.mvp.model.InformationModel;
import com.km.rmbank.mvp.presenter.InformationPresenter;
import com.km.rmbank.mvp.view.InformationView;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.MultiItemTypeAdapter;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;
import com.ps.commonadapter.adapter.wrapper.LoadMoreWrapper;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.ps.glidelib.progress.CircleProgressView;
import com.ps.mrcyclerview.BViewHolder;
import com.ps.mrcyclerview.ItemViewConvert;
import com.ps.mrcyclerview.LoadMoreListener;
import com.ps.mrcyclerview.MRecyclerView;
import com.ps.mrcyclerview.click.OnClickItemListener;
import com.ps.mrcyclerview.utils.RefreshUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 首页资讯
 */
public class InformationActivity extends BaseActivity<InformationView,InformationPresenter> implements InformationView {

    @BindView(R.id.informationRecycler)
    MRecyclerView<InformationDto> informationRecycler;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_information;
    }

    @Override
    protected InformationPresenter createPresenter() {
        return new InformationPresenter(new InformationModel());
    }

    @Override
    public String getTitleContent() {
        return "资讯";
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {

        initRecycler();
    }

    private void initRecycler(){

        informationRecycler.addContentLayout(R.layout.item_information, new ItemViewConvert<InformationDto>() {
            @Override
            public void convert(@NonNull BViewHolder holder, InformationDto mData, int position, @NonNull List<Object> payloads) {
                GlideImageView informationImage = holder.findView(R.id.informationImage);
                CircleProgressView progressView = holder.findView(R.id.progressView);
                GlideUtils.loadImageOnPregress(informationImage,mData.getAvatarUrl(),progressView);

                holder.setText(R.id.actionName,mData.getTitle());
                holder.setText(R.id.actionContent,mData.getSubtitle());
                holder.setText(R.id.personNum,mData.getViewCount());
            }

        }).create();

        informationRecycler.addRefreshListener(new RefreshUtils.OnRefreshListener() {
            @Override
            public void refresh() {
                getPresenter().getInfomationList(1,null);
            }
        });

        informationRecycler.addLoadMoreListener(new LoadMoreListener() {
            @Override
            public void loadMore(int nextPage) {
                getPresenter().getInfomationList(nextPage,null);
            }
        });

        informationRecycler.addClickItemListener(new OnClickItemListener() {
            @Override
            public void clickItem(Object mData, int position) {
                InformationDto data = (InformationDto) mData;
                Bundle bundle = new Bundle();
                bundle.putString("actionPastId",data.getId());
                startActivity(ActionPastDetailActivity.class,bundle);
            }
        });
        informationRecycler.startRefresh();
    }


    @Override
    public void showInformation(int pageNo, List<InformationDto> informationDtos, LoadMoreWrapper wrapper) {
        informationRecycler.stopRefresh();
        informationRecycler.loadDataOfNextPage(informationDtos);
    }
}
