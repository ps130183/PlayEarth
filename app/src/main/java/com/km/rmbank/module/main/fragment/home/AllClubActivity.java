package com.km.rmbank.module.main.fragment.home;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.andview.refreshview.XRefreshViewState;
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
import com.ps.commonadapter.adapter.wrapper.LoadMoreWrapper;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.ps.glidelib.progress.CircleProgressView;
import com.ps.mrcyclerview.BViewHolder;
import com.ps.mrcyclerview.ItemViewConvert;
import com.ps.mrcyclerview.LoadMoreListener;
import com.ps.mrcyclerview.MRecyclerView;
import com.ps.mrcyclerview.click.OnClickItemListener;
import com.ruffian.library.RTextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class AllClubActivity extends BaseActivity<IClubView, ClubPresenter> implements IClubView {

    private MRecyclerView<ClubDto> clubRecycler;
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
        clubRecycler = mViewManager.findView(R.id.clubRecycler);
        setRecommendClubList();
    }

    @Override
    public void showClubs(List<ClubDto> clubDtos, LoadMoreWrapper wrapper) {
        clubRecycler.stopRefresh();

        clubRecycler.loadDataOfNextPage(clubDtos);
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
     */
    private void setRecommendClubList() {

        clubRecycler.addContentLayout(R.layout.item_club, new ItemViewConvert<ClubDto>() {
            @Override
            public void convert(@NonNull BViewHolder holder, final ClubDto mData, int position, @NonNull List<Object> payloads) {
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
                    attention.setTextColorNormal(ContextCompat.getColor(mInstance,R.color.text_color_block8));
                    attention.setOnClickListener(null);
                }
            }

        }).create();
        clubRecycler.addLoadMoreListener(new LoadMoreListener() {
            @Override
            public void loadMore(int nextPage) {
                getPresenter().getAllClub(nextPage, null);
            }
        });

        clubRecycler.addRefreshListener(new com.ps.mrcyclerview.utils.RefreshUtils.OnRefreshListener() {
            @Override
            public void refresh() {
                getPresenter().getAllClub(1, null);
            }
        });
        clubRecycler.addClickItemListener(new OnClickItemListener() {
            @Override
            public void clickItem(Object mData, int position) {
                ClubDto data = (ClubDto) mData;
                getPresenter().getClubInfo(data.getId());
            }
        });

        clubRecycler.startRefresh();
//        getPresenter().getAllClub(1,null);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onClickAttentionClub(AttentionClubEvent event){
        List<ClubDto> clubDtos = clubRecycler.getAllDatas();
        for (int i = 0 ; i < clubDtos.size(); i++){
            ClubDto clubDto = clubDtos.get(i);
            if (event.getClubId().equals(clubDto.getId())){
                clubDto.setKeepStatus(event.isAttention() ? 1 : 0);
                clubRecycler.update(clubDto,i,null);
                break;
            }
        }
    }
}
