package com.km.rmbank.module.main.fragment.home;

import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.base.BaseActivity;
import com.km.rmbank.base.BaseTitleBar;
import com.km.rmbank.dto.MapMarkerDto;
import com.km.rmbank.module.main.map.MapActivity;
import com.km.rmbank.module.main.scenic.ScenicActivity;
import com.km.rmbank.mvp.model.ScenicListModel;
import com.km.rmbank.mvp.presenter.ScenicListPresenter;
import com.km.rmbank.mvp.view.ScenicListView;
import com.km.rmbank.titleBar.SimpleTitleBar;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.MultiItemTypeAdapter;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;
import com.ps.glidelib.GlideImageView;
import com.ps.glidelib.GlideUtils;
import com.ps.glidelib.progress.CircleProgressView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 首页的基地 或 会所
 */
public class ScenicListActivity extends BaseActivity<ScenicListView,ScenicListPresenter> implements ScenicListView {

    @BindView(R.id.scenicRecycler)
    RecyclerView scenicRecycler;

    private int scenicType = 0;

    private List<MapMarkerDto> mapMarkerDtoList;
    @Override
    public int getContentViewRes() {
        return R.layout.activity_scenic_list;
    }

    @Override
    public String getTitleContent() {
        scenicType = getIntent().getIntExtra("scenicType",0);
        if (scenicType == 2){
            return "基地";
        } else if (scenicType == 3){
            return "会所";
        }
        return "";
    }

    @Override
    protected void onCreateTitleBar(BaseTitleBar titleBar) {
        SimpleTitleBar simpleTitleBar = (SimpleTitleBar) titleBar;
        simpleTitleBar.setRightMenuRes(R.menu.toolbar_map);
        simpleTitleBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                getPresenter().getMapMarkers();
                return true;
            }
        });
    }

    @Override
    protected ScenicListPresenter createPresenter() {
        return new ScenicListPresenter(new ScenicListModel());
    }

    @Override
    public void onFinally(@Nullable Bundle savedInstanceState) {
        initRecycler();
    }

    /**
     * 初始化列表
     */
    private void initRecycler(){
        mapMarkerDtoList = new ArrayList<>();
        RecyclerAdapterHelper<String> mHelper = new RecyclerAdapterHelper<>(scenicRecycler);
        mHelper.addLinearLayoutManager()
                .addDividerItemDecoration(DividerItemDecoration.VERTICAL)
                .addCommonAdapter(R.layout.item_scenic_list, mapMarkerDtoList, new RecyclerAdapterHelper.CommonConvert<MapMarkerDto>() {
                    @Override
                    public void convert(CommonViewHolder holder, MapMarkerDto mData, int position) {
                        holder.addRippleEffectOnClick();
                        GlideImageView informationImage = holder.findView(R.id.informationImage);
                        CircleProgressView progressView = holder.findView(R.id.progressView);
                        GlideUtils.loadImageOnPregress(informationImage,mData.getClubLogo(),progressView);

                        TextView scenicName = holder.findView(R.id.scenicName);
                        scenicName.setText(mData.getClubName());

                        holder.setText(R.id.clubIntroduce,"地址：" + mData.getAddress());

                        holder.setText(R.id.personNum,mData.getApplyCount()+"");

                    }
                }).addRefreshView(mXRefreshView)
                .addRefreshListener(new RecyclerAdapterHelper.OnRefreshListener() {
                    @Override
                    public void refresh() {
                        getPresenter().getScenicLisit(scenicType);
                    }
                }).create();

        mHelper.getBasicAdapter().setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<MapMarkerDto>() {
            @Override
            public void onItemClick(CommonViewHolder holder, MapMarkerDto data, int position) {
                Bundle bundle = new Bundle();
                if (scenicType == 2){//基地

                } else if (scenicType == 3){//会所
                    bundle.putParcelable("mapMarker",data);
                }


                bundle.putString("scenicId",data.getId());
                startActivity(ScenicActivity.class,bundle);
            }

            @Override
            public boolean onItemLongClick(CommonViewHolder holder, MapMarkerDto data, int position) {
                return false;
            }

        });

        showLoading();
    }

    @Override
    public void showScenicList(List<MapMarkerDto> mapMarkerDtos) {
        mapMarkerDtoList.clear();
        mapMarkerDtoList.addAll(mapMarkerDtos);
        scenicRecycler.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showMapMarkerResult(List<MapMarkerDto> mapMarkerDtos) {
        Bundle bundle =new Bundle();

        bundle.putParcelableArrayList("mapMarkers", (ArrayList<? extends Parcelable>) mapMarkerDtos);
        startActivity(MapActivity.class,bundle);
    }
}
