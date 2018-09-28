package com.km.rmbank.module.main.scenic;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.example.zhouwei.library.CustomPopWindow;
import com.km.rmbank.R;
import com.km.rmbank.base.BaseFragment;
import com.km.rmbank.dto.ActionDto;
import com.km.rmbank.dto.ClubDto;
import com.km.rmbank.dto.MapMarkerDto;
import com.km.rmbank.dto.PayOrderDto;
import com.km.rmbank.dto.ScenicServiceDto;
import com.km.rmbank.dto.TicketDto;
import com.km.rmbank.entity.CheckDateEntity;
import com.km.rmbank.event.SelectDateResultEvent;
import com.km.rmbank.module.main.map.MapActivity;
import com.km.rmbank.module.main.payment.PaySuccessActivity;
import com.km.rmbank.module.main.payment.PaymentActivity;
import com.km.rmbank.module.webview.AgreementActivity;
import com.km.rmbank.mvp.model.ScenicServiceModel;
import com.km.rmbank.mvp.presenter.ScenicServicePresenter;
import com.km.rmbank.mvp.view.IScenicServiceView;
import com.km.rmbank.utils.Constant;
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
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScenicSpecialServiceContentFragment extends BaseFragment<IScenicServiceView, ScenicServicePresenter> implements IScenicServiceView {

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
    private String activityId;
    private ClubDto mClubDto;
    private boolean isYiZhan = false;
    private boolean isPlatformActivity = false;

    @BindView(R.id.limitPersonNum)
    TextView tvLimitPersonNum;
    private int limitPersonNum = 0;

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

        //平台基地活动 参数
        isPlatformActivity = getArguments().getBoolean("isPlatformActivity",false);
        activityId = getArguments().getString("activityId");

        if (!isPlatformActivity && mClubDto != null && mClubDto.getClubType().equals("3")) {//驿站
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

    private void initScenicServiceInfo() {

        if (isYiZhan) {
            MapMarkerDto mapMarkerDto = getArguments().getParcelable("mapMarker");
            scenicServiceName.setText(mClubDto.getClubName());
            mViewManager.findView(R.id.price).setVisibility(View.GONE);
//            mViewManager.findView(R.id.mastOrder).setVisibility(View.GONE);
            mViewManager.setText(R.id.nuit, mapMarkerDto.getAddress());
            mViewManager.setText(R.id.serviceHint, "");
        } else {
            scenicServiceName.setText(mServiceDto.getName());
            mViewManager.setText(R.id.price, "¥ " + mServiceDto.getPrice());
            mViewManager.setText(R.id.serviceHint, mServiceDto.getContent());
            if (isPlatformActivity){
                tvLimitPersonNum.setVisibility(View.VISIBLE);
                limitPersonNum = mServiceDto.getMaxReserve();
                if (limitPersonNum <= 0){
                    limitPersonNum = 0;
                    tvPersonNum.setText(limitPersonNum+"");
                } else {
                    limitPersonNum--;
                }
                tvLimitPersonNum.setText("剩余" + limitPersonNum + "个名额");
            }
        }

    }

    private void initTabFlowLayout1() {
        List<String> tabNames = new ArrayList<>();
        tabNames.add("限时活动");
        tabNames.add("专项服务");
        tabNames.add("电子券");
        TagFlowLayout flowLayout = mViewManager.findView(R.id.flowLayout1);
        flowLayout.setAdapter(new TagAdapter<String>(tabNames) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView biaoqian = (TextView) LayoutInflater.from(getContext())
                        .inflate(R.layout.flowlayout_special_service, null, false);
                biaoqian.setText(s);
                return biaoqian;
            }
        });
    }

    private void initTabFlowLayout2() {
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
                        .inflate(R.layout.flowlayout_special_service2, null, false);
                TextView biaoqian = view.findViewById(R.id.biaoqian);
                biaoqian.setText(s);
                return view;
            }
        });
    }

    /**
     * 初始化列表
     */
    private void initRecycler() {
        if (isPlatformActivity){
            int[] startTimes = DateUtils.getInstance().getYMD(DateUtils.getInstance().getDate(mServiceDto.getStartDate()));
            CheckDateEntity entity = new CheckDateEntity(startTimes[0],startTimes[1],startTimes[2]);
            entity.setCheck(true);
            checkDateEntities.add(entity);

            List<Date> nextDate = DateUtils.getInstance().getNextDate(mServiceDto.getMaxDay(),startTimes[0],startTimes[1],startTimes[2]);
            for (Date date : nextDate){
                int[] startTime = DateUtils.getInstance().getYMD(DateUtils.getInstance().dateToString(date));
                CheckDateEntity checkDateEntity = new CheckDateEntity(startTime[0],startTime[1],startTime[2]);
                checkDateEntity.setCheck(true);
                checkDateEntities.add(checkDateEntity);

            }
            dateRecycler.setVisibility(View.VISIBLE);
            totalDays.setVisibility(View.VISIBLE);
            totalDays.setText("(共" + checkDateEntities.size() + "天)");
            hint.setVisibility(View.GONE);
        }
        RecyclerAdapterHelper<CheckDateEntity> mHelper = new RecyclerAdapterHelper<>(dateRecycler);
        mHelper.addLinearLayoutManager(LinearLayoutManager.HORIZONTAL)
                .addCommonAdapter(R.layout.item_selected_date_special_service, checkDateEntities, new RecyclerAdapterHelper.CommonConvert<CheckDateEntity>() {
                    @Override
                    public void convert(CommonViewHolder holder, CheckDateEntity mData, int position) {
                        holder.setText(R.id.goOutDate, mData.getMonth() + "月" + mData.getDayOfMonty() + "日");
                        LogUtils.d(mData.getDayOfMonty() + "日");
                    }
                }).create();
    }

    private void initPopWindow() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.popup_scenic_confirm_order, null, false);
        popRecycler = view.findViewById(R.id.dateRecycler);
        popPersonNum = view.findViewById(R.id.personNum);
        popTotalDays = view.findViewById(R.id.totalDays);
        if (isPlatformActivity){
            popTotalDays.setText("(共" + checkDateEntities.size() + "天)");
        }
        popTotalMoney = view.findViewById(R.id.totalMoney);
        btnPay = view.findViewById(R.id.btn_to_pay);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isYiZhan) {
                    //去支付
                    if (isPlatformActivity){
                        getPresenter().getPlatformTicketListOfScenic(mServiceDto.getId(),clubId,activityId,0);
                    } else {
                        getPresenter().getTicketListOfScenic(mServiceDto.getId(), clubId);
                    }
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
                        holder.setText(R.id.goOutDate, mData.getMonth() + "月" + mData.getDayOfMonty() + "日");
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

    private void showPopWindow() {
        int personaNum = Integer.parseInt(tvPersonNum.getText().toString());
        if (personaNum <= 0){
            showToast("请设置出行人数");
            return;
        }
        if (mCustomPopWindow != null) {
            View anchor = mViewManager.findView(R.id.anchor);
            mCustomPopWindow.showAsDropDown(anchor, 0, 0);
        }
    }

    @OnClick(R.id.mastOrder)
    public void mastOrder(View view) {
        if (checkDateEntities == null || checkDateEntities.size() == 0) {
            showToast("请选择出行时间");
            return;
        }
        if (isYiZhan) {
            if ("4".equals(Constant.userInfo.getRoleId()) || "8".equals(Constant.userInfo.getRoleId())){
                showToast("请升级成为玩家合伙人");
                return;
            }
            String startDate = "";
            CheckDateEntity entity = checkDateEntities.get(0);
            startDate = DateUtils.getInstance().getDate(entity.getYear(), entity.getMonth(), entity.getDayOfMonty());
            getPresenter().freeTea(mClubDto.getId(), tvPersonNum.getText().toString(), startDate);
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
     *
     * @param view
     */
    @OnClick(R.id.reduce)
    public void reducePersonNum(View view) {
        if (personNum == 1) {
            return;
        }
        personNum--;
        tvPersonNum.setText(personNum + "");

        if (isPlatformActivity){
            limitPersonNum++;
            tvLimitPersonNum.setText("剩余" + limitPersonNum + "个名额");
        }

    }

    /**
     * 增加人数
     *
     * @param view
     */
    @OnClick(R.id.add)
    public void addPersonNum(View view) {
        if (isPlatformActivity){
            if (limitPersonNum <= 0){
                return;
            }
            limitPersonNum--;
            tvLimitPersonNum.setText("剩余" + limitPersonNum + "个名额");
        }


        personNum++;
        tvPersonNum.setText(personNum + "");


    }

    /**
     * 选择出行日期
     *
     * @param view
     */
    @OnClick({R.id.goOutDate, R.id.dateRecycler})
    public void goOutDate(View view) {
        if (isPlatformActivity){
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("checkDates", (ArrayList<? extends Parcelable>) checkDateEntities);
        bundle.putInt("maxDay", mServiceDto == null ? 1 : mServiceDto.getMaxDay());
        startActivity(SelectDateActivity.class, bundle);
    }

    /**
     * 跳转地图页面打开导航
     *
     * @param view
     */
    @OnClick(R.id.mapImage)
    public void openMap(View view) {
        Bundle bundle = getArguments();
        if (isPlatformActivity){
            MapMarkerDto mapMarkerDto = new MapMarkerDto(mClubDto.getClubName(),
                    TextUtils.isEmpty(mClubDto.getLatitude()) ? 0 :  Double.parseDouble(mClubDto.getLatitude()),
                    TextUtils.isEmpty(mClubDto.getLongitude()) ? 0 : Double.parseDouble(mClubDto.getLongitude()));
            mapMarkerDto.setClubType(mClubDto.getClubType());
            mapMarkerDto.setClubLogo(mClubDto.getClubLogo());
            mapMarkerDto.setAddress(mClubDto.getAddress());
            mapMarkerDto.setBackgroundImg(mClubDto.getBackgroundImg());
            mapMarkerDto.setContent(mClubDto.getContent());
            bundle.putParcelable("mapMarker",mapMarkerDto);
        }
        startActivity(MapActivity.class, getArguments());
    }

    @OnClick(R.id.iv_more1)
    public void openMore1(View view){
        Bundle bundle = new Bundle();
        bundle.putString("titleName","活动说明");
        bundle.putString("agreementUrl",mClubDto.getCommodityExplainUrl());
        startActivity(AgreementActivity.class,bundle);
    }

    @OnClick(R.id.iv_more2)
    public void openMore2(View view){
        Bundle bundle = new Bundle();
        bundle.putString("titleName","服务说明");
        bundle.putString("agreementUrl",mClubDto.getCommodityExplainUrl2());
        startActivity(AgreementActivity.class,bundle);
    }


    /**
     * 接收选择出行日期返回的结果 并刷新显示
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiverSelectDateResult(SelectDateResultEvent event) {
        List<CheckDateEntity> checkDateEntities = event.getCheckDateArray();
        totalDays.setText("(共" + checkDateEntities.size() + "天)");
        popTotalDays.setText("(共" + checkDateEntities.size() + "天)");
        this.checkDateEntities.clear();
        if (checkDateEntities != null && checkDateEntities.size() > 0) {
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
    public void showTicketList(List<TicketDto> ticketDtos,int money) {
        Bundle bundle = getArguments();
        bundle.putParcelableArrayList("ticketList", (ArrayList<? extends Parcelable>) ticketDtos);
        bundle.putInt("payType", 2);
        int personNum = Integer.parseInt(tvPersonNum.getText().toString());
        bundle.putInt("personNum", personNum);
        CheckDateEntity entity = checkDateEntities.get(0);
        String startDate = DateUtils.getInstance().getDate(entity.getYear(), entity.getMonth(), entity.getDayOfMonty());
        bundle.putString("startDate", startDate);
        startActivity(PaymentActivity.class, bundle);
    }

    @Override
    public void applyFreeTeaSuccess() {
        Bundle bundle = new Bundle();
        String hint1;
        String hint2;

        hint1 = "报名成功";
        hint2 = "请到“我的-已报名活动”中查看";

        bundle.putString("hint1", hint1);
        bundle.putString("hint2", hint2);
        startActivity(PaySuccessActivity.class, bundle);
    }

    @Override
    public void showActionInfo(ActionDto actionDto) {

    }
}
