package com.km.rmbank.module.main.scenic;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.example.zhouwei.library.CustomPopWindow;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseFragment;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.dto.MapMarkerDto;
import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.dto.ScenicServiceDto;
import com.km.rmbank.dto.TicketDto;
import com.km.rmbank.entity.CheckDateEntity;
import com.km.rmbank.event.SelectDateResultEvent;
import com.km.rmbank.module.main.map.MapActivity;
import com.km.rmbank.module.main.payment.PaymentActivity;
import com.km.rmbank.mvp.model.ScenicServiceModel;
import com.km.rmbank.mvp.presenter.ScenicServicePresenter;
import com.km.rmbank.mvp.view.IScenicServiceView;
import com.km.rmbank.utils.DateUtils;
import com.km.rmbank.utils.SystemBarHelper;
import com.ps.commonadapter.adapter.CommonViewHolder;
import com.ps.commonadapter.adapter.RecyclerAdapterHelper;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScenicSpecialServiceContentFragment extends BaseFragment<IScenicServiceView,ScenicServicePresenter> implements IScenicServiceView {

    @BindView(R.id.personNum)
    TextView tvPersonNum;
    private int personNum = 1;

    @BindView(R.id.scenicServiceName)
    TextView scenicServiceName;

    //出行日期
    @BindView(R.id.dateRecycler)
    RecyclerView dateRecycler;
    @BindView(R.id.totalDays)
    TextView totalDays;
    @BindView(R.id.hint)
    TextView hint;

    @BindView(R.id.mastOrder)
    TextView mastOrder;

    private RecyclerView popRecycler;
    private TextView popPersonNum;
    private TextView popTotalDays;
    private TextView popTotalMoney;
    private Button btnPay;

    private CustomPopWindow mCustomPopWindow;

    private List<CheckDateEntity> checkDateEntities;
    private ScenicServiceDto mServiceDto;

    private String clubId;
    private ClubDto mClubDto;
    private boolean isYiZhan = false;

    public static ScenicSpecialServiceContentFragment newInstance(Bundle bundle) {
        ScenicSpecialServiceContentFragment fragment = new ScenicSpecialServiceContentFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_scenic_special_service_content;
    }

    @Override
    protected ScenicServicePresenter createPresenter() {
        return new ScenicServicePresenter(new ScenicServiceModel());
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {
        checkDateEntities = new ArrayList<>();
        mClubDto = getArguments().getParcelable("clubDto");
        if (mClubDto != null && mClubDto.getClubType().equals("3")){//驿站
            isYiZhan = true;
        }
        mServiceDto = getArguments().getParcelable("scenicService");
        clubId = getArguments().getString("scenicId");
        initScenicServiceInfo();
        initTabFlowLayout1();
        initTabFlowLayout2();
        initRecycler();
        initPopWindow();
    }

    private void initScenicServiceInfo(){
        if (isYiZhan){
            MapMarkerDto mapMarkerDto = getArguments().getParcelable("mapMarker");
            scenicServiceName.setText(mClubDto.getClubName());
            mViewManager.findView(R.id.price).setVisibility(View.GONE);
            mViewManager.findView(R.id.mastOrder).setVisibility(View.GONE);
            mViewManager.setText(R.id.nuit,mapMarkerDto.getAddress());
            mViewManager.setText(R.id.serviceHint,"");
        } else {
            scenicServiceName.setText(mServiceDto.getName());
            mViewManager.setText(R.id.price,"¥ " + mServiceDto.getPrice());
            mViewManager.setText(R.id.serviceHint,mServiceDto.getContent());
        }

    }

    private void initTabFlowLayout1(){
        List<String> tabNames = new ArrayList<>();
        tabNames.add("显示活动");
        tabNames.add("专项服务");
        tabNames.add("电子券");
        TagFlowLayout flowLayout = mViewManager.findView(R.id.flowLayout1);
        flowLayout.setAdapter(new TagAdapter<String>(tabNames) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView biaoqian = (TextView) LayoutInflater.from(getContext())
                        .inflate(R.layout.flowlayout_special_service,null,false);
                biaoqian.setText(s);
                return biaoqian;
            }
        });
    }

    private void initTabFlowLayout2(){
        List<String> tabNames = new ArrayList<>();
        tabNames.add("舒适游");
        tabNames.add("特色旅行");
        tabNames.add("团队活动");
        tabNames.add("结交人脉");
        TagFlowLayout flowLayout = mViewManager.findView(R.id.flowLayout2);
        flowLayout.setAdapter(new TagAdapter<String>(tabNames) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                View view = LayoutInflater.from(getContext())
                        .inflate(R.layout.flowlayout_special_service2,null,false);
                TextView biaoqian = view.findViewById(R.id.biaoqian);
                biaoqian.setText(s);
                return view;
            }
        });
    }

    /**
     * 初始化列表
     */
    private void initRecycler(){
        RecyclerAdapterHelper<CheckDateEntity> mHelper = new RecyclerAdapterHelper<>(dateRecycler);
        mHelper.addLinearLayoutManager(LinearLayoutManager.HORIZONTAL)
                .addCommonAdapter(R.layout.item_selected_date_special_service, checkDateEntities, new RecyclerAdapterHelper.CommonConvert<CheckDateEntity>() {
            @Override
            public void convert(CommonViewHolder holder, CheckDateEntity mData, int position) {
                holder.setText(R.id.goOutDate,mData.getMonth() + "月" + mData.getDayOfMonty() + "日");
                LogUtils.d(mData.getDayOfMonty() + "日");
            }
        }).create();
    }

    private void initPopWindow(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.popup_scenic_confirm_order,null,false);
        popRecycler = view.findViewById(R.id.dateRecycler);
        popPersonNum = view.findViewById(R.id.personNum);
        popTotalDays = view.findViewById(R.id.totalDays);
        popTotalMoney = view.findViewById(R.id.totalMoney);
        btnPay = view.findViewById(R.id.btn_to_pay);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isYiZhan){
                    //去支付
                    getPresenter().getTicketListOfScenic(mServiceDto.getId(),clubId);
                }
            }
        });
        ImageView close = view.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomPopWindow.dissmiss();
            }
        });
        RecyclerAdapterHelper<CheckDateEntity> mHelper = new RecyclerAdapterHelper<>(popRecycler);
        mHelper.addLinearLayoutManager(LinearLayoutManager.HORIZONTAL)
                .addCommonAdapter(R.layout.item_selected_date_special_service, checkDateEntities, new RecyclerAdapterHelper.CommonConvert<CheckDateEntity>() {
                    @Override
                    public void convert(CommonViewHolder holder, CheckDateEntity mData, int position) {
                        holder.setText(R.id.goOutDate,mData.getMonth() + "月" + mData.getDayOfMonty() + "日");
                        LogUtils.d(mData.getDayOfMonty() + "日");
                    }
                }).create();
        mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(getContext())
                .setView(view)
                .size(ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight() - SystemBarHelper.getStatusBarHeight(getContext()))
                .setFocusable(true)
                .setOutsideTouchable(true)
                .create();
    }

    private void showPopWindow(){
        if (mCustomPopWindow != null){
            View anchor = mViewManager.findView(R.id.anchor);
            mCustomPopWindow.showAsDropDown(anchor,0,0);
        }
    }

    @OnClick(R.id.mastOrder)
    public void mastOrder(View view){
        if (checkDateEntities == null || checkDateEntities.size() == 0){
            showToast("请选择出行时间");
            return;
        }
        if (isYiZhan){
            showToast(getString(R.string.notOpen));
            return;
        }
        popPersonNum.setText(tvPersonNum.getText());
        int days = checkDateEntities.size();
        int personNum = Integer.parseInt(tvPersonNum.getText().toString());
        popTotalMoney.setText(mServiceDto == null ? "" : "¥ " + (mServiceDto.getPrice() * personNum));
//        CheckDateEntity entity = checkDateEntities.get(0);
//        getPresenter().applyScenicAction(mServiceDto.getId(),personNum+"", DateUtils.getInstance().getDate(entity.getYear(),entity.getMonth(),entity.getDayOfMonty()),mServiceDto.getMaxDay()+"");
        showPopWindow();
    }
    /**
     * 减少人数
     * @param view
     */
    @OnClick(R.id.reduce)
    public void reducePersonNum(View view){
        if (personNum == 1){
            return;
        }
        personNum--;
        tvPersonNum.setText(personNum+"");
    }
    /**
     * 增加人数
     * @param view
     */
    @OnClick(R.id.add)
    public void addPersonNum(View view){
        personNum++;
        tvPersonNum.setText(personNum+"");
    }

    /**
     * 选择出行日期
     * @param view
     */
    @OnClick({R.id.goOutDate,R.id.dateRecycler})
    public void goOutDate(View view){
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("checkDates", (ArrayList<? extends Parcelable>) checkDateEntities);
        bundle.putInt("maxDay",mServiceDto == null ? 1 : mServiceDto.getMaxDay());
        startActivity(SelectDateActivity.class,bundle);
    }

    /**
     * 跳转地图页面打开导航
     * @param view
     */
    @OnClick(R.id.mapImage)
    public void openMap(View view){
        startActivity(MapActivity.class,getArguments());
    }

    /**
     * 接收选择出行日期返回的结果 并刷新显示
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiverSelectDateResult(SelectDateResultEvent event){
        List<CheckDateEntity> checkDateEntities = event.getCheckDateArray();
        totalDays.setText("(共" + checkDateEntities.size() + "天)");
        popTotalDays.setText("(共" + checkDateEntities.size() + "天)");
        this.checkDateEntities.clear();
        if (checkDateEntities != null  && checkDateEntities.size() > 0){
            this.checkDateEntities.addAll(checkDateEntities);
            dateRecycler.getAdapter().notifyDataSetChanged();
            popRecycler.getAdapter().notifyDataSetChanged();
            dateRecycler.setVisibility(View.VISIBLE);
            totalDays.setVisibility(View.VISIBLE);
            hint.setVisibility(View.GONE);
        } else {
            hint.setVisibility(View.VISIBLE);
            dateRecycler.setVisibility(View.GONE);
            totalDays.setVisibility(View.GONE);
        }

    }

    @Override
    public void showTicketList(List<TicketDto> ticketDtos) {
        Bundle bundle = getArguments();
        bundle.putParcelableArrayList("ticketList", (ArrayList<? extends Parcelable>) ticketDtos);
        bundle.putInt("payType",2);
        int personNum = Integer.parseInt(tvPersonNum.getText().toString());
        bundle.putInt("personNum",personNum);
        CheckDateEntity entity = checkDateEntities.get(0);
        String startDate = DateUtils.getInstance().getDate(entity.getYear(),entity.getMonth(),entity.getDayOfMonty());
        bundle.putString("startDate",startDate);
        startActivity(PaymentActivity.class,bundle);
    }
}
