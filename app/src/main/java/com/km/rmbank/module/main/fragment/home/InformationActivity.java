package com.km.rmbank.module.main.fragment.home;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.dto.InformationDto;
import com.km.rmbank.module.main.club.ActionPastDetailActivity;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 首页资讯
 */
public class InformationActivity extends BaseActivity<InformationView,InformationPresenter> implements InformationView {

    @BindView(R.id.informationRecycler)
    RecyclerView informationRecycler;
    private List<InformationDto> informationDtoList;

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
        informationDtoList = new ArrayList<>();
        RecyclerAdapterHelper<String> mHelper = new RecyclerAdapterHelper<>(informationRecycler);
        mHelper.addLinearLayoutManager()
                .addDividerItemDecoration(DividerItemDecoration.VERTICAL)
                .addCommonAdapter(R.layout.item_information, informationDtoList, new RecyclerAdapterHelper.CommonConvert<InformationDto>() {
            @Override
            public void convert(CommonViewHolder holder, InformationDto mData, int position) {
                GlideImageView informationImage = holder.findView(R.id.informationImage);
                CircleProgressView progressView = holder.findView(R.id.progressView);
                GlideUtils.loadImageOnPregress(informationImage,mData.getAvatarUrl(),progressView);

                holder.setText(R.id.actionName,mData.getTitle());
                holder.setText(R.id.actionContent,mData.getSubtitle());
                holder.setText(R.id.personNum,mData.getViewCount());

            }
        }).addRefreshView(mXRefreshView)
                .addRefreshListener(new RecyclerAdapterHelper.OnRefreshListener() {
                    @Override
                    public void refresh() {
                        getPresenter().getInfomationList(1,null);
                    }
                }).addLoadMoreWrapper(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequest(LoadMoreWrapper wrapper, int nextPage) {
                getPresenter().getInfomationList(nextPage,wrapper);
            }
        }).create();

        mHelper.getBasicAdapter().setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<InformationDto>() {
            @Override
            public void onItemClick(CommonViewHolder holder, InformationDto data, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("actionPastId",data.getId());
                startActivity(ActionPastDetailActivity.class,bundle);
            }

            @Override
            public boolean onItemLongClick(CommonViewHolder holder, InformationDto data, int position) {
                return false;
            }
        });

        showLoading();
    }


    @Override
    public void showInformation(int pageNo, List<InformationDto> informationDtos, LoadMoreWrapper wrapper) {
        if (wrapper == null){
            informationDtoList.clear();
        } else {
            wrapper.setLoadMoreFinish(informationDtos.size());
        }
        informationDtoList.addAll(informationDtos);
        informationRecycler.getAdapter().notifyDataSetChanged();
    }
}
