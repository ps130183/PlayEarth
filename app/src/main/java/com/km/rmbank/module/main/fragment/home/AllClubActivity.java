package com.km.rmbank.module.main.fragment.home;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.BannerDto;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.module.main.personal.member.club.ClubActivity;
import com.km.rmbank.mvp.model.ClubModel;
import com.km.rmbank.mvp.presenter.ClubPresenter;
import com.km.rmbank.mvp.view.IClubView;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.MultiItemTypeAdapter;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;
import com.ps.commonadapter.adapter.wrapper.LoadMoreWrapper;
import com.ps.glidelib.GlideUtils;

import java.util.ArrayList;
import java.util.List;

public class AllClubActivity extends BaseActivity<IClubView, ClubPresenter> implements IClubView {

    private RecyclerView clubRecycler;
    private List<ClubDto> clubDtos;
    private RecyclerAdapterHelper<ClubDto> mHelper;
    private LoadMoreWrapper loadMoreWrapper;

    @Override
    public int getContentViewRes() {
        return R.layout.activity_all_club;
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setTitleContent("全部俱乐部");
    }

    @Override
    protected ClubPresenter createPresenter() {
        return new ClubPresenter(new ClubModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        clubDtos = new ArrayList<>();
        clubRecycler = mViewManager.getRecyclerView(R.id.clubRecycler);
        setRecommendClubList(clubRecycler);
    }

    @Override
    public void showClubs(List<ClubDto> clubDtos, LoadMoreWrapper wrapper) {
        if (wrapper != null) {
            wrapper.setLoadMoreFinish(clubDtos.size());
        } else {
            this.clubDtos.clear();
        }
        this.clubDtos.addAll(clubDtos);
        clubRecycler.getAdapter().notifyDataSetChanged();
        hideLoading();

    }

    @Override
    public void showClubInfo(ClubDto clubDto) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("clubInfo",clubDto);
        startActivity(ClubActivity.class,bundle);
    }

    @Override
    public void showBannerList(List<BannerDto> bannerDtos) {

    }

    @Override
    public void attentionClubResult(String result) {

    }

    /**
     * 设置推荐的俱乐部列表
     *
     * @param clubRecycler
     */
    private void setRecommendClubList(RecyclerView clubRecycler) {

        mHelper = new RecyclerAdapterHelper<>(clubRecycler);

        mHelper.addLinearLayoutManager()
                .addCommonAdapter(R.layout.item_club, clubDtos, new RecyclerAdapterHelper.CommonConvert<ClubDto>() {
                    @Override
                    public void convert(CommonViewHolder holder, ClubDto mData, int position) {
                        holder.addRippleEffectOnClick();
                        holder.setText(R.id.clubName, mData.getClubName());
                        GlideUtils.loadImage(AllClubActivity.this, mData.getClubLogo(), holder.getImageView(R.id.clubLogo));
                        holder.setText(R.id.clubIntroduce, mData.getContent());
                    }
                }).addRefreshView(mXRefreshView)
                .addRefreshListener(new RecyclerAdapterHelper.OnRefreshListener() {
                    @Override
                    public void refresh() {
                        getPresenter().getAllClub(1, null);
                    }
                }).addLoadMoreWrapper(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequest(LoadMoreWrapper wrapper, int nextPage) {
                getPresenter().getAllClub(nextPage, wrapper);
            }
        }).create();

        showLoading();

        mHelper.getBasicAdapter().setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<ClubDto>() {
            @Override
            public void onItemClick(CommonViewHolder holder, ClubDto data, int position) {
                getPresenter().getClubInfo(data.getId());
            }

            @Override
            public boolean onItemLongClick(CommonViewHolder holder, ClubDto data, int position) {
                return false;
            }
        });

    }
}
