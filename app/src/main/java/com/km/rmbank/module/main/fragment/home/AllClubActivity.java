package com.km.rmbank.module.main.fragment.home;

import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ConvertUtils;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.BannerDto;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.event.AttentionClubEvent;
import com.km.rmbank.module.main.personal.member.club.ClubActivity;
import com.km.rmbank.mvp.model.ClubModel;
import com.km.rmbank.mvp.presenter.ClubPresenter;
import com.km.rmbank.mvp.view.IClubView;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.km.rmbank.utils.EventBusUtils;
import com.km.rmbank.utils.ViewUtils;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.MultiItemTypeAdapter;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;
import com.ps.commonadapter.adapter.wrapper.LoadMoreWrapper;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.ps.glidelib.progress.CircleProgressView;
import com.ruffian.library.RTextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
        simpleTitleBar.setTitleContent("推荐俱乐部");
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
        EventBusUtils.post(new AttentionClubEvent(result,true));
    }

    /**
     * 设置推荐的俱乐部列表
     *
     * @param clubRecycler
     */
    private void setRecommendClubList(RecyclerView clubRecycler) {

        mHelper = new RecyclerAdapterHelper<>(clubRecycler);

        mHelper.addLinearLayoutManager()
                .addDividerItemDecoration(DividerItemDecoration.VERTICAL)
                .addCommonAdapter(R.layout.item_club, clubDtos, new RecyclerAdapterHelper.CommonConvert<ClubDto>() {
                    @Override
                    public void convert(CommonViewHolder holder, final ClubDto mData, int position) {
                        LinearLayout content = holder.findView(R.id.content);
                        if (position == 0){
                            ViewUtils.setMargins(content,0, ConvertUtils.dp2px(4),0,0);
                        } else {
                            ViewUtils.setMargins(content,0, 0,0,0);
                        }

                        holder.addRippleEffectOnClick();
                        holder.setText(R.id.clubName, mData.getClubName());

                        GlideImageView imageView = holder.findView(R.id.clubLogo);
                        CircleProgressView progressView = holder.findView(R.id.progressView);
                        GlideUtils.loadImageOnPregress(imageView,mData.getClubLogo(),progressView);

                        holder.setText(R.id.clubIntroduce, mData.getContent());

                        holder.setText(R.id.keppCount,mData.getKeepCount()+"");
                        holder.setText(R.id.fans,mData.getFans());

                        //关注
                        RTextView attention = holder.findView(R.id.attention);
                        if (!mData.getKeepStatus()){
                            attention.setText(R.string.attention);
                            attention.setTextColorNormal(ContextCompat.getColor(mInstance,R.color.colorAccent));
//                            attention.setIconNormal(getResources().getDrawable(R.mipmap.icon_home_arrow_right_baoming));
                            attention.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getPresenter().attentionClub(mData.getId());
                                }
                            });
                        } else {
                            attention.setText(R.string.attentioned);
                            attention.setTextColorNormal(ContextCompat.getColor(mInstance,R.color.text_color_block4));
                            attention.setOnClickListener(null);
                        }
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onClickAttentionClub(AttentionClubEvent event){
        for (int i = 0 ; i < clubDtos.size(); i++){
            ClubDto clubDto = clubDtos.get(i);
            if (event.getClubId().equals(clubDto.getId())){
                clubDto.setKeepStatus(event.isAttention() ? 1 : 0);
                clubRecycler.getAdapter().notifyItemChanged(i);
                break;
            }
        }
    }
}
