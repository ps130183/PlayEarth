package com.km.rmbank.module.main.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.adapter.OrderMasterAdapter;
import com.km.rmbank.base.BaseFragment;
import com.km.rmbank.dto.MasterBannerDto;
import com.km.rmbank.dto.MasterDto;
import com.km.rmbank.mvp.model.HomeAppointModel;
import com.km.rmbank.mvp.presenter.HomeAppointPresenter;
import com.km.rmbank.mvp.view.IHomeAppointView;
import com.km.rmbank.oldrecycler.BaseAdapter;
import com.km.rmbank.oldrecycler.RVUtils;
import com.km.rmbank.utils.SwipeRefreshUtils;
import com.ps.glidelib.GlideUtils;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.DefaultImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeAppointFragment extends BaseFragment<IHomeAppointView,HomeAppointPresenter> implements  IHomeAppointView {


    @BindView(R.id.title)
    TextView tvTitle;


    @BindView(R.id.rv_master)
    RecyclerView rvMaster;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.banner)
    Banner banner;


    public static HomeAppointFragment newInstance(Bundle bundle) {

        HomeAppointFragment fragment = new HomeAppointFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected HomeAppointPresenter createPresenter() {
        return new HomeAppointPresenter(new HomeAppointModel());
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_home_appoint;
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {
        tvTitle.setText("遇见大咖");

        initRvMaster();
    }

    private void initRvMaster() {
        RVUtils.setLinearLayoutManage(rvMaster, LinearLayoutManager.VERTICAL);
        RVUtils.addDivideItemForRv(rvMaster);
        final OrderMasterAdapter adapter = new OrderMasterAdapter(getActivity());
        rvMaster.setAdapter(adapter);

        adapter.addLoadMore(rvMaster, new BaseAdapter.MoreDataListener() {
            @Override
            public void loadMoreData() {
                getPresenter().getMasterList(adapter.getNextPage());
            }
        });

//        SwipeRefreshUtils.initSwipeRefresh(mSwipeRefresh, new SwipeRefreshUtils.OnRereshListener() {
//            @Override
//            public void onRefresh() {
//                getPresenter().getMasterList(1);
//                getPresenter().getMasterBannerList();
//            }
//        });

        adapter.setItemClickListener(new BaseAdapter.ItemClickListener<MasterDto>() {
            @Override
            public void onItemClick(MasterDto itemData, int position) {
                showMasterInfo(itemData,itemData.getId());
            }
        });

        getPresenter().getMasterList(1);
        getPresenter().getMasterBannerList();
    }


    @Override
    public void showMastersInfo(List<MasterDto> masterDtos, int pageNo) {
        OrderMasterAdapter adapter = (OrderMasterAdapter) rvMaster.getAdapter();
        adapter.addData(masterDtos,pageNo);
    }

    @Override
    public void showMasterBannerList(final List<MasterBannerDto> bannerDtos) {
        final List<String> bannerList = new ArrayList<>();
        for (MasterBannerDto bannerDto : bannerDtos){
            bannerList.add(bannerDto.getRepresentativeImag());
        }

        banner.setImages(bannerList)
                .setDelayTime(3000)
                .setImageLoader(new DefaultImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        GlideUtils.loadImage(context, (String) path,imageView);
                    }
                }).start();

        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                getPresenter().getMasterInfo(bannerDtos.get(position).getId());
            }
        });
    }

    @Override
    public void showMasterInfo(MasterDto masterDto,String id) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("masterInfo",masterDto);
        bundle.putString("id",id);
//        startActivity(MasterInfoActivity.class,bundle);
    }

}
