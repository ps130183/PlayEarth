package com.km.rmbank.module.main.personal.action;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseFragment;
import com.km.rmbank.dto.AppointDto;
import com.km.rmbank.module.main.appoint.ActionPastDetailActivity;
import com.km.rmbank.mvp.model.AppointModel;
import com.km.rmbank.mvp.presenter.AppointPresenter;
import com.km.rmbank.mvp.view.AppointView;
import com.km.rmbank.utils.DateUtils;
import com.ps.commonadapter.adapter.wrapper.LoadMoreWrapper;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.ps.glidelib.progress.CircleProgressView;
import com.ps.mrcyclerview.BViewHolder;
import com.ps.mrcyclerview.ItemViewConvert;
import com.ps.mrcyclerview.LoadMoreListener;
import com.ps.mrcyclerview.MRecyclerView;
import com.ps.mrcyclerview.click.OnClickItemListener;

import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActionFinishedFragment extends BaseFragment<AppointView, AppointPresenter> implements AppointView {

    private MRecyclerView<AppointDto> actionRecycler;

    public static ActionFinishedFragment newInstance(Bundle bundle) {
        ActionFinishedFragment fragment = new ActionFinishedFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public int getLayoutRes() {
        return R.layout.fragment_action_finished;
    }

    @Override
    protected AppointPresenter createPresenter() {
        return new AppointPresenter(new AppointModel());
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {
        initRecycler();
    }

    private void initRecycler() {
        actionRecycler = mViewManager.findView(R.id.actionRecycler);
        actionRecycler.addContentLayout(R.layout.item_appoint, new ItemViewConvert<AppointDto>() {
            @Override
            public void convert(@NonNull BViewHolder holder, AppointDto mData, int position, @NonNull List<Object> payloads) {
                holder.addRippleEffectOnClick();
                GlideImageView imageView = (GlideImageView) holder.getImageView(R.id.actionImae);
                CircleProgressView progressView = holder.findView(R.id.progressView);
                GlideUtils.loadImageOnPregress(imageView, mData.getActivityPictureUrl(), progressView);
                holder.setText(R.id.actionTitle, mData.getTitle());
                String time = "举办时间：<font color='#f91413'>" + DateUtils.getInstance().dateToString(new Date(mData.getStartDate()), DateUtils.YMDHM) + "</font>";
                TextView actionTime = holder.findView(R.id.actionTime);
                actionTime.setText(Html.fromHtml(time));

                holder.getTextView(R.id.memberNum).setVisibility(View.GONE);
                holder.getTextView(R.id.hint).setVisibility(View.GONE);
//                        holder.getTextView(R.id.baoming).setVisibility(View.GONE);
                holder.setText(R.id.actionAddress, "地址：" + mData.getAddress());
                holder.setText(R.id.free, mData.getStatus());
            }
        }).create();
        actionRecycler.addLoadMoreListener(new LoadMoreListener() {
            @Override
            public void loadMore(int nextPage) {
                getPresenter().getAppointAppliedList("1",nextPage,null);
            }
        });


        actionRecycler.addClickItemListener(new OnClickItemListener() {
            @Override
            public void clickItem(Object mData, int position) {
                AppointDto appointDto = (AppointDto) mData;
                if ("0".equals(appointDto.getIsDynamic())){
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("action", appointDto);
                    startActivity(AppliedActionInfoActivity.class, bundle);
                } else {
                    Bundle bundle = new Bundle();
//                    bundle.putString("actionPastId",appointDto.getActivityId());
                    bundle.putString("activityId",appointDto.getActivityId());
                    startActivity(ActionPastDetailActivity.class,bundle);
                }

            }
        });
        getPresenter().getAppointAppliedList("1",1, null);
    }

    @Override
    public void showAppointList(LoadMoreWrapper wrapper, List<AppointDto> appointDtos) {
        actionRecycler.loadDataOfNextPage(appointDtos);
    }

    @Override
    public void applyActionSuccess(String actionId) {

    }


}
